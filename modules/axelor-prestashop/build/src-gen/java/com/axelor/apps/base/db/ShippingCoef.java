package com.axelor.apps.base.db;

import java.math.BigDecimal;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import com.axelor.apps.purchase.db.SupplierCatalog;
import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@Table(name = "BASE_SHIPPING_COEF", indexes = { @Index(columnList = "company"), @Index(columnList = "supplier_catalog") })
public class ShippingCoef extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BASE_SHIPPING_COEF_SEQ")
	@SequenceGenerator(name = "BASE_SHIPPING_COEF_SEQ", sequenceName = "BASE_SHIPPING_COEF_SEQ", allocationSize = 1)
	private Long id;

	@Widget(title = "Company")
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Company company;

	@Widget(title = "Shipping Coef.")
	private BigDecimal shippingCoef = BigDecimal.ZERO;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private SupplierCatalog supplierCatalog;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public ShippingCoef() {
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

	public BigDecimal getShippingCoef() {
		return shippingCoef == null ? BigDecimal.ZERO : shippingCoef;
	}

	public void setShippingCoef(BigDecimal shippingCoef) {
		this.shippingCoef = shippingCoef;
	}

	public SupplierCatalog getSupplierCatalog() {
		return supplierCatalog;
	}

	public void setSupplierCatalog(SupplierCatalog supplierCatalog) {
		this.supplierCatalog = supplierCatalog;
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
		if (!(obj instanceof ShippingCoef)) return false;

		final ShippingCoef other = (ShippingCoef) obj;
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
			.add("shippingCoef", getShippingCoef())
			.omitNullValues()
			.toString();
	}
}
