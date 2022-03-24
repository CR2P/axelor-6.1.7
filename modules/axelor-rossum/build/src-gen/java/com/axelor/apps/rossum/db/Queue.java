package com.axelor.apps.rossum.db;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.NameColumn;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@Table(name = "ROSSUM_QUEUE", indexes = { @Index(columnList = "queueName"), @Index(columnList = "workspace_url"), @Index(columnList = "schema_url") })
public class Queue extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROSSUM_QUEUE_SEQ")
	@SequenceGenerator(name = "ROSSUM_QUEUE_SEQ", sequenceName = "ROSSUM_QUEUE_SEQ", allocationSize = 1)
	private Long id;

	@Widget(title = "Queue id", readonly = true)
	private Integer queueId = 0;

	@Widget(title = "Queue name")
	@NameColumn
	private String queueName;

	@Widget(title = "Queue url", readonly = true)
	private String queueUrl;

	@Widget(title = "Queue result")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String queueResult;

	@Widget(title = "Automation level", selection = "rossum.queue.automation.level.select")
	private String automationLevelSelect;

	@Widget(title = "Use confirmed state")
	private Boolean useConfirmedState = Boolean.FALSE;

	@Widget(title = "Workspace")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Workspace workspaceUrl;

	@Widget(title = "Schema url")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Schema schemaUrl;

	public Queue() {
	}

	public Queue(Integer queueId, String queueName, String queueUrl, Workspace workspaceUrl, Schema schemaUrl) {
		this.queueId = queueId;
		this.queueName = queueName;
		this.queueUrl = queueUrl;
		this.workspaceUrl = workspaceUrl;
		this.schemaUrl = schemaUrl;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Integer getQueueId() {
		return queueId == null ? 0 : queueId;
	}

	public void setQueueId(Integer queueId) {
		this.queueId = queueId;
	}

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public String getQueueUrl() {
		return queueUrl;
	}

	public void setQueueUrl(String queueUrl) {
		this.queueUrl = queueUrl;
	}

	public String getQueueResult() {
		return queueResult;
	}

	public void setQueueResult(String queueResult) {
		this.queueResult = queueResult;
	}

	public String getAutomationLevelSelect() {
		return automationLevelSelect;
	}

	public void setAutomationLevelSelect(String automationLevelSelect) {
		this.automationLevelSelect = automationLevelSelect;
	}

	public Boolean getUseConfirmedState() {
		return useConfirmedState == null ? Boolean.FALSE : useConfirmedState;
	}

	public void setUseConfirmedState(Boolean useConfirmedState) {
		this.useConfirmedState = useConfirmedState;
	}

	public Workspace getWorkspaceUrl() {
		return workspaceUrl;
	}

	public void setWorkspaceUrl(Workspace workspaceUrl) {
		this.workspaceUrl = workspaceUrl;
	}

	public Schema getSchemaUrl() {
		return schemaUrl;
	}

	public void setSchemaUrl(Schema schemaUrl) {
		this.schemaUrl = schemaUrl;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (this == obj) return true;
		if (!(obj instanceof Queue)) return false;

		final Queue other = (Queue) obj;
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
			.add("queueId", getQueueId())
			.add("queueName", getQueueName())
			.add("queueUrl", getQueueUrl())
			.add("automationLevelSelect", getAutomationLevelSelect())
			.add("useConfirmedState", getUseConfirmedState())
			.omitNullValues()
			.toString();
	}
}
