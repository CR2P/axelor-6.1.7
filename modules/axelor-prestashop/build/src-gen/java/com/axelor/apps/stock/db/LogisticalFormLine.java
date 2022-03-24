package com.axelor.apps.stock.db;

import java.math.BigDecimal;
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
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import com.axelor.apps.sale.db.SaleOrder;
import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@Table(name = "STOCK_LOGISTICAL_FORM_LINE", uniqueConstraints = { @UniqueConstraint(columnNames = { "typeSelect", "parcelPalletNumber", "logistical_form" }) }, indexes = { @Index(columnList = "logistical_form"), @Index(columnList = "stock_move_line"), @Index(columnList = "sale_order") })
public class LogisticalFormLine extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STOCK_LOGISTICAL_FORM_LINE_SEQ")
	@SequenceGenerator(name = "STOCK_LOGISTICAL_FORM_LINE_SEQ", sequenceName = "STOCK_LOGISTICAL_FORM_LINE_SEQ", allocationSize = 1)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private LogisticalForm logisticalForm;

	@Widget(title = "Seq.")
	private Integer sequence = 0;

	@Widget(title = "Type", selection = "logistical.form.line.type.select")
	@NotNull
	private Integer typeSelect = 0;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private StockMoveLine stockMoveLine;

	@Digits(integer = 10, fraction = 10)
	@Column(nullable = true)
	private BigDecimal qty;

	@Widget(title = "Parcel/pallet number")
	@Column(nullable = true)
	private Integer parcelPalletNumber;

	@Digits(integer = 17, fraction = 2)
	@Column(nullable = true)
	private BigDecimal grossMass;

	@Widget(title = "Dimensions (cm)")
	@Column(nullable = true)
	private String dimensions;

	@Widget(readonly = true)
	@Digits(integer = 10, fraction = 10)
	@Column(nullable = true)
	private BigDecimal unitNetMass;

	@Widget(title = "Sale Order")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private SaleOrder saleOrder;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public LogisticalFormLine() {
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public LogisticalForm getLogisticalForm() {
		return logisticalForm;
	}

	public void setLogisticalForm(LogisticalForm logisticalForm) {
		this.logisticalForm = logisticalForm;
	}

	public Integer getSequence() {
		return sequence == null ? 0 : sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public Integer getTypeSelect() {
		return typeSelect == null ? 0 : typeSelect;
	}

	public void setTypeSelect(Integer typeSelect) {
		this.typeSelect = typeSelect;
	}

	public StockMoveLine getStockMoveLine() {
		return stockMoveLine;
	}

	public void setStockMoveLine(StockMoveLine stockMoveLine) {
		this.stockMoveLine = stockMoveLine;
	}

	public BigDecimal getQty() {
		return qty;
	}

	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}

	public Integer getParcelPalletNumber() {
		return parcelPalletNumber;
	}

	public void setParcelPalletNumber(Integer parcelPalletNumber) {
		this.parcelPalletNumber = parcelPalletNumber;
	}

	public BigDecimal getGrossMass() {
		return grossMass;
	}

	public void setGrossMass(BigDecimal grossMass) {
		this.grossMass = grossMass;
	}

	public String getDimensions() {
		return dimensions;
	}

	public void setDimensions(String dimensions) {
		this.dimensions = dimensions;
	}

	public BigDecimal getUnitNetMass() {
		return unitNetMass;
	}

	public void setUnitNetMass(BigDecimal unitNetMass) {
		this.unitNetMass = unitNetMass;
	}

	public SaleOrder getSaleOrder() {
		return saleOrder;
	}

	public void setSaleOrder(SaleOrder saleOrder) {
		this.saleOrder = saleOrder;
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
		if (!(obj instanceof LogisticalFormLine)) return false;

		final LogisticalFormLine other = (LogisticalFormLine) obj;
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
			.add("sequence", getSequence())
			.add("typeSelect", getTypeSelect())
			.add("qty", getQty())
			.add("parcelPalletNumber", getParcelPalletNumber())
			.add("grossMass", getGrossMass())
			.add("dimensions", getDimensions())
			.add("unitNetMass", getUnitNetMass())
			.omitNullValues()
			.toString();
	}
}
