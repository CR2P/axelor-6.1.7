package com.axelor.apps.account.db;

import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import com.axelor.apps.base.db.BankDetails;
import com.axelor.apps.base.db.Company;
import com.axelor.apps.base.db.Product;
import com.axelor.apps.base.db.ProductFamily;
import com.axelor.apps.base.db.Sequence;
import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@Table(name = "ACCOUNT_ACCOUNT_MANAGEMENT", indexes = { @Index(columnList = "company"), @Index(columnList = "purchase_tax"), @Index(columnList = "sale_tax"), @Index(columnList = "tax"), @Index(columnList = "product"), @Index(columnList = "product_family"), @Index(columnList = "purchase_account"), @Index(columnList = "sale_account"), @Index(columnList = "cash_account"), @Index(columnList = "purch_fixed_assets_account"), @Index(columnList = "payment_mode"), @Index(columnList = "journal"), @Index(columnList = "sequence"), @Index(columnList = "bank_details"), @Index(columnList = "analytic_distribution_template"), @Index(columnList = "fixed_asset_category") })
public class AccountManagement extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCOUNT_ACCOUNT_MANAGEMENT_SEQ")
	@SequenceGenerator(name = "ACCOUNT_ACCOUNT_MANAGEMENT_SEQ", sequenceName = "ACCOUNT_ACCOUNT_MANAGEMENT_SEQ", allocationSize = 1)
	private Long id;

	@Widget(title = "Type", selection = "account.management.type.select")
	@NotNull
	private Integer typeSelect = 0;

	@Widget(title = "Company")
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Company company;

	@Widget(title = "Purchase Tax")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Tax purchaseTax;

	@Widget(title = "Sale Tax")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Tax saleTax;

	@Widget(title = "Tax")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Tax tax;

	@Widget(title = "Product")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Product product;

	@Widget(title = "Accounting family")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private ProductFamily productFamily;

	@Widget(title = "Purchase account")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Account purchaseAccount;

	@Widget(title = "Sale account")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Account saleAccount;

	@Widget(title = "Payment account")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Account cashAccount;

	@Widget(title = "Account of purchase fixed assets")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Account purchFixedAssetsAccount;

	@Widget(title = "Payment mode")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private PaymentMode paymentMode;

	@Widget(title = "Journal")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Journal journal;

	@Widget(title = "Sequence")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Sequence sequence;

	@Widget(title = "Bank details")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private BankDetails bankDetails;

	@Widget(title = "Analytic distribution template")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private AnalyticDistributionTemplate analyticDistributionTemplate;

	@Widget(title = "Fixed asset category")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private FixedAssetCategory fixedAssetCategory;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public AccountManagement() {
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Integer getTypeSelect() {
		return typeSelect == null ? 0 : typeSelect;
	}

	public void setTypeSelect(Integer typeSelect) {
		this.typeSelect = typeSelect;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Tax getPurchaseTax() {
		return purchaseTax;
	}

	public void setPurchaseTax(Tax purchaseTax) {
		this.purchaseTax = purchaseTax;
	}

	public Tax getSaleTax() {
		return saleTax;
	}

	public void setSaleTax(Tax saleTax) {
		this.saleTax = saleTax;
	}

	public Tax getTax() {
		return tax;
	}

	public void setTax(Tax tax) {
		this.tax = tax;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public ProductFamily getProductFamily() {
		return productFamily;
	}

	public void setProductFamily(ProductFamily productFamily) {
		this.productFamily = productFamily;
	}

	public Account getPurchaseAccount() {
		return purchaseAccount;
	}

	public void setPurchaseAccount(Account purchaseAccount) {
		this.purchaseAccount = purchaseAccount;
	}

	public Account getSaleAccount() {
		return saleAccount;
	}

	public void setSaleAccount(Account saleAccount) {
		this.saleAccount = saleAccount;
	}

	public Account getCashAccount() {
		return cashAccount;
	}

	public void setCashAccount(Account cashAccount) {
		this.cashAccount = cashAccount;
	}

	public Account getPurchFixedAssetsAccount() {
		return purchFixedAssetsAccount;
	}

	public void setPurchFixedAssetsAccount(Account purchFixedAssetsAccount) {
		this.purchFixedAssetsAccount = purchFixedAssetsAccount;
	}

	public PaymentMode getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(PaymentMode paymentMode) {
		this.paymentMode = paymentMode;
	}

	public Journal getJournal() {
		return journal;
	}

	public void setJournal(Journal journal) {
		this.journal = journal;
	}

	public Sequence getSequence() {
		return sequence;
	}

	public void setSequence(Sequence sequence) {
		this.sequence = sequence;
	}

	public BankDetails getBankDetails() {
		return bankDetails;
	}

	public void setBankDetails(BankDetails bankDetails) {
		this.bankDetails = bankDetails;
	}

	public AnalyticDistributionTemplate getAnalyticDistributionTemplate() {
		return analyticDistributionTemplate;
	}

	public void setAnalyticDistributionTemplate(AnalyticDistributionTemplate analyticDistributionTemplate) {
		this.analyticDistributionTemplate = analyticDistributionTemplate;
	}

	public FixedAssetCategory getFixedAssetCategory() {
		return fixedAssetCategory;
	}

	public void setFixedAssetCategory(FixedAssetCategory fixedAssetCategory) {
		this.fixedAssetCategory = fixedAssetCategory;
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
		if (!(obj instanceof AccountManagement)) return false;

		final AccountManagement other = (AccountManagement) obj;
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
			.add("typeSelect", getTypeSelect())
			.omitNullValues()
			.toString();
	}
}
