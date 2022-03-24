package com.axelor.apps.base.db.repo;

import com.axelor.apps.base.db.BaseBatch;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class BaseBatchRepository extends JpaRepository<BaseBatch> {

	public BaseBatchRepository() {
		super(BaseBatch.class);
	}

	public BaseBatch findByCode(String code) {
		return Query.of(BaseBatch.class)
				.filter("self.code = :code")
				.bind("code", code)
				.fetchOne();
	}

	public static final int ACTION_SYNCHRONIZE_CALENDARS = 2;

	public static final int ACTION_SYNCHRONIZE_CONTACTS = 3;
}

