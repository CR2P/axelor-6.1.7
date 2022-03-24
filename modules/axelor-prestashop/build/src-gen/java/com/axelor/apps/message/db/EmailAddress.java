package com.axelor.apps.message.db;

import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.axelor.apps.base.db.Partner;
import com.axelor.apps.crm.db.Lead;
import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.NameColumn;
import com.axelor.db.annotations.VirtualColumn;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@Table(name = "MESSAGE_EMAIL_ADDRESS", indexes = { @Index(columnList = "address"), @Index(columnList = "name") })
public class EmailAddress extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MESSAGE_EMAIL_ADDRESS_SEQ")
	@SequenceGenerator(name = "MESSAGE_EMAIL_ADDRESS_SEQ", sequenceName = "MESSAGE_EMAIL_ADDRESS_SEQ", allocationSize = 1)
	private Long id;

	@Widget(title = "Contact")
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "emailAddress", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Partner partner;

	@Widget(title = "Address")
	private String address;

	@Widget(readonly = true)
	@NameColumn
	@VirtualColumn
	@Access(AccessType.PROPERTY)
	@NotNull
	private String name;

	@Widget(title = "Lead")
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "emailAddress", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Lead lead;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public EmailAddress() {
	}

	public EmailAddress(String address) {
		this.address = address;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Partner getPartner() {
		return partner;
	}

	public void setPartner(Partner partner) {
		if (getPartner() != null) {
			getPartner().setEmailAddress(null);
		}
		if (partner != null) {
			partner.setEmailAddress(this);
		}
		this.partner = partner;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		try {
			name = computeName();
		} catch (NullPointerException e) {
			Logger logger = LoggerFactory.getLogger(getClass());
			logger.error("NPE in function field: getName()", e);
		}
		return name;
	}

	protected String computeName() {
		String name = "";
		if(partner != null && partner.getFullName() != null)  {   name += partner.getFullName()+" ";  }
		if(address != null)  {   name += "["+address+"]";  }
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Lead getLead() {
		return lead;
	}

	public void setLead(Lead lead) {
		if (getLead() != null) {
			getLead().setEmailAddress(null);
		}
		if (lead != null) {
			lead.setEmailAddress(this);
		}
		this.lead = lead;
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
		if (!(obj instanceof EmailAddress)) return false;

		final EmailAddress other = (EmailAddress) obj;
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
			.add("address", getAddress())
			.omitNullValues()
			.toString();
	}
}
