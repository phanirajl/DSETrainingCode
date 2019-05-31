# /bin/bash
#############################################################
#               OpsCenter Cluster via CTOOL                 #
#############################################################
# Assumes you have ctool installed and part of your path
echo "Starting the build at `date`"

# first ask for the cluster name
read -p 'Provision your servers (else we will build bare bones (y/n)? ' PROVISION


echo " "
echo "Remember to use: "
echo "    ctool list"
echo "To see running clusters"
echo " "
echo "And use:"
echo "    ctool info <CLUSTERNAME>"
echo "To see the servers and the ip's associated with your cluster" 
echo " "
echo "Finally use"
echo "    ctool dump_key $CLUSTERNAME"
echo "To see the key needed to log in as 'automaton' when not using ctool ssh to login to a node"
echo " "

#############################################################
#                  start the build process                  #
#############################################################

# create a set of 8 servers using CentOS 7.2
if [ "$PROVISION" == "y" ] || [ "$PROVISION" = "Y" ]   
then
    echo "Provisioning the 3 clusters of 3"

    #############################################################
    ##################      Storage Cluster     #################
    echo "Launching cluster of 3 called Storage"
    ctool launch -p centos72 Storage 3
    sleep 30
    echo "Launched cluster of 3 called Storage"

    # installs dse (enterprise) using the given configuration file for data center configuration
    # on the public ip's using the package install
    ctool install -x ./opsConfig.json -a public -i package -g 1.0 --enable-graph --enable-dsefs Storage enterprise
    echo "Installed DSE on Storage cluster"
    echo " "


    # now need to change the configuration for each server
    echo "STARTING TO CONFIGURE INDIVIDUAL servers"
    echo " "
    ctool yaml Storage 0 -o set -f cassandra.yaml -k num_tokens -v 24
    ctool yaml Storage 0 -o delete -f cassandra.yaml -k initial_token
    # update file to point to correct place for jmxremote.password 
    # this is based on 5.1.2 so need to check against other versions
    ctool scp Storage 0 ./cassandra-env.sh /etc/dse/cassandra/cassandra-env.sh

    ctool yaml Storage 1 -o set -f cassandra.yaml -k num_tokens -v 24
    ctool yaml Storage 1 -o delete -f cassandra.yaml -k initial_token
    ctool scp Storage 1 ./cassandra-env.sh /etc/dse/cassandra/cassandra-env.sh

    ctool yaml Storage 2 -o set -f cassandra.yaml -k num_tokens -v 24
    ctool yaml Storage 2 -o delete -f cassandra.yaml -k initial_token
    ctool scp Storage 2 ./cassandra-env.sh /etc/dse/cassandra/cassandra-env.sh

    # now lets start up dse seed nodes
    # node 0 is not started as opscenter is going on it
    echo "Starting DSE"
    ctool start -n 0 Storage enterprise

    # now start up a little at a time to avoid the bootstrapping messages 
    ctool start -n 1 Storage enterprise
    ctool start -n 2 Storage enterprise

    # Install OpsCenter onto node 0
    # This will install the latest version in the public repo. Use -v to specify a version.
    ctool install -a public -i package Storage opscenter

    echo "Installed OPSCENTER"
  
    # Start OpsCenter server.  After start go to: http://<public_dns>:8888
    ctool start Storage opscenter

    echo "STARTED OPSCENTER"
    echo " "
    echo " "
  
    # Add the cluster to OpsCenter and install the agents
    echo "Assigning cluster to OpsCenter"
    ctool install_agents Storage Storage
    
    echo "Installed datastax-agents"

    # start the agents
    ctool start_agents Storage
   
    echo " "
    echo "*************************************************************************************"
    echo "Check to see if started ok"
    ctool run Storage 0 "dsetool ring -l cassandra -p cassandra -a cassandra -b cassandra"
    echo " "
    echo " "


    #############################################################
    #################     Monitor Cluster     #################
    echo "Launching cluster of 3 called Monitor"
    ctool launch -p centos72 Monitor 3
    sleep 30
    echo "Launched cluster of 3 called Monitor"

    # installs dse (enterprise) using the given configuration file for data center configuration
    # on the public ip's using the package install
    ctool install -x ./monConfig.json -a public -i package -g 1.0 --enable-graph --enable-dsefs Monitor enterprise
    echo "Installed DSE on Storage cluster"
    echo " "

    # now need to change the configuration for each server
    echo "STARTING TO CONFIGURE INDIVIDUAL servers"
    echo " "
    ctool yaml Monitor 0 -o set -f cassandra.yaml -k num_tokens -v 24
    ctool yaml Monitor 0 -o delete -f cassandra.yaml -k initial_token
    ctool scp Monitor 0 ./cassandra-env.sh /etc/dse/cassandra/cassandra-env.sh

    ctool yaml Monitor 1 -o set -f cassandra.yaml -k num_tokens -v 24
    ctool yaml Monitor 1 -o delete -f cassandra.yaml -k initial_token
    ctool scp Monitor 1 ./cassandra-env.sh /etc/dse/cassandra/cassandra-env.sh

    ctool yaml Monitor 2 -o set -f cassandra.yaml -k num_tokens -v 24
    ctool yaml Monitor 2 -o delete -f cassandra.yaml -k initial_token
    ctool scp Monitor 2 ./cassandra-env.sh /etc/dse/cassandra/cassandra-env.sh

    # now lets start up dse seed nodes
    # node 0 is not started as opscenter is going on it
    echo "Starting DSE"
    ctool start -n 0 Monitor enterprise

    # now start up a little at a time to avoid the bootstrapping messages 
    ctool start -n 1 Monitor enterprise
    ctool start -n 2 Monitor enterprise

    # Add the cluster to OpsCenter and install the agents
    echo "Assigning cluster to OpsCenter"
    ctool install_agents Storage Monitor
    
    echo "Installed datastax-agents"

    # start the agents
    ctool start_agents Monitor
   
    echo " "
    echo "*************************************************************************************"
    echo "Check to see if started ok"
    ctool run Monitor 0 "dsetool ring -l cassandra -p cassandra -a cassandra -b cassandra"
    echo " "
    echo " "


    #############################################################
    ################     LCM Cluster     #################
    echo "Launching cluster of 3 called LCM"
    ctool launch -p centos72 LCM 3
    sleep 30
    echo "Launched cluster of 3 called LCM"

    # installs dse (enterprise) using the given configuration file for data center configuration
    # on the public ip's using the package install
    ctool install -x ./lcmConfig.json -a public -i package -g 1.0 --enable-graph --enable-dsefs LCM enterprise
    echo "Installed DSE on Storage cluster"
    echo " "


    # now need to change the configuration for each server
    echo "STARTING TO CONFIGURE INDIVIDUAL servers"
    echo " "
    ctool yaml LCM 0 -o set -f cassandra.yaml -k num_tokens -v 24
    ctool yaml LCM 0 -o delete -f cassandra.yaml -k initial_token
    ctool scp LCM 0 ./cassandra-env.sh /etc/dse/cassandra/cassandra-env.sh

    ctool yaml LCM 1 -o set -f cassandra.yaml -k num_tokens -v 24
    ctool yaml LCM 1 -o delete -f cassandra.yaml -k initial_token
    ctool scp LCM 1 ./cassandra-env.sh /etc/dse/cassandra/cassandra-env.sh

    ctool yaml LCM 2 -o set -f cassandra.yaml -k num_tokens -v 24
    ctool yaml LCM 2 -o delete -f cassandra.yaml -k initial_token
    ctool scp LCM 2 ./cassandra-env.sh /etc/dse/cassandra/cassandra-env.sh

    # now lets start up dse seed nodes
    # node 0 is not started as opscenter is going on it
    echo "Starting up DSE" 
    ctool start -n 0 LCM enterprise

    # now start up a little at a time to avoid the bootstrapping messages 
    ctool start -n 1 LCM enterprise
    ctool start -n 2 LCM enterprise

    # Add the cluster to OpsCenter and install the agents
    echo "Assigning Cluster to OpsCenter"
    ctool install_agents Storage LCM
    
    echo "Installed datastax-agents"

    # start the agents
    ctool start_agents LCM  
   
    echo " "
    echo "*************************************************************************************"
    echo "Check to see if started ok"
    ctool run LCM 0 "dsetool ring -l cassandra -p cassandra -a cassandra -b cassandra"
    echo " "
    echo " "

    # ctool always create the automaton user.  Use dump_key to get the .ssh key created for this cluster.
    echo "Getting ssh key for created clusters"
    ctool dump_key Storage
    ctool list
    ctool info Storage
    ctool info Monitor
    ctool info LCM

    #############################################################
    ################            Exiting         #################
    echo "Finished the build at `date`"

    exit 1

else
    echo "Setting up 9 bare nodes"
    ctool launch -p centos72 OpsTrain 9

    # ctool always create the automaton user.  Use dump_key to get the .ssh key created for this cluster.
    echo "Getting ssh key for created clusters"
    ctool dump_key OpsTrain
    ctool list
    ctool info OpsTrain

    #############################################################
    ################            Exiting         #################
    echo "Finished the build at `date`"
    exit 1    
fi


