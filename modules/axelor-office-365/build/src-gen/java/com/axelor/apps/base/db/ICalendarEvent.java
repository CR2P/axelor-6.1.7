package com.axelor.apps.base.db;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import com.axelor.auth.db.AuditableModel;
import com.axelor.auth.db.User;
import com.axelor.db.annotations.EqualsInclude;
import com.axelor.db.annotations.NameColumn;
import com.axelor.db.annotations.Track;
import com.axelor.db.annotations.TrackEvent;
import com.axelor.db.annotations.TrackField;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@Table(name = "ICAL_EVENT", indexes = { @Index(columnList = "subject"), @Index(columnList = "calendar"), @Index(columnList = "organizer"), @Index(columnList = "user_id") })
@Track(fields = { @TrackField(name = "subject"), @TrackField(name = "startDateTime"), @TrackField(name = "endDateTime", on = TrackEvent.UPDATE), @TrackField(name = "computedAttendeeList") }, subscribe = true)
public class ICalendarEvent extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ICAL_EVENT_SEQ")
	@SequenceGenerator(name = "ICAL_EVENT_SEQ", sequenceName = "ICAL_EVENT_SEQ", allocationSize = 1)
	private Long id;

	@EqualsInclude
	@Widget(title = "UID")
	@Column(name = "calendar_uid", unique = true)
	private String uid;

	@Widget(title = "URL")
	private String url;

	@Widget(title = "Subject")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	@NameColumn
	@NotNull
	private String subject;

	@Widget(title = "Description")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String description;

	@Widget(title = "Status")
	private String status;

	@Widget(title = "Start date")
	@NotNull
	private LocalDateTime startDateTime;

	@Widget(title = "End date")
	@NotNull
	private LocalDateTime endDateTime;

	@Widget(title = "All day")
	private Boolean allDay = Boolean.FALSE;

	@Widget(title = "Location")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String location;

	@Widget(title = "Geo. coordinates")
	private String geo;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private ICalendar calendar;

	@Widget(title = "Organizer")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private ICalendarUser organizer;

	@Widget(title = "Attendees")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "event", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private List<ICalendarUser> attendees;

	@Widget(title = "Attendees")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String computedAttendeeList;

	@Widget(title = "Visibility", selection = "i.cal.event.visibility.select")
	private Integer visibilitySelect = 1;

	@Widget(title = "Availability", selection = "i.cal.event.disponibility.select")
	private Integer disponibilitySelect = 1;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String subjectTeam;

	@Widget(title = "Type", selection = "icalendar.event.type.select")
	@NotNull
	private Integer typeSelect = 0;

	@Widget(title = "Assigned to")
	@JoinColumn(name = "user_id")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private User user;

	@Widget(title = "Office365 id")
	private String office365Id;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public ICalendarEvent() {
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(LocalDateTime startDateTime) {
		this.startDateTime = startDateTime;
	}

	public LocalDateTime getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(LocalDateTime endDateTime) {
		this.endDateTime = endDateTime;
	}

	public Boolean getAllDay() {
		return allDay == null ? Boolean.FALSE : allDay;
	}

	public void setAllDay(Boolean allDay) {
		this.allDay = allDay;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getGeo() {
		return geo;
	}

	public void setGeo(String geo) {
		this.geo = geo;
	}

	public ICalendar getCalendar() {
		return calendar;
	}

	public void setCalendar(ICalendar calendar) {
		this.calendar = calendar;
	}

	public ICalendarUser getOrganizer() {
		return organizer;
	}

	public void setOrganizer(ICalendarUser organizer) {
		this.organizer = organizer;
	}

	public List<ICalendarUser> getAttendees() {
		return attendees;
	}

	public void setAttendees(List<ICalendarUser> attendees) {
		this.attendees = attendees;
	}

	/**
	 * Add the given {@link ICalendarUser} item to the {@code attendees}.
	 *
	 * <p>
	 * It sets {@code item.event = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addAttendee(ICalendarUser item) {
		if (getAttendees() == null) {
			setAttendees(new ArrayList<>());
		}
		getAttendees().add(item);
		item.setEvent(this);
	}

	/**
	 * Remove the given {@link ICalendarUser} item from the {@code attendees}.
	 *
	 * <p>
	 * It sets {@code item.event = null} to break the relationship.
	 * </p>
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeAttendee(ICalendarUser item) {
		if (getAttendees() == null) {
			return;
		}
		getAttendees().remove(item);
		item.setEvent(null);
	}

	/**
	 * Clear the {@code attendees} collection.
	 *
	 * <p>
	 * It sets {@code item.event = null} to break the relationship.
	 * </p>
	 */
	public void clearAttendees() {
		if (getAttendees() != null) {
			for (ICalendarUser item : getAttendees()) {
        item.setEvent(null);
      }
			getAttendees().clear();
		}
	}

	public String getComputedAttendeeList() {
		return computedAttendeeList;
	}

	public void setComputedAttendeeList(String computedAttendeeList) {
		this.computedAttendeeList = computedAttendeeList;
	}

	public Integer getVisibilitySelect() {
		return visibilitySelect == null ? 0 : visibilitySelect;
	}

	public void setVisibilitySelect(Integer visibilitySelect) {
		this.visibilitySelect = visibilitySelect;
	}

	public Integer getDisponibilitySelect() {
		return disponibilitySelect == null ? 0 : disponibilitySelect;
	}

	public void setDisponibilitySelect(Integer disponibilitySelect) {
		this.disponibilitySelect = disponibilitySelect;
	}

	public String getSubjectTeam() {
		return subjectTeam;
	}

	public void setSubjectTeam(String subjectTeam) {
		this.subjectTeam = subjectTeam;
	}

	public Integer getTypeSelect() {
		return typeSelect == null ? 0 : typeSelect;
	}

	public void setTypeSelect(Integer typeSelect) {
		this.typeSelect = typeSelect;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getOffice365Id() {
		return office365Id;
	}

	public void setOffice365Id(String office365Id) {
		this.office365Id = office365Id;
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
		if (!(obj instanceof ICalendarEvent)) return false;

		final ICalendarEvent other = (ICalendarEvent) obj;
		if (this.getId() != null || other.getId() != null) {
			return Objects.equals(this.getId(), other.getId());
		}

		return Objects.equals(getUid(), other.getUid())
			&& (getUid() != null);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("id", getId())
			.add("uid", getUid())
			.add("url", getUrl())
			.add("status", getStatus())
			.add("startDateTime", getStartDateTime())
			.add("endDateTime", getEndDateTime())
			.add("allDay", getAllDay())
			.add("geo", getGeo())
			.add("visibilitySelect", getVisibilitySelect())
			.add("disponibilitySelect", getDisponibilitySelect())
			.add("typeSelect", getTypeSelect())
			.add("office365Id", getOffice365Id())
			.omitNullValues()
			.toString();
	}
}
