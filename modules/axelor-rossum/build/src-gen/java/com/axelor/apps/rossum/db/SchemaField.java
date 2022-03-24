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
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@Table(name = "ROSSUM_SCHEMA_FIELD", indexes = { @Index(columnList = "schema_url") })
public class SchemaField extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROSSUM_SCHEMA_FIELD_SEQ")
	@SequenceGenerator(name = "ROSSUM_SCHEMA_FIELD_SEQ", sequenceName = "ROSSUM_SCHEMA_FIELD_SEQ", allocationSize = 1)
	private Long id;

	@Widget(title = "Label", readonly = true)
	private String label;

	@Widget(title = "Field id", readonly = true)
	private String schemaFieldId;

	@Widget(title = "Parent schema field id", readonly = true)
	private String parentSchemaFieldId;

	@Widget(title = "Can export")
	private Boolean canExport = Boolean.TRUE;

	@Widget(title = "Schema")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Schema schemaUrl;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public SchemaField() {
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getSchemaFieldId() {
		return schemaFieldId;
	}

	public void setSchemaFieldId(String schemaFieldId) {
		this.schemaFieldId = schemaFieldId;
	}

	public String getParentSchemaFieldId() {
		return parentSchemaFieldId;
	}

	public void setParentSchemaFieldId(String parentSchemaFieldId) {
		this.parentSchemaFieldId = parentSchemaFieldId;
	}

	public Boolean getCanExport() {
		return canExport == null ? Boolean.FALSE : canExport;
	}

	public void setCanExport(Boolean canExport) {
		this.canExport = canExport;
	}

	public Schema getSchemaUrl() {
		return schemaUrl;
	}

	public void setSchemaUrl(Schema schemaUrl) {
		this.schemaUrl = schemaUrl;
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
		if (!(obj instanceof SchemaField)) return false;

		final SchemaField other = (SchemaField) obj;
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
			.add("label", getLabel())
			.add("schemaFieldId", getSchemaFieldId())
			.add("parentSchemaFieldId", getParentSchemaFieldId())
			.add("canExport", getCanExport())
			.omitNullValues()
			.toString();
	}
}
