package com.axelor.apps.base.db.repo;

import com.axelor.apps.base.db.Blocking;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class BlockingRepository extends JpaRepository<Blocking> {

	public BlockingRepository() {
		super(Blocking.class);
	}

	public Blocking findByName(String name) {
		return Query.of(Blocking.class)
				.filter("self.name = :name")
				.bind("name", name)
				.fetchOne();
	}

	public static final Integer REMINDER_BLOCKING = 1;
	public static final Integer INVOICING_BLOCKING = 2;
	public static final Integer REIMBURSEMENT_BLOCKING = 3;

	public static final int PURCHASE_BLOCKING = 6;

	public static final Integer DEBIT_BLOCKING = 4;
}

