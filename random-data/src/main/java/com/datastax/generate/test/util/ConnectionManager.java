package com.datastax.generate.test.util;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.HostDistance;
import com.datastax.driver.core.PoolingOptions;
import com.datastax.driver.core.Session;

public class ConnectionManager {
	Cluster cluster;

	public Session getConnection(String[] addresses, String keyspace) {
		PoolingOptions poolingOptions = new PoolingOptions();
		poolingOptions.setConnectionsPerHost(HostDistance.LOCAL, 4, 10).setConnectionsPerHost(HostDistance.REMOTE, 2,
				4);

		if (cluster == null) {
			cluster = Cluster.builder().addContactPoints(addresses).withPoolingOptions(poolingOptions).build();
		}
		return cluster.connect(keyspace);
	}

	public void closeConnection() {
		if (cluster != null && !cluster.isClosed()) {
			cluster.close();
		}

	}

	public Session getConnection(String[] addresses, String keyspace, String username, String password) {
		PoolingOptions poolingOptions = new PoolingOptions();
		poolingOptions.setConnectionsPerHost(HostDistance.LOCAL, 4, 10).setConnectionsPerHost(HostDistance.REMOTE, 2,
				4);

		if (cluster == null) {
			cluster = Cluster.builder().addContactPoints(addresses).withPoolingOptions(poolingOptions)
					.withCredentials(username, password).build();
		}
		return cluster.connect(keyspace);
	}

}
