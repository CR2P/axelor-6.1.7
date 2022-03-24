package com.axelor.apps.account.db;

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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import com.axelor.apps.base.db.Batch;
import com.axelor.apps.base.db.Company;
import com.axelor.apps.base.db.Currency;
import com.axelor.apps.base.db.Partner;
import com.axelor.apps.base.db.Period;
import com.axelor.apps.base.db.TradingName;
import com.axelor.apps.stock.db.StockMove;
import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.NameColumn;
import com.axelor.db.annotations.Track;
import com.axelor.db.annotations.TrackEvent;
import com.axelor.db.annotations.TrackField;
import com.axelor.db.annotations.TrackMessage;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@Table(name = "ACCOUNT_MOVE", uniqueConstraints = { @UniqueConstraint(columnNames = { "reference", "company" }) }, indexes = { @Index(columnList = "journal"), @Index(columnList = "period"), @Index(columnList = "reference"), @Index(columnList = "partner"), @Index(columnList = "company"), @Index(columnList = "payment_mode"), @Index(columnList = "invoice"), @Index(columnList = "accounting_report"), @Index(columnList = "payment_voucher"), @Index(columnList = "company_currency"), @Index(columnList = "currency"), @Index(columnList = "trading_name"), @Index(columnList = "stock_move") })
@Track(fields = { @TrackField(name = "statusSelect"), @TrackField(name = "journal"), @TrackField(name = "reference"), @TrackField(name = "period"), @TrackField(name = "partner"), @TrackField(name = "currency"), @TrackField(name = "companyCurrency"), @TrackField(name = "ignoreInAccountingOk"), @TrackField(name = "ignoreInDebtRecoveryOk") }, messages = { @TrackMessage(message = "Move created", condition = "true", on = TrackEvent.CREATE), @TrackMessage(message = "New move", condition = "statusSelect == 1", tag = "important"), @TrackMessage(message = "Accounted move", condition = "statusSelect == 2", tag = "info"), @TrackMessage(message = "Move validated", condition = "statusSelect == 3", tag = "success"), @TrackMessage(message = "Move canceled", condition = "statusSelect == 4", tag = "warning") })
public class Move extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCOUNT_MOVE_SEQ")
	@SequenceGenerator(name = "ACCOUNT_MOVE_SEQ", sequenceName = "ACCOUNT_MOVE_SEQ", allocationSize = 1)
	private Long id;

	@Widget(title = "Journal")
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Journal journal;

	@Widget(title = "Period", readonly = true)
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Period period;

	@Widget(title = "Date")
	@Column(name = "date_val")
	private LocalDate date;

	@Widget(title = "Reference", readonly = true)
	@NameColumn
	private String reference;

	@Widget(title = "Move lines")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "move", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<MoveLine> moveLineList;

	@Widget(title = "Partner")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Partner partner;

	@Widget(title = "Company")
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Company company;

	@Widget(title = "Payment Mode")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private PaymentMode paymentMode;

	@Widget(title = "Invoice")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Invoice invoice;

	@Widget(title = "Status", readonly = true, selection = "iaccount.move.status.select")
	private Integer statusSelect = 1;

	@Widget(title = "Rejection")
	private Boolean rejectOk = Boolean.FALSE;

	@Widget(title = "Get information from 1st Move line")
	private Boolean getInfoFromFirstMoveLineOk = Boolean.TRUE;

	@Widget(title = "Accounting Export N°")
	private String exportNumber;

	@Widget(title = "Export Date")
	private LocalDate exportDate;

	@Widget(title = "Exported")
	private Boolean accountingOk = Boolean.FALSE;

	@Widget(title = "Accounting Export")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private AccountingReport accountingReport;

	@Widget(title = "Payment voucher")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private PaymentVoucher paymentVoucher;

	@Widget(title = "Company currency")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Currency companyCurrency;

	@Widget(title = "Company currency code")
	private String companyCurrencyCode;

	@Widget(title = "Currency")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Currency currency;

	@Widget(title = "Currency code")
	private String currencyCode;

	@Widget(title = "Ignore in accounting")
	private Boolean ignoreInAccountingOk = Boolean.FALSE;

	@Widget(title = "Ignore in debt recovery process")
	private Boolean ignoreInDebtRecoveryOk = Boolean.FALSE;

	@Widget(title = "Validation date")
	private LocalDate validationDate;

	@Widget(title = "Technical origin", readonly = true, selection = "iaccount.move.technical.origin.select")
	@NotNull
	private Integer technicalOriginSelect = 0;

	@Widget(title = "Adjusting Move")
	private Boolean adjustingMove = Boolean.FALSE;

	@Widget(title = "Year closure move")
	private Boolean autoYearClosureMove = Boolean.FALSE;

	@Widget(title = "Batchs")
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Batch> batchSet;

	@Widget(title = "Functional origin", readonly = true, selection = "iaccount.move.functional.origin.select")
	private Integer functionalOriginSelect = 0;

	@Widget(title = "Trading name")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private TradingName tradingName;

	@Widget(title = "Stock move")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private StockMove stockMove;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public Move() {
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Journal getJournal() {
		return journal;
	}

	public void setJournal(Journal journal) {
		this.journal = journal;
	}

	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public List<MoveLine> getMoveLineList() {
		return moveLineList;
	}

	public void setMoveLineList(List<MoveLine> moveLineList) {
		this.moveLineList = moveLineList;
	}

	/**
	 * Add the given {@link MoveLine} item to the {@code moveLineList}.
	 *
	 * <p>
	 * It sets {@code item.move = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addMoveLineListItem(MoveLine item) {
		if (getMoveLineList() == null) {
			setMoveLineList(new ArrayList<>());
		}
		getMoveLineList().add(item);
		item.setMove(this);
	}

	/**
	 * Remove the given {@link MoveLine} item from the {@code moveLineList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeMoveLineListItem(MoveLine item) {
		if (getMoveLineList() == null) {
			return;
		}
		getMoveLineList().remove(item);
	}

	/**
	 * Clear the {@code moveLineList} collection.
	 *
	 * <p>
	 * If you have to query {@link MoveLine} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearMoveLineList() {
		if (getMoveLineList() != null) {
			getMoveLineList().clear();
		}
	}

	public Partner getPartner() {
		return partner;
	}

	public void setPartner(Partner partner) {
		this.partner = partner;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public PaymentMode getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(PaymentMode paymentMode) {
		this.paymentMode = paymentMode;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public Integer getStatusSelect() {
		return statusSelect == null ? 0 : statusSelect;
	}

	public void setStatusSelect(Integer statusSelect) {
		this.statusSelect = statusSelect;
	}

	public Boolean getRejectOk() {
		return rejectOk == null ? Boolean.FALSE : rejectOk;
	}

	public void setRejectOk(Boolean rejectOk) {
		this.rejectOk = rejectOk;
	}

	public Boolean getGetInfoFromFirstMoveLineOk() {
		return getInfoFromFirstMoveLineOk == null ? Boolean.FALSE : getInfoFromFirstMoveLineOk;
	}

	public void setGetInfoFromFirstMoveLineOk(Boolean getInfoFromFirstMoveLineOk) {
		this.getInfoFromFirstMoveLineOk = getInfoFromFirstMoveLineOk;
	}

	public String getExportNumber() {
		return exportNumber;
	}

	public void setExportNumber(String exportNumber) {
		this.exportNumber = exportNumber;
	}

	public LocalDate getExportDate() {
		return exportDate;
	}

	public void setExportDate(LocalDate exportDate) {
		this.exportDate = exportDate;
	}

	public Boolean getAccountingOk() {
		return accountingOk == null ? Boolean.FALSE : accountingOk;
	}

	public void setAccountingOk(Boolean accountingOk) {
		this.accountingOk = accountingOk;
	}

	public AccountingReport getAccountingReport() {
		return accountingReport;
	}

	public void setAccountingReport(AccountingReport accountingReport) {
		this.accountingReport = accountingReport;
	}

	public PaymentVoucher getPaymentVoucher() {
		return paymentVoucher;
	}

	public void setPaymentVoucher(PaymentVoucher paymentVoucher) {
		this.paymentVoucher = paymentVoucher;
	}

	public Currency getCompanyCurrency() {
		return companyCurrency;
	}

	public void setCompanyCurrency(Currency companyCurrency) {
		this.companyCurrency = companyCurrency;
	}

	public String getCompanyCurrencyCode() {
		return companyCurrencyCode;
	}

	public void setCompanyCurrencyCode(String companyCurrencyCode) {
		this.companyCurrencyCode = companyCurrencyCode;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public Boolean getIgnoreInAccountingOk() {
		return ignoreInAccountingOk == null ? Boolean.FALSE : ignoreInAccountingOk;
	}

	public void setIgnoreInAccountingOk(Boolean ignoreInAccountingOk) {
		this.ignoreInAccountingOk = ignoreInAccountingOk;
	}

	public Boolean getIgnoreInDebtRecoveryOk() {
		return ignoreInDebtRecoveryOk == null ? Boolean.FALSE : ignoreInDebtRecoveryOk;
	}

	public void setIgnoreInDebtRecoveryOk(Boolean ignoreInDebtRecoveryOk) {
		this.ignoreInDebtRecoveryOk = ignoreInDebtRecoveryOk;
	}

	public LocalDate getValidationDate() {
		return validationDate;
	}

	public void setValidationDate(LocalDate validationDate) {
		this.validationDate = validationDate;
	}

	public Integer getTechnicalOriginSelect() {
		return technicalOriginSelect == null ? 0 : technicalOriginSelect;
	}

	public void setTechnicalOriginSelect(Integer technicalOriginSelect) {
		this.technicalOriginSelect = technicalOriginSelect;
	}

	public Boolean getAdjustingMove() {
		return adjustingMove == null ? Boolean.FALSE : adjustingMove;
	}

	public void setAdjustingMove(Boolean adjustingMove) {
		this.adjustingMove = adjustingMove;
	}

	public Boolean getAutoYearClosureMove() {
		return autoYearClosureMove == null ? Boolean.FALSE : autoYearClosureMove;
	}

	public void setAutoYearClosureMove(Boolean autoYearClosureMove) {
		this.autoYearClosureMove = autoYearClosureMove;
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

	public Integer getFunctionalOriginSelect() {
		return functionalOriginSelect == null ? 0 : functionalOriginSelect;
	}

	public void setFunctionalOriginSelect(Integer functionalOriginSelect) {
		this.functionalOriginSelect = functionalOriginSelect;
	}

	public TradingName getTradingName() {
		return tradingName;
	}

	public void setTradingName(TradingName tradingName) {
		this.tradingName = tradingName;
	}

	public StockMove getStockMove() {
		return stockMove;
	}

	public void setStockMove(StockMove stockMove) {
		this.stockMove = stockMove;
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
		if (!(obj instanceof Move)) return false;

		final Move other = (Move) obj;
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
			.add("date", getDate())
			.add("reference", getReference())
			.add("statusSelect", getStatusSelect())
			.add("rejectOk", getRejectOk())
			.add("getInfoFromFirstMoveLineOk", getGetInfoFromFirstMoveLineOk())
			.add("exportNumber", getExportNumber())
			.add("exportDate", getExportDate())
			.add("accountingOk", getAccountingOk())
			.add("companyCurrencyCode", getCompanyCurrencyCode())
			.add("currencyCode", getCurrencyCode())
			.add("ignoreInAccountingOk", getIgnoreInAccountingOk())
			.omitNullValues()
			.toString();
	}
}
