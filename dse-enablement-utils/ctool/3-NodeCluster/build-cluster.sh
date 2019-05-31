# /bin/bash
#############################################################
#                   Cluster via CTOOL                       #
#############################################################
# Assumes you have ctool installed and part of your path
echo "Starting the build at `date`"

# first ask for the cluster name
read -p 'Name of your Cluster: ' CLUSTERNAME

if [ -z "$CLUSTERNAME" ]; then
    echo "You have to provide a cluster name.  Try again if you dare!!!"
    exit 1
fi

# check to see if this is an opscenter only cluster
read -p 'Is this an OpsCenter Only Cluster [Y/N] ' OPSONLY

if [ -z "$OPSONLY" ]; then
    echo "You must specify Y or N if this is an opscenter only build"
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

#############################################################
#                  start the build process                  #
#############################################################

# create a set of 3 servers using CentOS 7.2
ctool launch -p centos72 $CLUSTERNAME 3

echo "LAUNCHED THAT PUPPY!!!!"
echo " "
echo " "

# Check to see if we are installing Opscenter
if [ "$OPSONLY" == "Y" ]; then
   echo "Building OPS Center Only Cluster"
   # Install OpsCenter onto node 0
   # This will install the latest version in the public repo. Use -v to specify a version.
   ctool install -a public -i package $CLUSTERNAME opscenter

   echo "Installed OPSCENTER"

   # Start OpsCenter server.  After start go to: http://<public_dns>:8888
   ctool start $CLUSTERNAME opscenter

   echo "STARTED OPSCENTER"
   echo " "
   echo " "

else
  # install DSE
  ctool install -x ./configuration.json -a public -i package -g 1.0 --enable-graph --enable-dsefs $CLUSTERNAME enterprise

  ctool yaml $CLUSTERNAME 0 -o set -f cassandra.yaml -k num_tokens -v 16
  ctool yaml $CLUSTERNAME 0 -o delete -f cassandra.yaml -k initial_token

  ctool yaml $CLUSTERNAME 1 -o set -f cassandra.yaml -k num_tokens -v 16
  ctool yaml $CLUSTERNAME 1 -o delete -f cassandra.yaml -k initial_token

  ctool yaml $CLUSTERNAME 2 -o set -f cassandra.yaml -k num_tokens -v 16
  ctool yaml $CLUSTERNAME 2 -o delete -f cassandra.yaml -k initial_token

  # start up servers 1 at a time
  ctool start -n 0 $CLUSTERNAME enterprise
  echo "Started node 0"

  ctool start -n 1 $CLUSTERNAME enterprise
  echo "Started node 1"

  ctool start -n 2 $CLUSTERNAME enterprise
  echo "Started node 2"
  echo ""

  echo "--- Installing OpsCenter ---"
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

  # show running state
  ctool run $CLUSTERNAME 0 "dsetool ring"
fi

# ctool always create the automaton user.  Use dump_key to get the .ssh key created for this cluster.
ctool dump_key $CLUSTERNAME

# dump out info on the cluster
ctool info $CLUSTERNAME

echo "Finished the build at `date`"

