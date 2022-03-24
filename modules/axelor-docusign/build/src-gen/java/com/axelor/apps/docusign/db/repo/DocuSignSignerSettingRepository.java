package com.axelor.apps.docusign.db.repo;

import com.axelor.apps.docusign.db.DocuSignSignerSetting;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class DocuSignSignerSettingRepository extends JpaRepository<DocuSignSignerSetting> {

	public DocuSignSignerSettingRepository() {
		super(DocuSignSignerSetting.class);
	}

	public DocuSignSignerSetting findByName(String name) {
		return Query.of(DocuSignSignerSetting.class)
				.filter("self.name = :name")
				.bind("name", name)
				.fetchOne();
	}

}

