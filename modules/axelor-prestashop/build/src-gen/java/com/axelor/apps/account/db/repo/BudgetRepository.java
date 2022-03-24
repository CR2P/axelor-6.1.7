package com.axelor.apps.account.db.repo;

import com.axelor.apps.account.db.Budget;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class BudgetRepository extends JpaRepository<Budget> {

	public BudgetRepository() {
		super(Budget.class);
	}

	public Budget findByCode(String code) {
		return Query.of(Budget.class)
				.filter("self.code = :code")
				.bind("code", code)
				.fetchOne();
	}

	public Budget findByName(String name) {
		return Query.of(Budget.class)
				.filter("self.name = :name")
				.bind("name", name)
				.fetchOne();
	}

	// STATUS SELECT
	public static final int STATUS_DRAFT = 1;
	public static final int STATUS_VALIDATED = 2;
}

