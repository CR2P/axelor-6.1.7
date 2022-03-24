package com.axelor.apps.docusign.db.repo;

import com.axelor.apps.docusign.db.DocuSignDocument;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class DocuSignDocumentRepository extends JpaRepository<DocuSignDocument> {

	public DocuSignDocumentRepository() {
		super(DocuSignDocument.class);
	}

	public DocuSignDocument findByName(String name) {
		return Query.of(DocuSignDocument.class)
				.filter("self.name = :name")
				.bind("name", name)
				.fetchOne();
	}

}

