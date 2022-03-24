package com.axelor.meta.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.EqualsInclude;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

/**
 * This object stores the scheduler configuration.
 */
@Entity
@Table(name = "META_SCHEDULE")
public class MetaSchedule extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "META_SCHEDULE_SEQ")
	@SequenceGenerator(name = "META_SCHEDULE_SEQ", sequenceName = "META_SCHEDULE_SEQ", allocationSize = 1)
	private Long id;

	private Boolean active = Boolean.FALSE;

	@EqualsInclude
	@NotNull
	@Column(unique = true)
	private String name;

	private String description;

	@NotNull
	private String job;

	@NotNull
	private String cron;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<MetaScheduleParam> params;

	@Widget(title = "MetaSchedule.service", selection = "meta.schedule.batch.service.select")
	private String batchServiceSelect;

	private String batchCode;

	public MetaSchedule() {
	}

	public MetaSchedule(String name) {
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

	public Boolean getActive() {
		return active == null ? Boolean.FALSE : active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getCron() {
		return cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}

	public List<MetaScheduleParam> getParams() {
		return params;
	}

	public void setParams(List<MetaScheduleParam> params) {
		this.params = params;
	}

	/**
	 * Add the given {@link MetaScheduleParam} item to the {@code params}.
	 *
	 * <p>
	 * It sets {@code item.schedule = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addParam(MetaScheduleParam item) {
		if (getParams() == null) {
			setParams(new ArrayList<>());
		}
		getParams().add(item);
		item.setSchedule(this);
	}

	/**
	 * Remove the given {@link MetaScheduleParam} item from the {@code params}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeParam(MetaScheduleParam item) {
		if (getParams() == null) {
			return;
		}
		getParams().remove(item);
	}

	/**
	 * Clear the {@code params} collection.
	 *
	 * <p>
	 * If you have to query {@link MetaScheduleParam} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearParams() {
		if (getParams() != null) {
			getParams().clear();
		}
	}

	public String getBatchServiceSelect() {
		return batchServiceSelect;
	}

	public void setBatchServiceSelect(String batchServiceSelect) {
		this.batchServiceSelect = batchServiceSelect;
	}

	public String getBatchCode() {
		return batchCode;
	}

	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (this == obj) return true;
		if (!(obj instanceof MetaSchedule)) return false;

		final MetaSchedule other = (MetaSchedule) obj;
		if (this.getId() != null || other.getId() != null) {
			return Objects.equals(this.getId(), other.getId());
		}

		return Objects.equals(getName(), other.getName())
			&& (getName() != null);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("id", getId())
			.add("active", getActive())
			.add("name", getName())
			.add("description", getDescription())
			.add("job", getJob())
			.add("cron", getCron())
			.add("batchServiceSelect", getBatchServiceSelect())
			.add("batchCode", getBatchCode())
			.omitNullValues()
			.toString();
	}
}
