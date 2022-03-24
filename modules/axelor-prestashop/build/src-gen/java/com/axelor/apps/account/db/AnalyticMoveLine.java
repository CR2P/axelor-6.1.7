package com.axelor.apps.account.db;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
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

import com.axelor.apps.base.db.Currency;
import com.axelor.apps.purchase.db.PurchaseOrderLine;
import com.axelor.apps.sale.db.SaleOrderLine;
import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.Track;
import com.axelor.db.annotations.TrackEvent;
import com.axelor.db.annotations.TrackField;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@Table(name = "ACCOUNT_ANALYTIC_MOVE_LINE", indexes = { @Index(name = "idx_acc_ana_move_line_ana_dist_template", columnList = "analytic_distribution_template"), @Index(columnList = "invoice_line"), @Index(columnList = "move_line"), @Index(columnList = "analytic_axis"), @Index(columnList = "analytic_account"), @Index(columnList = "analytic_journal"), @Index(columnList = "account"), @Index(columnList = "account_type"), @Index(columnList = "currency"), @Index(columnList = "sale_order_line"), @Index(columnList = "purchase_order_line") })
@Track(fields = { @TrackField(name = "date", on = TrackEvent.UPDATE), @TrackField(name = "analyticAccount", on = TrackEvent.UPDATE), @TrackField(name = "analyticJournal", on = TrackEvent.UPDATE), @TrackField(name = "invoiceLine", on = TrackEvent.UPDATE), @TrackField(name = "moveLine", on = TrackEvent.UPDATE), @TrackField(name = "saleOrderLine", on = TrackEvent.UPDATE) })
public class AnalyticMoveLine extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCOUNT_ANALYTIC_MOVE_LINE_SEQ")
	@SequenceGenerator(name = "ACCOUNT_ANALYTIC_MOVE_LINE_SEQ", sequenceName = "ACCOUNT_ANALYTIC_MOVE_LINE_SEQ", allocationSize = 1)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private AnalyticDistributionTemplate analyticDistributionTemplate;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private InvoiceLine invoiceLine;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private MoveLine moveLine;

	@Widget(title = "Axis")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private AnalyticAxis analyticAxis;

	@Widget(title = "Analytic Acc.")
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private AnalyticAccount analyticAccount;

	@Widget(title = "%")
	private BigDecimal percentage = BigDecimal.ZERO;

	@Widget(title = "Original piece amount", hidden = true)
	private BigDecimal originalPieceAmount = BigDecimal.ZERO;

	@Widget(title = "Amount")
	private BigDecimal amount = BigDecimal.ZERO;

	@Widget(title = "Date")
	@NotNull
	@Column(name = "date_val")
	private LocalDate date;

	@Widget(title = "Analytic Journal")
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private AnalyticJournal analyticJournal;

	@Widget(title = "Accounting.Account")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Account account;

	@Widget(title = "Account Type")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private AccountType accountType;

	@Widget(title = "Type", selection = "account.analytic.move.line.type.select")
	private Integer typeSelect = 1;

	@Widget(title = "Currency")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Currency currency;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private SaleOrderLine saleOrderLine;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private PurchaseOrderLine purchaseOrderLine;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public AnalyticMoveLine() {
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public AnalyticDistributionTemplate getAnalyticDistributionTemplate() {
		return analyticDistributionTemplate;
	}

	public void setAnalyticDistributionTemplate(AnalyticDistributionTemplate analyticDistributionTemplate) {
		this.analyticDistributionTemplate = analyticDistributionTemplate;
	}

	public InvoiceLine getInvoiceLine() {
		return invoiceLine;
	}

	public void setInvoiceLine(InvoiceLine invoiceLine) {
		this.invoiceLine = invoiceLine;
	}

	public MoveLine getMoveLine() {
		return moveLine;
	}

	public void setMoveLine(MoveLine moveLine) {
		this.moveLine = moveLine;
	}

	public AnalyticAxis getAnalyticAxis() {
		return analyticAxis;
	}

	public void setAnalyticAxis(AnalyticAxis analyticAxis) {
		this.analyticAxis = analyticAxis;
	}

	public AnalyticAccount getAnalyticAccount() {
		return analyticAccount;
	}

	public void setAnalyticAccount(AnalyticAccount analyticAccount) {
		this.analyticAccount = analyticAccount;
	}

	public BigDecimal getPercentage() {
		return percentage == null ? BigDecimal.ZERO : percentage;
	}

	public void setPercentage(BigDecimal percentage) {
		this.percentage = percentage;
	}

	public BigDecimal getOriginalPieceAmount() {
		return originalPieceAmount == null ? BigDecimal.ZERO : originalPieceAmount;
	}

	public void setOriginalPieceAmount(BigDecimal originalPieceAmount) {
		this.originalPieceAmount = originalPieceAmount;
	}

	public BigDecimal getAmount() {
		return amount == null ? BigDecimal.ZERO : amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public AnalyticJournal getAnalyticJournal() {
		return analyticJournal;
	}

	public void setAnalyticJournal(AnalyticJournal analyticJournal) {
		this.analyticJournal = analyticJournal;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public Integer getTypeSelect() {
		return typeSelect == null ? 0 : typeSelect;
	}

	public void setTypeSelect(Integer typeSelect) {
		this.typeSelect = typeSelect;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public SaleOrderLine getSaleOrderLine() {
		return saleOrderLine;
	}

	public void setSaleOrderLine(SaleOrderLine saleOrderLine) {
		this.saleOrderLine = saleOrderLine;
	}

	public PurchaseOrderLine getPurchaseOrderLine() {
		return purchaseOrderLine;
	}

	public void setPurchaseOrderLine(PurchaseOrderLine purchaseOrderLine) {
		this.purchaseOrderLine = purchaseOrderLine;
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
		if (!(obj instanceof AnalyticMoveLine)) return false;

		final AnalyticMoveLine other = (AnalyticMoveLine) obj;
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
			.add("percentage", getPercentage())
			.add("originalPieceAmount", getOriginalPieceAmount())
			.add("amount", getAmount())
			.add("date", getDate())
			.add("typeSelect", getTypeSelect())
			.omitNullValues()
			.toString();
	}
}
