package com.axelor.apps.stock.db.repo;

import com.axelor.apps.stock.db.StockLocationLine;
import com.axelor.db.JpaRepository;

public class StockLocationLineRepository extends JpaRepository<StockLocationLine> {

	public StockLocationLineRepository() {
		super(StockLocationLine.class);
	}

}

