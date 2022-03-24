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
import com.axelor.db.annotations.NameColumn;
import com.axelor.db.annotations.Widget;
import com.axelor.meta.db.MetaFile;
import com.google.common.base.MoreObjects;

@Entity
@Table(name = "DOCUSIGN_DOCU_SIGN_DOCUMENT", indexes = { @Index(columnList = "name"), @Index(columnList = "docu_sign_envelope"), @Index(columnList = "unsigned_meta_file"), @Index(columnList = "signed_meta_file") })
public class DocuSignDocument extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DOCUSIGN_DOCU_SIGN_DOCUMENT_SEQ")
	@SequenceGenerator(name = "DOCUSIGN_DOCU_SIGN_DOCUMENT_SEQ", sequenceName = "DOCUSIGN_DOCU_SIGN_DOCUMENT_SEQ", allocationSize = 1)
	private Long id;

	@Widget(title = "Name")
	@NameColumn
	private String name;

	@Widget(title = "Document id")
	private String documentId;

	@Widget(title = "DocuSign envelope")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private DocuSignEnvelope docuSignEnvelope;

	@Widget(title = "Unsigned file")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private MetaFile unsignedMetaFile;

	@Widget(title = "Signed file")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private MetaFile signedMetaFile;

	@Widget(title = "Fields")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "docuSignDocument", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<DocuSignField> docuSignFieldList;

	@Widget(title = "Seq.")
	private Integer sequence = 0;

	@Widget(title = "File extension")
	private String fileExtension;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public DocuSignDocument() {
	}

	public DocuSignDocument(String name) {
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

	public DocuSignEnvelope getDocuSignEnvelope() {
		return docuSignEnvelope;
	}

	public void setDocuSignEnvelope(DocuSignEnvelope docuSignEnvelope) {
		this.docuSignEnvelope = docuSignEnvelope;
	}

	public MetaFile getUnsignedMetaFile() {
		return unsignedMetaFile;
	}

	public void setUnsignedMetaFile(MetaFile unsignedMetaFile) {
		this.unsignedMetaFile = unsignedMetaFile;
	}

	public MetaFile getSignedMetaFile() {
		return signedMetaFile;
	}

	public void setSignedMetaFile(MetaFile signedMetaFile) {
		this.signedMetaFile = signedMetaFile;
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
	 * It sets {@code item.docuSignDocument = this} to ensure the proper relationship.
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
		item.setDocuSignDocument(this);
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
		if (!(obj instanceof DocuSignDocument)) return false;

		final DocuSignDocument other = (DocuSignDocument) obj;
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
