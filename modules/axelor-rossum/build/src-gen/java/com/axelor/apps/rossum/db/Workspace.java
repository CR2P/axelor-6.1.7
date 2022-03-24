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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.NameColumn;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@Table(name = "ROSSUM_WORKSPACE", indexes = { @Index(columnList = "workspaceName"), @Index(columnList = "organisation_url") })
public class Workspace extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROSSUM_WORKSPACE_SEQ")
	@SequenceGenerator(name = "ROSSUM_WORKSPACE_SEQ", sequenceName = "ROSSUM_WORKSPACE_SEQ", allocationSize = 1)
	private Long id;

	@Widget(title = "Workspace id", readonly = true)
	private Integer workspaceId = 0;

	@Widget(title = "Workspace name")
	@NameColumn
	private String workspaceName;

	@Widget(title = "Workspace url", readonly = true)
	private String workspaceUrl;

	@Widget(title = "Workspace result")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String workspaceResult;

	@Widget(title = "Organisation")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Organisation organisationUrl;

	@Widget(title = "Queues", readonly = true)
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "workspaceUrl", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Queue> queueList;

	public Workspace() {
	}

	public Workspace(Integer workspaceId, String workspaceName, String workspaceUrl, Organisation organisationUrl) {
		this.workspaceId = workspaceId;
		this.workspaceName = workspaceName;
		this.workspaceUrl = workspaceUrl;
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

	public Integer getWorkspaceId() {
		return workspaceId == null ? 0 : workspaceId;
	}

	public void setWorkspaceId(Integer workspaceId) {
		this.workspaceId = workspaceId;
	}

	public String getWorkspaceName() {
		return workspaceName;
	}

	public void setWorkspaceName(String workspaceName) {
		this.workspaceName = workspaceName;
	}

	public String getWorkspaceUrl() {
		return workspaceUrl;
	}

	public void setWorkspaceUrl(String workspaceUrl) {
		this.workspaceUrl = workspaceUrl;
	}

	public String getWorkspaceResult() {
		return workspaceResult;
	}

	public void setWorkspaceResult(String workspaceResult) {
		this.workspaceResult = workspaceResult;
	}

	public Organisation getOrganisationUrl() {
		return organisationUrl;
	}

	public void setOrganisationUrl(Organisation organisationUrl) {
		this.organisationUrl = organisationUrl;
	}

	public List<Queue> getQueueList() {
		return queueList;
	}

	public void setQueueList(List<Queue> queueList) {
		this.queueList = queueList;
	}

	/**
	 * Add the given {@link Queue} item to the {@code queueList}.
	 *
	 * <p>
	 * It sets {@code item.workspaceUrl = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addQueueListItem(Queue item) {
		if (getQueueList() == null) {
			setQueueList(new ArrayList<>());
		}
		getQueueList().add(item);
		item.setWorkspaceUrl(this);
	}

	/**
	 * Remove the given {@link Queue} item from the {@code queueList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeQueueListItem(Queue item) {
		if (getQueueList() == null) {
			return;
		}
		getQueueList().remove(item);
	}

	/**
	 * Clear the {@code queueList} collection.
	 *
	 * <p>
	 * If you have to query {@link Queue} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearQueueList() {
		if (getQueueList() != null) {
			getQueueList().clear();
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (this == obj) return true;
		if (!(obj instanceof Workspace)) return false;

		final Workspace other = (Workspace) obj;
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
			.add("workspaceId", getWorkspaceId())
			.add("workspaceName", getWorkspaceName())
			.add("workspaceUrl", getWorkspaceUrl())
			.omitNullValues()
			.toString();
	}
}
