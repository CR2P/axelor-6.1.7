package com.axelor.apps.base.db;

import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.NameColumn;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@Table(name = "BASE_CANCEL_REASON", indexes = { @Index(columnList = "name") })
public class CancelReason extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BASE_CANCEL_REASON_SEQ")
	@SequenceGenerator(name = "BASE_CANCEL_REASON_SEQ", sequenceName = "BASE_CANCEL_REASON_SEQ", allocationSize = 1)
	private Long id;

	@NameColumn
	@NotNull
	private String name;

	@Widget(title = "Free Text")
	private Boolean freeText = Boolean.FALSE;

	@Widget(selection = "base.cancel.reason.application.type.select")
	private String applicationType;

	@Widget(title = "Cancel stock allocation ?")
	private Boolean cancelQuantityAllocation = Boolean.FALSE;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public CancelReason() {
	}

	public CancelReason(String name) {
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getFreeText() {
		return freeText == null ? Boolean.FALSE : freeText;
	}

	public void setFreeText(Boolean freeText) {
		this.freeText = freeText;
	}

	public String getApplicationType() {
		return applicationType;
	}

	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}

	public Boolean getCancelQuantityAllocation() {
		return cancelQuantityAllocation == null ? Boolean.FALSE : cancelQuantityAllocation;
	}

	public void setCancelQuantityAllocation(Boolean cancelQuantityAllocation) {
		this.cancelQuantityAllocation = cancelQuantityAllocation;
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
		if (!(obj instanceof CancelReason)) return false;

		final CancelReason other = (CancelReason) obj;
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
			.add("freeText", getFreeText())
			.add("applicationType", getApplicationType())
			.add("cancelQuantityAllocation", getCancelQuantityAllocation())
			.omitNullValues()
			.toString();
	}
}
