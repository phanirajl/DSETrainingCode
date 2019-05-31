# /bin/bash
#############################################################
#               Perf tuning and trouble shooting
#############################################################
# Assumes you have ctool installed and part of your path
echo "Starting the build at `date`"

# first ask for the cluster name
read -p 'Name of your Cluster: ' CLUSTERNAME

if [ -z "$CLUSTERNAME" ]; then
    echo "You have to provide a cluster name.  Try again if you dare!!!"
    exit 1
fi

echo " "
echo "Remember to use: "
echo "    ctool list"
echo "To see running clusters"
echo " "
echo "And use:"
echo "    ctool info $CLUSTERNAME"
echo "To see the servers and the ip's associated with your cluster"
echo " "
echo "Finally use"
echo "    ctool dump_key $CLUSTERNAME"
echo "To see the key needed to log in as 'automaton' when not using ctool ssh to login to a node"
echo " "
echo "Starting the build of your cluster named $CLUSTERNAME"
echo " "
echo "This will take a while so don't expect to see movement soon"

#############################################################
#                  start the build process                  #
#############################################################

# create a set of 8 servers using CentOS 7.2
ctool launch -p centos72 $CLUSTERNAME 9

echo "LAUNCHED THAT PUPPY!!!!"
echo "Now installing DSE "
echo " "

# seems sometimes it takes longer than you think for everything to really get going so you end up with errors
# so will force a sleep to let all the servers initialize before moving on (2 minutes)
sleep 120

# installs dse (enterprise) using the given configuration file for data center configuration
# on the public ip's using the package install
ctool install -x ./configuration.json -a public -i package -g 1.0 --enable-graph --enable-dsefs $CLUSTERNAME enterprise

echo "Finished initial install"
echo " "
echo " "
echo "Starting to set up security"
echo " "
echo " "
# set up security
# -s for client to node encryption
# -i for node to node encryption
# -a to turn on authority service
# -r to set the role manager
# -j to set up jmx security
# turning off encryption as people are getting stuck on it, but leaving on authentication
# ctool secure -s -i -a -r dse.internal --dseauth "{'role_management':'internal','default_scheme':'internal','other_schemes':['internal','ldap','kerberos']}" -j $CLUSTERNAME
ctool secure -a -r dse.internal --dseauth "{'default_scheme':'internal','other_schemes':['ldap']}" -j $CLUSTERNAME

# now need to change the configuration for each server
#############################################################
echo "STARTING TO CONFIGURE INDIVIDUAL servers"
echo " "

#node0 is the opscenter node so DSE is not configured on it

# node1 setting up wrong intial tokens
ctool yaml $CLUSTERNAME 1 -o set -f cassandra.yaml -k num_tokens -v 16
ctool yaml $CLUSTERNAME 1 -o delete -f cassandra.yaml -k initial_token
ctool scp $CLUSTERNAME 1 ./cassandra-env.sh /etc/dse/cassandra/cassandra-env.sh


echo "DONE NODE ONE"
echo " "

# node2 setting up different inital tokens
ctool yaml $CLUSTERNAME 2 -o set -f cassandra.yaml -k num_tokens -v 32
ctool yaml $CLUSTERNAME 2 -o delete -f cassandra.yaml -k initial_token
ctool scp $CLUSTERNAME 2 ./cassandra-env.sh /etc/dse/cassandra/cassandra-env.sh

echo "DONE NODE TWO"
echo " "

# node3 setting up different inital tokens
ctool yaml $CLUSTERNAME 3 -o set -f cassandra.yaml -k num_tokens -v 128
ctool yaml $CLUSTERNAME 3 -o delete -f cassandra.yaml -k initial_token
ctool scp $CLUSTERNAME 3 ./cassandra-env.sh /etc/dse/cassandra/cassandra-env.sh

echo "DONE NODE THREE"
echo " "

