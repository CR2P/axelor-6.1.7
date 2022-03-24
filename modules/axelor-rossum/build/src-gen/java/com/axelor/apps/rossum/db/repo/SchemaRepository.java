package com.axelor.apps.rossum.db.repo;

import com.axelor.apps.rossum.db.Schema;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class SchemaRepository extends JpaRepository<Schema> {

	public SchemaRepository() {
		super(Schema.class);
	}

	public Schema findByUrl(String schemaUrl) {
		return Query.of(Schema.class)
				.filter("self.schemaUrl = :schemaUrl")
				.bind("schemaUrl", schemaUrl)
				.fetchOne();
	}

}

