package com.axelor.apps.account.db;

import java.math.BigDecimal;
import java.time.LocalDate;
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

import org.hibernate.annotations.Type;

import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@Cacheable
@Table(name = "ACCOUNT_BUDGET_LINE", indexes = { @Index(columnList = "budget") })
public class BudgetLine extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCOUNT_BUDGET_LINE_SEQ")
	@SequenceGenerator(name = "ACCOUNT_BUDGET_LINE_SEQ", sequenceName = "ACCOUNT_BUDGET_LINE_SEQ", allocationSize = 1)
	private Long id;

	@Widget(title = "From")
	private LocalDate fromDate;

	@Widget(title = "To")
	private LocalDate toDate;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Budget budget;

	@Widget(title = "Expected amount")
	@Digits(integer = 18, fraction = 2)
	private BigDecimal amountExpected = BigDecimal.ZERO;

	@Widget(title = "Realized amount")
	@Digits(integer = 18, fraction = 2)
	private BigDecimal amountRealized = BigDecimal.ZERO;

	@Widget(title = "Committed amount")
	@Digits(integer = 18, fraction = 2)
	private BigDecimal amountCommitted = BigDecimal.ZERO;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public BudgetLine() {
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getFromDate() {
		return fromDate;
	}

	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	public LocalDate getToDate() {
		return toDate;
	}

	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}

	public Budget getBudget() {
		return budget;
	}

	public void setBudget(Budget budget) {
		this.budget = budget;
	}

	public BigDecimal getAmountExpected() {
		return amountExpected == null ? BigDecimal.ZERO : amountExpected;
	}

	public void setAmountExpected(BigDecimal amountExpected) {
		this.amountExpected = amountExpected;
	}

	public BigDecimal getAmountRealized() {
		return amountRealized == null ? BigDecimal.ZERO : amountRealized;
	}

	public void setAmountRealized(BigDecimal amountRealized) {
		this.amountRealized = amountRealized;
	}

	public BigDecimal getAmountCommitted() {
		return amountCommitted == null ? BigDecimal.ZERO : amountCommitted;
	}

	public void setAmountCommitted(BigDecimal amountCommitted) {
		this.amountCommitted = amountCommitted;
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
		if (!(obj instanceof BudgetLine)) return false;

		final BudgetLine other = (BudgetLine) obj;
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
			.add("fromDate", getFromDate())
			.add("toDate", getToDate())
			.add("amountExpected", getAmountExpected())
			.add("amountRealized", getAmountRealized())
			.add("amountCommitted", getAmountCommitted())
			.omitNullValues()
			.toString();
	}
}
