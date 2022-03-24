package com.axelor.apps.docusign.db;

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

import org.hibernate.annotations.Type;

import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@Table(name = "DOCUSIGN_DOCU_SIGN_FIELD_SETTING", indexes = { @Index(columnList = "docu_sign_document_setting"), @Index(columnList = "docu_sign_signer_setting"), @Index(columnList = "name"), @Index(columnList = "parent") })
public class DocuSignFieldSetting extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DOCUSIGN_DOCU_SIGN_FIELD_SETTING_SEQ")
	@SequenceGenerator(name = "DOCUSIGN_DOCU_SIGN_FIELD_SETTING_SEQ", sequenceName = "DOCUSIGN_DOCU_SIGN_FIELD_SETTING_SEQ", allocationSize = 1)
	private Long id;

	@Widget(title = "Document setting")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private DocuSignDocumentSetting docuSignDocumentSetting;

	@Widget(title = "Signer setting")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private DocuSignSignerSetting docuSignSignerSetting;

	@Widget(title = "Name")
	private String name;

	@Widget(title = "Type", selection = "docusign.field.setting.type.select")
	private Integer typeSelect = 0;

	@Widget(title = "Value")
	private String value;

	@Widget(title = "Tab label")
	private String tabLabel;

	@Widget(title = "Page number")
	private String pageNumber;

	@Widget(title = "Anchor")
	private String anchor;

	@Widget(title = "Anchor units")
	private String anchorUnits;

	@Widget(title = "Anchor Y offset")
	private String anchorYOffset;

	@Widget(title = "Anchor X offset")
	private String anchorXOffset;

	@Widget(title = "X position")
	private String xPosition;

	@Widget(title = "Y position")
	private String yPosition;

	@Widget(title = "Required")
	private Boolean isRequired = Boolean.TRUE;

	@Widget(title = "Font")
	private String font;

	@Widget(title = "Font size")
	private String fontSize;

	@Widget(title = "Font color")
	private String fontColor;

	@Widget(title = "Bold")
	private Boolean isBold = Boolean.FALSE;

	@Widget(title = "Parent")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private DocuSignFieldSetting parent;

	@Widget(title = "Field settings")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<DocuSignFieldSetting> docuSignFieldSettingList;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public DocuSignFieldSetting() {
	}

	public DocuSignFieldSetting(String name) {
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

	public DocuSignDocumentSetting getDocuSignDocumentSetting() {
		return docuSignDocumentSetting;
	}

	public void setDocuSignDocumentSetting(DocuSignDocumentSetting docuSignDocumentSetting) {
		this.docuSignDocumentSetting = docuSignDocumentSetting;
	}

	public DocuSignSignerSetting getDocuSignSignerSetting() {
		return docuSignSignerSetting;
	}

	public void setDocuSignSignerSetting(DocuSignSignerSetting docuSignSignerSetting) {
		this.docuSignSignerSetting = docuSignSignerSetting;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getTypeSelect() {
		return typeSelect == null ? 0 : typeSelect;
	}

	public void setTypeSelect(Integer typeSelect) {
		this.typeSelect = typeSelect;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getTabLabel() {
		return tabLabel;
	}

	public void setTabLabel(String tabLabel) {
		this.tabLabel = tabLabel;
	}

	public String getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getAnchor() {
		return anchor;
	}

	public void setAnchor(String anchor) {
		this.anchor = anchor;
	}

	public String getAnchorUnits() {
		return anchorUnits;
	}

	public void setAnchorUnits(String anchorUnits) {
		this.anchorUnits = anchorUnits;
	}

	public String getAnchorYOffset() {
		return anchorYOffset;
	}

	public void setAnchorYOffset(String anchorYOffset) {
		this.anchorYOffset = anchorYOffset;
	}

	public String getAnchorXOffset() {
		return anchorXOffset;
	}

	public void setAnchorXOffset(String anchorXOffset) {
		this.anchorXOffset = anchorXOffset;
	}

	public String getxPosition() {
		return xPosition;
	}

	public void setxPosition(String xPosition) {
		this.xPosition = xPosition;
	}

	public String getyPosition() {
		return yPosition;
	}

	public void setyPosition(String yPosition) {
		this.yPosition = yPosition;
	}

	public Boolean getIsRequired() {
		return isRequired == null ? Boolean.FALSE : isRequired;
	}

	public void setIsRequired(Boolean isRequired) {
		this.isRequired = isRequired;
	}

	public String getFont() {
		return font;
	}

	public void setFont(String font) {
		this.font = font;
	}

	public String getFontSize() {
		return fontSize;
	}

	public void setFontSize(String fontSize) {
		this.fontSize = fontSize;
	}

	public String getFontColor() {
		return fontColor;
	}

	public void setFontColor(String fontColor) {
		this.fontColor = fontColor;
	}

	public Boolean getIsBold() {
		return isBold == null ? Boolean.FALSE : isBold;
	}

	public void setIsBold(Boolean isBold) {
		this.isBold = isBold;
	}

	public DocuSignFieldSetting getParent() {
		return parent;
	}

	public void setParent(DocuSignFieldSetting parent) {
		this.parent = parent;
	}

	public List<DocuSignFieldSetting> getDocuSignFieldSettingList() {
		return docuSignFieldSettingList;
	}

	public void setDocuSignFieldSettingList(List<DocuSignFieldSetting> docuSignFieldSettingList) {
		this.docuSignFieldSettingList = docuSignFieldSettingList;
	}

	/**
	 * Add the given {@link DocuSignFieldSetting} item to the {@code docuSignFieldSettingList}.
	 *
	 * <p>
	 * It sets {@code item.parent = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addDocuSignFieldSettingListItem(DocuSignFieldSetting item) {
		if (getDocuSignFieldSettingList() == null) {
			setDocuSignFieldSettingList(new ArrayList<>());
		}
		getDocuSignFieldSettingList().add(item);
		item.setParent(this);
	}

	/**
	 * Remove the given {@link DocuSignFieldSetting} item from the {@code docuSignFieldSettingList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeDocuSignFieldSettingListItem(DocuSignFieldSetting item) {
		if (getDocuSignFieldSettingList() == null) {
			return;
		}
		getDocuSignFieldSettingList().remove(item);
	}

	/**
	 * Clear the {@code docuSignFieldSettingList} collection.
	 *
	 * <p>
	 * If you have to query {@link DocuSignFieldSetting} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearDocuSignFieldSettingList() {
		if (getDocuSignFieldSettingList() != null) {
			getDocuSignFieldSettingList().clear();
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
		if (!(obj instanceof DocuSignFieldSetting)) return false;

		final DocuSignFieldSetting other = (DocuSignFieldSetting) obj;
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
			.add("value", getValue())
			.add("tabLabel", getTabLabel())
			.add("pageNumber", getPageNumber())
			.add("anchor", getAnchor())
			.add("anchorUnits", getAnchorUnits())
			.add("anchorYOffset", getAnchorYOffset())
			.add("anchorXOffset", getAnchorXOffset())
			.add("xPosition", getxPosition())
			.add("yPosition", getyPosition())
			.omitNullValues()
			.toString();
	}
}
