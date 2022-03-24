package com.axelor.apps.account.db;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Digits;

import org.hibernate.annotations.Type;

import com.axelor.apps.base.db.Company;
import com.axelor.auth.db.AuditableModel;
import com.axelor.auth.db.User;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@Cacheable
@Table(name = "ACCOUNT_BUDGET", indexes = { @Index(columnList = "name"), @Index(columnList = "code"), @Index(columnList = "company"), @Index(columnList = "in_charge_user") })
public class Budget extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCOUNT_BUDGET_SEQ")
	@SequenceGenerator(name = "ACCOUNT_BUDGET_SEQ", sequenceName = "ACCOUNT_BUDGET_SEQ", allocationSize = 1)
	private Long id;

	@Widget(selection = "account.budget.status.select")
	private Integer statusSelect = 1;

	@Widget(title = "Name")
	private String name;

	@Widget(title = "Code")
	private String code;

	@Widget(title = "From")
	private LocalDate fromDate;

	@Widget(title = "To")
	private LocalDate toDate;

	@Widget(title = "Company")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Company company;

	@Widget(title = "Person in charge")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private User inChargeUser;

	@Widget(title = "Expected total amount")
	@Digits(integer = 18, fraction = 2)
	private BigDecimal totalAmountExpected = BigDecimal.ZERO;

	@Widget(title = "Realized total amount")
	@Digits(integer = 18, fraction = 2)
	private BigDecimal totalAmountRealized = BigDecimal.ZERO;

	@Widget(title = "Budget Lines")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "budget", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<BudgetLine> budgetLineList;

	@Widget(title = "Period duration", selection = "account.year.period.duration.select")
	private Integer periodDurationSelect = 0;

	@Widget(title = "Amount for each line")
	private BigDecimal amountForGeneration = BigDecimal.ZERO;

	@Widget(title = "Check available budget")
	private Boolean checkAvailableBudget = Boolean.FALSE;

	@Widget(title = "Committed total amount")
	@Digits(integer = 18, fraction = 2)
	private BigDecimal totalAmountCommitted = BigDecimal.ZERO;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public Budget() {
	}

	public Budget(String name, String code) {
		this.name = name;
		this.code = code;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Integer getStatusSelect() {
		return statusSelect == null ? 0 : statusSelect;
	}

	public void setStatusSelect(Integer statusSelect) {
		this.statusSelect = statusSelect;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public User getInChargeUser() {
		return inChargeUser;
	}

	public void setInChargeUser(User inChargeUser) {
		this.inChargeUser = inChargeUser;
	}

	public BigDecimal getTotalAmountExpected() {
		return totalAmountExpected == null ? BigDecimal.ZERO : totalAmountExpected;
	}

	public void setTotalAmountExpected(BigDecimal totalAmountExpected) {
		this.totalAmountExpected = totalAmountExpected;
	}

	public BigDecimal getTotalAmountRealized() {
		return totalAmountRealized == null ? BigDecimal.ZERO : totalAmountRealized;
	}

	public void setTotalAmountRealized(BigDecimal totalAmountRealized) {
		this.totalAmountRealized = totalAmountRealized;
	}

	public List<BudgetLine> getBudgetLineList() {
		return budgetLineList;
	}

	public void setBudgetLineList(List<BudgetLine> budgetLineList) {
		this.budgetLineList = budgetLineList;
	}

	/**
	 * Add the given {@link BudgetLine} item to the {@code budgetLineList}.
	 *
	 * <p>
	 * It sets {@code item.budget = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addBudgetLineListItem(BudgetLine item) {
		if (getBudgetLineList() == null) {
			setBudgetLineList(new ArrayList<>());
		}
		getBudgetLineList().add(item);
		item.setBudget(this);
	}

	/**
	 * Remove the given {@link BudgetLine} item from the {@code budgetLineList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeBudgetLineListItem(BudgetLine item) {
		if (getBudgetLineList() == null) {
			return;
		}
		getBudgetLineList().remove(item);
	}

	/**
	 * Clear the {@code budgetLineList} collection.
	 *
	 * <p>
	 * If you have to query {@link BudgetLine} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearBudgetLineList() {
		if (getBudgetLineList() != null) {
			getBudgetLineList().clear();
		}
	}

	public Integer getPeriodDurationSelect() {
		return periodDurationSelect == null ? 0 : periodDurationSelect;
	}

	public void setPeriodDurationSelect(Integer periodDurationSelect) {
		this.periodDurationSelect = periodDurationSelect;
	}

	public BigDecimal getAmountForGeneration() {
		return amountForGeneration == null ? BigDecimal.ZERO : amountForGeneration;
	}

	public void setAmountForGeneration(BigDecimal amountForGeneration) {
		this.amountForGeneration = amountForGeneration;
	}

	public Boolean getCheckAvailableBudget() {
		return checkAvailableBudget == null ? Boolean.FALSE : checkAvailableBudget;
	}

	public void setCheckAvailableBudget(Boolean checkAvailableBudget) {
		this.checkAvailableBudget = checkAvailableBudget;
	}

	public BigDecimal getTotalAmountCommitted() {
		return totalAmountCommitted == null ? BigDecimal.ZERO : totalAmountCommitted;
	}

	public void setTotalAmountCommitted(BigDecimal totalAmountCommitted) {
		this.totalAmountCommitted = totalAmountCommitted;
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
		if (!(obj instanceof Budget)) return false;

		final Budget other = (Budget) obj;
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
			.add("statusSelect", getStatusSelect())
			.add("name", getName())
			.add("code", getCode())
			.add("fromDate", getFromDate())
			.add("toDate", getToDate())
			.add("totalAmountExpected", getTotalAmountExpected())
			.add("totalAmountRealized", getTotalAmountRealized())
			.add("periodDurationSelect", getPeriodDurationSelect())
			.add("amountForGeneration", getAmountForGeneration())
			.add("checkAvailableBudget", getCheckAvailableBudget())
			.add("totalAmountCommitted", getTotalAmountCommitted())
			.omitNullValues()
			.toString();
	}
}
