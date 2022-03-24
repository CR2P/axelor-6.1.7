package com.axelor.team.db;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import com.axelor.auth.db.AuditableModel;
import com.axelor.auth.db.Role;
import com.axelor.auth.db.User;
import com.axelor.db.annotations.EqualsInclude;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@Table(name = "TEAM_TEAM", indexes = { @Index(columnList = "code") })
public class Team extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TEAM_TEAM_SEQ")
	@SequenceGenerator(name = "TEAM_TEAM_SEQ", sequenceName = "TEAM_TEAM_SEQ", allocationSize = 1)
	private Long id;

	@EqualsInclude
	@NotNull
	@Column(unique = true)
	private String name;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String description;

	@Widget(title = "Photo")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] image;

	@Widget(title = "Authorized roles")
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Role> roles;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<User> members;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TeamTopic> topics;

	@Widget(title = "Code")
	private String code;

	@Widget(title = "Type", selection = "team.type.select")
	private String typeSelect;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public Team() {
	}

	public Team(String name, String code) {
		this.name = name;
		this.code = code;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
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

	public Set<User> getMembers() {
		return members;
	}

	public void setMembers(Set<User> members) {
		this.members = members;
	}

	/**
	 * Add the given {@link User} item to the {@code members}.
	 *
	 * @param item
	 *            the item to add
	 */
	public void addMember(User item) {
		if (getMembers() == null) {
			setMembers(new HashSet<>());
		}
		getMembers().add(item);
	}

	/**
	 * Remove the given {@link User} item from the {@code members}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeMember(User item) {
		if (getMembers() == null) {
			return;
		}
		getMembers().remove(item);
	}

	/**
	 * Clear the {@code members} collection.
	 *
	 */
	public void clearMembers() {
		if (getMembers() != null) {
			getMembers().clear();
		}
	}

	public List<TeamTopic> getTopics() {
		return topics;
	}

	public void setTopics(List<TeamTopic> topics) {
		this.topics = topics;
	}

	/**
	 * Add the given {@link TeamTopic} item to the {@code topics}.
	 *
	 * <p>
	 * It sets {@code item.team = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addTopic(TeamTopic item) {
		if (getTopics() == null) {
			setTopics(new ArrayList<>());
		}
		getTopics().add(item);
		item.setTeam(this);
	}

	/**
	 * Remove the given {@link TeamTopic} item from the {@code topics}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeTopic(TeamTopic item) {
		if (getTopics() == null) {
			return;
		}
		getTopics().remove(item);
	}

	/**
	 * Clear the {@code topics} collection.
	 *
	 * <p>
	 * If you have to query {@link TeamTopic} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearTopics() {
		if (getTopics() != null) {
			getTopics().clear();
		}
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTypeSelect() {
		return typeSelect;
	}

	public void setTypeSelect(String typeSelect) {
		this.typeSelect = typeSelect;
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
		if (!(obj instanceof Team)) return false;

		final Team other = (Team) obj;
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
			.add("code", getCode())
			.add("typeSelect", getTypeSelect())
			.omitNullValues()
			.toString();
	}
}
