package com.axelor.apps.base.db;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.axelor.auth.db.AuditableModel;
import com.axelor.auth.db.User;
import com.axelor.db.annotations.VirtualColumn;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "BASE_BLOCKING", indexes = { @Index(columnList = "partner"), @Index(columnList = "blocking_reason"), @Index(columnList = "blocking_by_user"), @Index(columnList = "name") })
public class Blocking extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BASE_BLOCKING_SEQ")
	@SequenceGenerator(name = "BASE_BLOCKING_SEQ", sequenceName = "BASE_BLOCKING_SEQ", allocationSize = 1)
	private Long id;

	@Widget(title = "Companies")
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Company> companySet;

	@Widget(title = "Partner")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Partner partner;

	@Widget(title = "Blocking Type", selection = "base.blocking.select")
	private Integer blockingSelect = 1;

	@Widget(title = "Block until")
	private LocalDate blockingToDate;

	@Widget(title = "Blocking reason")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private StopReason blockingReason;

	@Widget(title = "Blocking done by")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private User blockingByUser;

	@Widget(title = "Reference", search = { "partner" })
	@VirtualColumn
	@Access(AccessType.PROPERTY)
	private String name;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public Blocking() {
	}

	public Blocking(String name) {
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

	public Set<Company> getCompanySet() {
		return companySet;
	}

	public void setCompanySet(Set<Company> companySet) {
		this.companySet = companySet;
	}

	/**
	 * Add the given {@link Company} item to the {@code companySet}.
	 *
	 * @param item
	 *            the item to add
	 */
	public void addCompanySetItem(Company item) {
		if (getCompanySet() == null) {
			setCompanySet(new HashSet<>());
		}
		getCompanySet().add(item);
	}

	/**
	 * Remove the given {@link Company} item from the {@code companySet}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeCompanySetItem(Company item) {
		if (getCompanySet() == null) {
			return;
		}
		getCompanySet().remove(item);
	}

	/**
	 * Clear the {@code companySet} collection.
	 *
	 */
	public void clearCompanySet() {
		if (getCompanySet() != null) {
			getCompanySet().clear();
		}
	}

	public Partner getPartner() {
		return partner;
	}

	public void setPartner(Partner partner) {
		this.partner = partner;
	}

	public Integer getBlockingSelect() {
		return blockingSelect == null ? 0 : blockingSelect;
	}

	public void setBlockingSelect(Integer blockingSelect) {
		this.blockingSelect = blockingSelect;
	}

	public LocalDate getBlockingToDate() {
		return blockingToDate;
	}

	public void setBlockingToDate(LocalDate blockingToDate) {
		this.blockingToDate = blockingToDate;
	}

	public StopReason getBlockingReason() {
		return blockingReason;
	}

	public void setBlockingReason(StopReason blockingReason) {
		this.blockingReason = blockingReason;
	}

	public User getBlockingByUser() {
		return blockingByUser;
	}

	public void setBlockingByUser(User blockingByUser) {
		this.blockingByUser = blockingByUser;
	}

	public String getName() {
		try {
			name = computeName();
		} catch (NullPointerException e) {
			Logger logger = LoggerFactory.getLogger(getClass());
			logger.error("NPE in function field: getName()", e);
		}
		return name;
	}

	protected String computeName() {
		return partner != null ? partner.getName() : "";
	}

	public void setName(String name) {
		this.name = name;
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
		if (!(obj instanceof Blocking)) return false;

		final Blocking other = (Blocking) obj;
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
			.add("blockingSelect", getBlockingSelect())
			.add("blockingToDate", getBlockingToDate())
			.omitNullValues()
			.toString();
	}
}
