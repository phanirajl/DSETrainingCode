# Cassandra Stress Examples

cassandra-stress write n=1000000 -node node0 

cassandra-stress read n=1000000 -node node0 -schema "replication(factor=3)"

cassandra-stress read n=500000 -node node0 -schema "replication(factor=2)”

cassandra-stress write n=1000000 -node <ip> -mode thrift user=[user] password=[password] -schema "replication(factor=2)”

cassandra-stress write n=5000 -node 192.168.1.48 -mode thrift user=cassandra password=cassandra -schema "replication(factor=2)"

cassandra-stress write n=5000 -node 192.168.1.48 -mode native cql3 user=cassandra password=cassandra

cassandra-stress write  duration=5m -node 192.168.1.48 -mode thrift user=cassandra password=cassandra -schema "replication(factor=2)"

-rate threads=150

-schema "replication(strategy=NetworkTopologyStrategy ,SDC=3, TDC=3)"

cl=QUOROM

cassandra-stress write n=1000000 -node node0 cl=QUOROM -schema "replication(strategy=NetworkTopologyStrategy ,SDC=3, TDC=3)" -rate threads=15

cassandra-stress write duration=1m -node 192.168.1.48 -mode native cql3 user=cassandra password=cassandra -schema "replication(strategy=NetworkTopologyStrategy ,DC1=2, DC2=3)" -log file=/opt/cass/stress.log


cassandra-stress mixed ratio\(write=1,read=3\) duration=1m  -pop dist=UNIFORM\(1..1000000\)  -node 192.168.1.48 -mode native cql3 user=cassandra password=cassandra -schema "replication(strategy=NetworkTopologyStrategy ,DC1=2)”


cassandra-stress mixed ratio\(write=1,read=3\) duration=1m cl=ALL -pop dist=UNIFORM\(1..1000000\) -node 192.168.1.48 -mode native cql3 user=cassandra password=cassandra -schema "replication(strategy=NetworkTopologyStrategy ,DC1=2)"

cassandra-stress mixed ratio\(write=1,read=3\) duration=1m -pop dist=UNIFORM\(1..1000000\) -node 192.168.1.48 -mode native cql3 user=cassandra password=cassandra -schema "replication(strategy=NetworkTopologyStrategy ,DC1=2)"

cassandra-stress mixed ratio\(write=1,read=3\) duration=1m -col n=FIXED\(100\) -node 192.168.1.48 -mode native cql3 user=cassandra password=cassandra -schema "replication(strategy=NetworkTopologyStrategy ,DC1=2)"

cassandra-stress write duration=1m cl=EACH_QUORUM -col n=FIXED\(100\) -node 192.168.1.48 -mode native cql3 user=cassandra password=cassandra -schema "replication(strategy=NetworkTopologyStrategy ,DC1=2)"


## New in Cassandra 3.2 the -graph option to create an html graph page
cassandra-stress user profile=tools/cqlstress-example.yaml ops\(insert=1\) -graph file=test.html title=test revision=test1

cassandra-stress user profile=sensor.yaml n=1000000 ops\(insert=3,read1=1\)

cassandra-stress user profile=./sensor_data.yaml n=1000000 ops\(insert=3,read1=1\) -rate threads=64


https://docs.datastax.com/en/cassandra/3.x/cassandra/tools/toolsCStress.html
