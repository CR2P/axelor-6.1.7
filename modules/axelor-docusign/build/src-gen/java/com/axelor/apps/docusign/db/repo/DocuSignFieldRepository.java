package com.axelor.apps.docusign.db.repo;

import com.axelor.apps.docusign.db.DocuSignField;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class DocuSignFieldRepository extends JpaRepository<DocuSignField> {

	public DocuSignFieldRepository() {
		super(DocuSignField.class);
	}

	public DocuSignField findByName(String name) {
		return Query.of(DocuSignField.class)
				.filter("self.name = :name")
				.bind("name", name)
				.fetchOne();
	}

}

