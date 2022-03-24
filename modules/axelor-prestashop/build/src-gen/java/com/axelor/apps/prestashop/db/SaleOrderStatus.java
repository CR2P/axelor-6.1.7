package com.axelor.apps.prestashop.db;

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
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@Cacheable
@Table(name = "PRESTASHOP_SALE_ORDER_STATUS", indexes = { @Index(columnList = "presta_shop_status") })
public class SaleOrderStatus extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRESTASHOP_SALE_ORDER_STATUS_SEQ")
	@SequenceGenerator(name = "PRESTASHOP_SALE_ORDER_STATUS_SEQ", sequenceName = "PRESTASHOP_SALE_ORDER_STATUS_SEQ", allocationSize = 1)
	private Long id;

	@Widget(title = "Sale order status", selection = "sale.order.status.select")
	private Integer absStatus = 0;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private PrestashopOrderStatusCacheEntry prestaShopStatus;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public SaleOrderStatus() {
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Integer getAbsStatus() {
		return absStatus == null ? 0 : absStatus;
	}

	public void setAbsStatus(Integer absStatus) {
		this.absStatus = absStatus;
	}

	public PrestashopOrderStatusCacheEntry getPrestaShopStatus() {
		return prestaShopStatus;
	}

	public void setPrestaShopStatus(PrestashopOrderStatusCacheEntry prestaShopStatus) {
		this.prestaShopStatus = prestaShopStatus;
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
		if (!(obj instanceof SaleOrderStatus)) return false;

		final SaleOrderStatus other = (SaleOrderStatus) obj;
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
			.add("absStatus", getAbsStatus())
			.omitNullValues()
			.toString();
	}
}
