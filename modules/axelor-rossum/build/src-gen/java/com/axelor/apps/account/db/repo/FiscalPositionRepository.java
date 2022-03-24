package com.axelor.apps.account.db.repo;

import com.axelor.apps.account.db.FiscalPosition;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class FiscalPositionRepository extends JpaRepository<FiscalPosition> {

	public FiscalPositionRepository() {
		super(FiscalPosition.class);
	}

	public FiscalPosition findByCode(String code) {
		return Query.of(FiscalPosition.class)
				.filter("self.code = :code")
				.bind("code", code)
				.fetchOne();
	}

	public FiscalPosition findByName(String name) {
		return Query.of(FiscalPosition.class)
				.filter("self.name = :name")
				.bind("name", name)
				.fetchOne();
	}

}

