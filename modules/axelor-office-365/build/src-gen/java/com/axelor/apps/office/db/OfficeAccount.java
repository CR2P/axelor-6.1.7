package com.axelor.apps.office.db;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.axelor.auth.db.AuditableModel;
import com.axelor.auth.db.User;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@Table(name = "OFFICE_OFFICE_ACCOUNT", indexes = { @Index(columnList = "name"), @Index(columnList = "owner_user") })
public class OfficeAccount extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OFFICE_OFFICE_ACCOUNT_SEQ")
	@SequenceGenerator(name = "OFFICE_OFFICE_ACCOUNT_SEQ", sequenceName = "OFFICE_OFFICE_ACCOUNT_SEQ", allocationSize = 1)
	private Long id;

	@Widget(title = "Name")
	private String name;

	@Widget(readonly = true)
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String refreshToken;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String authenticationUrl;

	@Widget(title = "Authorized")
	private Boolean isAuthorized = Boolean.FALSE;

	@Widget(title = "User")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private User ownerUser;

	@Widget(title = "Contact sync")
	private LocalDateTime lastContactSyncOn;

	@Widget(title = "Calendar sync")
	private LocalDateTime lastCalendarSyncOn;

	@Widget(title = "Mail sync")
	private LocalDateTime lastMailSyncOn;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public OfficeAccount() {
	}

	public OfficeAccount(String name) {
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

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getAuthenticationUrl() {
		return authenticationUrl;
	}

	public void setAuthenticationUrl(String authenticationUrl) {
		this.authenticationUrl = authenticationUrl;
	}

	public Boolean getIsAuthorized() {
		return isAuthorized == null ? Boolean.FALSE : isAuthorized;
	}

	public void setIsAuthorized(Boolean isAuthorized) {
		this.isAuthorized = isAuthorized;
	}

	public User getOwnerUser() {
		return ownerUser;
	}

	public void setOwnerUser(User ownerUser) {
		this.ownerUser = ownerUser;
	}

	public LocalDateTime getLastContactSyncOn() {
		return lastContactSyncOn;
	}

	public void setLastContactSyncOn(LocalDateTime lastContactSyncOn) {
		this.lastContactSyncOn = lastContactSyncOn;
	}

	public LocalDateTime getLastCalendarSyncOn() {
		return lastCalendarSyncOn;
	}

	public void setLastCalendarSyncOn(LocalDateTime lastCalendarSyncOn) {
		this.lastCalendarSyncOn = lastCalendarSyncOn;
	}

	public LocalDateTime getLastMailSyncOn() {
		return lastMailSyncOn;
	}

	public void setLastMailSyncOn(LocalDateTime lastMailSyncOn) {
		this.lastMailSyncOn = lastMailSyncOn;
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
		if (!(obj instanceof OfficeAccount)) return false;

		final OfficeAccount other = (OfficeAccount) obj;
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
			.add("isAuthorized", getIsAuthorized())
			.add("lastContactSyncOn", getLastContactSyncOn())
			.add("lastCalendarSyncOn", getLastCalendarSyncOn())
			.add("lastMailSyncOn", getLastMailSyncOn())
			.omitNullValues()
			.toString();
	}
}
