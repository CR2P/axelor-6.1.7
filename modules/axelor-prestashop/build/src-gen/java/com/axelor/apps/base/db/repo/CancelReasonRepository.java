package com.axelor.apps.base.db.repo;

import com.axelor.apps.base.db.CancelReason;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class CancelReasonRepository extends JpaRepository<CancelReason> {

	public CancelReasonRepository() {
		super(CancelReason.class);
	}

	public CancelReason findByName(String name) {
		return Query.of(CancelReason.class)
				.filter("self.name = :name")
				.bind("name", name)
				.fetchOne();
	}

}

