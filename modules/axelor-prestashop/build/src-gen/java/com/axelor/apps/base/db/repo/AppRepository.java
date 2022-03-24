package com.axelor.apps.base.db.repo;

import com.axelor.apps.base.db.App;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class AppRepository extends JpaRepository<App> {

	public AppRepository() {
		super(App.class);
	}

	public App findByCode(String code) {
		return Query.of(App.class)
				.filter("self.code = :code")
				.bind("code", code)
				.cacheable()
				.fetchOne();
	}

	public App findByName(String name) {
		return Query.of(App.class)
				.filter("self.name = :name")
				.bind("name", name)
				.cacheable()
				.fetchOne();
	}

}

