package com.axelor.apps.account.db;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Cacheable;
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
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import com.axelor.apps.purchase.db.PurchaseOrderLine;
import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@Cacheable
@Table(name = "ACCOUNT_BUDGET_DISTRIBUTION", indexes = { @Index(columnList = "invoice_line"), @Index(columnList = "budget"), @Index(columnList = "purchase_order_line") })
public class BudgetDistribution extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCOUNT_BUDGET_DISTRIBUTION_SEQ")
	@SequenceGenerator(name = "ACCOUNT_BUDGET_DISTRIBUTION_SEQ", sequenceName = "ACCOUNT_BUDGET_DISTRIBUTION_SEQ", allocationSize = 1)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private InvoiceLine invoiceLine;

	@Widget(title = "Budget")
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Budget budget;

	@Widget(title = "Amount")
	@Digits(integer = 18, fraction = 2)
	private BigDecimal amount = BigDecimal.ZERO;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private PurchaseOrderLine purchaseOrderLine;

	private BigDecimal budgetAmountAvailable = BigDecimal.ZERO;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public BudgetDistribution() {
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public InvoiceLine getInvoiceLine() {
		return invoiceLine;
	}

	public void setInvoiceLine(InvoiceLine invoiceLine) {
		this.invoiceLine = invoiceLine;
	}

	public Budget getBudget() {
		return budget;
	}

	public void setBudget(Budget budget) {
		this.budget = budget;
	}

	public BigDecimal getAmount() {
		return amount == null ? BigDecimal.ZERO : amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public PurchaseOrderLine getPurchaseOrderLine() {
		return purchaseOrderLine;
	}

	public void setPurchaseOrderLine(PurchaseOrderLine purchaseOrderLine) {
		this.purchaseOrderLine = purchaseOrderLine;
	}

	public BigDecimal getBudgetAmountAvailable() {
		return budgetAmountAvailable == null ? BigDecimal.ZERO : budgetAmountAvailable;
	}

	public void setBudgetAmountAvailable(BigDecimal budgetAmountAvailable) {
		this.budgetAmountAvailable = budgetAmountAvailable;
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
		if (!(obj instanceof BudgetDistribution)) return false;

		final BudgetDistribution other = (BudgetDistribution) obj;
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
			.add("amount", getAmount())
			.add("budgetAmountAvailable", getBudgetAmountAvailable())
			.omitNullValues()
			.toString();
	}
}
