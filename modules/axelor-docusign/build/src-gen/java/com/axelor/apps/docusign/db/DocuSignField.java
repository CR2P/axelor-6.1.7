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
@Table(name = "DOCUSIGN_DOCU_SIGN_FIELD", indexes = { @Index(columnList = "docu_sign_document"), @Index(columnList = "docu_sign_signer"), @Index(columnList = "name"), @Index(columnList = "parent") })
public class DocuSignField extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DOCUSIGN_DOCU_SIGN_FIELD_SEQ")
	@SequenceGenerator(name = "DOCUSIGN_DOCU_SIGN_FIELD_SEQ", sequenceName = "DOCUSIGN_DOCU_SIGN_FIELD_SEQ", allocationSize = 1)
	private Long id;

	@Widget(title = "Document")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private DocuSignDocument docuSignDocument;

	@Widget(title = "Signer")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private DocuSignSigner docuSignSigner;

	@Widget(title = "Name")
	private String name;

	@Widget(title = "Type", selection = "docusign.field.setting.type.select")
	private Integer typeSelect = 0;

	@Widget(title = "Value")
	private String value;

	@Widget(title = "Status")
	private String status;

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
	private DocuSignField parent;

	@Widget(title = "Fields")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<DocuSignField> docuSignFieldList;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public DocuSignField() {
	}

	public DocuSignField(String name) {
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

	public DocuSignDocument getDocuSignDocument() {
		return docuSignDocument;
	}

	public void setDocuSignDocument(DocuSignDocument docuSignDocument) {
		this.docuSignDocument = docuSignDocument;
	}

	public DocuSignSigner getDocuSignSigner() {
		return docuSignSigner;
	}

	public void setDocuSignSigner(DocuSignSigner docuSignSigner) {
		this.docuSignSigner = docuSignSigner;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public DocuSignField getParent() {
		return parent;
	}

	public void setParent(DocuSignField parent) {
		this.parent = parent;
	}

	public List<DocuSignField> getDocuSignFieldList() {
		return docuSignFieldList;
	}

	public void setDocuSignFieldList(List<DocuSignField> docuSignFieldList) {
		this.docuSignFieldList = docuSignFieldList;
	}

	/**
	 * Add the given {@link DocuSignField} item to the {@code docuSignFieldList}.
	 *
	 * <p>
	 * It sets {@code item.parent = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addDocuSignFieldListItem(DocuSignField item) {
		if (getDocuSignFieldList() == null) {
			setDocuSignFieldList(new ArrayList<>());
		}
		getDocuSignFieldList().add(item);
		item.setParent(this);
	}

	/**
	 * Remove the given {@link DocuSignField} item from the {@code docuSignFieldList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeDocuSignFieldListItem(DocuSignField item) {
		if (getDocuSignFieldList() == null) {
			return;
		}
		getDocuSignFieldList().remove(item);
	}

	/**
	 * Clear the {@code docuSignFieldList} collection.
	 *
	 * <p>
	 * If you have to query {@link DocuSignField} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearDocuSignFieldList() {
		if (getDocuSignFieldList() != null) {
			getDocuSignFieldList().clear();
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
		if (!(obj instanceof DocuSignField)) return false;

		final DocuSignField other = (DocuSignField) obj;
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
			.add("status", getStatus())
			.add("tabLabel", getTabLabel())
			.add("pageNumber", getPageNumber())
			.add("anchor", getAnchor())
			.add("anchorUnits", getAnchorUnits())
			.add("anchorYOffset", getAnchorYOffset())
			.add("anchorXOffset", getAnchorXOffset())
			.add("xPosition", getxPosition())
			.omitNullValues()
			.toString();
	}
}
