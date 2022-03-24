package com.axelor.auth.db;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.axelor.apps.base.db.CalendarManagement;
import com.axelor.apps.base.db.Company;
import com.axelor.apps.base.db.ICalendar;
import com.axelor.apps.base.db.Partner;
import com.axelor.apps.base.db.TradingName;
import com.axelor.apps.message.db.EmailAccount;
import com.axelor.db.annotations.EqualsInclude;
import com.axelor.db.annotations.NameColumn;
import com.axelor.db.annotations.Track;
import com.axelor.db.annotations.TrackField;
import com.axelor.db.annotations.VirtualColumn;
import com.axelor.db.annotations.Widget;
import com.axelor.meta.db.MetaFile;
import com.axelor.meta.db.MetaModel;
import com.axelor.meta.db.MetaPermission;
import com.axelor.team.db.Team;
import com.google.common.base.MoreObjects;

/**
 * This object stores the users.
 */
@Entity
@Cacheable
@Table(name = "AUTH_USER", indexes = { @Index(columnList = "name"), @Index(columnList = "group_id"), @Index(columnList = "active_company"), @Index(columnList = "partner"), @Index(columnList = "active_team"), @Index(columnList = "fullName"), @Index(columnList = "i_calendar"), @Index(columnList = "electronic_signature"), @Index(columnList = "group_id"), @Index(columnList = "trading_name") })
@Track(fields = { @TrackField(name = "name"), @TrackField(name = "code"), @TrackField(name = "email"), @TrackField(name = "theme"), @TrackField(name = "activateOn"), @TrackField(name = "expiresOn"), @TrackField(name = "activeCompany"), @TrackField(name = "partner"), @TrackField(name = "today"), @TrackField(name = "activeTeam"), @TrackField(name = "fullName"), @TrackField(name = "iCalendar"), @TrackField(name = "electronicSignature"), @TrackField(name = "group"), @TrackField(name = "language"), @TrackField(name = "singleTab"), @TrackField(name = "noHelp"), @TrackField(name = "blocked"), @TrackField(name = "sendEmailUponPasswordChange"), @TrackField(name = "homeAction"), @TrackField(name = "receiveEmails") })
public class User extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AUTH_USER_SEQ")
	@SequenceGenerator(name = "AUTH_USER_SEQ", sequenceName = "AUTH_USER_SEQ", allocationSize = 1)
	private Long id;

	@EqualsInclude
	@Widget(title = "Login")
	@NotNull
	@Size(min = 2)
	@Column(unique = true)
	private String code;

	@NotNull
	@Size(min = 2)
	private String name;

	@Widget(password = true)
	@NotNull
	@Size(min = 4)
	private String password;

	@Widget(title = "Last password update date")
	private LocalDateTime passwordUpdatedOn;

	@Widget(help = "Force the user to change their password at next login.")
	private Boolean forcePasswordChange = Boolean.FALSE;

	@Widget(image = true, title = "Photo", help = "Max size 4MB.")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] image;

	private String email;

	private String theme;

	@Widget(help = "Activate the user from the specified date.")
	private LocalDateTime activateOn;

	@Widget(help = "Disable the user from the specified date.")
	private LocalDateTime expiresOn;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Role> roles;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Permission> permissions;

	@Widget(title = "Permissions (fields)")
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<MetaPermission> metaPermissions;

	@Widget(title = "Company")
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Company> companySet;

	@Widget(title = "Active company", massUpdate = true)
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Company activeCompany;

	@Widget(title = "Partner")
	@OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Partner partner;

	@Widget(title = "Today date")
	private ZonedDateTime today;

	@Widget(title = "Teams")
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "members", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Team> teamSet;

	@Widget(title = "Active Team", massUpdate = true)
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Team activeTeam;

	@Widget(title = "Partner name", search = { "partner", "name" })
	@NameColumn
	@VirtualColumn
	@Access(AccessType.PROPERTY)
	private String fullName;

	@Widget(title = "Main calendar")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private ICalendar iCalendar;

	@Widget(title = "Followed users")
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<User> followersCalUserSet;

	@Widget(title = "Calendars permissions")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parentUser", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CalendarManagement> calendarManagementList;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private MetaFile electronicSignature;

	@Widget(title = "Use signature for sales quotations")
	private Boolean useSignatureForSalesQuotations = Boolean.FALSE;

	@Widget(title = "Use signature for purchase quotations")
	private Boolean useSignatureForPurchaseQuotations = Boolean.FALSE;

	@Widget(massUpdate = true)
	@JoinColumn(name = "group_id")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Group group;

	@Widget(selection = "select.language", massUpdate = true)
	private String language;

	@Widget(help = "Whether to use tabbed ui.", massUpdate = true)
	private Boolean singleTab = Boolean.FALSE;

	@Widget(help = "Whether to show help messages.", massUpdate = true)
	private Boolean noHelp = Boolean.FALSE;

	@Widget(help = "Specify whether to block the user for an indefinite period.", massUpdate = true)
	private Boolean blocked = Boolean.TRUE;

	private Boolean sendEmailUponPasswordChange = Boolean.FALSE;

	@Widget(massUpdate = true)
	private String homeAction;

	@Widget(title = "Receive notifications by email", help = "Allow notifications to be sent by email")
	private Boolean receiveEmails = Boolean.TRUE;

	@Widget(title = "Entities you wish to follow by email")
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<MetaModel> followedMetaModelSet;

	@Widget(title = "Email accounts")
	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private List<EmailAccount> emailAccountList;

	@Transient
	private String transientPassword;

	@Widget(selection = "base.user.form.step.select")
	private Integer stepStatusSelect = 0;

	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	@Widget(title = "Email signature")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String emailSignature;

	@Widget(title = "Trading name")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private TradingName tradingName;

	public User() {
	}

	public User(String code, String name) {
		this.code = code;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDateTime getPasswordUpdatedOn() {
		return passwordUpdatedOn;
	}

	public void setPasswordUpdatedOn(LocalDateTime passwordUpdatedOn) {
		this.passwordUpdatedOn = passwordUpdatedOn;
	}

	/**
	 * Force the user to change their password at next login.
	 *
	 * @return the property value
	 */
	public Boolean getForcePasswordChange() {
		return forcePasswordChange == null ? Boolean.FALSE : forcePasswordChange;
	}

	public void setForcePasswordChange(Boolean forcePasswordChange) {
		this.forcePasswordChange = forcePasswordChange;
	}

	/**
	 * Max size 4MB.
	 *
	 * @return the property value
	 */
	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	/**
	 * Activate the user from the specified date.
	 *
	 * @return the property value
	 */
	public LocalDateTime getActivateOn() {
		return activateOn;
	}

	public void setActivateOn(LocalDateTime activateOn) {
		this.activateOn = activateOn;
	}

	/**
	 * Disable the user from the specified date.
	 *
	 * @return the property value
	 */
	public LocalDateTime getExpiresOn() {
		return expiresOn;
	}

	public void setExpiresOn(LocalDateTime expiresOn) {
		this.expiresOn = expiresOn;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	/**
	 * Add the given {@link Role} item to the {@code roles}.
	 *
	 * @param item
	 *            the item to add
	 */
	public void addRole(Role item) {
		if (getRoles() == null) {
			setRoles(new HashSet<>());
		}
		getRoles().add(item);
	}

	/**
	 * Remove the given {@link Role} item from the {@code roles}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeRole(Role item) {
		if (getRoles() == null) {
			return;
		}
		getRoles().remove(item);
	}

	/**
	 * Clear the {@code roles} collection.
	 *
	 */
	public void clearRoles() {
		if (getRoles() != null) {
			getRoles().clear();
		}
	}

	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	/**
	 * Add the given {@link Permission} item to the {@code permissions}.
	 *
	 * @param item
	 *            the item to add
	 */
	public void addPermission(Permission item) {
		if (getPermissions() == null) {
			setPermissions(new HashSet<>());
		}
		getPermissions().add(item);
	}

	/**
	 * Remove the given {@link Permission} item from the {@code permissions}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removePermission(Permission item) {
		if (getPermissions() == null) {
			return;
		}
		getPermissions().remove(item);
	}

	/**
	 * Clear the {@code permissions} collection.
	 *
	 */
	public void clearPermissions() {
		if (getPermissions() != null) {
			getPermissions().clear();
		}
	}

	public Set<MetaPermission> getMetaPermissions() {
		return metaPermissions;
	}

	public void setMetaPermissions(Set<MetaPermission> metaPermissions) {
		this.metaPermissions = metaPermissions;
	}

	/**
	 * Add the given {@link MetaPermission} item to the {@code metaPermissions}.
	 *
	 * @param item
	 *            the item to add
	 */
	public void addMetaPermission(MetaPermission item) {
		if (getMetaPermissions() == null) {
			setMetaPermissions(new HashSet<>());
		}
		getMetaPermissions().add(item);
	}

	/**
	 * Remove the given {@link MetaPermission} item from the {@code metaPermissions}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeMetaPermission(MetaPermission item) {
		if (getMetaPermissions() == null) {
			return;
		}
		getMetaPermissions().remove(item);
	}

	/**
	 * Clear the {@code metaPermissions} collection.
	 *
	 */
	public void clearMetaPermissions() {
		if (getMetaPermissions() != null) {
			getMetaPermissions().clear();
		}
	}

	public Set<Company> getCompanySet() {
		return companySet;
	}

	public void setCompanySet(Set<Company> companySet) {
		this.companySet = companySet;
	}

	/**
	 * Add the given {@link Company} item to the {@code companySet}.
	 *
	 * @param item
	 *            the item to add
	 */
	public void addCompanySetItem(Company item) {
		if (getCompanySet() == null) {
			setCompanySet(new HashSet<>());
		}
		getCompanySet().add(item);
	}

	/**
	 * Remove the given {@link Company} item from the {@code companySet}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeCompanySetItem(Company item) {
		if (getCompanySet() == null) {
			return;
		}
		getCompanySet().remove(item);
	}

	/**
	 * Clear the {@code companySet} collection.
	 *
	 */
	public void clearCompanySet() {
		if (getCompanySet() != null) {
			getCompanySet().clear();
		}
	}

	public Company getActiveCompany() {
		return activeCompany;
	}

	public void setActiveCompany(Company activeCompany) {
		this.activeCompany = activeCompany;
	}

	public Partner getPartner() {
		return partner;
	}

	public void setPartner(Partner partner) {
		this.partner = partner;
	}

	public ZonedDateTime getToday() {
		return today;
	}

	public void setToday(ZonedDateTime today) {
		this.today = today;
	}

	public Set<Team> getTeamSet() {
		return teamSet;
	}

	public void setTeamSet(Set<Team> teamSet) {
		this.teamSet = teamSet;
	}

	/**
	 * Add the given {@link Team} item to the {@code teamSet}.
	 *
	 * @param item
	 *            the item to add
	 */
	public void addTeamSetItem(Team item) {
		if (getTeamSet() == null) {
			setTeamSet(new HashSet<>());
		}
		getTeamSet().add(item);
	}

	/**
	 * Remove the given {@link Team} item from the {@code teamSet}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeTeamSetItem(Team item) {
		if (getTeamSet() == null) {
			return;
		}
		getTeamSet().remove(item);
	}

	/**
	 * Clear the {@code teamSet} collection.
	 *
	 */
	public void clearTeamSet() {
		if (getTeamSet() != null) {
			getTeamSet().clear();
		}
	}

	public Team getActiveTeam() {
		return activeTeam;
	}

	public void setActiveTeam(Team activeTeam) {
		this.activeTeam = activeTeam;
	}

	public String getFullName() {
		try {
			fullName = computeFullName();
		} catch (NullPointerException e) {
			Logger logger = LoggerFactory.getLogger(getClass());
			logger.error("NPE in function field: getFullName()", e);
		}
		return fullName;
	}

	protected String computeFullName() {
		if(partner != null) {
			if(partner.getFirstName() != null){
				return partner.getFirstName()+" "+partner.getName();
			}
			return partner.getName();
		}
		return name;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public ICalendar getiCalendar() {
		return iCalendar;
	}

	public void setiCalendar(ICalendar iCalendar) {
		this.iCalendar = iCalendar;
	}

	public Set<User> getFollowersCalUserSet() {
		return followersCalUserSet;
	}

	public void setFollowersCalUserSet(Set<User> followersCalUserSet) {
		this.followersCalUserSet = followersCalUserSet;
	}

	/**
	 * Add the given {@link User} item to the {@code followersCalUserSet}.
	 *
	 * @param item
	 *            the item to add
	 */
	public void addFollowersCalUserSetItem(User item) {
		if (getFollowersCalUserSet() == null) {
			setFollowersCalUserSet(new HashSet<>());
		}
		getFollowersCalUserSet().add(item);
	}

	/**
	 * Remove the given {@link User} item from the {@code followersCalUserSet}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeFollowersCalUserSetItem(User item) {
		if (getFollowersCalUserSet() == null) {
			return;
		}
		getFollowersCalUserSet().remove(item);
	}

	/**
	 * Clear the {@code followersCalUserSet} collection.
	 *
	 */
	public void clearFollowersCalUserSet() {
		if (getFollowersCalUserSet() != null) {
			getFollowersCalUserSet().clear();
		}
	}

	public List<CalendarManagement> getCalendarManagementList() {
		return calendarManagementList;
	}

	public void setCalendarManagementList(List<CalendarManagement> calendarManagementList) {
		this.calendarManagementList = calendarManagementList;
	}

	/**
	 * Add the given {@link CalendarManagement} item to the {@code calendarManagementList}.
	 *
	 * <p>
	 * It sets {@code item.parentUser = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addCalendarManagementListItem(CalendarManagement item) {
		if (getCalendarManagementList() == null) {
			setCalendarManagementList(new ArrayList<>());
		}
		getCalendarManagementList().add(item);
		item.setParentUser(this);
	}

	/**
	 * Remove the given {@link CalendarManagement} item from the {@code calendarManagementList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeCalendarManagementListItem(CalendarManagement item) {
		if (getCalendarManagementList() == null) {
			return;
		}
		getCalendarManagementList().remove(item);
	}

	/**
	 * Clear the {@code calendarManagementList} collection.
	 *
	 * <p>
	 * If you have to query {@link CalendarManagement} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearCalendarManagementList() {
		if (getCalendarManagementList() != null) {
			getCalendarManagementList().clear();
		}
	}

	public MetaFile getElectronicSignature() {
		return electronicSignature;
	}

	public void setElectronicSignature(MetaFile electronicSignature) {
		this.electronicSignature = electronicSignature;
	}

	public Boolean getUseSignatureForSalesQuotations() {
		return useSignatureForSalesQuotations == null ? Boolean.FALSE : useSignatureForSalesQuotations;
	}

	public void setUseSignatureForSalesQuotations(Boolean useSignatureForSalesQuotations) {
		this.useSignatureForSalesQuotations = useSignatureForSalesQuotations;
	}

	public Boolean getUseSignatureForPurchaseQuotations() {
		return useSignatureForPurchaseQuotations == null ? Boolean.FALSE : useSignatureForPurchaseQuotations;
	}

	public void setUseSignatureForPurchaseQuotations(Boolean useSignatureForPurchaseQuotations) {
		this.useSignatureForPurchaseQuotations = useSignatureForPurchaseQuotations;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * Whether to use tabbed ui.
	 *
	 * @return the property value
	 */
	public Boolean getSingleTab() {
		return singleTab == null ? Boolean.FALSE : singleTab;
	}

	public void setSingleTab(Boolean singleTab) {
		this.singleTab = singleTab;
	}

	/**
	 * Whether to show help messages.
	 *
	 * @return the property value
	 */
	public Boolean getNoHelp() {
		return noHelp == null ? Boolean.FALSE : noHelp;
	}

	public void setNoHelp(Boolean noHelp) {
		this.noHelp = noHelp;
	}

	/**
	 * Specify whether to block the user for an indefinite period.
	 *
	 * @return the property value
	 */
	public Boolean getBlocked() {
		return blocked == null ? Boolean.FALSE : blocked;
	}

	public void setBlocked(Boolean blocked) {
		this.blocked = blocked;
	}

	public Boolean getSendEmailUponPasswordChange() {
		return sendEmailUponPasswordChange == null ? Boolean.FALSE : sendEmailUponPasswordChange;
	}

	public void setSendEmailUponPasswordChange(Boolean sendEmailUponPasswordChange) {
		this.sendEmailUponPasswordChange = sendEmailUponPasswordChange;
	}

	public String getHomeAction() {
		return homeAction;
	}

	public void setHomeAction(String homeAction) {
		this.homeAction = homeAction;
	}

	/**
	 * Allow notifications to be sent by email
	 *
	 * @return the property value
	 */
	public Boolean getReceiveEmails() {
		return receiveEmails == null ? Boolean.FALSE : receiveEmails;
	}

	public void setReceiveEmails(Boolean receiveEmails) {
		this.receiveEmails = receiveEmails;
	}

	public Set<MetaModel> getFollowedMetaModelSet() {
		return followedMetaModelSet;
	}

	public void setFollowedMetaModelSet(Set<MetaModel> followedMetaModelSet) {
		this.followedMetaModelSet = followedMetaModelSet;
	}

	/**
	 * Add the given {@link MetaModel} item to the {@code followedMetaModelSet}.
	 *
	 * @param item
	 *            the item to add
	 */
	public void addFollowedMetaModelSetItem(MetaModel item) {
		if (getFollowedMetaModelSet() == null) {
			setFollowedMetaModelSet(new HashSet<>());
		}
		getFollowedMetaModelSet().add(item);
	}

	/**
	 * Remove the given {@link MetaModel} item from the {@code followedMetaModelSet}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeFollowedMetaModelSetItem(MetaModel item) {
		if (getFollowedMetaModelSet() == null) {
			return;
		}
		getFollowedMetaModelSet().remove(item);
	}

	/**
	 * Clear the {@code followedMetaModelSet} collection.
	 *
	 */
	public void clearFollowedMetaModelSet() {
		if (getFollowedMetaModelSet() != null) {
			getFollowedMetaModelSet().clear();
		}
	}

	public List<EmailAccount> getEmailAccountList() {
		return emailAccountList;
	}

	public void setEmailAccountList(List<EmailAccount> emailAccountList) {
		this.emailAccountList = emailAccountList;
	}

	/**
	 * Add the given {@link EmailAccount} item to the {@code emailAccountList}.
	 *
	 * @param item
	 *            the item to add
	 */
	public void addEmailAccountListItem(EmailAccount item) {
		if (getEmailAccountList() == null) {
			setEmailAccountList(new ArrayList<>());
		}
		getEmailAccountList().add(item);
	}

	/**
	 * Remove the given {@link EmailAccount} item from the {@code emailAccountList}.
	 *
	 * <p>
	 * It sets {@code item.null = null} to break the relationship.
	 * </p>
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeEmailAccountListItem(EmailAccount item) {
		if (getEmailAccountList() == null) {
			return;
		}
		getEmailAccountList().remove(item);
	}

	/**
	 * Clear the {@code emailAccountList} collection.
	 *
	 * <p>
	 * It sets {@code item.null = null} to break the relationship.
	 * </p>
	 */
	public void clearEmailAccountList() {
		if (getEmailAccountList() != null) {
			getEmailAccountList().clear();
		}
	}

	public String getTransientPassword() {
		return transientPassword;
	}

	public void setTransientPassword(String transientPassword) {
		this.transientPassword = transientPassword;
	}

	public Integer getStepStatusSelect() {
		return stepStatusSelect == null ? 0 : stepStatusSelect;
	}

	public void setStepStatusSelect(Integer stepStatusSelect) {
		this.stepStatusSelect = stepStatusSelect;
	}

	public String getAttrs() {
		return attrs;
	}

	public void setAttrs(String attrs) {
		this.attrs = attrs;
	}

	public String getEmailSignature() {
		return emailSignature;
	}

	public void setEmailSignature(String emailSignature) {
		this.emailSignature = emailSignature;
	}

	public TradingName getTradingName() {
		return tradingName;
	}

	public void setTradingName(TradingName tradingName) {
		this.tradingName = tradingName;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (this == obj) return true;
		if (!(obj instanceof User)) return false;

		final User other = (User) obj;
		if (this.getId() != null || other.getId() != null) {
			return Objects.equals(this.getId(), other.getId());
		}

		return Objects.equals(getCode(), other.getCode())
			&& (getCode() != null);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("id", getId())
			.add("code", getCode())
			.add("name", getName())
			.add("passwordUpdatedOn", getPasswordUpdatedOn())
			.add("forcePasswordChange", getForcePasswordChange())
			.add("email", getEmail())
			.add("theme", getTheme())
			.add("activateOn", getActivateOn())
			.add("expiresOn", getExpiresOn())
			.add("today", getToday())
			.add("useSignatureForSalesQuotations", getUseSignatureForSalesQuotations())
			.add("useSignatureForPurchaseQuotations", getUseSignatureForPurchaseQuotations())
			.omitNullValues()
			.toString();
	}
}
