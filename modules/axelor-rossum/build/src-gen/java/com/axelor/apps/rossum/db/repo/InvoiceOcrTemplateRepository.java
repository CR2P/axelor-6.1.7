package com.axelor.apps.rossum.db.repo;

import com.axelor.apps.rossum.db.InvoiceOcrTemplate;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class InvoiceOcrTemplateRepository extends JpaRepository<InvoiceOcrTemplate> {

	public InvoiceOcrTemplateRepository() {
		super(InvoiceOcrTemplate.class);
	}

	public InvoiceOcrTemplate findByName(String name) {
		return Query.of(InvoiceOcrTemplate.class)
				.filter("self.name = :name")
				.bind("name", name)
				.fetchOne();
	}

	//Export type select

	public static final String EXPORT_TYPE_SELECT_JSON = "json";
	public static final String EXPORT_TYPE_SELECT_CSV = "csv";
	public static final String EXPORT_TYPE_SELECT_XML = "xml";
}

