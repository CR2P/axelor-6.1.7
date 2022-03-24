package com.axelor.apps.docusign.db.repo;

import com.axelor.apps.docusign.db.DocuSignDocumentSetting;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class DocuSignDocumentSettingRepository extends JpaRepository<DocuSignDocumentSetting> {

	public DocuSignDocumentSettingRepository() {
		super(DocuSignDocumentSetting.class);
	}

	public DocuSignDocumentSetting findByName(String name) {
		return Query.of(DocuSignDocumentSetting.class)
				.filter("self.name = :name")
				.bind("name", name)
				.fetchOne();
	}

}

