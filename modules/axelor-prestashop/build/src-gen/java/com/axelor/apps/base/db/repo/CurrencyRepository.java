package com.axelor.apps.base.db.repo;

import com.axelor.apps.base.db.Currency;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class CurrencyRepository extends JpaRepository<Currency> {

	public CurrencyRepository() {
		super(Currency.class);
	}

	public Currency findByCode(String code) {
		return Query.of(Currency.class)
				.filter("self.code = :code")
				.bind("code", code)
				.fetchOne();
	}

	public Currency findByName(String name) {
		return Query.of(Currency.class)
				.filter("self.name = :name")
				.bind("name", name)
				.fetchOne();
	}

	public Currency findByPrestaShopId(Integer prestaShopId) {
		return Query.of(Currency.class)
				.filter("self.prestaShopId = :prestaShopId")
				.bind("prestaShopId", prestaShopId)
				.fetchOne();
	}

}

