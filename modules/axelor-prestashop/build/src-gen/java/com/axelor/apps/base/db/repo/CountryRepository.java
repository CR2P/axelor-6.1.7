package com.axelor.apps.base.db.repo;

import com.axelor.apps.base.db.Country;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class CountryRepository extends JpaRepository<Country> {

	public CountryRepository() {
		super(Country.class);
	}

	public Country findByName(String name) {
		return Query.of(Country.class)
				.filter("self.name = :name")
				.bind("name", name)
				.fetchOne();
	}

	public Country findByPrestaShopId(Integer prestaShopId) {
		return Query.of(Country.class)
				.filter("self.prestaShopId = :prestaShopId")
				.bind("prestaShopId", prestaShopId)
				.fetchOne();
	}

	public Country findByAlpha2Code(String alpha2Code) {
		return Query.of(Country.class)
				.filter("self.alpha2Code = :alpha2Code")
				.bind("alpha2Code", alpha2Code)
				.fetchOne();
	}

}

