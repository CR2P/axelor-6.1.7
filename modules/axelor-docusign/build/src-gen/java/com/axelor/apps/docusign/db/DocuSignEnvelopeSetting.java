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
import com.axelor.meta.db.MetaModel;
import com.google.common.base.MoreObjects;

@Entity
@Table(name = "DOCUSIGN_DOCU_SIGN_ENVELOPE_SETTING", indexes = { @Index(columnList = "name"), @Index(columnList = "meta_model"), @Index(columnList = "docu_sign_account") })
public class DocuSignEnvelopeSetting extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DOCUSIGN_DOCU_SIGN_ENVELOPE_SETTING_SEQ")
	@SequenceGenerator(name = "DOCUSIGN_DOCU_SIGN_ENVELOPE_SETTING_SEQ", sequenceName = "DOCUSIGN_DOCU_SIGN_ENVELOPE_SETTING_SEQ", allocationSize = 1)
	private Long id;

	@Widget(title = "Name")
	private String name;

	@Widget(title = "Model")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private MetaModel metaModel;

	@Widget(title = "Email subject")
	private String emailSubject;

	@Widget(title = "DocuSign account")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private DocuSignAccount docuSignAccount;

	@Widget(title = "Document settings")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "docuSignEnvelopeSetting", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<DocuSignDocumentSetting> docuSignDocumentSettingList;

	@Widget(title = "Signer settings")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "docuSignEnvelopeSetting", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<DocuSignSignerSetting> docuSignSignerSettingList;

	@Widget(title = "Ordered documents")
	private Boolean isOrderedDocuments = Boolean.FALSE;

	@Widget(title = "Ordered signers")
	private Boolean isOrderedSigners = Boolean.FALSE;

	@Widget(title = "Active webhook")
	private Boolean activeWebhook = Boolean.TRUE;

	@Widget(title = "Check envelope status")
	private Boolean checkEnvelopeStatus = Boolean.TRUE;

	@Widget(title = "Check recipient status")
	private Boolean checkRecipientStatus = Boolean.FALSE;

	@Widget(title = "Delivered")
	private Boolean checkEnvelopeStatusDelivered = Boolean.FALSE;

	@Widget(title = "Completed")
	private Boolean checkEnvelopeStatusCompleted = Boolean.TRUE;

	@Widget(title = "Declined")
	private Boolean checkEnvelopeStatusDeclined = Boolean.TRUE;

	@Widget(title = "Voided")
	private Boolean checkEnvelopeStatusVoided = Boolean.TRUE;

	@Widget(title = "Delivered")
	private Boolean checkRecipientStatusDelivered = Boolean.FALSE;

	@Widget(title = "Completed")
	private Boolean checkRecipientStatusCompleted = Boolean.FALSE;

	@Widget(title = "Declined")
	private Boolean checkRecipientStatusDeclined = Boolean.FALSE;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public DocuSignEnvelopeSetting() {
	}

	public DocuSignEnvelopeSetting(String name) {
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

	public MetaModel getMetaModel() {
		return metaModel;
	}

	public void setMetaModel(MetaModel metaModel) {
		this.metaModel = metaModel;
	}

	public String getEmailSubject() {
		return emailSubject;
	}

	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}

	public DocuSignAccount getDocuSignAccount() {
		return docuSignAccount;
	}

	public void setDocuSignAccount(DocuSignAccount docuSignAccount) {
		this.docuSignAccount = docuSignAccount;
	}

	public List<DocuSignDocumentSetting> getDocuSignDocumentSettingList() {
		return docuSignDocumentSettingList;
	}

	public void setDocuSignDocumentSettingList(List<DocuSignDocumentSetting> docuSignDocumentSettingList) {
		this.docuSignDocumentSettingList = docuSignDocumentSettingList;
	}

	/**
	 * Add the given {@link DocuSignDocumentSetting} item to the {@code docuSignDocumentSettingList}.
	 *
	 * <p>
	 * It sets {@code item.docuSignEnvelopeSetting = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addDocuSignDocumentSettingListItem(DocuSignDocumentSetting item) {
		if (getDocuSignDocumentSettingList() == null) {
			setDocuSignDocumentSettingList(new ArrayList<>());
		}
		getDocuSignDocumentSettingList().add(item);
		item.setDocuSignEnvelopeSetting(this);
	}

	/**
	 * Remove the given {@link DocuSignDocumentSetting} item from the {@code docuSignDocumentSettingList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeDocuSignDocumentSettingListItem(DocuSignDocumentSetting item) {
		if (getDocuSignDocumentSettingList() == null) {
			return;
		}
		getDocuSignDocumentSettingList().remove(item);
	}

	/**
	 * Clear the {@code docuSignDocumentSettingList} collection.
	 *
	 * <p>
	 * If you have to query {@link DocuSignDocumentSetting} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearDocuSignDocumentSettingList() {
		if (getDocuSignDocumentSettingList() != null) {
			getDocuSignDocumentSettingList().clear();
		}
	}

	public List<DocuSignSignerSetting> getDocuSignSignerSettingList() {
		return docuSignSignerSettingList;
	}

	public void setDocuSignSignerSettingList(List<DocuSignSignerSetting> docuSignSignerSettingList) {
		this.docuSignSignerSettingList = docuSignSignerSettingList;
	}

	/**
	 * Add the given {@link DocuSignSignerSetting} item to the {@code docuSignSignerSettingList}.
	 *
	 * <p>
	 * It sets {@code item.docuSignEnvelopeSetting = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addDocuSignSignerSettingListItem(DocuSignSignerSetting item) {
		if (getDocuSignSignerSettingList() == null) {
			setDocuSignSignerSettingList(new ArrayList<>());
		}
		getDocuSignSignerSettingList().add(item);
		item.setDocuSignEnvelopeSetting(this);
	}

	/**
	 * Remove the given {@link DocuSignSignerSetting} item from the {@code docuSignSignerSettingList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeDocuSignSignerSettingListItem(DocuSignSignerSetting item) {
		if (getDocuSignSignerSettingList() == null) {
			return;
		}
		getDocuSignSignerSettingList().remove(item);
	}

	/**
	 * Clear the {@code docuSignSignerSettingList} collection.
	 *
	 * <p>
	 * If you have to query {@link DocuSignSignerSetting} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearDocuSignSignerSettingList() {
		if (getDocuSignSignerSettingList() != null) {
			getDocuSignSignerSettingList().clear();
		}
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

	public Boolean getActiveWebhook() {
		return activeWebhook == null ? Boolean.FALSE : activeWebhook;
	}

	public void setActiveWebhook(Boolean activeWebhook) {
		this.activeWebhook = activeWebhook;
	}

	public Boolean getCheckEnvelopeStatus() {
		return checkEnvelopeStatus == null ? Boolean.FALSE : checkEnvelopeStatus;
	}

	public void setCheckEnvelopeStatus(Boolean checkEnvelopeStatus) {
		this.checkEnvelopeStatus = checkEnvelopeStatus;
	}

	public Boolean getCheckRecipientStatus() {
		return checkRecipientStatus == null ? Boolean.FALSE : checkRecipientStatus;
	}

	public void setCheckRecipientStatus(Boolean checkRecipientStatus) {
		this.checkRecipientStatus = checkRecipientStatus;
	}

	public Boolean getCheckEnvelopeStatusDelivered() {
		return checkEnvelopeStatusDelivered == null ? Boolean.FALSE : checkEnvelopeStatusDelivered;
	}

	public void setCheckEnvelopeStatusDelivered(Boolean checkEnvelopeStatusDelivered) {
		this.checkEnvelopeStatusDelivered = checkEnvelopeStatusDelivered;
	}

	public Boolean getCheckEnvelopeStatusCompleted() {
		return checkEnvelopeStatusCompleted == null ? Boolean.FALSE : checkEnvelopeStatusCompleted;
	}

	public void setCheckEnvelopeStatusCompleted(Boolean checkEnvelopeStatusCompleted) {
		this.checkEnvelopeStatusCompleted = checkEnvelopeStatusCompleted;
	}

	public Boolean getCheckEnvelopeStatusDeclined() {
		return checkEnvelopeStatusDeclined == null ? Boolean.FALSE : checkEnvelopeStatusDeclined;
	}

	public void setCheckEnvelopeStatusDeclined(Boolean checkEnvelopeStatusDeclined) {
		this.checkEnvelopeStatusDeclined = checkEnvelopeStatusDeclined;
	}

	public Boolean getCheckEnvelopeStatusVoided() {
		return checkEnvelopeStatusVoided == null ? Boolean.FALSE : checkEnvelopeStatusVoided;
	}

	public void setCheckEnvelopeStatusVoided(Boolean checkEnvelopeStatusVoided) {
		this.checkEnvelopeStatusVoided = checkEnvelopeStatusVoided;
	}

	public Boolean getCheckRecipientStatusDelivered() {
		return checkRecipientStatusDelivered == null ? Boolean.FALSE : checkRecipientStatusDelivered;
	}

	public void setCheckRecipientStatusDelivered(Boolean checkRecipientStatusDelivered) {
		this.checkRecipientStatusDelivered = checkRecipientStatusDelivered;
	}

	public Boolean getCheckRecipientStatusCompleted() {
		return checkRecipientStatusCompleted == null ? Boolean.FALSE : checkRecipientStatusCompleted;
	}

	public void setCheckRecipientStatusCompleted(Boolean checkRecipientStatusCompleted) {
		this.checkRecipientStatusCompleted = checkRecipientStatusCompleted;
	}

	public Boolean getCheckRecipientStatusDeclined() {
		return checkRecipientStatusDeclined == null ? Boolean.FALSE : checkRecipientStatusDeclined;
	}

	public void setCheckRecipientStatusDeclined(Boolean checkRecipientStatusDeclined) {
		this.checkRecipientStatusDeclined = checkRecipientStatusDeclined;
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
		if (!(obj instanceof DocuSignEnvelopeSetting)) return false;

		final DocuSignEnvelopeSetting other = (DocuSignEnvelopeSetting) obj;
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
			.add("emailSubject", getEmailSubject())
			.add("isOrderedDocuments", getIsOrderedDocuments())
			.add("isOrderedSigners", getIsOrderedSigners())
			.add("activeWebhook", getActiveWebhook())
			.add("checkEnvelopeStatus", getCheckEnvelopeStatus())
			.add("checkRecipientStatus", getCheckRecipientStatus())
			.add("checkEnvelopeStatusDelivered", getCheckEnvelopeStatusDelivered())
			.add("checkEnvelopeStatusCompleted", getCheckEnvelopeStatusCompleted())
			.add("checkEnvelopeStatusDeclined", getCheckEnvelopeStatusDeclined())
			.add("checkEnvelopeStatusVoided", getCheckEnvelopeStatusVoided())
			.omitNullValues()
			.toString();
	}
}
