package com.axelor.apps.sale.db.repo;

import com.axelor.apps.sale.db.AdvancePayment;
import com.axelor.db.JpaRepository;

public class AdvancePaymentRepository extends JpaRepository<AdvancePayment> {

	public AdvancePaymentRepository() {
		super(AdvancePayment.class);
	}

	public static final int STATUS_DRAFT = 0;
	public static final int STATUS_VALIDATED = 1;
	public static final int STATUS_CANCELED = 2;
}

