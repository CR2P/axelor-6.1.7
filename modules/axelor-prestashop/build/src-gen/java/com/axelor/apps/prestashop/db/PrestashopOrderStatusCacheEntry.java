package com.axelor.apps.prestashop.db;

import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Type;

import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.NameColumn;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@Cacheable
@Table(name = "PRESTASHOP_PRESTASHOP_ORDER_STATUS_CACHE_ENTRY", uniqueConstraints = { @UniqueConstraint(columnNames = { "prestaShopId" }) }, indexes = { @Index(columnList = "name") })
public class PrestashopOrderStatusCacheEntry extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRESTASHOP_PRESTASHOP_ORDER_STATUS_CACHE_ENTRY_SEQ")
	@SequenceGenerator(name = "PRESTASHOP_PRESTASHOP_ORDER_STATUS_CACHE_ENTRY_SEQ", sequenceName = "PRESTASHOP_PRESTASHOP_ORDER_STATUS_CACHE_ENTRY_SEQ", allocationSize = 1)
	private Long id;

	@Column(nullable = false)
	private Integer prestaShopId = 0;

	@NameColumn
	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private Boolean delivered = Boolean.FALSE;

	@Column(nullable = false)
	private Boolean invoiced = Boolean.FALSE;

	@Column(nullable = false)
	private Boolean shipped = Boolean.FALSE;

	@Column(nullable = false)
	private Boolean paid = Boolean.FALSE;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public PrestashopOrderStatusCacheEntry() {
	}

	public PrestashopOrderStatusCacheEntry(String name) {
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

	public Integer getPrestaShopId() {
		return prestaShopId == null ? 0 : prestaShopId;
	}

	public void setPrestaShopId(Integer prestaShopId) {
		this.prestaShopId = prestaShopId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getDelivered() {
		return delivered == null ? Boolean.FALSE : delivered;
	}

	public void setDelivered(Boolean delivered) {
		this.delivered = delivered;
	}

	public Boolean getInvoiced() {
		return invoiced == null ? Boolean.FALSE : invoiced;
	}

	public void setInvoiced(Boolean invoiced) {
		this.invoiced = invoiced;
	}

	public Boolean getShipped() {
		return shipped == null ? Boolean.FALSE : shipped;
	}

	public void setShipped(Boolean shipped) {
		this.shipped = shipped;
	}

	public Boolean getPaid() {
		return paid == null ? Boolean.FALSE : paid;
	}

	public void setPaid(Boolean paid) {
		this.paid = paid;
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
		if (!(obj instanceof PrestashopOrderStatusCacheEntry)) return false;

		final PrestashopOrderStatusCacheEntry other = (PrestashopOrderStatusCacheEntry) obj;
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
			.add("prestaShopId", getPrestaShopId())
			.add("name", getName())
			.add("delivered", getDelivered())
			.add("invoiced", getInvoiced())
			.add("shipped", getShipped())
			.add("paid", getPaid())
			.omitNullValues()
			.toString();
	}
}
