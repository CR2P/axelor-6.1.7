package com.axelor.apps.base.db.repo;

import com.axelor.apps.base.db.AppOffice365;
import com.axelor.db.JpaRepository;

public class AppOffice365Repository extends JpaRepository<AppOffice365> {

	public AppOffice365Repository() {
		super(AppOffice365.class);
	}

}

