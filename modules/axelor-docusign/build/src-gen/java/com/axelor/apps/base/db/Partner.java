package com.axelor.apps.base.db;

import java.math.BigDecimal;
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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import com.axelor.apps.account.db.FiscalPosition;
import com.axelor.apps.message.db.EmailAddress;
import com.axelor.apps.sale.db.ComplementaryProduct;
import com.axelor.apps.sale.db.CustomerCatalog;
import com.axelor.auth.db.AuditableModel;
import com.axelor.auth.db.User;
import com.axelor.db.annotations.EqualsInclude;
import com.axelor.db.annotations.NameColumn;
import com.axelor.db.annotations.Widget;
import com.axelor.meta.db.MetaFile;
import com.axelor.team.db.Team;
import com.google.common.base.MoreObjects;

@Entity
@Table(name = "BASE_PARTNER", indexes = { @Index(columnList = "partner_category"), @Index(columnList = "job_title_function"), @Index(columnList = "language"), @Index(columnList = "email_address"), @Index(columnList = "sale_partner_price_list"), @Index(columnList = "purchase_partner_price_list"), @Index(columnList = "main_activity"), @Index(columnList = "trading_name"), @Index(columnList = "fullName"), @Index(columnList = "fiscal_position"), @Index(columnList = "main_address") })
public class Partner extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BASE_PARTNER_SEQ")
	@SequenceGenerator(name = "BASE_PARTNER_SEQ", sequenceName = "BASE_PARTNER_SEQ", allocationSize = 1)
	private Long id;

	@Widget(title = "Category", massUpdate = true)
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private PartnerCategory partnerCategory;

	@EqualsInclude
	@Widget(title = "Reference", readonly = true)
	@Column(unique = true)
	private String partnerSeq;

	@Widget(title = "Partner Type", selection = "partner.partner.type.select")
	private Integer partnerTypeSelect = 0;

	@Widget(title = "Civility", selection = "partner.title.type.select", massUpdate = true)
	private Integer titleSelect = 0;

	@Widget(title = "Name/Company Name")
	@NotNull
	private String name;

	@Widget(title = "First Name")
	private String firstName;

	@Widget(title = "Function")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Function jobTitleFunction;

	@Widget(title = "Photo")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private MetaFile picture;

	@Widget(title = "Customer")
	private Boolean isCustomer = Boolean.FALSE;

	@Widget(title = "Prospect")
	private Boolean isProspect = Boolean.FALSE;

	@Widget(title = "Supplier")
	private Boolean isSupplier = Boolean.FALSE;

	@Widget(title = "Employee")
	private Boolean isEmployee = Boolean.FALSE;

	@Widget(title = "Contact")
	private Boolean isContact = Boolean.FALSE;

	@Widget(title = "Language", massUpdate = true)
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Language language;

	@Widget(title = "Mother company", massUpdate = true)
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Partner parentPartner;

	@Widget(title = "Addresses")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "partner", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PartnerAddress> partnerAddressList;

	@Widget(title = "Contacts")
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Partner> contactPartnerSet;

	@Widget(title = "Main company")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Partner mainPartner;

	@Widget(title = "Source", massUpdate = true)
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Source source;

	@Widget(title = "Email")
	@OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private EmailAddress emailAddress;

	@Widget(title = "Fax")
	private String fax;

	@Widget(title = "Fixed phone")
	private String fixedPhone;

	@Widget(title = "Mobile phone")
	private String mobilePhone;

	@Widget(title = "Website")
	private String webSite;

	@Widget(title = "Dept./Div.")
	private String department;

	@Widget(title = "Companies associated to")
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Company> companySet;

	@Widget(title = "Bank Details list")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "partner", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<BankDetails> bankDetailsList;

	@Widget(title = "Currency", massUpdate = true)
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Currency currency;

	@Widget(title = "Sale price lists")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private PartnerPriceList salePartnerPriceList;

	@Widget(title = "Purchase price lists")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private PartnerPriceList purchasePartnerPriceList;

	@Widget(title = "Payment delay (Average in days)", massUpdate = true)
	private BigDecimal paymentDelay = BigDecimal.ZERO;

	@Widget(title = "Group products on printings")
	private Boolean groupProductsOnPrintings = Boolean.FALSE;

	@Widget(title = "Blocking follow-up List")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "partner", cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy("blockingToDate DESC")
	private List<Blocking> blockingList;

	@Widget(title = "Batches")
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Batch> batchSet;

	@Widget(title = "Assigned to", massUpdate = true)
	@JoinColumn(name = "user_id")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private User user;

	@Widget(title = "User")
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "partner", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private User linkedUser;

	@Widget(title = "Team", massUpdate = true)
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Team team;

	@Widget(title = "Reports to", massUpdate = true)
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Partner reportsTo;

	@Widget(title = "Description")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String description;

	@Widget(title = "Industry sector", massUpdate = true)
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private IndustrySector industrySector;

	@Widget(title = "Employees (Nbr)")
	@Min(0)
	private Integer nbrEmployees = 0;

	@Widget(title = "Turnover")
	private Integer saleTurnover = 0;

	@Widget(title = "Registration code")
	private String registrationCode;

	@Widget(title = "Main Activity")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private MainActivity mainActivity;

	@Widget(title = "Tax N°")
	private String taxNbr;

	@Widget(title = "N° Siren")
	private String siren;

	@Widget(title = "Internal Classification Number")
	private String nic;

	@Widget(title = "Agency")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private TradingName tradingName;

	@Widget(title = "Internal partner")
	private Boolean isInternal = Boolean.FALSE;

	@Widget(title = "Name")
	@NameColumn
	private String fullName;

	@Widget(title = "Name")
	private String simpleFullName;

	@Widget(title = "Fiscal position", massUpdate = true)
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private FiscalPosition fiscalPosition;

	@Widget(title = "Address")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Address mainAddress;

	@Widget(title = "Time slot")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String timeSlot;

	@Widget(title = "Companies")
	private String companyStr;

	@Widget(title = "Head office address")
	private String headOfficeAddress;

	@Widget(title = "Comment to display on invoice")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String invoiceComments;

	@Widget(title = "Comment to display on sale order")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String saleOrderComments;

	@Widget(title = "Comment to display on purchase order")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String purchaseOrderComments;

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

	@Widget(title = "Function / Business card")
	private String functionBusinessCard;

	@Widget(title = "Roles")
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<PartnerRole> partnerRoleSet;

	@Widget(title = "Fields")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String partnerAttrs;

	@Widget(title = "Fields")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String contactAttrs;

	@Widget(title = "Label to be displayed on sale orders", multiline = true)
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String saleOrderInformation;

	@Widget(title = "Specific tax note")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String specificTaxNote;

	@Widget(title = "Customer Catalog Lines")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customerPartner", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CustomerCatalog> customerCatalogList;

	@Widget(title = "Complementary product(s)")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "partner", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ComplementaryProduct> complementaryProductList;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public Partner() {
	}

	public Partner(String name) {
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

	public PartnerCategory getPartnerCategory() {
		return partnerCategory;
	}

	public void setPartnerCategory(PartnerCategory partnerCategory) {
		this.partnerCategory = partnerCategory;
	}

	public String getPartnerSeq() {
		return partnerSeq;
	}

	public void setPartnerSeq(String partnerSeq) {
		this.partnerSeq = partnerSeq;
	}

	public Integer getPartnerTypeSelect() {
		return partnerTypeSelect == null ? 0 : partnerTypeSelect;
	}

	public void setPartnerTypeSelect(Integer partnerTypeSelect) {
		this.partnerTypeSelect = partnerTypeSelect;
	}

	public Integer getTitleSelect() {
		return titleSelect == null ? 0 : titleSelect;
	}

	public void setTitleSelect(Integer titleSelect) {
		this.titleSelect = titleSelect;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Function getJobTitleFunction() {
		return jobTitleFunction;
	}

	public void setJobTitleFunction(Function jobTitleFunction) {
		this.jobTitleFunction = jobTitleFunction;
	}

	public MetaFile getPicture() {
		return picture;
	}

	public void setPicture(MetaFile picture) {
		this.picture = picture;
	}

	public Boolean getIsCustomer() {
		return isCustomer == null ? Boolean.FALSE : isCustomer;
	}

	public void setIsCustomer(Boolean isCustomer) {
		this.isCustomer = isCustomer;
	}

	public Boolean getIsProspect() {
		return isProspect == null ? Boolean.FALSE : isProspect;
	}

	public void setIsProspect(Boolean isProspect) {
		this.isProspect = isProspect;
	}

	public Boolean getIsSupplier() {
		return isSupplier == null ? Boolean.FALSE : isSupplier;
	}

	public void setIsSupplier(Boolean isSupplier) {
		this.isSupplier = isSupplier;
	}

	public Boolean getIsEmployee() {
		return isEmployee == null ? Boolean.FALSE : isEmployee;
	}

	public void setIsEmployee(Boolean isEmployee) {
		this.isEmployee = isEmployee;
	}

	public Boolean getIsContact() {
		return isContact == null ? Boolean.FALSE : isContact;
	}

	public void setIsContact(Boolean isContact) {
		this.isContact = isContact;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public Partner getParentPartner() {
		return parentPartner;
	}

	public void setParentPartner(Partner parentPartner) {
		this.parentPartner = parentPartner;
	}

	public List<PartnerAddress> getPartnerAddressList() {
		return partnerAddressList;
	}

	public void setPartnerAddressList(List<PartnerAddress> partnerAddressList) {
		this.partnerAddressList = partnerAddressList;
	}

	/**
	 * Add the given {@link PartnerAddress} item to the {@code partnerAddressList}.
	 *
	 * <p>
	 * It sets {@code item.partner = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addPartnerAddressListItem(PartnerAddress item) {
		if (getPartnerAddressList() == null) {
			setPartnerAddressList(new ArrayList<>());
		}
		getPartnerAddressList().add(item);
		item.setPartner(this);
	}

	/**
	 * Remove the given {@link PartnerAddress} item from the {@code partnerAddressList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removePartnerAddressListItem(PartnerAddress item) {
		if (getPartnerAddressList() == null) {
			return;
		}
		getPartnerAddressList().remove(item);
	}

	/**
	 * Clear the {@code partnerAddressList} collection.
	 *
	 * <p>
	 * If you have to query {@link PartnerAddress} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearPartnerAddressList() {
		if (getPartnerAddressList() != null) {
			getPartnerAddressList().clear();
		}
	}

	public Set<Partner> getContactPartnerSet() {
		return contactPartnerSet;
	}

	public void setContactPartnerSet(Set<Partner> contactPartnerSet) {
		this.contactPartnerSet = contactPartnerSet;
	}

	/**
	 * Add the given {@link Partner} item to the {@code contactPartnerSet}.
	 *
	 * @param item
	 *            the item to add
	 */
	public void addContactPartnerSetItem(Partner item) {
		if (getContactPartnerSet() == null) {
			setContactPartnerSet(new HashSet<>());
		}
		getContactPartnerSet().add(item);
	}

	/**
	 * Remove the given {@link Partner} item from the {@code contactPartnerSet}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeContactPartnerSetItem(Partner item) {
		if (getContactPartnerSet() == null) {
			return;
		}
		getContactPartnerSet().remove(item);
	}

	/**
	 * Clear the {@code contactPartnerSet} collection.
	 *
	 */
	public void clearContactPartnerSet() {
		if (getContactPartnerSet() != null) {
			getContactPartnerSet().clear();
		}
	}

	public Partner getMainPartner() {
		return mainPartner;
	}

	public void setMainPartner(Partner mainPartner) {
		this.mainPartner = mainPartner;
	}

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	public EmailAddress getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(EmailAddress emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getFixedPhone() {
		return fixedPhone;
	}

	public void setFixedPhone(String fixedPhone) {
		this.fixedPhone = fixedPhone;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Set<Company> getCompanySet() {
		return companySet;
	}

	public void setCompanySet(Set<Company> companySet) {
		this.companySet = companySet;
	}

	/**
	 * Add the given {@link Company} item to the {@code companySet}.
	 *
	 * @param item
	 *            the item to add
	 */
	public void addCompanySetItem(Company item) {
		if (getCompanySet() == null) {
			setCompanySet(new HashSet<>());
		}
		getCompanySet().add(item);
	}

	/**
	 * Remove the given {@link Company} item from the {@code companySet}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeCompanySetItem(Company item) {
		if (getCompanySet() == null) {
			return;
		}
		getCompanySet().remove(item);
	}

	/**
	 * Clear the {@code companySet} collection.
	 *
	 */
	public void clearCompanySet() {
		if (getCompanySet() != null) {
			getCompanySet().clear();
		}
	}

	public List<BankDetails> getBankDetailsList() {
		return bankDetailsList;
	}

	public void setBankDetailsList(List<BankDetails> bankDetailsList) {
		this.bankDetailsList = bankDetailsList;
	}

	/**
	 * Add the given {@link BankDetails} item to the {@code bankDetailsList}.
	 *
	 * <p>
	 * It sets {@code item.partner = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addBankDetailsListItem(BankDetails item) {
		if (getBankDetailsList() == null) {
			setBankDetailsList(new ArrayList<>());
		}
		getBankDetailsList().add(item);
		item.setPartner(this);
	}

	/**
	 * Remove the given {@link BankDetails} item from the {@code bankDetailsList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeBankDetailsListItem(BankDetails item) {
		if (getBankDetailsList() == null) {
			return;
		}
		getBankDetailsList().remove(item);
	}

	/**
	 * Clear the {@code bankDetailsList} collection.
	 *
	 * <p>
	 * If you have to query {@link BankDetails} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearBankDetailsList() {
		if (getBankDetailsList() != null) {
			getBankDetailsList().clear();
		}
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public PartnerPriceList getSalePartnerPriceList() {
		return salePartnerPriceList;
	}

	public void setSalePartnerPriceList(PartnerPriceList salePartnerPriceList) {
		this.salePartnerPriceList = salePartnerPriceList;
	}

	public PartnerPriceList getPurchasePartnerPriceList() {
		return purchasePartnerPriceList;
	}

	public void setPurchasePartnerPriceList(PartnerPriceList purchasePartnerPriceList) {
		this.purchasePartnerPriceList = purchasePartnerPriceList;
	}

	public BigDecimal getPaymentDelay() {
		return paymentDelay == null ? BigDecimal.ZERO : paymentDelay;
	}

	public void setPaymentDelay(BigDecimal paymentDelay) {
		this.paymentDelay = paymentDelay;
	}

	public Boolean getGroupProductsOnPrintings() {
		return groupProductsOnPrintings == null ? Boolean.FALSE : groupProductsOnPrintings;
	}

	public void setGroupProductsOnPrintings(Boolean groupProductsOnPrintings) {
		this.groupProductsOnPrintings = groupProductsOnPrintings;
	}

	public List<Blocking> getBlockingList() {
		return blockingList;
	}

	public void setBlockingList(List<Blocking> blockingList) {
		this.blockingList = blockingList;
	}

	/**
	 * Add the given {@link Blocking} item to the {@code blockingList}.
	 *
	 * <p>
	 * It sets {@code item.partner = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addBlockingListItem(Blocking item) {
		if (getBlockingList() == null) {
			setBlockingList(new ArrayList<>());
		}
		getBlockingList().add(item);
		item.setPartner(this);
	}

	/**
	 * Remove the given {@link Blocking} item from the {@code blockingList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeBlockingListItem(Blocking item) {
		if (getBlockingList() == null) {
			return;
		}
		getBlockingList().remove(item);
	}

	/**
	 * Clear the {@code blockingList} collection.
	 *
	 * <p>
	 * If you have to query {@link Blocking} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearBlockingList() {
		if (getBlockingList() != null) {
			getBlockingList().clear();
		}
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getLinkedUser() {
		return linkedUser;
	}

	public void setLinkedUser(User linkedUser) {
		if (getLinkedUser() != null) {
			getLinkedUser().setPartner(null);
		}
		if (linkedUser != null) {
			linkedUser.setPartner(this);
		}
		this.linkedUser = linkedUser;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Partner getReportsTo() {
		return reportsTo;
	}

	public void setReportsTo(Partner reportsTo) {
		this.reportsTo = reportsTo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public IndustrySector getIndustrySector() {
		return industrySector;
	}

	public void setIndustrySector(IndustrySector industrySector) {
		this.industrySector = industrySector;
	}

	public Integer getNbrEmployees() {
		return nbrEmployees == null ? 0 : nbrEmployees;
	}

	public void setNbrEmployees(Integer nbrEmployees) {
		this.nbrEmployees = nbrEmployees;
	}

	public Integer getSaleTurnover() {
		return saleTurnover == null ? 0 : saleTurnover;
	}

	public void setSaleTurnover(Integer saleTurnover) {
		this.saleTurnover = saleTurnover;
	}

	public String getRegistrationCode() {
		return registrationCode;
	}

	public void setRegistrationCode(String registrationCode) {
		this.registrationCode = registrationCode;
	}

	public MainActivity getMainActivity() {
		return mainActivity;
	}

	public void setMainActivity(MainActivity mainActivity) {
		this.mainActivity = mainActivity;
	}

	public String getTaxNbr() {
		return taxNbr;
	}

	public void setTaxNbr(String taxNbr) {
		this.taxNbr = taxNbr;
	}

	public String getSiren() {
		return siren;
	}

	public void setSiren(String siren) {
		this.siren = siren;
	}

	public String getNic() {
		return nic;
	}

	public void setNic(String nic) {
		this.nic = nic;
	}

	public TradingName getTradingName() {
		return tradingName;
	}

	public void setTradingName(TradingName tradingName) {
		this.tradingName = tradingName;
	}

	public Boolean getIsInternal() {
		return isInternal == null ? Boolean.FALSE : isInternal;
	}

	public void setIsInternal(Boolean isInternal) {
		this.isInternal = isInternal;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getSimpleFullName() {
		return simpleFullName;
	}

	public void setSimpleFullName(String simpleFullName) {
		this.simpleFullName = simpleFullName;
	}

	public FiscalPosition getFiscalPosition() {
		return fiscalPosition;
	}

	public void setFiscalPosition(FiscalPosition fiscalPosition) {
		this.fiscalPosition = fiscalPosition;
	}

	public Address getMainAddress() {
		return mainAddress;
	}

	public void setMainAddress(Address mainAddress) {
		this.mainAddress = mainAddress;
	}

	public String getTimeSlot() {
		return timeSlot;
	}

	public void setTimeSlot(String timeSlot) {
		this.timeSlot = timeSlot;
	}

	public String getCompanyStr() {
		return companyStr;
	}

	public void setCompanyStr(String companyStr) {
		this.companyStr = companyStr;
	}

	public String getHeadOfficeAddress() {
		return headOfficeAddress;
	}

	public void setHeadOfficeAddress(String headOfficeAddress) {
		this.headOfficeAddress = headOfficeAddress;
	}

	public String getInvoiceComments() {
		return invoiceComments;
	}

	public void setInvoiceComments(String invoiceComments) {
		this.invoiceComments = invoiceComments;
	}

	public String getSaleOrderComments() {
		return saleOrderComments;
	}

	public void setSaleOrderComments(String saleOrderComments) {
		this.saleOrderComments = saleOrderComments;
	}

	public String getPurchaseOrderComments() {
		return purchaseOrderComments;
	}

	public void setPurchaseOrderComments(String purchaseOrderComments) {
		this.purchaseOrderComments = purchaseOrderComments;
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

	public String getFunctionBusinessCard() {
		return functionBusinessCard;
	}

	public void setFunctionBusinessCard(String functionBusinessCard) {
		this.functionBusinessCard = functionBusinessCard;
	}

	public Set<PartnerRole> getPartnerRoleSet() {
		return partnerRoleSet;
	}

	public void setPartnerRoleSet(Set<PartnerRole> partnerRoleSet) {
		this.partnerRoleSet = partnerRoleSet;
	}

	/**
	 * Add the given {@link PartnerRole} item to the {@code partnerRoleSet}.
	 *
	 * @param item
	 *            the item to add
	 */
	public void addPartnerRoleSetItem(PartnerRole item) {
		if (getPartnerRoleSet() == null) {
			setPartnerRoleSet(new HashSet<>());
		}
		getPartnerRoleSet().add(item);
	}

	/**
	 * Remove the given {@link PartnerRole} item from the {@code partnerRoleSet}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removePartnerRoleSetItem(PartnerRole item) {
		if (getPartnerRoleSet() == null) {
			return;
		}
		getPartnerRoleSet().remove(item);
	}

	/**
	 * Clear the {@code partnerRoleSet} collection.
	 *
	 */
	public void clearPartnerRoleSet() {
		if (getPartnerRoleSet() != null) {
			getPartnerRoleSet().clear();
		}
	}

	public String getPartnerAttrs() {
		return partnerAttrs;
	}

	public void setPartnerAttrs(String partnerAttrs) {
		this.partnerAttrs = partnerAttrs;
	}

	public String getContactAttrs() {
		return contactAttrs;
	}

	public void setContactAttrs(String contactAttrs) {
		this.contactAttrs = contactAttrs;
	}

	public String getSaleOrderInformation() {
		return saleOrderInformation;
	}

	public void setSaleOrderInformation(String saleOrderInformation) {
		this.saleOrderInformation = saleOrderInformation;
	}

	public String getSpecificTaxNote() {
		return specificTaxNote;
	}

	public void setSpecificTaxNote(String specificTaxNote) {
		this.specificTaxNote = specificTaxNote;
	}

	public List<CustomerCatalog> getCustomerCatalogList() {
		return customerCatalogList;
	}

	public void setCustomerCatalogList(List<CustomerCatalog> customerCatalogList) {
		this.customerCatalogList = customerCatalogList;
	}

	/**
	 * Add the given {@link CustomerCatalog} item to the {@code customerCatalogList}.
	 *
	 * <p>
	 * It sets {@code item.customerPartner = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addCustomerCatalogListItem(CustomerCatalog item) {
		if (getCustomerCatalogList() == null) {
			setCustomerCatalogList(new ArrayList<>());
		}
		getCustomerCatalogList().add(item);
		item.setCustomerPartner(this);
	}

	/**
	 * Remove the given {@link CustomerCatalog} item from the {@code customerCatalogList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeCustomerCatalogListItem(CustomerCatalog item) {
		if (getCustomerCatalogList() == null) {
			return;
		}
		getCustomerCatalogList().remove(item);
	}

	/**
	 * Clear the {@code customerCatalogList} collection.
	 *
	 * <p>
	 * If you have to query {@link CustomerCatalog} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearCustomerCatalogList() {
		if (getCustomerCatalogList() != null) {
			getCustomerCatalogList().clear();
		}
	}

	public List<ComplementaryProduct> getComplementaryProductList() {
		return complementaryProductList;
	}

	public void setComplementaryProductList(List<ComplementaryProduct> complementaryProductList) {
		this.complementaryProductList = complementaryProductList;
	}

	/**
	 * Add the given {@link ComplementaryProduct} item to the {@code complementaryProductList}.
	 *
	 * <p>
	 * It sets {@code item.partner = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addComplementaryProductListItem(ComplementaryProduct item) {
		if (getComplementaryProductList() == null) {
			setComplementaryProductList(new ArrayList<>());
		}
		getComplementaryProductList().add(item);
		item.setPartner(this);
	}

	/**
	 * Remove the given {@link ComplementaryProduct} item from the {@code complementaryProductList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeComplementaryProductListItem(ComplementaryProduct item) {
		if (getComplementaryProductList() == null) {
			return;
		}
		getComplementaryProductList().remove(item);
	}

	/**
	 * Clear the {@code complementaryProductList} collection.
	 *
	 * <p>
	 * If you have to query {@link ComplementaryProduct} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearComplementaryProductList() {
		if (getComplementaryProductList() != null) {
			getComplementaryProductList().clear();
		}
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
		if (!(obj instanceof Partner)) return false;

		final Partner other = (Partner) obj;
		if (this.getId() != null || other.getId() != null) {
			return Objects.equals(this.getId(), other.getId());
		}

		return Objects.equals(getPartnerSeq(), other.getPartnerSeq())
			&& (getPartnerSeq() != null);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("id", getId())
			.add("partnerSeq", getPartnerSeq())
			.add("partnerTypeSelect", getPartnerTypeSelect())
			.add("titleSelect", getTitleSelect())
			.add("name", getName())
			.add("firstName", getFirstName())
			.add("isCustomer", getIsCustomer())
			.add("isProspect", getIsProspect())
			.add("isSupplier", getIsSupplier())
			.add("isEmployee", getIsEmployee())
			.add("isContact", getIsContact())
			.add("fax", getFax())
			.omitNullValues()
			.toString();
	}
}
