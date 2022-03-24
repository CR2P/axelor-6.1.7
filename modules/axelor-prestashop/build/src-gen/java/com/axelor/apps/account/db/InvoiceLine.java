package com.axelor.apps.account.db;

import java.math.BigDecimal;
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
import javax.validation.constraints.Digits;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.axelor.apps.base.db.Product;
import com.axelor.apps.base.db.Unit;
import com.axelor.apps.purchase.db.PurchaseOrderLine;
import com.axelor.apps.sale.db.SaleOrderLine;
import com.axelor.apps.stock.db.StockMove;
import com.axelor.apps.stock.db.StockMoveLine;
import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.Track;
import com.axelor.db.annotations.TrackField;
import com.axelor.db.annotations.VirtualColumn;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "ACCOUNT_INVOICE_LINE", indexes = { @Index(columnList = "invoice"), @Index(columnList = "product"), @Index(columnList = "unit"), @Index(columnList = "tax_line"), @Index(columnList = "tax_equiv"), @Index(columnList = "analytic_distribution_template"), @Index(columnList = "budget"), @Index(columnList = "account"), @Index(columnList = "fixed_asset_category"), @Index(columnList = "name"), @Index(columnList = "sale_order_line"), @Index(columnList = "purchase_order_line"), @Index(columnList = "stock_move_line"), @Index(columnList = "outgoing_stock_move"), @Index(columnList = "incoming_stock_move"), @Index(columnList = "parent_line") })
@Track(fields = { @TrackField(name = "unit"), @TrackField(name = "qty"), @TrackField(name = "price"), @TrackField(name = "taxLine"), @TrackField(name = "account"), @TrackField(name = "analyticDistributionTemplate") })
public class InvoiceLine extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCOUNT_INVOICE_LINE_SEQ")
	@SequenceGenerator(name = "ACCOUNT_INVOICE_LINE_SEQ", sequenceName = "ACCOUNT_INVOICE_LINE_SEQ", allocationSize = 1)
	private Long id;

	@Widget(title = "Invoice")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Invoice invoice;

	@Widget(title = "Seq.")
	private Integer sequence = 0;

	@Widget(title = "Product")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Product product;

	@Widget(title = "Product Name")
	private String productName;

	@Widget(title = "Product Code")
	private String productCode;

	@Widget(title = "Unit price W.T.")
	@Digits(integer = 10, fraction = 10)
	private BigDecimal price = BigDecimal.ZERO;

	@Widget(title = "Unit price A.T.I.")
	@Digits(integer = 10, fraction = 10)
	private BigDecimal inTaxPrice = BigDecimal.ZERO;

	@Widget(title = "Unit price discounted")
	@Digits(integer = 10, fraction = 10)
	private BigDecimal priceDiscounted = BigDecimal.ZERO;

	@Widget(title = "Unit")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Unit unit;

	@Widget(title = "Qty")
	@Digits(integer = 10, fraction = 10)
	private BigDecimal qty = BigDecimal.ZERO;

	@Widget(hidden = true)
	@Digits(integer = 10, fraction = 10)
	private BigDecimal oldQty = BigDecimal.ZERO;

	@Widget(title = "Total W.T.", readonly = true)
	@Digits(integer = 18, fraction = 2)
	private BigDecimal exTaxTotal = BigDecimal.ZERO;

	@Widget(title = "Total A.T.I.")
	private BigDecimal inTaxTotal = BigDecimal.ZERO;

	@Widget(title = "Tax")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private TaxLine taxLine;

	@Widget(title = "Tax Equiv")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private TaxEquiv taxEquiv;

	@Widget(title = "Discount amount")
	@Digits(integer = 10, fraction = 10)
	private BigDecimal discountAmount = BigDecimal.ZERO;

	@Widget(title = "Discount type", selection = "base.price.list.line.amount.type.select")
	private Integer discountTypeSelect = 0;

	@Widget(title = "Description")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String description;

	@Widget(title = "Fixed Assets")
	private Boolean fixedAssets = Boolean.FALSE;

	@Widget(title = "Total W.T. in Acc. currency", hidden = true)
	private BigDecimal companyExTaxTotal = BigDecimal.ZERO;

	@Widget(title = "Total A.T.I. in company currency", hidden = true)
	private BigDecimal companyInTaxTotal = BigDecimal.ZERO;

	@Widget(title = "Analytic distribution lines")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "invoiceLine", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<AnalyticMoveLine> analyticMoveLineList;

	@Widget(title = "Analytic distribution template")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private AnalyticDistributionTemplate analyticDistributionTemplate;

	@Widget(title = "Budget")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Budget budget;

	@Widget(title = "Budget Distribution")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "invoiceLine", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<BudgetDistribution> budgetDistributionList;

	@Widget(title = "Total amount attributed")
	private BigDecimal budgetDistributionSumAmount = BigDecimal.ZERO;

	@Widget(title = "Accounting.Account")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Account account;

	@Widget(title = "Fixed asset category")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private FixedAssetCategory fixedAssetCategory;

	@Widget(title = "Type", selection = "line.type.select")
	private Integer typeSelect = 0;

	@Widget(readonly = true)
	@Digits(integer = 17, fraction = 3)
	private BigDecimal taxRate = BigDecimal.ZERO;

	@Widget(readonly = true)
	private String taxCode;

	@Widget(title = "Name", search = { "invoice", "productName" })
	@VirtualColumn
	@Access(AccessType.PROPERTY)
	private String name;

	@Widget(title = "SO line")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private SaleOrderLine saleOrderLine;

	@Widget(title = "PO line")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private PurchaseOrderLine purchaseOrderLine;

	@Widget(title = "Stock move line")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private StockMoveLine stockMoveLine;

	@Widget(title = "Customer delivery")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private StockMove outgoingStockMove;

	@Widget(title = "Supplier arrival")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private StockMove incomingStockMove;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private InvoiceLine parentLine;

	@Widget(title = "Pack lines")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parentLine", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<InvoiceLine> subLineList;

	@Widget(title = "Show Total")
	private Boolean isShowTotal = Boolean.FALSE;

	@Widget(title = "Hide Unit Amounts")
	private Boolean isHideUnitAmounts = Boolean.FALSE;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public InvoiceLine() {
	}

	public InvoiceLine(String name) {
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

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
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

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getFixedAssets() {
		return fixedAssets == null ? Boolean.FALSE : fixedAssets;
	}

	public void setFixedAssets(Boolean fixedAssets) {
		this.fixedAssets = fixedAssets;
	}

	public BigDecimal getCompanyExTaxTotal() {
		return companyExTaxTotal == null ? BigDecimal.ZERO : companyExTaxTotal;
	}

	public void setCompanyExTaxTotal(BigDecimal companyExTaxTotal) {
		this.companyExTaxTotal = companyExTaxTotal;
	}

	public BigDecimal getCompanyInTaxTotal() {
		return companyInTaxTotal == null ? BigDecimal.ZERO : companyInTaxTotal;
	}

	public void setCompanyInTaxTotal(BigDecimal companyInTaxTotal) {
		this.companyInTaxTotal = companyInTaxTotal;
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
	 * It sets {@code item.invoiceLine = this} to ensure the proper relationship.
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
		item.setInvoiceLine(this);
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

	public Budget getBudget() {
		return budget;
	}

	public void setBudget(Budget budget) {
		this.budget = budget;
	}

	public List<BudgetDistribution> getBudgetDistributionList() {
		return budgetDistributionList;
	}

	public void setBudgetDistributionList(List<BudgetDistribution> budgetDistributionList) {
		this.budgetDistributionList = budgetDistributionList;
	}

	/**
	 * Add the given {@link BudgetDistribution} item to the {@code budgetDistributionList}.
	 *
	 * <p>
	 * It sets {@code item.invoiceLine = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addBudgetDistributionListItem(BudgetDistribution item) {
		if (getBudgetDistributionList() == null) {
			setBudgetDistributionList(new ArrayList<>());
		}
		getBudgetDistributionList().add(item);
		item.setInvoiceLine(this);
	}

	/**
	 * Remove the given {@link BudgetDistribution} item from the {@code budgetDistributionList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeBudgetDistributionListItem(BudgetDistribution item) {
		if (getBudgetDistributionList() == null) {
			return;
		}
		getBudgetDistributionList().remove(item);
	}

	/**
	 * Clear the {@code budgetDistributionList} collection.
	 *
	 * <p>
	 * If you have to query {@link BudgetDistribution} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearBudgetDistributionList() {
		if (getBudgetDistributionList() != null) {
			getBudgetDistributionList().clear();
		}
	}

	public BigDecimal getBudgetDistributionSumAmount() {
		return budgetDistributionSumAmount == null ? BigDecimal.ZERO : budgetDistributionSumAmount;
	}

	public void setBudgetDistributionSumAmount(BigDecimal budgetDistributionSumAmount) {
		this.budgetDistributionSumAmount = budgetDistributionSumAmount;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public FixedAssetCategory getFixedAssetCategory() {
		return fixedAssetCategory;
	}

	public void setFixedAssetCategory(FixedAssetCategory fixedAssetCategory) {
		this.fixedAssetCategory = fixedAssetCategory;
	}

	public Integer getTypeSelect() {
		return typeSelect == null ? 0 : typeSelect;
	}

	public void setTypeSelect(Integer typeSelect) {
		this.typeSelect = typeSelect;
	}

	public BigDecimal getTaxRate() {
		return taxRate == null ? BigDecimal.ZERO : taxRate;
	}

	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}

	public String getTaxCode() {
		return taxCode;
	}

	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}

	public String getName() {
		try {
			name = computeName();
		} catch (NullPointerException e) {
			Logger logger = LoggerFactory.getLogger(getClass());
			logger.error("NPE in function field: getName()", e);
		}
		return name;
	}

	protected String computeName() {
		String name = "";
		if(invoice != null && invoice.getInvoiceId() != null){
			name += invoice.getInvoiceId();
		}
		if(productName != null)  {
			name += "-";
			if(productName.length() > 100)  {
				name += productName.substring(1, 100);
			}
			else  {  name += productName;  }
		}
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SaleOrderLine getSaleOrderLine() {
		return saleOrderLine;
	}

	public void setSaleOrderLine(SaleOrderLine saleOrderLine) {
		this.saleOrderLine = saleOrderLine;
	}

	public PurchaseOrderLine getPurchaseOrderLine() {
		return purchaseOrderLine;
	}

	public void setPurchaseOrderLine(PurchaseOrderLine purchaseOrderLine) {
		this.purchaseOrderLine = purchaseOrderLine;
	}

	public StockMoveLine getStockMoveLine() {
		return stockMoveLine;
	}

	public void setStockMoveLine(StockMoveLine stockMoveLine) {
		this.stockMoveLine = stockMoveLine;
	}

	public StockMove getOutgoingStockMove() {
		return outgoingStockMove;
	}

	public void setOutgoingStockMove(StockMove outgoingStockMove) {
		this.outgoingStockMove = outgoingStockMove;
	}

	public StockMove getIncomingStockMove() {
		return incomingStockMove;
	}

	public void setIncomingStockMove(StockMove incomingStockMove) {
		this.incomingStockMove = incomingStockMove;
	}

	public InvoiceLine getParentLine() {
		return parentLine;
	}

	public void setParentLine(InvoiceLine parentLine) {
		this.parentLine = parentLine;
	}

	public List<InvoiceLine> getSubLineList() {
		return subLineList;
	}

	public void setSubLineList(List<InvoiceLine> subLineList) {
		this.subLineList = subLineList;
	}

	/**
	 * Add the given {@link InvoiceLine} item to the {@code subLineList}.
	 *
	 * <p>
	 * It sets {@code item.parentLine = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addSubLineListItem(InvoiceLine item) {
		if (getSubLineList() == null) {
			setSubLineList(new ArrayList<>());
		}
		getSubLineList().add(item);
		item.setParentLine(this);
	}

	/**
	 * Remove the given {@link InvoiceLine} item from the {@code subLineList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeSubLineListItem(InvoiceLine item) {
		if (getSubLineList() == null) {
			return;
		}
		getSubLineList().remove(item);
	}

	/**
	 * Clear the {@code subLineList} collection.
	 *
	 * <p>
	 * If you have to query {@link InvoiceLine} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearSubLineList() {
		if (getSubLineList() != null) {
			getSubLineList().clear();
		}
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
		if (!(obj instanceof InvoiceLine)) return false;

		final InvoiceLine other = (InvoiceLine) obj;
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
			.add("productName", getProductName())
			.add("productCode", getProductCode())
			.add("price", getPrice())
			.add("inTaxPrice", getInTaxPrice())
			.add("priceDiscounted", getPriceDiscounted())
			.add("qty", getQty())
			.add("oldQty", getOldQty())
			.add("exTaxTotal", getExTaxTotal())
			.add("inTaxTotal", getInTaxTotal())
			.add("discountAmount", getDiscountAmount())
			.omitNullValues()
			.toString();
	}
}
