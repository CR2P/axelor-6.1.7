package com.axelor.apps.base.db;

import java.util.Objects;

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

import org.hibernate.annotations.Type;

import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@Table(name = "BASE_APP_OFFICE365", indexes = { @Index(columnList = "app") })
public class AppOffice365 extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BASE_APP_OFFICE365_SEQ")
	@SequenceGenerator(name = "BASE_APP_OFFICE365_SEQ", sequenceName = "BASE_APP_OFFICE365_SEQ", allocationSize = 1)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private App app;

	@Widget(title = "Application (client) ID")
	private String clientId;

	@Widget(title = "Redirect URI")
	private String redirectUri;

	@Widget(title = "Application (client) secret")
	private String clientSecret;

	private Boolean manageMessageRelatedTo = Boolean.FALSE;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public AppOffice365() {
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

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getRedirectUri() {
		return redirectUri;
	}

	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public Boolean getManageMessageRelatedTo() {
		return manageMessageRelatedTo == null ? Boolean.FALSE : manageMessageRelatedTo;
	}

	public void setManageMessageRelatedTo(Boolean manageMessageRelatedTo) {
		this.manageMessageRelatedTo = manageMessageRelatedTo;
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
		if (!(obj instanceof AppOffice365)) return false;

		final AppOffice365 other = (AppOffice365) obj;
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
			.add("clientId", getClientId())
			.add("redirectUri", getRedirectUri())
			.add("clientSecret", getClientSecret())
			.add("manageMessageRelatedTo", getManageMessageRelatedTo())
			.omitNullValues()
			.toString();
	}
}
