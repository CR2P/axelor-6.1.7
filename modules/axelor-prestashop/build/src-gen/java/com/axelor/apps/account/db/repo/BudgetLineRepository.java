package com.axelor.apps.account.db.repo;

import com.axelor.apps.account.db.BudgetLine;
import com.axelor.db.JpaRepository;

public class BudgetLineRepository extends JpaRepository<BudgetLine> {

	public BudgetLineRepository() {
		super(BudgetLine.class);
	}

}

