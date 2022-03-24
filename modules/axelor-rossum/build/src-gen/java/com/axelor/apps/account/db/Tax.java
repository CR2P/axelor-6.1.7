package com.axelor.apps.account.db;

import java.util.ArrayList;
import java.util.List;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.EqualsInclude;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@Cacheable
@Table(name = "ACCOUNT_TAX", uniqueConstraints = { @UniqueConstraint(columnNames = { "code", "name" }) }, indexes = { @Index(columnList = "name"), @Index(columnList = "active_tax_line") })
public class Tax extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCOUNT_TAX_SEQ")
	@SequenceGenerator(name = "ACCOUNT_TAX_SEQ", sequenceName = "ACCOUNT_TAX_SEQ", allocationSize = 1)
	private Long id;

	@Widget(title = "Name")
	@NotNull
	private String name;

	@EqualsInclude
	@Widget(title = "Code")
	@NotNull
	@Column(unique = true)
	private String code;

	@Widget(title = "Tax active version")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private TaxLine activeTaxLine;

	@Widget(title = "Tax versions history")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tax", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TaxLine> taxLineList;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tax", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<AccountManagement> accountManagementList;

	@Widget(title = "Tax type", selection = "account.tax.type.select")
	private Integer typeSelect = 2;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public Tax() {
	}

	public Tax(String name, String code) {
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public TaxLine getActiveTaxLine() {
		return activeTaxLine;
	}

	public void setActiveTaxLine(TaxLine activeTaxLine) {
		this.activeTaxLine = activeTaxLine;
	}

	public List<TaxLine> getTaxLineList() {
		return taxLineList;
	}

	public void setTaxLineList(List<TaxLine> taxLineList) {
		this.taxLineList = taxLineList;
	}

	/**
	 * Add the given {@link TaxLine} item to the {@code taxLineList}.
	 *
	 * <p>
	 * It sets {@code item.tax = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addTaxLineListItem(TaxLine item) {
		if (getTaxLineList() == null) {
			setTaxLineList(new ArrayList<>());
		}
		getTaxLineList().add(item);
		item.setTax(this);
	}

	/**
	 * Remove the given {@link TaxLine} item from the {@code taxLineList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeTaxLineListItem(TaxLine item) {
		if (getTaxLineList() == null) {
			return;
		}
		getTaxLineList().remove(item);
	}

	/**
	 * Clear the {@code taxLineList} collection.
	 *
	 * <p>
	 * If you have to query {@link TaxLine} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearTaxLineList() {
		if (getTaxLineList() != null) {
			getTaxLineList().clear();
		}
	}

	public List<AccountManagement> getAccountManagementList() {
		return accountManagementList;
	}

	public void setAccountManagementList(List<AccountManagement> accountManagementList) {
		this.accountManagementList = accountManagementList;
	}

	/**
	 * Add the given {@link AccountManagement} item to the {@code accountManagementList}.
	 *
	 * <p>
	 * It sets {@code item.tax = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addAccountManagementListItem(AccountManagement item) {
		if (getAccountManagementList() == null) {
			setAccountManagementList(new ArrayList<>());
		}
		getAccountManagementList().add(item);
		item.setTax(this);
	}

	/**
	 * Remove the given {@link AccountManagement} item from the {@code accountManagementList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeAccountManagementListItem(AccountManagement item) {
		if (getAccountManagementList() == null) {
			return;
		}
		getAccountManagementList().remove(item);
	}

	/**
	 * Clear the {@code accountManagementList} collection.
	 *
	 * <p>
	 * If you have to query {@link AccountManagement} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearAccountManagementList() {
		if (getAccountManagementList() != null) {
			getAccountManagementList().clear();
		}
	}

	public Integer getTypeSelect() {
		return typeSelect == null ? 0 : typeSelect;
	}

	public void setTypeSelect(Integer typeSelect) {
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
		if (!(obj instanceof Tax)) return false;

		final Tax other = (Tax) obj;
		if (this.getId() != null || other.getId() != null) {
			return Objects.equals(this.getId(), other.getId());
		}

		return Objects.equals(getCode(), other.getCode())
			&& (getCode() != null);
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
