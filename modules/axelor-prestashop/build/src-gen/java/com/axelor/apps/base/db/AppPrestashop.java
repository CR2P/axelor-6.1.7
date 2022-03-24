package com.axelor.apps.base.db;

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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.annotations.Type;

import com.axelor.apps.account.db.Account;
import com.axelor.apps.account.db.PaymentCondition;
import com.axelor.apps.account.db.PaymentMode;
import com.axelor.apps.account.db.Tax;
import com.axelor.apps.prestashop.db.PrestashopOrderStatusCacheEntry;
import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@Cacheable
@Table(name = "BASE_APP_PRESTASHOP", indexes = { @Index(columnList = "app"), @Index(columnList = "presta_shop_country"), @Index(columnList = "presta_shop_currency"), @Index(columnList = "presta_shop_weight_unit"), @Index(columnList = "presta_shop_length_unit"), @Index(columnList = "texts_language"), @Index(columnList = "default_status"), @Index(columnList = "invoiced_status"), @Index(columnList = "paid_status"), @Index(columnList = "delivered_status"), @Index(columnList = "default_payment_condition"), @Index(columnList = "default_payment_mode"), @Index(columnList = "default_shipping_costs_product"), @Index(columnList = "discount_product"), @Index(columnList = "default_tax"), @Index(columnList = "default_sale_account_for_product"), @Index(columnList = "default_sale_account_for_tax") })
public class AppPrestashop extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BASE_APP_PRESTASHOP_SEQ")
	@SequenceGenerator(name = "BASE_APP_PRESTASHOP_SEQ", sequenceName = "BASE_APP_PRESTASHOP_SEQ", allocationSize = 1)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private App app;

	@Widget(title = "PrestaShop base URL")
	private String prestaShopUrl;

	@Widget(title = "PrestaShop webservices key")
	private String prestaShopKey;

	@Widget(title = "PrestaShop's default country")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Country prestaShopCountry;

	@Widget(title = "PrestaShop's default currency")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Currency prestaShopCurrency;

	@Widget(title = "PrestaShop's weight unit")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Unit prestaShopWeightUnit;

	@Widget(title = "PrestaShop's length unit")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Unit prestaShopLengthUnit;

	@Widget(title = "Axelor texts languages (for descriptions of products, name of countries, aso.)")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Language textsLanguage;

	@Widget(title = "Valid")
	private Boolean isValid = Boolean.FALSE;

	@Widget(title = "Number of digits after decimal point for sales price")
	@Min(0)
	@Max(9)
	private Integer exportPriceScale = 2;

	@Widget(title = "Export products not flagged as sellable", help = "If checked, all products, event those not marked as non-sellable, will be exported")
	private Boolean exportNonSoldProducts = Boolean.TRUE;

	@Widget(title = "Export non-Prestashop customers' orders", help = "If checked, all orders will be synchronized to prestashop, not only those created on it")
	private Boolean exportNonPrestashopOrders = Boolean.TRUE;

	@Widget(title = "Export non-Prestashop customers", help = "If checked, all customers will be synchronized to prestashop, not only those created on it")
	private Boolean exportNonPrestashopCustomers = Boolean.TRUE;

	@Widget(title = "Currencies are handled on prestashop", help = "If checked, export process will not update existing remote currencies and local currencies data will be overwritten by PrestaShop data")
	private Boolean prestaShopMasterForCurrencies = Boolean.FALSE;

	@Widget(title = "Countries are handled on prestashop", help = "If checked, export process will not update existing remote countries and local countries data will be overwritten by PrestaShop data")
	private Boolean prestaShopMasterForCountries = Boolean.FALSE;

	@Widget(title = "Customers are handled on prestashop", help = "If checked, export process will not update existing remote customers and local customers' data will be overwritten by PrestaShop data")
	private Boolean prestaShopMasterForCustomers = Boolean.FALSE;

	@Widget(title = "Taxes are handled on prestashop", help = "If checked, export process will not update existing remote taxes and local taxes's data will be overwritten by PrestaShop data")
	private Boolean prestaShopMasterForTaxes = Boolean.FALSE;

	@Widget(title = "Products categories are handled on prestashop", help = "If checked, export process will not update existing remote categories and local categories' data will be overwritten by PrestaShop data")
	private Boolean prestaShopMasterForCategories = Boolean.FALSE;

	@Widget(title = "Products are handled on prestashop", help = "If checked, export process will not update existing remote products and local product's data will be overwritten by PrestaShop data")
	private Boolean prestaShopMasterForProducts = Boolean.FALSE;

	@Widget(title = "Orders are handled exclusively on PrestaShop", help = "If checked, this means that all orders are handled on prestashop. You'll not use Axelor for anything except creating orders that will be sent & managed on PrestaShop. This means, among other things, no stock handling on Axelor side. If unchecked, import process will only create order taken from prestashop and all order management will be done on Axelor.")
	private Boolean prestaShopMasterForOrders = Boolean.FALSE;

	@Widget(title = "PrestaShop status for confirmed orders", help = "Orders with a Confirmed/Finalized/Finished status but neither paid nor delivered nor invoiced will be set to this status")
	@OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private PrestashopOrderStatusCacheEntry defaultStatus;

	@Widget(title = "PrestaShop status for invoiced orders", help = "Orders invoiced but not paid will be set to this status")
	@OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private PrestashopOrderStatusCacheEntry invoicedStatus;

	@Widget(title = "PrestaShop status for paid orders", help = "Orders paid but not delivered will be set to this status")
	@OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private PrestashopOrderStatusCacheEntry paidStatus;

	@Widget(title = "PrestaShop status for delivered orders", help = "Delivered orders will be set to this status")
	@OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private PrestashopOrderStatusCacheEntry deliveredStatus;

	@Widget(title = "Default payment condition", help = "Used on import if no local payment condition with the same name as the PrestaShop one exists")
	@OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private PaymentCondition defaultPaymentCondition;

	@Widget(title = "Default payment mode", help = "Used on import if no payment mode matches the remote one cannot be found")
	@OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private PaymentMode defaultPaymentMode;

	@Widget(title = "Default product for deliveries fees", help = "Used on import if carrier cannot be mapped to a local shipping cost product")
	@OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Product defaultShippingCostsProduct;

	@Widget(title = "Product used to apply discount to the whole order, not per product")
	@OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Product discountProduct;

	@Widget(title = "Default tax rate", help = "Used when tax rate cannot be determined")
	@OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Tax defaultTax;

	@Widget(title = "Default sale account for product", help = "Used when import tax for the product")
	@OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Account defaultSaleAccountForProduct;

	@Widget(title = "Default sale account for tax", help = "Used when import tax for accounting configuration of tax")
	@OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Account defaultSaleAccountForTax;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public AppPrestashop() {
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public App getApp() {
		return app;
	}

	public void setApp(App app) {
		this.app = app;
	}

	public String getPrestaShopUrl() {
		return prestaShopUrl;
	}

	public void setPrestaShopUrl(String prestaShopUrl) {
		this.prestaShopUrl = prestaShopUrl;
	}

	public String getPrestaShopKey() {
		return prestaShopKey;
	}

	public void setPrestaShopKey(String prestaShopKey) {
		this.prestaShopKey = prestaShopKey;
	}

	public Country getPrestaShopCountry() {
		return prestaShopCountry;
	}

	public void setPrestaShopCountry(Country prestaShopCountry) {
		this.prestaShopCountry = prestaShopCountry;
	}

	public Currency getPrestaShopCurrency() {
		return prestaShopCurrency;
	}

	public void setPrestaShopCurrency(Currency prestaShopCurrency) {
		this.prestaShopCurrency = prestaShopCurrency;
	}

	public Unit getPrestaShopWeightUnit() {
		return prestaShopWeightUnit;
	}

	public void setPrestaShopWeightUnit(Unit prestaShopWeightUnit) {
		this.prestaShopWeightUnit = prestaShopWeightUnit;
	}

	public Unit getPrestaShopLengthUnit() {
		return prestaShopLengthUnit;
	}

	public void setPrestaShopLengthUnit(Unit prestaShopLengthUnit) {
		this.prestaShopLengthUnit = prestaShopLengthUnit;
	}

	public Language getTextsLanguage() {
		return textsLanguage;
	}

	public void setTextsLanguage(Language textsLanguage) {
		this.textsLanguage = textsLanguage;
	}

	public Boolean getIsValid() {
		return isValid == null ? Boolean.FALSE : isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	public Integer getExportPriceScale() {
		return exportPriceScale == null ? 0 : exportPriceScale;
	}

	public void setExportPriceScale(Integer exportPriceScale) {
		this.exportPriceScale = exportPriceScale;
	}

	/**
	 * If checked, all products, event those not marked as non-sellable, will be exported
	 *
	 * @return the property value
	 */
	public Boolean getExportNonSoldProducts() {
		return exportNonSoldProducts == null ? Boolean.FALSE : exportNonSoldProducts;
	}

	public void setExportNonSoldProducts(Boolean exportNonSoldProducts) {
		this.exportNonSoldProducts = exportNonSoldProducts;
	}

	/**
	 * If checked, all orders will be synchronized to prestashop, not only those created on it
	 *
	 * @return the property value
	 */
	public Boolean getExportNonPrestashopOrders() {
		return exportNonPrestashopOrders == null ? Boolean.FALSE : exportNonPrestashopOrders;
	}

	public void setExportNonPrestashopOrders(Boolean exportNonPrestashopOrders) {
		this.exportNonPrestashopOrders = exportNonPrestashopOrders;
	}

	/**
	 * If checked, all customers will be synchronized to prestashop, not only those created on it
	 *
	 * @return the property value
	 */
	public Boolean getExportNonPrestashopCustomers() {
		return exportNonPrestashopCustomers == null ? Boolean.FALSE : exportNonPrestashopCustomers;
	}

	public void setExportNonPrestashopCustomers(Boolean exportNonPrestashopCustomers) {
		this.exportNonPrestashopCustomers = exportNonPrestashopCustomers;
	}

	/**
	 * If checked, export process will not update existing remote currencies and local currencies data will be overwritten by PrestaShop data
	 *
	 * @return the property value
	 */
	public Boolean getPrestaShopMasterForCurrencies() {
		return prestaShopMasterForCurrencies == null ? Boolean.FALSE : prestaShopMasterForCurrencies;
	}

	public void setPrestaShopMasterForCurrencies(Boolean prestaShopMasterForCurrencies) {
		this.prestaShopMasterForCurrencies = prestaShopMasterForCurrencies;
	}

	/**
	 * If checked, export process will not update existing remote countries and local countries data will be overwritten by PrestaShop data
	 *
	 * @return the property value
	 */
	public Boolean getPrestaShopMasterForCountries() {
		return prestaShopMasterForCountries == null ? Boolean.FALSE : prestaShopMasterForCountries;
	}

	public void setPrestaShopMasterForCountries(Boolean prestaShopMasterForCountries) {
		this.prestaShopMasterForCountries = prestaShopMasterForCountries;
	}

	/**
	 * If checked, export process will not update existing remote customers and local customers' data will be overwritten by PrestaShop data
	 *
	 * @return the property value
	 */
	public Boolean getPrestaShopMasterForCustomers() {
		return prestaShopMasterForCustomers == null ? Boolean.FALSE : prestaShopMasterForCustomers;
	}

	public void setPrestaShopMasterForCustomers(Boolean prestaShopMasterForCustomers) {
		this.prestaShopMasterForCustomers = prestaShopMasterForCustomers;
	}

	/**
	 * If checked, export process will not update existing remote taxes and local taxes's data will be overwritten by PrestaShop data
	 *
	 * @return the property value
	 */
	public Boolean getPrestaShopMasterForTaxes() {
		return prestaShopMasterForTaxes == null ? Boolean.FALSE : prestaShopMasterForTaxes;
	}

	public void setPrestaShopMasterForTaxes(Boolean prestaShopMasterForTaxes) {
		this.prestaShopMasterForTaxes = prestaShopMasterForTaxes;
	}

	/**
	 * If checked, export process will not update existing remote categories and local categories' data will be overwritten by PrestaShop data
	 *
	 * @return the property value
	 */
	public Boolean getPrestaShopMasterForCategories() {
		return prestaShopMasterForCategories == null ? Boolean.FALSE : prestaShopMasterForCategories;
	}

	public void setPrestaShopMasterForCategories(Boolean prestaShopMasterForCategories) {
		this.prestaShopMasterForCategories = prestaShopMasterForCategories;
	}

	/**
	 * If checked, export process will not update existing remote products and local product's data will be overwritten by PrestaShop data
	 *
	 * @return the property value
	 */
	public Boolean getPrestaShopMasterForProducts() {
		return prestaShopMasterForProducts == null ? Boolean.FALSE : prestaShopMasterForProducts;
	}

	public void setPrestaShopMasterForProducts(Boolean prestaShopMasterForProducts) {
		this.prestaShopMasterForProducts = prestaShopMasterForProducts;
	}

	/**
	 * If checked, this means that all orders are handled on prestashop. You'll not use Axelor for anything except creating orders that will be sent & managed on PrestaShop. This means, among other things, no stock handling on Axelor side. If unchecked, import process will only create order taken from prestashop and all order management will be done on Axelor.
	 *
	 * @return the property value
	 */
	public Boolean getPrestaShopMasterForOrders() {
		return prestaShopMasterForOrders == null ? Boolean.FALSE : prestaShopMasterForOrders;
	}

	public void setPrestaShopMasterForOrders(Boolean prestaShopMasterForOrders) {
		this.prestaShopMasterForOrders = prestaShopMasterForOrders;
	}

	/**
	 * Orders with a Confirmed/Finalized/Finished status but neither paid nor delivered nor invoiced will be set to this status
	 *
	 * @return the property value
	 */
	public PrestashopOrderStatusCacheEntry getDefaultStatus() {
		return defaultStatus;
	}

	public void setDefaultStatus(PrestashopOrderStatusCacheEntry defaultStatus) {
		this.defaultStatus = defaultStatus;
	}

	/**
	 * Orders invoiced but not paid will be set to this status
	 *
	 * @return the property value
	 */
	public PrestashopOrderStatusCacheEntry getInvoicedStatus() {
		return invoicedStatus;
	}

	public void setInvoicedStatus(PrestashopOrderStatusCacheEntry invoicedStatus) {
		this.invoicedStatus = invoicedStatus;
	}

	/**
	 * Orders paid but not delivered will be set to this status
	 *
	 * @return the property value
	 */
	public PrestashopOrderStatusCacheEntry getPaidStatus() {
		return paidStatus;
	}

	public void setPaidStatus(PrestashopOrderStatusCacheEntry paidStatus) {
		this.paidStatus = paidStatus;
	}

	/**
	 * Delivered orders will be set to this status
	 *
	 * @return the property value
	 */
	public PrestashopOrderStatusCacheEntry getDeliveredStatus() {
		return deliveredStatus;
	}

	public void setDeliveredStatus(PrestashopOrderStatusCacheEntry deliveredStatus) {
		this.deliveredStatus = deliveredStatus;
	}

	/**
	 * Used on import if no local payment condition with the same name as the PrestaShop one exists
	 *
	 * @return the property value
	 */
	public PaymentCondition getDefaultPaymentCondition() {
		return defaultPaymentCondition;
	}

	public void setDefaultPaymentCondition(PaymentCondition defaultPaymentCondition) {
		this.defaultPaymentCondition = defaultPaymentCondition;
	}

	/**
	 * Used on import if no payment mode matches the remote one cannot be found
	 *
	 * @return the property value
	 */
	public PaymentMode getDefaultPaymentMode() {
		return defaultPaymentMode;
	}

	public void setDefaultPaymentMode(PaymentMode defaultPaymentMode) {
		this.defaultPaymentMode = defaultPaymentMode;
	}

	/**
	 * Used on import if carrier cannot be mapped to a local shipping cost product
	 *
	 * @return the property value
	 */
	public Product getDefaultShippingCostsProduct() {
		return defaultShippingCostsProduct;
	}

	public void setDefaultShippingCostsProduct(Product defaultShippingCostsProduct) {
		this.defaultShippingCostsProduct = defaultShippingCostsProduct;
	}

	public Product getDiscountProduct() {
		return discountProduct;
	}

	public void setDiscountProduct(Product discountProduct) {
		this.discountProduct = discountProduct;
	}

	/**
	 * Used when tax rate cannot be determined
	 *
	 * @return the property value
	 */
	public Tax getDefaultTax() {
		return defaultTax;
	}

	public void setDefaultTax(Tax defaultTax) {
		this.defaultTax = defaultTax;
	}

	/**
	 * Used when import tax for the product
	 *
	 * @return the property value
	 */
	public Account getDefaultSaleAccountForProduct() {
		return defaultSaleAccountForProduct;
	}

	public void setDefaultSaleAccountForProduct(Account defaultSaleAccountForProduct) {
		this.defaultSaleAccountForProduct = defaultSaleAccountForProduct;
	}

	/**
	 * Used when import tax for accounting configuration of tax
	 *
	 * @return the property value
	 */
	public Account getDefaultSaleAccountForTax() {
		return defaultSaleAccountForTax;
	}

	public void setDefaultSaleAccountForTax(Account defaultSaleAccountForTax) {
		this.defaultSaleAccountForTax = defaultSaleAccountForTax;
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
		if (!(obj instanceof AppPrestashop)) return false;

		final AppPrestashop other = (AppPrestashop) obj;
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
			.add("prestaShopUrl", getPrestaShopUrl())
			.add("prestaShopKey", getPrestaShopKey())
			.add("isValid", getIsValid())
			.add("exportPriceScale", getExportPriceScale())
			.add("exportNonSoldProducts", getExportNonSoldProducts())
			.add("exportNonPrestashopOrders", getExportNonPrestashopOrders())
			.add("exportNonPrestashopCustomers", getExportNonPrestashopCustomers())
			.add("prestaShopMasterForCurrencies", getPrestaShopMasterForCurrencies())
			.add("prestaShopMasterForCountries", getPrestaShopMasterForCountries())
			.add("prestaShopMasterForCustomers", getPrestaShopMasterForCustomers())
			.add("prestaShopMasterForTaxes", getPrestaShopMasterForTaxes())
			.omitNullValues()
			.toString();
	}
}
