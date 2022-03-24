package com.axelor.apps.base.db.repo;

import com.axelor.apps.base.db.AppRossum;
import com.axelor.db.JpaRepository;

public class AppRossumRepository extends JpaRepository<AppRossum> {

	public AppRossumRepository() {
		super(AppRossum.class);
	}

}

