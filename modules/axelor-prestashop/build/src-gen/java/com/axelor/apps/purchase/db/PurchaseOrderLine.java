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
import javax.persistence.Transient;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.axelor.apps.account.db.AnalyticDistributionTemplate;
import com.axelor.apps.account.db.AnalyticMoveLine;
import com.axelor.apps.account.db.Budget;
import com.axelor.apps.account.db.BudgetDistribution;
import com.axelor.apps.account.db.TaxEquiv;
import com.axelor.apps.account.db.TaxLine;
import com.axelor.apps.base.db.Product;
import com.axelor.apps.base.db.Unit;
import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.NameColumn;
import com.axelor.db.annotations.VirtualColumn;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "PURCHASE_PURCHASE_ORDER_LINE", indexes = { @Index(columnList = "fullName"), @Index(columnList = "purchase_order"), @Index(columnList = "product"), @Index(columnList = "tax_line"), @Index(columnList = "tax_equiv"), @Index(columnList = "unit"), @Index(columnList = "analytic_distribution_template"), @Index(columnList = "budget") })
public class PurchaseOrderLine extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PURCHASE_PURCHASE_ORDER_LINE_SEQ")
	@SequenceGenerator(name = "PURCHASE_PURCHASE_ORDER_LINE_SEQ", sequenceName = "PURCHASE_PURCHASE_ORDER_LINE_SEQ", allocationSize = 1)
	private Long id;

	@NameColumn
	@VirtualColumn
	@Access(AccessType.PROPERTY)
	private String fullName;

	@Widget(title = "Purchase order")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private PurchaseOrder purchaseOrder;

	@Widget(title = "Seq.")
	private Integer sequence = 0;

	@Widget(title = "Product")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Product product;

	@Widget(title = "Qty")
	@Digits(integer = 10, fraction = 10)
	private BigDecimal qty = new BigDecimal("1");

	@Widget(title = "Displayed Product name", translatable = true)
	@NotNull
	private String productName;

	@Widget(title = "Supplier code")
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

	@Widget(title = "Tax")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private TaxLine taxLine;

	@Widget(title = "Total W.T.")
	private BigDecimal exTaxTotal = BigDecimal.ZERO;

	@Widget(title = "Total A.T.I.")
	private BigDecimal inTaxTotal = BigDecimal.ZERO;

	@Widget(title = "Tax Equiv")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private TaxEquiv taxEquiv;

	@Widget(title = "Description")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String description;

	@Widget(title = "Unit")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Unit unit;

	@Widget(title = "Discount amount")
	@Digits(integer = 10, fraction = 10)
	private BigDecimal discountAmount = BigDecimal.ZERO;

	@Widget(title = "Discount type", selection = "base.price.list.line.amount.type.select")
	private Integer discountTypeSelect = 0;

	@Widget(title = "Ordered")
	private Boolean isOrdered = Boolean.FALSE;

	@Widget(title = "Max purchase price")
	@Digits(integer = 10, fraction = 10)
	private BigDecimal maxPurchasePrice = BigDecimal.ZERO;

	@Widget(title = "Estim. receipt date")
	private LocalDate estimatedDelivDate;

	@Widget(title = "Desired receipt date")
	private LocalDate desiredDelivDate;

	@Widget(title = "Received quantity")
	@Digits(integer = 10, fraction = 10)
	private BigDecimal receivedQty = BigDecimal.ZERO;

	@Widget(title = "Supplier Comment")
	private String supplierComment;

	@Widget(title = "Fixed Assets")
	private Boolean fixedAssets = Boolean.FALSE;

	@Widget(title = "Total W.T. in company currency", hidden = true)
	private BigDecimal companyExTaxTotal = BigDecimal.ZERO;

	@Widget(title = "Total A.T.I. in company currency", hidden = true)
	private BigDecimal companyInTaxTotal = BigDecimal.ZERO;

	@Widget(title = "Title Line")
	private Boolean isTitleLine = Boolean.FALSE;

	@Widget(title = "Freeze fields")
	@Transient
	private Boolean enableFreezeFields = Boolean.FALSE;

	@Widget(title = "Amount invoiced W.T.", readonly = true)
	private BigDecimal amountInvoiced = BigDecimal.ZERO;

	@Widget(title = "Analytic distribution lines")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "purchaseOrderLine", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<AnalyticMoveLine> analyticMoveLineList;

	@Widget(title = "Analytic distribution template")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private AnalyticDistributionTemplate analyticDistributionTemplate;

	@Widget(readonly = true)
	private Boolean invoiced = Boolean.FALSE;

	@Widget(title = "Budget")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Budget budget;

	@Widget(title = "Budget Distribution")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "purchaseOrderLine", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<BudgetDistribution> budgetDistributionList;

	@Widget(title = "Total amount attributed")
	private BigDecimal budgetDistributionSumAmount = BigDecimal.ZERO;

	@Widget(title = "Receipt state", readonly = true, selection = "purchase.order.receipt.state")
	private Integer receiptState = 0;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public PurchaseOrderLine() {
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
		String fullName = "";
		if(purchaseOrder != null && purchaseOrder.getPurchaseOrderSeq() != null){
			fullName += purchaseOrder.getPurchaseOrderSeq();
		}
		if(productName != null)  {
			fullName += "-";
			if(productName.length() > 100)  {
				fullName += productName.substring(1, 100);
			}
			else  {  fullName += productName;  }
		}
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public PurchaseOrder getPurchaseOrder() {
		return purchaseOrder;
	}

	public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
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

	public BigDecimal getQty() {
		return qty == null ? BigDecimal.ZERO : qty;
	}

	public void setQty(BigDecimal qty) {
		this.qty = qty;
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

	public TaxLine getTaxLine() {
		return taxLine;
	}

	public void setTaxLine(TaxLine taxLine) {
		this.taxLine = taxLine;
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

	public TaxEquiv getTaxEquiv() {
		return taxEquiv;
	}

	public void setTaxEquiv(TaxEquiv taxEquiv) {
		this.taxEquiv = taxEquiv;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
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

	public Boolean getIsOrdered() {
		return isOrdered == null ? Boolean.FALSE : isOrdered;
	}

	public void setIsOrdered(Boolean isOrdered) {
		this.isOrdered = isOrdered;
	}

	public BigDecimal getMaxPurchasePrice() {
		return maxPurchasePrice == null ? BigDecimal.ZERO : maxPurchasePrice;
	}

	public void setMaxPurchasePrice(BigDecimal maxPurchasePrice) {
		this.maxPurchasePrice = maxPurchasePrice;
	}

	public LocalDate getEstimatedDelivDate() {
		return estimatedDelivDate;
	}

	public void setEstimatedDelivDate(LocalDate estimatedDelivDate) {
		this.estimatedDelivDate = estimatedDelivDate;
	}

	public LocalDate getDesiredDelivDate() {
		return desiredDelivDate;
	}

	public void setDesiredDelivDate(LocalDate desiredDelivDate) {
		this.desiredDelivDate = desiredDelivDate;
	}

	public BigDecimal getReceivedQty() {
		return receivedQty == null ? BigDecimal.ZERO : receivedQty;
	}

	public void setReceivedQty(BigDecimal receivedQty) {
		this.receivedQty = receivedQty;
	}

	public String getSupplierComment() {
		return supplierComment;
	}

	public void setSupplierComment(String supplierComment) {
		this.supplierComment = supplierComment;
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

	public Boolean getIsTitleLine() {
		return isTitleLine == null ? Boolean.FALSE : isTitleLine;
	}

	public void setIsTitleLine(Boolean isTitleLine) {
		this.isTitleLine = isTitleLine;
	}

	public Boolean getEnableFreezeFields() {
		return enableFreezeFields == null ? Boolean.FALSE : enableFreezeFields;
	}

	public void setEnableFreezeFields(Boolean enableFreezeFields) {
		this.enableFreezeFields = enableFreezeFields;
	}

	public BigDecimal getAmountInvoiced() {
		return amountInvoiced == null ? BigDecimal.ZERO : amountInvoiced;
	}

	public void setAmountInvoiced(BigDecimal amountInvoiced) {
		this.amountInvoiced = amountInvoiced;
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
	 * It sets {@code item.purchaseOrderLine = this} to ensure the proper relationship.
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
		item.setPurchaseOrderLine(this);
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

	public Boolean getInvoiced() {
		return invoiced == null ? Boolean.FALSE : invoiced;
	}

	public void setInvoiced(Boolean invoiced) {
		this.invoiced = invoiced;
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
	 * It sets {@code item.purchaseOrderLine = this} to ensure the proper relationship.
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
		item.setPurchaseOrderLine(this);
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

	public Integer getReceiptState() {
		return receiptState == null ? 0 : receiptState;
	}

	public void setReceiptState(Integer receiptState) {
		this.receiptState = receiptState;
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
		if (!(obj instanceof PurchaseOrderLine)) return false;

		final PurchaseOrderLine other = (PurchaseOrderLine) obj;
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
			.add("qty", getQty())
			.add("productName", getProductName())
			.add("productCode", getProductCode())
			.add("price", getPrice())
			.add("inTaxPrice", getInTaxPrice())
			.add("priceDiscounted", getPriceDiscounted())
			.add("exTaxTotal", getExTaxTotal())
			.add("inTaxTotal", getInTaxTotal())
			.add("discountAmount", getDiscountAmount())
			.add("discountTypeSelect", getDiscountTypeSelect())
			.omitNullValues()
			.toString();
	}
}
