package com.axelor.apps.base.db;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
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

import org.hibernate.annotations.Type;

import com.axelor.apps.stock.db.StockLocation;
import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.Widget;
import com.axelor.meta.db.MetaFile;
import com.google.common.base.MoreObjects;

@Entity
@Table(name = "BASE_TRADING_NAME", indexes = { @Index(columnList = "name"), @Index(columnList = "logo"), @Index(columnList = "partner"), @Index(columnList = "workshop_default_stock_location"), @Index(columnList = "receipt_default_stock_location"), @Index(columnList = "waste_default_stock_location"), @Index(columnList = "shipping_default_stock_location"), @Index(columnList = "quality_control_default_stock_location") })
public class TradingName extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BASE_TRADING_NAME_SEQ")
	@SequenceGenerator(name = "BASE_TRADING_NAME_SEQ", sequenceName = "BASE_TRADING_NAME_SEQ", allocationSize = 1)
	private Long id;

	private String name;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private MetaFile logo;

	@Widget(title = "Companies")
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "tradingNameSet", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Company> companySet;

	@Widget(title = "Partner")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Partner partner;

	@Widget(title = "Stock locations")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tradingName", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<StockLocation> stockLocationList;

	@Widget(title = "Workshop default stock location")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private StockLocation workshopDefaultStockLocation;

	@Widget(title = "Receipt default stock location")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private StockLocation receiptDefaultStockLocation;

	@Widget(title = "Waste default stock location")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private StockLocation wasteDefaultStockLocation;

	@Widget(title = "Shipping default stock location")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private StockLocation shippingDefaultStockLocation;

	@Widget(title = "Quality control default stock location")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private StockLocation qualityControlDefaultStockLocation;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public TradingName() {
	}

	public TradingName(String name) {
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

	public MetaFile getLogo() {
		return logo;
	}

	public void setLogo(MetaFile logo) {
		this.logo = logo;
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

	public Partner getPartner() {
		return partner;
	}

	public void setPartner(Partner partner) {
		this.partner = partner;
	}

	public List<StockLocation> getStockLocationList() {
		return stockLocationList;
	}

	public void setStockLocationList(List<StockLocation> stockLocationList) {
		this.stockLocationList = stockLocationList;
	}

	/**
	 * Add the given {@link StockLocation} item to the {@code stockLocationList}.
	 *
	 * <p>
	 * It sets {@code item.tradingName = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addStockLocationListItem(StockLocation item) {
		if (getStockLocationList() == null) {
			setStockLocationList(new ArrayList<>());
		}
		getStockLocationList().add(item);
		item.setTradingName(this);
	}

	/**
	 * Remove the given {@link StockLocation} item from the {@code stockLocationList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeStockLocationListItem(StockLocation item) {
		if (getStockLocationList() == null) {
			return;
		}
		getStockLocationList().remove(item);
	}

	/**
	 * Clear the {@code stockLocationList} collection.
	 *
	 * <p>
	 * If you have to query {@link StockLocation} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearStockLocationList() {
		if (getStockLocationList() != null) {
			getStockLocationList().clear();
		}
	}

	public StockLocation getWorkshopDefaultStockLocation() {
		return workshopDefaultStockLocation;
	}

	public void setWorkshopDefaultStockLocation(StockLocation workshopDefaultStockLocation) {
		this.workshopDefaultStockLocation = workshopDefaultStockLocation;
	}

	public StockLocation getReceiptDefaultStockLocation() {
		return receiptDefaultStockLocation;
	}

	public void setReceiptDefaultStockLocation(StockLocation receiptDefaultStockLocation) {
		this.receiptDefaultStockLocation = receiptDefaultStockLocation;
	}

	public StockLocation getWasteDefaultStockLocation() {
		return wasteDefaultStockLocation;
	}

	public void setWasteDefaultStockLocation(StockLocation wasteDefaultStockLocation) {
		this.wasteDefaultStockLocation = wasteDefaultStockLocation;
	}

	public StockLocation getShippingDefaultStockLocation() {
		return shippingDefaultStockLocation;
	}

	public void setShippingDefaultStockLocation(StockLocation shippingDefaultStockLocation) {
		this.shippingDefaultStockLocation = shippingDefaultStockLocation;
	}

	public StockLocation getQualityControlDefaultStockLocation() {
		return qualityControlDefaultStockLocation;
	}

	public void setQualityControlDefaultStockLocation(StockLocation qualityControlDefaultStockLocation) {
		this.qualityControlDefaultStockLocation = qualityControlDefaultStockLocation;
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
		if (!(obj instanceof TradingName)) return false;

		final TradingName other = (TradingName) obj;
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
			.omitNullValues()
			.toString();
	}
}
