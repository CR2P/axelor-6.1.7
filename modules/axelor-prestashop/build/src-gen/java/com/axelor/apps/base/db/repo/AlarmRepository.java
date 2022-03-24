package com.axelor.apps.base.db.repo;

import com.axelor.apps.base.db.Alarm;
import com.axelor.db.JpaRepository;

public class AlarmRepository extends JpaRepository<Alarm> {

	public AlarmRepository() {
		super(Alarm.class);
	}

}

