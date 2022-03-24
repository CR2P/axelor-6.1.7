package com.axelor.apps.account.db.repo;

import com.axelor.apps.account.db.BudgetDistribution;
import com.axelor.db.JpaRepository;

public class BudgetDistributionRepository extends JpaRepository<BudgetDistribution> {

	public BudgetDistributionRepository() {
		super(BudgetDistribution.class);
	}

}

