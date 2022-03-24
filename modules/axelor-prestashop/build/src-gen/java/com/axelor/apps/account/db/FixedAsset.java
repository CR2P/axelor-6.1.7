package com.axelor.apps.account.db;

import java.math.BigDecimal;
import java.time.LocalDate;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import com.axelor.apps.base.db.Company;
import com.axelor.apps.base.db.Partner;
import com.axelor.apps.stock.db.StockLocation;
import com.axelor.apps.stock.db.TrackingNumber;
import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@Cacheable
@Table(name = "ACCOUNT_FIXED_ASSET", indexes = { @Index(columnList = "name"), @Index(columnList = "company"), @Index(columnList = "journal"), @Index(columnList = "partner"), @Index(columnList = "invoice_line"), @Index(columnList = "purchase_account"), @Index(columnList = "fixed_asset_category"), @Index(columnList = "disposal_move"), @Index(columnList = "analytic_distribution_template"), @Index(columnList = "stock_location"), @Index(columnList = "tracking_number") })
public class FixedAsset extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCOUNT_FIXED_ASSET_SEQ")
	@SequenceGenerator(name = "ACCOUNT_FIXED_ASSET_SEQ", sequenceName = "ACCOUNT_FIXED_ASSET_SEQ", allocationSize = 1)
	private Long id;

	@Widget(title = "Reference", readonly = true)
	private String reference;

	@Widget(title = "Name")
	@NotNull
	private String name;

	@Widget(title = "Status", selection = "account.fixed.asset.status.select")
	private Integer statusSelect = 0;

	@Widget(title = "Company")
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Company company;

	@Widget(title = "Journal")
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Journal journal;

	@Widget(title = "Supplier")
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Partner partner;

	@Widget(title = "Invoice line")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private InvoiceLine invoiceLine;

	@Widget(title = "Purchase account")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Account purchaseAccount;

	@Widget(title = "Fixed asset category")
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private FixedAssetCategory fixedAssetCategory;

	@Widget(title = "Computation method", selection = "account.fixed.type.category.computation.method.select")
	private String computationMethodSelect;

	@Widget(title = "Degressive coef")
	private BigDecimal degressiveCoef = BigDecimal.ZERO;

	@Widget(title = "Periodicity in month")
	private Integer periodicityInMonth = 12;

	@Widget(title = "Number of depreciation")
	private Integer numberOfDepreciation = 1;

	@Widget(title = "Duration in month")
	private Integer durationInMonth = 0;

	@Widget(title = "Date of acquisition")
	private LocalDate acquisitionDate;

	@Widget(title = "First depreciation date")
	private LocalDate firstDepreciationDate;

	@Widget(title = "Gross value")
	private BigDecimal grossValue = BigDecimal.ZERO;

	@Widget(title = "Residual value")
	private BigDecimal residualValue = BigDecimal.ZERO;

	@Widget(title = "Disposal date")
	private LocalDate disposalDate;

	@Widget(title = "Disposal value")
	private BigDecimal disposalValue = BigDecimal.ZERO;

	@Widget(title = "Depreciation board")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fixedAsset", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<FixedAssetLine> fixedAssetLineList;

	@Widget(title = "Disposal move")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Move disposalMove;

	@Widget(title = "Analytic distribution template")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private AnalyticDistributionTemplate analyticDistributionTemplate;

	@Widget(title = "Stock location")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private StockLocation stockLocation;

	@Widget(title = "Tracking Number")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private TrackingNumber trackingNumber;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public FixedAsset() {
	}

	public FixedAsset(String name) {
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

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatusSelect() {
		return statusSelect == null ? 0 : statusSelect;
	}

	public void setStatusSelect(Integer statusSelect) {
		this.statusSelect = statusSelect;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Journal getJournal() {
		return journal;
	}

	public void setJournal(Journal journal) {
		this.journal = journal;
	}

	public Partner getPartner() {
		return partner;
	}

	public void setPartner(Partner partner) {
		this.partner = partner;
	}

	public InvoiceLine getInvoiceLine() {
		return invoiceLine;
	}

	public void setInvoiceLine(InvoiceLine invoiceLine) {
		this.invoiceLine = invoiceLine;
	}

	public Account getPurchaseAccount() {
		return purchaseAccount;
	}

	public void setPurchaseAccount(Account purchaseAccount) {
		this.purchaseAccount = purchaseAccount;
	}

	public FixedAssetCategory getFixedAssetCategory() {
		return fixedAssetCategory;
	}

	public void setFixedAssetCategory(FixedAssetCategory fixedAssetCategory) {
		this.fixedAssetCategory = fixedAssetCategory;
	}

	public String getComputationMethodSelect() {
		return computationMethodSelect;
	}

	public void setComputationMethodSelect(String computationMethodSelect) {
		this.computationMethodSelect = computationMethodSelect;
	}

	public BigDecimal getDegressiveCoef() {
		return degressiveCoef == null ? BigDecimal.ZERO : degressiveCoef;
	}

	public void setDegressiveCoef(BigDecimal degressiveCoef) {
		this.degressiveCoef = degressiveCoef;
	}

	public Integer getPeriodicityInMonth() {
		return periodicityInMonth == null ? 0 : periodicityInMonth;
	}

	public void setPeriodicityInMonth(Integer periodicityInMonth) {
		this.periodicityInMonth = periodicityInMonth;
	}

	public Integer getNumberOfDepreciation() {
		return numberOfDepreciation == null ? 0 : numberOfDepreciation;
	}

	public void setNumberOfDepreciation(Integer numberOfDepreciation) {
		this.numberOfDepreciation = numberOfDepreciation;
	}

	public Integer getDurationInMonth() {
		return durationInMonth == null ? 0 : durationInMonth;
	}

	public void setDurationInMonth(Integer durationInMonth) {
		this.durationInMonth = durationInMonth;
	}

	public LocalDate getAcquisitionDate() {
		return acquisitionDate;
	}

	public void setAcquisitionDate(LocalDate acquisitionDate) {
		this.acquisitionDate = acquisitionDate;
	}

	public LocalDate getFirstDepreciationDate() {
		return firstDepreciationDate;
	}

	public void setFirstDepreciationDate(LocalDate firstDepreciationDate) {
		this.firstDepreciationDate = firstDepreciationDate;
	}

	public BigDecimal getGrossValue() {
		return grossValue == null ? BigDecimal.ZERO : grossValue;
	}

	public void setGrossValue(BigDecimal grossValue) {
		this.grossValue = grossValue;
	}

	public BigDecimal getResidualValue() {
		return residualValue == null ? BigDecimal.ZERO : residualValue;
	}

	public void setResidualValue(BigDecimal residualValue) {
		this.residualValue = residualValue;
	}

	public LocalDate getDisposalDate() {
		return disposalDate;
	}

	public void setDisposalDate(LocalDate disposalDate) {
		this.disposalDate = disposalDate;
	}

	public BigDecimal getDisposalValue() {
		return disposalValue == null ? BigDecimal.ZERO : disposalValue;
	}

	public void setDisposalValue(BigDecimal disposalValue) {
		this.disposalValue = disposalValue;
	}

	public List<FixedAssetLine> getFixedAssetLineList() {
		return fixedAssetLineList;
	}

	public void setFixedAssetLineList(List<FixedAssetLine> fixedAssetLineList) {
		this.fixedAssetLineList = fixedAssetLineList;
	}

	/**
	 * Add the given {@link FixedAssetLine} item to the {@code fixedAssetLineList}.
	 *
	 * <p>
	 * It sets {@code item.fixedAsset = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addFixedAssetLineListItem(FixedAssetLine item) {
		if (getFixedAssetLineList() == null) {
			setFixedAssetLineList(new ArrayList<>());
		}
		getFixedAssetLineList().add(item);
		item.setFixedAsset(this);
	}

	/**
	 * Remove the given {@link FixedAssetLine} item from the {@code fixedAssetLineList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeFixedAssetLineListItem(FixedAssetLine item) {
		if (getFixedAssetLineList() == null) {
			return;
		}
		getFixedAssetLineList().remove(item);
	}

	/**
	 * Clear the {@code fixedAssetLineList} collection.
	 *
	 * <p>
	 * If you have to query {@link FixedAssetLine} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearFixedAssetLineList() {
		if (getFixedAssetLineList() != null) {
			getFixedAssetLineList().clear();
		}
	}

	public Move getDisposalMove() {
		return disposalMove;
	}

	public void setDisposalMove(Move disposalMove) {
		this.disposalMove = disposalMove;
	}

	public AnalyticDistributionTemplate getAnalyticDistributionTemplate() {
		return analyticDistributionTemplate;
	}

	public void setAnalyticDistributionTemplate(AnalyticDistributionTemplate analyticDistributionTemplate) {
		this.analyticDistributionTemplate = analyticDistributionTemplate;
	}

	public StockLocation getStockLocation() {
		return stockLocation;
	}

	public void setStockLocation(StockLocation stockLocation) {
		this.stockLocation = stockLocation;
	}

	public TrackingNumber getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(TrackingNumber trackingNumber) {
		this.trackingNumber = trackingNumber;
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
		if (!(obj instanceof FixedAsset)) return false;

		final FixedAsset other = (FixedAsset) obj;
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
			.add("reference", getReference())
			.add("name", getName())
			.add("statusSelect", getStatusSelect())
			.add("computationMethodSelect", getComputationMethodSelect())
			.add("degressiveCoef", getDegressiveCoef())
			.add("periodicityInMonth", getPeriodicityInMonth())
			.add("numberOfDepreciation", getNumberOfDepreciation())
			.add("durationInMonth", getDurationInMonth())
			.add("acquisitionDate", getAcquisitionDate())
			.add("firstDepreciationDate", getFirstDepreciationDate())
			.add("grossValue", getGrossValue())
			.omitNullValues()
			.toString();
	}
}
