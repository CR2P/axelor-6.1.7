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
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.NameColumn;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@Table(name = "ROSSUM_SCHEMA", indexes = { @Index(columnList = "schemaName") })
public class Schema extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROSSUM_SCHEMA_SEQ")
	@SequenceGenerator(name = "ROSSUM_SCHEMA_SEQ", sequenceName = "ROSSUM_SCHEMA_SEQ", allocationSize = 1)
	private Long id;

	@Widget(title = "Schema id", readonly = true)
	private Integer schemaId = 0;

	@Widget(title = "Schema name")
	@NameColumn
	private String schemaName;

	@Widget(title = "Schema url", readonly = true)
	private String schemaUrl;

	@Widget(title = "Schema result")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String schemaResult;

	@Widget(title = "Schema template", selection = "rossum.schema.template.select")
	private String schemaTemplateSelect = "EU Demo Template";

	@Widget(title = "Queues", readonly = true)
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "schemaUrl", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Queue> queueList;

	@Widget(title = "Schema fields")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "schemaUrl", cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy("id")
	private List<SchemaField> schemaFieldList;

	public Schema() {
	}

	public Schema(Integer schemaId, String schemaName, String schemaUrl) {
		this.schemaId = schemaId;
		this.schemaName = schemaName;
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

	public Integer getSchemaId() {
		return schemaId == null ? 0 : schemaId;
	}

	public void setSchemaId(Integer schemaId) {
		this.schemaId = schemaId;
	}

	public String getSchemaName() {
		return schemaName;
	}

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

	public String getSchemaUrl() {
		return schemaUrl;
	}

	public void setSchemaUrl(String schemaUrl) {
		this.schemaUrl = schemaUrl;
	}

	public String getSchemaResult() {
		return schemaResult;
	}

	public void setSchemaResult(String schemaResult) {
		this.schemaResult = schemaResult;
	}

	public String getSchemaTemplateSelect() {
		return schemaTemplateSelect;
	}

	public void setSchemaTemplateSelect(String schemaTemplateSelect) {
		this.schemaTemplateSelect = schemaTemplateSelect;
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
	 * It sets {@code item.schemaUrl = this} to ensure the proper relationship.
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
		item.setSchemaUrl(this);
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

	public List<SchemaField> getSchemaFieldList() {
		return schemaFieldList;
	}

	public void setSchemaFieldList(List<SchemaField> schemaFieldList) {
		this.schemaFieldList = schemaFieldList;
	}

	/**
	 * Add the given {@link SchemaField} item to the {@code schemaFieldList}.
	 *
	 * <p>
	 * It sets {@code item.schemaUrl = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addSchemaFieldListItem(SchemaField item) {
		if (getSchemaFieldList() == null) {
			setSchemaFieldList(new ArrayList<>());
		}
		getSchemaFieldList().add(item);
		item.setSchemaUrl(this);
	}

	/**
	 * Remove the given {@link SchemaField} item from the {@code schemaFieldList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeSchemaFieldListItem(SchemaField item) {
		if (getSchemaFieldList() == null) {
			return;
		}
		getSchemaFieldList().remove(item);
	}

	/**
	 * Clear the {@code schemaFieldList} collection.
	 *
	 * <p>
	 * If you have to query {@link SchemaField} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearSchemaFieldList() {
		if (getSchemaFieldList() != null) {
			getSchemaFieldList().clear();
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (this == obj) return true;
		if (!(obj instanceof Schema)) return false;

		final Schema other = (Schema) obj;
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
			.add("schemaId", getSchemaId())
			.add("schemaName", getSchemaName())
			.add("schemaUrl", getSchemaUrl())
			.add("schemaTemplateSelect", getSchemaTemplateSelect())
			.omitNullValues()
			.toString();
	}
}
