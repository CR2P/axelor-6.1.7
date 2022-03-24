package com.axelor.apps.base.db;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Cacheable;
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

import org.hibernate.annotations.Type;

import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@Cacheable
@Table(name = "BASE_APP_ROSSUM", indexes = { @Index(columnList = "app") })
public class AppRossum extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BASE_APP_ROSSUM_SEQ")
	@SequenceGenerator(name = "BASE_APP_ROSSUM_SEQ", sequenceName = "BASE_APP_ROSSUM_SEQ", allocationSize = 1)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private App app;

	@Widget(title = "User name")
	private String username;

	@Widget(title = "Password")
	private String password;

	@Widget(title = "Token", help = "It lasts upto 162 hours unless user logouts")
	private String token;

	@Widget(title = "Token created on")
	private LocalDateTime tokenDateTime;

	@Widget(title = "Valid connection")
	private Boolean isValid = Boolean.FALSE;

	@Widget(title = "Consolidate invoice lines")
	private Boolean isInvoiceLineConsolidated = Boolean.FALSE;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public AppRossum() {
	}

	public AppRossum(String username) {
		this.username = username;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public App getApp() {
		return app;
	}

	public void setApp(App app) {
		this.app = app;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * It lasts upto 162 hours unless user logouts
	 *
	 * @return the property value
	 */
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LocalDateTime getTokenDateTime() {
		return tokenDateTime;
	}

	public void setTokenDateTime(LocalDateTime tokenDateTime) {
		this.tokenDateTime = tokenDateTime;
	}

	public Boolean getIsValid() {
		return isValid == null ? Boolean.FALSE : isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	public Boolean getIsInvoiceLineConsolidated() {
		return isInvoiceLineConsolidated == null ? Boolean.FALSE : isInvoiceLineConsolidated;
	}

	public void setIsInvoiceLineConsolidated(Boolean isInvoiceLineConsolidated) {
		this.isInvoiceLineConsolidated = isInvoiceLineConsolidated;
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
		if (!(obj instanceof AppRossum)) return false;

		final AppRossum other = (AppRossum) obj;
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
			.add("username", getUsername())
			.add("password", getPassword())
			.add("token", getToken())
			.add("tokenDateTime", getTokenDateTime())
			.add("isValid", getIsValid())
			.add("isInvoiceLineConsolidated", getIsInvoiceLineConsolidated())
			.omitNullValues()
			.toString();
	}
}
