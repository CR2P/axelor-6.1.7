package com.axelor.apps.prestashop.db.repo;

import com.axelor.apps.prestashop.db.PrestashopOrderStatusCacheEntry;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class PrestashopOrderStatusCacheEntryRepository extends JpaRepository<PrestashopOrderStatusCacheEntry> {

	public PrestashopOrderStatusCacheEntryRepository() {
		super(PrestashopOrderStatusCacheEntry.class);
	}

	public PrestashopOrderStatusCacheEntry findByName(String name) {
		return Query.of(PrestashopOrderStatusCacheEntry.class)
				.filter("self.name = :name")
				.bind("name", name)
				.fetchOne();
	}

	public PrestashopOrderStatusCacheEntry findByPrestaShopId(Integer prestaShopId) {
		return Query.of(PrestashopOrderStatusCacheEntry.class)
				.filter("self.prestaShopId = :prestaShopId")
				.bind("prestaShopId", prestaShopId)
				.fetchOne();
	}

}

