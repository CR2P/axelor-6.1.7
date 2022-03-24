package com.axelor.apps.docusign.db;

import java.time.LocalDateTime;
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
import com.axelor.meta.db.MetaFile;
import com.google.common.base.MoreObjects;

@Entity
@Table(name = "DOCUSIGN_DOCU_SIGN_ENVELOPE", indexes = { @Index(columnList = "docu_sign_envelope_setting"), @Index(columnList = "name"), @Index(columnList = "certificate_meta_file") })
public class DocuSignEnvelope extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DOCUSIGN_DOCU_SIGN_ENVELOPE_SEQ")
	@SequenceGenerator(name = "DOCUSIGN_DOCU_SIGN_ENVELOPE_SEQ", sequenceName = "DOCUSIGN_DOCU_SIGN_ENVELOPE_SEQ", allocationSize = 1)
	private Long id;

	@Widget(title = "Envelope setting")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private DocuSignEnvelopeSetting docuSignEnvelopeSetting;

	@Widget(title = "Name")
	private String name;

	@Widget(title = "Envelope id")
	private String envelopeId;

	@Widget(title = "Email subject")
	private String emailSubject;

	@Widget(title = "Documents")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "docuSignEnvelope", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<DocuSignDocument> docuSignDocumentList;

	@Widget(title = "Signers")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "docuSignEnvelope", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<DocuSignSigner> docuSignSignerList;

	@Widget(title = "Status", selection = "docusign.envelope.status.select")
	private String statusSelect;

	@Widget(title = "Related to", selection = "docusign.envelope.related.to.select")
	private String relatedToSelect;

	private Long relatedToId = 0L;

	@Widget(title = "Ordered documents")
	private Boolean isOrderedDocuments = Boolean.FALSE;

	@Widget(title = "Ordered signers")
	private Boolean isOrderedSigners = Boolean.FALSE;

	@Widget(title = "Certificate of completion")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private MetaFile certificateMetaFile;

	@Widget(title = "Completed date time")
	private LocalDateTime completedDateTime;

	@Widget(title = "Declined date time")
	private LocalDateTime declinedDateTime;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public DocuSignEnvelope() {
	}

	public DocuSignEnvelope(String name) {
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

	public DocuSignEnvelopeSetting getDocuSignEnvelopeSetting() {
		return docuSignEnvelopeSetting;
	}

	public void setDocuSignEnvelopeSetting(DocuSignEnvelopeSetting docuSignEnvelopeSetting) {
		this.docuSignEnvelopeSetting = docuSignEnvelopeSetting;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEnvelopeId() {
		return envelopeId;
	}

	public void setEnvelopeId(String envelopeId) {
		this.envelopeId = envelopeId;
	}

	public String getEmailSubject() {
		return emailSubject;
	}

	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}

	public List<DocuSignDocument> getDocuSignDocumentList() {
		return docuSignDocumentList;
	}

	public void setDocuSignDocumentList(List<DocuSignDocument> docuSignDocumentList) {
		this.docuSignDocumentList = docuSignDocumentList;
	}

	/**
	 * Add the given {@link DocuSignDocument} item to the {@code docuSignDocumentList}.
	 *
	 * <p>
	 * It sets {@code item.docuSignEnvelope = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addDocuSignDocumentListItem(DocuSignDocument item) {
		if (getDocuSignDocumentList() == null) {
			setDocuSignDocumentList(new ArrayList<>());
		}
		getDocuSignDocumentList().add(item);
		item.setDocuSignEnvelope(this);
	}

	/**
	 * Remove the given {@link DocuSignDocument} item from the {@code docuSignDocumentList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeDocuSignDocumentListItem(DocuSignDocument item) {
		if (getDocuSignDocumentList() == null) {
			return;
		}
		getDocuSignDocumentList().remove(item);
	}

	/**
	 * Clear the {@code docuSignDocumentList} collection.
	 *
	 * <p>
	 * If you have to query {@link DocuSignDocument} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearDocuSignDocumentList() {
		if (getDocuSignDocumentList() != null) {
			getDocuSignDocumentList().clear();
		}
	}

	public List<DocuSignSigner> getDocuSignSignerList() {
		return docuSignSignerList;
	}

	public void setDocuSignSignerList(List<DocuSignSigner> docuSignSignerList) {
		this.docuSignSignerList = docuSignSignerList;
	}

	/**
	 * Add the given {@link DocuSignSigner} item to the {@code docuSignSignerList}.
	 *
	 * <p>
	 * It sets {@code item.docuSignEnvelope = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addDocuSignSignerListItem(DocuSignSigner item) {
		if (getDocuSignSignerList() == null) {
			setDocuSignSignerList(new ArrayList<>());
		}
		getDocuSignSignerList().add(item);
		item.setDocuSignEnvelope(this);
	}

	/**
	 * Remove the given {@link DocuSignSigner} item from the {@code docuSignSignerList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeDocuSignSignerListItem(DocuSignSigner item) {
		if (getDocuSignSignerList() == null) {
			return;
		}
		getDocuSignSignerList().remove(item);
	}

	/**
	 * Clear the {@code docuSignSignerList} collection.
	 *
	 * <p>
	 * If you have to query {@link DocuSignSigner} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearDocuSignSignerList() {
		if (getDocuSignSignerList() != null) {
			getDocuSignSignerList().clear();
		}
	}

	public String getStatusSelect() {
		return statusSelect;
	}

	public void setStatusSelect(String statusSelect) {
		this.statusSelect = statusSelect;
	}

	public String getRelatedToSelect() {
		return relatedToSelect;
	}

	public void setRelatedToSelect(String relatedToSelect) {
		this.relatedToSelect = relatedToSelect;
	}

	public Long getRelatedToId() {
		return relatedToId == null ? 0L : relatedToId;
	}

	public void setRelatedToId(Long relatedToId) {
		this.relatedToId = relatedToId;
	}

	public Boolean getIsOrderedDocuments() {
		return isOrderedDocuments == null ? Boolean.FALSE : isOrderedDocuments;
	}

	public void setIsOrderedDocuments(Boolean isOrderedDocuments) {
		this.isOrderedDocuments = isOrderedDocuments;
	}

	public Boolean getIsOrderedSigners() {
		return isOrderedSigners == null ? Boolean.FALSE : isOrderedSigners;
	}

	public void setIsOrderedSigners(Boolean isOrderedSigners) {
		this.isOrderedSigners = isOrderedSigners;
	}

	public MetaFile getCertificateMetaFile() {
		return certificateMetaFile;
	}

	public void setCertificateMetaFile(MetaFile certificateMetaFile) {
		this.certificateMetaFile = certificateMetaFile;
	}

	public LocalDateTime getCompletedDateTime() {
		return completedDateTime;
	}

	public void setCompletedDateTime(LocalDateTime completedDateTime) {
		this.completedDateTime = completedDateTime;
	}

	public LocalDateTime getDeclinedDateTime() {
		return declinedDateTime;
	}

	public void setDeclinedDateTime(LocalDateTime declinedDateTime) {
		this.declinedDateTime = declinedDateTime;
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
		if (!(obj instanceof DocuSignEnvelope)) return false;

		final DocuSignEnvelope other = (DocuSignEnvelope) obj;
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
			.add("envelopeId", getEnvelopeId())
			.add("emailSubject", getEmailSubject())
			.add("statusSelect", getStatusSelect())
			.add("relatedToSelect", getRelatedToSelect())
			.add("relatedToId", getRelatedToId())
			.add("isOrderedDocuments", getIsOrderedDocuments())
			.add("isOrderedSigners", getIsOrderedSigners())
			.add("completedDateTime", getCompletedDateTime())
			.add("declinedDateTime", getDeclinedDateTime())
			.omitNullValues()
			.toString();
	}
}
