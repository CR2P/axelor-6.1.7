package com.axelor.apps.stock.db;

import java.util.ArrayList;
import java.util.List;
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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import com.axelor.apps.base.db.Company;
import com.axelor.apps.base.db.Unit;
import com.axelor.apps.message.db.Template;
import com.axelor.auth.db.AuditableModel;
import com.axelor.auth.db.User;
import com.axelor.db.annotations.EqualsInclude;
import com.axelor.db.annotations.Track;
import com.axelor.db.annotations.TrackEvent;
import com.axelor.db.annotations.TrackField;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@Cacheable
@Table(name = "STOCK_STOCK_CONFIG", indexes = { @Index(columnList = "receipt_default_stock_location"), @Index(columnList = "pickup_default_stock_location"), @Index(columnList = "quality_control_default_stock_location"), @Index(columnList = "customer_virtual_stock_location"), @Index(columnList = "supplier_virtual_stock_location"), @Index(columnList = "inventory_virtual_stock_location"), @Index(columnList = "virtual_outsourcing_stock_location"), @Index(columnList = "outsourcing_receipt_stock_location"), @Index(columnList = "customs_mass_unit"), @Index(columnList = "planned_stock_move_message_template"), @Index(columnList = "real_stock_move_message_template"), @Index(columnList = "logistical_form_message_template"), @Index(columnList = "signatory_user"), @Index(columnList = "stock_rule_message_template"), @Index(columnList = "direct_order_stock_location") })
@Track(fields = { @TrackField(name = "company", on = TrackEvent.UPDATE), @TrackField(name = "receiptDefaultStockLocation", on = TrackEvent.UPDATE), @TrackField(name = "pickupDefaultStockLocation", on = TrackEvent.UPDATE), @TrackField(name = "qualityControlDefaultStockLocation", on = TrackEvent.UPDATE), @TrackField(name = "customerVirtualStockLocation", on = TrackEvent.UPDATE), @TrackField(name = "supplierVirtualStockLocation", on = TrackEvent.UPDATE), @TrackField(name = "inventoryVirtualStockLocation", on = TrackEvent.UPDATE), @TrackField(name = "customsMassUnit", on = TrackEvent.UPDATE), @TrackField(name = "realizeStockMovesUponParcelPalletCollection", on = TrackEvent.UPDATE), @TrackField(name = "plannedStockMoveAutomaticMail", on = TrackEvent.UPDATE), @TrackField(name = "realStockMoveAutomaticMail", on = TrackEvent.UPDATE), @TrackField(name = "plannedStockMoveMessageTemplate", on = TrackEvent.UPDATE), @TrackField(name = "realStockMoveMessageTemplate", on = TrackEvent.UPDATE), @TrackField(name = "conformityCertificateTitle", on = TrackEvent.UPDATE), @TrackField(name = "conformityCertificateDescription", on = TrackEvent.UPDATE), @TrackField(name = "signatoryUser", on = TrackEvent.UPDATE), @TrackField(name = "displayTrackNbrOnPickingPrinting", on = TrackEvent.UPDATE), @TrackField(name = "displayBarcodeOnPickingPrinting", on = TrackEvent.UPDATE), @TrackField(name = "displayCustomerCodeOnPickingPrinting", on = TrackEvent.UPDATE), @TrackField(name = "displayPartnerSeqOnPrinting", on = TrackEvent.UPDATE), @TrackField(name = "displayLineDetailsOnPrinting", on = TrackEvent.UPDATE), @TrackField(name = "isWithReturnSurplus", on = TrackEvent.UPDATE), @TrackField(name = "isWithBackorder", on = TrackEvent.UPDATE), @TrackField(name = "pickingOrderPrintingDetailed", on = TrackEvent.UPDATE), @TrackField(name = "isDisplaySaleValueInPrinting", on = TrackEvent.UPDATE), @TrackField(name = "isDisplayAccountingValueInPrinting", on = TrackEvent.UPDATE), @TrackField(name = "isDisplayAgPriceInPrinting", on = TrackEvent.UPDATE), @TrackField(name = "stockValuationTypeSelect", on = TrackEvent.UPDATE), @TrackField(name = "inventoryValuationTypeSelect", on = TrackEvent.UPDATE), @TrackField(name = "displayTrackNbrOnCertificateOfConformityPrinting", on = TrackEvent.UPDATE), @TrackField(name = "displayExtRefOnCertificateOfConformityPrinting", on = TrackEvent.UPDATE), @TrackField(name = "directOrderStockLocation", on = TrackEvent.UPDATE) })
public class StockConfig extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STOCK_STOCK_CONFIG_SEQ")
	@SequenceGenerator(name = "STOCK_STOCK_CONFIG_SEQ", sequenceName = "STOCK_STOCK_CONFIG_SEQ", allocationSize = 1)
	private Long id;

	@EqualsInclude
	@Widget(title = "Company")
	@NotNull
	@JoinColumn(unique = true)
	@OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Company company;

	@Widget(title = "Receipt default stock location")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private StockLocation receiptDefaultStockLocation;

	@Widget(title = "Pickup default stock location")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private StockLocation pickupDefaultStockLocation;

	@Widget(title = "Quality control default stock location")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private StockLocation qualityControlDefaultStockLocation;

	@Widget(title = "Customer virtual stock location")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private StockLocation customerVirtualStockLocation;

	@Widget(title = "Supplier virtual stock location")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private StockLocation supplierVirtualStockLocation;

	@Widget(title = "Inventory virtual stock location")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private StockLocation inventoryVirtualStockLocation;

	@Widget(title = "Virtual outsourcing stock location")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private StockLocation virtualOutsourcingStockLocation;

	@Widget(title = "Outsourcing receipt stock location")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private StockLocation outsourcingReceiptStockLocation;

	@Widget(title = "Unit of mass")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Unit customsMassUnit;

	@Widget(title = "Realize stock moves upon parcel/pallet collection")
	private Boolean realizeStockMovesUponParcelPalletCollection = Boolean.FALSE;

	@Widget(title = "Send email when planning stock move")
	private Boolean plannedStockMoveAutomaticMail = Boolean.FALSE;

	@Widget(title = "Send email on stock move realization")
	private Boolean realStockMoveAutomaticMail = Boolean.FALSE;

	@Widget(title = "Message template")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Template plannedStockMoveMessageTemplate;

	@Widget(title = "Message template")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Template realStockMoveMessageTemplate;

	@Widget(title = "Send email when saving logistical form")
	private Boolean logisticalFormAutomaticEmail = Boolean.FALSE;

	@Widget(title = "Message template")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Template logisticalFormMessageTemplate;

	@Widget(title = "Customer account numbers to carriers")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "stockConfig", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<FreightCarrierCustomerAccountNumber> freightCarrierCustomerAccountNumberList;

	@Widget(title = "Certificate of conformity title", translatable = true)
	private String conformityCertificateTitle;

	@Widget(title = "Text in certificate of conformity", translatable = true)
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String conformityCertificateDescription;

	@Widget(title = "Default signatory user")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private User signatoryUser;

	@Widget(title = "Display tracking number details on Picking printing")
	private Boolean displayTrackNbrOnPickingPrinting = Boolean.TRUE;

	@Widget(title = "Display barcode on Picking printing")
	private Boolean displayBarcodeOnPickingPrinting = Boolean.TRUE;

	@Widget(title = "Display customer code on picking printing")
	private Boolean displayCustomerCodeOnPickingPrinting = Boolean.TRUE;

	@Widget(title = "Display partner sequence on printing")
	private Boolean displayPartnerSeqOnPrinting = Boolean.TRUE;

	@Widget(title = "Display line details on printing")
	private Boolean displayLineDetailsOnPrinting = Boolean.TRUE;

	@Widget(title = "Return Surplus")
	private Boolean isWithReturnSurplus = Boolean.FALSE;

	@Widget(title = "Manage backorder")
	private Boolean isWithBackorder = Boolean.FALSE;

	@Widget(title = "Picking order printing detailed")
	private Boolean pickingOrderPrintingDetailed = Boolean.TRUE;

	@Widget(title = "Display sale value in location financial data printing")
	private Boolean isDisplaySaleValueInPrinting = Boolean.TRUE;

	@Widget(title = "Display accounting value in location financial data printing")
	private Boolean isDisplayAccountingValueInPrinting = Boolean.TRUE;

	@Widget(title = "Display average price in printing")
	private Boolean isDisplayAgPriceInPrinting = Boolean.TRUE;

	@Widget(title = "Display purchase value in location financial data printing")
	private Boolean isDisplayPurchaseValueInPrinting = Boolean.TRUE;

	@Widget(title = "Stock location value on form view", selection = "stock.stock.location.value.type")
	private Integer stockValuationTypeSelect = 0;

	@Widget(title = "Inventory valuation type", selection = "stock.stock.location.value.type")
	private Integer inventoryValuationTypeSelect = 0;

	@Widget(title = "Display tracking number details on certificate of conformity printing")
	private Boolean displayTrackNbrOnCertificateOfConformityPrinting = Boolean.TRUE;

	@Widget(title = "Display external ref. on certificate of conformity printing")
	private Boolean displayExtRefOnCertificateOfConformityPrinting = Boolean.TRUE;

	@Widget(title = "Message template for stock rules")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Template stockRuleMessageTemplate;

	@Widget(title = "Display product code on printing")
	private Boolean displayProductCodeOnPrinting = Boolean.TRUE;

	@Widget(title = "Display price on printing")
	private Boolean displayPriceOnPrinting = Boolean.TRUE;

	@Widget(title = "Display order reference and order date")
	private Boolean displayOrderReferenceAndOrderDate = Boolean.TRUE;

	@Widget(title = "Direct order default stock location")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private StockLocation directOrderStockLocation;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public StockConfig() {
	}

	public StockConfig(Boolean displayBarcodeOnPickingPrinting) {
		this.displayBarcodeOnPickingPrinting = displayBarcodeOnPickingPrinting;
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

	public StockLocation getReceiptDefaultStockLocation() {
		return receiptDefaultStockLocation;
	}

	public void setReceiptDefaultStockLocation(StockLocation receiptDefaultStockLocation) {
		this.receiptDefaultStockLocation = receiptDefaultStockLocation;
	}

	public StockLocation getPickupDefaultStockLocation() {
		return pickupDefaultStockLocation;
	}

	public void setPickupDefaultStockLocation(StockLocation pickupDefaultStockLocation) {
		this.pickupDefaultStockLocation = pickupDefaultStockLocation;
	}

	public StockLocation getQualityControlDefaultStockLocation() {
		return qualityControlDefaultStockLocation;
	}

	public void setQualityControlDefaultStockLocation(StockLocation qualityControlDefaultStockLocation) {
		this.qualityControlDefaultStockLocation = qualityControlDefaultStockLocation;
	}

	public StockLocation getCustomerVirtualStockLocation() {
		return customerVirtualStockLocation;
	}

	public void setCustomerVirtualStockLocation(StockLocation customerVirtualStockLocation) {
		this.customerVirtualStockLocation = customerVirtualStockLocation;
	}

	public StockLocation getSupplierVirtualStockLocation() {
		return supplierVirtualStockLocation;
	}

	public void setSupplierVirtualStockLocation(StockLocation supplierVirtualStockLocation) {
		this.supplierVirtualStockLocation = supplierVirtualStockLocation;
	}

	public StockLocation getInventoryVirtualStockLocation() {
		return inventoryVirtualStockLocation;
	}

	public void setInventoryVirtualStockLocation(StockLocation inventoryVirtualStockLocation) {
		this.inventoryVirtualStockLocation = inventoryVirtualStockLocation;
	}

	public StockLocation getVirtualOutsourcingStockLocation() {
		return virtualOutsourcingStockLocation;
	}

	public void setVirtualOutsourcingStockLocation(StockLocation virtualOutsourcingStockLocation) {
		this.virtualOutsourcingStockLocation = virtualOutsourcingStockLocation;
	}

	public StockLocation getOutsourcingReceiptStockLocation() {
		return outsourcingReceiptStockLocation;
	}

	public void setOutsourcingReceiptStockLocation(StockLocation outsourcingReceiptStockLocation) {
		this.outsourcingReceiptStockLocation = outsourcingReceiptStockLocation;
	}

	public Unit getCustomsMassUnit() {
		return customsMassUnit;
	}

	public void setCustomsMassUnit(Unit customsMassUnit) {
		this.customsMassUnit = customsMassUnit;
	}

	public Boolean getRealizeStockMovesUponParcelPalletCollection() {
		return realizeStockMovesUponParcelPalletCollection == null ? Boolean.FALSE : realizeStockMovesUponParcelPalletCollection;
	}

	public void setRealizeStockMovesUponParcelPalletCollection(Boolean realizeStockMovesUponParcelPalletCollection) {
		this.realizeStockMovesUponParcelPalletCollection = realizeStockMovesUponParcelPalletCollection;
	}

	public Boolean getPlannedStockMoveAutomaticMail() {
		return plannedStockMoveAutomaticMail == null ? Boolean.FALSE : plannedStockMoveAutomaticMail;
	}

	public void setPlannedStockMoveAutomaticMail(Boolean plannedStockMoveAutomaticMail) {
		this.plannedStockMoveAutomaticMail = plannedStockMoveAutomaticMail;
	}

	public Boolean getRealStockMoveAutomaticMail() {
		return realStockMoveAutomaticMail == null ? Boolean.FALSE : realStockMoveAutomaticMail;
	}

	public void setRealStockMoveAutomaticMail(Boolean realStockMoveAutomaticMail) {
		this.realStockMoveAutomaticMail = realStockMoveAutomaticMail;
	}

	public Template getPlannedStockMoveMessageTemplate() {
		return plannedStockMoveMessageTemplate;
	}

	public void setPlannedStockMoveMessageTemplate(Template plannedStockMoveMessageTemplate) {
		this.plannedStockMoveMessageTemplate = plannedStockMoveMessageTemplate;
	}

	public Template getRealStockMoveMessageTemplate() {
		return realStockMoveMessageTemplate;
	}

	public void setRealStockMoveMessageTemplate(Template realStockMoveMessageTemplate) {
		this.realStockMoveMessageTemplate = realStockMoveMessageTemplate;
	}

	public Boolean getLogisticalFormAutomaticEmail() {
		return logisticalFormAutomaticEmail == null ? Boolean.FALSE : logisticalFormAutomaticEmail;
	}

	public void setLogisticalFormAutomaticEmail(Boolean logisticalFormAutomaticEmail) {
		this.logisticalFormAutomaticEmail = logisticalFormAutomaticEmail;
	}

	public Template getLogisticalFormMessageTemplate() {
		return logisticalFormMessageTemplate;
	}

	public void setLogisticalFormMessageTemplate(Template logisticalFormMessageTemplate) {
		this.logisticalFormMessageTemplate = logisticalFormMessageTemplate;
	}

	public List<FreightCarrierCustomerAccountNumber> getFreightCarrierCustomerAccountNumberList() {
		return freightCarrierCustomerAccountNumberList;
	}

	public void setFreightCarrierCustomerAccountNumberList(List<FreightCarrierCustomerAccountNumber> freightCarrierCustomerAccountNumberList) {
		this.freightCarrierCustomerAccountNumberList = freightCarrierCustomerAccountNumberList;
	}

	/**
	 * Add the given {@link FreightCarrierCustomerAccountNumber} item to the {@code freightCarrierCustomerAccountNumberList}.
	 *
	 * <p>
	 * It sets {@code item.stockConfig = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addFreightCarrierCustomerAccountNumberListItem(FreightCarrierCustomerAccountNumber item) {
		if (getFreightCarrierCustomerAccountNumberList() == null) {
			setFreightCarrierCustomerAccountNumberList(new ArrayList<>());
		}
		getFreightCarrierCustomerAccountNumberList().add(item);
		item.setStockConfig(this);
	}

	/**
	 * Remove the given {@link FreightCarrierCustomerAccountNumber} item from the {@code freightCarrierCustomerAccountNumberList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeFreightCarrierCustomerAccountNumberListItem(FreightCarrierCustomerAccountNumber item) {
		if (getFreightCarrierCustomerAccountNumberList() == null) {
			return;
		}
		getFreightCarrierCustomerAccountNumberList().remove(item);
	}

	/**
	 * Clear the {@code freightCarrierCustomerAccountNumberList} collection.
	 *
	 * <p>
	 * If you have to query {@link FreightCarrierCustomerAccountNumber} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearFreightCarrierCustomerAccountNumberList() {
		if (getFreightCarrierCustomerAccountNumberList() != null) {
			getFreightCarrierCustomerAccountNumberList().clear();
		}
	}

	public String getConformityCertificateTitle() {
		return conformityCertificateTitle;
	}

	public void setConformityCertificateTitle(String conformityCertificateTitle) {
		this.conformityCertificateTitle = conformityCertificateTitle;
	}

	public String getConformityCertificateDescription() {
		return conformityCertificateDescription;
	}

	public void setConformityCertificateDescription(String conformityCertificateDescription) {
		this.conformityCertificateDescription = conformityCertificateDescription;
	}

	public User getSignatoryUser() {
		return signatoryUser;
	}

	public void setSignatoryUser(User signatoryUser) {
		this.signatoryUser = signatoryUser;
	}

	public Boolean getDisplayTrackNbrOnPickingPrinting() {
		return displayTrackNbrOnPickingPrinting == null ? Boolean.FALSE : displayTrackNbrOnPickingPrinting;
	}

	public void setDisplayTrackNbrOnPickingPrinting(Boolean displayTrackNbrOnPickingPrinting) {
		this.displayTrackNbrOnPickingPrinting = displayTrackNbrOnPickingPrinting;
	}

	public Boolean getDisplayBarcodeOnPickingPrinting() {
		return displayBarcodeOnPickingPrinting == null ? Boolean.FALSE : displayBarcodeOnPickingPrinting;
	}

	public void setDisplayBarcodeOnPickingPrinting(Boolean displayBarcodeOnPickingPrinting) {
		this.displayBarcodeOnPickingPrinting = displayBarcodeOnPickingPrinting;
	}

	public Boolean getDisplayCustomerCodeOnPickingPrinting() {
		return displayCustomerCodeOnPickingPrinting == null ? Boolean.FALSE : displayCustomerCodeOnPickingPrinting;
	}

	public void setDisplayCustomerCodeOnPickingPrinting(Boolean displayCustomerCodeOnPickingPrinting) {
		this.displayCustomerCodeOnPickingPrinting = displayCustomerCodeOnPickingPrinting;
	}

	public Boolean getDisplayPartnerSeqOnPrinting() {
		return displayPartnerSeqOnPrinting == null ? Boolean.FALSE : displayPartnerSeqOnPrinting;
	}

	public void setDisplayPartnerSeqOnPrinting(Boolean displayPartnerSeqOnPrinting) {
		this.displayPartnerSeqOnPrinting = displayPartnerSeqOnPrinting;
	}

	public Boolean getDisplayLineDetailsOnPrinting() {
		return displayLineDetailsOnPrinting == null ? Boolean.FALSE : displayLineDetailsOnPrinting;
	}

	public void setDisplayLineDetailsOnPrinting(Boolean displayLineDetailsOnPrinting) {
		this.displayLineDetailsOnPrinting = displayLineDetailsOnPrinting;
	}

	public Boolean getIsWithReturnSurplus() {
		return isWithReturnSurplus == null ? Boolean.FALSE : isWithReturnSurplus;
	}

	public void setIsWithReturnSurplus(Boolean isWithReturnSurplus) {
		this.isWithReturnSurplus = isWithReturnSurplus;
	}

	public Boolean getIsWithBackorder() {
		return isWithBackorder == null ? Boolean.FALSE : isWithBackorder;
	}

	public void setIsWithBackorder(Boolean isWithBackorder) {
		this.isWithBackorder = isWithBackorder;
	}

	public Boolean getPickingOrderPrintingDetailed() {
		return pickingOrderPrintingDetailed == null ? Boolean.FALSE : pickingOrderPrintingDetailed;
	}

	public void setPickingOrderPrintingDetailed(Boolean pickingOrderPrintingDetailed) {
		this.pickingOrderPrintingDetailed = pickingOrderPrintingDetailed;
	}

	public Boolean getIsDisplaySaleValueInPrinting() {
		return isDisplaySaleValueInPrinting == null ? Boolean.FALSE : isDisplaySaleValueInPrinting;
	}

	public void setIsDisplaySaleValueInPrinting(Boolean isDisplaySaleValueInPrinting) {
		this.isDisplaySaleValueInPrinting = isDisplaySaleValueInPrinting;
	}

	public Boolean getIsDisplayAccountingValueInPrinting() {
		return isDisplayAccountingValueInPrinting == null ? Boolean.FALSE : isDisplayAccountingValueInPrinting;
	}

	public void setIsDisplayAccountingValueInPrinting(Boolean isDisplayAccountingValueInPrinting) {
		this.isDisplayAccountingValueInPrinting = isDisplayAccountingValueInPrinting;
	}

	public Boolean getIsDisplayAgPriceInPrinting() {
		return isDisplayAgPriceInPrinting == null ? Boolean.FALSE : isDisplayAgPriceInPrinting;
	}

	public void setIsDisplayAgPriceInPrinting(Boolean isDisplayAgPriceInPrinting) {
		this.isDisplayAgPriceInPrinting = isDisplayAgPriceInPrinting;
	}

	public Boolean getIsDisplayPurchaseValueInPrinting() {
		return isDisplayPurchaseValueInPrinting == null ? Boolean.FALSE : isDisplayPurchaseValueInPrinting;
	}

	public void setIsDisplayPurchaseValueInPrinting(Boolean isDisplayPurchaseValueInPrinting) {
		this.isDisplayPurchaseValueInPrinting = isDisplayPurchaseValueInPrinting;
	}

	public Integer getStockValuationTypeSelect() {
		return stockValuationTypeSelect == null ? 0 : stockValuationTypeSelect;
	}

	public void setStockValuationTypeSelect(Integer stockValuationTypeSelect) {
		this.stockValuationTypeSelect = stockValuationTypeSelect;
	}

	public Integer getInventoryValuationTypeSelect() {
		return inventoryValuationTypeSelect == null ? 0 : inventoryValuationTypeSelect;
	}

	public void setInventoryValuationTypeSelect(Integer inventoryValuationTypeSelect) {
		this.inventoryValuationTypeSelect = inventoryValuationTypeSelect;
	}

	public Boolean getDisplayTrackNbrOnCertificateOfConformityPrinting() {
		return displayTrackNbrOnCertificateOfConformityPrinting == null ? Boolean.FALSE : displayTrackNbrOnCertificateOfConformityPrinting;
	}

	public void setDisplayTrackNbrOnCertificateOfConformityPrinting(Boolean displayTrackNbrOnCertificateOfConformityPrinting) {
		this.displayTrackNbrOnCertificateOfConformityPrinting = displayTrackNbrOnCertificateOfConformityPrinting;
	}

	public Boolean getDisplayExtRefOnCertificateOfConformityPrinting() {
		return displayExtRefOnCertificateOfConformityPrinting == null ? Boolean.FALSE : displayExtRefOnCertificateOfConformityPrinting;
	}

	public void setDisplayExtRefOnCertificateOfConformityPrinting(Boolean displayExtRefOnCertificateOfConformityPrinting) {
		this.displayExtRefOnCertificateOfConformityPrinting = displayExtRefOnCertificateOfConformityPrinting;
	}

	public Template getStockRuleMessageTemplate() {
		return stockRuleMessageTemplate;
	}

	public void setStockRuleMessageTemplate(Template stockRuleMessageTemplate) {
		this.stockRuleMessageTemplate = stockRuleMessageTemplate;
	}

	public Boolean getDisplayProductCodeOnPrinting() {
		return displayProductCodeOnPrinting == null ? Boolean.FALSE : displayProductCodeOnPrinting;
	}

	public void setDisplayProductCodeOnPrinting(Boolean displayProductCodeOnPrinting) {
		this.displayProductCodeOnPrinting = displayProductCodeOnPrinting;
	}

	public Boolean getDisplayPriceOnPrinting() {
		return displayPriceOnPrinting == null ? Boolean.FALSE : displayPriceOnPrinting;
	}

	public void setDisplayPriceOnPrinting(Boolean displayPriceOnPrinting) {
		this.displayPriceOnPrinting = displayPriceOnPrinting;
	}

	public Boolean getDisplayOrderReferenceAndOrderDate() {
		return displayOrderReferenceAndOrderDate == null ? Boolean.FALSE : displayOrderReferenceAndOrderDate;
	}

	public void setDisplayOrderReferenceAndOrderDate(Boolean displayOrderReferenceAndOrderDate) {
		this.displayOrderReferenceAndOrderDate = displayOrderReferenceAndOrderDate;
	}

	public StockLocation getDirectOrderStockLocation() {
		return directOrderStockLocation;
	}

	public void setDirectOrderStockLocation(StockLocation directOrderStockLocation) {
		this.directOrderStockLocation = directOrderStockLocation;
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
		if (!(obj instanceof StockConfig)) return false;

		final StockConfig other = (StockConfig) obj;
		if (this.getId() != null || other.getId() != null) {
			return Objects.equals(this.getId(), other.getId());
		}

		return Objects.equals(getCompany(), other.getCompany())
			&& (getCompany() != null);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("id", getId())
			.add("realizeStockMovesUponParcelPalletCollection", getRealizeStockMovesUponParcelPalletCollection())
			.add("plannedStockMoveAutomaticMail", getPlannedStockMoveAutomaticMail())
			.add("realStockMoveAutomaticMail", getRealStockMoveAutomaticMail())
			.add("logisticalFormAutomaticEmail", getLogisticalFormAutomaticEmail())
			.add("conformityCertificateTitle", getConformityCertificateTitle())
			.add("displayTrackNbrOnPickingPrinting", getDisplayTrackNbrOnPickingPrinting())
			.add("displayBarcodeOnPickingPrinting", getDisplayBarcodeOnPickingPrinting())
			.add("displayCustomerCodeOnPickingPrinting", getDisplayCustomerCodeOnPickingPrinting())
			.add("displayPartnerSeqOnPrinting", getDisplayPartnerSeqOnPrinting())
			.add("displayLineDetailsOnPrinting", getDisplayLineDetailsOnPrinting())
			.add("isWithReturnSurplus", getIsWithReturnSurplus())
			.omitNullValues()
			.toString();
	}
}
