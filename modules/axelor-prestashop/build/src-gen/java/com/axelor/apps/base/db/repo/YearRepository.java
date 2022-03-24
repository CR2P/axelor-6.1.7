package com.axelor.apps.base.db.repo;

import com.axelor.apps.base.db.Year;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class YearRepository extends JpaRepository<Year> {

	public YearRepository() {
		super(Year.class);
	}

	public Year findByCode(String code) {
		return Query.of(Year.class)
				.filter("self.code = :code")
				.bind("code", code)
				.fetchOne();
	}

	public Year findByName(String name) {
		return Query.of(Year.class)
				.filter("self.name = :name")
				.bind("name", name)
				.fetchOne();
	}

	// STATUS SELECT
	public static final int STATUS_OPENED = 1;
	public static final int STATUS_CLOSED = 2;
	public static final int STATUS_ADJUSTING = 3;

	// TYPE SELECT
	public static final int TYPE_CIVIL = 0;
	public static final int TYPE_FISCAL = 1;
	public static final int TYPE_PAYROLL = 2;
}

