package com.axelor.apps.account.db;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Digits;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.axelor.apps.base.db.BankDetails;
import com.axelor.apps.base.db.Batch;
import com.axelor.apps.base.db.Company;
import com.axelor.apps.base.db.Partner;
import com.axelor.apps.message.db.Template;
import com.axelor.auth.db.AuditableModel;
import com.axelor.auth.db.User;
import com.axelor.db.annotations.VirtualColumn;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "ACCOUNT_ACCOUNTING_SITUATION", indexes = { @Index(columnList = "company"), @Index(columnList = "partner"), @Index(columnList = "customer_account"), @Index(columnList = "supplier_account"), @Index(columnList = "employee_account"), @Index(columnList = "company_in_bank_details"), @Index(columnList = "company_out_bank_details"), @Index(columnList = "invoice_message_template"), @Index(columnList = "invoice_message_template_on_validate"), @Index(columnList = "pfp_validator_user"), @Index(columnList = "name") })
public class AccountingSituation extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCOUNT_ACCOUNTING_SITUATION_SEQ")
	@SequenceGenerator(name = "ACCOUNT_ACCOUNTING_SITUATION_SEQ", sequenceName = "ACCOUNT_ACCOUNTING_SITUATION_SEQ", allocationSize = 1)
	private Long id;

	@Widget(title = "Company")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Company company;

	@Widget(title = "Payer partner")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Partner partner;

	@Widget(title = "Customer account")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Account customerAccount;

	@Widget(title = "Supplier account")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Account supplierAccount;

	@Widget(title = "Employee account")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Account employeeAccount;

	@Widget(title = "Total balance", readonly = true)
	private BigDecimal balanceCustAccount = BigDecimal.ZERO;

	@Widget(title = "Due balance", readonly = true)
	private BigDecimal balanceDueCustAccount = BigDecimal.ZERO;

	@Widget(title = "Due balance recoverable", readonly = true)
	private BigDecimal balanceDueDebtRecoveryCustAccount = BigDecimal.ZERO;

	@Widget(title = "Partner situation must be updated")
	private Boolean custAccountMustBeUpdateOk = Boolean.FALSE;

	@Widget(title = "Debt recovery")
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "accountingSituation", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private DebtRecovery debtRecovery;

	@Widget(title = "Debt recoveries")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tradingNameAccountingSituation", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<DebtRecovery> tradingNameDebtRecoveryList;

	@Widget(title = "Company bank for in payment mode")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private BankDetails companyInBankDetails;

	@Widget(title = "Company bank for out payment mode")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private BankDetails companyOutBankDetails;

	@Widget(title = "Batchs")
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Batch> batchSet;

	@Widget(title = "Send email on invoice ventilation")
	private Boolean invoiceAutomaticMail = Boolean.FALSE;

	@Widget(title = "Message template")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Template invoiceMessageTemplate;

	@Widget(title = "Send email on invoice validate")
	private Boolean invoiceAutomaticMailOnValidate = Boolean.FALSE;

	@Widget(title = "Message template")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Template invoiceMessageTemplateOnValidate;

	@Widget(title = "Reported balance lines")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "accountingSituation", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ReportedBalanceLine> reportedBalanceLineList;

	@Widget(title = "PFP Validator")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private User pfpValidatorUser;

	@VirtualColumn
	@Access(AccessType.PROPERTY)
	private String name;

	@Widget(title = "Accepted Credit")
	@Digits(integer = 18, fraction = 2)
	private BigDecimal acceptedCredit = BigDecimal.ZERO;

	@Widget(title = "Used Credit", readonly = true)
	@Digits(integer = 18, fraction = 2)
	private BigDecimal usedCredit = BigDecimal.ZERO;

	@Widget(title = "Insurance date credit")
	private LocalDate insurCreditDate;

	@Widget(title = "Comment")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String description;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public AccountingSituation() {
	}

	public AccountingSituation(String name) {
		this.name = name;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Partner getPartner() {
		return partner;
	}

	public void setPartner(Partner partner) {
		this.partner = partner;
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

	public Account getEmployeeAccount() {
		return employeeAccount;
	}

	public void setEmployeeAccount(Account employeeAccount) {
		this.employeeAccount = employeeAccount;
	}

	public BigDecimal getBalanceCustAccount() {
		return balanceCustAccount == null ? BigDecimal.ZERO : balanceCustAccount;
	}

	public void setBalanceCustAccount(BigDecimal balanceCustAccount) {
		this.balanceCustAccount = balanceCustAccount;
	}

	public BigDecimal getBalanceDueCustAccount() {
		return balanceDueCustAccount == null ? BigDecimal.ZERO : balanceDueCustAccount;
	}

	public void setBalanceDueCustAccount(BigDecimal balanceDueCustAccount) {
		this.balanceDueCustAccount = balanceDueCustAccount;
	}

	public BigDecimal getBalanceDueDebtRecoveryCustAccount() {
		return balanceDueDebtRecoveryCustAccount == null ? BigDecimal.ZERO : balanceDueDebtRecoveryCustAccount;
	}

	public void setBalanceDueDebtRecoveryCustAccount(BigDecimal balanceDueDebtRecoveryCustAccount) {
		this.balanceDueDebtRecoveryCustAccount = balanceDueDebtRecoveryCustAccount;
	}

	public Boolean getCustAccountMustBeUpdateOk() {
		return custAccountMustBeUpdateOk == null ? Boolean.FALSE : custAccountMustBeUpdateOk;
	}

	public void setCustAccountMustBeUpdateOk(Boolean custAccountMustBeUpdateOk) {
		this.custAccountMustBeUpdateOk = custAccountMustBeUpdateOk;
	}

	public DebtRecovery getDebtRecovery() {
		return debtRecovery;
	}

	public void setDebtRecovery(DebtRecovery debtRecovery) {
		if (getDebtRecovery() != null) {
			getDebtRecovery().setAccountingSituation(null);
		}
		if (debtRecovery != null) {
			debtRecovery.setAccountingSituation(this);
		}
		this.debtRecovery = debtRecovery;
	}

	public List<DebtRecovery> getTradingNameDebtRecoveryList() {
		return tradingNameDebtRecoveryList;
	}

	public void setTradingNameDebtRecoveryList(List<DebtRecovery> tradingNameDebtRecoveryList) {
		this.tradingNameDebtRecoveryList = tradingNameDebtRecoveryList;
	}

	/**
	 * Add the given {@link DebtRecovery} item to the {@code tradingNameDebtRecoveryList}.
	 *
	 * <p>
	 * It sets {@code item.tradingNameAccountingSituation = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addTradingNameDebtRecoveryListItem(DebtRecovery item) {
		if (getTradingNameDebtRecoveryList() == null) {
			setTradingNameDebtRecoveryList(new ArrayList<>());
		}
		getTradingNameDebtRecoveryList().add(item);
		item.setTradingNameAccountingSituation(this);
	}

	/**
	 * Remove the given {@link DebtRecovery} item from the {@code tradingNameDebtRecoveryList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeTradingNameDebtRecoveryListItem(DebtRecovery item) {
		if (getTradingNameDebtRecoveryList() == null) {
			return;
		}
		getTradingNameDebtRecoveryList().remove(item);
	}

	/**
	 * Clear the {@code tradingNameDebtRecoveryList} collection.
	 *
	 * <p>
	 * If you have to query {@link DebtRecovery} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearTradingNameDebtRecoveryList() {
		if (getTradingNameDebtRecoveryList() != null) {
			getTradingNameDebtRecoveryList().clear();
		}
	}

	public BankDetails getCompanyInBankDetails() {
		return companyInBankDetails;
	}

	public void setCompanyInBankDetails(BankDetails companyInBankDetails) {
		this.companyInBankDetails = companyInBankDetails;
	}

	public BankDetails getCompanyOutBankDetails() {
		return companyOutBankDetails;
	}

	public void setCompanyOutBankDetails(BankDetails companyOutBankDetails) {
		this.companyOutBankDetails = companyOutBankDetails;
	}

	public Set<Batch> getBatchSet() {
		return batchSet;
	}

	public void setBatchSet(Set<Batch> batchSet) {
		this.batchSet = batchSet;
	}

	/**
	 * Add the given {@link Batch} item to the {@code batchSet}.
	 *
	 * @param item
	 *            the item to add
	 */
	public void addBatchSetItem(Batch item) {
		if (getBatchSet() == null) {
			setBatchSet(new HashSet<>());
		}
		getBatchSet().add(item);
	}

	/**
	 * Remove the given {@link Batch} item from the {@code batchSet}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeBatchSetItem(Batch item) {
		if (getBatchSet() == null) {
			return;
		}
		getBatchSet().remove(item);
	}

	/**
	 * Clear the {@code batchSet} collection.
	 *
	 */
	public void clearBatchSet() {
		if (getBatchSet() != null) {
			getBatchSet().clear();
		}
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

	public Boolean getInvoiceAutomaticMailOnValidate() {
		return invoiceAutomaticMailOnValidate == null ? Boolean.FALSE : invoiceAutomaticMailOnValidate;
	}

	public void setInvoiceAutomaticMailOnValidate(Boolean invoiceAutomaticMailOnValidate) {
		this.invoiceAutomaticMailOnValidate = invoiceAutomaticMailOnValidate;
	}

	public Template getInvoiceMessageTemplateOnValidate() {
		return invoiceMessageTemplateOnValidate;
	}

	public void setInvoiceMessageTemplateOnValidate(Template invoiceMessageTemplateOnValidate) {
		this.invoiceMessageTemplateOnValidate = invoiceMessageTemplateOnValidate;
	}

	public List<ReportedBalanceLine> getReportedBalanceLineList() {
		return reportedBalanceLineList;
	}

	public void setReportedBalanceLineList(List<ReportedBalanceLine> reportedBalanceLineList) {
		this.reportedBalanceLineList = reportedBalanceLineList;
	}

	/**
	 * Add the given {@link ReportedBalanceLine} item to the {@code reportedBalanceLineList}.
	 *
	 * <p>
	 * It sets {@code item.accountingSituation = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addReportedBalanceLineListItem(ReportedBalanceLine item) {
		if (getReportedBalanceLineList() == null) {
			setReportedBalanceLineList(new ArrayList<>());
		}
		getReportedBalanceLineList().add(item);
		item.setAccountingSituation(this);
	}

	/**
	 * Remove the given {@link ReportedBalanceLine} item from the {@code reportedBalanceLineList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeReportedBalanceLineListItem(ReportedBalanceLine item) {
		if (getReportedBalanceLineList() == null) {
			return;
		}
		getReportedBalanceLineList().remove(item);
	}

	/**
	 * Clear the {@code reportedBalanceLineList} collection.
	 *
	 * <p>
	 * If you have to query {@link ReportedBalanceLine} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearReportedBalanceLineList() {
		if (getReportedBalanceLineList() != null) {
			getReportedBalanceLineList().clear();
		}
	}

	public User getPfpValidatorUser() {
		return pfpValidatorUser;
	}

	public void setPfpValidatorUser(User pfpValidatorUser) {
		this.pfpValidatorUser = pfpValidatorUser;
	}

	public String getName() {
		try {
			name = computeName();
		} catch (NullPointerException e) {
			Logger logger = LoggerFactory.getLogger(getClass());
			logger.error("NPE in function field: getName()", e);
		}
		return name;
	}

	protected String computeName() {
		String name = "";
		if (partner != null) {
		  if(partner.getFullName() != null)
		  {
		   if(partner.getPartnerSeq() != null)
			name += partner.getFullName()+" ("+partner.getPartnerSeq()+")";
		   else
			name += partner.getFullName();
		  }
		  else if(partner.getPartnerSeq() != null){
		   name += "("+partner.getPartnerSeq()+")";
		  }
		}
		if (company != null) name += (name.isEmpty()?"":"-") +company.getName();
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getAcceptedCredit() {
		return acceptedCredit == null ? BigDecimal.ZERO : acceptedCredit;
	}

	public void setAcceptedCredit(BigDecimal acceptedCredit) {
		this.acceptedCredit = acceptedCredit;
	}

	public BigDecimal getUsedCredit() {
		return usedCredit == null ? BigDecimal.ZERO : usedCredit;
	}

	public void setUsedCredit(BigDecimal usedCredit) {
		this.usedCredit = usedCredit;
	}

	public LocalDate getInsurCreditDate() {
		return insurCreditDate;
	}

	public void setInsurCreditDate(LocalDate insurCreditDate) {
		this.insurCreditDate = insurCreditDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
		if (!(obj instanceof AccountingSituation)) return false;

		final AccountingSituation other = (AccountingSituation) obj;
		if (this.getId() != null || other.getId() != null) {
			return Objects.equals(this.getId(), other.getId());
		}

		return false;
	}

	@Override
	public int hashCode() {
		return 31;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("id", getId())
			.add("balanceCustAccount", getBalanceCustAccount())
			.add("balanceDueCustAccount", getBalanceDueCustAccount())
			.add("balanceDueDebtRecoveryCustAccount", getBalanceDueDebtRecoveryCustAccount())
			.add("custAccountMustBeUpdateOk", getCustAccountMustBeUpdateOk())
			.add("invoiceAutomaticMail", getInvoiceAutomaticMail())
			.add("invoiceAutomaticMailOnValidate", getInvoiceAutomaticMailOnValidate())
			.add("acceptedCredit", getAcceptedCredit())
			.add("usedCredit", getUsedCredit())
			.add("insurCreditDate", getInsurCreditDate())
			.omitNullValues()
			.toString();
	}
}
