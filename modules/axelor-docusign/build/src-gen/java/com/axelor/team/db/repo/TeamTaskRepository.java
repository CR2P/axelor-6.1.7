package com.axelor.team.db.repo;

import com.axelor.db.JpaRepository;
import com.axelor.db.Query;
import com.axelor.team.db.TeamTask;

public class TeamTaskRepository extends JpaRepository<TeamTask> {

	public TeamTaskRepository() {
		super(TeamTask.class);
	}

	public TeamTask findByName(String name) {
		return Query.of(TeamTask.class)
				.filter("self.name = :name")
				.bind("name", name)
				.fetchOne();
	}

	// TYPE SELECT
	public static final String TYPE_TASK = "task";
	public static final
	String TYPE_TICKET = "ticket";
}

