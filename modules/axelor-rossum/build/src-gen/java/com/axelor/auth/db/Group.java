package com.axelor.auth.db;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;

import com.axelor.db.annotations.EqualsInclude;
import com.axelor.db.annotations.Track;
import com.axelor.db.annotations.TrackField;
import com.axelor.db.annotations.Widget;
import com.axelor.meta.db.MetaMenu;
import com.axelor.meta.db.MetaPermission;
import com.axelor.meta.db.MetaView;
import com.google.common.base.MoreObjects;

/**
 * This object stores the groups.
 */
@Entity
@Cacheable
@Table(name = "AUTH_GROUP")
@Track(fields = { @TrackField(name = "code"), @TrackField(name = "name"), @TrackField(name = "navigation"), @TrackField(name = "homeAction"), @TrackField(name = "technicalStaff"), @TrackField(name = "isClient"), @TrackField(name = "isSupplier") })
public class Group extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AUTH_GROUP_SEQ")
	@SequenceGenerator(name = "AUTH_GROUP_SEQ", sequenceName = "AUTH_GROUP_SEQ", allocationSize = 1)
	private Long id;

	@EqualsInclude
	@NotNull
	@Size(min = 2)
	@Column(unique = true)
	private String code;

	@EqualsInclude
	@NotNull
	@Size(min = 2)
	@Column(unique = true)
	private String name;

	@Widget(title = "View customization")
	@Basic
	@Type(type = "com.axelor.db.hibernate.type.ValueEnumType")
	private ViewCustomizationPermission viewCustomizationPermission = ViewCustomizationPermission.NOT_ALLOWED;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Role> roles;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Permission> permissions;

	@Widget(title = "Permissions (fields)")
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<MetaPermission> metaPermissions;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "groups", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<MetaMenu> menus;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "groups", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<MetaView> views;

	@Widget(help = "Specify whether the members of this group are technical staff.", massUpdate = true)
	private Boolean technicalStaff = Boolean.FALSE;

	@Widget(selection = "select.user.navigation", massUpdate = true)
	private String navigation;

	@Widget(help = "Default home action.", massUpdate = true)
	private String homeAction;

	@Widget(title = "Client", massUpdate = true)
	private Boolean isClient = Boolean.FALSE;

	@Widget(title = "Supplier", massUpdate = true)
	private Boolean isSupplier = Boolean.FALSE;

	public Group() {
	}

	public Group(String code, String name) {
		this.code = code;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ViewCustomizationPermission getViewCustomizationPermission() {
		return viewCustomizationPermission;
	}

	public void setViewCustomizationPermission(ViewCustomizationPermission viewCustomizationPermission) {
		this.viewCustomizationPermission = viewCustomizationPermission;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	/**
	 * Add the given {@link Role} item to the {@code roles}.
	 *
	 * @param item
	 *            the item to add
	 */
	public void addRole(Role item) {
		if (getRoles() == null) {
			setRoles(new HashSet<>());
		}
		getRoles().add(item);
	}

	/**
	 * Remove the given {@link Role} item from the {@code roles}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeRole(Role item) {
		if (getRoles() == null) {
			return;
		}
		getRoles().remove(item);
	}

	/**
	 * Clear the {@code roles} collection.
	 *
	 */
	public void clearRoles() {
		if (getRoles() != null) {
			getRoles().clear();
		}
	}

	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	/**
	 * Add the given {@link Permission} item to the {@code permissions}.
	 *
	 * @param item
	 *            the item to add
	 */
	public void addPermission(Permission item) {
		if (getPermissions() == null) {
			setPermissions(new HashSet<>());
		}
		getPermissions().add(item);
	}

	/**
	 * Remove the given {@link Permission} item from the {@code permissions}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removePermission(Permission item) {
		if (getPermissions() == null) {
			return;
		}
		getPermissions().remove(item);
	}

	/**
	 * Clear the {@code permissions} collection.
	 *
	 */
	public void clearPermissions() {
		if (getPermissions() != null) {
			getPermissions().clear();
		}
	}

	public Set<MetaPermission> getMetaPermissions() {
		return metaPermissions;
	}

	public void setMetaPermissions(Set<MetaPermission> metaPermissions) {
		this.metaPermissions = metaPermissions;
	}

	/**
	 * Add the given {@link MetaPermission} item to the {@code metaPermissions}.
	 *
	 * @param item
	 *            the item to add
	 */
	public void addMetaPermission(MetaPermission item) {
		if (getMetaPermissions() == null) {
			setMetaPermissions(new HashSet<>());
		}
		getMetaPermissions().add(item);
	}

	/**
	 * Remove the given {@link MetaPermission} item from the {@code metaPermissions}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeMetaPermission(MetaPermission item) {
		if (getMetaPermissions() == null) {
			return;
		}
		getMetaPermissions().remove(item);
	}

	/**
	 * Clear the {@code metaPermissions} collection.
	 *
	 */
	public void clearMetaPermissions() {
		if (getMetaPermissions() != null) {
			getMetaPermissions().clear();
		}
	}

	public Set<MetaMenu> getMenus() {
		return menus;
	}

	public void setMenus(Set<MetaMenu> menus) {
		this.menus = menus;
	}

	/**
	 * Add the given {@link MetaMenu} item to the {@code menus}.
	 *
	 * @param item
	 *            the item to add
	 */
	public void addMenu(MetaMenu item) {
		if (getMenus() == null) {
			setMenus(new HashSet<>());
		}
		getMenus().add(item);
	}

	/**
	 * Remove the given {@link MetaMenu} item from the {@code menus}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeMenu(MetaMenu item) {
		if (getMenus() == null) {
			return;
		}
		getMenus().remove(item);
	}

	/**
	 * Clear the {@code menus} collection.
	 *
	 */
	public void clearMenus() {
		if (getMenus() != null) {
			getMenus().clear();
		}
	}

	public Set<MetaView> getViews() {
		return views;
	}

	public void setViews(Set<MetaView> views) {
		this.views = views;
	}

	/**
	 * Add the given {@link MetaView} item to the {@code views}.
	 *
	 * @param item
	 *            the item to add
	 */
	public void addView(MetaView item) {
		if (getViews() == null) {
			setViews(new HashSet<>());
		}
		getViews().add(item);
	}

	/**
	 * Remove the given {@link MetaView} item from the {@code views}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeView(MetaView item) {
		if (getViews() == null) {
			return;
		}
		getViews().remove(item);
	}

	/**
	 * Clear the {@code views} collection.
	 *
	 */
	public void clearViews() {
		if (getViews() != null) {
			getViews().clear();
		}
	}

	/**
	 * Specify whether the members of this group are technical staff.
	 *
	 * @return the property value
	 */
	public Boolean getTechnicalStaff() {
		return technicalStaff == null ? Boolean.FALSE : technicalStaff;
	}

	public void setTechnicalStaff(Boolean technicalStaff) {
		this.technicalStaff = technicalStaff;
	}

	public String getNavigation() {
		return navigation;
	}

	public void setNavigation(String navigation) {
		this.navigation = navigation;
	}

	/**
	 * Default home action.
	 *
	 * @return the property value
	 */
	public String getHomeAction() {
		return homeAction;
	}

	public void setHomeAction(String homeAction) {
		this.homeAction = homeAction;
	}

	public Boolean getIsClient() {
		return isClient == null ? Boolean.FALSE : isClient;
	}

	public void setIsClient(Boolean isClient) {
		this.isClient = isClient;
	}

	public Boolean getIsSupplier() {
		return isSupplier == null ? Boolean.FALSE : isSupplier;
	}

	public void setIsSupplier(Boolean isSupplier) {
		this.isSupplier = isSupplier;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (this == obj) return true;
		if (!(obj instanceof Group)) return false;

		final Group other = (Group) obj;
		if (this.getId() != null || other.getId() != null) {
			return Objects.equals(this.getId(), other.getId());
		}

		return Objects.equals(getCode(), other.getCode())
			&& Objects.equals(getName(), other.getName())
			&& (getCode() != null
				|| getName() != null);
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
			.add("name", getName())
			.add("viewCustomizationPermission", getViewCustomizationPermission())
			.add("technicalStaff", getTechnicalStaff())
			.add("navigation", getNavigation())
			.add("homeAction", getHomeAction())
			.add("isClient", getIsClient())
			.add("isSupplier", getIsSupplier())
			.omitNullValues()
			.toString();
	}
}
