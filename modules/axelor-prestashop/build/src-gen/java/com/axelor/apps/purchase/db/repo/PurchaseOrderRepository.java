package com.axelor.apps.purchase.db.repo;

import com.axelor.apps.purchase.db.PurchaseOrder;
import com.axelor.db.JpaRepository;

public class PurchaseOrderRepository extends JpaRepository<PurchaseOrder> {

	public PurchaseOrderRepository() {
		super(PurchaseOrder.class);
	}

	/** Static purchase order status select */
	public static final int STATUS_DRAFT = 1;

	public static final int STATUS_REQUESTED = 2;
	public static final int STATUS_VALIDATED = 3;
	public static final int STATUS_FINISHED = 4;
	public static final int STATUS_CANCELED = 5;

	/** Static purchase order receipt status select */
	public static final int STATE_NOT_RECEIVED = 1;

	public static final int STATE_PARTIALLY_RECEIVED = 2;
	public static final int STATE_RECEIVED = 3;

	/** Static invoicing type select */
	public static final int INVOICING_FREE = 1;

	public static final int INVOICING_BY_DELIVERY = 2;
	public static final int INVOICING_PER_ORDER = 3;
}

