package com.axelor.apps.stock.db.repo;

import com.axelor.apps.stock.db.ShipmentMode;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class ShipmentModeRepository extends JpaRepository<ShipmentMode> {

	public ShipmentModeRepository() {
		super(ShipmentMode.class);
	}

	public ShipmentMode findByName(String name) {
		return Query.of(ShipmentMode.class)
				.filter("self.name = :name")
				.bind("name", name)
				.fetchOne();
	}

}

