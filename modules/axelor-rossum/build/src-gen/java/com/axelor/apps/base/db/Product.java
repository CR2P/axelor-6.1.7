package com.axelor.apps.base.db;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;

import org.hibernate.annotations.Type;

import com.axelor.apps.account.db.AccountManagement;
import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.EqualsInclude;
import com.axelor.db.annotations.NameColumn;
import com.axelor.db.annotations.Track;
import com.axelor.db.annotations.TrackEvent;
import com.axelor.db.annotations.TrackField;
import com.axelor.db.annotations.TrackMessage;
import com.axelor.db.annotations.Widget;
import com.axelor.meta.db.MetaFile;
import com.google.common.base.MoreObjects;

@Entity
@Table(name = "BASE_PRODUCT", uniqueConstraints = { @UniqueConstraint(columnNames = { "product", "company" }) }, indexes = { @Index(columnList = "name"), @Index(columnList = "picture"), @Index(columnList = "product_category"), @Index(columnList = "product_family"), @Index(columnList = "unit"), @Index(columnList = "sale_currency"), @Index(columnList = "purchase_currency"), @Index(columnList = "product_variant_config"), @Index(columnList = "product_variant"), @Index(columnList = "parent_product"), @Index(columnList = "default_supplier_partner"), @Index(columnList = "bar_code"), @Index(columnList = "barcode_type_config"), @Index(columnList = "fullName"), @Index(columnList = "mass_unit"), @Index(columnList = "length_unit") })
@Track(on = TrackEvent.UPDATE, fields = { @TrackField(name = "name"), @TrackField(name = "code"), @TrackField(name = "productCategory"), @TrackField(name = "productFamily"), @TrackField(name = "saleSupplySelect"), @TrackField(name = "sellable"), @TrackField(name = "salePrice"), @TrackField(name = "saleCurrency"), @TrackField(name = "unit"), @TrackField(name = "startDate"), @TrackField(name = "endDate"), @TrackField(name = "purchasable"), @TrackField(name = "purchasePrice"), @TrackField(name = "defaultSupplierPartner"), @TrackField(name = "purchaseCurrency"), @TrackField(name = "supplierDeliveryTime"), @TrackField(name = "costPrice"), @TrackField(name = "managPriceCoef"), @TrackField(name = "costTypeSelect"), @TrackField(name = "hasWarranty"), @TrackField(name = "warrantyNbrOfMonths"), @TrackField(name = "isPerishable"), @TrackField(name = "perishableNbrOfMonths") }, messages = { @TrackMessage(message = "Product updated", condition = "true", on = TrackEvent.UPDATE) })
public class Product extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BASE_PRODUCT_SEQ")
	@SequenceGenerator(name = "BASE_PRODUCT_SEQ", sequenceName = "BASE_PRODUCT_SEQ", allocationSize = 1)
	private Long id;

	@Widget(title = "Name", translatable = true)
	private String name;

	@EqualsInclude
	@Widget(title = "Serial Nbr")
	@Column(unique = true)
	private String serialNumber;

	@EqualsInclude
	@Widget(title = "Code")
	@Column(unique = true)
	private String code;

	@Widget(title = "Description", translatable = true)
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String description;

	@Widget(title = "Internal description")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String internalDescription;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private MetaFile picture;

	@Widget(title = "Product category", massUpdate = true)
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private ProductCategory productCategory;

	@Widget(title = "Accounting family", massUpdate = true)
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private ProductFamily productFamily;

	@Widget(title = "Unit", massUpdate = true)
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Unit unit;

	@Widget(title = "Sale supply default method on sale order", selection = "product.sale.supply.select", massUpdate = true)
	private Integer saleSupplySelect = 0;

	@Widget(title = "Type", selection = "product.product.type.select", massUpdate = true)
	private String productTypeSelect;

	@Widget(title = "Procurement method", selection = "product.procurement.method.select")
	private String procurementMethodSelect;

	@Widget(title = "Prototype")
	private Boolean isPrototype = Boolean.FALSE;

	@Widget(title = "Unrenewed")
	private Boolean isUnrenewed = Boolean.FALSE;

	@Widget(title = "Product Subtype", selection = "product.sub.type.product.select")
	private Integer productSubTypeSelect = 0;

	@Widget(title = "Inventory type", selection = "product.inventory.type.select")
	private Integer inventoryTypeSelect = 0;

	@Widget(title = "Sale price W.T.", massUpdate = true)
	@Digits(integer = 10, fraction = 10)
	private BigDecimal salePrice = BigDecimal.ZERO;

	@Widget(title = "Sale currency")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Currency saleCurrency;

	@Widget(title = "Purchase price W.T.", massUpdate = true)
	@DecimalMin("0")
	@Digits(integer = 10, fraction = 10)
	private BigDecimal purchasePrice = BigDecimal.ZERO;

	@Widget(title = "Last purchase date")
	private LocalDate lastPurchaseDate;

	@Widget(title = "Last purchase price W.T.", copyable = false)
	@Digits(integer = 10, fraction = 10)
	private BigDecimal lastPurchasePrice = BigDecimal.ZERO;

	@Widget(title = "Purchase / Cost currency")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Currency purchaseCurrency;

	@Widget(title = "Update sale price from cost price")
	private Boolean autoUpdateSalePrice = Boolean.FALSE;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PriceListLine> priceListLineList;

	@Widget(title = "Cost price", massUpdate = true)
	@DecimalMin("0")
	@Digits(integer = 10, fraction = 10)
	private BigDecimal costPrice = BigDecimal.ZERO;

	@Widget(title = "Management coef.")
	@DecimalMin("0")
	private BigDecimal managPriceCoef = BigDecimal.ZERO;

	@Widget(title = "Shipping Coef.")
	@DecimalMin("0")
	private BigDecimal shippingCoef = BigDecimal.ZERO;

	@Widget(title = "Define the shipping coef by partner")
	private Boolean defShipCoefByPartner = Boolean.FALSE;

	@Widget(title = "Product launch Date")
	private LocalDate startDate;

	@Widget(title = "Product pulled off market Date")
	private LocalDate endDate;

	@Widget(title = "Warranty")
	private Boolean hasWarranty = Boolean.FALSE;

	@Widget(title = "Perishable")
	private Boolean isPerishable = Boolean.FALSE;

	@Widget(title = "Warranty length (in months)")
	private Integer warrantyNbrOfMonths = 0;

	@Widget(title = "Time before expiry (in months)")
	private Integer perishableNbrOfMonths = 0;

	private Boolean checkExpirationDateAtStockMoveRealization = Boolean.FALSE;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private ProductVariantConfig productVariantConfig;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private ProductVariant productVariant;

	@Widget(title = "Parent product")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Product parentProduct;

	@Widget(title = "Is model")
	private Boolean isModel = Boolean.FALSE;

	@Widget(title = "Manage prices for product variants")
	private Boolean manageVariantPrice = Boolean.FALSE;

	@Widget(title = "Default supplier")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Partner defaultSupplierPartner;

	@Widget(title = "Accounts configuration")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<AccountManagement> accountManagementList;

	@Widget(title = "Version", selection = "base.product.version.select")
	private Integer versionSelect = 0;

	@Widget(title = "Sellable")
	private Boolean sellable = Boolean.TRUE;

	@Widget(title = "Purchasable")
	private Boolean purchasable = Boolean.TRUE;

	@Widget(title = "In ATI")
	private Boolean inAti = Boolean.FALSE;

	@Widget(title = "Cost type", selection = "base.product.cost.type.select")
	private Integer costTypeSelect = 1;

	@Widget(title = "Supplier delivery time (days)")
	@Min(0)
	private Integer supplierDeliveryTime = 0;

	@Widget(title = "Barcode")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private MetaFile barCode;

	@Widget(title = "Barcode Type")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private BarcodeTypeConfig barcodeTypeConfig;

	@Widget(title = "Full name", translatable = true)
	@NameColumn
	private String fullName;

	@Widget(title = "Unit of mass")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Unit massUnit;

	@Widget(title = "Gross mass")
	@Digits(integer = 17, fraction = 3)
	private BigDecimal grossMass = BigDecimal.ZERO;

	@Widget(title = "Net mass")
	@Digits(integer = 17, fraction = 3)
	private BigDecimal netMass = BigDecimal.ZERO;

	@Widget(title = "Unit of length")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Unit lengthUnit;

	@Widget(title = "Length")
	private BigDecimal length = new BigDecimal("0");

	@Widget(title = "Width")
	private BigDecimal width = new BigDecimal("0");

	@Widget(title = "Height")
	private BigDecimal height = new BigDecimal("0");

	@Widget(title = "Diameter")
	private BigDecimal diameter = BigDecimal.ZERO;

	@Widget(title = "Article volume")
	private BigDecimal articleVolume = BigDecimal.ZERO;

	@Widget(title = "Economic manuf. qty")
	@Digits(integer = 10, fraction = 10)
	private BigDecimal economicManufOrderQty = BigDecimal.ZERO;

	@Widget(title = "Is shipping costs product")
	private Boolean isShippingCostsProduct = Boolean.FALSE;

	@Widget(title = "Multiple quantities")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "saleProduct", cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy("multipleQty")
	private List<ProductMultipleQty> saleProductMultipleQtyList;

	@Widget(title = "Allow to force sales quantities")
	private Boolean allowToForceSaleQty = Boolean.FALSE;

	@Widget(title = "Multiple quantities")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "purchaseProduct", cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy("multipleQty")
	private List<ProductMultipleQty> purchaseProductMultipleQtyList;

	@Widget(title = "Allow to force purchases quantities")
	private Boolean allowToForcePurchaseQty = Boolean.FALSE;

	@Widget(title = "Trading names that can sell this product")
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<TradingName> tradingNameSellerSet;

	@Widget(title = "Trading names that can buy this product")
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<TradingName> tradingNameBuyerSet;

	@Widget(title = "Products per companies")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProductCompany> productCompanyList;

	@Column(insertable = false, updatable = false)
	private String dtype;

	@Widget(title = "Fields")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String productAttrs;

	@Widget(title = "Expense")
	private Boolean expense = Boolean.FALSE;

	@Widget(title = "Use for Analytic Capture")
	private Boolean isAnalyticCapture = Boolean.FALSE;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public Product() {
	}

	public Product(String name, String code, String description, String internalDescription, MetaFile picture, ProductCategory productCategory, ProductFamily productFamily, Unit unit, Integer saleSupplySelect, String productTypeSelect, String procurementMethodSelect, Currency saleCurrency, Currency purchaseCurrency, LocalDate startDate, LocalDate endDate) {
		this.name = name;
		this.code = code;
		this.description = description;
		this.internalDescription = internalDescription;
		this.picture = picture;
		this.productCategory = productCategory;
		this.productFamily = productFamily;
		this.unit = unit;
		this.saleSupplySelect = saleSupplySelect;
		this.productTypeSelect = productTypeSelect;
		this.procurementMethodSelect = procurementMethodSelect;
		this.saleCurrency = saleCurrency;
		this.purchaseCurrency = purchaseCurrency;
		this.startDate = startDate;
		this.endDate = endDate;
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

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getInternalDescription() {
		return internalDescription;
	}

	public void setInternalDescription(String internalDescription) {
		this.internalDescription = internalDescription;
	}

	public MetaFile getPicture() {
		return picture;
	}

	public void setPicture(MetaFile picture) {
		this.picture = picture;
	}

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	public ProductFamily getProductFamily() {
		return productFamily;
	}

	public void setProductFamily(ProductFamily productFamily) {
		this.productFamily = productFamily;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public Integer getSaleSupplySelect() {
		return saleSupplySelect == null ? 0 : saleSupplySelect;
	}

	public void setSaleSupplySelect(Integer saleSupplySelect) {
		this.saleSupplySelect = saleSupplySelect;
	}

	public String getProductTypeSelect() {
		return productTypeSelect;
	}

	public void setProductTypeSelect(String productTypeSelect) {
		this.productTypeSelect = productTypeSelect;
	}

	public String getProcurementMethodSelect() {
		return procurementMethodSelect;
	}

	public void setProcurementMethodSelect(String procurementMethodSelect) {
		this.procurementMethodSelect = procurementMethodSelect;
	}

	public Boolean getIsPrototype() {
		return isPrototype == null ? Boolean.FALSE : isPrototype;
	}

	public void setIsPrototype(Boolean isPrototype) {
		this.isPrototype = isPrototype;
	}

	public Boolean getIsUnrenewed() {
		return isUnrenewed == null ? Boolean.FALSE : isUnrenewed;
	}

	public void setIsUnrenewed(Boolean isUnrenewed) {
		this.isUnrenewed = isUnrenewed;
	}

	public Integer getProductSubTypeSelect() {
		return productSubTypeSelect == null ? 0 : productSubTypeSelect;
	}

	public void setProductSubTypeSelect(Integer productSubTypeSelect) {
		this.productSubTypeSelect = productSubTypeSelect;
	}

	public Integer getInventoryTypeSelect() {
		return inventoryTypeSelect == null ? 0 : inventoryTypeSelect;
	}

	public void setInventoryTypeSelect(Integer inventoryTypeSelect) {
		this.inventoryTypeSelect = inventoryTypeSelect;
	}

	public BigDecimal getSalePrice() {
		return salePrice == null ? BigDecimal.ZERO : salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public Currency getSaleCurrency() {
		return saleCurrency;
	}

	public void setSaleCurrency(Currency saleCurrency) {
		this.saleCurrency = saleCurrency;
	}

	public BigDecimal getPurchasePrice() {
		return purchasePrice == null ? BigDecimal.ZERO : purchasePrice;
	}

	public void setPurchasePrice(BigDecimal purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public LocalDate getLastPurchaseDate() {
		return lastPurchaseDate;
	}

	public void setLastPurchaseDate(LocalDate lastPurchaseDate) {
		this.lastPurchaseDate = lastPurchaseDate;
	}

	public BigDecimal getLastPurchasePrice() {
		return lastPurchasePrice == null ? BigDecimal.ZERO : lastPurchasePrice;
	}

	public void setLastPurchasePrice(BigDecimal lastPurchasePrice) {
		this.lastPurchasePrice = lastPurchasePrice;
	}

	public Currency getPurchaseCurrency() {
		return purchaseCurrency;
	}

	public void setPurchaseCurrency(Currency purchaseCurrency) {
		this.purchaseCurrency = purchaseCurrency;
	}

	public Boolean getAutoUpdateSalePrice() {
		return autoUpdateSalePrice == null ? Boolean.FALSE : autoUpdateSalePrice;
	}

	public void setAutoUpdateSalePrice(Boolean autoUpdateSalePrice) {
		this.autoUpdateSalePrice = autoUpdateSalePrice;
	}

	public List<PriceListLine> getPriceListLineList() {
		return priceListLineList;
	}

	public void setPriceListLineList(List<PriceListLine> priceListLineList) {
		this.priceListLineList = priceListLineList;
	}

	/**
	 * Add the given {@link PriceListLine} item to the {@code priceListLineList}.
	 *
	 * <p>
	 * It sets {@code item.product = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addPriceListLineListItem(PriceListLine item) {
		if (getPriceListLineList() == null) {
			setPriceListLineList(new ArrayList<>());
		}
		getPriceListLineList().add(item);
		item.setProduct(this);
	}

	/**
	 * Remove the given {@link PriceListLine} item from the {@code priceListLineList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removePriceListLineListItem(PriceListLine item) {
		if (getPriceListLineList() == null) {
			return;
		}
		getPriceListLineList().remove(item);
	}

	/**
	 * Clear the {@code priceListLineList} collection.
	 *
	 * <p>
	 * If you have to query {@link PriceListLine} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearPriceListLineList() {
		if (getPriceListLineList() != null) {
			getPriceListLineList().clear();
		}
	}

	public BigDecimal getCostPrice() {
		return costPrice == null ? BigDecimal.ZERO : costPrice;
	}

	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
	}

	public BigDecimal getManagPriceCoef() {
		return managPriceCoef == null ? BigDecimal.ZERO : managPriceCoef;
	}

	public void setManagPriceCoef(BigDecimal managPriceCoef) {
		this.managPriceCoef = managPriceCoef;
	}

	public BigDecimal getShippingCoef() {
		return shippingCoef == null ? BigDecimal.ZERO : shippingCoef;
	}

	public void setShippingCoef(BigDecimal shippingCoef) {
		this.shippingCoef = shippingCoef;
	}

	public Boolean getDefShipCoefByPartner() {
		return defShipCoefByPartner == null ? Boolean.FALSE : defShipCoefByPartner;
	}

	public void setDefShipCoefByPartner(Boolean defShipCoefByPartner) {
		this.defShipCoefByPartner = defShipCoefByPartner;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Boolean getHasWarranty() {
		return hasWarranty == null ? Boolean.FALSE : hasWarranty;
	}

	public void setHasWarranty(Boolean hasWarranty) {
		this.hasWarranty = hasWarranty;
	}

	public Boolean getIsPerishable() {
		return isPerishable == null ? Boolean.FALSE : isPerishable;
	}

	public void setIsPerishable(Boolean isPerishable) {
		this.isPerishable = isPerishable;
	}

	public Integer getWarrantyNbrOfMonths() {
		return warrantyNbrOfMonths == null ? 0 : warrantyNbrOfMonths;
	}

	public void setWarrantyNbrOfMonths(Integer warrantyNbrOfMonths) {
		this.warrantyNbrOfMonths = warrantyNbrOfMonths;
	}

	public Integer getPerishableNbrOfMonths() {
		return perishableNbrOfMonths == null ? 0 : perishableNbrOfMonths;
	}

	public void setPerishableNbrOfMonths(Integer perishableNbrOfMonths) {
		this.perishableNbrOfMonths = perishableNbrOfMonths;
	}

	public Boolean getCheckExpirationDateAtStockMoveRealization() {
		return checkExpirationDateAtStockMoveRealization == null ? Boolean.FALSE : checkExpirationDateAtStockMoveRealization;
	}

	public void setCheckExpirationDateAtStockMoveRealization(Boolean checkExpirationDateAtStockMoveRealization) {
		this.checkExpirationDateAtStockMoveRealization = checkExpirationDateAtStockMoveRealization;
	}

	public ProductVariantConfig getProductVariantConfig() {
		return productVariantConfig;
	}

	public void setProductVariantConfig(ProductVariantConfig productVariantConfig) {
		this.productVariantConfig = productVariantConfig;
	}

	public ProductVariant getProductVariant() {
		return productVariant;
	}

	public void setProductVariant(ProductVariant productVariant) {
		this.productVariant = productVariant;
	}

	public Product getParentProduct() {
		return parentProduct;
	}

	public void setParentProduct(Product parentProduct) {
		this.parentProduct = parentProduct;
	}

	public Boolean getIsModel() {
		return isModel == null ? Boolean.FALSE : isModel;
	}

	public void setIsModel(Boolean isModel) {
		this.isModel = isModel;
	}

	public Boolean getManageVariantPrice() {
		return manageVariantPrice == null ? Boolean.FALSE : manageVariantPrice;
	}

	public void setManageVariantPrice(Boolean manageVariantPrice) {
		this.manageVariantPrice = manageVariantPrice;
	}

	public Partner getDefaultSupplierPartner() {
		return defaultSupplierPartner;
	}

	public void setDefaultSupplierPartner(Partner defaultSupplierPartner) {
		this.defaultSupplierPartner = defaultSupplierPartner;
	}

	public List<AccountManagement> getAccountManagementList() {
		return accountManagementList;
	}

	public void setAccountManagementList(List<AccountManagement> accountManagementList) {
		this.accountManagementList = accountManagementList;
	}

	/**
	 * Add the given {@link AccountManagement} item to the {@code accountManagementList}.
	 *
	 * <p>
	 * It sets {@code item.product = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addAccountManagementListItem(AccountManagement item) {
		if (getAccountManagementList() == null) {
			setAccountManagementList(new ArrayList<>());
		}
		getAccountManagementList().add(item);
		item.setProduct(this);
	}

	/**
	 * Remove the given {@link AccountManagement} item from the {@code accountManagementList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeAccountManagementListItem(AccountManagement item) {
		if (getAccountManagementList() == null) {
			return;
		}
		getAccountManagementList().remove(item);
	}

	/**
	 * Clear the {@code accountManagementList} collection.
	 *
	 * <p>
	 * If you have to query {@link AccountManagement} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearAccountManagementList() {
		if (getAccountManagementList() != null) {
			getAccountManagementList().clear();
		}
	}

	public Integer getVersionSelect() {
		return versionSelect == null ? 0 : versionSelect;
	}

	public void setVersionSelect(Integer versionSelect) {
		this.versionSelect = versionSelect;
	}

	public Boolean getSellable() {
		return sellable == null ? Boolean.FALSE : sellable;
	}

	public void setSellable(Boolean sellable) {
		this.sellable = sellable;
	}

	public Boolean getPurchasable() {
		return purchasable == null ? Boolean.FALSE : purchasable;
	}

	public void setPurchasable(Boolean purchasable) {
		this.purchasable = purchasable;
	}

	public Boolean getInAti() {
		return inAti == null ? Boolean.FALSE : inAti;
	}

	public void setInAti(Boolean inAti) {
		this.inAti = inAti;
	}

	public Integer getCostTypeSelect() {
		return costTypeSelect == null ? 0 : costTypeSelect;
	}

	public void setCostTypeSelect(Integer costTypeSelect) {
		this.costTypeSelect = costTypeSelect;
	}

	public Integer getSupplierDeliveryTime() {
		return supplierDeliveryTime == null ? 0 : supplierDeliveryTime;
	}

	public void setSupplierDeliveryTime(Integer supplierDeliveryTime) {
		this.supplierDeliveryTime = supplierDeliveryTime;
	}

	public MetaFile getBarCode() {
		return barCode;
	}

	public void setBarCode(MetaFile barCode) {
		this.barCode = barCode;
	}

	public BarcodeTypeConfig getBarcodeTypeConfig() {
		return barcodeTypeConfig;
	}

	public void setBarcodeTypeConfig(BarcodeTypeConfig barcodeTypeConfig) {
		this.barcodeTypeConfig = barcodeTypeConfig;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Unit getMassUnit() {
		return massUnit;
	}

	public void setMassUnit(Unit massUnit) {
		this.massUnit = massUnit;
	}

	public BigDecimal getGrossMass() {
		return grossMass == null ? BigDecimal.ZERO : grossMass;
	}

	public void setGrossMass(BigDecimal grossMass) {
		this.grossMass = grossMass;
	}

	public BigDecimal getNetMass() {
		return netMass == null ? BigDecimal.ZERO : netMass;
	}

	public void setNetMass(BigDecimal netMass) {
		this.netMass = netMass;
	}

	public Unit getLengthUnit() {
		return lengthUnit;
	}

	public void setLengthUnit(Unit lengthUnit) {
		this.lengthUnit = lengthUnit;
	}

	public BigDecimal getLength() {
		return length == null ? BigDecimal.ZERO : length;
	}

	public void setLength(BigDecimal length) {
		this.length = length;
	}

	public BigDecimal getWidth() {
		return width == null ? BigDecimal.ZERO : width;
	}

	public void setWidth(BigDecimal width) {
		this.width = width;
	}

	public BigDecimal getHeight() {
		return height == null ? BigDecimal.ZERO : height;
	}

	public void setHeight(BigDecimal height) {
		this.height = height;
	}

	public BigDecimal getDiameter() {
		return diameter == null ? BigDecimal.ZERO : diameter;
	}

	public void setDiameter(BigDecimal diameter) {
		this.diameter = diameter;
	}

	public BigDecimal getArticleVolume() {
		return articleVolume == null ? BigDecimal.ZERO : articleVolume;
	}

	public void setArticleVolume(BigDecimal articleVolume) {
		this.articleVolume = articleVolume;
	}

	public BigDecimal getEconomicManufOrderQty() {
		return economicManufOrderQty == null ? BigDecimal.ZERO : economicManufOrderQty;
	}

	public void setEconomicManufOrderQty(BigDecimal economicManufOrderQty) {
		this.economicManufOrderQty = economicManufOrderQty;
	}

	public Boolean getIsShippingCostsProduct() {
		return isShippingCostsProduct == null ? Boolean.FALSE : isShippingCostsProduct;
	}

	public void setIsShippingCostsProduct(Boolean isShippingCostsProduct) {
		this.isShippingCostsProduct = isShippingCostsProduct;
	}

	public List<ProductMultipleQty> getSaleProductMultipleQtyList() {
		return saleProductMultipleQtyList;
	}

	public void setSaleProductMultipleQtyList(List<ProductMultipleQty> saleProductMultipleQtyList) {
		this.saleProductMultipleQtyList = saleProductMultipleQtyList;
	}

	/**
	 * Add the given {@link ProductMultipleQty} item to the {@code saleProductMultipleQtyList}.
	 *
	 * <p>
	 * It sets {@code item.saleProduct = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addSaleProductMultipleQtyListItem(ProductMultipleQty item) {
		if (getSaleProductMultipleQtyList() == null) {
			setSaleProductMultipleQtyList(new ArrayList<>());
		}
		getSaleProductMultipleQtyList().add(item);
		item.setSaleProduct(this);
	}

	/**
	 * Remove the given {@link ProductMultipleQty} item from the {@code saleProductMultipleQtyList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeSaleProductMultipleQtyListItem(ProductMultipleQty item) {
		if (getSaleProductMultipleQtyList() == null) {
			return;
		}
		getSaleProductMultipleQtyList().remove(item);
	}

	/**
	 * Clear the {@code saleProductMultipleQtyList} collection.
	 *
	 * <p>
	 * If you have to query {@link ProductMultipleQty} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearSaleProductMultipleQtyList() {
		if (getSaleProductMultipleQtyList() != null) {
			getSaleProductMultipleQtyList().clear();
		}
	}

	public Boolean getAllowToForceSaleQty() {
		return allowToForceSaleQty == null ? Boolean.FALSE : allowToForceSaleQty;
	}

	public void setAllowToForceSaleQty(Boolean allowToForceSaleQty) {
		this.allowToForceSaleQty = allowToForceSaleQty;
	}

	public List<ProductMultipleQty> getPurchaseProductMultipleQtyList() {
		return purchaseProductMultipleQtyList;
	}

	public void setPurchaseProductMultipleQtyList(List<ProductMultipleQty> purchaseProductMultipleQtyList) {
		this.purchaseProductMultipleQtyList = purchaseProductMultipleQtyList;
	}

	/**
	 * Add the given {@link ProductMultipleQty} item to the {@code purchaseProductMultipleQtyList}.
	 *
	 * <p>
	 * It sets {@code item.purchaseProduct = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addPurchaseProductMultipleQtyListItem(ProductMultipleQty item) {
		if (getPurchaseProductMultipleQtyList() == null) {
			setPurchaseProductMultipleQtyList(new ArrayList<>());
		}
		getPurchaseProductMultipleQtyList().add(item);
		item.setPurchaseProduct(this);
	}

	/**
	 * Remove the given {@link ProductMultipleQty} item from the {@code purchaseProductMultipleQtyList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removePurchaseProductMultipleQtyListItem(ProductMultipleQty item) {
		if (getPurchaseProductMultipleQtyList() == null) {
			return;
		}
		getPurchaseProductMultipleQtyList().remove(item);
	}

	/**
	 * Clear the {@code purchaseProductMultipleQtyList} collection.
	 *
	 * <p>
	 * If you have to query {@link ProductMultipleQty} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearPurchaseProductMultipleQtyList() {
		if (getPurchaseProductMultipleQtyList() != null) {
			getPurchaseProductMultipleQtyList().clear();
		}
	}

	public Boolean getAllowToForcePurchaseQty() {
		return allowToForcePurchaseQty == null ? Boolean.FALSE : allowToForcePurchaseQty;
	}

	public void setAllowToForcePurchaseQty(Boolean allowToForcePurchaseQty) {
		this.allowToForcePurchaseQty = allowToForcePurchaseQty;
	}

	public Set<TradingName> getTradingNameSellerSet() {
		return tradingNameSellerSet;
	}

	public void setTradingNameSellerSet(Set<TradingName> tradingNameSellerSet) {
		this.tradingNameSellerSet = tradingNameSellerSet;
	}

	/**
	 * Add the given {@link TradingName} item to the {@code tradingNameSellerSet}.
	 *
	 * @param item
	 *            the item to add
	 */
	public void addTradingNameSellerSetItem(TradingName item) {
		if (getTradingNameSellerSet() == null) {
			setTradingNameSellerSet(new HashSet<>());
		}
		getTradingNameSellerSet().add(item);
	}

	/**
	 * Remove the given {@link TradingName} item from the {@code tradingNameSellerSet}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeTradingNameSellerSetItem(TradingName item) {
		if (getTradingNameSellerSet() == null) {
			return;
		}
		getTradingNameSellerSet().remove(item);
	}

	/**
	 * Clear the {@code tradingNameSellerSet} collection.
	 *
	 */
	public void clearTradingNameSellerSet() {
		if (getTradingNameSellerSet() != null) {
			getTradingNameSellerSet().clear();
		}
	}

	public Set<TradingName> getTradingNameBuyerSet() {
		return tradingNameBuyerSet;
	}

	public void setTradingNameBuyerSet(Set<TradingName> tradingNameBuyerSet) {
		this.tradingNameBuyerSet = tradingNameBuyerSet;
	}

	/**
	 * Add the given {@link TradingName} item to the {@code tradingNameBuyerSet}.
	 *
	 * @param item
	 *            the item to add
	 */
	public void addTradingNameBuyerSetItem(TradingName item) {
		if (getTradingNameBuyerSet() == null) {
			setTradingNameBuyerSet(new HashSet<>());
		}
		getTradingNameBuyerSet().add(item);
	}

	/**
	 * Remove the given {@link TradingName} item from the {@code tradingNameBuyerSet}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeTradingNameBuyerSetItem(TradingName item) {
		if (getTradingNameBuyerSet() == null) {
			return;
		}
		getTradingNameBuyerSet().remove(item);
	}

	/**
	 * Clear the {@code tradingNameBuyerSet} collection.
	 *
	 */
	public void clearTradingNameBuyerSet() {
		if (getTradingNameBuyerSet() != null) {
			getTradingNameBuyerSet().clear();
		}
	}

	public List<ProductCompany> getProductCompanyList() {
		return productCompanyList;
	}

	public void setProductCompanyList(List<ProductCompany> productCompanyList) {
		this.productCompanyList = productCompanyList;
	}

	/**
	 * Add the given {@link ProductCompany} item to the {@code productCompanyList}.
	 *
	 * <p>
	 * It sets {@code item.product = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addProductCompanyListItem(ProductCompany item) {
		if (getProductCompanyList() == null) {
			setProductCompanyList(new ArrayList<>());
		}
		getProductCompanyList().add(item);
		item.setProduct(this);
	}

	/**
	 * Remove the given {@link ProductCompany} item from the {@code productCompanyList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeProductCompanyListItem(ProductCompany item) {
		if (getProductCompanyList() == null) {
			return;
		}
		getProductCompanyList().remove(item);
	}

	/**
	 * Clear the {@code productCompanyList} collection.
	 *
	 * <p>
	 * If you have to query {@link ProductCompany} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearProductCompanyList() {
		if (getProductCompanyList() != null) {
			getProductCompanyList().clear();
		}
	}

	public String getDtype() {
		return dtype;
	}

	public void setDtype(String dtype) {
		this.dtype = dtype;
	}

	public String getProductAttrs() {
		return productAttrs;
	}

	public void setProductAttrs(String productAttrs) {
		this.productAttrs = productAttrs;
	}

	public Boolean getExpense() {
		return expense == null ? Boolean.FALSE : expense;
	}

	public void setExpense(Boolean expense) {
		this.expense = expense;
	}

	public Boolean getIsAnalyticCapture() {
		return isAnalyticCapture == null ? Boolean.FALSE : isAnalyticCapture;
	}

	public void setIsAnalyticCapture(Boolean isAnalyticCapture) {
		this.isAnalyticCapture = isAnalyticCapture;
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
		if (!(obj instanceof Product)) return false;

		final Product other = (Product) obj;
		if (this.getId() != null || other.getId() != null) {
			return Objects.equals(this.getId(), other.getId());
		}

		return Objects.equals(getSerialNumber(), other.getSerialNumber())
			&& Objects.equals(getCode(), other.getCode())
			&& (getSerialNumber() != null
				|| getCode() != null);
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
			.add("serialNumber", getSerialNumber())
			.add("code", getCode())
			.add("saleSupplySelect", getSaleSupplySelect())
			.add("productTypeSelect", getProductTypeSelect())
			.add("procurementMethodSelect", getProcurementMethodSelect())
			.add("isPrototype", getIsPrototype())
			.add("isUnrenewed", getIsUnrenewed())
			.add("productSubTypeSelect", getProductSubTypeSelect())
			.add("inventoryTypeSelect", getInventoryTypeSelect())
			.add("salePrice", getSalePrice())
			.omitNullValues()
			.toString();
	}
}
