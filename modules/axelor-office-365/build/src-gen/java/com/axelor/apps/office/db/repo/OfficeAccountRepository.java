package com.axelor.apps.office.db.repo;

import com.axelor.apps.office.db.OfficeAccount;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class OfficeAccountRepository extends JpaRepository<OfficeAccount> {

	public OfficeAccountRepository() {
		super(OfficeAccount.class);
	}

	public OfficeAccount findByName(String name) {
		return Query.of(OfficeAccount.class)
				.filter("self.name = :name")
				.bind("name", name)
				.fetchOne();
	}

}

