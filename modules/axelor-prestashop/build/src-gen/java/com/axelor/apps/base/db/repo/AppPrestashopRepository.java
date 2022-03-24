package com.axelor.apps.base.db.repo;

import com.axelor.apps.base.db.AppPrestashop;
import com.axelor.db.JpaRepository;

public class AppPrestashopRepository extends JpaRepository<AppPrestashop> {

	public AppPrestashopRepository() {
		super(AppPrestashop.class);
	}

}

