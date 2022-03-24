package com.axelor.apps.base.db;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.EqualsInclude;
import com.axelor.db.annotations.NameColumn;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@Cacheable
@Table(name = "BASE_PRODUCT_CATEGORY", uniqueConstraints = { @UniqueConstraint(columnNames = { "prestaShopId" }) }, indexes = { @Index(columnList = "parent_product_category"), @Index(columnList = "sequence") })
public class ProductCategory extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BASE_PRODUCT_CATEGORY_SEQ")
	@SequenceGenerator(name = "BASE_PRODUCT_CATEGORY_SEQ", sequenceName = "BASE_PRODUCT_CATEGORY_SEQ", allocationSize = 1)
	private Long id;

	@EqualsInclude
	@Widget(title = "Code")
	@NotNull
	@Column(unique = true)
	private String code;

	@EqualsInclude
	@Widget(title = "Name")
	@NameColumn
	@NotNull
	@Column(unique = true)
	private String name;

	@Widget(title = "Parent category")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private ProductCategory parentProductCategory;

	@Widget(title = "Sequence")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Sequence sequence;

	@Widget(title = "Max discount (%)")
	@DecimalMin("0")
	@DecimalMax("100")
	@Digits(integer = 3, fraction = 2)
	private BigDecimal maxDiscount = BigDecimal.ZERO;

	@Column(nullable = true)
	private Integer prestaShopId;

	@Column(nullable = true)
	private Integer prestaShopVersion;

	@Column(nullable = true)
	private LocalDateTime prestaShopUpdateDateTime;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public ProductCategory() {
	}

	public ProductCategory(String code, String name) {
		this.code = code;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ProductCategory getParentProductCategory() {
		return parentProductCategory;
	}

	public void setParentProductCategory(ProductCategory parentProductCategory) {
		this.parentProductCategory = parentProductCategory;
	}

	public Sequence getSequence() {
		return sequence;
	}

	public void setSequence(Sequence sequence) {
		this.sequence = sequence;
	}

	public BigDecimal getMaxDiscount() {
		return maxDiscount == null ? BigDecimal.ZERO : maxDiscount;
	}

	public void setMaxDiscount(BigDecimal maxDiscount) {
		this.maxDiscount = maxDiscount;
	}

	public Integer getPrestaShopId() {
		return prestaShopId;
	}

	public void setPrestaShopId(Integer prestaShopId) {
		this.prestaShopId = prestaShopId;
	}

	public Integer getPrestaShopVersion() {
		return prestaShopVersion;
	}

	public void setPrestaShopVersion(Integer prestaShopVersion) {
		this.prestaShopVersion = prestaShopVersion;
	}

	public LocalDateTime getPrestaShopUpdateDateTime() {
		return prestaShopUpdateDateTime;
	}

	public void setPrestaShopUpdateDateTime(LocalDateTime prestaShopUpdateDateTime) {
		this.prestaShopUpdateDateTime = prestaShopUpdateDateTime;
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
		if (!(obj instanceof ProductCategory)) return false;

		final ProductCategory other = (ProductCategory) obj;
		if (this.getId() != null || other.getId() != null) {
			return Objects.equals(this.getId(), other.getId());
		}

		return Objects.equals(getCode(), other.getCode())
			&& Objects.equals(getName(), other.getName())
			&& (getCode() != null
				|| getName() != null);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("id", getId())
			.add("code", getCode())
			.add("name", getName())
			.add("maxDiscount", getMaxDiscount())
			.add("prestaShopId", getPrestaShopId())
			.add("prestaShopVersion", getPrestaShopVersion())
			.add("prestaShopUpdateDateTime", getPrestaShopUpdateDateTime())
			.omitNullValues()
			.toString();
	}
}
