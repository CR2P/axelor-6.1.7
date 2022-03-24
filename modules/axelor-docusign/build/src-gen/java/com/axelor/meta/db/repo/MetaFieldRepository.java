package com.axelor.meta.db.repo;

import com.axelor.db.JpaRepository;
import com.axelor.db.Query;
import com.axelor.meta.db.MetaField;
import com.axelor.meta.db.MetaModel;

public class MetaFieldRepository extends JpaRepository<MetaField> {

	public MetaFieldRepository() {
		super(MetaField.class);
	}

	public MetaField findByName(String name) {
		return Query.of(MetaField.class)
				.filter("self.name = :name")
				.bind("name", name)
				.fetchOne();
	}

	public MetaField findByModel(String name, MetaModel metaModel) {
		return Query.of(MetaField.class)
				.filter("self.name = :name AND self.metaModel = :metaModel")
				.bind("name", name)
				.bind("metaModel", metaModel)
				.cacheable()
				.fetchOne();
	}

}

