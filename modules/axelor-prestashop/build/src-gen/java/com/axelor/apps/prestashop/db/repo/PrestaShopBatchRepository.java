package com.axelor.apps.prestashop.db.repo;

import com.axelor.apps.prestashop.db.PrestaShopBatch;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class PrestaShopBatchRepository extends JpaRepository<PrestaShopBatch> {

	public PrestaShopBatchRepository() {
		super(PrestaShopBatch.class);
	}

	public PrestaShopBatch findByCode(String code) {
		return Query.of(PrestaShopBatch.class)
				.filter("self.code = :code")
				.bind("code", code)
				.fetchOne();
	}

}

