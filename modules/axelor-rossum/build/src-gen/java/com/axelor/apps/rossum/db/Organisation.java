package com.axelor.apps.rossum.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.NameColumn;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@Table(name = "ROSSUM_ORGANISATION", indexes = { @Index(columnList = "organisationName") })
public class Organisation extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROSSUM_ORGANISATION_SEQ")
	@SequenceGenerator(name = "ROSSUM_ORGANISATION_SEQ", sequenceName = "ROSSUM_ORGANISATION_SEQ", allocationSize = 1)
	private Long id;

	@Widget(title = "Organisation id", readonly = true)
	private Integer organisationId = 0;

	@Widget(title = "Organisation name")
	@NameColumn
	private String organisationName;

	@Widget(title = "Organisation url", readonly = true)
	private String organisationUrl;

	@Widget(title = "Organisation result")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String organisationResult;

	@Widget(title = "Workspaces", readonly = true)
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "organisationUrl", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Workspace> workspaceList;

	public Organisation() {
	}

	public Organisation(Integer organisationId, String organisationName, String organisationUrl) {
		this.organisationId = organisationId;
		this.organisationName = organisationName;
		this.organisationUrl = organisationUrl;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Integer getOrganisationId() {
		return organisationId == null ? 0 : organisationId;
	}

	public void setOrganisationId(Integer organisationId) {
		this.organisationId = organisationId;
	}

	public String getOrganisationName() {
		return organisationName;
	}

	public void setOrganisationName(String organisationName) {
		this.organisationName = organisationName;
	}

	public String getOrganisationUrl() {
		return organisationUrl;
	}

	public void setOrganisationUrl(String organisationUrl) {
		this.organisationUrl = organisationUrl;
	}

	public String getOrganisationResult() {
		return organisationResult;
	}

	public void setOrganisationResult(String organisationResult) {
		this.organisationResult = organisationResult;
	}

	public List<Workspace> getWorkspaceList() {
		return workspaceList;
	}

	public void setWorkspaceList(List<Workspace> workspaceList) {
		this.workspaceList = workspaceList;
	}

	/**
	 * Add the given {@link Workspace} item to the {@code workspaceList}.
	 *
	 * <p>
	 * It sets {@code item.organisationUrl = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addWorkspaceListItem(Workspace item) {
		if (getWorkspaceList() == null) {
			setWorkspaceList(new ArrayList<>());
		}
		getWorkspaceList().add(item);
		item.setOrganisationUrl(this);
	}

	/**
	 * Remove the given {@link Workspace} item from the {@code workspaceList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeWorkspaceListItem(Workspace item) {
		if (getWorkspaceList() == null) {
			return;
		}
		getWorkspaceList().remove(item);
	}

	/**
	 * Clear the {@code workspaceList} collection.
	 *
	 * <p>
	 * If you have to query {@link Workspace} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearWorkspaceList() {
		if (getWorkspaceList() != null) {
			getWorkspaceList().clear();
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (this == obj) return true;
		if (!(obj instanceof Organisation)) return false;

		final Organisation other = (Organisation) obj;
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
			.add("organisationId", getOrganisationId())
			.add("organisationName", getOrganisationName())
			.add("organisationUrl", getOrganisationUrl())
			.omitNullValues()
			.toString();
	}
}
