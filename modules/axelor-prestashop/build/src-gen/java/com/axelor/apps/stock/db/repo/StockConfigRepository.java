package com.axelor.apps.stock.db.repo;

import com.axelor.apps.stock.db.StockConfig;
import com.axelor.db.JpaRepository;

public class StockConfigRepository extends JpaRepository<StockConfig> {

	public StockConfigRepository() {
		super(StockConfig.class);
	}

	public static final int VALUATION_TYPE_WAP_VALUE = 1;
	public static final int VALUATION_TYPE_ACCOUNTING_VALUE = 2;
	public static final int VALUATION_TYPE_SALE_VALUE = 3;
}

