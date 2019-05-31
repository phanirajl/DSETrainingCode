package com.datastax.generate.test.tables;

import java.util.Map;
import java.util.UUID;

import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Param;
import com.datastax.driver.mapping.annotations.Query;

@Accessor
public interface TestAccessor {

	@Query("SELECT * FROM test WHERE id = :tuid AND pk2 = :pk2")
	TestMapper getTestByPartionKeys(@Param("tuid") UUID id, @Param("pk2") String pk2);

	@Query("SELECT * FROM test WHERE solr_query = :solr LIMIT 1")
	TestMapper getTestBySolrQuery(@Param("solr") String solr);

	@Query("UPDATE test SET rand_map = rand_map + :mapAddition WHERE id = :tuid AND pk2 = :pk2 AND cc1 = :cc1 AND cc2 = :cc2")
	void updateRandMap(@Param("mapAddition") Map<String, String> mapAddition, @Param("tuid") UUID id,
			@Param("pk2") String pk2, @Param("cc1") String cc1, @Param("cc2") String cc2);

}
