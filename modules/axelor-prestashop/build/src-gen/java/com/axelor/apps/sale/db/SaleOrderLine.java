package com.axelor.apps.sale.db;

import java.math.BigDecimal;
import java.time.LocalDate;
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
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.axelor.apps.account.db.AnalyticDistributionTemplate;
import com.axelor.apps.account.db.AnalyticMoveLine;
import com.axelor.apps.account.db.TaxEquiv;
import com.axelor.apps.account.db.TaxLine;
import com.axelor.apps.base.db.Partner;
import com.axelor.apps.base.db.Product;
import com.axelor.apps.base.db.Unit;
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
@Table(name = "SALE_SALE_ORDER_LINE", indexes = { @Index(columnList = "fullName"), @Index(columnList = "sale_order"), @Index(columnList = "product"), @Index(columnList = "tax_line"), @Index(columnList = "tax_equiv"), @Index(columnList = "unit"), @Index(columnList = "supplier_partner"), @Index(columnList = "main_sale_order_line"), @Index(columnList = "analytic_distribution_template") })
@Track(fields = { @TrackField(name = "requestedReservedQty"), @TrackField(name = "reservedQty") })
public class SaleOrderLine extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SALE_SALE_ORDER_LINE_SEQ")
	@SequenceGenerator(name = "SALE_SALE_ORDER_LINE_SEQ", sequenceName = "SALE_SALE_ORDER_LINE_SEQ", allocationSize = 1)
	private Long id;

	@NameColumn
	@VirtualColumn
	@Access(AccessType.PROPERTY)
	private String fullName;

	@Widget(title = "Sale order")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private SaleOrder saleOrder;

	@Widget(title = "Seq.")
	private Integer sequence = 0;

	@Widget(title = "Product")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Product product;

	@Widget(title = "Qty")
	@Digits(integer = 10, fraction = 10)
	private BigDecimal qty = new BigDecimal("1");

	@Widget(hidden = true)
	@Digits(integer = 10, fraction = 10)
	private BigDecimal oldQty = BigDecimal.ZERO;

	@Widget(title = "Print subtotal / line")
	private Boolean isToPrintLineSubTotal = Boolean.FALSE;

	@Widget(title = "Displayed Product name", translatable = true)
	@NotNull
	private String productName;

	@Widget(title = "Unit price W.T.")
	@Digits(integer = 10, fraction = 10)
	private BigDecimal price = BigDecimal.ZERO;

	@Widget(title = "Unit price A.T.I.")
	@Digits(integer = 10, fraction = 10)
	private BigDecimal inTaxPrice = BigDecimal.ZERO;

	@Widget(title = "Unit price discounted")
	@Digits(integer = 10, fraction = 10)
	private BigDecimal priceDiscounted = BigDecimal.ZERO;

	@Widget(title = "Tax")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private TaxLine taxLine;

	@Widget(title = "Tax Equiv")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private TaxEquiv taxEquiv;

	@Widget(title = "Total W.T.")
	private BigDecimal exTaxTotal = BigDecimal.ZERO;

	@Widget(title = "Total A.T.I.")
	private BigDecimal inTaxTotal = BigDecimal.ZERO;

	@Widget(title = "Description")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String description;

	@Widget(title = "Unit")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Unit unit;

	@Widget(title = "Supplier")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Partner supplierPartner;

	@Widget(title = "Discount amount")
	@Digits(integer = 10, fraction = 10)
	private BigDecimal discountAmount = BigDecimal.ZERO;

	@Widget(title = "Discount type", selection = "base.price.list.line.amount.type.select")
	private Integer discountTypeSelect = 0;

	@Widget(title = "Estimated shipping date")
	private LocalDate estimatedDelivDate;

	@Widget(title = "Desired delivery date")
	private LocalDate desiredDelivDate;

	@Widget(title = "Delivered quantity")
	@Digits(integer = 10, fraction = 10)
	private BigDecimal deliveredQty = BigDecimal.ZERO;

	@Widget(hidden = true)
	private Boolean isComplementaryProductsUnhandledYet = Boolean.FALSE;

	@Widget(title = "Complementary products")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "saleOrderLine", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ComplementaryProductSelected> selectedComplementaryProductList;

	private String manualId;

	private String parentId;

	@Widget(title = "Complementary lines")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "mainSaleOrderLine", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SaleOrderLine> complementarySaleOrderLineList;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private SaleOrderLine mainSaleOrderLine;

	@Widget(title = "SubTotal cost price")
	private BigDecimal subTotalCostPrice = BigDecimal.ZERO;

	@Widget(title = "Sub Total gross profit")
	private BigDecimal subTotalGrossMargin = BigDecimal.ZERO;

	@Widget(title = "Sub margin rate")
	private BigDecimal subMarginRate = BigDecimal.ZERO;

	@Widget(title = "Sub Total markup")
	private BigDecimal subTotalMarkup = BigDecimal.ZERO;

	@Widget(title = "Total W.T. in company currency", hidden = true)
	private BigDecimal companyExTaxTotal = BigDecimal.ZERO;

	@Widget(title = "Unit cost price in company currency", hidden = true)
	private BigDecimal companyCostPrice = BigDecimal.ZERO;

	@Widget(title = "Total A.T.I. in company currency", hidden = true)
	private BigDecimal companyInTaxTotal = BigDecimal.ZERO;

	@Widget(title = "Total cost in company currency", hidden = true)
	private BigDecimal companyCostTotal = BigDecimal.ZERO;

	@Widget(title = "Type", selection = "line.type.select")
	private Integer typeSelect = 0;

	@Widget(title = "Show Total")
	private Boolean isShowTotal = Boolean.FALSE;

	@Widget(title = "Hide Unit Amounts")
	private Boolean isHideUnitAmounts = Boolean.FALSE;

	@Widget(title = "Freeze fields")
	@Transient
	private Boolean enableFreezeFields = Boolean.FALSE;

	@Widget(readonly = true)
	private Boolean discountsNeedReview = Boolean.FALSE;

	@Widget(title = "Discount derogation (%)")
	@DecimalMin("0")
	@DecimalMax("100")
	private BigDecimal discountDerogation = BigDecimal.ZERO;

	@Widget(title = "Supply method", selection = "product.sale.supply.select")
	private Integer saleSupplySelect = 0;

	@Widget(title = "Invoicing Date")
	private LocalDate invoicingDate;

	@Widget(title = "Amount invoiced W.T.", readonly = true)
	private BigDecimal amountInvoiced = BigDecimal.ZERO;

	@Widget(title = "Invoice controlled")
	private Boolean isInvoiceControlled = Boolean.FALSE;

	@Widget(title = "Analytic move lines")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "saleOrderLine", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<AnalyticMoveLine> analyticMoveLineList;

	@Widget(title = "Analytic distribution template")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private AnalyticDistributionTemplate analyticDistributionTemplate;

	@Widget(readonly = true)
	private Boolean invoiced = Boolean.FALSE;

	@Widget(title = "Picking Order Info")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String pickingOrderInfo;

	@Widget(title = "Available status")
	@Transient
	private String availableStatus;

	@Widget(title = "Available status", selection = "supplychain.sale.order.line.available.status.select")
	@Transient
	private Integer availableStatusSelect = 0;

	@Widget(title = "Allocated qty")
	@DecimalMin("0")
	@Digits(integer = 10, fraction = 10)
	private BigDecimal reservedQty = BigDecimal.ZERO;

	@Widget(title = "Requested reserved qty")
	@DecimalMin("0")
	@Digits(integer = 10, fraction = 10)
	private BigDecimal requestedReservedQty = BigDecimal.ZERO;

	@Widget(title = "Quantity requested", readonly = true)
	private Boolean isQtyRequested = Boolean.FALSE;

	@Widget(title = "Delivery state", readonly = true, selection = "sale.order.delivery.state")
	private Integer deliveryState = 0;

	@Widget(title = "Standard delay (days)")
	private Integer standardDelay = 0;

	@Column(nullable = true)
	private Integer prestaShopId;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public SaleOrderLine() {
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getFullName() {
		try {
			fullName = computeFullName();
		} catch (NullPointerException e) {
			Logger logger = LoggerFactory.getLogger(getClass());
			logger.error("NPE in function field: getFullName()", e);
		}
		return fullName;
	}

	protected String computeFullName() {
		String fullName = "";
		if(saleOrder != null && saleOrder.getSaleOrderSeq() != null){
			fullName += saleOrder.getSaleOrderSeq();
		}
		if(productName != null)  {
			fullName += "-";
			if(productName.length() > 100)  {
				fullName += productName.substring(1, 100);
			}
			else  {  fullName += productName;  }
		}
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public SaleOrder getSaleOrder() {
		return saleOrder;
	}

	public void setSaleOrder(SaleOrder saleOrder) {
		this.saleOrder = saleOrder;
	}

	public Integer getSequence() {
		return sequence == null ? 0 : sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public BigDecimal getQty() {
		return qty == null ? BigDecimal.ZERO : qty;
	}

	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}

	public BigDecimal getOldQty() {
		return oldQty == null ? BigDecimal.ZERO : oldQty;
	}

	public void setOldQty(BigDecimal oldQty) {
		this.oldQty = oldQty;
	}

	public Boolean getIsToPrintLineSubTotal() {
		return isToPrintLineSubTotal == null ? Boolean.FALSE : isToPrintLineSubTotal;
	}

	public void setIsToPrintLineSubTotal(Boolean isToPrintLineSubTotal) {
		this.isToPrintLineSubTotal = isToPrintLineSubTotal;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getPrice() {
		return price == null ? BigDecimal.ZERO : price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getInTaxPrice() {
		return inTaxPrice == null ? BigDecimal.ZERO : inTaxPrice;
	}

	public void setInTaxPrice(BigDecimal inTaxPrice) {
		this.inTaxPrice = inTaxPrice;
	}

	public BigDecimal getPriceDiscounted() {
		return priceDiscounted == null ? BigDecimal.ZERO : priceDiscounted;
	}

	public void setPriceDiscounted(BigDecimal priceDiscounted) {
		this.priceDiscounted = priceDiscounted;
	}

	public TaxLine getTaxLine() {
		return taxLine;
	}

	public void setTaxLine(TaxLine taxLine) {
		this.taxLine = taxLine;
	}

	public TaxEquiv getTaxEquiv() {
		return taxEquiv;
	}

	public void setTaxEquiv(TaxEquiv taxEquiv) {
		this.taxEquiv = taxEquiv;
	}

	public BigDecimal getExTaxTotal() {
		return exTaxTotal == null ? BigDecimal.ZERO : exTaxTotal;
	}

	public void setExTaxTotal(BigDecimal exTaxTotal) {
		this.exTaxTotal = exTaxTotal;
	}

	public BigDecimal getInTaxTotal() {
		return inTaxTotal == null ? BigDecimal.ZERO : inTaxTotal;
	}

	public void setInTaxTotal(BigDecimal inTaxTotal) {
		this.inTaxTotal = inTaxTotal;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public Partner getSupplierPartner() {
		return supplierPartner;
	}

	public void setSupplierPartner(Partner supplierPartner) {
		this.supplierPartner = supplierPartner;
	}

	public BigDecimal getDiscountAmount() {
		return discountAmount == null ? BigDecimal.ZERO : discountAmount;
	}

	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}

	public Integer getDiscountTypeSelect() {
		return discountTypeSelect == null ? 0 : discountTypeSelect;
	}

	public void setDiscountTypeSelect(Integer discountTypeSelect) {
		this.discountTypeSelect = discountTypeSelect;
	}

	public LocalDate getEstimatedDelivDate() {
		return estimatedDelivDate;
	}

	public void setEstimatedDelivDate(LocalDate estimatedDelivDate) {
		this.estimatedDelivDate = estimatedDelivDate;
	}

	public LocalDate getDesiredDelivDate() {
		return desiredDelivDate;
	}

	public void setDesiredDelivDate(LocalDate desiredDelivDate) {
		this.desiredDelivDate = desiredDelivDate;
	}

	public BigDecimal getDeliveredQty() {
		return deliveredQty == null ? BigDecimal.ZERO : deliveredQty;
	}

	public void setDeliveredQty(BigDecimal deliveredQty) {
		this.deliveredQty = deliveredQty;
	}

	public Boolean getIsComplementaryProductsUnhandledYet() {
		return isComplementaryProductsUnhandledYet == null ? Boolean.FALSE : isComplementaryProductsUnhandledYet;
	}

	public void setIsComplementaryProductsUnhandledYet(Boolean isComplementaryProductsUnhandledYet) {
		this.isComplementaryProductsUnhandledYet = isComplementaryProductsUnhandledYet;
	}

	public List<ComplementaryProductSelected> getSelectedComplementaryProductList() {
		return selectedComplementaryProductList;
	}

	public void setSelectedComplementaryProductList(List<ComplementaryProductSelected> selectedComplementaryProductList) {
		this.selectedComplementaryProductList = selectedComplementaryProductList;
	}

	/**
	 * Add the given {@link ComplementaryProductSelected} item to the {@code selectedComplementaryProductList}.
	 *
	 * <p>
	 * It sets {@code item.saleOrderLine = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addSelectedComplementaryProductListItem(ComplementaryProductSelected item) {
		if (getSelectedComplementaryProductList() == null) {
			setSelectedComplementaryProductList(new ArrayList<>());
		}
		getSelectedComplementaryProductList().add(item);
		item.setSaleOrderLine(this);
	}

	/**
	 * Remove the given {@link ComplementaryProductSelected} item from the {@code selectedComplementaryProductList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeSelectedComplementaryProductListItem(ComplementaryProductSelected item) {
		if (getSelectedComplementaryProductList() == null) {
			return;
		}
		getSelectedComplementaryProductList().remove(item);
	}

	/**
	 * Clear the {@code selectedComplementaryProductList} collection.
	 *
	 * <p>
	 * If you have to query {@link ComplementaryProductSelected} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearSelectedComplementaryProductList() {
		if (getSelectedComplementaryProductList() != null) {
			getSelectedComplementaryProductList().clear();
		}
	}

	public String getManualId() {
		return manualId;
	}

	public void setManualId(String manualId) {
		this.manualId = manualId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public List<SaleOrderLine> getComplementarySaleOrderLineList() {
		return complementarySaleOrderLineList;
	}

	public void setComplementarySaleOrderLineList(List<SaleOrderLine> complementarySaleOrderLineList) {
		this.complementarySaleOrderLineList = complementarySaleOrderLineList;
	}

	/**
	 * Add the given {@link SaleOrderLine} item to the {@code complementarySaleOrderLineList}.
	 *
	 * <p>
	 * It sets {@code item.mainSaleOrderLine = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addComplementarySaleOrderLineListItem(SaleOrderLine item) {
		if (getComplementarySaleOrderLineList() == null) {
			setComplementarySaleOrderLineList(new ArrayList<>());
		}
		getComplementarySaleOrderLineList().add(item);
		item.setMainSaleOrderLine(this);
	}

	/**
	 * Remove the given {@link SaleOrderLine} item from the {@code complementarySaleOrderLineList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeComplementarySaleOrderLineListItem(SaleOrderLine item) {
		if (getComplementarySaleOrderLineList() == null) {
			return;
		}
		getComplementarySaleOrderLineList().remove(item);
	}

	/**
	 * Clear the {@code complementarySaleOrderLineList} collection.
	 *
	 * <p>
	 * If you have to query {@link SaleOrderLine} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearComplementarySaleOrderLineList() {
		if (getComplementarySaleOrderLineList() != null) {
			getComplementarySaleOrderLineList().clear();
		}
	}

	public SaleOrderLine getMainSaleOrderLine() {
		return mainSaleOrderLine;
	}

	public void setMainSaleOrderLine(SaleOrderLine mainSaleOrderLine) {
		this.mainSaleOrderLine = mainSaleOrderLine;
	}

	public BigDecimal getSubTotalCostPrice() {
		return subTotalCostPrice == null ? BigDecimal.ZERO : subTotalCostPrice;
	}

	public void setSubTotalCostPrice(BigDecimal subTotalCostPrice) {
		this.subTotalCostPrice = subTotalCostPrice;
	}

	public BigDecimal getSubTotalGrossMargin() {
		return subTotalGrossMargin == null ? BigDecimal.ZERO : subTotalGrossMargin;
	}

	public void setSubTotalGrossMargin(BigDecimal subTotalGrossMargin) {
		this.subTotalGrossMargin = subTotalGrossMargin;
	}

	public BigDecimal getSubMarginRate() {
		return subMarginRate == null ? BigDecimal.ZERO : subMarginRate;
	}

	public void setSubMarginRate(BigDecimal subMarginRate) {
		this.subMarginRate = subMarginRate;
	}

	public BigDecimal getSubTotalMarkup() {
		return subTotalMarkup == null ? BigDecimal.ZERO : subTotalMarkup;
	}

	public void setSubTotalMarkup(BigDecimal subTotalMarkup) {
		this.subTotalMarkup = subTotalMarkup;
	}

	public BigDecimal getCompanyExTaxTotal() {
		return companyExTaxTotal == null ? BigDecimal.ZERO : companyExTaxTotal;
	}

	public void setCompanyExTaxTotal(BigDecimal companyExTaxTotal) {
		this.companyExTaxTotal = companyExTaxTotal;
	}

	public BigDecimal getCompanyCostPrice() {
		return companyCostPrice == null ? BigDecimal.ZERO : companyCostPrice;
	}

	public void setCompanyCostPrice(BigDecimal companyCostPrice) {
		this.companyCostPrice = companyCostPrice;
	}

	public BigDecimal getCompanyInTaxTotal() {
		return companyInTaxTotal == null ? BigDecimal.ZERO : companyInTaxTotal;
	}

	public void setCompanyInTaxTotal(BigDecimal companyInTaxTotal) {
		this.companyInTaxTotal = companyInTaxTotal;
	}

	public BigDecimal getCompanyCostTotal() {
		return companyCostTotal == null ? BigDecimal.ZERO : companyCostTotal;
	}

	public void setCompanyCostTotal(BigDecimal companyCostTotal) {
		this.companyCostTotal = companyCostTotal;
	}

	public Integer getTypeSelect() {
		return typeSelect == null ? 0 : typeSelect;
	}

	public void setTypeSelect(Integer typeSelect) {
		this.typeSelect = typeSelect;
	}

	public Boolean getIsShowTotal() {
		return isShowTotal == null ? Boolean.FALSE : isShowTotal;
	}

	public void setIsShowTotal(Boolean isShowTotal) {
		this.isShowTotal = isShowTotal;
	}

	public Boolean getIsHideUnitAmounts() {
		return isHideUnitAmounts == null ? Boolean.FALSE : isHideUnitAmounts;
	}

	public void setIsHideUnitAmounts(Boolean isHideUnitAmounts) {
		this.isHideUnitAmounts = isHideUnitAmounts;
	}

	public Boolean getEnableFreezeFields() {
		return enableFreezeFields == null ? Boolean.FALSE : enableFreezeFields;
	}

	public void setEnableFreezeFields(Boolean enableFreezeFields) {
		this.enableFreezeFields = enableFreezeFields;
	}

	public Boolean getDiscountsNeedReview() {
		return discountsNeedReview == null ? Boolean.FALSE : discountsNeedReview;
	}

	public void setDiscountsNeedReview(Boolean discountsNeedReview) {
		this.discountsNeedReview = discountsNeedReview;
	}

	public BigDecimal getDiscountDerogation() {
		return discountDerogation == null ? BigDecimal.ZERO : discountDerogation;
	}

	public void setDiscountDerogation(BigDecimal discountDerogation) {
		this.discountDerogation = discountDerogation;
	}

	public Integer getSaleSupplySelect() {
		return saleSupplySelect == null ? 0 : saleSupplySelect;
	}

	public void setSaleSupplySelect(Integer saleSupplySelect) {
		this.saleSupplySelect = saleSupplySelect;
	}

	public LocalDate getInvoicingDate() {
		return invoicingDate;
	}

	public void setInvoicingDate(LocalDate invoicingDate) {
		this.invoicingDate = invoicingDate;
	}

	public BigDecimal getAmountInvoiced() {
		return amountInvoiced == null ? BigDecimal.ZERO : amountInvoiced;
	}

	public void setAmountInvoiced(BigDecimal amountInvoiced) {
		this.amountInvoiced = amountInvoiced;
	}

	public Boolean getIsInvoiceControlled() {
		return isInvoiceControlled == null ? Boolean.FALSE : isInvoiceControlled;
	}

	public void setIsInvoiceControlled(Boolean isInvoiceControlled) {
		this.isInvoiceControlled = isInvoiceControlled;
	}

	public List<AnalyticMoveLine> getAnalyticMoveLineList() {
		return analyticMoveLineList;
	}

	public void setAnalyticMoveLineList(List<AnalyticMoveLine> analyticMoveLineList) {
		this.analyticMoveLineList = analyticMoveLineList;
	}

	/**
	 * Add the given {@link AnalyticMoveLine} item to the {@code analyticMoveLineList}.
	 *
	 * <p>
	 * It sets {@code item.saleOrderLine = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addAnalyticMoveLineListItem(AnalyticMoveLine item) {
		if (getAnalyticMoveLineList() == null) {
			setAnalyticMoveLineList(new ArrayList<>());
		}
		getAnalyticMoveLineList().add(item);
		item.setSaleOrderLine(this);
	}

	/**
	 * Remove the given {@link AnalyticMoveLine} item from the {@code analyticMoveLineList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeAnalyticMoveLineListItem(AnalyticMoveLine item) {
		if (getAnalyticMoveLineList() == null) {
			return;
		}
		getAnalyticMoveLineList().remove(item);
	}

	/**
	 * Clear the {@code analyticMoveLineList} collection.
	 *
	 * <p>
	 * If you have to query {@link AnalyticMoveLine} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearAnalyticMoveLineList() {
		if (getAnalyticMoveLineList() != null) {
			getAnalyticMoveLineList().clear();
		}
	}

	public AnalyticDistributionTemplate getAnalyticDistributionTemplate() {
		return analyticDistributionTemplate;
	}

	public void setAnalyticDistributionTemplate(AnalyticDistributionTemplate analyticDistributionTemplate) {
		this.analyticDistributionTemplate = analyticDistributionTemplate;
	}

	public Boolean getInvoiced() {
		return invoiced == null ? Boolean.FALSE : invoiced;
	}

	public void setInvoiced(Boolean invoiced) {
		this.invoiced = invoiced;
	}

	public String getPickingOrderInfo() {
		return pickingOrderInfo;
	}

	public void setPickingOrderInfo(String pickingOrderInfo) {
		this.pickingOrderInfo = pickingOrderInfo;
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

	public Boolean getIsQtyRequested() {
		return isQtyRequested == null ? Boolean.FALSE : isQtyRequested;
	}

	public void setIsQtyRequested(Boolean isQtyRequested) {
		this.isQtyRequested = isQtyRequested;
	}

	public Integer getDeliveryState() {
		return deliveryState == null ? 0 : deliveryState;
	}

	public void setDeliveryState(Integer deliveryState) {
		this.deliveryState = deliveryState;
	}

	public Integer getStandardDelay() {
		return standardDelay == null ? 0 : standardDelay;
	}

	public void setStandardDelay(Integer standardDelay) {
		this.standardDelay = standardDelay;
	}

	public Integer getPrestaShopId() {
		return prestaShopId;
	}

	public void setPrestaShopId(Integer prestaShopId) {
		this.prestaShopId = prestaShopId;
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
		if (!(obj instanceof SaleOrderLine)) return false;

		final SaleOrderLine other = (SaleOrderLine) obj;
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
			.add("qty", getQty())
			.add("oldQty", getOldQty())
			.add("isToPrintLineSubTotal", getIsToPrintLineSubTotal())
			.add("productName", getProductName())
			.add("price", getPrice())
			.add("inTaxPrice", getInTaxPrice())
			.add("priceDiscounted", getPriceDiscounted())
			.add("exTaxTotal", getExTaxTotal())
			.add("inTaxTotal", getInTaxTotal())
			.add("discountAmount", getDiscountAmount())
			.omitNullValues()
			.toString();
	}
}