# node4 setting up different inital tokens
ctool yaml $CLUSTERNAME 4 -o set -f cassandra.yaml -k num_tokens -v 256
ctool yaml $CLUSTERNAME 4 -o delete -f cassandra.yaml -k initial_token
ctool scp $CLUSTERNAME 4 ./cassandra-env.sh /etc/dse/cassandra/cassandra-env.sh


echo "DONE NODE FOUR"
echo " "

# node5
ctool yaml $CLUSTERNAME 5 -o set -f cassandra.yaml -k initial_token -v -9223372036854775808
ctool yaml $CLUSTERNAME 5 -o delete -f cassandra.yaml -k num_tokens
ctool scp $CLUSTERNAME 5 ./cassandra-env.sh /etc/dse/cassandra/cassandra-env.sh

echo "DONE NODE FIVE"
echo " "

# node6
ctool yaml $CLUSTERNAME 6 -o set -f cassandra.yaml -k initial_token -v 0
ctool yaml $CLUSTERNAME 6 -o delete -f cassandra.yaml -k num_tokens
ctool scp $CLUSTERNAME 6 ./cassandra-env.sh /etc/dse/cassandra/cassandra-env.sh

echo "DONE NODE SIX"
echo " "

# node7
ctool yaml $CLUSTERNAME 7 -o set -f cassandra.yaml -k initial_token -v 2305843009213693952
ctool yaml $CLUSTERNAME 7 -o delete -f cassandra.yaml -k num_tokens
ctool scp $CLUSTERNAME 7 ./cassandra-env.sh /etc/dse/cassandra/cassandra-env.sh

echo "DONE NODE SEVEN"
echo " "

# node8
ctool yaml $CLUSTERNAME 8 -o set -f cassandra.yaml -k initial_token -v 6917529027641081856
ctool yaml $CLUSTERNAME 8 -o delete -f cassandra.yaml -k num_tokens
ctool scp $CLUSTERNAME 8 ./cassandra-env.sh /etc/dse/cassandra/cassandra-env.sh

echo "DONE NODE EIGHT"
echo " "
echo " "

# now lets start up dse seed nodes
# node 0 is not started as opscenter is going on it
ctool start -n 1 $CLUSTERNAME enterprise
ctool start -n 5 $CLUSTERNAME enterprise

echo "Started nodes 1 and 5"

# now start up a little at a time to avoid the bootstrapping messages
ctool start -n 2 $CLUSTERNAME enterprise
ctool start -n 6 $CLUSTERNAME enterprise

echo "Started nodes 2 and 6"

ctool start -n 3 $CLUSTERNAME enterprise
ctool start -n 7 $CLUSTERNAME enterprise

echo "Started nodes 3 and 7"

ctool start -n 4 $CLUSTERNAME enterprise
ctool start -n 8 $CLUSTERNAME enterprise

echo "Started nodes 4 and 8"


# Install OpsCenter onto node 0
# This will install the latest version in the public repo. Use -v to specify a version.
ctool install -a public -i package $CLUSTERNAME opscenter

echo "Installed OPSCENTER"

# Start OpsCenter server.  After start go to: http://<public_dns>:8888
ctool start $CLUSTERNAME opscenter

echo "STARTED OPSCENTER"
echo " "
echo " "

# Add the cluster to OpsCenter and install the agents
ctool install_agents $CLUSTERNAME $CLUSTERNAME

echo "Installed datastax-agents"

# start the agents
ctool start_agents $CLUSTERNAME

echo "Started datastax-agents"

echo " "
echo "*************************************************************************************"
echo "Check to see if started ok"
ctool run $CLUSTERNAME 3 "dsetool ring -l cassandra -p cassandra -a cassandra -b cassandra"

# now we will break the nodes, first thing to do is to stop everything
echo " "
echo " "
echo "Stoping all nodes"
echo " "
ctool stop $CLUSTERNAME enterprise

echo "Now Messing with their Configurations to cause problems"

