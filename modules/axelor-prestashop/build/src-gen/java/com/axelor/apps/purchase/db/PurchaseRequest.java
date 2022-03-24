package com.axelor.apps.purchase.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
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
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Type;

import com.axelor.apps.base.db.Company;
import com.axelor.apps.base.db.Partner;
import com.axelor.apps.stock.db.StockLocation;
import com.axelor.auth.db.AuditableModel;
import com.axelor.auth.db.User;
import com.axelor.db.annotations.Track;
import com.axelor.db.annotations.TrackField;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@Cacheable
@Table(name = "PURCHASE_PURCHASE_REQUEST", uniqueConstraints = { @UniqueConstraint(columnNames = { "purchaseRequestSeq" }) }, indexes = { @Index(columnList = "company"), @Index(columnList = "supplier_user"), @Index(columnList = "purchase_order"), @Index(columnList = "requester_user"), @Index(columnList = "validator_user"), @Index(columnList = "stock_location") })
@Track(fields = { @TrackField(name = "supplierUser"), @TrackField(name = "stockLocation") })
public class PurchaseRequest extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PURCHASE_PURCHASE_REQUEST_SEQ")
	@SequenceGenerator(name = "PURCHASE_PURCHASE_REQUEST_SEQ", sequenceName = "PURCHASE_PURCHASE_REQUEST_SEQ", allocationSize = 1)
	private Long id;

	@Widget(title = "Company")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Company company;

	@Widget(title = "Supplier")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Partner supplierUser;

	@Widget(title = "Description")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String description;

	@Widget(title = "Purchase order")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private PurchaseOrder purchaseOrder;

	@Widget(title = "Status", selection = "purchase.request.status.select")
	private Integer statusSelect = 1;

	@Widget(title = "Ref.")
	private String purchaseRequestSeq;

	@Widget(title = "Purchase Request Lines")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "purchaseRequest", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PurchaseRequestLine> purchaseRequestLineList;

	@Widget(title = "Requester", readonly = true)
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private User requesterUser;

	@Widget(title = "Validator", readonly = true)
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private User validatorUser;

	@Widget(title = "Stock location")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private StockLocation stockLocation;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public PurchaseRequest() {
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Partner getSupplierUser() {
		return supplierUser;
	}

	public void setSupplierUser(Partner supplierUser) {
		this.supplierUser = supplierUser;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public PurchaseOrder getPurchaseOrder() {
		return purchaseOrder;
	}

	public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}

	public Integer getStatusSelect() {
		return statusSelect == null ? 0 : statusSelect;
	}

	public void setStatusSelect(Integer statusSelect) {
		this.statusSelect = statusSelect;
	}

	public String getPurchaseRequestSeq() {
		return purchaseRequestSeq;
	}

	public void setPurchaseRequestSeq(String purchaseRequestSeq) {
		this.purchaseRequestSeq = purchaseRequestSeq;
	}

	public List<PurchaseRequestLine> getPurchaseRequestLineList() {
		return purchaseRequestLineList;
	}

	public void setPurchaseRequestLineList(List<PurchaseRequestLine> purchaseRequestLineList) {
		this.purchaseRequestLineList = purchaseRequestLineList;
	}

	/**
	 * Add the given {@link PurchaseRequestLine} item to the {@code purchaseRequestLineList}.
	 *
	 * <p>
	 * It sets {@code item.purchaseRequest = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addPurchaseRequestLineListItem(PurchaseRequestLine item) {
		if (getPurchaseRequestLineList() == null) {
			setPurchaseRequestLineList(new ArrayList<>());
		}
		getPurchaseRequestLineList().add(item);
		item.setPurchaseRequest(this);
	}

	/**
	 * Remove the given {@link PurchaseRequestLine} item from the {@code purchaseRequestLineList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removePurchaseRequestLineListItem(PurchaseRequestLine item) {
		if (getPurchaseRequestLineList() == null) {
			return;
		}
		getPurchaseRequestLineList().remove(item);
	}

	/**
	 * Clear the {@code purchaseRequestLineList} collection.
	 *
	 * <p>
	 * If you have to query {@link PurchaseRequestLine} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearPurchaseRequestLineList() {
		if (getPurchaseRequestLineList() != null) {
			getPurchaseRequestLineList().clear();
		}
	}

	public User getRequesterUser() {
		return requesterUser;
	}

	public void setRequesterUser(User requesterUser) {
		this.requesterUser = requesterUser;
	}

	public User getValidatorUser() {
		return validatorUser;
	}

	public void setValidatorUser(User validatorUser) {
		this.validatorUser = validatorUser;
	}

	public StockLocation getStockLocation() {
		return stockLocation;
	}

	public void setStockLocation(StockLocation stockLocation) {
		this.stockLocation = stockLocation;
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
		if (!(obj instanceof PurchaseRequest)) return false;

		final PurchaseRequest other = (PurchaseRequest) obj;
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
			.add("statusSelect", getStatusSelect())
			.add("purchaseRequestSeq", getPurchaseRequestSeq())
			.omitNullValues()
			.toString();
	}
}
