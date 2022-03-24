package com.axelor.apps.message.db.repo;

import com.axelor.apps.message.db.TemplateContext;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class TemplateContextRepository extends JpaRepository<TemplateContext> {

	public TemplateContextRepository() {
		super(TemplateContext.class);
	}

	public TemplateContext findByName(String name) {
		return Query.of(TemplateContext.class)
				.filter("self.name = :name")
				.bind("name", name)
				.fetchOne();
	}

}

