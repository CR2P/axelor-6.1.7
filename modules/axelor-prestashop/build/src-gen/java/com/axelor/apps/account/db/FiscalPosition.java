package com.axelor.apps.account.db;

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
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@Table(name = "ACCOUNT_FISCAL_POSITION", indexes = { @Index(columnList = "name"), @Index(columnList = "code") })
public class FiscalPosition extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCOUNT_FISCAL_POSITION_SEQ")
	@SequenceGenerator(name = "ACCOUNT_FISCAL_POSITION_SEQ", sequenceName = "ACCOUNT_FISCAL_POSITION_SEQ", allocationSize = 1)
	private Long id;

	@Widget(title = "Name")
	@NotNull
	private String name;

	@Widget(title = "Code")
	@NotNull
	private String code;

	@Widget(title = "Tax equivalences")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fiscalPosition", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TaxEquiv> taxEquivList;

	@Widget(title = "Account equivalences")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fiscalPosition", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<AccountEquiv> accountEquivList;

	@Widget(title = "Customer specific note")
	private Boolean customerSpecificNote = Boolean.FALSE;

	@Widget(title = "Customer specific note")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String customerSpecificNoteText;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public FiscalPosition() {
	}

	public FiscalPosition(String name, String code) {
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

	public List<TaxEquiv> getTaxEquivList() {
		return taxEquivList;
	}

	public void setTaxEquivList(List<TaxEquiv> taxEquivList) {
		this.taxEquivList = taxEquivList;
	}

	/**
	 * Add the given {@link TaxEquiv} item to the {@code taxEquivList}.
	 *
	 * <p>
	 * It sets {@code item.fiscalPosition = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addTaxEquivListItem(TaxEquiv item) {
		if (getTaxEquivList() == null) {
			setTaxEquivList(new ArrayList<>());
		}
		getTaxEquivList().add(item);
		item.setFiscalPosition(this);
	}

	/**
	 * Remove the given {@link TaxEquiv} item from the {@code taxEquivList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeTaxEquivListItem(TaxEquiv item) {
		if (getTaxEquivList() == null) {
			return;
		}
		getTaxEquivList().remove(item);
	}

	/**
	 * Clear the {@code taxEquivList} collection.
	 *
	 * <p>
	 * If you have to query {@link TaxEquiv} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearTaxEquivList() {
		if (getTaxEquivList() != null) {
			getTaxEquivList().clear();
		}
	}

	public List<AccountEquiv> getAccountEquivList() {
		return accountEquivList;
	}

	public void setAccountEquivList(List<AccountEquiv> accountEquivList) {
		this.accountEquivList = accountEquivList;
	}

	/**
	 * Add the given {@link AccountEquiv} item to the {@code accountEquivList}.
	 *
	 * <p>
	 * It sets {@code item.fiscalPosition = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addAccountEquivListItem(AccountEquiv item) {
		if (getAccountEquivList() == null) {
			setAccountEquivList(new ArrayList<>());
		}
		getAccountEquivList().add(item);
		item.setFiscalPosition(this);
	}

	/**
	 * Remove the given {@link AccountEquiv} item from the {@code accountEquivList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeAccountEquivListItem(AccountEquiv item) {
		if (getAccountEquivList() == null) {
			return;
		}
		getAccountEquivList().remove(item);
	}

	/**
	 * Clear the {@code accountEquivList} collection.
	 *
	 * <p>
	 * If you have to query {@link AccountEquiv} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearAccountEquivList() {
		if (getAccountEquivList() != null) {
			getAccountEquivList().clear();
		}
	}

	public Boolean getCustomerSpecificNote() {
		return customerSpecificNote == null ? Boolean.FALSE : customerSpecificNote;
	}

	public void setCustomerSpecificNote(Boolean customerSpecificNote) {
		this.customerSpecificNote = customerSpecificNote;
	}

	public String getCustomerSpecificNoteText() {
		return customerSpecificNoteText;
	}

	public void setCustomerSpecificNoteText(String customerSpecificNoteText) {
		this.customerSpecificNoteText = customerSpecificNoteText;
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
		if (!(obj instanceof FiscalPosition)) return false;

		final FiscalPosition other = (FiscalPosition) obj;
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
			.add("code", getCode())
			.add("customerSpecificNote", getCustomerSpecificNote())
			.omitNullValues()
			.toString();
	}
}
