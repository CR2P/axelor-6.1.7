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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@Table(name = "DOCUSIGN_DOCU_SIGN_DOCUMENT_SETTING", indexes = { @Index(columnList = "name"), @Index(columnList = "docu_sign_envelope_setting") })
public class DocuSignDocumentSetting extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DOCUSIGN_DOCU_SIGN_DOCUMENT_SETTING_SEQ")
	@SequenceGenerator(name = "DOCUSIGN_DOCU_SIGN_DOCUMENT_SETTING_SEQ", sequenceName = "DOCUSIGN_DOCU_SIGN_DOCUMENT_SETTING_SEQ", allocationSize = 1)
	private Long id;

	@Widget(title = "Name")
	private String name;

	@Widget(title = "Document id")
	private String documentId;

	@Widget(title = "DocuSign envelope setting")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private DocuSignEnvelopeSetting docuSignEnvelopeSetting;

	@Widget(title = "Field settings")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "docuSignDocumentSetting", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<DocuSignFieldSetting> docuSignFieldSettingList;

	@Widget(title = "Seq.")
	private Integer sequence = 0;

	@Widget(title = "File extension")
	private String fileExtension;

	@Widget(title = "File default path")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String unsignedMetaFileDefaultPath;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public DocuSignDocumentSetting() {
	}

	public DocuSignDocumentSetting(String name) {
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

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public DocuSignEnvelopeSetting getDocuSignEnvelopeSetting() {
		return docuSignEnvelopeSetting;
	}

	public void setDocuSignEnvelopeSetting(DocuSignEnvelopeSetting docuSignEnvelopeSetting) {
		this.docuSignEnvelopeSetting = docuSignEnvelopeSetting;
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
	 * It sets {@code item.docuSignDocumentSetting = this} to ensure the proper relationship.
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
		item.setDocuSignDocumentSetting(this);
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

	public Integer getSequence() {
		return sequence == null ? 0 : sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	public String getUnsignedMetaFileDefaultPath() {
		return unsignedMetaFileDefaultPath;
	}

	public void setUnsignedMetaFileDefaultPath(String unsignedMetaFileDefaultPath) {
		this.unsignedMetaFileDefaultPath = unsignedMetaFileDefaultPath;
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
		if (!(obj instanceof DocuSignDocumentSetting)) return false;

		final DocuSignDocumentSetting other = (DocuSignDocumentSetting) obj;
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
			.add("documentId", getDocumentId())
			.add("sequence", getSequence())
			.add("fileExtension", getFileExtension())
			.omitNullValues()
			.toString();
	}
}
