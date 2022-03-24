package com.axelor.apps.base.db.repo;

import com.axelor.apps.base.db.AppDocuSign;
import com.axelor.db.JpaRepository;

public class AppDocuSignRepository extends JpaRepository<AppDocuSign> {

	public AppDocuSignRepository() {
		super(AppDocuSign.class);
	}

}

