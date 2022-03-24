package com.axelor.apps.stock.db.repo;

import com.axelor.apps.base.db.Company;
import com.axelor.apps.base.db.Partner;
import com.axelor.apps.stock.db.StockLocation;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class StockLocationRepository extends JpaRepository<StockLocation> {

	public StockLocationRepository() {
		super(StockLocation.class);
	}

	public StockLocation findByName(String name) {
		return Query.of(StockLocation.class)
				.filter("self.name = :name")
				.bind("name", name)
				.fetchOne();
	}

	public StockLocation findByCompany(Company company) {
		return Query.of(StockLocation.class)
				.filter("self.company = :company")
				.bind("company", company)
				.fetchOne();
	}

	public StockLocation findByPartner(Partner partner) {
		return Query.of(StockLocation.class)
				.filter("self.partner = :partner")
				.bind("partner", partner)
				.fetchOne();
	}

	// TYPE SELECT
	public static final int TYPE_INTERNAL = 1;
	public static final int TYPE_EXTERNAL = 2;
	public static final int TYPE_VIRTUAL = 3;

	// PRINT TYPE SELECT
	public static final int PRINT_TYPE_LOCATION_FINANCIAL_DATA = 1;
	public static final int PRINT_TYPE_STOCK_LOCATION_CONTENT = 2;
}

