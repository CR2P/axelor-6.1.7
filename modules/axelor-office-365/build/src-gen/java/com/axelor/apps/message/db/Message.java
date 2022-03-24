package com.axelor.apps.message.db;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.axelor.apps.base.db.Company;
import com.axelor.apps.crm.db.Event;
import com.axelor.apps.office.db.OfficeAccount;
import com.axelor.auth.db.AuditableModel;
import com.axelor.auth.db.User;
import com.axelor.db.annotations.NameColumn;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@Table(name = "MESSAGE_MESSAGE", indexes = { @Index(columnList = "subject"), @Index(columnList = "from_email_address"), @Index(columnList = "mail_account"), @Index(columnList = "sender_user"), @Index(columnList = "recipient_user"), @Index(columnList = "template"), @Index(columnList = "company"), @Index(columnList = "event"), @Index(columnList = "office_account") })
public class Message extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MESSAGE_MESSAGE_SEQ")
	@SequenceGenerator(name = "MESSAGE_MESSAGE_SEQ", sequenceName = "MESSAGE_MESSAGE_SEQ", allocationSize = 1)
	private Long id;

	@Widget(title = "Type", readonly = true, selection = "message.type.select")
	private Integer typeSelect = 2;

	@Widget(title = "Subject")
	@NameColumn
	private String subject;

	@Widget(title = "Content")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String content;

	@Widget(title = "Sent date", readonly = true)
	private LocalDateTime sentDateT;

	@Widget(title = "Forecasted Sent Date")
	private LocalDate sendScheduleDate;

	@Widget(title = "Related to", selection = "message.related.to.select")
	private String relatedTo1Select;

	private Long relatedTo1SelectId = 0L;

	@Widget(title = "Related to", selection = "message.related.to.select")
	private String relatedTo2Select;

	private Long relatedTo2SelectId = 0L;

	@Widget(title = "Status", readonly = true, selection = "message.status.select")
	private Integer statusSelect = 1;

	@Widget(title = "Media Type", selection = "message.media.type.select")
	private Integer mediaTypeSelect = 0;

	@Widget(title = "Address Block", multiline = true)
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String addressBlock;

	@Widget(title = "From")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private EmailAddress fromEmailAddress;

	@Widget(title = "Reply to")
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<EmailAddress> replyToEmailAddressSet;

	@Widget(title = "To")
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<EmailAddress> toEmailAddressSet;

	@Widget(title = "Cc")
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<EmailAddress> ccEmailAddressSet;

	@Widget(title = "Bcc")
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<EmailAddress> bccEmailAddressSet;

	@Widget(title = "Sent by email")
	private Boolean sentByEmail = Boolean.FALSE;

	@Widget(title = "Mail account")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private EmailAccount mailAccount;

	@Widget(title = "Sender (User)", readonly = true)
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private User senderUser;

	@Widget(title = "Recipient")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private User recipientUser;

	@Widget(readonly = true)
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Template template;

	@Widget(title = "Thread ID", hidden = true)
	private String emailThreadId;

	@Widget(title = "Mobile phone")
	private String toMobilePhone;

	@Widget(title = "Company")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Company company;

	@Widget(title = "Event")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Event event;

	@Widget(title = "Office365 id")
	private String office365Id;

	@Widget(title = "Office account")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private OfficeAccount officeAccount;

	@Widget(title = "Received date", readonly = true)
	private LocalDateTime receivedDateT;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public Message() {
	}

	public Message(Integer typeSelect, String subject, String content, Integer statusSelect, Integer mediaTypeSelect, String addressBlock, EmailAddress fromEmailAddress, Set<EmailAddress> replyToEmailAddressSet, Set<EmailAddress> toEmailAddressSet, Set<EmailAddress> ccEmailAddressSet, Set<EmailAddress> bccEmailAddressSet, Boolean sentByEmail, EmailAccount mailAccount) {
		this.typeSelect = typeSelect;
		this.subject = subject;
		this.content = content;
		this.statusSelect = statusSelect;
		this.mediaTypeSelect = mediaTypeSelect;
		this.addressBlock = addressBlock;
		this.fromEmailAddress = fromEmailAddress;
		this.replyToEmailAddressSet = replyToEmailAddressSet;
		this.toEmailAddressSet = toEmailAddressSet;
		this.ccEmailAddressSet = ccEmailAddressSet;
		this.bccEmailAddressSet = bccEmailAddressSet;
		this.sentByEmail = sentByEmail;
		this.mailAccount = mailAccount;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Integer getTypeSelect() {
		return typeSelect == null ? 0 : typeSelect;
	}

	public void setTypeSelect(Integer typeSelect) {
		this.typeSelect = typeSelect;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getSentDateT() {
		return sentDateT;
	}

	public void setSentDateT(LocalDateTime sentDateT) {
		this.sentDateT = sentDateT;
	}

	public LocalDate getSendScheduleDate() {
		return sendScheduleDate;
	}

	public void setSendScheduleDate(LocalDate sendScheduleDate) {
		this.sendScheduleDate = sendScheduleDate;
	}

	public String getRelatedTo1Select() {
		return relatedTo1Select;
	}

	public void setRelatedTo1Select(String relatedTo1Select) {
		this.relatedTo1Select = relatedTo1Select;
	}

	public Long getRelatedTo1SelectId() {
		return relatedTo1SelectId == null ? 0L : relatedTo1SelectId;
	}

	public void setRelatedTo1SelectId(Long relatedTo1SelectId) {
		this.relatedTo1SelectId = relatedTo1SelectId;
	}

	public String getRelatedTo2Select() {
		return relatedTo2Select;
	}

	public void setRelatedTo2Select(String relatedTo2Select) {
		this.relatedTo2Select = relatedTo2Select;
	}

	public Long getRelatedTo2SelectId() {
		return relatedTo2SelectId == null ? 0L : relatedTo2SelectId;
	}

	public void setRelatedTo2SelectId(Long relatedTo2SelectId) {
		this.relatedTo2SelectId = relatedTo2SelectId;
	}

	public Integer getStatusSelect() {
		return statusSelect == null ? 0 : statusSelect;
	}

	public void setStatusSelect(Integer statusSelect) {
		this.statusSelect = statusSelect;
	}

	public Integer getMediaTypeSelect() {
		return mediaTypeSelect == null ? 0 : mediaTypeSelect;
	}

	public void setMediaTypeSelect(Integer mediaTypeSelect) {
		this.mediaTypeSelect = mediaTypeSelect;
	}

	public String getAddressBlock() {
		return addressBlock;
	}

	public void setAddressBlock(String addressBlock) {
		this.addressBlock = addressBlock;
	}

	public EmailAddress getFromEmailAddress() {
		return fromEmailAddress;
	}

	public void setFromEmailAddress(EmailAddress fromEmailAddress) {
		this.fromEmailAddress = fromEmailAddress;
	}

	public Set<EmailAddress> getReplyToEmailAddressSet() {
		return replyToEmailAddressSet;
	}

	public void setReplyToEmailAddressSet(Set<EmailAddress> replyToEmailAddressSet) {
		this.replyToEmailAddressSet = replyToEmailAddressSet;
	}

	/**
	 * Add the given {@link EmailAddress} item to the {@code replyToEmailAddressSet}.
	 *
	 * @param item
	 *            the item to add
	 */
	public void addReplyToEmailAddressSetItem(EmailAddress item) {
		if (getReplyToEmailAddressSet() == null) {
			setReplyToEmailAddressSet(new HashSet<>());
		}
		getReplyToEmailAddressSet().add(item);
	}

	/**
	 * Remove the given {@link EmailAddress} item from the {@code replyToEmailAddressSet}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeReplyToEmailAddressSetItem(EmailAddress item) {
		if (getReplyToEmailAddressSet() == null) {
			return;
		}
		getReplyToEmailAddressSet().remove(item);
	}

	/**
	 * Clear the {@code replyToEmailAddressSet} collection.
	 *
	 */
	public void clearReplyToEmailAddressSet() {
		if (getReplyToEmailAddressSet() != null) {
			getReplyToEmailAddressSet().clear();
		}
	}

	public Set<EmailAddress> getToEmailAddressSet() {
		return toEmailAddressSet;
	}

	public void setToEmailAddressSet(Set<EmailAddress> toEmailAddressSet) {
		this.toEmailAddressSet = toEmailAddressSet;
	}

	/**
	 * Add the given {@link EmailAddress} item to the {@code toEmailAddressSet}.
	 *
	 * @param item
	 *            the item to add
	 */
	public void addToEmailAddressSetItem(EmailAddress item) {
		if (getToEmailAddressSet() == null) {
			setToEmailAddressSet(new HashSet<>());
		}
		getToEmailAddressSet().add(item);
	}

	/**
	 * Remove the given {@link EmailAddress} item from the {@code toEmailAddressSet}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeToEmailAddressSetItem(EmailAddress item) {
		if (getToEmailAddressSet() == null) {
			return;
		}
		getToEmailAddressSet().remove(item);
	}

	/**
	 * Clear the {@code toEmailAddressSet} collection.
	 *
	 */
	public void clearToEmailAddressSet() {
		if (getToEmailAddressSet() != null) {
			getToEmailAddressSet().clear();
		}
	}

	public Set<EmailAddress> getCcEmailAddressSet() {
		return ccEmailAddressSet;
	}

	public void setCcEmailAddressSet(Set<EmailAddress> ccEmailAddressSet) {
		this.ccEmailAddressSet = ccEmailAddressSet;
	}

	/**
	 * Add the given {@link EmailAddress} item to the {@code ccEmailAddressSet}.
	 *
	 * @param item
	 *            the item to add
	 */
	public void addCcEmailAddressSetItem(EmailAddress item) {
		if (getCcEmailAddressSet() == null) {
			setCcEmailAddressSet(new HashSet<>());
		}
		getCcEmailAddressSet().add(item);
	}

	/**
	 * Remove the given {@link EmailAddress} item from the {@code ccEmailAddressSet}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeCcEmailAddressSetItem(EmailAddress item) {
		if (getCcEmailAddressSet() == null) {
			return;
		}
		getCcEmailAddressSet().remove(item);
	}

	/**
	 * Clear the {@code ccEmailAddressSet} collection.
	 *
	 */
	public void clearCcEmailAddressSet() {
		if (getCcEmailAddressSet() != null) {
			getCcEmailAddressSet().clear();
		}
	}

	public Set<EmailAddress> getBccEmailAddressSet() {
		return bccEmailAddressSet;
	}

	public void setBccEmailAddressSet(Set<EmailAddress> bccEmailAddressSet) {
		this.bccEmailAddressSet = bccEmailAddressSet;
	}

	/**
	 * Add the given {@link EmailAddress} item to the {@code bccEmailAddressSet}.
	 *
	 * @param item
	 *            the item to add
	 */
	public void addBccEmailAddressSetItem(EmailAddress item) {
		if (getBccEmailAddressSet() == null) {
			setBccEmailAddressSet(new HashSet<>());
		}
		getBccEmailAddressSet().add(item);
	}

	/**
	 * Remove the given {@link EmailAddress} item from the {@code bccEmailAddressSet}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeBccEmailAddressSetItem(EmailAddress item) {
		if (getBccEmailAddressSet() == null) {
			return;
		}
		getBccEmailAddressSet().remove(item);
	}

	/**
	 * Clear the {@code bccEmailAddressSet} collection.
	 *
	 */
	public void clearBccEmailAddressSet() {
		if (getBccEmailAddressSet() != null) {
			getBccEmailAddressSet().clear();
		}
	}

	public Boolean getSentByEmail() {
		return sentByEmail == null ? Boolean.FALSE : sentByEmail;
	}

	public void setSentByEmail(Boolean sentByEmail) {
		this.sentByEmail = sentByEmail;
	}

	public EmailAccount getMailAccount() {
		return mailAccount;
	}

	public void setMailAccount(EmailAccount mailAccount) {
		this.mailAccount = mailAccount;
	}

	public User getSenderUser() {
		return senderUser;
	}

	public void setSenderUser(User senderUser) {
		this.senderUser = senderUser;
	}

	public User getRecipientUser() {
		return recipientUser;
	}

	public void setRecipientUser(User recipientUser) {
		this.recipientUser = recipientUser;
	}

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	public String getEmailThreadId() {
		return emailThreadId;
	}

	public void setEmailThreadId(String emailThreadId) {
		this.emailThreadId = emailThreadId;
	}

	public String getToMobilePhone() {
		return toMobilePhone;
	}

	public void setToMobilePhone(String toMobilePhone) {
		this.toMobilePhone = toMobilePhone;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public String getOffice365Id() {
		return office365Id;
	}

	public void setOffice365Id(String office365Id) {
		this.office365Id = office365Id;
	}

	public OfficeAccount getOfficeAccount() {
		return officeAccount;
	}

	public void setOfficeAccount(OfficeAccount officeAccount) {
		this.officeAccount = officeAccount;
	}

	public LocalDateTime getReceivedDateT() {
		return receivedDateT;
	}

	public void setReceivedDateT(LocalDateTime receivedDateT) {
		this.receivedDateT = receivedDateT;
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
		if (!(obj instanceof Message)) return false;

		final Message other = (Message) obj;
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
			.add("typeSelect", getTypeSelect())
			.add("subject", getSubject())
			.add("sentDateT", getSentDateT())
			.add("sendScheduleDate", getSendScheduleDate())
			.add("relatedTo1Select", getRelatedTo1Select())
			.add("relatedTo1SelectId", getRelatedTo1SelectId())
			.add("relatedTo2Select", getRelatedTo2Select())
			.add("relatedTo2SelectId", getRelatedTo2SelectId())
			.add("statusSelect", getStatusSelect())
			.add("mediaTypeSelect", getMediaTypeSelect())
			.add("sentByEmail", getSentByEmail())
			.omitNullValues()
			.toString();
	}
}
