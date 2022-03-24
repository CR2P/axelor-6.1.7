package com.axelor.apps.rossum.db.repo;

import com.axelor.apps.rossum.db.Schema;
import com.axelor.apps.rossum.db.SchemaField;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class SchemaFieldRepository extends JpaRepository<SchemaField> {

	public SchemaFieldRepository() {
		super(SchemaField.class);
	}

	public SchemaField findBySchemaAndId(Schema schemaUrl, String schemaFieldId) {
		return Query.of(SchemaField.class)
				.filter("self.schemaUrl = :schemaUrl AND self.schemaFieldId = :schemaFieldId")
				.bind("schemaUrl", schemaUrl)
				.bind("schemaFieldId", schemaFieldId)
				.fetchOne();
	}

}

