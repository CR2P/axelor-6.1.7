package com.axelor.apps.base.db;

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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.NameColumn;
import com.axelor.db.annotations.Track;
import com.axelor.db.annotations.TrackEvent;
import com.axelor.db.annotations.TrackField;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@Table(name = "BASE_SEQUENCE", uniqueConstraints = { @UniqueConstraint(columnNames = { "company", "codeSelect", "prefixe", "suffixe" }) }, indexes = { @Index(columnList = "company"), @Index(columnList = "name"), @Index(columnList = "fullName"), @Index(name = "idx_sequence_code_company", columnList = "codeSelect,company") })
@Track(on = TrackEvent.UPDATE, fields = { @TrackField(name = "codeSelect"), @TrackField(name = "company"), @TrackField(name = "sequenceTypeSelect"), @TrackField(name = "padding"), @TrackField(name = "toBeAdded"), @TrackField(name = "prefixe"), @TrackField(name = "suffixe"), @TrackField(name = "yearlyResetOk"), @TrackField(name = "monthlyResetOk") })
public class Sequence extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BASE_SEQUENCE_SEQ")
	@SequenceGenerator(name = "BASE_SEQUENCE_SEQ", sequenceName = "BASE_SEQUENCE_SEQ", allocationSize = 1)
	private Long id;

	@Widget(title = "Company")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Company company;

	@Widget(title = "Name")
	@NotNull
	private String name;

	@Widget(title = "Document concerned", selection = "sequence.generic.code.select")
	@NotNull
	private String codeSelect;

	@Widget(title = "Sequence type")
	@Basic
	@Type(type = "com.axelor.db.hibernate.type.ValueEnumType")
	@NotNull
	private SequenceTypeSelect sequenceTypeSelect = SequenceTypeSelect.NUMBERS;

	@Widget(title = "Uppercase / lowercase")
	@Basic
	@Type(type = "com.axelor.db.hibernate.type.ValueEnumType")
	private SequenceLettersTypeSelect sequenceLettersTypeSelect = SequenceLettersTypeSelect.UPPERCASE;

	@Widget(title = "Prefix")
	private String prefixe;

	@Widget(title = "Suffix")
	private String suffixe;

	@Widget(title = "Padding")
	@NotNull
	private Integer padding = 0;

	@Widget(title = "Increment")
	@NotNull
	@Min(1)
	private Integer toBeAdded = 0;

	@Widget(title = "Yearly reset")
	private Boolean yearlyResetOk = Boolean.FALSE;

	@Widget(title = "Monthly reset")
	private Boolean monthlyResetOk = Boolean.FALSE;

	@Widget(title = "Versions")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "sequence", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SequenceVersion> sequenceVersionList;

	@Widget(title = "Full name")
	@NameColumn
	private String fullName;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public Sequence() {
	}

	public Sequence(String name, String codeSelect) {
		this.name = name;
		this.codeSelect = codeSelect;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCodeSelect() {
		return codeSelect;
	}

	public void setCodeSelect(String codeSelect) {
		this.codeSelect = codeSelect;
	}

	public SequenceTypeSelect getSequenceTypeSelect() {
		return sequenceTypeSelect;
	}

	public void setSequenceTypeSelect(SequenceTypeSelect sequenceTypeSelect) {
		this.sequenceTypeSelect = sequenceTypeSelect;
	}

	public SequenceLettersTypeSelect getSequenceLettersTypeSelect() {
		return sequenceLettersTypeSelect;
	}

	public void setSequenceLettersTypeSelect(SequenceLettersTypeSelect sequenceLettersTypeSelect) {
		this.sequenceLettersTypeSelect = sequenceLettersTypeSelect;
	}

	public String getPrefixe() {
		return prefixe;
	}

	public void setPrefixe(String prefixe) {
		this.prefixe = prefixe;
	}

	public String getSuffixe() {
		return suffixe;
	}

	public void setSuffixe(String suffixe) {
		this.suffixe = suffixe;
	}

	public Integer getPadding() {
		return padding == null ? 0 : padding;
	}

	public void setPadding(Integer padding) {
		this.padding = padding;
	}

	public Integer getToBeAdded() {
		return toBeAdded == null ? 0 : toBeAdded;
	}

	public void setToBeAdded(Integer toBeAdded) {
		this.toBeAdded = toBeAdded;
	}

	public Boolean getYearlyResetOk() {
		return yearlyResetOk == null ? Boolean.FALSE : yearlyResetOk;
	}

	public void setYearlyResetOk(Boolean yearlyResetOk) {
		this.yearlyResetOk = yearlyResetOk;
	}

	public Boolean getMonthlyResetOk() {
		return monthlyResetOk == null ? Boolean.FALSE : monthlyResetOk;
	}

	public void setMonthlyResetOk(Boolean monthlyResetOk) {
		this.monthlyResetOk = monthlyResetOk;
	}

	public List<SequenceVersion> getSequenceVersionList() {
		return sequenceVersionList;
	}

	public void setSequenceVersionList(List<SequenceVersion> sequenceVersionList) {
		this.sequenceVersionList = sequenceVersionList;
	}

	/**
	 * Add the given {@link SequenceVersion} item to the {@code sequenceVersionList}.
	 *
	 * <p>
	 * It sets {@code item.sequence = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addSequenceVersionListItem(SequenceVersion item) {
		if (getSequenceVersionList() == null) {
			setSequenceVersionList(new ArrayList<>());
		}
		getSequenceVersionList().add(item);
		item.setSequence(this);
	}

	/**
	 * Remove the given {@link SequenceVersion} item from the {@code sequenceVersionList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeSequenceVersionListItem(SequenceVersion item) {
		if (getSequenceVersionList() == null) {
			return;
		}
		getSequenceVersionList().remove(item);
	}

	/**
	 * Clear the {@code sequenceVersionList} collection.
	 *
	 * <p>
	 * If you have to query {@link SequenceVersion} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearSequenceVersionList() {
		if (getSequenceVersionList() != null) {
			getSequenceVersionList().clear();
		}
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
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
		if (!(obj instanceof Sequence)) return false;

		final Sequence other = (Sequence) obj;
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
			.add("codeSelect", getCodeSelect())
			.add("sequenceTypeSelect", getSequenceTypeSelect())
			.add("sequenceLettersTypeSelect", getSequenceLettersTypeSelect())
			.add("prefixe", getPrefixe())
			.add("suffixe", getSuffixe())
			.add("padding", getPadding())
			.add("toBeAdded", getToBeAdded())
			.add("yearlyResetOk", getYearlyResetOk())
			.add("monthlyResetOk", getMonthlyResetOk())
			.add("fullName", getFullName())
			.omitNullValues()
			.toString();
	}
}
