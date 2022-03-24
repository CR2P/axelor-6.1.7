package com.axelor.apps.base.db.repo;

import com.axelor.apps.base.db.ICalendarEvent;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class ICalendarEventRepository extends JpaRepository<ICalendarEvent> {

	public ICalendarEventRepository() {
		super(ICalendarEvent.class);
	}

	public ICalendarEvent findByUid(String uid) {
		return Query.of(ICalendarEvent.class)
				.filter("self.uid = :uid")
				.bind("uid", uid)
				.fetchOne();
	}

	public ICalendarEvent findByOffice365Id(String office365Id) {
		return Query.of(ICalendarEvent.class)
				.filter("self.office365Id = :office365Id")
				.bind("office365Id", office365Id)
				.fetchOne();
	}

	public static final Integer VISIBILITY_PUBLIC = 1;
	public static final Integer VISIBILITY_PRIVATE = 2;
	public static final Integer DISPONIBILITY_BUSY = 1;
	public static final Integer DISPONIBILITY_AVAILABLE = 2;

	// DISPONIBILITY SELECT
			public static final Integer DISPONIBILITY_AWAY = 3;
			public static final Integer DISPONIBILITY_TENTATIVE = 4;
			public static final Integer DISPONIBILITY_WORKING_ELSEWHERE = 5;
}

