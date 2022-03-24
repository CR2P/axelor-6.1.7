package com.axelor.meta.db;

import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Cacheable;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

/**
 * This object stores the fields.
 */
@Entity
@Cacheable
@Table(name = "META_FIELD", indexes = { @Index(columnList = "meta_model"), @Index(columnList = "name") })
public class MetaField extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "META_FIELD_SEQ")
	@SequenceGenerator(name = "META_FIELD_SEQ", sequenceName = "META_FIELD_SEQ", allocationSize = 1)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private MetaModel metaModel;

	@Widget(title = "Name")
	@NotNull
	private String name;

	@Widget(title = "Package")
	private String packageName;

	@Widget(title = "Type")
	@NotNull
	private String typeName;

	@Widget(title = "Relationship", selection = "relationship.field.selection")
	private String relationship;

	@Widget(title = "Mapped by")
	private String mappedBy;

	@Widget(title = "Description")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String description;

	@Column(name = "is_json")
	private Boolean json = Boolean.FALSE;

	@Widget(title = "Label", translatable = true)
	private String label;

	public MetaField() {
	}

	public MetaField(String name) {
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

	public MetaModel getMetaModel() {
		return metaModel;
	}

	public void setMetaModel(MetaModel metaModel) {
		this.metaModel = metaModel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public String getMappedBy() {
		return mappedBy;
	}

	public void setMappedBy(String mappedBy) {
		this.mappedBy = mappedBy;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getJson() {
		return json == null ? Boolean.FALSE : json;
	}

	public void setJson(Boolean json) {
		this.json = json;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (this == obj) return true;
		if (!(obj instanceof MetaField)) return false;

		final MetaField other = (MetaField) obj;
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
			.add("packageName", getPackageName())
			.add("typeName", getTypeName())
			.add("relationship", getRelationship())
			.add("mappedBy", getMappedBy())
			.add("json", getJson())
			.add("label", getLabel())
			.omitNullValues()
			.toString();
	}
}
