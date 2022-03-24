package com.axelor.apps.sale.db;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import com.axelor.apps.account.db.PaymentCondition;
import com.axelor.apps.account.db.PaymentMode;
import com.axelor.apps.base.db.Address;
import com.axelor.apps.base.db.BankDetails;
import com.axelor.apps.base.db.Batch;
import com.axelor.apps.base.db.CancelReason;
import com.axelor.apps.base.db.Company;
import com.axelor.apps.base.db.Currency;
import com.axelor.apps.base.db.Duration;
import com.axelor.apps.base.db.Partner;
import com.axelor.apps.base.db.PriceList;
import com.axelor.apps.base.db.PrintingSettings;
import com.axelor.apps.base.db.TradingName;
import com.axelor.apps.crm.db.Opportunity;
import com.axelor.apps.stock.db.FreightCarrierMode;
import com.axelor.apps.stock.db.Incoterm;
import com.axelor.apps.stock.db.ShipmentMode;
import com.axelor.apps.stock.db.StockLocation;
import com.axelor.apps.stock.db.StockMove;
import com.axelor.apps.supplychain.db.Timetable;
import com.axelor.apps.supplychain.db.TimetableTemplate;
import com.axelor.auth.db.AuditableModel;
import com.axelor.auth.db.User;
import com.axelor.db.annotations.NameColumn;
import com.axelor.db.annotations.Track;
import com.axelor.db.annotations.TrackEvent;
import com.axelor.db.annotations.TrackField;
import com.axelor.db.annotations.TrackMessage;
import com.axelor.db.annotations.Widget;
import com.axelor.team.db.Team;
import com.google.common.base.MoreObjects;

