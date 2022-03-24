package com.axelor.apps.base.db;

import java.util.ArrayList;
import java.util.List;
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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import com.axelor.apps.office.db.OfficeAccount;
import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.EqualsInclude;
import com.axelor.db.annotations.NameColumn;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@Table(name = "BASE_BASE_BATCH", indexes = { @Index(columnList = "company") })
public class BaseBatch extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BASE_BASE_BATCH_SEQ")
	@SequenceGenerator(name = "BASE_BASE_BATCH_SEQ", sequenceName = "BASE_BASE_BATCH_SEQ", allocationSize = 1)
	private Long id;

	@EqualsInclude
	@Widget(title = "Code")
	@NameColumn
	@Column(unique = true)
	private String code;

	@Widget(title = "Action", selection = "ibase.batch.action.select")
	@NotNull
	private Integer actionSelect = 0;

	@Widget(title = "Company")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Company company;

	@Widget(title = "Description")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String description;

	@Widget(title = "Batchs")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "baseBatch", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Batch> batchList;

	@Widget(title = "Synchronization duration (week)")
	@Min(0)
	private Integer synchronizationDuration = 0;

	@Widget(title = "All events")
	private Boolean allEvents = Boolean.FALSE;

	@Widget(title = "Calendars")
	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private List<ICalendar> calendarList;

	@Widget(title = "Office accounts")
	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private List<OfficeAccount> officeAccountList;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public BaseBatch() {
	}

	public BaseBatch(String code) {
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getActionSelect() {
		return actionSelect == null ? 0 : actionSelect;
	}

	public void setActionSelect(Integer actionSelect) {
		this.actionSelect = actionSelect;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Batch> getBatchList() {
		return batchList;
	}

	public void setBatchList(List<Batch> batchList) {
		this.batchList = batchList;
	}

	/**
	 * Add the given {@link Batch} item to the {@code batchList}.
	 *
	 * <p>
	 * It sets {@code item.baseBatch = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addBatchListItem(Batch item) {
		if (getBatchList() == null) {
			setBatchList(new ArrayList<>());
		}
		getBatchList().add(item);
		item.setBaseBatch(this);
	}

	/**
	 * Remove the given {@link Batch} item from the {@code batchList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeBatchListItem(Batch item) {
		if (getBatchList() == null) {
			return;
		}
		getBatchList().remove(item);
	}

	/**
	 * Clear the {@code batchList} collection.
	 *
	 * <p>
	 * If you have to query {@link Batch} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearBatchList() {
		if (getBatchList() != null) {
			getBatchList().clear();
		}
	}

	public Integer getSynchronizationDuration() {
		return synchronizationDuration == null ? 0 : synchronizationDuration;
	}

	public void setSynchronizationDuration(Integer synchronizationDuration) {
		this.synchronizationDuration = synchronizationDuration;
	}

	public Boolean getAllEvents() {
		return allEvents == null ? Boolean.FALSE : allEvents;
	}

	public void setAllEvents(Boolean allEvents) {
		this.allEvents = allEvents;
	}

	public List<ICalendar> getCalendarList() {
		return calendarList;
	}

	public void setCalendarList(List<ICalendar> calendarList) {
		this.calendarList = calendarList;
	}

	/**
	 * Add the given {@link ICalendar} item to the {@code calendarList}.
	 *
	 * @param item
	 *            the item to add
	 */
	public void addCalendarListItem(ICalendar item) {
		if (getCalendarList() == null) {
			setCalendarList(new ArrayList<>());
		}
		getCalendarList().add(item);
	}

	/**
	 * Remove the given {@link ICalendar} item from the {@code calendarList}.
	 *
	 * <p>
	 * It sets {@code item.null = null} to break the relationship.
	 * </p>
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeCalendarListItem(ICalendar item) {
		if (getCalendarList() == null) {
			return;
		}
		getCalendarList().remove(item);
	}

	/**
	 * Clear the {@code calendarList} collection.
	 *
	 * <p>
	 * It sets {@code item.null = null} to break the relationship.
	 * </p>
	 */
	public void clearCalendarList() {
		if (getCalendarList() != null) {
			getCalendarList().clear();
		}
	}

	public List<OfficeAccount> getOfficeAccountList() {
		return officeAccountList;
	}

	public void setOfficeAccountList(List<OfficeAccount> officeAccountList) {
		this.officeAccountList = officeAccountList;
	}

	/**
	 * Add the given {@link OfficeAccount} item to the {@code officeAccountList}.
	 *
	 * @param item
	 *            the item to add
	 */
	public void addOfficeAccountListItem(OfficeAccount item) {
		if (getOfficeAccountList() == null) {
			setOfficeAccountList(new ArrayList<>());
		}
		getOfficeAccountList().add(item);
	}

	/**
	 * Remove the given {@link OfficeAccount} item from the {@code officeAccountList}.
	 *
	 * <p>
	 * It sets {@code item.null = null} to break the relationship.
	 * </p>
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeOfficeAccountListItem(OfficeAccount item) {
		if (getOfficeAccountList() == null) {
			return;
		}
		getOfficeAccountList().remove(item);
	}

	/**
	 * Clear the {@code officeAccountList} collection.
	 *
	 * <p>
	 * It sets {@code item.null = null} to break the relationship.
	 * </p>
	 */
	public void clearOfficeAccountList() {
		if (getOfficeAccountList() != null) {
			getOfficeAccountList().clear();
		}
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
		if (!(obj instanceof BaseBatch)) return false;

		final BaseBatch other = (BaseBatch) obj;
		if (this.getId() != null || other.getId() != null) {
			return Objects.equals(this.getId(), other.getId());
		}

		return Objects.equals(getCode(), other.getCode())
			&& (getCode() != null);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("id", getId())
			.add("code", getCode())
			.add("actionSelect", getActionSelect())
			.add("synchronizationDuration", getSynchronizationDuration())
			.add("allEvents", getAllEvents())
			.omitNullValues()
			.toString();
	}
}
