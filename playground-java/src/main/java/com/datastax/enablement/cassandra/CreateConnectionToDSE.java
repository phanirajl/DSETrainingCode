package com.datastax.enablement.cassandra;

import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;

import com.datastax.driver.core.JdkSSLOptions;
import com.datastax.driver.core.RemoteEndpointAwareJdkSSLOptions;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.policies.DCAwareRoundRobinPolicy;
import com.datastax.driver.dse.DseCluster;

public class CreateConnectionToDSE {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		DCAwareRoundRobinPolicy.builder().build();
		DseCluster cluster = DseCluster.builder().addContactPoint("127.0.0.1")
				.withLoadBalancingPolicy(DCAwareRoundRobinPolicy.builder().build()).build();
		
		SSLContext sslContext = null;
		try {
			sslContext = SSLContext.getDefault();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] suites = new String[] {"AES"};
		@SuppressWarnings("deprecation")
		JdkSSLOptions options = RemoteEndpointAwareJdkSSLOptions.builder().withSSLContext(sslContext).withCipherSuites(suites).build();
		DseCluster sslcluster = DseCluster.builder().addContactPoint("127.0.0.1")
				.withLoadBalancingPolicy(DCAwareRoundRobinPolicy.builder().build()).withSSL(options).build();

		Session session = cluster.connect("bootcamp");

		ResultSet results = session.execute("SELECT count(*) FROM user_address_multiple");
		System.out.println(results.toString());
	}

}
