package com.axelor.apps.stock.db.repo;

import com.axelor.apps.stock.db.LogisticalFormLine;
import com.axelor.db.JpaRepository;

public class LogisticalFormLineRepository extends JpaRepository<LogisticalFormLine> {

	public LogisticalFormLineRepository() {
		super(LogisticalFormLine.class);
	}

	public static int TYPE_PARCEL = 1;
	public static int TYPE_PALLET = 2;
	public static int TYPE_DETAIL = 3;
}

