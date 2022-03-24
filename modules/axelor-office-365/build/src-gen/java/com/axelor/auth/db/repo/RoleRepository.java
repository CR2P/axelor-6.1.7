package com.axelor.auth.db.repo;

import com.axelor.auth.db.Role;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class RoleRepository extends JpaRepository<Role> {

	public RoleRepository() {
		super(Role.class);
	}

	public Role findByName(String name) {
		return Query.of(Role.class)
				.filter("self.name = :name")
				.bind("name", name)
				.fetchOne();
	}

}

