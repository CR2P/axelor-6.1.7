package com.axelor.apps.crm.db.repo;

import com.axelor.apps.crm.db.Opportunity;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class OpportunityRepository extends JpaRepository<Opportunity> {

	public OpportunityRepository() {
		super(Opportunity.class);
	}

	public Opportunity findByName(String name) {
		return Query.of(Opportunity.class)
				.filter("self.name = :name")
				.bind("name", name)
				.fetchOne();
	}

	// Stage SELECT
		public static final int SALES_STAGE_NEW = 1;
		public static final int SALES_STAGE_QUALIFICATION = 2;
		public static final int SALES_STAGE_PROPOSITION = 3;
		public static final int SALES_STAGE_NEGOTIATION = 4;
		public static final int SALES_STAGE_CLOSED_WON = 5;
		public static final int SALES_STAGE_CLOSED_LOST = 6;
}

