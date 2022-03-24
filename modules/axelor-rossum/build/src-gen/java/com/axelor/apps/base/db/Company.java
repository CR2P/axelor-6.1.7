package com.axelor.apps.base.db;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Cacheable;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import com.axelor.apps.account.db.AccountConfig;
import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.EqualsInclude;
import com.axelor.db.annotations.Widget;
import com.axelor.meta.db.MetaFile;
import com.google.common.base.MoreObjects;

@Entity
@Cacheable
@Table(name = "BASE_COMPANY", indexes = { @Index(columnList = "address"), @Index(columnList = "partner"), @Index(columnList = "parent"), @Index(columnList = "logo"), @Index(columnList = "currency"), @Index(columnList = "default_bank_details"), @Index(columnList = "printing_settings"), @Index(columnList = "weekly_planning"), @Index(columnList = "public_holiday_events_planning"), @Index(columnList = "language") })
public class Company extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BASE_COMPANY_SEQ")
	@SequenceGenerator(name = "BASE_COMPANY_SEQ", sequenceName = "BASE_COMPANY_SEQ", allocationSize = 1)
	private Long id;

	@EqualsInclude
	@Widget(title = "Name")
	@NotNull
	@Column(unique = true)
	private String name;

	@EqualsInclude
	@Widget(title = "Code")
	@NotNull
	@Column(unique = true)
	private String code;

	@Widget(title = "Address", massUpdate = true)
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Address address;

	@Widget(title = "Partner")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Partner partner;

	@Widget(title = "Parent company", massUpdate = true)
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Company parent;

	@Widget(title = "Company departments")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CompanyDepartment> companyDepartmentList;

	@Widget(title = "Notes")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String notes;

	@Widget(title = "Logo")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private MetaFile logo;

	@Widget(title = "Currency", massUpdate = true)
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Currency currency;

	@Widget(title = "Customers payment delay (Average in days)")
	private BigDecimal customerPaymentDelay = BigDecimal.ZERO;

	@Widget(title = "Suppliers payment delay (Average in days)")
	private BigDecimal supplierPaymentDelay = BigDecimal.ZERO;

	@Widget(title = "Default Bank Account")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private BankDetails defaultBankDetails;

	@Widget(title = "Bank accounts")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<BankDetails> bankDetailsList;

	@Widget(title = "Printing Settings", massUpdate = true)
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private PrintingSettings printingSettings;

	@Widget(title = "Partner Type", selection = "company.partner.type.select", massUpdate = true)
	private Integer defaultPartnerTypeSelect = 1;

	@Widget(title = "Partner default category", selection = "company.partner.category.select", massUpdate = true)
	private Integer defaultPartnerCategorySelect = 0;

	@Widget(title = "Trading names")
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<TradingName> tradingNameSet;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TradingNamePrintingSettings> tradingNamePrintingSettingsList;

	@Widget(massUpdate = true)
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private WeeklyPlanning weeklyPlanning;

	@Widget(title = "Public Holiday Planning", massUpdate = true)
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private EventsPlanning publicHolidayEventsPlanning;

	@Widget(title = "Language", massUpdate = true)
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Language language;

	@Widget(title = "Width")
	@Min(0)
	private Integer width = 0;

	@Widget(title = "Height", help = "Maximum height should be 60 px.")
	@Min(0)
	@Max(60)
	private Integer height = 0;

	@Widget(title = "Attached partners")
	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private List<Partner> partnerList;

	@Widget(title = "Time Zone", selection = "company.timezone.select")
	private String timezone;

	@Widget(title = "Accounting configuration")
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "company", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private AccountConfig accountConfig;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public Company() {
	}

	public Company(String name, String code) {
		this.name = name;
		this.code = code;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Partner getPartner() {
		return partner;
	}

	public void setPartner(Partner partner) {
		this.partner = partner;
	}

	public Company getParent() {
		return parent;
	}

	public void setParent(Company parent) {
		this.parent = parent;
	}

	public List<CompanyDepartment> getCompanyDepartmentList() {
		return companyDepartmentList;
	}

	public void setCompanyDepartmentList(List<CompanyDepartment> companyDepartmentList) {
		this.companyDepartmentList = companyDepartmentList;
	}

	/**
	 * Add the given {@link CompanyDepartment} item to the {@code companyDepartmentList}.
	 *
	 * <p>
	 * It sets {@code item.company = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addCompanyDepartmentListItem(CompanyDepartment item) {
		if (getCompanyDepartmentList() == null) {
			setCompanyDepartmentList(new ArrayList<>());
		}
		getCompanyDepartmentList().add(item);
		item.setCompany(this);
	}

	/**
	 * Remove the given {@link CompanyDepartment} item from the {@code companyDepartmentList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeCompanyDepartmentListItem(CompanyDepartment item) {
		if (getCompanyDepartmentList() == null) {
			return;
		}
		getCompanyDepartmentList().remove(item);
	}

	/**
	 * Clear the {@code companyDepartmentList} collection.
	 *
	 * <p>
	 * If you have to query {@link CompanyDepartment} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearCompanyDepartmentList() {
		if (getCompanyDepartmentList() != null) {
			getCompanyDepartmentList().clear();
		}
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public MetaFile getLogo() {
		return logo;
	}

	public void setLogo(MetaFile logo) {
		this.logo = logo;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public BigDecimal getCustomerPaymentDelay() {
		return customerPaymentDelay == null ? BigDecimal.ZERO : customerPaymentDelay;
	}

	public void setCustomerPaymentDelay(BigDecimal customerPaymentDelay) {
		this.customerPaymentDelay = customerPaymentDelay;
	}

	public BigDecimal getSupplierPaymentDelay() {
		return supplierPaymentDelay == null ? BigDecimal.ZERO : supplierPaymentDelay;
	}

	public void setSupplierPaymentDelay(BigDecimal supplierPaymentDelay) {
		this.supplierPaymentDelay = supplierPaymentDelay;
	}

	public BankDetails getDefaultBankDetails() {
		return defaultBankDetails;
	}

	public void setDefaultBankDetails(BankDetails defaultBankDetails) {
		this.defaultBankDetails = defaultBankDetails;
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
	 * It sets {@code item.company = this} to ensure the proper relationship.
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
		item.setCompany(this);
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

	public PrintingSettings getPrintingSettings() {
		return printingSettings;
	}

	public void setPrintingSettings(PrintingSettings printingSettings) {
		this.printingSettings = printingSettings;
	}

	public Integer getDefaultPartnerTypeSelect() {
		return defaultPartnerTypeSelect == null ? 0 : defaultPartnerTypeSelect;
	}

	public void setDefaultPartnerTypeSelect(Integer defaultPartnerTypeSelect) {
		this.defaultPartnerTypeSelect = defaultPartnerTypeSelect;
	}

	public Integer getDefaultPartnerCategorySelect() {
		return defaultPartnerCategorySelect == null ? 0 : defaultPartnerCategorySelect;
	}

	public void setDefaultPartnerCategorySelect(Integer defaultPartnerCategorySelect) {
		this.defaultPartnerCategorySelect = defaultPartnerCategorySelect;
	}

	public Set<TradingName> getTradingNameSet() {
		return tradingNameSet;
	}

	public void setTradingNameSet(Set<TradingName> tradingNameSet) {
		this.tradingNameSet = tradingNameSet;
	}

	/**
	 * Add the given {@link TradingName} item to the {@code tradingNameSet}.
	 *
	 * @param item
	 *            the item to add
	 */
	public void addTradingNameSetItem(TradingName item) {
		if (getTradingNameSet() == null) {
			setTradingNameSet(new HashSet<>());
		}
		getTradingNameSet().add(item);
	}

	/**
	 * Remove the given {@link TradingName} item from the {@code tradingNameSet}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeTradingNameSetItem(TradingName item) {
		if (getTradingNameSet() == null) {
			return;
		}
		getTradingNameSet().remove(item);
	}

	/**
	 * Clear the {@code tradingNameSet} collection.
	 *
	 */
	public void clearTradingNameSet() {
		if (getTradingNameSet() != null) {
			getTradingNameSet().clear();
		}
	}

	public List<TradingNamePrintingSettings> getTradingNamePrintingSettingsList() {
		return tradingNamePrintingSettingsList;
	}

	public void setTradingNamePrintingSettingsList(List<TradingNamePrintingSettings> tradingNamePrintingSettingsList) {
		this.tradingNamePrintingSettingsList = tradingNamePrintingSettingsList;
	}

	/**
	 * Add the given {@link TradingNamePrintingSettings} item to the {@code tradingNamePrintingSettingsList}.
	 *
	 * <p>
	 * It sets {@code item.company = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addTradingNamePrintingSettingsListItem(TradingNamePrintingSettings item) {
		if (getTradingNamePrintingSettingsList() == null) {
			setTradingNamePrintingSettingsList(new ArrayList<>());
		}
		getTradingNamePrintingSettingsList().add(item);
		item.setCompany(this);
	}

	/**
	 * Remove the given {@link TradingNamePrintingSettings} item from the {@code tradingNamePrintingSettingsList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeTradingNamePrintingSettingsListItem(TradingNamePrintingSettings item) {
		if (getTradingNamePrintingSettingsList() == null) {
			return;
		}
		getTradingNamePrintingSettingsList().remove(item);
	}

	/**
	 * Clear the {@code tradingNamePrintingSettingsList} collection.
	 *
	 * <p>
	 * If you have to query {@link TradingNamePrintingSettings} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearTradingNamePrintingSettingsList() {
		if (getTradingNamePrintingSettingsList() != null) {
			getTradingNamePrintingSettingsList().clear();
		}
	}

	public WeeklyPlanning getWeeklyPlanning() {
		return weeklyPlanning;
	}

	public void setWeeklyPlanning(WeeklyPlanning weeklyPlanning) {
		this.weeklyPlanning = weeklyPlanning;
	}

	public EventsPlanning getPublicHolidayEventsPlanning() {
		return publicHolidayEventsPlanning;
	}

	public void setPublicHolidayEventsPlanning(EventsPlanning publicHolidayEventsPlanning) {
		this.publicHolidayEventsPlanning = publicHolidayEventsPlanning;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public Integer getWidth() {
		return width == null ? 0 : width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	/**
	 * Maximum height should be 60 px.
	 *
	 * @return the property value
	 */
	public Integer getHeight() {
		return height == null ? 0 : height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public List<Partner> getPartnerList() {
		return partnerList;
	}

	public void setPartnerList(List<Partner> partnerList) {
		this.partnerList = partnerList;
	}

	/**
	 * Add the given {@link Partner} item to the {@code partnerList}.
	 *
	 * @param item
	 *            the item to add
	 */
	public void addPartnerListItem(Partner item) {
		if (getPartnerList() == null) {
			setPartnerList(new ArrayList<>());
		}
		getPartnerList().add(item);
	}

	/**
	 * Remove the given {@link Partner} item from the {@code partnerList}.
	 *
	 * <p>
	 * It sets {@code item.null = null} to break the relationship.
	 * </p>
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removePartnerListItem(Partner item) {
		if (getPartnerList() == null) {
			return;
		}
		getPartnerList().remove(item);
	}

	/**
	 * Clear the {@code partnerList} collection.
	 *
	 * <p>
	 * It sets {@code item.null = null} to break the relationship.
	 * </p>
	 */
	public void clearPartnerList() {
		if (getPartnerList() != null) {
			getPartnerList().clear();
		}
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public AccountConfig getAccountConfig() {
		return accountConfig;
	}

	public void setAccountConfig(AccountConfig accountConfig) {
		if (getAccountConfig() != null) {
			getAccountConfig().setCompany(null);
		}
		if (accountConfig != null) {
			accountConfig.setCompany(this);
		}
		this.accountConfig = accountConfig;
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
		if (!(obj instanceof Company)) return false;

		final Company other = (Company) obj;
		if (this.getId() != null || other.getId() != null) {
			return Objects.equals(this.getId(), other.getId());
		}

		return Objects.equals(getName(), other.getName())
			&& Objects.equals(getCode(), other.getCode())
			&& (getName() != null
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
			.add("code", getCode())
			.add("customerPaymentDelay", getCustomerPaymentDelay())
			.add("supplierPaymentDelay", getSupplierPaymentDelay())
			.add("defaultPartnerTypeSelect", getDefaultPartnerTypeSelect())
			.add("defaultPartnerCategorySelect", getDefaultPartnerCategorySelect())
			.add("width", getWidth())
			.add("height", getHeight())
			.add("timezone", getTimezone())
			.omitNullValues()
			.toString();
	}
}
