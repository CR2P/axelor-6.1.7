package com.axelor.apps.stock.db;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.AccessType;
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
import javax.persistence.Transient;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.axelor.apps.base.db.Country;
import com.axelor.apps.base.db.Product;
import com.axelor.apps.base.db.Unit;
import com.axelor.apps.purchase.db.PurchaseOrderLine;
import com.axelor.apps.sale.db.SaleOrderLine;
import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.NameColumn;
import com.axelor.db.annotations.Track;
import com.axelor.db.annotations.TrackEvent;
import com.axelor.db.annotations.TrackField;
import com.axelor.db.annotations.VirtualColumn;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "STOCK_STOCK_MOVE_LINE", indexes = { @Index(columnList = "stock_move"), @Index(columnList = "planned_stock_move"), @Index(columnList = "product"), @Index(columnList = "unit"), @Index(columnList = "tracking_number"), @Index(columnList = "product_model"), @Index(columnList = "productName"), @Index(columnList = "name"), @Index(columnList = "customs_code_nomenclature"), @Index(columnList = "country_of_origin"), @Index(columnList = "sale_order_line"), @Index(columnList = "purchase_order_line") })
@Track(fields = { @TrackField(name = "realQty"), @TrackField(name = "requestedReservedQty"), @TrackField(name = "reservedQty") })
public class StockMoveLine extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STOCK_STOCK_MOVE_LINE_SEQ")
	@SequenceGenerator(name = "STOCK_STOCK_MOVE_LINE_SEQ", sequenceName = "STOCK_STOCK_MOVE_LINE_SEQ", allocationSize = 1)
	private Long id;

	@Widget(title = "Stock move", readonly = true)
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private StockMove stockMove;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private StockMove plannedStockMove;

	@Widget(title = "Product")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Product product;

	@Widget(title = "Filter on available products")
	@Transient
	private Boolean filterOnAvailableProducts = Boolean.TRUE;

	@Widget(title = "Expected Qty")
	@DecimalMin("0")
	@Digits(integer = 10, fraction = 10)
	private BigDecimal qty = BigDecimal.ZERO;

	@Widget(title = "Real Qty")
	@DecimalMin("0")
	@Digits(integer = 10, fraction = 10)
	private BigDecimal realQty = BigDecimal.ZERO;

	@Widget(hidden = true)
	@Digits(integer = 10, fraction = 10)
	private BigDecimal oldQty = BigDecimal.ZERO;

	@Widget(title = "Available qty")
	@Digits(integer = 10, fraction = 10)
	@Transient
	private BigDecimal availableQty = BigDecimal.ZERO;

	@Widget(title = "Available qty for product")
	@Digits(integer = 10, fraction = 10)
	@Transient
	private BigDecimal availableQtyForProduct = BigDecimal.ZERO;

	@Widget(title = "Availability")
	@Transient
	private String availableStatus;

	@Widget(title = "Status", selection = "stock.move.line.available.status.select")
	@Transient
	private Integer availableStatusSelect = 0;

	@Widget(title = "Unit")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Unit unit;

	@Widget(title = "Net mass")
	@Digits(integer = 10, fraction = 10)
	private BigDecimal netMass = BigDecimal.ZERO;

	@Widget(title = "Total net mass")
	private BigDecimal totalNetMass = BigDecimal.ZERO;

	@Widget(title = "Tracking Nbr.")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private TrackingNumber trackingNumber;

	@Widget(title = "Conformity", selection = "stock.move.line.conformity.select")
	private Integer conformitySelect = 0;

	@Widget(title = "Shipped qty")
	@Digits(integer = 10, fraction = 10)
	private BigDecimal shippedQty = BigDecimal.ZERO;

	@Widget(title = "Shipped date")
	private LocalDate shippedDate;

	@Widget(title = "Product model")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Product productModel;

	@Widget(title = "Title")
	@NameColumn
	@NotNull
	private String productName;

	@Widget(title = "Description")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String description;

	@Widget(title = "Unit price")
	@Digits(integer = 10, fraction = 10)
	private BigDecimal unitPriceUntaxed = BigDecimal.ZERO;

	@Widget(title = "Unit price")
	@Digits(integer = 10, fraction = 10)
	private BigDecimal unitPriceTaxed = BigDecimal.ZERO;

	@Widget(title = "Company Unit price W.T.")
	@Digits(integer = 10, fraction = 10)
	private BigDecimal companyUnitPriceUntaxed = BigDecimal.ZERO;

	@Widget(title = "Price used for WAP", readonly = true)
	@Digits(integer = 10, fraction = 10)
	@Column(nullable = true)
	private BigDecimal wapPrice;

	@Widget(title = "Purchase price", help = "Purchase price in company currency, used for declaration of exchange.")
	@Digits(integer = 10, fraction = 10)
	private BigDecimal companyPurchasePrice = BigDecimal.ZERO;

	@Widget(title = "Product type", selection = "product.product.type.select")
	private String productTypeSelect;

	@Widget(title = "Seq.")
	private Integer sequence = 0;

	@Widget(title = "Ref.")
	@VirtualColumn
	@Access(AccessType.PROPERTY)
	private String name;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private CustomsCodeNomenclature customsCodeNomenclature;

	private String customsCode;

	@Widget(title = "Logistical form lines")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "stockMoveLine", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<LogisticalFormLine> logisticalFormLineList;

	@Widget(title = "Type", selection = "line.type.select")
	private Integer lineTypeSelect = 0;

	@Basic
	@Type(type = "com.axelor.db.hibernate.type.ValueEnumType")
	private Regime regime;

	@Basic
	@Type(type = "com.axelor.db.hibernate.type.ValueEnumType")
	private NatureOfTransaction natureOfTransaction;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Country countryOfOrigin;

	@Widget(title = "Sales order line")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private SaleOrderLine saleOrderLine;

	@Widget(title = "Purchase order line")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private PurchaseOrderLine purchaseOrderLine;

	@Widget(title = "Allocated Qty")
	@Digits(integer = 10, fraction = 10)
	private BigDecimal reservedQty = BigDecimal.ZERO;

	@Widget(title = "Requested reserved qty")
	@Digits(integer = 10, fraction = 10)
	private BigDecimal requestedReservedQty = BigDecimal.ZERO;

	@Widget(title = "Invoiced Qty")
	@Digits(integer = 10, fraction = 10)
	private BigDecimal qtyInvoiced = BigDecimal.ZERO;

	@Widget(title = "Quantity requested", readonly = true)
	private Boolean isQtyRequested = Boolean.FALSE;

	@Transient
	private Boolean isMergedStockMoveLine = Boolean.FALSE;

	@Widget(readonly = true)
	private LocalDateTime reservationDateTime;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public StockMoveLine() {
	}

	public StockMoveLine(String name) {
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

	public StockMove getStockMove() {
		return stockMove;
	}

	public void setStockMove(StockMove stockMove) {
		this.stockMove = stockMove;
	}

	public StockMove getPlannedStockMove() {
		return plannedStockMove;
	}

	public void setPlannedStockMove(StockMove plannedStockMove) {
		this.plannedStockMove = plannedStockMove;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Boolean getFilterOnAvailableProducts() {
		return filterOnAvailableProducts == null ? Boolean.FALSE : filterOnAvailableProducts;
	}

	public void setFilterOnAvailableProducts(Boolean filterOnAvailableProducts) {
		this.filterOnAvailableProducts = filterOnAvailableProducts;
	}

	public BigDecimal getQty() {
		return qty == null ? BigDecimal.ZERO : qty;
	}

	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}

	public BigDecimal getRealQty() {
		return realQty == null ? BigDecimal.ZERO : realQty;
	}

	public void setRealQty(BigDecimal realQty) {
		this.realQty = realQty;
	}

	public BigDecimal getOldQty() {
		return oldQty == null ? BigDecimal.ZERO : oldQty;
	}

	public void setOldQty(BigDecimal oldQty) {
		this.oldQty = oldQty;
	}

	public BigDecimal getAvailableQty() {
		return availableQty == null ? BigDecimal.ZERO : availableQty;
	}

	public void setAvailableQty(BigDecimal availableQty) {
		this.availableQty = availableQty;
	}

	public BigDecimal getAvailableQtyForProduct() {
		return availableQtyForProduct == null ? BigDecimal.ZERO : availableQtyForProduct;
	}

	public void setAvailableQtyForProduct(BigDecimal availableQtyForProduct) {
		this.availableQtyForProduct = availableQtyForProduct;
	}

	public String getAvailableStatus() {
		return availableStatus;
	}

	public void setAvailableStatus(String availableStatus) {
		this.availableStatus = availableStatus;
	}

	public Integer getAvailableStatusSelect() {
		return availableStatusSelect == null ? 0 : availableStatusSelect;
	}

	public void setAvailableStatusSelect(Integer availableStatusSelect) {
		this.availableStatusSelect = availableStatusSelect;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public BigDecimal getNetMass() {
		return netMass == null ? BigDecimal.ZERO : netMass;
	}

	public void setNetMass(BigDecimal netMass) {
		this.netMass = netMass;
	}

	public BigDecimal getTotalNetMass() {
		return totalNetMass == null ? BigDecimal.ZERO : totalNetMass;
	}

	public void setTotalNetMass(BigDecimal totalNetMass) {
		this.totalNetMass = totalNetMass;
	}

	public TrackingNumber getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(TrackingNumber trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public Integer getConformitySelect() {
		return conformitySelect == null ? 0 : conformitySelect;
	}

	public void setConformitySelect(Integer conformitySelect) {
		this.conformitySelect = conformitySelect;
	}

	public BigDecimal getShippedQty() {
		return shippedQty == null ? BigDecimal.ZERO : shippedQty;
	}

	public void setShippedQty(BigDecimal shippedQty) {
		this.shippedQty = shippedQty;
	}

	public LocalDate getShippedDate() {
		return shippedDate;
	}

	public void setShippedDate(LocalDate shippedDate) {
		this.shippedDate = shippedDate;
	}

	public Product getProductModel() {
		return productModel;
	}

	public void setProductModel(Product productModel) {
		this.productModel = productModel;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getUnitPriceUntaxed() {
		return unitPriceUntaxed == null ? BigDecimal.ZERO : unitPriceUntaxed;
	}

	public void setUnitPriceUntaxed(BigDecimal unitPriceUntaxed) {
		this.unitPriceUntaxed = unitPriceUntaxed;
	}

	public BigDecimal getUnitPriceTaxed() {
		return unitPriceTaxed == null ? BigDecimal.ZERO : unitPriceTaxed;
	}

	public void setUnitPriceTaxed(BigDecimal unitPriceTaxed) {
		this.unitPriceTaxed = unitPriceTaxed;
	}

	public BigDecimal getCompanyUnitPriceUntaxed() {
		return companyUnitPriceUntaxed == null ? BigDecimal.ZERO : companyUnitPriceUntaxed;
	}

	public void setCompanyUnitPriceUntaxed(BigDecimal companyUnitPriceUntaxed) {
		this.companyUnitPriceUntaxed = companyUnitPriceUntaxed;
	}

	public BigDecimal getWapPrice() {
		return wapPrice;
	}

	public void setWapPrice(BigDecimal wapPrice) {
		this.wapPrice = wapPrice;
	}

	/**
	 * Purchase price in company currency, used for declaration of exchange.
	 *
	 * @return the property value
	 */
	public BigDecimal getCompanyPurchasePrice() {
		return companyPurchasePrice == null ? BigDecimal.ZERO : companyPurchasePrice;
	}

	public void setCompanyPurchasePrice(BigDecimal companyPurchasePrice) {
		this.companyPurchasePrice = companyPurchasePrice;
	}

	public String getProductTypeSelect() {
		return productTypeSelect;
	}

	public void setProductTypeSelect(String productTypeSelect) {
		this.productTypeSelect = productTypeSelect;
	}

	public Integer getSequence() {
		return sequence == null ? 0 : sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public String getName() {
		try {
			name = computeName();
		} catch (NullPointerException e) {
			Logger logger = LoggerFactory.getLogger(getClass());
			logger.error("NPE in function field: getName()", e);
		}
		return name;
	}

	protected String computeName() {
		if (stockMove != null && stockMove.getStockMoveSeq() != null){
			return stockMove.getStockMoveSeq()+ "-" +Integer.toString(sequence);
		}
		else {
			return Integer.toString(sequence);
		 }
	}

	public void setName(String name) {
		this.name = name;
	}

	public CustomsCodeNomenclature getCustomsCodeNomenclature() {
		return customsCodeNomenclature;
	}

	public void setCustomsCodeNomenclature(CustomsCodeNomenclature customsCodeNomenclature) {
		this.customsCodeNomenclature = customsCodeNomenclature;
	}

	public String getCustomsCode() {
		return customsCode;
	}

	public void setCustomsCode(String customsCode) {
		this.customsCode = customsCode;
	}

	public List<LogisticalFormLine> getLogisticalFormLineList() {
		return logisticalFormLineList;
	}

	public void setLogisticalFormLineList(List<LogisticalFormLine> logisticalFormLineList) {
		this.logisticalFormLineList = logisticalFormLineList;
	}

	/**
	 * Add the given {@link LogisticalFormLine} item to the {@code logisticalFormLineList}.
	 *
	 * <p>
	 * It sets {@code item.stockMoveLine = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addLogisticalFormLineListItem(LogisticalFormLine item) {
		if (getLogisticalFormLineList() == null) {
			setLogisticalFormLineList(new ArrayList<>());
		}
		getLogisticalFormLineList().add(item);
		item.setStockMoveLine(this);
	}

	/**
	 * Remove the given {@link LogisticalFormLine} item from the {@code logisticalFormLineList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeLogisticalFormLineListItem(LogisticalFormLine item) {
		if (getLogisticalFormLineList() == null) {
			return;
		}
		getLogisticalFormLineList().remove(item);
	}

	/**
	 * Clear the {@code logisticalFormLineList} collection.
	 *
	 * <p>
	 * If you have to query {@link LogisticalFormLine} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearLogisticalFormLineList() {
		if (getLogisticalFormLineList() != null) {
			getLogisticalFormLineList().clear();
		}
	}

	public Integer getLineTypeSelect() {
		return lineTypeSelect == null ? 0 : lineTypeSelect;
	}

	public void setLineTypeSelect(Integer lineTypeSelect) {
		this.lineTypeSelect = lineTypeSelect;
	}

	public Regime getRegime() {
		return regime;
	}

	public void setRegime(Regime regime) {
		this.regime = regime;
	}

	public NatureOfTransaction getNatureOfTransaction() {
		return natureOfTransaction;
	}

	public void setNatureOfTransaction(NatureOfTransaction natureOfTransaction) {
		this.natureOfTransaction = natureOfTransaction;
	}

	public Country getCountryOfOrigin() {
		return countryOfOrigin;
	}

	public void setCountryOfOrigin(Country countryOfOrigin) {
		this.countryOfOrigin = countryOfOrigin;
	}

	public SaleOrderLine getSaleOrderLine() {
		return saleOrderLine;
	}

	public void setSaleOrderLine(SaleOrderLine saleOrderLine) {
		this.saleOrderLine = saleOrderLine;
	}

	public PurchaseOrderLine getPurchaseOrderLine() {
		return purchaseOrderLine;
	}

	public void setPurchaseOrderLine(PurchaseOrderLine purchaseOrderLine) {
		this.purchaseOrderLine = purchaseOrderLine;
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

	public BigDecimal getQtyInvoiced() {
		return qtyInvoiced == null ? BigDecimal.ZERO : qtyInvoiced;
	}

	public void setQtyInvoiced(BigDecimal qtyInvoiced) {
		this.qtyInvoiced = qtyInvoiced;
	}

	public Boolean getIsQtyRequested() {
		return isQtyRequested == null ? Boolean.FALSE : isQtyRequested;
	}

	public void setIsQtyRequested(Boolean isQtyRequested) {
		this.isQtyRequested = isQtyRequested;
	}

	public Boolean getIsMergedStockMoveLine() {
		return isMergedStockMoveLine == null ? Boolean.FALSE : isMergedStockMoveLine;
	}

	public void setIsMergedStockMoveLine(Boolean isMergedStockMoveLine) {
		this.isMergedStockMoveLine = isMergedStockMoveLine;
	}

	public LocalDateTime getReservationDateTime() {
		return reservationDateTime;
	}

	public void setReservationDateTime(LocalDateTime reservationDateTime) {
		this.reservationDateTime = reservationDateTime;
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
		if (!(obj instanceof StockMoveLine)) return false;

		final StockMoveLine other = (StockMoveLine) obj;
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
			.add("filterOnAvailableProducts", getFilterOnAvailableProducts())
			.add("qty", getQty())
			.add("realQty", getRealQty())
			.add("oldQty", getOldQty())
			.add("availableQty", getAvailableQty())
			.add("availableQtyForProduct", getAvailableQtyForProduct())
			.add("availableStatus", getAvailableStatus())
			.add("availableStatusSelect", getAvailableStatusSelect())
			.add("netMass", getNetMass())
			.add("totalNetMass", getTotalNetMass())
			.add("conformitySelect", getConformitySelect())
			.omitNullValues()
			.toString();
	}
}
