package com.axelor.auth.db.repo;

import com.axelor.auth.db.Group;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class GroupRepository extends JpaRepository<Group> {

	public GroupRepository() {
		super(Group.class);
	}

	public Group findByCode(String code) {
		return Query.of(Group.class)
				.filter("self.code = :code")
				.bind("code", code)
				.fetchOne();
	}

	public Group findByName(String name) {
		return Query.of(Group.class)
				.filter("self.name = :name")
				.bind("name", name)
				.fetchOne();
	}

}

