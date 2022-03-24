package com.axelor.apps.docusign.db.repo;

import com.axelor.apps.docusign.db.DocuSignEnvelopeSetting;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class DocuSignEnvelopeSettingRepository extends JpaRepository<DocuSignEnvelopeSetting> {

	public DocuSignEnvelopeSettingRepository() {
		super(DocuSignEnvelopeSetting.class);
	}

	public DocuSignEnvelopeSetting findByName(String name) {
		return Query.of(DocuSignEnvelopeSetting.class)
				.filter("self.name = :name")
				.bind("name", name)
				.fetchOne();
	}

}

