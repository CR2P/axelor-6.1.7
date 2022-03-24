package com.axelor.apps.docusign.db.repo;

import com.axelor.apps.docusign.db.DocuSignSigner;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class DocuSignSignerRepository extends JpaRepository<DocuSignSigner> {

	public DocuSignSignerRepository() {
		super(DocuSignSigner.class);
	}

	public DocuSignSigner findByName(String name) {
		return Query.of(DocuSignSigner.class)
				.filter("self.name = :name")
				.bind("name", name)
				.fetchOne();
	}

}

