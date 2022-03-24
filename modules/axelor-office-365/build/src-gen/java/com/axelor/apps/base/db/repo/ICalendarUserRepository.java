package com.axelor.apps.base.db.repo;

import com.axelor.apps.base.db.ICalendarUser;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class ICalendarUserRepository extends JpaRepository<ICalendarUser> {

	public ICalendarUserRepository() {
		super(ICalendarUser.class);
	}

	public ICalendarUser findByName(String name) {
		return Query.of(ICalendarUser.class)
				.filter("self.name = :name")
				.bind("name", name)
				.fetchOne();
	}

	public ICalendarUser findByEmail(String email) {
		return Query.of(ICalendarUser.class)
				.filter("self.email = :email")
				.bind("email", email)
				.fetchOne();
	}

	public static final Integer STATUS_YES = 1;
	public static final Integer STATUS_NO = 2;
	public static final Integer STATUS_MAYBE = 3;

	public static final Integer STATUS_REQUIRED = 4;
	public static final Integer STATUS_OPTIONAL = 5;
}

