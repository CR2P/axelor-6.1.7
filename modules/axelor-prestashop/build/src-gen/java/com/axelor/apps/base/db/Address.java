package com.axelor.apps.base.db;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.NameColumn;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@Table(name = "BASE_ADDRESS", uniqueConstraints = { @UniqueConstraint(columnNames = { "prestaShopId" }) }, indexes = { @Index(columnList = "addressl7country"), @Index(columnList = "city"), @Index(columnList = "street"), @Index(columnList = "fullName") })
public class Address extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BASE_ADDRESS_SEQ")
	@SequenceGenerator(name = "BASE_ADDRESS_SEQ", sequenceName = "BASE_ADDRESS_SEQ", allocationSize = 1)
	private Long id;

	@Widget(title = "Recipient details")
	private String addressL2;

	@Widget(title = "Address precisions")
	private String addressL3;

	@Widget(title = "N° and Street label")
	@NotNull
	private String addressL4;

	@Widget(title = "Distribution precisions (POB, Village...)")
	private String addressL5;

	@Widget(title = "Zip/City")
	private String addressL6;

	@Widget(title = "Country")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Country addressL7Country;

	@Widget(title = "City")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private City city;

	@Widget(title = "Latitude")
	@Digits(integer = 20, fraction = 18)
	@Column(nullable = true)
	private BigDecimal latit;

	@Widget(title = "Longitude")
	@Digits(integer = 20, fraction = 18)
	@Column(nullable = true)
	private BigDecimal longit;

	@Widget(title = "Valid Latitude Longitude")
	private Boolean isValidLatLong = Boolean.TRUE;

	@Widget(title = "Default zoom")
	private Integer zoom = 0;

	@Widget(title = "National Code", readonly = true)
	private String inseeCode;

	@Widget(title = "Address certified by QAS engine", readonly = true)
	private Boolean certifiedOk = Boolean.FALSE;

	@Widget(title = "QAS Proposal lists")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "address", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PickListEntry> pickList;

	@Widget(title = "Zip code")
	private String zip;

	@Widget(title = "Street number")
	private String streetNumber;

	@Widget(title = "Street")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Street street;

	@Widget(title = "Address", search = { "addressL2", "addressL3", "addressL4", "addressL5", "addressL6" })
	@NameColumn
	private String fullName;

	@Column(nullable = true)
	private Integer prestaShopId;

	@Column(nullable = true)
	private Integer prestaShopVersion;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public Address() {
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getAddressL2() {
		return addressL2;
	}

	public void setAddressL2(String addressL2) {
		this.addressL2 = addressL2;
	}

	public String getAddressL3() {
		return addressL3;
	}

	public void setAddressL3(String addressL3) {
		this.addressL3 = addressL3;
	}

	public String getAddressL4() {
		return addressL4;
	}

	public void setAddressL4(String addressL4) {
		this.addressL4 = addressL4;
	}

	public String getAddressL5() {
		return addressL5;
	}

	public void setAddressL5(String addressL5) {
		this.addressL5 = addressL5;
	}

	public String getAddressL6() {
		return addressL6;
	}

	public void setAddressL6(String addressL6) {
		this.addressL6 = addressL6;
	}

	public Country getAddressL7Country() {
		return addressL7Country;
	}

	public void setAddressL7Country(Country addressL7Country) {
		this.addressL7Country = addressL7Country;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public BigDecimal getLatit() {
		return latit;
	}

	public void setLatit(BigDecimal latit) {
		this.latit = latit;
	}

	public BigDecimal getLongit() {
		return longit;
	}

	public void setLongit(BigDecimal longit) {
		this.longit = longit;
	}

	public Boolean getIsValidLatLong() {
		return isValidLatLong == null ? Boolean.FALSE : isValidLatLong;
	}

	public void setIsValidLatLong(Boolean isValidLatLong) {
		this.isValidLatLong = isValidLatLong;
	}

	public Integer getZoom() {
		return zoom == null ? 0 : zoom;
	}

	public void setZoom(Integer zoom) {
		this.zoom = zoom;
	}

	public String getInseeCode() {
		return inseeCode;
	}

	public void setInseeCode(String inseeCode) {
		this.inseeCode = inseeCode;
	}

	public Boolean getCertifiedOk() {
		return certifiedOk == null ? Boolean.FALSE : certifiedOk;
	}

	public void setCertifiedOk(Boolean certifiedOk) {
		this.certifiedOk = certifiedOk;
	}

	public List<PickListEntry> getPickList() {
		return pickList;
	}

	public void setPickList(List<PickListEntry> pickList) {
		this.pickList = pickList;
	}

	/**
	 * Add the given {@link PickListEntry} item to the {@code pickList}.
	 *
	 * <p>
	 * It sets {@code item.address = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addPickListItem(PickListEntry item) {
		if (getPickList() == null) {
			setPickList(new ArrayList<>());
		}
		getPickList().add(item);
		item.setAddress(this);
	}

	/**
	 * Remove the given {@link PickListEntry} item from the {@code pickList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removePickListItem(PickListEntry item) {
		if (getPickList() == null) {
			return;
		}
		getPickList().remove(item);
	}

	/**
	 * Clear the {@code pickList} collection.
	 *
	 * <p>
	 * If you have to query {@link PickListEntry} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearPickList() {
		if (getPickList() != null) {
			getPickList().clear();
		}
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}

	public Street getStreet() {
		return street;
	}

	public void setStreet(Street street) {
		this.street = street;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
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
		if (!(obj instanceof Address)) return false;

		final Address other = (Address) obj;
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
			.add("addressL2", getAddressL2())
			.add("addressL3", getAddressL3())
			.add("addressL4", getAddressL4())
			.add("addressL5", getAddressL5())
			.add("addressL6", getAddressL6())
			.add("latit", getLatit())
			.add("longit", getLongit())
			.add("isValidLatLong", getIsValidLatLong())
			.add("zoom", getZoom())
			.add("inseeCode", getInseeCode())
			.add("certifiedOk", getCertifiedOk())
			.omitNullValues()
			.toString();
	}
}
