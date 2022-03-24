package com.axelor.apps.account.db.repo;

import com.axelor.apps.account.db.AnalyticMoveLine;
import com.axelor.db.JpaRepository;

public class AnalyticMoveLineRepository extends JpaRepository<AnalyticMoveLine> {

	public AnalyticMoveLineRepository() {
		super(AnalyticMoveLine.class);
	}

	// STATUS SELECT
	public static final int STATUS_FORECAST_ORDER = 1;
	public static final int STATUS_FORECAST_INVOICE = 2;
	public static final int STATUS_REAL_ACCOUNTING = 3;
}

