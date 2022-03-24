package com.axelor.apps.docusign.db.repo;

import com.axelor.apps.docusign.db.DocuSignFieldSetting;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class DocuSignFieldSettingRepository extends JpaRepository<DocuSignFieldSetting> {

	public DocuSignFieldSettingRepository() {
		super(DocuSignFieldSetting.class);
	}

	public DocuSignFieldSetting findByName(String name) {
		return Query.of(DocuSignFieldSetting.class)
				.filter("self.name = :name")
				.bind("name", name)
				.fetchOne();
	}

	//TYPE SELECT
	public static final int TYPE_SIGN_HERE = 1;
	public static final int TYPE_FULL_NAME = 2;
	public static final int TYPE_EMAIL = 3;
	public static final int TYPE_COMPANY = 4;
	public static final int TYPE_CHECKBOX = 5;
	public static final int TYPE_RADIO_GROUP = 6;
	public static final int TYPE_RADIO_BUTTON = 7;
	public static final int TYPE_LIST = 8;
	public static final int TYPE_LIST_ITEM = 9;
	public static final int TYPE_APPROVE = 10;
	public static final int TYPE_DECLINE = 11;
}

