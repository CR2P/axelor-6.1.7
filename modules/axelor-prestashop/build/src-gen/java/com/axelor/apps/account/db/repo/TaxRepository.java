package com.axelor.apps.account.db.repo;

import com.axelor.apps.account.db.Tax;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class TaxRepository extends JpaRepository<Tax> {

	public TaxRepository() {
		super(Tax.class);
	}

	public Tax findByCode(String code) {
		return Query.of(Tax.class)
				.filter("self.code = :code")
				.bind("code", code)
				.fetchOne();
	}

	public Tax findByName(String name) {
		return Query.of(Tax.class)
				.filter("self.name = :name")
				.bind("name", name)
				.fetchOne();
	}

	public Tax findByPrestaShopId(Integer prestaShopId) {
		return Query.of(Tax.class)
				.filter("self.prestaShopId = :prestaShopId")
				.bind("prestaShopId", prestaShopId)
				.fetchOne();
	}

	//TAX TYPE
	public static final int TAX_TYPE_COLLECTION = 1;
	public static final int TAX_TYPE_DEBIT = 2;
}

