package com.axelor.apps.account.db.repo;

import com.axelor.apps.account.db.Invoice;
import com.axelor.db.JpaRepository;

public class InvoiceRepository extends JpaRepository<Invoice> {

	public InvoiceRepository() {
		super(Invoice.class);
	}

	static final int NONE = 0;

	// OPERATION TYPE SELECT
	public static final int OPERATION_TYPE_SUPPLIER_PURCHASE = 1;
	public static final int OPERATION_TYPE_SUPPLIER_REFUND = 2;
	public static final int OPERATION_TYPE_CLIENT_SALE = 3;
	public static final int OPERATION_TYPE_CLIENT_REFUND = 4;

	// IRRECOVERABLE STATUS SELECT
	public static final int IRRECOVERABLE_STATUS_NOT_IRRECOUVRABLE = 0;
	public static final int IRRECOVERABLE_STATUS_TO_PASS_IN_IRRECOUVRABLE = 1;
	public static final int IRRECOVERABLE_STATUS_PASSED_IN_IRRECOUVRABLE = 2;

	// STATUS SELECT
	public static final int STATUS_DRAFT = 1;
	public static final int STATUS_VALIDATED = 2;
	public static final int STATUS_VENTILATED = 3;
	public static final int STATUS_CANCELED = 4;

	// OPERATION TYPE SUB SELECT
	public static final int OPERATION_SUB_TYPE_DEFAULT = 1;
	public static final int OPERATION_SUB_TYPE_ADVANCE = 2;
	public static final int OPERATION_SUB_TYPE_BALANCE = 3;

	// REPORT TYPE
	public static final int REPORT_TYPE_PROFORMA = 1;
	public static final int REPORT_TYPE_ORIGINAL_INVOICE = 2;
	public static final int REPORT_TYPE_INVOICE_WITH_PAYMENTS_DETAILS= 3;
	public static final int REPORT_TYPE_UPDATED_COPY = 4;

	// PFP VALIDATE STATUS SELECT
	public static final int PFP_STATUS_AWAITING = 1;
	public static final int PFP_STATUS_VALIDATED = 2;
	public static final int PFP_STATUS_LITIGATION = 3;

	// OPERATION TYPE SUB SELECT
	public static final int OPERATION_SUB_TYPE_SUBSCRIPTION = 6;
}

