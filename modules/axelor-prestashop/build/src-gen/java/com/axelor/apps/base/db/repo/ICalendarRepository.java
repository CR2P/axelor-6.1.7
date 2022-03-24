package com.axelor.apps.base.db.repo;

import com.axelor.apps.base.db.ICalendar;
import com.axelor.auth.db.User;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class ICalendarRepository extends JpaRepository<ICalendar> {

	public ICalendarRepository() {
		super(ICalendar.class);
	}

	public ICalendar findByName(String name) {
		return Query.of(ICalendar.class)
				.filter("self.name = :name")
				.bind("name", name)
				.fetchOne();
	}

	public ICalendar findByUser(User user) {
		return Query.of(ICalendar.class)
				.filter("self.user = :user")
				.bind("user", user)
				.fetchOne();
	}

	// TYPE SELECT
			public static final int ICAL_SERVER = 1;
	public static final int CALENDAR_SERVER = 2;
	public static final int GCAL = 3;
	public static final int ZIMBRA = 4;
	public static final int KMS = 5;
	public static final int CGP = 6;
	public static final int CHANDLER = 7;

	// SYNCHRONISATION SELECT
	public static final String ICAL_ONLY = "ICalEvent";

	// SYNCHRONISATION SELECT
	public static final String CRM_SYNCHRO = "Event";
}

