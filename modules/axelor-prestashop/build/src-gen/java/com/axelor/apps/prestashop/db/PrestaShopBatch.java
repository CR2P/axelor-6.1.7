package com.axelor.apps.prestashop.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import com.axelor.apps.base.db.Batch;
import com.axelor.apps.base.db.Company;
import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.EqualsInclude;
import com.axelor.db.annotations.NameColumn;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@Table(name = "PRESTASHOP_PRESTA_SHOP_BATCH", indexes = { @Index(columnList = "company") })
public class PrestaShopBatch extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRESTASHOP_PRESTA_SHOP_BATCH_SEQ")
	@SequenceGenerator(name = "PRESTASHOP_PRESTA_SHOP_BATCH_SEQ", sequenceName = "PRESTASHOP_PRESTA_SHOP_BATCH_SEQ", allocationSize = 1)
	private Long id;

	@Widget(title = "Action", selection = "iprestashop.batch.action.select")
	@NotNull
	private Integer actionSelect = 0;

	@EqualsInclude
	@Widget(title = "Code")
	@NameColumn
	@Column(unique = true)
	private String code;

	@Widget(title = "Company")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Company company;

	@Widget(title = "Description")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String description;

	@Widget(title = "Batches")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "prestaShopBatch", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Batch> batchList;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public PrestaShopBatch() {
	}

	public PrestaShopBatch(String code) {
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

	public Integer getActionSelect() {
		return actionSelect == null ? 0 : actionSelect;
	}

	public void setActionSelect(Integer actionSelect) {
		this.actionSelect = actionSelect;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Batch> getBatchList() {
		return batchList;
	}

	public void setBatchList(List<Batch> batchList) {
		this.batchList = batchList;
	}

	/**
	 * Add the given {@link Batch} item to the {@code batchList}.
	 *
	 * <p>
	 * It sets {@code item.prestaShopBatch = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addBatchListItem(Batch item) {
		if (getBatchList() == null) {
			setBatchList(new ArrayList<>());
		}
		getBatchList().add(item);
		item.setPrestaShopBatch(this);
	}

	/**
	 * Remove the given {@link Batch} item from the {@code batchList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeBatchListItem(Batch item) {
		if (getBatchList() == null) {
			return;
		}
		getBatchList().remove(item);
	}

	/**
	 * Clear the {@code batchList} collection.
	 *
	 * <p>
	 * If you have to query {@link Batch} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearBatchList() {
		if (getBatchList() != null) {
			getBatchList().clear();
		}
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
		if (!(obj instanceof PrestaShopBatch)) return false;

		final PrestaShopBatch other = (PrestaShopBatch) obj;
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
			.add("actionSelect", getActionSelect())
			.add("code", getCode())
			.omitNullValues()
			.toString();
	}
}
