package com.axelor.meta.db.repo;

import com.axelor.db.JpaRepository;
import com.axelor.db.Query;
import com.axelor.meta.db.MetaSchedule;

public class MetaScheduleRepository extends JpaRepository<MetaSchedule> {

	public MetaScheduleRepository() {
		super(MetaSchedule.class);
	}

	public MetaSchedule findByName(String name) {
		return Query.of(MetaSchedule.class)
				.filter("self.name = :name")
				.bind("name", name)
				.fetchOne();
	}

}

