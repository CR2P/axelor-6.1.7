package com.axelor.apps.message.db;

import java.util.Objects;

import javax.persistence.Basic;
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
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import com.axelor.auth.db.AuditableModel;
import com.axelor.auth.db.User;
import com.axelor.db.annotations.NameColumn;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@Table(name = "MESSAGE_EMAIL_ACCOUNT", indexes = { @Index(columnList = "name"), @Index(columnList = "user_id") })
public class EmailAccount extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MESSAGE_EMAIL_ACCOUNT_SEQ")
	@SequenceGenerator(name = "MESSAGE_EMAIL_ACCOUNT_SEQ", sequenceName = "MESSAGE_EMAIL_ACCOUNT_SEQ", allocationSize = 1)
	private Long id;

	@Widget(title = "Name")
	@NameColumn
	@NotNull
	private String name;

	@Widget(title = "Server Type", selection = "mail.account.server.type.select")
	@NotNull
	private Integer serverTypeSelect = 1;

	@Widget(title = "Login")
	private String login;

	@Widget(title = "Password")
	private String password;

	@Widget(title = "Host")
	@NotNull
	private String host;

	@Widget(title = "Port")
	@Min(1)
	@Column(nullable = true)
	private Integer port;

	@Widget(title = "SSL/STARTTLS", selection = "mail.account.security.select")
	private Integer securitySelect = 0;

	@Widget(title = "Default account")
	private Boolean isDefault = Boolean.FALSE;

	@Widget(title = "Valid")
	private Boolean isValid = Boolean.FALSE;

	@Widget(title = "Signature")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String signature;

	@Widget(title = "From email name")
	private String fromName;

	@Widget(title = "From email address")
	private String fromAddress;

	@Widget(title = "SendinBlue API key")
	private String sendingblueApiKey;

	@Widget(title = "User")
	@JoinColumn(name = "user_id")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private User user;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public EmailAccount() {
	}

	public EmailAccount(String name) {
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

	public Integer getServerTypeSelect() {
		return serverTypeSelect == null ? 0 : serverTypeSelect;
	}

	public void setServerTypeSelect(Integer serverTypeSelect) {
		this.serverTypeSelect = serverTypeSelect;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public Integer getSecuritySelect() {
		return securitySelect == null ? 0 : securitySelect;
	}

	public void setSecuritySelect(Integer securitySelect) {
		this.securitySelect = securitySelect;
	}

	public Boolean getIsDefault() {
		return isDefault == null ? Boolean.FALSE : isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	public Boolean getIsValid() {
		return isValid == null ? Boolean.FALSE : isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public String getSendingblueApiKey() {
		return sendingblueApiKey;
	}

	public void setSendingblueApiKey(String sendingblueApiKey) {
		this.sendingblueApiKey = sendingblueApiKey;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
		if (!(obj instanceof EmailAccount)) return false;

		final EmailAccount other = (EmailAccount) obj;
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
			.add("serverTypeSelect", getServerTypeSelect())
			.add("login", getLogin())
			.add("password", getPassword())
			.add("host", getHost())
			.add("port", getPort())
			.add("securitySelect", getSecuritySelect())
			.add("isDefault", getIsDefault())
			.add("isValid", getIsValid())
			.add("fromName", getFromName())
			.add("fromAddress", getFromAddress())
			.omitNullValues()
			.toString();
	}
}
