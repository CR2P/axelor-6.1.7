package com.axelor.exception.db.repo;

import com.axelor.db.JpaRepository;
import com.axelor.exception.db.ExceptionOrigin;

public class ExceptionOriginRepository extends JpaRepository<ExceptionOrigin> {

	public ExceptionOriginRepository() {
		super(ExceptionOrigin.class);
	}

	/**Origin select*/

	public static final String IMPORT = "import";

	public static final String CRM = "crm";

	public static final String INVOICE_ORIGIN = "invoice";
	public static final String DEBT_RECOVERY = "debtRecovery";
	public static final String DOUBTFUL_CUSTOMER = "doubtfulCustomer";
	public static final String REIMBURSEMENT = "reimbursement";
	/**
	 * @deprecated
	 * 'Account customer' is incorrect
	 * prefer to use the correct one CUSTOMER_ACCOUNT
	 * Do not forget to delete the corresponding option in Select.xml :
	 * <selection name='trace.back.origin.select' id="trace.back.origin.select.account">
	 * when deleting this constant
	*/
	@Deprecated
	public static final String ACCOUNT_CUSTOMER = "accountCustomer";
	public static final String CUSTOMER_ACCOUNT = "customerAccount";
	public static final String MOVE_LINE_EXPORT_ORIGIN = "moveLineExport";
	public static final String IRRECOVERABLE = "irrecoverable";
	public static final String CREDIT_TRANSFER = "creditTransfer";
	public static final String REPORTED_BALANCE = "reportedBalance";
}

