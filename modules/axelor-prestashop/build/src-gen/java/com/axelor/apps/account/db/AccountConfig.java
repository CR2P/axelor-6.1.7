package com.axelor.apps.account.db;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import com.axelor.apps.base.db.Company;
import com.axelor.apps.base.db.Partner;
import com.axelor.apps.base.db.Product;
import com.axelor.apps.base.db.Sequence;
import com.axelor.apps.message.db.Template;
import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.EqualsInclude;
import com.axelor.db.annotations.Track;
import com.axelor.db.annotations.TrackEvent;
import com.axelor.db.annotations.TrackField;
import com.axelor.db.annotations.Widget;
import com.axelor.meta.db.MetaFile;
import com.google.common.base.MoreObjects;

@Entity
@Cacheable
@Table(name = "ACCOUNT_ACCOUNT_CONFIG", indexes = { @Index(columnList = "customer_sales_journal"), @Index(columnList = "customer_credit_note_journal"), @Index(columnList = "supplier_purchase_journal"), @Index(columnList = "supplier_credit_note_journal"), @Index(columnList = "reject_journal"), @Index(columnList = "account_clearance_journal"), @Index(columnList = "reimbursement_journal"), @Index(columnList = "irrecoverable_journal"), @Index(columnList = "auto_misc_ope_journal"), @Index(columnList = "manual_misc_ope_journal"), @Index(columnList = "reported_balance_journal"), @Index(columnList = "sale_journal_type"), @Index(columnList = "purchase_journal_type"), @Index(columnList = "cash_journal_type"), @Index(columnList = "credit_note_journal_type"), @Index(columnList = "customer_account"), @Index(columnList = "supplier_account"), @Index(columnList = "doubtful_customer_account"), @Index(columnList = "employee_account"), @Index(columnList = "irrecoverable_account"), @Index(columnList = "cash_position_variation_account"), @Index(columnList = "advance_payment_account"), @Index(columnList = "factor_debit_account"), @Index(columnList = "factor_credit_account"), @Index(columnList = "year_opening_account"), @Index(columnList = "year_closure_account"), @Index(columnList = "customer_account_sequence"), @Index(columnList = "supplier_account_sequence"), @Index(columnList = "employee_account_sequence"), @Index(columnList = "paying_back_tax"), @Index(columnList = "in_payment_mode"), @Index(columnList = "out_payment_mode"), @Index(columnList = "def_payment_condition"), @Index(columnList = "cfonb_config"), @Index(columnList = "factor_partner"), @Index(columnList = "reimbursement_account"), @Index(columnList = "reimbursement_template"), @Index(columnList = "direct_debit_payment_mode"), @Index(columnList = "rejection_payment_mode"), @Index(columnList = "reject_payment_schedule_template"), @Index(columnList = "profit_account"), @Index(columnList = "standard_rate_tax"), @Index(columnList = "irrecoverable_standard_rate_tax"), @Index(columnList = "account_chart"), @Index(columnList = "cust_inv_sequence"), @Index(columnList = "cust_ref_sequence"), @Index(columnList = "supp_inv_sequence"), @Index(columnList = "supp_ref_sequence"), @Index(columnList = "invoicing_product"), @Index(columnList = "advance_payment_product"), @Index(columnList = "invoice_watermark"), @Index(columnList = "invoice_message_template"), @Index(columnList = "forecasted_inv_cust_account"), @Index(columnList = "forecasted_inv_supp_account") })
@Track(fields = { @TrackField(name = "company", on = TrackEvent.UPDATE), @TrackField(name = "customerSalesJournal", on = TrackEvent.UPDATE), @TrackField(name = "customerCreditNoteJournal", on = TrackEvent.UPDATE), @TrackField(name = "supplierPurchaseJournal", on = TrackEvent.UPDATE), @TrackField(name = "supplierCreditNoteJournal", on = TrackEvent.UPDATE), @TrackField(name = "rejectJournal", on = TrackEvent.UPDATE), @TrackField(name = "accountClearanceJournal", on = TrackEvent.UPDATE), @TrackField(name = "reimbursementJournal", on = TrackEvent.UPDATE), @TrackField(name = "irrecoverableJournal", on = TrackEvent.UPDATE), @TrackField(name = "autoMiscOpeJournal", on = TrackEvent.UPDATE), @TrackField(name = "manualMiscOpeJournal", on = TrackEvent.UPDATE), @TrackField(name = "saleJournalType", on = TrackEvent.UPDATE), @TrackField(name = "purchaseJournalType", on = TrackEvent.UPDATE), @TrackField(name = "cashJournalType", on = TrackEvent.UPDATE), @TrackField(name = "creditNoteJournalType", on = TrackEvent.UPDATE), @TrackField(name = "customerAccount", on = TrackEvent.UPDATE), @TrackField(name = "supplierAccount", on = TrackEvent.UPDATE), @TrackField(name = "doubtfulCustomerAccount", on = TrackEvent.UPDATE), @TrackField(name = "employeeAccount", on = TrackEvent.UPDATE), @TrackField(name = "irrecoverableAccount", on = TrackEvent.UPDATE), @TrackField(name = "cashPositionVariationAccount", on = TrackEvent.UPDATE), @TrackField(name = "advancePaymentAccount", on = TrackEvent.UPDATE), @TrackField(name = "factorDebitAccount", on = TrackEvent.UPDATE), @TrackField(name = "factorCreditAccount", on = TrackEvent.UPDATE), @TrackField(name = "partnerAccountGenerationModeSelect", on = TrackEvent.UPDATE), @TrackField(name = "customerAccountPrefix", on = TrackEvent.UPDATE), @TrackField(name = "supplierAccountPrefix", on = TrackEvent.UPDATE), @TrackField(name = "employeeAccountPrefix", on = TrackEvent.UPDATE), @TrackField(name = "customerAccountSequence", on = TrackEvent.UPDATE), @TrackField(name = "supplierAccountSequence", on = TrackEvent.UPDATE), @TrackField(name = "employeeAccountSequence", on = TrackEvent.UPDATE), @TrackField(name = "thresholdDistanceFromRegulation", on = TrackEvent.UPDATE), @TrackField(name = "autoReconcileOnInvoice", on = TrackEvent.UPDATE), @TrackField(name = "autoReconcileOnPayment", on = TrackEvent.UPDATE), @TrackField(name = "payingBackTax", on = TrackEvent.UPDATE), @TrackField(name = "inPaymentMode", on = TrackEvent.UPDATE), @TrackField(name = "outPaymentMode", on = TrackEvent.UPDATE), @TrackField(name = "defPaymentCondition", on = TrackEvent.UPDATE), @TrackField(name = "cfonbConfig", on = TrackEvent.UPDATE), @TrackField(name = "sixMonthDebtPassReason", on = TrackEvent.UPDATE), @TrackField(name = "threeMonthDebtPassReason", on = TrackEvent.UPDATE), @TrackField(name = "sixMonthDebtMonthNumber", on = TrackEvent.UPDATE), @TrackField(name = "threeMonthDebtMontsNumber", on = TrackEvent.UPDATE), @TrackField(name = "mailTransitTime", on = TrackEvent.UPDATE), @TrackField(name = "factorPartner", on = TrackEvent.UPDATE), @TrackField(name = "reimbursementExportFolderPathCFONB", on = TrackEvent.UPDATE), @TrackField(name = "reimbursementExportFolderPath", on = TrackEvent.UPDATE), @TrackField(name = "lowerThresholdReimbursement", on = TrackEvent.UPDATE), @TrackField(name = "upperThresholdReimbursement", on = TrackEvent.UPDATE), @TrackField(name = "reimbursementAccount", on = TrackEvent.UPDATE), @TrackField(name = "reimbursementTemplate", on = TrackEvent.UPDATE), @TrackField(name = "reimbursementImportFolderPathCFONB", on = TrackEvent.UPDATE), @TrackField(name = "tempReimbImportFolderPathCFONB", on = TrackEvent.UPDATE), @TrackField(name = "exportPath", on = TrackEvent.UPDATE), @TrackField(name = "exportFileName", on = TrackEvent.UPDATE), @TrackField(name = "paymentScheduleExportFolderPathCFONB", on = TrackEvent.UPDATE), @TrackField(name = "directDebitPaymentMode", on = TrackEvent.UPDATE), @TrackField(name = "rejectionPaymentMode", on = TrackEvent.UPDATE), @TrackField(name = "paymentScheduleRejectNumLimit", on = TrackEvent.UPDATE), @TrackField(name = "invoiceRejectNumLimit", on = TrackEvent.UPDATE), @TrackField(name = "rejectPaymentScheduleTemplate", on = TrackEvent.UPDATE), @TrackField(name = "profitAccount", on = TrackEvent.UPDATE), @TrackField(name = "standardRateTax", on = TrackEvent.UPDATE), @TrackField(name = "irrecoverableReasonPassage", on = TrackEvent.UPDATE), @TrackField(name = "irrecoverableStandardRateTax", on = TrackEvent.UPDATE), @TrackField(name = "allowCancelVentilatedInvoice", on = TrackEvent.UPDATE), @TrackField(name = "allowRemovalValidatedMove", on = TrackEvent.UPDATE), @TrackField(name = "generateMoveForInvoicePayment", on = TrackEvent.UPDATE), @TrackField(name = "generateMoveForAdvancePayment", on = TrackEvent.UPDATE), @TrackField(name = "fixedAssetCatReqOnInvoice", on = TrackEvent.UPDATE), @TrackField(name = "accountChart", on = TrackEvent.UPDATE), @TrackField(name = "hasChartImported", on = TrackEvent.UPDATE), @TrackField(name = "invoiceInAtiSelect", on = TrackEvent.UPDATE), @TrackField(name = "accountingDaybook", on = TrackEvent.UPDATE), @TrackField(name = "custInvSequence", on = TrackEvent.UPDATE), @TrackField(name = "custRefSequence", on = TrackEvent.UPDATE), @TrackField(name = "suppInvSequence", on = TrackEvent.UPDATE), @TrackField(name = "suppRefSequence", on = TrackEvent.UPDATE), @TrackField(name = "invoicingProduct", on = TrackEvent.UPDATE), @TrackField(name = "advancePaymentProduct", on = TrackEvent.UPDATE), @TrackField(name = "displayDelAddrOnPrinting", on = TrackEvent.UPDATE), @TrackField(name = "displayProductCodeOnPrinting", on = TrackEvent.UPDATE), @TrackField(name = "displayTaxDetailOnPrinting", on = TrackEvent.UPDATE), @TrackField(name = "displayPartnerSeqOnPrinting", on = TrackEvent.UPDATE), @TrackField(name = "invoiceClientBox", on = TrackEvent.UPDATE), @TrackField(name = "saleInvoiceLegalNote", on = TrackEvent.UPDATE), @TrackField(name = "termsAndConditions", on = TrackEvent.UPDATE), @TrackField(name = "invoiceAutomaticMail", on = TrackEvent.UPDATE), @TrackField(name = "invoiceMessageTemplate", on = TrackEvent.UPDATE), @TrackField(name = "lineMinBeforeLongReportGenerationMessageNumber", on = TrackEvent.UPDATE), @TrackField(name = "company", on = TrackEvent.UPDATE), @TrackField(name = "forecastedInvCustAccount", on = TrackEvent.UPDATE), @TrackField(name = "forecastedInvSuppAccount", on = TrackEvent.UPDATE) })
public class AccountConfig extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCOUNT_ACCOUNT_CONFIG_SEQ")
	@SequenceGenerator(name = "ACCOUNT_ACCOUNT_CONFIG_SEQ", sequenceName = "ACCOUNT_ACCOUNT_CONFIG_SEQ", allocationSize = 1)
	private Long id;

	@Widget(title = "Sales Journal")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Journal customerSalesJournal;

	@Widget(title = "Customer Credit note Journal")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Journal customerCreditNoteJournal;

	@Widget(title = "Supplier Purchase Journal")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Journal supplierPurchaseJournal;

	@Widget(title = "Supplier Credit Note Journal")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Journal supplierCreditNoteJournal;

	@Widget(title = "Rejects journal")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Journal rejectJournal;

	@Widget(title = "Account clearance journal")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Journal accountClearanceJournal;

	@Widget(title = "Reimbursement Journal")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Journal reimbursementJournal;

	@Widget(title = "Irrecoverable Journal")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Journal irrecoverableJournal;

	@Widget(title = "Auto Misc. Operation Journal")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Journal autoMiscOpeJournal;

	@Widget(title = "Manual Misc. Operation Journal")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Journal manualMiscOpeJournal;

	@Widget(title = "Reported Balance Journal")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Journal reportedBalanceJournal;

	@Widget(title = "Sales journal type")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private JournalType saleJournalType;

	@Widget(title = "Purchase journal type")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private JournalType purchaseJournalType;

	@Widget(title = "Cash journal type")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private JournalType cashJournalType;

	@Widget(title = "Credit note journal type")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private JournalType creditNoteJournalType;

	@Widget(title = "Customer account")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Account customerAccount;

	@Widget(title = "Supplier account")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Account supplierAccount;

	@Widget(title = "Doubtful customers account")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Account doubtfulCustomerAccount;

	@Widget(title = "Employee account")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Account employeeAccount;

	@Widget(title = "Irrecoverable account")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Account irrecoverableAccount;

	@Widget(title = "Cashier Regulation account")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Account cashPositionVariationAccount;

	@Widget(title = "Advance Payment Account")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Account advancePaymentAccount;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Account factorDebitAccount;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Account factorCreditAccount;

	@Widget(title = "Year opening account")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Account yearOpeningAccount;

	@Widget(title = "Year closure account")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Account yearClosureAccount;

	@Widget(title = "Automatic partner account creation mode", selection = "account.create.mode.select")
	private Integer partnerAccountGenerationModeSelect = 0;

	@Widget(title = "Autogenerated customer accounts' prefix")
	private String customerAccountPrefix;

	@Widget(title = "Autogenerated supplier accounts' prefix")
	private String supplierAccountPrefix;

	@Widget(title = "Autogenerated employee accounts' prefix")
	private String employeeAccountPrefix;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Sequence customerAccountSequence;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Sequence supplierAccountSequence;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Sequence employeeAccountSequence;

	@Widget(title = "Allowed payment range")
	private BigDecimal thresholdDistanceFromRegulation = BigDecimal.ZERO;

	@Widget(title = "Authorize auto reconcile on invoice")
	private Boolean autoReconcileOnInvoice = Boolean.FALSE;

	@Widget(title = "Authorize auto reconcile on payment")
	private Boolean autoReconcileOnPayment = Boolean.FALSE;

	@Widget(title = "Tax for management fee")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Tax payingBackTax;

	@Widget(title = "Incoming Payment Mode")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private PaymentMode inPaymentMode;

	@Widget(title = "Outgoing Payment Mode")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private PaymentMode outPaymentMode;

	@Widget(title = "Default Payment Condition")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private PaymentCondition defPaymentCondition;

	@Widget(title = "Cfonb configuration")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private CfonbConfig cfonbConfig;

	@Widget(title = "Shift Reason (long term debt)")
	private String sixMonthDebtPassReason = "Receivables over 6 months";

	@Widget(title = "Shift Reason (short term debt)")
	private String threeMonthDebtPassReason = "Receivables over 3 months";

	@Widget(title = "Long term debt duration (month)")
	private Integer sixMonthDebtMonthNumber = 6;

	@Widget(title = "Short term debt duration (month)")
	private Integer threeMonthDebtMontsNumber = 3;

	@Widget(title = "Debt recovery configuration table")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "accountConfig", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<DebtRecoveryConfigLine> debtRecoveryConfigLineList;

	@Widget(title = "Mail transit time")
	private Integer mailTransitTime = 0;

	@Widget(title = "Factor")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Partner factorPartner;

	@Widget(title = "Path for reimbursement files (CFONB)")
	private String reimbursementExportFolderPathCFONB;

	@Widget(title = "Path for reimbursement files (SEPA)")
	private String reimbursementExportFolderPath;

	@Widget(title = "Lower reimbursement limit")
	private BigDecimal lowerThresholdReimbursement = BigDecimal.ZERO;

	@Widget(title = "Upper reimbursement limit")
	private BigDecimal upperThresholdReimbursement = BigDecimal.ZERO;

	@Widget(title = "Reimbursement account")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Account reimbursementAccount;

	@Widget(title = "Reimbursement email template")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Template reimbursementTemplate;

	@Widget(title = "Filepath for rejected reimbursements")
	private String reimbursementImportFolderPathCFONB;

	@Widget(title = "Filepath for temporary rejected reimbursements import")
	private String tempReimbImportFolderPathCFONB;

	@Widget(title = "Filepath for exported files (Accounting)")
	private String exportPath;

	@Widget(title = "File name for payroll journal entries")
	private String exportFileName;

	@Widget(title = "Filepath for direct debit exports (CFONB)")
	private String paymentScheduleExportFolderPathCFONB;

	@Widget(title = "Payment mode for direct debit")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private PaymentMode directDebitPaymentMode;

	@Widget(title = "Payment mode after rejection")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private PaymentMode rejectionPaymentMode;

	@Widget(title = "Max Nbr. for payment schedule rejection")
	private Integer paymentScheduleRejectNumLimit = 0;

	@Widget(title = "Max Nbr. of invoices rejection")
	private Integer invoiceRejectNumLimit = 0;

	@Widget(title = "Email template for invoice and payment schedule rejections")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Template rejectPaymentScheduleTemplate;

	@Widget(title = "Clearance Accounts")
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Account> clearanceAccountSet;

	@Widget(title = "Profit accounts")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Account profitAccount;

	@Widget(title = "Tax standard rate for clearance")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Tax standardRateTax;

	@Widget(title = "Irrecoverable shifting reason")
	private String irrecoverableReasonPassage = "Manual shift into irrecoverable receivables";

	@Widget(title = "Tax standard rate for irrecoverables")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Tax irrecoverableStandardRateTax;

	@Widget(title = "Allow cancelation of ventilated invoice")
	private Boolean allowCancelVentilatedInvoice = Boolean.FALSE;

	@Widget(title = "Allow removal of validated move")
	private Boolean allowRemovalValidatedMove = Boolean.FALSE;

	@Widget(title = "Generate move for payment on invoice")
	private Boolean generateMoveForInvoicePayment = Boolean.TRUE;

	@Widget(title = "Generate move for advance payment")
	private Boolean generateMoveForAdvancePayment = Boolean.TRUE;

	@Widget(title = "Fixed asset category required on invoices")
	private Boolean fixedAssetCatReqOnInvoice = Boolean.FALSE;

	@Widget(title = "Print invoices in company language")
	private Boolean isPrintInvoicesInCompanyLanguage = Boolean.TRUE;

	@Widget(title = "Account chart")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private AccountChart accountChart;

	@Widget(title = "Chart imported")
	private Boolean hasChartImported = Boolean.FALSE;

	@Widget(title = "Invoice ATI/WT", selection = "base.in.ati.select")
	private Integer invoiceInAtiSelect = 1;

	@Widget(title = "Accounting Daybook")
	private Boolean accountingDaybook = Boolean.FALSE;

	@Widget(title = "Customer invoices sequence")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Sequence custInvSequence;

	@Widget(title = "Customer refunds sequence")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Sequence custRefSequence;

	@Widget(title = "Supplier invoices sequence")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Sequence suppInvSequence;

	@Widget(title = "Supplier refunds sequence")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Sequence suppRefSequence;

	@Widget(title = "Product to partially invoice sale order")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Product invoicingProduct;

	@Widget(title = "Advance payment product")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Product advancePaymentProduct;

	@Widget(title = "Display delivery address on printing")
	private Boolean displayDelAddrOnPrinting = Boolean.FALSE;

	@Widget(title = "Display product code on printing")
	private Boolean displayProductCodeOnPrinting = Boolean.FALSE;

	@Widget(title = "Display tax detail on printing")
	private Boolean displayTaxDetailOnPrinting = Boolean.FALSE;

	@Widget(title = "Display partner sequence on printing")
	private Boolean displayPartnerSeqOnPrinting = Boolean.FALSE;

	@Widget(title = "Display head office address on invoice printing")
	private Boolean displayHeadOfficeAddrOnInvoicePrinting = Boolean.FALSE;

	@Widget(title = "Client box in invoice", multiline = true)
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String invoiceClientBox;

	@Widget(title = "Legal note on sale invoices", multiline = true)
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String saleInvoiceLegalNote;

	@Widget(title = "Terms and Conditions")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String termsAndConditions;

	@Widget(title = "Watermark")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private MetaFile invoiceWatermark;

	@Widget(title = "Send email on invoice ventilation")
	private Boolean invoiceAutomaticMail = Boolean.FALSE;

	@Widget(title = "Message template")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Template invoiceMessageTemplate;

	@Widget(title = "Passed for payment")
	private Boolean isManagePassedForPayment = Boolean.FALSE;

	@Widget(title = "Manage PFP on supplier refunds")
	private Boolean isManagePFPInRefund = Boolean.FALSE;

	@Widget(title = "Number of lines minimum before long report generation message appears", help = "Minimal number of lines required to display a confirmation message when you try to export an accounting report. Value 0 is equal to no message")
	@Min(0)
	private Integer lineMinBeforeLongReportGenerationMessageNumber = 10000;

	@EqualsInclude
	@Widget(title = "Company")
	@NotNull
	@JoinColumn(unique = true)
	@OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Company company;

	@Widget(title = "Forecasted invoice customer account")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Account forecastedInvCustAccount;

	@Widget(title = "Forecasted invoice supplier account")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Account forecastedInvSuppAccount;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public AccountConfig() {
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Journal getCustomerSalesJournal() {
		return customerSalesJournal;
	}

	public void setCustomerSalesJournal(Journal customerSalesJournal) {
		this.customerSalesJournal = customerSalesJournal;
	}

	public Journal getCustomerCreditNoteJournal() {
		return customerCreditNoteJournal;
	}

	public void setCustomerCreditNoteJournal(Journal customerCreditNoteJournal) {
		this.customerCreditNoteJournal = customerCreditNoteJournal;
	}

	public Journal getSupplierPurchaseJournal() {
		return supplierPurchaseJournal;
	}

	public void setSupplierPurchaseJournal(Journal supplierPurchaseJournal) {
		this.supplierPurchaseJournal = supplierPurchaseJournal;
	}

	public Journal getSupplierCreditNoteJournal() {
		return supplierCreditNoteJournal;
	}

	public void setSupplierCreditNoteJournal(Journal supplierCreditNoteJournal) {
		this.supplierCreditNoteJournal = supplierCreditNoteJournal;
	}

	public Journal getRejectJournal() {
		return rejectJournal;
	}

	public void setRejectJournal(Journal rejectJournal) {
		this.rejectJournal = rejectJournal;
	}

	public Journal getAccountClearanceJournal() {
		return accountClearanceJournal;
	}

	public void setAccountClearanceJournal(Journal accountClearanceJournal) {
		this.accountClearanceJournal = accountClearanceJournal;
	}

	public Journal getReimbursementJournal() {
		return reimbursementJournal;
	}

	public void setReimbursementJournal(Journal reimbursementJournal) {
		this.reimbursementJournal = reimbursementJournal;
	}

	public Journal getIrrecoverableJournal() {
		return irrecoverableJournal;
	}

	public void setIrrecoverableJournal(Journal irrecoverableJournal) {
		this.irrecoverableJournal = irrecoverableJournal;
	}

	public Journal getAutoMiscOpeJournal() {
		return autoMiscOpeJournal;
	}

	public void setAutoMiscOpeJournal(Journal autoMiscOpeJournal) {
		this.autoMiscOpeJournal = autoMiscOpeJournal;
	}

	public Journal getManualMiscOpeJournal() {
		return manualMiscOpeJournal;
	}

	public void setManualMiscOpeJournal(Journal manualMiscOpeJournal) {
		this.manualMiscOpeJournal = manualMiscOpeJournal;
	}

	public Journal getReportedBalanceJournal() {
		return reportedBalanceJournal;
	}

	public void setReportedBalanceJournal(Journal reportedBalanceJournal) {
		this.reportedBalanceJournal = reportedBalanceJournal;
	}

	public JournalType getSaleJournalType() {
		return saleJournalType;
	}

	public void setSaleJournalType(JournalType saleJournalType) {
		this.saleJournalType = saleJournalType;
	}

	public JournalType getPurchaseJournalType() {
		return purchaseJournalType;
	}

	public void setPurchaseJournalType(JournalType purchaseJournalType) {
		this.purchaseJournalType = purchaseJournalType;
	}

	public JournalType getCashJournalType() {
		return cashJournalType;
	}

	public void setCashJournalType(JournalType cashJournalType) {
		this.cashJournalType = cashJournalType;
	}

	public JournalType getCreditNoteJournalType() {
		return creditNoteJournalType;
	}

	public void setCreditNoteJournalType(JournalType creditNoteJournalType) {
		this.creditNoteJournalType = creditNoteJournalType;
	}

	public Account getCustomerAccount() {
		return customerAccount;
	}

	public void setCustomerAccount(Account customerAccount) {
		this.customerAccount = customerAccount;
	}

	public Account getSupplierAccount() {
		return supplierAccount;
	}

	public void setSupplierAccount(Account supplierAccount) {
		this.supplierAccount = supplierAccount;
	}

	public Account getDoubtfulCustomerAccount() {
		return doubtfulCustomerAccount;
	}

	public void setDoubtfulCustomerAccount(Account doubtfulCustomerAccount) {
		this.doubtfulCustomerAccount = doubtfulCustomerAccount;
	}

	public Account getEmployeeAccount() {
		return employeeAccount;
	}

	public void setEmployeeAccount(Account employeeAccount) {
		this.employeeAccount = employeeAccount;
	}

	public Account getIrrecoverableAccount() {
		return irrecoverableAccount;
	}

	public void setIrrecoverableAccount(Account irrecoverableAccount) {
		this.irrecoverableAccount = irrecoverableAccount;
	}

	public Account getCashPositionVariationAccount() {
		return cashPositionVariationAccount;
	}

	public void setCashPositionVariationAccount(Account cashPositionVariationAccount) {
		this.cashPositionVariationAccount = cashPositionVariationAccount;
	}

	public Account getAdvancePaymentAccount() {
		return advancePaymentAccount;
	}

	public void setAdvancePaymentAccount(Account advancePaymentAccount) {
		this.advancePaymentAccount = advancePaymentAccount;
	}

	public Account getFactorDebitAccount() {
		return factorDebitAccount;
	}

	public void setFactorDebitAccount(Account factorDebitAccount) {
		this.factorDebitAccount = factorDebitAccount;
	}

	public Account getFactorCreditAccount() {
		return factorCreditAccount;
	}

	public void setFactorCreditAccount(Account factorCreditAccount) {
		this.factorCreditAccount = factorCreditAccount;
	}

	public Account getYearOpeningAccount() {
		return yearOpeningAccount;
	}

	public void setYearOpeningAccount(Account yearOpeningAccount) {
		this.yearOpeningAccount = yearOpeningAccount;
	}

	public Account getYearClosureAccount() {
		return yearClosureAccount;
	}

	public void setYearClosureAccount(Account yearClosureAccount) {
		this.yearClosureAccount = yearClosureAccount;
	}

	public Integer getPartnerAccountGenerationModeSelect() {
		return partnerAccountGenerationModeSelect == null ? 0 : partnerAccountGenerationModeSelect;
	}

	public void setPartnerAccountGenerationModeSelect(Integer partnerAccountGenerationModeSelect) {
		this.partnerAccountGenerationModeSelect = partnerAccountGenerationModeSelect;
	}

	public String getCustomerAccountPrefix() {
		return customerAccountPrefix;
	}

	public void setCustomerAccountPrefix(String customerAccountPrefix) {
		this.customerAccountPrefix = customerAccountPrefix;
	}

	public String getSupplierAccountPrefix() {
		return supplierAccountPrefix;
	}

	public void setSupplierAccountPrefix(String supplierAccountPrefix) {
		this.supplierAccountPrefix = supplierAccountPrefix;
	}

	public String getEmployeeAccountPrefix() {
		return employeeAccountPrefix;
	}

	public void setEmployeeAccountPrefix(String employeeAccountPrefix) {
		this.employeeAccountPrefix = employeeAccountPrefix;
	}

	public Sequence getCustomerAccountSequence() {
		return customerAccountSequence;
	}

	public void setCustomerAccountSequence(Sequence customerAccountSequence) {
		this.customerAccountSequence = customerAccountSequence;
	}

	public Sequence getSupplierAccountSequence() {
		return supplierAccountSequence;
	}

	public void setSupplierAccountSequence(Sequence supplierAccountSequence) {
		this.supplierAccountSequence = supplierAccountSequence;
	}

	public Sequence getEmployeeAccountSequence() {
		return employeeAccountSequence;
	}

	public void setEmployeeAccountSequence(Sequence employeeAccountSequence) {
		this.employeeAccountSequence = employeeAccountSequence;
	}

	public BigDecimal getThresholdDistanceFromRegulation() {
		return thresholdDistanceFromRegulation == null ? BigDecimal.ZERO : thresholdDistanceFromRegulation;
	}

	public void setThresholdDistanceFromRegulation(BigDecimal thresholdDistanceFromRegulation) {
		this.thresholdDistanceFromRegulation = thresholdDistanceFromRegulation;
	}

	public Boolean getAutoReconcileOnInvoice() {
		return autoReconcileOnInvoice == null ? Boolean.FALSE : autoReconcileOnInvoice;
	}

	public void setAutoReconcileOnInvoice(Boolean autoReconcileOnInvoice) {
		this.autoReconcileOnInvoice = autoReconcileOnInvoice;
	}

	public Boolean getAutoReconcileOnPayment() {
		return autoReconcileOnPayment == null ? Boolean.FALSE : autoReconcileOnPayment;
	}

	public void setAutoReconcileOnPayment(Boolean autoReconcileOnPayment) {
		this.autoReconcileOnPayment = autoReconcileOnPayment;
	}

	public Tax getPayingBackTax() {
		return payingBackTax;
	}

	public void setPayingBackTax(Tax payingBackTax) {
		this.payingBackTax = payingBackTax;
	}

	public PaymentMode getInPaymentMode() {
		return inPaymentMode;
	}

	public void setInPaymentMode(PaymentMode inPaymentMode) {
		this.inPaymentMode = inPaymentMode;
	}

	public PaymentMode getOutPaymentMode() {
		return outPaymentMode;
	}

	public void setOutPaymentMode(PaymentMode outPaymentMode) {
		this.outPaymentMode = outPaymentMode;
	}

	public PaymentCondition getDefPaymentCondition() {
		return defPaymentCondition;
	}

	public void setDefPaymentCondition(PaymentCondition defPaymentCondition) {
		this.defPaymentCondition = defPaymentCondition;
	}

	public CfonbConfig getCfonbConfig() {
		return cfonbConfig;
	}

	public void setCfonbConfig(CfonbConfig cfonbConfig) {
		this.cfonbConfig = cfonbConfig;
	}

	public String getSixMonthDebtPassReason() {
		return sixMonthDebtPassReason;
	}

	public void setSixMonthDebtPassReason(String sixMonthDebtPassReason) {
		this.sixMonthDebtPassReason = sixMonthDebtPassReason;
	}

	public String getThreeMonthDebtPassReason() {
		return threeMonthDebtPassReason;
	}

	public void setThreeMonthDebtPassReason(String threeMonthDebtPassReason) {
		this.threeMonthDebtPassReason = threeMonthDebtPassReason;
	}

	public Integer getSixMonthDebtMonthNumber() {
		return sixMonthDebtMonthNumber == null ? 0 : sixMonthDebtMonthNumber;
	}

	public void setSixMonthDebtMonthNumber(Integer sixMonthDebtMonthNumber) {
		this.sixMonthDebtMonthNumber = sixMonthDebtMonthNumber;
	}

	public Integer getThreeMonthDebtMontsNumber() {
		return threeMonthDebtMontsNumber == null ? 0 : threeMonthDebtMontsNumber;
	}

	public void setThreeMonthDebtMontsNumber(Integer threeMonthDebtMontsNumber) {
		this.threeMonthDebtMontsNumber = threeMonthDebtMontsNumber;
	}

	public List<DebtRecoveryConfigLine> getDebtRecoveryConfigLineList() {
		return debtRecoveryConfigLineList;
	}

	public void setDebtRecoveryConfigLineList(List<DebtRecoveryConfigLine> debtRecoveryConfigLineList) {
		this.debtRecoveryConfigLineList = debtRecoveryConfigLineList;
	}

	/**
	 * Add the given {@link DebtRecoveryConfigLine} item to the {@code debtRecoveryConfigLineList}.
	 *
	 * <p>
	 * It sets {@code item.accountConfig = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addDebtRecoveryConfigLineListItem(DebtRecoveryConfigLine item) {
		if (getDebtRecoveryConfigLineList() == null) {
			setDebtRecoveryConfigLineList(new ArrayList<>());
		}
		getDebtRecoveryConfigLineList().add(item);
		item.setAccountConfig(this);
	}

	/**
	 * Remove the given {@link DebtRecoveryConfigLine} item from the {@code debtRecoveryConfigLineList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeDebtRecoveryConfigLineListItem(DebtRecoveryConfigLine item) {
		if (getDebtRecoveryConfigLineList() == null) {
			return;
		}
		getDebtRecoveryConfigLineList().remove(item);
	}

	/**
	 * Clear the {@code debtRecoveryConfigLineList} collection.
	 *
	 * <p>
	 * If you have to query {@link DebtRecoveryConfigLine} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearDebtRecoveryConfigLineList() {
		if (getDebtRecoveryConfigLineList() != null) {
			getDebtRecoveryConfigLineList().clear();
		}
	}

	public Integer getMailTransitTime() {
		return mailTransitTime == null ? 0 : mailTransitTime;
	}

	public void setMailTransitTime(Integer mailTransitTime) {
		this.mailTransitTime = mailTransitTime;
	}

	public Partner getFactorPartner() {
		return factorPartner;
	}

	public void setFactorPartner(Partner factorPartner) {
		this.factorPartner = factorPartner;
	}

	public String getReimbursementExportFolderPathCFONB() {
		return reimbursementExportFolderPathCFONB;
	}

	public void setReimbursementExportFolderPathCFONB(String reimbursementExportFolderPathCFONB) {
		this.reimbursementExportFolderPathCFONB = reimbursementExportFolderPathCFONB;
	}

	public String getReimbursementExportFolderPath() {
		return reimbursementExportFolderPath;
	}

	public void setReimbursementExportFolderPath(String reimbursementExportFolderPath) {
		this.reimbursementExportFolderPath = reimbursementExportFolderPath;
	}

	public BigDecimal getLowerThresholdReimbursement() {
		return lowerThresholdReimbursement == null ? BigDecimal.ZERO : lowerThresholdReimbursement;
	}

	public void setLowerThresholdReimbursement(BigDecimal lowerThresholdReimbursement) {
		this.lowerThresholdReimbursement = lowerThresholdReimbursement;
	}

	public BigDecimal getUpperThresholdReimbursement() {
		return upperThresholdReimbursement == null ? BigDecimal.ZERO : upperThresholdReimbursement;
	}

	public void setUpperThresholdReimbursement(BigDecimal upperThresholdReimbursement) {
		this.upperThresholdReimbursement = upperThresholdReimbursement;
	}

	public Account getReimbursementAccount() {
		return reimbursementAccount;
	}

	public void setReimbursementAccount(Account reimbursementAccount) {
		this.reimbursementAccount = reimbursementAccount;
	}

	public Template getReimbursementTemplate() {
		return reimbursementTemplate;
	}

	public void setReimbursementTemplate(Template reimbursementTemplate) {
		this.reimbursementTemplate = reimbursementTemplate;
	}

	public String getReimbursementImportFolderPathCFONB() {
		return reimbursementImportFolderPathCFONB;
	}

	public void setReimbursementImportFolderPathCFONB(String reimbursementImportFolderPathCFONB) {
		this.reimbursementImportFolderPathCFONB = reimbursementImportFolderPathCFONB;
	}

	public String getTempReimbImportFolderPathCFONB() {
		return tempReimbImportFolderPathCFONB;
	}

	public void setTempReimbImportFolderPathCFONB(String tempReimbImportFolderPathCFONB) {
		this.tempReimbImportFolderPathCFONB = tempReimbImportFolderPathCFONB;
	}

	public String getExportPath() {
		return exportPath;
	}

	public void setExportPath(String exportPath) {
		this.exportPath = exportPath;
	}

	public String getExportFileName() {
		return exportFileName;
	}

	public void setExportFileName(String exportFileName) {
		this.exportFileName = exportFileName;
	}

	public String getPaymentScheduleExportFolderPathCFONB() {
		return paymentScheduleExportFolderPathCFONB;
	}

	public void setPaymentScheduleExportFolderPathCFONB(String paymentScheduleExportFolderPathCFONB) {
		this.paymentScheduleExportFolderPathCFONB = paymentScheduleExportFolderPathCFONB;
	}

	public PaymentMode getDirectDebitPaymentMode() {
		return directDebitPaymentMode;
	}

	public void setDirectDebitPaymentMode(PaymentMode directDebitPaymentMode) {
		this.directDebitPaymentMode = directDebitPaymentMode;
	}

	public PaymentMode getRejectionPaymentMode() {
		return rejectionPaymentMode;
	}

	public void setRejectionPaymentMode(PaymentMode rejectionPaymentMode) {
		this.rejectionPaymentMode = rejectionPaymentMode;
	}

	public Integer getPaymentScheduleRejectNumLimit() {
		return paymentScheduleRejectNumLimit == null ? 0 : paymentScheduleRejectNumLimit;
	}

	public void setPaymentScheduleRejectNumLimit(Integer paymentScheduleRejectNumLimit) {
		this.paymentScheduleRejectNumLimit = paymentScheduleRejectNumLimit;
	}

	public Integer getInvoiceRejectNumLimit() {
		return invoiceRejectNumLimit == null ? 0 : invoiceRejectNumLimit;
	}

	public void setInvoiceRejectNumLimit(Integer invoiceRejectNumLimit) {
		this.invoiceRejectNumLimit = invoiceRejectNumLimit;
	}

	public Template getRejectPaymentScheduleTemplate() {
		return rejectPaymentScheduleTemplate;
	}

	public void setRejectPaymentScheduleTemplate(Template rejectPaymentScheduleTemplate) {
		this.rejectPaymentScheduleTemplate = rejectPaymentScheduleTemplate;
	}

	public Set<Account> getClearanceAccountSet() {
		return clearanceAccountSet;
	}

	public void setClearanceAccountSet(Set<Account> clearanceAccountSet) {
		this.clearanceAccountSet = clearanceAccountSet;
	}

	/**
	 * Add the given {@link Account} item to the {@code clearanceAccountSet}.
	 *
	 * @param item
	 *            the item to add
	 */
	public void addClearanceAccountSetItem(Account item) {
		if (getClearanceAccountSet() == null) {
			setClearanceAccountSet(new HashSet<>());
		}
		getClearanceAccountSet().add(item);
	}

	/**
	 * Remove the given {@link Account} item from the {@code clearanceAccountSet}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeClearanceAccountSetItem(Account item) {
		if (getClearanceAccountSet() == null) {
			return;
		}
		getClearanceAccountSet().remove(item);
	}

	/**
	 * Clear the {@code clearanceAccountSet} collection.
	 *
	 */
	public void clearClearanceAccountSet() {
		if (getClearanceAccountSet() != null) {
			getClearanceAccountSet().clear();
		}
	}

	public Account getProfitAccount() {
		return profitAccount;
	}

	public void setProfitAccount(Account profitAccount) {
		this.profitAccount = profitAccount;
	}

	public Tax getStandardRateTax() {
		return standardRateTax;
	}

	public void setStandardRateTax(Tax standardRateTax) {
		this.standardRateTax = standardRateTax;
	}

	public String getIrrecoverableReasonPassage() {
		return irrecoverableReasonPassage;
	}

	public void setIrrecoverableReasonPassage(String irrecoverableReasonPassage) {
		this.irrecoverableReasonPassage = irrecoverableReasonPassage;
	}

	public Tax getIrrecoverableStandardRateTax() {
		return irrecoverableStandardRateTax;
	}

	public void setIrrecoverableStandardRateTax(Tax irrecoverableStandardRateTax) {
		this.irrecoverableStandardRateTax = irrecoverableStandardRateTax;
	}

	public Boolean getAllowCancelVentilatedInvoice() {
		return allowCancelVentilatedInvoice == null ? Boolean.FALSE : allowCancelVentilatedInvoice;
	}

	public void setAllowCancelVentilatedInvoice(Boolean allowCancelVentilatedInvoice) {
		this.allowCancelVentilatedInvoice = allowCancelVentilatedInvoice;
	}

	public Boolean getAllowRemovalValidatedMove() {
		return allowRemovalValidatedMove == null ? Boolean.FALSE : allowRemovalValidatedMove;
	}

	public void setAllowRemovalValidatedMove(Boolean allowRemovalValidatedMove) {
		this.allowRemovalValidatedMove = allowRemovalValidatedMove;
	}

	public Boolean getGenerateMoveForInvoicePayment() {
		return generateMoveForInvoicePayment == null ? Boolean.FALSE : generateMoveForInvoicePayment;
	}

	public void setGenerateMoveForInvoicePayment(Boolean generateMoveForInvoicePayment) {
		this.generateMoveForInvoicePayment = generateMoveForInvoicePayment;
	}

	public Boolean getGenerateMoveForAdvancePayment() {
		return generateMoveForAdvancePayment == null ? Boolean.FALSE : generateMoveForAdvancePayment;
	}

	public void setGenerateMoveForAdvancePayment(Boolean generateMoveForAdvancePayment) {
		this.generateMoveForAdvancePayment = generateMoveForAdvancePayment;
	}

	public Boolean getFixedAssetCatReqOnInvoice() {
		return fixedAssetCatReqOnInvoice == null ? Boolean.FALSE : fixedAssetCatReqOnInvoice;
	}

	public void setFixedAssetCatReqOnInvoice(Boolean fixedAssetCatReqOnInvoice) {
		this.fixedAssetCatReqOnInvoice = fixedAssetCatReqOnInvoice;
	}

	public Boolean getIsPrintInvoicesInCompanyLanguage() {
		return isPrintInvoicesInCompanyLanguage == null ? Boolean.FALSE : isPrintInvoicesInCompanyLanguage;
	}

	public void setIsPrintInvoicesInCompanyLanguage(Boolean isPrintInvoicesInCompanyLanguage) {
		this.isPrintInvoicesInCompanyLanguage = isPrintInvoicesInCompanyLanguage;
	}

	public AccountChart getAccountChart() {
		return accountChart;
	}

	public void setAccountChart(AccountChart accountChart) {
		this.accountChart = accountChart;
	}

	public Boolean getHasChartImported() {
		return hasChartImported == null ? Boolean.FALSE : hasChartImported;
	}

	public void setHasChartImported(Boolean hasChartImported) {
		this.hasChartImported = hasChartImported;
	}

	public Integer getInvoiceInAtiSelect() {
		return invoiceInAtiSelect == null ? 0 : invoiceInAtiSelect;
	}

	public void setInvoiceInAtiSelect(Integer invoiceInAtiSelect) {
		this.invoiceInAtiSelect = invoiceInAtiSelect;
	}

	public Boolean getAccountingDaybook() {
		return accountingDaybook == null ? Boolean.FALSE : accountingDaybook;
	}

	public void setAccountingDaybook(Boolean accountingDaybook) {
		this.accountingDaybook = accountingDaybook;
	}

	public Sequence getCustInvSequence() {
		return custInvSequence;
	}

	public void setCustInvSequence(Sequence custInvSequence) {
		this.custInvSequence = custInvSequence;
	}

	public Sequence getCustRefSequence() {
		return custRefSequence;
	}

	public void setCustRefSequence(Sequence custRefSequence) {
		this.custRefSequence = custRefSequence;
	}

	public Sequence getSuppInvSequence() {
		return suppInvSequence;
	}

	public void setSuppInvSequence(Sequence suppInvSequence) {
		this.suppInvSequence = suppInvSequence;
	}

	public Sequence getSuppRefSequence() {
		return suppRefSequence;
	}

	public void setSuppRefSequence(Sequence suppRefSequence) {
		this.suppRefSequence = suppRefSequence;
	}

	public Product getInvoicingProduct() {
		return invoicingProduct;
	}

	public void setInvoicingProduct(Product invoicingProduct) {
		this.invoicingProduct = invoicingProduct;
	}

	public Product getAdvancePaymentProduct() {
		return advancePaymentProduct;
	}

	public void setAdvancePaymentProduct(Product advancePaymentProduct) {
		this.advancePaymentProduct = advancePaymentProduct;
	}

	public Boolean getDisplayDelAddrOnPrinting() {
		return displayDelAddrOnPrinting == null ? Boolean.FALSE : displayDelAddrOnPrinting;
	}

	public void setDisplayDelAddrOnPrinting(Boolean displayDelAddrOnPrinting) {
		this.displayDelAddrOnPrinting = displayDelAddrOnPrinting;
	}

	public Boolean getDisplayProductCodeOnPrinting() {
		return displayProductCodeOnPrinting == null ? Boolean.FALSE : displayProductCodeOnPrinting;
	}

	public void setDisplayProductCodeOnPrinting(Boolean displayProductCodeOnPrinting) {
		this.displayProductCodeOnPrinting = displayProductCodeOnPrinting;
	}

	public Boolean getDisplayTaxDetailOnPrinting() {
		return displayTaxDetailOnPrinting == null ? Boolean.FALSE : displayTaxDetailOnPrinting;
	}

	public void setDisplayTaxDetailOnPrinting(Boolean displayTaxDetailOnPrinting) {
		this.displayTaxDetailOnPrinting = displayTaxDetailOnPrinting;
	}

	public Boolean getDisplayPartnerSeqOnPrinting() {
		return displayPartnerSeqOnPrinting == null ? Boolean.FALSE : displayPartnerSeqOnPrinting;
	}

	public void setDisplayPartnerSeqOnPrinting(Boolean displayPartnerSeqOnPrinting) {
		this.displayPartnerSeqOnPrinting = displayPartnerSeqOnPrinting;
	}

	public Boolean getDisplayHeadOfficeAddrOnInvoicePrinting() {
		return displayHeadOfficeAddrOnInvoicePrinting == null ? Boolean.FALSE : displayHeadOfficeAddrOnInvoicePrinting;
	}

	public void setDisplayHeadOfficeAddrOnInvoicePrinting(Boolean displayHeadOfficeAddrOnInvoicePrinting) {
		this.displayHeadOfficeAddrOnInvoicePrinting = displayHeadOfficeAddrOnInvoicePrinting;
	}

	public String getInvoiceClientBox() {
		return invoiceClientBox;
	}

	public void setInvoiceClientBox(String invoiceClientBox) {
		this.invoiceClientBox = invoiceClientBox;
	}

	public String getSaleInvoiceLegalNote() {
		return saleInvoiceLegalNote;
	}

	public void setSaleInvoiceLegalNote(String saleInvoiceLegalNote) {
		this.saleInvoiceLegalNote = saleInvoiceLegalNote;
	}

	public String getTermsAndConditions() {
		return termsAndConditions;
	}

	public void setTermsAndConditions(String termsAndConditions) {
		this.termsAndConditions = termsAndConditions;
	}

	public MetaFile getInvoiceWatermark() {
		return invoiceWatermark;
	}

	public void setInvoiceWatermark(MetaFile invoiceWatermark) {
		this.invoiceWatermark = invoiceWatermark;
	}

	public Boolean getInvoiceAutomaticMail() {
		return invoiceAutomaticMail == null ? Boolean.FALSE : invoiceAutomaticMail;
	}

	public void setInvoiceAutomaticMail(Boolean invoiceAutomaticMail) {
		this.invoiceAutomaticMail = invoiceAutomaticMail;
	}

	public Template getInvoiceMessageTemplate() {
		return invoiceMessageTemplate;
	}

	public void setInvoiceMessageTemplate(Template invoiceMessageTemplate) {
		this.invoiceMessageTemplate = invoiceMessageTemplate;
	}

	public Boolean getIsManagePassedForPayment() {
		return isManagePassedForPayment == null ? Boolean.FALSE : isManagePassedForPayment;
	}

	public void setIsManagePassedForPayment(Boolean isManagePassedForPayment) {
		this.isManagePassedForPayment = isManagePassedForPayment;
	}

	public Boolean getIsManagePFPInRefund() {
		return isManagePFPInRefund == null ? Boolean.FALSE : isManagePFPInRefund;
	}

	public void setIsManagePFPInRefund(Boolean isManagePFPInRefund) {
		this.isManagePFPInRefund = isManagePFPInRefund;
	}

	/**
	 * Minimal number of lines required to display a confirmation message when you try to export an accounting report. Value 0 is equal to no message
	 *
	 * @return the property value
	 */
	public Integer getLineMinBeforeLongReportGenerationMessageNumber() {
		return lineMinBeforeLongReportGenerationMessageNumber == null ? 0 : lineMinBeforeLongReportGenerationMessageNumber;
	}

	public void setLineMinBeforeLongReportGenerationMessageNumber(Integer lineMinBeforeLongReportGenerationMessageNumber) {
		this.lineMinBeforeLongReportGenerationMessageNumber = lineMinBeforeLongReportGenerationMessageNumber;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Account getForecastedInvCustAccount() {
		return forecastedInvCustAccount;
	}

	public void setForecastedInvCustAccount(Account forecastedInvCustAccount) {
		this.forecastedInvCustAccount = forecastedInvCustAccount;
	}

	public Account getForecastedInvSuppAccount() {
		return forecastedInvSuppAccount;
	}

	public void setForecastedInvSuppAccount(Account forecastedInvSuppAccount) {
		this.forecastedInvSuppAccount = forecastedInvSuppAccount;
	}

	public String getAttrs() {
		return attrs;
	}

	public void setAttrs(String attrs) {
		this.attrs = attrs;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (this == obj) return true;
		if (!(obj instanceof AccountConfig)) return false;

		final AccountConfig other = (AccountConfig) obj;
		if (this.getId() != null || other.getId() != null) {
			return Objects.equals(this.getId(), other.getId());
		}

		return Objects.equals(getCompany(), other.getCompany())
			&& (getCompany() != null);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("id", getId())
			.add("partnerAccountGenerationModeSelect", getPartnerAccountGenerationModeSelect())
			.add("customerAccountPrefix", getCustomerAccountPrefix())
			.add("supplierAccountPrefix", getSupplierAccountPrefix())
			.add("employeeAccountPrefix", getEmployeeAccountPrefix())
			.add("thresholdDistanceFromRegulation", getThresholdDistanceFromRegulation())
			.add("autoReconcileOnInvoice", getAutoReconcileOnInvoice())
			.add("autoReconcileOnPayment", getAutoReconcileOnPayment())
			.add("sixMonthDebtPassReason", getSixMonthDebtPassReason())
			.add("threeMonthDebtPassReason", getThreeMonthDebtPassReason())
			.add("sixMonthDebtMonthNumber", getSixMonthDebtMonthNumber())
			.add("threeMonthDebtMontsNumber", getThreeMonthDebtMontsNumber())
			.omitNullValues()
			.toString();
	}
}
