package com.axelor.auth.db.repo;

import com.axelor.auth.db.Permission;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class PermissionRepository extends JpaRepository<Permission> {

	public PermissionRepository() {
		super(Permission.class);
	}

	public Permission findByName(String name) {
		return Query.of(Permission.class)
				.filter("self.name = :name")
				.bind("name", name)
				.fetchOne();
	}

}

