package com.axelor.apps.purchase.db.repo;

import com.axelor.apps.purchase.db.PurchaseOrderLine;
import com.axelor.db.JpaRepository;

public class PurchaseOrderLineRepository extends JpaRepository<PurchaseOrderLine> {

	public PurchaseOrderLineRepository() {
		super(PurchaseOrderLine.class);
	}

	// RECEIPT STATE SELECT
	public static final int RECEIPT_STATE_NOT_RECEIVED = 1;
	public static final int RECEIPT_STATE_PARTIALLY_RECEIVED = 2;
	public static final int RECEIPT_STATE_RECEIVED = 3;
}

