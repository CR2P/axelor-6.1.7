package com.axelor.apps.purchase.db.repo;

import com.axelor.apps.purchase.db.PurchaseRequest;
import com.axelor.db.JpaRepository;

public class PurchaseRequestRepository extends JpaRepository<PurchaseRequest> {

	public PurchaseRequestRepository() {
		super(PurchaseRequest.class);
	}

	// STATUS
	public static final int STATUS_DRAFT = 1;
	public static final int STATUS_REQUESTED = 2;
	public static final int STATUS_ACCEPTED = 3;
	public static final int STATUS_PURCHASED = 4;
	public static final int STATUS_REFUSED = 5;
	public static final int STATUS_CANCELED = 6;
}

