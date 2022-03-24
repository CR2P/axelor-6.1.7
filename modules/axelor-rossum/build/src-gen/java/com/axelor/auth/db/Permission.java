package com.axelor.auth.db;

import java.util.Objects;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.axelor.db.annotations.EqualsInclude;
import com.axelor.db.annotations.Track;
import com.axelor.db.annotations.TrackField;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

/**
 * This object stores the permissions.
 */
@Entity
@Cacheable
@Table(name = "AUTH_PERMISSION")
@Track(fields = { @TrackField(name = "name"), @TrackField(name = "object"), @TrackField(name = "canRead"), @TrackField(name = "canWrite"), @TrackField(name = "canCreate"), @TrackField(name = "canRemove"), @TrackField(name = "canExport"), @TrackField(name = "condition"), @TrackField(name = "conditionParams") })
public class Permission extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AUTH_PERMISSION_SEQ")
	@SequenceGenerator(name = "AUTH_PERMISSION_SEQ", sequenceName = "AUTH_PERMISSION_SEQ", allocationSize = 1)
	private Long id;

	@EqualsInclude
	@Widget(help = "An unique permission name.")
	@NotNull
	@Column(unique = true)
	private String name;

	@Widget(help = "Fully qualified object name or wild card package name.")
	@NotNull
	private String object;

	@Widget(title = "Read", help = "Whether to grant read access.")
	private Boolean canRead = Boolean.FALSE;

	@Widget(title = "Write", help = "Whether to grant write access.")
	private Boolean canWrite = Boolean.FALSE;

	@Widget(title = "Create", help = "Whether to grant create access.")
	private Boolean canCreate = Boolean.FALSE;

	@Widget(title = "Remove", help = "Whether to grant remove access.")
	private Boolean canRemove = Boolean.FALSE;

	@Widget(title = "Export", help = "Whether to grant export access.")
	private Boolean canExport = Boolean.FALSE;

	@Widget(help = "Domain filter as condition.")
	@Size(max = 1024)
	@Column(name = "condition_value")
	private String condition;

	@Widget(help = "Comma separated list of params for the condition.")
	private String conditionParams;

	public Permission() {
	}

	public Permission(String name) {
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

	/**
	 * An unique permission name.
	 *
	 * @return the property value
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Fully qualified object name or wild card package name.
	 *
	 * @return the property value
	 */
	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	/**
	 * Whether to grant read access.
	 *
	 * @return the property value
	 */
	public Boolean getCanRead() {
		return canRead == null ? Boolean.FALSE : canRead;
	}

	public void setCanRead(Boolean canRead) {
		this.canRead = canRead;
	}

	/**
	 * Whether to grant write access.
	 *
	 * @return the property value
	 */
	public Boolean getCanWrite() {
		return canWrite == null ? Boolean.FALSE : canWrite;
	}

	public void setCanWrite(Boolean canWrite) {
		this.canWrite = canWrite;
	}

	/**
	 * Whether to grant create access.
	 *
	 * @return the property value
	 */
	public Boolean getCanCreate() {
		return canCreate == null ? Boolean.FALSE : canCreate;
	}

	public void setCanCreate(Boolean canCreate) {
		this.canCreate = canCreate;
	}

	/**
	 * Whether to grant remove access.
	 *
	 * @return the property value
	 */
	public Boolean getCanRemove() {
		return canRemove == null ? Boolean.FALSE : canRemove;
	}

	public void setCanRemove(Boolean canRemove) {
		this.canRemove = canRemove;
	}

	/**
	 * Whether to grant export access.
	 *
	 * @return the property value
	 */
	public Boolean getCanExport() {
		return canExport == null ? Boolean.FALSE : canExport;
	}

	public void setCanExport(Boolean canExport) {
		this.canExport = canExport;
	}

	/**
	 * Domain filter as condition.
	 *
	 * @return the property value
	 */
	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	/**
	 * Comma separated list of params for the condition.
	 *
	 * @return the property value
	 */
	public String getConditionParams() {
		return conditionParams;
	}

	public void setConditionParams(String conditionParams) {
		this.conditionParams = conditionParams;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (this == obj) return true;
		if (!(obj instanceof Permission)) return false;

		final Permission other = (Permission) obj;
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
			.add("name", getName())
			.add("object", getObject())
			.add("canRead", getCanRead())
			.add("canWrite", getCanWrite())
			.add("canCreate", getCanCreate())
			.add("canRemove", getCanRemove())
			.add("canExport", getCanExport())
			.add("condition", getCondition())
			.add("conditionParams", getConditionParams())
			.omitNullValues()
			.toString();
	}
}
