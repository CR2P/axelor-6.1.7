package com.axelor.apps.purchase.db;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.axelor.apps.account.db.Budget;
import com.axelor.apps.account.db.PaymentCondition;
import com.axelor.apps.account.db.PaymentMode;
import com.axelor.apps.base.db.BankDetails;
import com.axelor.apps.base.db.Company;
import com.axelor.apps.base.db.Currency;
import com.axelor.apps.base.db.Partner;
import com.axelor.apps.base.db.PriceList;
import com.axelor.apps.base.db.PrintingSettings;
import com.axelor.apps.base.db.TradingName;
import com.axelor.apps.stock.db.FreightCarrierMode;
import com.axelor.apps.stock.db.ShipmentMode;
import com.axelor.apps.stock.db.StockLocation;
import com.axelor.apps.supplychain.db.Timetable;
import com.axelor.auth.db.AuditableModel;
import com.axelor.auth.db.User;
import com.axelor.db.annotations.NameColumn;
import com.axelor.db.annotations.Track;
import com.axelor.db.annotations.TrackEvent;
import com.axelor.db.annotations.TrackField;
import com.axelor.db.annotations.TrackMessage;
import com.axelor.db.annotations.VirtualColumn;
import com.axelor.db.annotations.Widget;
import com.axelor.team.db.Team;
import com.google.common.base.MoreObjects;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "PURCHASE_PURCHASE_ORDER", uniqueConstraints = { @UniqueConstraint(columnNames = { "purchaseOrderSeq", "company" }) }, indexes = { @Index(columnList = "fullName"), @Index(columnList = "company"), @Index(columnList = "supplier_partner"), @Index(columnList = "contact_partner"), @Index(columnList = "price_list"), @Index(columnList = "team"), @Index(columnList = "buyer_user"), @Index(columnList = "currency"), @Index(columnList = "validated_by_user"), @Index(columnList = "company_bank_details"), @Index(columnList = "trading_name"), @Index(columnList = "printing_settings"), @Index(columnList = "shipment_mode"), @Index(columnList = "freight_carrier_mode"), @Index(columnList = "payment_mode"), @Index(columnList = "payment_condition"), @Index(columnList = "stock_location"), @Index(columnList = "from_stock_location"), @Index(columnList = "budget") })
@Track(fields = { @TrackField(name = "purchaseOrderSeq"), @TrackField(name = "supplierPartner"), @TrackField(name = "statusSelect", on = TrackEvent.UPDATE) }, messages = { @TrackMessage(message = "Purchase order created", condition = "true", on = TrackEvent.CREATE), @TrackMessage(message = "Draft", condition = "statusSelect == 1", tag = "important"), @TrackMessage(message = "Requested", condition = "statusSelect == 2", tag = "important"), @TrackMessage(message = "Validated", condition = "statusSelect == 3", tag = "info"), @TrackMessage(message = "Finished", condition = "statusSelect == 4", tag = "success"), @TrackMessage(message = "Canceled", condition = "statusSelect == 5", tag = "warning") })
public class PurchaseOrder extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PURCHASE_PURCHASE_ORDER_SEQ")
	@SequenceGenerator(name = "PURCHASE_PURCHASE_ORDER_SEQ", sequenceName = "PURCHASE_PURCHASE_ORDER_SEQ", allocationSize = 1)
	private Long id;

	@NameColumn
	@VirtualColumn
	@Access(AccessType.PROPERTY)
	private String fullName;

	@Widget(title = "Ref.", readonly = true)
	private String purchaseOrderSeq;

	@Widget(title = "Company")
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Company company;

	@Widget(title = "Supplier")
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Partner supplierPartner;

	@Widget(title = "Contact")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Partner contactPartner;

	@Widget(title = "Price list")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private PriceList priceList;

	@Widget(title = "Team")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Team team;

	@Widget(title = "Buyer")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private User buyerUser;

	@Widget(title = "Currency")
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Currency currency;

	@Widget(title = "Estimated delivery Date")
	private LocalDate deliveryDate;

	@Widget(title = "Order Date")
	private LocalDate orderDate;

	@Widget(title = "Products list")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "purchaseOrder", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PurchaseOrderLine> purchaseOrderLineList;

	@Widget(title = "Status", readonly = true, selection = "purchase.purchase.order.status.select")
	private Integer statusSelect = 0;

	@Widget(title = "Supplier ref.")
	private String externalReference;

	@Widget(title = "Internal Ref.")
	private String internalReference;

	@Widget(title = "Receipt State", readonly = true, selection = "purchase.order.receipt.state")
	private Integer receiptState = 1;

	@Widget(title = "Tax Lines")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "purchaseOrder", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PurchaseOrderLineTax> purchaseOrderLineTaxList;

	@Widget(title = "Validated by", readonly = true)
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private User validatedByUser;

	@Widget(title = "Validation date", readonly = true)
	private LocalDate validationDate;

	@Widget(title = "Total W.T.", readonly = true)
	@Digits(integer = 18, fraction = 2)
	private BigDecimal exTaxTotal = BigDecimal.ZERO;

	@Widget(title = "Total Tax", readonly = true)
	@Digits(integer = 18, fraction = 2)
	private BigDecimal taxTotal = BigDecimal.ZERO;

	@Widget(title = "Total A.T.I.", readonly = true)
	@Digits(integer = 18, fraction = 2)
	private BigDecimal inTaxTotal = BigDecimal.ZERO;

	@Widget(title = "Amount to be spread over the timetable", readonly = true)
	@Digits(integer = 18, fraction = 2)
	private BigDecimal amountToBeSpreadOverTheTimetable = BigDecimal.ZERO;

	@Widget(title = "Total W.T.", readonly = true)
	@Digits(integer = 18, fraction = 2)
	private BigDecimal companyExTaxTotal = BigDecimal.ZERO;

	@Widget(title = "Description To Display", multiline = true)
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String notes;

	@Widget(title = "Internal Note", multiline = true)
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String internalNote;

	@Widget(title = "Version Number", readonly = true)
	private Integer versionNumber = 1;

	@Widget(title = "In ATI")
	private Boolean inAti = Boolean.FALSE;

	@Widget(title = "Company bank")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private BankDetails companyBankDetails;

	@Widget(title = "Display price on requested purchase printing")
	private Boolean displayPriceOnQuotationRequest = Boolean.FALSE;

	@Widget(title = "Message for requesting prices")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String priceRequest;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private TradingName tradingName;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private PrintingSettings printingSettings;

	@Widget(title = "Group products on printings")
	private Boolean groupProductsOnPrintings = Boolean.FALSE;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private ShipmentMode shipmentMode;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private FreightCarrierMode freightCarrierMode;

	@Widget(title = "Payment mode")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private PaymentMode paymentMode;

	@Widget(title = "Payment condition")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private PaymentCondition paymentCondition;

	@Widget(title = "Amount invoiced W.T.", readonly = true)
	private BigDecimal amountInvoiced = BigDecimal.ZERO;

	@Widget(title = "Stock location")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private StockLocation stockLocation;

	@Widget(title = "From stock location")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private StockLocation fromStockLocation;

	@Widget(title = "Timetable")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "purchaseOrder", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Timetable> timetableList;

	@Widget(title = "Expected realisation date")
	private LocalDate expectedRealisationDate;

	@Widget(title = "Interco")
	private Boolean interco = Boolean.FALSE;

	private Boolean createdByInterco = Boolean.FALSE;

	@Widget(title = "Budget")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Budget budget;

	private Long generatedSaleOrderId = 0L;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public PurchaseOrder() {
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
		if(purchaseOrderSeq==null){
			return " ";
		}
		return purchaseOrderSeq + "-" + supplierPartner.getName();
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPurchaseOrderSeq() {
		return purchaseOrderSeq;
	}

	public void setPurchaseOrderSeq(String purchaseOrderSeq) {
		this.purchaseOrderSeq = purchaseOrderSeq;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Partner getSupplierPartner() {
		return supplierPartner;
	}

	public void setSupplierPartner(Partner supplierPartner) {
		this.supplierPartner = supplierPartner;
	}

	public Partner getContactPartner() {
		return contactPartner;
	}

	public void setContactPartner(Partner contactPartner) {
		this.contactPartner = contactPartner;
	}

	public PriceList getPriceList() {
		return priceList;
	}

	public void setPriceList(PriceList priceList) {
		this.priceList = priceList;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public User getBuyerUser() {
		return buyerUser;
	}

	public void setBuyerUser(User buyerUser) {
		this.buyerUser = buyerUser;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public LocalDate getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(LocalDate deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}

	public List<PurchaseOrderLine> getPurchaseOrderLineList() {
		return purchaseOrderLineList;
	}

	public void setPurchaseOrderLineList(List<PurchaseOrderLine> purchaseOrderLineList) {
		this.purchaseOrderLineList = purchaseOrderLineList;
	}

	/**
	 * Add the given {@link PurchaseOrderLine} item to the {@code purchaseOrderLineList}.
	 *
	 * <p>
	 * It sets {@code item.purchaseOrder = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addPurchaseOrderLineListItem(PurchaseOrderLine item) {
		if (getPurchaseOrderLineList() == null) {
			setPurchaseOrderLineList(new ArrayList<>());
		}
		getPurchaseOrderLineList().add(item);
		item.setPurchaseOrder(this);
	}

	/**
	 * Remove the given {@link PurchaseOrderLine} item from the {@code purchaseOrderLineList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removePurchaseOrderLineListItem(PurchaseOrderLine item) {
		if (getPurchaseOrderLineList() == null) {
			return;
		}
		getPurchaseOrderLineList().remove(item);
	}

	/**
	 * Clear the {@code purchaseOrderLineList} collection.
	 *
	 * <p>
	 * If you have to query {@link PurchaseOrderLine} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearPurchaseOrderLineList() {
		if (getPurchaseOrderLineList() != null) {
			getPurchaseOrderLineList().clear();
		}
	}

	public Integer getStatusSelect() {
		return statusSelect == null ? 0 : statusSelect;
	}

	public void setStatusSelect(Integer statusSelect) {
		this.statusSelect = statusSelect;
	}

	public String getExternalReference() {
		return externalReference;
	}

	public void setExternalReference(String externalReference) {
		this.externalReference = externalReference;
	}

	public String getInternalReference() {
		return internalReference;
	}

	public void setInternalReference(String internalReference) {
		this.internalReference = internalReference;
	}

	public Integer getReceiptState() {
		return receiptState == null ? 0 : receiptState;
	}

	public void setReceiptState(Integer receiptState) {
		this.receiptState = receiptState;
	}

	public List<PurchaseOrderLineTax> getPurchaseOrderLineTaxList() {
		return purchaseOrderLineTaxList;
	}

	public void setPurchaseOrderLineTaxList(List<PurchaseOrderLineTax> purchaseOrderLineTaxList) {
		this.purchaseOrderLineTaxList = purchaseOrderLineTaxList;
	}

	/**
	 * Add the given {@link PurchaseOrderLineTax} item to the {@code purchaseOrderLineTaxList}.
	 *
	 * <p>
	 * It sets {@code item.purchaseOrder = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addPurchaseOrderLineTaxListItem(PurchaseOrderLineTax item) {
		if (getPurchaseOrderLineTaxList() == null) {
			setPurchaseOrderLineTaxList(new ArrayList<>());
		}
		getPurchaseOrderLineTaxList().add(item);
		item.setPurchaseOrder(this);
	}

	/**
	 * Remove the given {@link PurchaseOrderLineTax} item from the {@code purchaseOrderLineTaxList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removePurchaseOrderLineTaxListItem(PurchaseOrderLineTax item) {
		if (getPurchaseOrderLineTaxList() == null) {
			return;
		}
		getPurchaseOrderLineTaxList().remove(item);
	}

	/**
	 * Clear the {@code purchaseOrderLineTaxList} collection.
	 *
	 * <p>
	 * If you have to query {@link PurchaseOrderLineTax} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearPurchaseOrderLineTaxList() {
		if (getPurchaseOrderLineTaxList() != null) {
			getPurchaseOrderLineTaxList().clear();
		}
	}

	public User getValidatedByUser() {
		return validatedByUser;
	}

	public void setValidatedByUser(User validatedByUser) {
		this.validatedByUser = validatedByUser;
	}

	public LocalDate getValidationDate() {
		return validationDate;
	}

	public void setValidationDate(LocalDate validationDate) {
		this.validationDate = validationDate;
	}

	public BigDecimal getExTaxTotal() {
		return exTaxTotal == null ? BigDecimal.ZERO : exTaxTotal;
	}

	public void setExTaxTotal(BigDecimal exTaxTotal) {
		this.exTaxTotal = exTaxTotal;
	}

	public BigDecimal getTaxTotal() {
		return taxTotal == null ? BigDecimal.ZERO : taxTotal;
	}

	public void setTaxTotal(BigDecimal taxTotal) {
		this.taxTotal = taxTotal;
	}

	public BigDecimal getInTaxTotal() {
		return inTaxTotal == null ? BigDecimal.ZERO : inTaxTotal;
	}

	public void setInTaxTotal(BigDecimal inTaxTotal) {
		this.inTaxTotal = inTaxTotal;
	}

	public BigDecimal getAmountToBeSpreadOverTheTimetable() {
		return amountToBeSpreadOverTheTimetable == null ? BigDecimal.ZERO : amountToBeSpreadOverTheTimetable;
	}

	public void setAmountToBeSpreadOverTheTimetable(BigDecimal amountToBeSpreadOverTheTimetable) {
		this.amountToBeSpreadOverTheTimetable = amountToBeSpreadOverTheTimetable;
	}

	public BigDecimal getCompanyExTaxTotal() {
		return companyExTaxTotal == null ? BigDecimal.ZERO : companyExTaxTotal;
	}

	public void setCompanyExTaxTotal(BigDecimal companyExTaxTotal) {
		this.companyExTaxTotal = companyExTaxTotal;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getInternalNote() {
		return internalNote;
	}

	public void setInternalNote(String internalNote) {
		this.internalNote = internalNote;
	}

	public Integer getVersionNumber() {
		return versionNumber == null ? 0 : versionNumber;
	}

	public void setVersionNumber(Integer versionNumber) {
		this.versionNumber = versionNumber;
	}

	public Boolean getInAti() {
		return inAti == null ? Boolean.FALSE : inAti;
	}

	public void setInAti(Boolean inAti) {
		this.inAti = inAti;
	}

	public BankDetails getCompanyBankDetails() {
		return companyBankDetails;
	}

	public void setCompanyBankDetails(BankDetails companyBankDetails) {
		this.companyBankDetails = companyBankDetails;
	}

	public Boolean getDisplayPriceOnQuotationRequest() {
		return displayPriceOnQuotationRequest == null ? Boolean.FALSE : displayPriceOnQuotationRequest;
	}

	public void setDisplayPriceOnQuotationRequest(Boolean displayPriceOnQuotationRequest) {
		this.displayPriceOnQuotationRequest = displayPriceOnQuotationRequest;
	}

	public String getPriceRequest() {
		return priceRequest;
	}

	public void setPriceRequest(String priceRequest) {
		this.priceRequest = priceRequest;
	}

	public TradingName getTradingName() {
		return tradingName;
	}

	public void setTradingName(TradingName tradingName) {
		this.tradingName = tradingName;
	}

	public PrintingSettings getPrintingSettings() {
		return printingSettings;
	}

	public void setPrintingSettings(PrintingSettings printingSettings) {
		this.printingSettings = printingSettings;
	}

	public Boolean getGroupProductsOnPrintings() {
		return groupProductsOnPrintings == null ? Boolean.FALSE : groupProductsOnPrintings;
	}

	public void setGroupProductsOnPrintings(Boolean groupProductsOnPrintings) {
		this.groupProductsOnPrintings = groupProductsOnPrintings;
	}

	public ShipmentMode getShipmentMode() {
		return shipmentMode;
	}

	public void setShipmentMode(ShipmentMode shipmentMode) {
		this.shipmentMode = shipmentMode;
	}

	public FreightCarrierMode getFreightCarrierMode() {
		return freightCarrierMode;
	}

	public void setFreightCarrierMode(FreightCarrierMode freightCarrierMode) {
		this.freightCarrierMode = freightCarrierMode;
	}

	public PaymentMode getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(PaymentMode paymentMode) {
		this.paymentMode = paymentMode;
	}

	public PaymentCondition getPaymentCondition() {
		return paymentCondition;
	}

	public void setPaymentCondition(PaymentCondition paymentCondition) {
		this.paymentCondition = paymentCondition;
	}

	public BigDecimal getAmountInvoiced() {
		return amountInvoiced == null ? BigDecimal.ZERO : amountInvoiced;
	}

	public void setAmountInvoiced(BigDecimal amountInvoiced) {
		this.amountInvoiced = amountInvoiced;
	}

	public StockLocation getStockLocation() {
		return stockLocation;
	}

	public void setStockLocation(StockLocation stockLocation) {
		this.stockLocation = stockLocation;
	}

	public StockLocation getFromStockLocation() {
		return fromStockLocation;
	}

	public void setFromStockLocation(StockLocation fromStockLocation) {
		this.fromStockLocation = fromStockLocation;
	}

	public List<Timetable> getTimetableList() {
		return timetableList;
	}

	public void setTimetableList(List<Timetable> timetableList) {
		this.timetableList = timetableList;
	}

	/**
	 * Add the given {@link Timetable} item to the {@code timetableList}.
	 *
	 * <p>
	 * It sets {@code item.purchaseOrder = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addTimetableListItem(Timetable item) {
		if (getTimetableList() == null) {
			setTimetableList(new ArrayList<>());
		}
		getTimetableList().add(item);
		item.setPurchaseOrder(this);
	}

	/**
	 * Remove the given {@link Timetable} item from the {@code timetableList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeTimetableListItem(Timetable item) {
		if (getTimetableList() == null) {
			return;
		}
		getTimetableList().remove(item);
	}

	/**
	 * Clear the {@code timetableList} collection.
	 *
	 * <p>
	 * If you have to query {@link Timetable} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearTimetableList() {
		if (getTimetableList() != null) {
			getTimetableList().clear();
		}
	}

	public LocalDate getExpectedRealisationDate() {
		return expectedRealisationDate;
	}

	public void setExpectedRealisationDate(LocalDate expectedRealisationDate) {
		this.expectedRealisationDate = expectedRealisationDate;
	}

	public Boolean getInterco() {
		return interco == null ? Boolean.FALSE : interco;
	}

	public void setInterco(Boolean interco) {
		this.interco = interco;
	}

	public Boolean getCreatedByInterco() {
		return createdByInterco == null ? Boolean.FALSE : createdByInterco;
	}

	public void setCreatedByInterco(Boolean createdByInterco) {
		this.createdByInterco = createdByInterco;
	}

	public Budget getBudget() {
		return budget;
	}

	public void setBudget(Budget budget) {
		this.budget = budget;
	}

	public Long getGeneratedSaleOrderId() {
		return generatedSaleOrderId == null ? 0L : generatedSaleOrderId;
	}

	public void setGeneratedSaleOrderId(Long generatedSaleOrderId) {
		this.generatedSaleOrderId = generatedSaleOrderId;
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
		if (!(obj instanceof PurchaseOrder)) return false;

		final PurchaseOrder other = (PurchaseOrder) obj;
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
			.add("purchaseOrderSeq", getPurchaseOrderSeq())
			.add("deliveryDate", getDeliveryDate())
			.add("orderDate", getOrderDate())
			.add("statusSelect", getStatusSelect())
			.add("externalReference", getExternalReference())
			.add("internalReference", getInternalReference())
			.add("receiptState", getReceiptState())
			.add("validationDate", getValidationDate())
			.add("exTaxTotal", getExTaxTotal())
			.add("taxTotal", getTaxTotal())
			.add("inTaxTotal", getInTaxTotal())
			.omitNullValues()
			.toString();
	}
}
