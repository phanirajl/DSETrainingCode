# CTOOL Examples

This location contains various scripts and samples for different ctool builds that we may utilize during some of our enablement courses

## 3-NodeCluster
Script that allows you to build a 3 node cluster with an option to only install OpsCenter and then provision through LCM, or do the full install of all servers with Search, Analytics and Graph.

## opscenter
Script that creates 2 clusters.  1 to mock a production/real environment.  1 to be an opscenter only cluster.  Some manual work will need to take place after clusters are built to fully setup.

## multi-2-nodes
Script that 2 node clusters with Search, Anayltics, and Graph.  One node 0 OpsCenter is also installed.  On execution enter a cluster name (with message saying to limit to 4 characters, this is to avoid issues if running on GCE with the long naming convention there) and enter the number of clusters to build.  It will loop through and build the said number of 2 node clusters.  Count on each cluster taking 15 - 20 minutes (depending on cloud sites on a given day), thus for 10 clusters you would want to allocate at least 200 minutes (3 hours 20 minutes) for the builds to complete.  
