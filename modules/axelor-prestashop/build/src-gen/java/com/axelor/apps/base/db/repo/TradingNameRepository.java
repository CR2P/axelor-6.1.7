package com.axelor.apps.base.db.repo;

import com.axelor.apps.base.db.TradingName;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class TradingNameRepository extends JpaRepository<TradingName> {

	public TradingNameRepository() {
		super(TradingName.class);
	}

	public TradingName findByName(String name) {
		return Query.of(TradingName.class)
				.filter("self.name = :name")
				.bind("name", name)
				.fetchOne();
	}

}

