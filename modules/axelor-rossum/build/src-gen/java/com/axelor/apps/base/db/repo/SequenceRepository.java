package com.axelor.apps.base.db.repo;

import com.axelor.apps.base.db.Company;
import com.axelor.apps.base.db.Sequence;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class SequenceRepository extends JpaRepository<Sequence> {

	public SequenceRepository() {
		super(Sequence.class);
	}

	public Sequence findByName(String name) {
		return Query.of(Sequence.class)
				.filter("self.name = :name")
				.bind("name", name)
				.fetchOne();
	}

	public Sequence find(String codeSelect, Company company) {
		return Query.of(Sequence.class)
				.filter("self.codeSelect = :codeSelect AND self.company = :company")
				.bind("codeSelect", codeSelect)
				.bind("company", company)
				.fetchOne();
	}

	public Sequence findByCodeSelect(String codeSelect) {
		return Query.of(Sequence.class)
				.filter("self.codeSelect = :codeSelect")
				.bind("codeSelect", codeSelect)
				.fetchOne();
	}

	//SEQUENCE SELECT
	public static final String PARTNER = "partner";
	public static final String PRODUCT = "product";

	//SEQUENCE SELECT
		public static final String ACCOUNT_CLEARANCE = "accountClearance";
		public static final String ACCOUNTING_REPORT = "accountingReport";
		public static final String CUSTOM_ACCOUNTING_REPORT = "customAccountingReport";
		public static final String ANALYTIC_REPORT = "analyticReport";
		public static final String CHEQUE_REJECT = "chequeReject";
		public static final String DEPOSIT_SLIP = "depositSlip";
		public static final String DEBIT = "debit";
		public static final String INVOICE = "invoice";
		public static final String IRRECOVERABLE = "irrecoverable";
		public static final String MOVE = "move";
		public static final String MOVE_LINE_EXPORT = "moveLineExport";
		public static final String PAYMENT_SCHEDULE = "paymentSchedule";
		public static final String PAYMENT_VOUCHER = "paymentVoucher";
		public static final String PAYMENT_VOUCHER_RECEIPT_NUMBER = "paymentVoucherReceiptNo";
		public static final String RECONCILE = "reconcile";
	public static final String RECONCILE_GROUP_DRAFT = "reconcileGroupDraft";
	public static final String RECONCILE_GROUP_FINAL = "reconcileGroupFinal";
		public static final String REIMBOURSEMENT = "reimbursement";
		public static final String SALES_INTERFACE = "saleInterface";
		public static final String REFUND_INTERFACE = "refundInterface";
		public static final String TREASURY_INTERFACE = "treasuryInterface";
		public static final String PURCHASE_INTERFACE = "purchaseInterface";
		public static final String SUB_ROGATION_RELEASE = "subrogationRelease";

	//SEQUENCE SELECT
	public static final String INVOICE_OCR_TEMPLATE = "invoiceOcrTemplate";
}

