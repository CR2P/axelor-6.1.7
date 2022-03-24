package com.axelor.apps.stock.db;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
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
import javax.persistence.Transient;
import javax.validation.constraints.Digits;

import org.hibernate.annotations.Type;

import com.axelor.apps.base.db.Product;
import com.axelor.apps.base.db.Unit;
import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.Track;
import com.axelor.db.annotations.TrackEvent;
import com.axelor.db.annotations.TrackField;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@Table(name = "STOCK_STOCK_LOCATION_LINE", indexes = { @Index(columnList = "stock_location"), @Index(columnList = "product"), @Index(columnList = "unit"), @Index(columnList = "tracking_number"), @Index(columnList = "details_stock_location") })
@Track(fields = { @TrackField(name = "requestedReservedQty"), @TrackField(name = "reservedQty") })
public class StockLocationLine extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STOCK_STOCK_LOCATION_LINE_SEQ")
	@SequenceGenerator(name = "STOCK_STOCK_LOCATION_LINE_SEQ", sequenceName = "STOCK_STOCK_LOCATION_LINE_SEQ", allocationSize = 1)
	private Long id;

	@Widget(title = "Stock location", readonly = true)
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private StockLocation stockLocation;

	@Widget(title = "Product", readonly = true)
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Product product;

	@Widget(title = "Unit")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Unit unit;

	@Widget(title = "Current Qty", readonly = true)
	@Digits(integer = 10, fraction = 10)
	private BigDecimal currentQty = BigDecimal.ZERO;

	@Widget(title = "Future Qty", readonly = true)
	@Digits(integer = 10, fraction = 10)
	private BigDecimal futureQty = BigDecimal.ZERO;

	@Widget(title = "Average Price", readonly = true)
	@Digits(integer = 10, fraction = 10)
	private BigDecimal avgPrice = BigDecimal.ZERO;

	@Transient
	private Boolean isAvgPriceChanged = Boolean.FALSE;

	@Widget(title = "Last Future Stock Move", readonly = true)
	private LocalDate lastFutureStockMoveDate;

	@Widget(title = "Rack")
	private String rack;

	@Widget(title = "Tracking Nbr", readonly = true)
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private TrackingNumber trackingNumber;

	@Widget(title = "Stock location details", readonly = true)
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private StockLocation detailsStockLocation;

	@Digits(integer = 10, fraction = 10)
	private BigDecimal lastInventoryRealQty = BigDecimal.ZERO;

	@Widget(title = "Last inventory date")
	private ZonedDateTime lastInventoryDateT;

	@Widget(title = "Allocated qty")
	@Digits(integer = 10, fraction = 10)
	private BigDecimal reservedQty = BigDecimal.ZERO;

	@Widget(title = "Requested reserved qty")
	@Digits(integer = 10, fraction = 10)
	private BigDecimal requestedReservedQty = BigDecimal.ZERO;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public StockLocationLine() {
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public StockLocation getStockLocation() {
		return stockLocation;
	}

	public void setStockLocation(StockLocation stockLocation) {
		this.stockLocation = stockLocation;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public BigDecimal getCurrentQty() {
		return currentQty == null ? BigDecimal.ZERO : currentQty;
	}

	public void setCurrentQty(BigDecimal currentQty) {
		this.currentQty = currentQty;
	}

	public BigDecimal getFutureQty() {
		return futureQty == null ? BigDecimal.ZERO : futureQty;
	}

	public void setFutureQty(BigDecimal futureQty) {
		this.futureQty = futureQty;
	}

	public BigDecimal getAvgPrice() {
		return avgPrice == null ? BigDecimal.ZERO : avgPrice;
	}

	public void setAvgPrice(BigDecimal avgPrice) {
		this.avgPrice = avgPrice;
	}

	public Boolean getIsAvgPriceChanged() {
		return isAvgPriceChanged == null ? Boolean.FALSE : isAvgPriceChanged;
	}

	public void setIsAvgPriceChanged(Boolean isAvgPriceChanged) {
		this.isAvgPriceChanged = isAvgPriceChanged;
	}

	public LocalDate getLastFutureStockMoveDate() {
		return lastFutureStockMoveDate;
	}

	public void setLastFutureStockMoveDate(LocalDate lastFutureStockMoveDate) {
		this.lastFutureStockMoveDate = lastFutureStockMoveDate;
	}

	public String getRack() {
		return rack;
	}

	public void setRack(String rack) {
		this.rack = rack;
	}

	public TrackingNumber getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(TrackingNumber trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public StockLocation getDetailsStockLocation() {
		return detailsStockLocation;
	}

	public void setDetailsStockLocation(StockLocation detailsStockLocation) {
		this.detailsStockLocation = detailsStockLocation;
	}

	public BigDecimal getLastInventoryRealQty() {
		return lastInventoryRealQty == null ? BigDecimal.ZERO : lastInventoryRealQty;
	}

	public void setLastInventoryRealQty(BigDecimal lastInventoryRealQty) {
		this.lastInventoryRealQty = lastInventoryRealQty;
	}

	public ZonedDateTime getLastInventoryDateT() {
		return lastInventoryDateT;
	}

	public void setLastInventoryDateT(ZonedDateTime lastInventoryDateT) {
		this.lastInventoryDateT = lastInventoryDateT;
	}

	public BigDecimal getReservedQty() {
		return reservedQty == null ? BigDecimal.ZERO : reservedQty;
	}

	public void setReservedQty(BigDecimal reservedQty) {
		this.reservedQty = reservedQty;
	}

	public BigDecimal getRequestedReservedQty() {
		return requestedReservedQty == null ? BigDecimal.ZERO : requestedReservedQty;
	}

	public void setRequestedReservedQty(BigDecimal requestedReservedQty) {
		this.requestedReservedQty = requestedReservedQty;
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
		if (!(obj instanceof StockLocationLine)) return false;

		final StockLocationLine other = (StockLocationLine) obj;
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
			.add("currentQty", getCurrentQty())
			.add("futureQty", getFutureQty())
			.add("avgPrice", getAvgPrice())
			.add("isAvgPriceChanged", getIsAvgPriceChanged())
			.add("lastFutureStockMoveDate", getLastFutureStockMoveDate())
			.add("rack", getRack())
			.add("lastInventoryRealQty", getLastInventoryRealQty())
			.add("lastInventoryDateT", getLastInventoryDateT())
			.add("reservedQty", getReservedQty())
			.add("requestedReservedQty", getRequestedReservedQty())
			.omitNullValues()
			.toString();
	}
}