# Break node1
# turned off encryption so commenting out encrpytion changes
#ctool run $CLUSTERNAME 1 "sudo chown root:root /var/tmp/.keystore"
#ctool run $CLUSTERNAME 1 "sudo chmod 600 /var/tmp/.keystore"
#ctool run $CLUSTERNAME 1 "sudo chown root:root /var/tmp/.truststore"
#ctool run $CLUSTERNAME 1 "sudo chmod 600 /var/tmp/.truststore"
#ctool run $CLUSTERNAME 1 "sudo chown root:root /var/tmp/node*.cer"
#ctool run $CLUSTERNAME 1 "sudo chmod 600 /var/tmp/node*.cer"
ctool yaml $CLUSTERNAME 2 -o set -f cassandra.yaml -k native_transport_address -v '"localhost"'


# Break node2
ctool yaml $CLUSTERNAME 2 -o set -f cassandra.yaml -k cluster_name -v '"Bob"'
ctool yaml $CLUSTERNAME 2 -o set -f cassandra.yaml -k listen_address -v '"127.0.0.1"'

# Break node3
ctool scp $CLUSTERNAME 3 ./jvm.options /home/automaton/jvm.options
ctool run $CLUSTERNAME 3 "sudo mv /home/automaton/jvm.options /etc/dse/cassandra/jvm.options"
ctool run $CLUSTERNAME 3 "sudo chown root:root /var/lib/cassandra/data"


# Break node4
ctool run $CLUSTERNAME 4 "sudo rm /etc/dse/cassandra/jmxremote.password"
ctool yaml $CLUSTERNAME 2 -o set -f cassandra.yaml -k endpoint_snitch -v '"com.datastax.bdp.snitch.DseSimpleSnitch"'
ctool yaml $CLUSTERNAME 2 -o set -f cassandra.yaml -k seed_provider.0.parameters.0.seeds -v '"localhost"'

# Break node5
#ctool run $CLUSTERNAME 5 "sudo mv /var/tmp/.keystore /var/tmp/keystore"

# Break node6
ctool yaml $CLUSTERNAME 6 -o set -f cassandra.yaml -k compaction_throughput_mb_per_sec -v 2
ctool yaml $CLUSTERNAME 6 -o set -f cassandra.yaml -k concurrent_compactors -v 1
ctool yaml $CLUSTERNAME 6 -o set -f cassandra.yaml -k read_request_timeout_in_ms -v 50
ctool yaml $CLUSTERNAME 6 -o set -f cassandra.yaml -k write_request_timeout_in_ms -v 20

# Break node7
ctool yaml $CLUSTERNAME 7 -o set -f cassandra.yaml -k stream_throughput_outbound_megabits_per_sec -v 1
ctool yaml $CLUSTERNAME 7 -o set -f cassandra.yaml -k inter_dc_stream_throughput_outbound_megabits_per_sec -v 1

# Break node8
ctool scp $CLUSTERNAME 8 ./jvm.options /home/automaton/jvm.options
ctool run $CLUSTERNAME 8 "sudo mv /home/automaton/jvm.options /etc/dse/cassandra/jvm.options"

echo "Done Messing with the nodes, will now try to start. Not all should start correctly"
ctool start -n 1,2,3,4,5,6,7,8 $CLUSTERNAME enterprise

# start will fail as seed nodes are broke, but by trying to start messages should go into the logs
echo " "
echo "Finished trying to start again"
echo " "


# ctool always create the automaton user.  Use dump_key to get the .ssh key created for this cluster.
ctool dump_key $CLUSTERNAME

# ssh to the server and run demos
ctool list

ctool info $CLUSTERNAME

# the below won't work as I broke the seed nodes and nothing will start without them
#ctool run $CLUSTERNAME 3 "dsetool ring -l cassandra -p cassandra -a cassandra -b cassandra"

echo "Finished the build at `date`"
