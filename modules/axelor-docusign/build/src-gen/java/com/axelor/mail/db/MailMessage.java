package com.axelor.mail.db;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.axelor.auth.db.AuditableModel;
import com.axelor.auth.db.User;
import com.axelor.db.annotations.EqualsInclude;
import com.axelor.db.annotations.NameColumn;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

/**
 * The model to store different kind of messages like system notifications,
 * comments, email messages etc.
 */
@Entity
@Cacheable
@Table(name = "MAIL_MESSAGE", indexes = { @Index(columnList = "author"), @Index(columnList = "email_from"), @Index(columnList = "subject"), @Index(columnList = "root"), @Index(columnList = "parent"), @Index(columnList = "relatedModel,relatedId") })
public class MailMessage extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MAIL_MESSAGE_SEQ")
	@SequenceGenerator(name = "MAIL_MESSAGE_SEQ", sequenceName = "MAIL_MESSAGE_SEQ", allocationSize = 1)
	private Long id;

	@Widget(selection = "message.type.selection")
	private String type;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private User author;

	@JoinColumn(name = "email_from")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private MailAddress from;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<MailAddress> recipients;

	@NameColumn
	private String subject;

	@Widget(multiline = true)
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String body;

	private String summary;

	@EqualsInclude
	@Widget(help = "Unique message identifier", readonly = true)
	@Column(unique = true)
	private String messageId;

	@Widget(title = "Related document id")
	private Long relatedId = 0L;

	@Widget(title = "Related document model")
	private String relatedModel;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private MailMessage root;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private MailMessage parent;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<MailMessage> replies;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "message", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<MailFlags> flags;

	@Widget(title = "Related document name")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String relatedName;

	public MailMessage() {
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public MailAddress getFrom() {
		return from;
	}

	public void setFrom(MailAddress from) {
		this.from = from;
	}

	public Set<MailAddress> getRecipients() {
		return recipients;
	}

	public void setRecipients(Set<MailAddress> recipients) {
		this.recipients = recipients;
	}

	/**
	 * Add the given {@link MailAddress} item to the {@code recipients}.
	 *
	 * @param item
	 *            the item to add
	 */
	public void addRecipient(MailAddress item) {
		if (getRecipients() == null) {
			setRecipients(new HashSet<>());
		}
		getRecipients().add(item);
	}

	/**
	 * Remove the given {@link MailAddress} item from the {@code recipients}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeRecipient(MailAddress item) {
		if (getRecipients() == null) {
			return;
		}
		getRecipients().remove(item);
	}

	/**
	 * Clear the {@code recipients} collection.
	 *
	 */
	public void clearRecipients() {
		if (getRecipients() != null) {
			getRecipients().clear();
		}
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	/**
	 * Unique message identifier
	 *
	 * @return the property value
	 */
	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public Long getRelatedId() {
		return relatedId == null ? 0L : relatedId;
	}

	public void setRelatedId(Long relatedId) {
		this.relatedId = relatedId;
	}

	public String getRelatedModel() {
		return relatedModel;
	}

	public void setRelatedModel(String relatedModel) {
		this.relatedModel = relatedModel;
	}

	public MailMessage getRoot() {
		return root;
	}

	public void setRoot(MailMessage root) {
		this.root = root;
	}

	public MailMessage getParent() {
		return parent;
	}

	public void setParent(MailMessage parent) {
		this.parent = parent;
	}

	public List<MailMessage> getReplies() {
		return replies;
	}

	public void setReplies(List<MailMessage> replies) {
		this.replies = replies;
	}

	/**
	 * Add the given {@link MailMessage} item to the {@code replies}.
	 *
	 * <p>
	 * It sets {@code item.parent = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addReply(MailMessage item) {
		if (getReplies() == null) {
			setReplies(new ArrayList<>());
		}
		getReplies().add(item);
		item.setParent(this);
	}

	/**
	 * Remove the given {@link MailMessage} item from the {@code replies}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeReply(MailMessage item) {
		if (getReplies() == null) {
			return;
		}
		getReplies().remove(item);
	}

	/**
	 * Clear the {@code replies} collection.
	 *
	 * <p>
	 * If you have to query {@link MailMessage} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearReplies() {
		if (getReplies() != null) {
			getReplies().clear();
		}
	}

	public List<MailFlags> getFlags() {
		return flags;
	}

	public void setFlags(List<MailFlags> flags) {
		this.flags = flags;
	}

	/**
	 * Add the given {@link MailFlags} item to the {@code flags}.
	 *
	 * <p>
	 * It sets {@code item.message = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addFlag(MailFlags item) {
		if (getFlags() == null) {
			setFlags(new ArrayList<>());
		}
		getFlags().add(item);
		item.setMessage(this);
	}

	/**
	 * Remove the given {@link MailFlags} item from the {@code flags}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeFlag(MailFlags item) {
		if (getFlags() == null) {
			return;
		}
		getFlags().remove(item);
	}

	/**
	 * Clear the {@code flags} collection.
	 *
	 * <p>
	 * If you have to query {@link MailFlags} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearFlags() {
		if (getFlags() != null) {
			getFlags().clear();
		}
	}

	public String getRelatedName() {
		return relatedName;
	}

	public void setRelatedName(String relatedName) {
		this.relatedName = relatedName;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (this == obj) return true;
		if (!(obj instanceof MailMessage)) return false;

		final MailMessage other = (MailMessage) obj;
		if (this.getId() != null || other.getId() != null) {
			return Objects.equals(this.getId(), other.getId());
		}

		return Objects.equals(getMessageId(), other.getMessageId())
			&& (getMessageId() != null);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("id", getId())
			.add("type", getType())
			.add("subject", getSubject())
			.add("summary", getSummary())
			.add("messageId", getMessageId())
			.add("relatedId", getRelatedId())
			.add("relatedModel", getRelatedModel())
			.omitNullValues()
			.toString();
	}
}