@Entity
@Table(name = "SALE_SALE_ORDER", uniqueConstraints = { @UniqueConstraint(columnNames = { "saleOrderSeq", "company" }), @UniqueConstraint(columnNames = { "prestaShopId" }) }, indexes = { @Index(columnList = "company"), @Index(columnList = "fullName"), @Index(columnList = "currency"), @Index(columnList = "client_partner"), @Index(columnList = "contact_partner"), @Index(columnList = "main_invoicing_address"), @Index(columnList = "price_list"), @Index(columnList = "delivery_address"), @Index(columnList = "duration"), @Index(columnList = "salesperson_user"), @Index(columnList = "team"), @Index(columnList = "confirmed_by_user"), @Index(columnList = "cancel_reason"), @Index(columnList = "template_user"), @Index(columnList = "company_bank_details"), @Index(columnList = "opportunity"), @Index(columnList = "trading_name"), @Index(columnList = "printing_settings"), @Index(columnList = "payment_mode"), @Index(columnList = "payment_condition"), @Index(columnList = "stock_location"), @Index(columnList = "to_stock_location"), @Index(columnList = "timetable_template"), @Index(columnList = "shipment_mode"), @Index(columnList = "freight_carrier_mode"), @Index(columnList = "carrier_partner"), @Index(columnList = "forwarder_partner"), @Index(columnList = "incoterm"), @Index(columnList = "invoiced_partner"), @Index(columnList = "delivered_partner") })
@Track(fields = { @TrackField(name = "saleOrderSeq"), @TrackField(name = "clientPartner"), @TrackField(name = "statusSelect"), @TrackField(name = "creationDate", on = TrackEvent.CREATE), @TrackField(name = "confirmationDateTime", on = TrackEvent.UPDATE), @TrackField(name = "inTaxTotal") }, messages = { @TrackMessage(message = "Quotation/sale order created", condition = "true", on = TrackEvent.CREATE), @TrackMessage(message = "Draft quotation", condition = "statusSelect == 1", tag = "important"), @TrackMessage(message = "Finalized quotation", condition = "statusSelect == 2", tag = "info"), @TrackMessage(message = "Order confirmed", condition = "statusSelect == 3", tag = "success"), @TrackMessage(message = "Order completed", condition = "statusSelect == 4", tag = "success"), @TrackMessage(message = "Canceled", condition = "statusSelect == 5", tag = "warning") })
public class SaleOrder extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SALE_SALE_ORDER_SEQ")
	@SequenceGenerator(name = "SALE_SALE_ORDER_SEQ", sequenceName = "SALE_SALE_ORDER_SEQ", allocationSize = 1)
	private Long id;

	@Widget(title = "Company")
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Company company;

	@NameColumn
	private String fullName;

	@Widget(title = "Internal Number", readonly = true)
	private String saleOrderSeq;

	@Widget(title = "Currency", massUpdate = true)
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Currency currency;

	@Widget(title = "Customer reference")
	private String externalReference;

	@Widget(title = "Creation date", readonly = true)
	@NotNull
	private LocalDate creationDate;

	@Widget(title = "Confirmation date", readonly = true)
	private LocalDateTime confirmationDateTime;

	@Widget(title = "Batchs")
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Batch> batchSet;

	@Widget(title = "Blocked")
	private Boolean blockedOnCustCreditExceed = Boolean.FALSE;

	@Widget(title = "Manual Unblock")
	private Boolean manualUnblock = Boolean.FALSE;

	@Widget(title = "Title")
	private String title;

	@Widget(title = "Customer", massUpdate = true)
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Partner clientPartner;

	@Widget(title = "Customer contact")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Partner contactPartner;

	@Widget(title = "Main/Invoicing address")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Address mainInvoicingAddress;

	@Widget(title = "Main/Invoicing address", multiline = true)
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String mainInvoicingAddressStr;

	@Widget(title = "Price list")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private PriceList priceList;

	@Widget(title = "Delivery address")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Address deliveryAddress;

	@Widget(title = "Delivery address", multiline = true)
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String deliveryAddressStr;

	@Widget(title = "Print subtotal per line")
	private Boolean isToPrintLineSubTotal = Boolean.FALSE;

	@Widget(title = "In ATI")
	private Boolean inAti = Boolean.FALSE;

	@Widget(title = "Validity duration")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Duration duration;

	@Widget(title = "End of validity")
	private LocalDate endOfValidityDate;

	@Widget(title = "Sale order lines")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "saleOrder", cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy("sequence")
	private List<SaleOrderLine> saleOrderLineList;

	@Widget(title = "Tax Lines", readonly = true)
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "saleOrder", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SaleOrderLineTax> saleOrderLineTaxList;

	@Widget(title = "Total W.T.", readonly = true)
	@Digits(integer = 18, fraction = 2)
	private BigDecimal exTaxTotal = BigDecimal.ZERO;

	@Widget(title = "Total tax", readonly = true)
	@Digits(integer = 18, fraction = 2)
	private BigDecimal taxTotal = BigDecimal.ZERO;

	@Widget(title = "Total A.T.I.", readonly = true)
	@Digits(integer = 18, fraction = 2)
	private BigDecimal inTaxTotal = BigDecimal.ZERO;

	@Widget(title = "Advance payment total", readonly = true)
	@Digits(integer = 18, fraction = 2)
	private BigDecimal advanceTotal = BigDecimal.ZERO;

	@Widget(title = "Amount to be spread over the timetable", readonly = true, copyable = false)
	@Digits(integer = 18, fraction = 2)
	private BigDecimal amountToBeSpreadOverTheTimetable = BigDecimal.ZERO;

	@Widget(title = "Total W.T.", readonly = true)
	@Digits(integer = 18, fraction = 2)
	private BigDecimal companyExTaxTotal = BigDecimal.ZERO;

	@Widget(title = "Total cost in company currency", hidden = true)
	private BigDecimal companyCostTotal = BigDecimal.ZERO;

	@Widget(title = "Status", readonly = true, selection = "sale.order.status.select")
	private Integer statusSelect = 0;

	@Widget(title = "Accounted Revenue")
	private BigDecimal accountedRevenue = BigDecimal.ZERO;

	@Widget(title = "Total cost price")
	private BigDecimal totalCostPrice = BigDecimal.ZERO;

	@Widget(title = "Gross Profit")
	private BigDecimal totalGrossMargin = BigDecimal.ZERO;

	@Widget(title = "Margin (%)")
	private BigDecimal marginRate = BigDecimal.ZERO;

	@Widget(title = "Markup (%)")
	private BigDecimal markup = BigDecimal.ZERO;

	@Widget(title = "Salesperson", massUpdate = true)
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private User salespersonUser;

	@Widget(title = "Team", massUpdate = true)
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Team team;

	@Widget(title = "Comment to display")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String description;

	@Widget(title = "Internal note")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String internalNote;

	@Widget(title = "Order Date")
	private LocalDate orderDate;

	@Widget(title = "Order Number")
	private String orderNumber;

	@Widget(title = "Invoiced first date")
	private LocalDate invoicedFirstDate;

	@Widget(title = "Next invoiced period start date", readonly = true)
	private LocalDate nextInvPeriodStartDate;

	@Widget(title = "Confirmed by", readonly = true)
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private User confirmedByUser;

	@Widget(title = "Template", hidden = true)
	private Boolean template = Boolean.FALSE;

	@Widget(title = "Version Number", readonly = true)
	private Integer versionNumber = 1;

	@Widget(title = "Cancel reason")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private CancelReason cancelReason;

	@Widget(title = "Cancel Reason")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String cancelReasonStr;

	@Widget(title = "Template created by", readonly = true)
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private User templateUser;

	@Widget(title = "Hide Discount on prints")
	private Boolean hideDiscount = Boolean.FALSE;

	@Widget(title = "Estimated shipping date")
	private LocalDate deliveryDate;

	@Widget(title = "Delivery conditions", multiline = true)
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String deliveryCondition;

	@Widget(title = "Specific package", multiline = true)
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String specificPackage;

	@Widget(title = "Advance Payments")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "saleOrder", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<AdvancePayment> advancePaymentList;

	@Widget(title = "Advance payment needed")
	private Boolean advancePaymentNeeded = Boolean.FALSE;

	@Widget(title = "Advance payment amount needed")
	private BigDecimal advancePaymentAmountNeeded = BigDecimal.ZERO;

	@Widget(title = "Company bank")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private BankDetails companyBankDetails;

	@Widget(title = "Specific notes")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String specificNotes;

	private Boolean orderBeingEdited = Boolean.FALSE;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Opportunity opportunity;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private TradingName tradingName;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private PrintingSettings printingSettings;

	@Widget(title = "Group products on printings")
	private Boolean groupProductsOnPrintings = Boolean.FALSE;

	@Widget(title = "Periodicity type", selection = "sale.subscription.periodicity.type.select")
	private Integer periodicityTypeSelect = 2;

	@Widget(title = "Period duration")
	@Min(1)
	private Integer numberOfPeriods = 1;

	@Widget(title = "Contract start date")
	private LocalDate contractStartDate;

	@Widget(title = "Contract period in months")
	private Integer contractPeriodInMonths = 0;

	@Widget(title = "End of current contract period")
	private LocalDate currentContractPeriodEndDate;

	@Widget(title = "Contract end date")
	private LocalDate contractEndDate;

	@Widget(title = "Tacit agreement")
	private Boolean isTacitAgreement = Boolean.FALSE;

	@Widget(title = "Notice period in days")
	private Integer noticePeriodInDays = 0;

	@Widget(title = "Next invoicing date")
	private LocalDate nextInvoicingDate;

	@Widget(title = "Next invoicing start period date")
	private LocalDate nextInvoicingStartPeriodDate;

	@Widget(title = "Next invoicing end period date")
	private LocalDate nextInvoicingEndPeriodDate;

	@Widget(title = "Comment")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String subscriptionComment;

	@Widget(title = "Subscription text to display")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String subscriptionText;

	@Widget(title = "Comment to display on invoice")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String invoiceComments;

	@Widget(title = "Comment to display on delivery")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String deliveryComments;

	@Widget(title = "Comment to display on picking order")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String pickingOrderComments;

	@Widget(title = "Comment to display on proforma")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String proformaComments;

	@Widget(title = "Payment mode")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private PaymentMode paymentMode;

	@Widget(title = "Payment condition")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private PaymentCondition paymentCondition;

	@Widget(title = "Delivery State", readonly = true, selection = "sale.order.delivery.state")
	private Integer deliveryState = 1;

	@Widget(title = "Stock location")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private StockLocation stockLocation;

	@Widget(title = "To stock location")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private StockLocation toStockLocation;

	@Widget(title = "Date of shipment")
	private LocalDate shipmentDate;

	@Widget(title = "Amount invoiced W.T.", readonly = true)
	private BigDecimal amountInvoiced = BigDecimal.ZERO;

	@Widget(title = "Type", selection = "supplychain.sale.order.type.select")
	@NotNull
	private Integer saleOrderTypeSelect = 1;

	@Widget(title = "Schedule line list")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "saleOrder", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SaleOrderScheduleLine> saleOrderScheduleLineList;

	@Widget(title = "Timetable", copyable = false)
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "saleOrder", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Timetable> timetableList;

	@Widget(title = "Interco")
	private Boolean interco = Boolean.FALSE;

	private Boolean createdByInterco = Boolean.FALSE;

	@Widget(title = "Expected realisation date", copyable = false)
	private LocalDate expectedRealisationDate;

	@Widget(title = "Timetable computation date", copyable = false)
	private LocalDate computationDate;

	@Widget(title = "Timetable template", copyable = false)
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private TimetableTemplate timetableTemplate;

	@Widget(title = "Standard delay (days)")
	private Integer standardDelay = 0;

	@Widget(title = "Stock moves")
	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private List<StockMove> stockMoveList;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private ShipmentMode shipmentMode;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private FreightCarrierMode freightCarrierMode;

	@Widget(title = "Carrier")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Partner carrierPartner;

	@Widget(title = "Forwarder")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Partner forwarderPartner;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Incoterm incoterm;

	@Widget(title = "Is certificate of conformity required")
	private Boolean isNeedingConformityCertificate = Boolean.FALSE;

	@Widget(title = "Is ISPM 15 required")
	private Boolean isIspmRequired = Boolean.FALSE;

	@Widget(title = "Direct order")
	private Boolean directOrderLocation = Boolean.FALSE;

	@Widget(title = "Invoiced partner")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Partner invoicedPartner;

	@Widget(title = "Delivery partner")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Partner deliveredPartner;

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

	public SaleOrder() {
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

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getSaleOrderSeq() {
		return saleOrderSeq;
	}

	public void setSaleOrderSeq(String saleOrderSeq) {
		this.saleOrderSeq = saleOrderSeq;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public String getExternalReference() {
		return externalReference;
	}

	public void setExternalReference(String externalReference) {
		this.externalReference = externalReference;
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	public LocalDateTime getConfirmationDateTime() {
		return confirmationDateTime;
	}

	public void setConfirmationDateTime(LocalDateTime confirmationDateTime) {
		this.confirmationDateTime = confirmationDateTime;
	}

	public Set<Batch> getBatchSet() {
		return batchSet;
	}

	public void setBatchSet(Set<Batch> batchSet) {
		this.batchSet = batchSet;
	}

	/**
	 * Add the given {@link Batch} item to the {@code batchSet}.
	 *
	 * @param item
	 *            the item to add
	 */
	public void addBatchSetItem(Batch item) {
		if (getBatchSet() == null) {
			setBatchSet(new HashSet<>());
		}
		getBatchSet().add(item);
	}

	/**
	 * Remove the given {@link Batch} item from the {@code batchSet}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeBatchSetItem(Batch item) {
		if (getBatchSet() == null) {
			return;
		}
		getBatchSet().remove(item);
	}

	/**
	 * Clear the {@code batchSet} collection.
	 *
	 */
	public void clearBatchSet() {
		if (getBatchSet() != null) {
			getBatchSet().clear();
		}
	}

	public Boolean getBlockedOnCustCreditExceed() {
		return blockedOnCustCreditExceed == null ? Boolean.FALSE : blockedOnCustCreditExceed;
	}

	public void setBlockedOnCustCreditExceed(Boolean blockedOnCustCreditExceed) {
		this.blockedOnCustCreditExceed = blockedOnCustCreditExceed;
	}

	public Boolean getManualUnblock() {
		return manualUnblock == null ? Boolean.FALSE : manualUnblock;
	}

	public void setManualUnblock(Boolean manualUnblock) {
		this.manualUnblock = manualUnblock;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Partner getClientPartner() {
		return clientPartner;
	}

	public void setClientPartner(Partner clientPartner) {
		this.clientPartner = clientPartner;
	}

	public Partner getContactPartner() {
		return contactPartner;
	}

	public void setContactPartner(Partner contactPartner) {
		this.contactPartner = contactPartner;
	}

	public Address getMainInvoicingAddress() {
		return mainInvoicingAddress;
	}

	public void setMainInvoicingAddress(Address mainInvoicingAddress) {
		this.mainInvoicingAddress = mainInvoicingAddress;
	}

	public String getMainInvoicingAddressStr() {
		return mainInvoicingAddressStr;
	}

	public void setMainInvoicingAddressStr(String mainInvoicingAddressStr) {
		this.mainInvoicingAddressStr = mainInvoicingAddressStr;
	}

	public PriceList getPriceList() {
		return priceList;
	}

	public void setPriceList(PriceList priceList) {
		this.priceList = priceList;
	}

	public Address getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(Address deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public String getDeliveryAddressStr() {
		return deliveryAddressStr;
	}

	public void setDeliveryAddressStr(String deliveryAddressStr) {
		this.deliveryAddressStr = deliveryAddressStr;
	}

	public Boolean getIsToPrintLineSubTotal() {
		return isToPrintLineSubTotal == null ? Boolean.FALSE : isToPrintLineSubTotal;
	}

	public void setIsToPrintLineSubTotal(Boolean isToPrintLineSubTotal) {
		this.isToPrintLineSubTotal = isToPrintLineSubTotal;
	}

	public Boolean getInAti() {
		return inAti == null ? Boolean.FALSE : inAti;
	}

	public void setInAti(Boolean inAti) {
		this.inAti = inAti;
	}

	public Duration getDuration() {
		return duration;
	}

	public void setDuration(Duration duration) {
		this.duration = duration;
	}

	public LocalDate getEndOfValidityDate() {
		return endOfValidityDate;
	}

	public void setEndOfValidityDate(LocalDate endOfValidityDate) {
		this.endOfValidityDate = endOfValidityDate;
	}

	public List<SaleOrderLine> getSaleOrderLineList() {
		return saleOrderLineList;
	}

	public void setSaleOrderLineList(List<SaleOrderLine> saleOrderLineList) {
		this.saleOrderLineList = saleOrderLineList;
	}

	/**
	 * Add the given {@link SaleOrderLine} item to the {@code saleOrderLineList}.
	 *
	 * <p>
	 * It sets {@code item.saleOrder = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addSaleOrderLineListItem(SaleOrderLine item) {
		if (getSaleOrderLineList() == null) {
			setSaleOrderLineList(new ArrayList<>());
		}
		getSaleOrderLineList().add(item);
		item.setSaleOrder(this);
	}

	/**
	 * Remove the given {@link SaleOrderLine} item from the {@code saleOrderLineList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeSaleOrderLineListItem(SaleOrderLine item) {
		if (getSaleOrderLineList() == null) {
			return;
		}
		getSaleOrderLineList().remove(item);
	}

	/**
	 * Clear the {@code saleOrderLineList} collection.
	 *
	 * <p>
	 * If you have to query {@link SaleOrderLine} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearSaleOrderLineList() {
		if (getSaleOrderLineList() != null) {
			getSaleOrderLineList().clear();
		}
	}

	public List<SaleOrderLineTax> getSaleOrderLineTaxList() {
		return saleOrderLineTaxList;
	}

	public void setSaleOrderLineTaxList(List<SaleOrderLineTax> saleOrderLineTaxList) {
		this.saleOrderLineTaxList = saleOrderLineTaxList;
	}

	/**
	 * Add the given {@link SaleOrderLineTax} item to the {@code saleOrderLineTaxList}.
	 *
	 * <p>
	 * It sets {@code item.saleOrder = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addSaleOrderLineTaxListItem(SaleOrderLineTax item) {
		if (getSaleOrderLineTaxList() == null) {
			setSaleOrderLineTaxList(new ArrayList<>());
		}
		getSaleOrderLineTaxList().add(item);
		item.setSaleOrder(this);
	}

	/**
	 * Remove the given {@link SaleOrderLineTax} item from the {@code saleOrderLineTaxList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeSaleOrderLineTaxListItem(SaleOrderLineTax item) {
		if (getSaleOrderLineTaxList() == null) {
			return;
		}
		getSaleOrderLineTaxList().remove(item);
	}

	/**
	 * Clear the {@code saleOrderLineTaxList} collection.
	 *
	 * <p>
	 * If you have to query {@link SaleOrderLineTax} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearSaleOrderLineTaxList() {
		if (getSaleOrderLineTaxList() != null) {
			getSaleOrderLineTaxList().clear();
		}
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

	public BigDecimal getAdvanceTotal() {
		return advanceTotal == null ? BigDecimal.ZERO : advanceTotal;
	}

	public void setAdvanceTotal(BigDecimal advanceTotal) {
		this.advanceTotal = advanceTotal;
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

	public BigDecimal getCompanyCostTotal() {
		return companyCostTotal == null ? BigDecimal.ZERO : companyCostTotal;
	}

	public void setCompanyCostTotal(BigDecimal companyCostTotal) {
		this.companyCostTotal = companyCostTotal;
	}

	public Integer getStatusSelect() {
		return statusSelect == null ? 0 : statusSelect;
	}

	public void setStatusSelect(Integer statusSelect) {
		this.statusSelect = statusSelect;
	}

	public BigDecimal getAccountedRevenue() {
		return accountedRevenue == null ? BigDecimal.ZERO : accountedRevenue;
	}

	public void setAccountedRevenue(BigDecimal accountedRevenue) {
		this.accountedRevenue = accountedRevenue;
	}

	public BigDecimal getTotalCostPrice() {
		return totalCostPrice == null ? BigDecimal.ZERO : totalCostPrice;
	}

	public void setTotalCostPrice(BigDecimal totalCostPrice) {
		this.totalCostPrice = totalCostPrice;
	}

	public BigDecimal getTotalGrossMargin() {
		return totalGrossMargin == null ? BigDecimal.ZERO : totalGrossMargin;
	}

	public void setTotalGrossMargin(BigDecimal totalGrossMargin) {
		this.totalGrossMargin = totalGrossMargin;
	}

	public BigDecimal getMarginRate() {
		return marginRate == null ? BigDecimal.ZERO : marginRate;
	}

	public void setMarginRate(BigDecimal marginRate) {
		this.marginRate = marginRate;
	}

	public BigDecimal getMarkup() {
		return markup == null ? BigDecimal.ZERO : markup;
	}

	public void setMarkup(BigDecimal markup) {
		this.markup = markup;
	}

	public User getSalespersonUser() {
		return salespersonUser;
	}

	public void setSalespersonUser(User salespersonUser) {
		this.salespersonUser = salespersonUser;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getInternalNote() {
		return internalNote;
	}

	public void setInternalNote(String internalNote) {
		this.internalNote = internalNote;
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public LocalDate getInvoicedFirstDate() {
		return invoicedFirstDate;
	}

	public void setInvoicedFirstDate(LocalDate invoicedFirstDate) {
		this.invoicedFirstDate = invoicedFirstDate;
	}

	public LocalDate getNextInvPeriodStartDate() {
		return nextInvPeriodStartDate;
	}

	public void setNextInvPeriodStartDate(LocalDate nextInvPeriodStartDate) {
		this.nextInvPeriodStartDate = nextInvPeriodStartDate;
	}

	public User getConfirmedByUser() {
		return confirmedByUser;
	}

	public void setConfirmedByUser(User confirmedByUser) {
		this.confirmedByUser = confirmedByUser;
	}

	public Boolean getTemplate() {
		return template == null ? Boolean.FALSE : template;
	}

	public void setTemplate(Boolean template) {
		this.template = template;
	}

	public Integer getVersionNumber() {
		return versionNumber == null ? 0 : versionNumber;
	}

	public void setVersionNumber(Integer versionNumber) {
		this.versionNumber = versionNumber;
	}

	public CancelReason getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(CancelReason cancelReason) {
		this.cancelReason = cancelReason;
	}

	public String getCancelReasonStr() {
		return cancelReasonStr;
	}

	public void setCancelReasonStr(String cancelReasonStr) {
		this.cancelReasonStr = cancelReasonStr;
	}

	public User getTemplateUser() {
		return templateUser;
	}

	public void setTemplateUser(User templateUser) {
		this.templateUser = templateUser;
	}

	public Boolean getHideDiscount() {
		return hideDiscount == null ? Boolean.FALSE : hideDiscount;
	}

	public void setHideDiscount(Boolean hideDiscount) {
		this.hideDiscount = hideDiscount;
	}

	public LocalDate getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(LocalDate deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getDeliveryCondition() {
		return deliveryCondition;
	}

	public void setDeliveryCondition(String deliveryCondition) {
		this.deliveryCondition = deliveryCondition;
	}

	public String getSpecificPackage() {
		return specificPackage;
	}

	public void setSpecificPackage(String specificPackage) {
		this.specificPackage = specificPackage;
	}

	public List<AdvancePayment> getAdvancePaymentList() {
		return advancePaymentList;
	}

	public void setAdvancePaymentList(List<AdvancePayment> advancePaymentList) {
		this.advancePaymentList = advancePaymentList;
	}

	/**
	 * Add the given {@link AdvancePayment} item to the {@code advancePaymentList}.
	 *
	 * <p>
	 * It sets {@code item.saleOrder = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addAdvancePaymentListItem(AdvancePayment item) {
		if (getAdvancePaymentList() == null) {
			setAdvancePaymentList(new ArrayList<>());
		}
		getAdvancePaymentList().add(item);
		item.setSaleOrder(this);
	}

	/**
	 * Remove the given {@link AdvancePayment} item from the {@code advancePaymentList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeAdvancePaymentListItem(AdvancePayment item) {
		if (getAdvancePaymentList() == null) {
			return;
		}
		getAdvancePaymentList().remove(item);
	}

	/**
	 * Clear the {@code advancePaymentList} collection.
	 *
	 * <p>
	 * If you have to query {@link AdvancePayment} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearAdvancePaymentList() {
		if (getAdvancePaymentList() != null) {
			getAdvancePaymentList().clear();
		}
	}

	public Boolean getAdvancePaymentNeeded() {
		return advancePaymentNeeded == null ? Boolean.FALSE : advancePaymentNeeded;
	}

	public void setAdvancePaymentNeeded(Boolean advancePaymentNeeded) {
		this.advancePaymentNeeded = advancePaymentNeeded;
	}

	public BigDecimal getAdvancePaymentAmountNeeded() {
		return advancePaymentAmountNeeded == null ? BigDecimal.ZERO : advancePaymentAmountNeeded;
	}

	public void setAdvancePaymentAmountNeeded(BigDecimal advancePaymentAmountNeeded) {
		this.advancePaymentAmountNeeded = advancePaymentAmountNeeded;
	}

	public BankDetails getCompanyBankDetails() {
		return companyBankDetails;
	}

	public void setCompanyBankDetails(BankDetails companyBankDetails) {
		this.companyBankDetails = companyBankDetails;
	}

	public String getSpecificNotes() {
		return specificNotes;
	}

	public void setSpecificNotes(String specificNotes) {
		this.specificNotes = specificNotes;
	}

	public Boolean getOrderBeingEdited() {
		return orderBeingEdited == null ? Boolean.FALSE : orderBeingEdited;
	}

	public void setOrderBeingEdited(Boolean orderBeingEdited) {
		this.orderBeingEdited = orderBeingEdited;
	}

	public Opportunity getOpportunity() {
		return opportunity;
	}

	public void setOpportunity(Opportunity opportunity) {
		this.opportunity = opportunity;
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

	public Integer getPeriodicityTypeSelect() {
		return periodicityTypeSelect == null ? 0 : periodicityTypeSelect;
	}

	public void setPeriodicityTypeSelect(Integer periodicityTypeSelect) {
		this.periodicityTypeSelect = periodicityTypeSelect;
	}

	public Integer getNumberOfPeriods() {
		return numberOfPeriods == null ? 0 : numberOfPeriods;
	}

	public void setNumberOfPeriods(Integer numberOfPeriods) {
		this.numberOfPeriods = numberOfPeriods;
	}

	public LocalDate getContractStartDate() {
		return contractStartDate;
	}

	public void setContractStartDate(LocalDate contractStartDate) {
		this.contractStartDate = contractStartDate;
	}

	public Integer getContractPeriodInMonths() {
		return contractPeriodInMonths == null ? 0 : contractPeriodInMonths;
	}

	public void setContractPeriodInMonths(Integer contractPeriodInMonths) {
		this.contractPeriodInMonths = contractPeriodInMonths;
	}

	public LocalDate getCurrentContractPeriodEndDate() {
		return currentContractPeriodEndDate;
	}

	public void setCurrentContractPeriodEndDate(LocalDate currentContractPeriodEndDate) {
		this.currentContractPeriodEndDate = currentContractPeriodEndDate;
	}

	public LocalDate getContractEndDate() {
		return contractEndDate;
	}

	public void setContractEndDate(LocalDate contractEndDate) {
		this.contractEndDate = contractEndDate;
	}

	public Boolean getIsTacitAgreement() {
		return isTacitAgreement == null ? Boolean.FALSE : isTacitAgreement;
	}

	public void setIsTacitAgreement(Boolean isTacitAgreement) {
		this.isTacitAgreement = isTacitAgreement;
	}

	public Integer getNoticePeriodInDays() {
		return noticePeriodInDays == null ? 0 : noticePeriodInDays;
	}

	public void setNoticePeriodInDays(Integer noticePeriodInDays) {
		this.noticePeriodInDays = noticePeriodInDays;
	}

	public LocalDate getNextInvoicingDate() {
		return nextInvoicingDate;
	}

	public void setNextInvoicingDate(LocalDate nextInvoicingDate) {
		this.nextInvoicingDate = nextInvoicingDate;
	}

	public LocalDate getNextInvoicingStartPeriodDate() {
		return nextInvoicingStartPeriodDate;
	}

	public void setNextInvoicingStartPeriodDate(LocalDate nextInvoicingStartPeriodDate) {
		this.nextInvoicingStartPeriodDate = nextInvoicingStartPeriodDate;
	}

	public LocalDate getNextInvoicingEndPeriodDate() {
		return nextInvoicingEndPeriodDate;
	}

	public void setNextInvoicingEndPeriodDate(LocalDate nextInvoicingEndPeriodDate) {
		this.nextInvoicingEndPeriodDate = nextInvoicingEndPeriodDate;
	}

	public String getSubscriptionComment() {
		return subscriptionComment;
	}

	public void setSubscriptionComment(String subscriptionComment) {
		this.subscriptionComment = subscriptionComment;
	}

	public String getSubscriptionText() {
		return subscriptionText;
	}

	public void setSubscriptionText(String subscriptionText) {
		this.subscriptionText = subscriptionText;
	}

	public String getInvoiceComments() {
		return invoiceComments;
	}

	public void setInvoiceComments(String invoiceComments) {
		this.invoiceComments = invoiceComments;
	}

	public String getDeliveryComments() {
		return deliveryComments;
	}

	public void setDeliveryComments(String deliveryComments) {
		this.deliveryComments = deliveryComments;
	}

	public String getPickingOrderComments() {
		return pickingOrderComments;
	}

	public void setPickingOrderComments(String pickingOrderComments) {
		this.pickingOrderComments = pickingOrderComments;
	}

	public String getProformaComments() {
		return proformaComments;
	}

	public void setProformaComments(String proformaComments) {
		this.proformaComments = proformaComments;
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

	public Integer getDeliveryState() {
		return deliveryState == null ? 0 : deliveryState;
	}

	public void setDeliveryState(Integer deliveryState) {
		this.deliveryState = deliveryState;
	}

	public StockLocation getStockLocation() {
		return stockLocation;
	}

	public void setStockLocation(StockLocation stockLocation) {
		this.stockLocation = stockLocation;
	}

	public StockLocation getToStockLocation() {
		return toStockLocation;
	}

	public void setToStockLocation(StockLocation toStockLocation) {
		this.toStockLocation = toStockLocation;
	}

	public LocalDate getShipmentDate() {
		return shipmentDate;
	}

	public void setShipmentDate(LocalDate shipmentDate) {
		this.shipmentDate = shipmentDate;
	}

	public BigDecimal getAmountInvoiced() {
		return amountInvoiced == null ? BigDecimal.ZERO : amountInvoiced;
	}

	public void setAmountInvoiced(BigDecimal amountInvoiced) {
		this.amountInvoiced = amountInvoiced;
	}

	public Integer getSaleOrderTypeSelect() {
		return saleOrderTypeSelect == null ? 0 : saleOrderTypeSelect;
	}

	public void setSaleOrderTypeSelect(Integer saleOrderTypeSelect) {
		this.saleOrderTypeSelect = saleOrderTypeSelect;
	}

	public List<SaleOrderScheduleLine> getSaleOrderScheduleLineList() {
		return saleOrderScheduleLineList;
	}

	public void setSaleOrderScheduleLineList(List<SaleOrderScheduleLine> saleOrderScheduleLineList) {
		this.saleOrderScheduleLineList = saleOrderScheduleLineList;
	}

	/**
	 * Add the given {@link SaleOrderScheduleLine} item to the {@code saleOrderScheduleLineList}.
	 *
	 * <p>
	 * It sets {@code item.saleOrder = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addSaleOrderScheduleLineListItem(SaleOrderScheduleLine item) {
		if (getSaleOrderScheduleLineList() == null) {
			setSaleOrderScheduleLineList(new ArrayList<>());
		}
		getSaleOrderScheduleLineList().add(item);
		item.setSaleOrder(this);
	}

	/**
	 * Remove the given {@link SaleOrderScheduleLine} item from the {@code saleOrderScheduleLineList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeSaleOrderScheduleLineListItem(SaleOrderScheduleLine item) {
		if (getSaleOrderScheduleLineList() == null) {
			return;
		}
		getSaleOrderScheduleLineList().remove(item);
	}

	/**
	 * Clear the {@code saleOrderScheduleLineList} collection.
	 *
	 * <p>
	 * If you have to query {@link SaleOrderScheduleLine} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearSaleOrderScheduleLineList() {
		if (getSaleOrderScheduleLineList() != null) {
			getSaleOrderScheduleLineList().clear();
		}
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
	 * It sets {@code item.saleOrder = this} to ensure the proper relationship.
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
		item.setSaleOrder(this);
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

	public LocalDate getExpectedRealisationDate() {
		return expectedRealisationDate;
	}

	public void setExpectedRealisationDate(LocalDate expectedRealisationDate) {
		this.expectedRealisationDate = expectedRealisationDate;
	}

	public LocalDate getComputationDate() {
		return computationDate;
	}

	public void setComputationDate(LocalDate computationDate) {
		this.computationDate = computationDate;
	}

	public TimetableTemplate getTimetableTemplate() {
		return timetableTemplate;
	}

	public void setTimetableTemplate(TimetableTemplate timetableTemplate) {
		this.timetableTemplate = timetableTemplate;
	}

	public Integer getStandardDelay() {
		return standardDelay == null ? 0 : standardDelay;
	}

	public void setStandardDelay(Integer standardDelay) {
		this.standardDelay = standardDelay;
	}

	public List<StockMove> getStockMoveList() {
		return stockMoveList;
	}

	public void setStockMoveList(List<StockMove> stockMoveList) {
		this.stockMoveList = stockMoveList;
	}

	/**
	 * Add the given {@link StockMove} item to the {@code stockMoveList}.
	 *
	 * @param item
	 *            the item to add
	 */
	public void addStockMoveListItem(StockMove item) {
		if (getStockMoveList() == null) {
			setStockMoveList(new ArrayList<>());
		}
		getStockMoveList().add(item);
	}

	/**
	 * Remove the given {@link StockMove} item from the {@code stockMoveList}.
	 *
	 * <p>
	 * It sets {@code item.null = null} to break the relationship.
	 * </p>
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeStockMoveListItem(StockMove item) {
		if (getStockMoveList() == null) {
			return;
		}
		getStockMoveList().remove(item);
	}

	/**
	 * Clear the {@code stockMoveList} collection.
	 *
	 * <p>
	 * It sets {@code item.null = null} to break the relationship.
	 * </p>
	 */
	public void clearStockMoveList() {
		if (getStockMoveList() != null) {
			getStockMoveList().clear();
		}
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

	public Partner getCarrierPartner() {
		return carrierPartner;
	}

	public void setCarrierPartner(Partner carrierPartner) {
		this.carrierPartner = carrierPartner;
	}

	public Partner getForwarderPartner() {
		return forwarderPartner;
	}

	public void setForwarderPartner(Partner forwarderPartner) {
		this.forwarderPartner = forwarderPartner;
	}

	public Incoterm getIncoterm() {
		return incoterm;
	}

	public void setIncoterm(Incoterm incoterm) {
		this.incoterm = incoterm;
	}

	public Boolean getIsNeedingConformityCertificate() {
		return isNeedingConformityCertificate == null ? Boolean.FALSE : isNeedingConformityCertificate;
	}

	public void setIsNeedingConformityCertificate(Boolean isNeedingConformityCertificate) {
		this.isNeedingConformityCertificate = isNeedingConformityCertificate;
	}

	public Boolean getIsIspmRequired() {
		return isIspmRequired == null ? Boolean.FALSE : isIspmRequired;
	}

	public void setIsIspmRequired(Boolean isIspmRequired) {
		this.isIspmRequired = isIspmRequired;
	}

	public Boolean getDirectOrderLocation() {
		return directOrderLocation == null ? Boolean.FALSE : directOrderLocation;
	}

	public void setDirectOrderLocation(Boolean directOrderLocation) {
		this.directOrderLocation = directOrderLocation;
	}

	public Partner getInvoicedPartner() {
		return invoicedPartner;
	}

	public void setInvoicedPartner(Partner invoicedPartner) {
		this.invoicedPartner = invoicedPartner;
	}

	public Partner getDeliveredPartner() {
		return deliveredPartner;
	}

	public void setDeliveredPartner(Partner deliveredPartner) {
		this.deliveredPartner = deliveredPartner;
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
		if (!(obj instanceof SaleOrder)) return false;

		final SaleOrder other = (SaleOrder) obj;
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
			.add("fullName", getFullName())
			.add("saleOrderSeq", getSaleOrderSeq())
			.add("externalReference", getExternalReference())
			.add("creationDate", getCreationDate())
			.add("confirmationDateTime", getConfirmationDateTime())
			.add("blockedOnCustCreditExceed", getBlockedOnCustCreditExceed())
			.add("manualUnblock", getManualUnblock())
			.add("title", getTitle())
			.add("isToPrintLineSubTotal", getIsToPrintLineSubTotal())
			.add("inAti", getInAti())
			.add("endOfValidityDate", getEndOfValidityDate())
			.omitNullValues()
			.toString();
	}
}
