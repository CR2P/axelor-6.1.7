package com.axelor.apps.base.db.repo;

import com.axelor.apps.base.db.Company;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class CompanyRepository extends JpaRepository<Company> {

	public CompanyRepository() {
		super(Company.class);
	}

	public Company findByCode(String code) {
		return Query.of(Company.class)
				.filter("self.code = :code")
				.bind("code", code)
				.fetchOne();
	}

	public Company findByName(String name) {
		return Query.of(Company.class)
				.filter("self.name = :name")
				.bind("name", name)
				.fetchOne();
	}

	// CATEGORY SELECT
	public static final int CATEGORY_CUSTOMER = 1;
	public static final int CATEGORY_SUPPLIER = 2;
}

