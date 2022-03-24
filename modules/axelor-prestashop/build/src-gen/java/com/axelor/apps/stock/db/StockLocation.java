package com.axelor.apps.stock.db;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Basic;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import com.axelor.apps.base.db.Address;
import com.axelor.apps.base.db.Company;
import com.axelor.apps.base.db.Partner;
import com.axelor.apps.base.db.TradingName;
import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@Table(name = "STOCK_STOCK_LOCATION", indexes = { @Index(columnList = "name"), @Index(columnList = "parent_stock_location"), @Index(columnList = "company"), @Index(columnList = "partner"), @Index(columnList = "address"), @Index(columnList = "trading_name") })
public class StockLocation extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STOCK_STOCK_LOCATION_SEQ")
	@SequenceGenerator(name = "STOCK_STOCK_LOCATION_SEQ", sequenceName = "STOCK_STOCK_LOCATION_SEQ", allocationSize = 1)
	private Long id;

	@Widget(title = "Name")
	@NotNull
	private String name;

	@Widget(title = "Parent stock location")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private StockLocation parentStockLocation;

	@Widget(title = "Company")
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Company company;

	@Widget(title = "Deported stock location")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Partner partner;

	@Widget(title = "Type", selection = "stock.stock.location.type.select")
	@NotNull
	private Integer typeSelect = 0;

	@Widget(title = "Stock location lines")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "stockLocation", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<StockLocationLine> stockLocationLineList;

	@Widget(title = "Details stock location lines")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "detailsStockLocation", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<StockLocationLine> detailsStockLocationLineList;

	@Widget(title = "Address")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Address address;

	@Widget(title = "Include out of stocks products")
	private Boolean includeOutOfStock = Boolean.FALSE;

	@Widget(title = "Stock location value")
	@Transient
	private BigDecimal stockLocationValue = BigDecimal.ZERO;

	@Widget(title = "Trading Name")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private TradingName tradingName;

	@Widget(title = "Include virtual sub location")
	private Boolean includeVirtualSubLocation = Boolean.FALSE;

	@Widget(title = "Direct order stock location")
	private Boolean directOrderLocation = Boolean.FALSE;

	@Widget(title = "Usable on purchase order")
	private Boolean usableOnPurchaseOrder = Boolean.FALSE;

	@Widget(title = "Usable on sale order")
	private Boolean usableOnSaleOrder = Boolean.FALSE;

	@Widget(title = "Don't take in consideration for the stock calcul")
	private Boolean isNotInCalculStock = Boolean.FALSE;

	@Widget(title = "Don't take in consideration on MRP")
	private Boolean isNotInMrp = Boolean.FALSE;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public StockLocation() {
	}

	public StockLocation(String name) {
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public StockLocation getParentStockLocation() {
		return parentStockLocation;
	}

	public void setParentStockLocation(StockLocation parentStockLocation) {
		this.parentStockLocation = parentStockLocation;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Partner getPartner() {
		return partner;
	}

	public void setPartner(Partner partner) {
		this.partner = partner;
	}

	public Integer getTypeSelect() {
		return typeSelect == null ? 0 : typeSelect;
	}

	public void setTypeSelect(Integer typeSelect) {
		this.typeSelect = typeSelect;
	}

	public List<StockLocationLine> getStockLocationLineList() {
		return stockLocationLineList;
	}

	public void setStockLocationLineList(List<StockLocationLine> stockLocationLineList) {
		this.stockLocationLineList = stockLocationLineList;
	}

	/**
	 * Add the given {@link StockLocationLine} item to the {@code stockLocationLineList}.
	 *
	 * <p>
	 * It sets {@code item.stockLocation = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addStockLocationLineListItem(StockLocationLine item) {
		if (getStockLocationLineList() == null) {
			setStockLocationLineList(new ArrayList<>());
		}
		getStockLocationLineList().add(item);
		item.setStockLocation(this);
	}

	/**
	 * Remove the given {@link StockLocationLine} item from the {@code stockLocationLineList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeStockLocationLineListItem(StockLocationLine item) {
		if (getStockLocationLineList() == null) {
			return;
		}
		getStockLocationLineList().remove(item);
	}

	/**
	 * Clear the {@code stockLocationLineList} collection.
	 *
	 * <p>
	 * If you have to query {@link StockLocationLine} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearStockLocationLineList() {
		if (getStockLocationLineList() != null) {
			getStockLocationLineList().clear();
		}
	}

	public List<StockLocationLine> getDetailsStockLocationLineList() {
		return detailsStockLocationLineList;
	}

	public void setDetailsStockLocationLineList(List<StockLocationLine> detailsStockLocationLineList) {
		this.detailsStockLocationLineList = detailsStockLocationLineList;
	}

	/**
	 * Add the given {@link StockLocationLine} item to the {@code detailsStockLocationLineList}.
	 *
	 * <p>
	 * It sets {@code item.detailsStockLocation = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addDetailsStockLocationLineListItem(StockLocationLine item) {
		if (getDetailsStockLocationLineList() == null) {
			setDetailsStockLocationLineList(new ArrayList<>());
		}
		getDetailsStockLocationLineList().add(item);
		item.setDetailsStockLocation(this);
	}

	/**
	 * Remove the given {@link StockLocationLine} item from the {@code detailsStockLocationLineList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeDetailsStockLocationLineListItem(StockLocationLine item) {
		if (getDetailsStockLocationLineList() == null) {
			return;
		}
		getDetailsStockLocationLineList().remove(item);
	}

	/**
	 * Clear the {@code detailsStockLocationLineList} collection.
	 *
	 * <p>
	 * If you have to query {@link StockLocationLine} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearDetailsStockLocationLineList() {
		if (getDetailsStockLocationLineList() != null) {
			getDetailsStockLocationLineList().clear();
		}
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Boolean getIncludeOutOfStock() {
		return includeOutOfStock == null ? Boolean.FALSE : includeOutOfStock;
	}

	public void setIncludeOutOfStock(Boolean includeOutOfStock) {
		this.includeOutOfStock = includeOutOfStock;
	}

	public BigDecimal getStockLocationValue() {
		return stockLocationValue == null ? BigDecimal.ZERO : stockLocationValue;
	}

	public void setStockLocationValue(BigDecimal stockLocationValue) {
		this.stockLocationValue = stockLocationValue;
	}

	public TradingName getTradingName() {
		return tradingName;
	}

	public void setTradingName(TradingName tradingName) {
		this.tradingName = tradingName;
	}

	public Boolean getIncludeVirtualSubLocation() {
		return includeVirtualSubLocation == null ? Boolean.FALSE : includeVirtualSubLocation;
	}

	public void setIncludeVirtualSubLocation(Boolean includeVirtualSubLocation) {
		this.includeVirtualSubLocation = includeVirtualSubLocation;
	}

	public Boolean getDirectOrderLocation() {
		return directOrderLocation == null ? Boolean.FALSE : directOrderLocation;
	}

	public void setDirectOrderLocation(Boolean directOrderLocation) {
		this.directOrderLocation = directOrderLocation;
	}

	public Boolean getUsableOnPurchaseOrder() {
		return usableOnPurchaseOrder == null ? Boolean.FALSE : usableOnPurchaseOrder;
	}

	public void setUsableOnPurchaseOrder(Boolean usableOnPurchaseOrder) {
		this.usableOnPurchaseOrder = usableOnPurchaseOrder;
	}

	public Boolean getUsableOnSaleOrder() {
		return usableOnSaleOrder == null ? Boolean.FALSE : usableOnSaleOrder;
	}

	public void setUsableOnSaleOrder(Boolean usableOnSaleOrder) {
		this.usableOnSaleOrder = usableOnSaleOrder;
	}

	public Boolean getIsNotInCalculStock() {
		return isNotInCalculStock == null ? Boolean.FALSE : isNotInCalculStock;
	}

	public void setIsNotInCalculStock(Boolean isNotInCalculStock) {
		this.isNotInCalculStock = isNotInCalculStock;
	}

	public Boolean getIsNotInMrp() {
		return isNotInMrp == null ? Boolean.FALSE : isNotInMrp;
	}

	public void setIsNotInMrp(Boolean isNotInMrp) {
		this.isNotInMrp = isNotInMrp;
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
		if (!(obj instanceof StockLocation)) return false;

		final StockLocation other = (StockLocation) obj;
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
			.add("name", getName())
			.add("typeSelect", getTypeSelect())
			.add("includeOutOfStock", getIncludeOutOfStock())
			.add("stockLocationValue", getStockLocationValue())
			.add("includeVirtualSubLocation", getIncludeVirtualSubLocation())
			.add("directOrderLocation", getDirectOrderLocation())
			.add("usableOnPurchaseOrder", getUsableOnPurchaseOrder())
			.add("usableOnSaleOrder", getUsableOnSaleOrder())
			.add("isNotInCalculStock", getIsNotInCalculStock())
			.add("isNotInMrp", getIsNotInMrp())
			.omitNullValues()
			.toString();
	}
}
