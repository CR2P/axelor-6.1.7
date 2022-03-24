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

import com.axelor.apps.base.db.Company;
import com.axelor.apps.base.db.Partner;
import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.NameColumn;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@Table(name = "DOCUSIGN_DOCU_SIGN_SIGNER", indexes = { @Index(columnList = "name"), @Index(columnList = "docu_sign_envelope"), @Index(columnList = "signer"), @Index(columnList = "company") })
public class DocuSignSigner extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DOCUSIGN_DOCU_SIGN_SIGNER_SEQ")
	@SequenceGenerator(name = "DOCUSIGN_DOCU_SIGN_SIGNER_SEQ", sequenceName = "DOCUSIGN_DOCU_SIGN_SIGNER_SEQ", allocationSize = 1)
	private Long id;

	@Widget(title = "Name")
	@NameColumn
	private String name;

	@Widget(title = "Recipient id")
	private String recipientId;

	@Widget(title = "Envelope")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private DocuSignEnvelope docuSignEnvelope;

	@Widget(title = "Signer")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Partner signer;

	@Widget(title = "Company")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Company company;

	@Widget(title = "Required")
	private Boolean isRequired = Boolean.FALSE;

	@Widget(title = "Fields")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "docuSignSigner", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<DocuSignField> docuSignFieldList;

	@Widget(title = "Seq.")
	private Integer sequence = 0;

	@Widget(title = "Access code")
	private String accessCode;

	@Widget(title = "In person signer")
	private Boolean isInPersonSigner = Boolean.FALSE;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public DocuSignSigner() {
	}

	public DocuSignSigner(String name) {
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

	public String getRecipientId() {
		return recipientId;
	}

	public void setRecipientId(String recipientId) {
		this.recipientId = recipientId;
	}

	public DocuSignEnvelope getDocuSignEnvelope() {
		return docuSignEnvelope;
	}

	public void setDocuSignEnvelope(DocuSignEnvelope docuSignEnvelope) {
		this.docuSignEnvelope = docuSignEnvelope;
	}

	public Partner getSigner() {
		return signer;
	}

	public void setSigner(Partner signer) {
		this.signer = signer;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Boolean getIsRequired() {
		return isRequired == null ? Boolean.FALSE : isRequired;
	}

	public void setIsRequired(Boolean isRequired) {
		this.isRequired = isRequired;
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
	 * It sets {@code item.docuSignSigner = this} to ensure the proper relationship.
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
		item.setDocuSignSigner(this);
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

	public String getAccessCode() {
		return accessCode;
	}

	public void setAccessCode(String accessCode) {
		this.accessCode = accessCode;
	}

	public Boolean getIsInPersonSigner() {
		return isInPersonSigner == null ? Boolean.FALSE : isInPersonSigner;
	}

	public void setIsInPersonSigner(Boolean isInPersonSigner) {
		this.isInPersonSigner = isInPersonSigner;
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
		if (!(obj instanceof DocuSignSigner)) return false;

		final DocuSignSigner other = (DocuSignSigner) obj;
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
			.add("recipientId", getRecipientId())
			.add("isRequired", getIsRequired())
			.add("sequence", getSequence())
			.add("accessCode", getAccessCode())
			.add("isInPersonSigner", getIsInPersonSigner())
			.omitNullValues()
			.toString();
	}
}
