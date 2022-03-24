package com.axelor.apps.stock.db;

import java.math.BigDecimal;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.axelor.apps.base.db.Product;
import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@Table(name = "STOCK_SHIPMENT_MODE", indexes = { @Index(columnList = "name"), @Index(columnList = "shipping_costs_product") })
public class ShipmentMode extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STOCK_SHIPMENT_MODE_SEQ")
	@SequenceGenerator(name = "STOCK_SHIPMENT_MODE_SEQ", sequenceName = "STOCK_SHIPMENT_MODE_SEQ", allocationSize = 1)
	private Long id;

	@Widget(title = "Shipment Mode")
	private String name;

	@Widget(title = "Freight Carrier Mode")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "shipmentMode", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<FreightCarrierMode> freightCarrierMode;

	@Widget(title = "Shipping cost")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Product shippingCostsProduct;

	@Widget(title = "Carriage paid threshold")
	private BigDecimal carriagePaidThreshold = BigDecimal.ZERO;

	@Widget(title = "Has carriage paid possibility")
	private Boolean hasCarriagePaidPossibility = Boolean.FALSE;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public ShipmentMode() {
	}

	public ShipmentMode(String name) {
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<FreightCarrierMode> getFreightCarrierMode() {
		return freightCarrierMode;
	}

	public void setFreightCarrierMode(List<FreightCarrierMode> freightCarrierMode) {
		this.freightCarrierMode = freightCarrierMode;
	}

	/**
	 * Add the given {@link FreightCarrierMode} item to the {@code freightCarrierMode}.
	 *
	 * <p>
	 * It sets {@code item.shipmentMode = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addFreightCarrierMode(FreightCarrierMode item) {
		if (getFreightCarrierMode() == null) {
			setFreightCarrierMode(new ArrayList<>());
		}
		getFreightCarrierMode().add(item);
		item.setShipmentMode(this);
	}

	/**
	 * Remove the given {@link FreightCarrierMode} item from the {@code freightCarrierMode}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeFreightCarrierMode(FreightCarrierMode item) {
		if (getFreightCarrierMode() == null) {
			return;
		}
		getFreightCarrierMode().remove(item);
	}

	/**
	 * Clear the {@code freightCarrierMode} collection.
	 *
	 * <p>
	 * If you have to query {@link FreightCarrierMode} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearFreightCarrierMode() {
		if (getFreightCarrierMode() != null) {
			getFreightCarrierMode().clear();
		}
	}

	public Product getShippingCostsProduct() {
		return shippingCostsProduct;
	}

	public void setShippingCostsProduct(Product shippingCostsProduct) {
		this.shippingCostsProduct = shippingCostsProduct;
	}

	public BigDecimal getCarriagePaidThreshold() {
		return carriagePaidThreshold == null ? BigDecimal.ZERO : carriagePaidThreshold;
	}

	public void setCarriagePaidThreshold(BigDecimal carriagePaidThreshold) {
		this.carriagePaidThreshold = carriagePaidThreshold;
	}

	public Boolean getHasCarriagePaidPossibility() {
		return hasCarriagePaidPossibility == null ? Boolean.FALSE : hasCarriagePaidPossibility;
	}

	public void setHasCarriagePaidPossibility(Boolean hasCarriagePaidPossibility) {
		this.hasCarriagePaidPossibility = hasCarriagePaidPossibility;
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
		if (!(obj instanceof ShipmentMode)) return false;

		final ShipmentMode other = (ShipmentMode) obj;
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
			.add("carriagePaidThreshold", getCarriagePaidThreshold())
			.add("hasCarriagePaidPossibility", getHasCarriagePaidPossibility())
			.omitNullValues()
			.toString();
	}
}
