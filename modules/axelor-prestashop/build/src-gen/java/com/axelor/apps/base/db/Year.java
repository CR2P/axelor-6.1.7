package com.axelor.apps.base.db;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@Cacheable
@Table(name = "BASE_YEAR", uniqueConstraints = { @UniqueConstraint(columnNames = { "code", "company", "typeSelect" }) }, indexes = { @Index(columnList = "name"), @Index(columnList = "code"), @Index(columnList = "company") })
public class Year extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BASE_YEAR_SEQ")
	@SequenceGenerator(name = "BASE_YEAR_SEQ", sequenceName = "BASE_YEAR_SEQ", allocationSize = 1)
	private Long id;

	@Widget(title = "Name")
	@NotNull
	private String name;

	@Widget(title = "Code")
	@NotNull
	private String code;

	@Widget(title = "From")
	@NotNull
	private LocalDate fromDate;

	@Widget(title = "To")
	@NotNull
	private LocalDate toDate;

	@Widget(title = "Periods list")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "year", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Period> periodList;

	@Widget(title = "Company")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Company company;

	@Widget(title = "Status", readonly = true, selection = "base.year.status.select")
	private Integer statusSelect = 1;

	@Widget(title = "Period duration", selection = "base.year.period.duration.select")
	private Integer periodDurationSelect = 0;

	@Widget(title = "Type", selection = "account.year.type.select")
	@NotNull
	private Integer typeSelect = 0;

	@Widget(title = "Adjust History")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fiscalYear", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<AdjustHistory> adjustHistoryList;

	@Widget(title = "Reported balance Date")
	private LocalDate reportedBalanceDate;

	@Widget(title = "Closure date")
	private LocalDateTime closureDateTime;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public Year() {
	}

	public Year(String name, String code) {
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

	public List<Period> getPeriodList() {
		return periodList;
	}

	public void setPeriodList(List<Period> periodList) {
		this.periodList = periodList;
	}

	/**
	 * Add the given {@link Period} item to the {@code periodList}.
	 *
	 * <p>
	 * It sets {@code item.year = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addPeriodListItem(Period item) {
		if (getPeriodList() == null) {
			setPeriodList(new ArrayList<>());
		}
		getPeriodList().add(item);
		item.setYear(this);
	}

	/**
	 * Remove the given {@link Period} item from the {@code periodList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removePeriodListItem(Period item) {
		if (getPeriodList() == null) {
			return;
		}
		getPeriodList().remove(item);
	}

	/**
	 * Clear the {@code periodList} collection.
	 *
	 * <p>
	 * If you have to query {@link Period} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearPeriodList() {
		if (getPeriodList() != null) {
			getPeriodList().clear();
		}
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Integer getStatusSelect() {
		return statusSelect == null ? 0 : statusSelect;
	}

	public void setStatusSelect(Integer statusSelect) {
		this.statusSelect = statusSelect;
	}

	public Integer getPeriodDurationSelect() {
		return periodDurationSelect == null ? 0 : periodDurationSelect;
	}

	public void setPeriodDurationSelect(Integer periodDurationSelect) {
		this.periodDurationSelect = periodDurationSelect;
	}

	public Integer getTypeSelect() {
		return typeSelect == null ? 0 : typeSelect;
	}

	public void setTypeSelect(Integer typeSelect) {
		this.typeSelect = typeSelect;
	}

	public List<AdjustHistory> getAdjustHistoryList() {
		return adjustHistoryList;
	}

	public void setAdjustHistoryList(List<AdjustHistory> adjustHistoryList) {
		this.adjustHistoryList = adjustHistoryList;
	}

	/**
	 * Add the given {@link AdjustHistory} item to the {@code adjustHistoryList}.
	 *
	 * <p>
	 * It sets {@code item.fiscalYear = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addAdjustHistoryListItem(AdjustHistory item) {
		if (getAdjustHistoryList() == null) {
			setAdjustHistoryList(new ArrayList<>());
		}
		getAdjustHistoryList().add(item);
		item.setFiscalYear(this);
	}

	/**
	 * Remove the given {@link AdjustHistory} item from the {@code adjustHistoryList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeAdjustHistoryListItem(AdjustHistory item) {
		if (getAdjustHistoryList() == null) {
			return;
		}
		getAdjustHistoryList().remove(item);
	}

	/**
	 * Clear the {@code adjustHistoryList} collection.
	 *
	 * <p>
	 * If you have to query {@link AdjustHistory} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearAdjustHistoryList() {
		if (getAdjustHistoryList() != null) {
			getAdjustHistoryList().clear();
		}
	}

	public LocalDate getReportedBalanceDate() {
		return reportedBalanceDate;
	}

	public void setReportedBalanceDate(LocalDate reportedBalanceDate) {
		this.reportedBalanceDate = reportedBalanceDate;
	}

	public LocalDateTime getClosureDateTime() {
		return closureDateTime;
	}

	public void setClosureDateTime(LocalDateTime closureDateTime) {
		this.closureDateTime = closureDateTime;
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
		if (!(obj instanceof Year)) return false;

		final Year other = (Year) obj;
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
			.add("name", getName())
			.add("code", getCode())
			.add("fromDate", getFromDate())
			.add("toDate", getToDate())
			.add("statusSelect", getStatusSelect())
			.add("periodDurationSelect", getPeriodDurationSelect())
			.add("typeSelect", getTypeSelect())
			.add("reportedBalanceDate", getReportedBalanceDate())
			.add("closureDateTime", getClosureDateTime())
			.omitNullValues()
			.toString();
	}
}
