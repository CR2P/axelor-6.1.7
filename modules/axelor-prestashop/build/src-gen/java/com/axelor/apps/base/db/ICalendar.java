package com.axelor.apps.base.db;

import java.time.LocalDateTime;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import com.axelor.auth.db.AuditableModel;
import com.axelor.auth.db.User;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@Table(name = "ICAL_CALENDAR", indexes = { @Index(columnList = "name"), @Index(columnList = "user_id") })
public class ICalendar extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ICAL_CALENDAR_SEQ")
	@SequenceGenerator(name = "ICAL_CALENDAR_SEQ", sequenceName = "ICAL_CALENDAR_SEQ", allocationSize = 1)
	private Long id;

	@Widget(title = "Name")
	@NotNull
	private String name;

	@Widget(title = "CID")
	private String cid;

	@Widget(title = "Keep remote")
	private Boolean keepRemote = Boolean.FALSE;

	@Widget(title = "User")
	@JoinColumn(name = "user_id")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private User user;

	@Widget(title = "Url")
	private String url;

	@Widget(title = "Login")
	private String login;

	@Widget(title = "Password")
	private String password;

	@Widget(title = "Type", selection = "icrm.calendar.type.select")
	private Integer typeSelect = 0;

	@Widget(title = "Advanced synchronization", selection = "icalendar.synchronization.select")
	private String synchronizationSelect;

	@Widget(title = "SSL")
	private Boolean isSslConnection = Boolean.FALSE;

	@Widget(title = "Port")
	private Integer port = 80;

	@Widget(title = "Valid")
	private Boolean isValid = Boolean.FALSE;

	@Widget(title = "Last Synchronization")
	private LocalDateTime lastSynchronizationDateT;

	@Widget(title = "Synchronization duration (week)")
	@Min(1)
	private Integer synchronizationDuration = 1;

	@Widget(title = "Sharing settings")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "calendar", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SharingSetting> sharingSettingList;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public ICalendar() {
	}

	public ICalendar(String name) {
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

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public Boolean getKeepRemote() {
		return keepRemote == null ? Boolean.FALSE : keepRemote;
	}

	public void setKeepRemote(Boolean keepRemote) {
		this.keepRemote = keepRemote;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public Integer getTypeSelect() {
		return typeSelect == null ? 0 : typeSelect;
	}

	public void setTypeSelect(Integer typeSelect) {
		this.typeSelect = typeSelect;
	}

	public String getSynchronizationSelect() {
		return synchronizationSelect;
	}

	public void setSynchronizationSelect(String synchronizationSelect) {
		this.synchronizationSelect = synchronizationSelect;
	}

	public Boolean getIsSslConnection() {
		return isSslConnection == null ? Boolean.FALSE : isSslConnection;
	}

	public void setIsSslConnection(Boolean isSslConnection) {
		this.isSslConnection = isSslConnection;
	}

	public Integer getPort() {
		return port == null ? 0 : port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public Boolean getIsValid() {
		return isValid == null ? Boolean.FALSE : isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	public LocalDateTime getLastSynchronizationDateT() {
		return lastSynchronizationDateT;
	}

	public void setLastSynchronizationDateT(LocalDateTime lastSynchronizationDateT) {
		this.lastSynchronizationDateT = lastSynchronizationDateT;
	}

	public Integer getSynchronizationDuration() {
		return synchronizationDuration == null ? 0 : synchronizationDuration;
	}

	public void setSynchronizationDuration(Integer synchronizationDuration) {
		this.synchronizationDuration = synchronizationDuration;
	}

	public List<SharingSetting> getSharingSettingList() {
		return sharingSettingList;
	}

	public void setSharingSettingList(List<SharingSetting> sharingSettingList) {
		this.sharingSettingList = sharingSettingList;
	}

	/**
	 * Add the given {@link SharingSetting} item to the {@code sharingSettingList}.
	 *
	 * <p>
	 * It sets {@code item.calendar = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addSharingSettingListItem(SharingSetting item) {
		if (getSharingSettingList() == null) {
			setSharingSettingList(new ArrayList<>());
		}
		getSharingSettingList().add(item);
		item.setCalendar(this);
	}

	/**
	 * Remove the given {@link SharingSetting} item from the {@code sharingSettingList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeSharingSettingListItem(SharingSetting item) {
		if (getSharingSettingList() == null) {
			return;
		}
		getSharingSettingList().remove(item);
	}

	/**
	 * Clear the {@code sharingSettingList} collection.
	 *
	 * <p>
	 * If you have to query {@link SharingSetting} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearSharingSettingList() {
		if (getSharingSettingList() != null) {
			getSharingSettingList().clear();
		}
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
		if (!(obj instanceof ICalendar)) return false;

		final ICalendar other = (ICalendar) obj;
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
			.add("cid", getCid())
			.add("keepRemote", getKeepRemote())
			.add("url", getUrl())
			.add("login", getLogin())
			.add("password", getPassword())
			.add("typeSelect", getTypeSelect())
			.add("synchronizationSelect", getSynchronizationSelect())
			.add("isSslConnection", getIsSslConnection())
			.add("port", getPort())
			.add("isValid", getIsValid())
			.omitNullValues()
			.toString();
	}
}
