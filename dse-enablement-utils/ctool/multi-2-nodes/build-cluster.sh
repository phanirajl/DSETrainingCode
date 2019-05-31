# /bin/bash
#############################################################
#                   Cluster via CTOOL                       #
#############################################################
# Assumes you have ctool installed and part of your path
echo " "
echo " "

# first ask for the cluster name
read -p 'Name of your Cluster (4 char max): ' CLUSTERNAME

if [ -z "$CLUSTERNAME" ]; then
    echo "You have to provide a cluster name.  Try again if you dare!!!"
    exit 1
fi

# check to see if this is an opscenter only cluster
read -p 'How many clusters to build? ' NUM

# Validate it is a number
if ! [ -z "${NUM//[0-9]}" ] ; then
    echo "$NUM"
    echo "You must specify an Integer value for number of clusters!"
    exit 1
fi

echo " "
echo "Remember to use: "
echo "    ctool list"
echo "    To see running clusters"
echo " "
echo "And use:"
echo "    ctool info $CLUSTERNAME##"
echo "    To see the servers and the ip's associated with your cluster" 
echo " "
echo "Finally use"
echo "    ctool dump_key $CLUSTERNAME##"
echo "    To see the key needed to log in as 'automaton' when not using ctool ssh to login to a node"
echo " "
echo " "
echo "Starting the build at `date`"

#############################################################
#                  start the build process                  #
#############################################################
# looping through the number of clusters to build
for (( loop = 1; loop <="$NUM"; loop++))
  do
    echo "Building cluster named $CLUSTERNAME$loop"
    # create a set of 2 servers using CentOS 7.2
    ctool launch -p centos72 $CLUSTERNAME$loop 2
    
    echo "    Installing DSE on cluster $CLUSTERNAME$loop"
    ctool install -x ./configuration.json -a public -i package -g 1.0 --enable-graph --enable-dsefs $CLUSTERNAME$loop enterprise

    ctool yaml $CLUSTERNAME$loop 0 -o set -f cassandra.yaml -k num_tokens -v 16
    ctool yaml $CLUSTERNAME$loop 0 -o delete -f cassandra.yaml -k initial_token

    ctool yaml $CLUSTERNAME$loop 1 -o set -f cassandra.yaml -k num_tokens -v 16
    ctool yaml $CLUSTERNAME$loop 1 -o delete -f cassandra.yaml -k initial_token
    
    # start up servers 1 at a time
    ctool start -n 0 $CLUSTERNAME$loop enterprise
    echo "    Started node 0 on cluster $CLUSTERNAME$loop"

    ctool start -n 1 $CLUSTERNAME$loop enterprise
    echo "    Started node 1 on cluster $CLUSTERNAME$loop"
    
    echo "    --- Installing OpsCenter ---"
    # Install OpsCenter onto node 0
    # This will install the latest version in the public repo. Use -v to specify a version.
    ctool install -a public -i package $CLUSTERNAME$loop opscenter

    echo "    Installed OPSCENTER"

    # Start OpsCenter server.  After start go to: http://<public_dns>:8888
    ctool start $CLUSTERNAME$loop opscenter

    echo "    STARTED OPSCENTER"
    echo " "
    echo " "

    # Add the cluster to OpsCenter and install the agents 
    # this seems to not work more often than it does work
    # thus requiring you to install agents via opscenter
    ctool install_agents $CLUSTERNAME$loop $CLUSTERNAME$loop

    echo "    Installed datastax-agents"

    # start the agents
    ctool start_agents $CLUSTERNAME$loop

    echo "    Started datastax-agents"

    # show running state
    ctool run $CLUSTERNAME$loop 0 "dsetool ring"
  
    echo ""
    echo ""
done

echo "Finished the build at `date`"

