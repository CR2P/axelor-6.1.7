package com.axelor.apps.message.db;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import com.axelor.apps.base.db.BirtTemplate;
import com.axelor.apps.base.db.Language;
import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.NameColumn;
import com.axelor.db.annotations.Widget;
import com.axelor.meta.db.MetaJsonModel;
import com.axelor.meta.db.MetaModel;
import com.google.common.base.MoreObjects;

@Entity
@Table(name = "MESSAGE_TEMPLATE", indexes = { @Index(columnList = "name"), @Index(columnList = "meta_model"), @Index(columnList = "meta_json_model"), @Index(columnList = "language") })
public class Template extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MESSAGE_TEMPLATE_SEQ")
	@SequenceGenerator(name = "MESSAGE_TEMPLATE_SEQ", sequenceName = "MESSAGE_TEMPLATE_SEQ", allocationSize = 1)
	private Long id;

	@Widget(title = "Name")
	@NameColumn
	@NotNull
	private String name;

	@Widget(title = "Content", multiline = true)
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String content;

	@Widget(title = "Subject", multiline = true)
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String subject;

	@Widget(title = "Reply to", multiline = true)
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String replyToRecipients;

	@Widget(title = "To", multiline = true)
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String toRecipients;

	@Widget(title = "Cc", multiline = true)
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String ccRecipients;

	@Widget(title = "Bcc", multiline = true)
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String bccRecipients;

	@Widget(title = "Target receptor")
	private String target;

	@Widget(title = "Media Type", selection = "template.media.type.select")
	@NotNull
	private Integer mediaTypeSelect = 2;

	@Widget(title = "Address Block", multiline = true)
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String addressBlock;

	@Widget(title = "Mobile phone")
	private String toMobilePhone;

	@Widget(title = "Model")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private MetaModel metaModel;

	@Widget(title = "Default")
	private Boolean isDefault = Boolean.FALSE;

	@Widget(title = "System")
	private Boolean isSystem = Boolean.FALSE;

	@Widget(title = "Json")
	private Boolean isJson = Boolean.FALSE;

	@Widget(title = "Model")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private MetaJsonModel metaJsonModel;

	@Widget(title = "Context")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "template", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TemplateContext> templateContextList;

	@Widget(title = "Signature")
	private String signature;

	@Widget(title = "Add Signature")
	private Boolean addSignature = Boolean.FALSE;

	@Widget(title = "Template engine", selection = "template.engine.select")
	private Integer templateEngineSelect = 1;

	@Widget(title = "Birt Template")
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<BirtTemplate> birtTemplateSet;

	@Widget(title = "Language")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Language language;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public Template() {
	}

	public Template(String name) {
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getReplyToRecipients() {
		return replyToRecipients;
	}

	public void setReplyToRecipients(String replyToRecipients) {
		this.replyToRecipients = replyToRecipients;
	}

	public String getToRecipients() {
		return toRecipients;
	}

	public void setToRecipients(String toRecipients) {
		this.toRecipients = toRecipients;
	}

	public String getCcRecipients() {
		return ccRecipients;
	}

	public void setCcRecipients(String ccRecipients) {
		this.ccRecipients = ccRecipients;
	}

	public String getBccRecipients() {
		return bccRecipients;
	}

	public void setBccRecipients(String bccRecipients) {
		this.bccRecipients = bccRecipients;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
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

	public String getToMobilePhone() {
		return toMobilePhone;
	}

	public void setToMobilePhone(String toMobilePhone) {
		this.toMobilePhone = toMobilePhone;
	}

	public MetaModel getMetaModel() {
		return metaModel;
	}

	public void setMetaModel(MetaModel metaModel) {
		this.metaModel = metaModel;
	}

	public Boolean getIsDefault() {
		return isDefault == null ? Boolean.FALSE : isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	public Boolean getIsSystem() {
		return isSystem == null ? Boolean.FALSE : isSystem;
	}

	public void setIsSystem(Boolean isSystem) {
		this.isSystem = isSystem;
	}

	public Boolean getIsJson() {
		return isJson == null ? Boolean.FALSE : isJson;
	}

	public void setIsJson(Boolean isJson) {
		this.isJson = isJson;
	}

	public MetaJsonModel getMetaJsonModel() {
		return metaJsonModel;
	}

	public void setMetaJsonModel(MetaJsonModel metaJsonModel) {
		this.metaJsonModel = metaJsonModel;
	}

	public List<TemplateContext> getTemplateContextList() {
		return templateContextList;
	}

	public void setTemplateContextList(List<TemplateContext> templateContextList) {
		this.templateContextList = templateContextList;
	}

	/**
	 * Add the given {@link TemplateContext} item to the {@code templateContextList}.
	 *
	 * <p>
	 * It sets {@code item.template = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addTemplateContextListItem(TemplateContext item) {
		if (getTemplateContextList() == null) {
			setTemplateContextList(new ArrayList<>());
		}
		getTemplateContextList().add(item);
		item.setTemplate(this);
	}

	/**
	 * Remove the given {@link TemplateContext} item from the {@code templateContextList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeTemplateContextListItem(TemplateContext item) {
		if (getTemplateContextList() == null) {
			return;
		}
		getTemplateContextList().remove(item);
	}

	/**
	 * Clear the {@code templateContextList} collection.
	 *
	 * <p>
	 * If you have to query {@link TemplateContext} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearTemplateContextList() {
		if (getTemplateContextList() != null) {
			getTemplateContextList().clear();
		}
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public Boolean getAddSignature() {
		return addSignature == null ? Boolean.FALSE : addSignature;
	}

	public void setAddSignature(Boolean addSignature) {
		this.addSignature = addSignature;
	}

	public Integer getTemplateEngineSelect() {
		return templateEngineSelect == null ? 0 : templateEngineSelect;
	}

	public void setTemplateEngineSelect(Integer templateEngineSelect) {
		this.templateEngineSelect = templateEngineSelect;
	}

	public Set<BirtTemplate> getBirtTemplateSet() {
		return birtTemplateSet;
	}

	public void setBirtTemplateSet(Set<BirtTemplate> birtTemplateSet) {
		this.birtTemplateSet = birtTemplateSet;
	}

	/**
	 * Add the given {@link BirtTemplate} item to the {@code birtTemplateSet}.
	 *
	 * @param item
	 *            the item to add
	 */
	public void addBirtTemplateSetItem(BirtTemplate item) {
		if (getBirtTemplateSet() == null) {
			setBirtTemplateSet(new HashSet<>());
		}
		getBirtTemplateSet().add(item);
	}

	/**
	 * Remove the given {@link BirtTemplate} item from the {@code birtTemplateSet}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeBirtTemplateSetItem(BirtTemplate item) {
		if (getBirtTemplateSet() == null) {
			return;
		}
		getBirtTemplateSet().remove(item);
	}

	/**
	 * Clear the {@code birtTemplateSet} collection.
	 *
	 */
	public void clearBirtTemplateSet() {
		if (getBirtTemplateSet() != null) {
			getBirtTemplateSet().clear();
		}
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
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
		if (!(obj instanceof Template)) return false;

		final Template other = (Template) obj;
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
			.add("target", getTarget())
			.add("mediaTypeSelect", getMediaTypeSelect())
			.add("toMobilePhone", getToMobilePhone())
			.add("isDefault", getIsDefault())
			.add("isSystem", getIsSystem())
			.add("isJson", getIsJson())
			.add("signature", getSignature())
			.add("addSignature", getAddSignature())
			.add("templateEngineSelect", getTemplateEngineSelect())
			.omitNullValues()
			.toString();
	}
}
