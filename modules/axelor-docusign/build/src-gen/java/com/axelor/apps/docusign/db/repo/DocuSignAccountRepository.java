package com.axelor.apps.docusign.db.repo;

import com.axelor.apps.docusign.db.DocuSignAccount;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class DocuSignAccountRepository extends JpaRepository<DocuSignAccount> {

	public DocuSignAccountRepository() {
		super(DocuSignAccount.class);
	}

	public DocuSignAccount findByName(String name) {
		return Query.of(DocuSignAccount.class)
				.filter("self.name = :name")
				.bind("name", name)
				.fetchOne();
	}

}

