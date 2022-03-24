package com.axelor.apps.sale.db.repo;

import com.axelor.apps.sale.db.SaleOrder;
import com.axelor.apps.sale.db.SaleOrderLine;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class SaleOrderLineRepository extends JpaRepository<SaleOrderLine> {

	public SaleOrderLineRepository() {
		super(SaleOrderLine.class);
	}

	public Query<SaleOrderLine> findAllBySaleOrder(SaleOrder saleOrder) {
		return Query.of(SaleOrderLine.class)
				.filter("self.saleOrder = :saleOrder")
				.bind("saleOrder", saleOrder);
	}

	// TYPE SELECT
	public static final int TYPE_NORMAL = 0;
	public static final int TYPE_TITLE = 1;
	public static final int TYPE_START_OF_PACK = 2;
	public static final int TYPE_END_OF_PACK = 3;

	// SALE SUPPLY SELECT
			public static final int SALE_SUPPLY_NONE = 0;
			public static final int SALE_SUPPLY_FROM_STOCK = 1;
			public static final int SALE_SUPPLY_PURCHASE = 2;
			public static final int SALE_SUPPLY_PRODUCE = 3;

	// DELIVERY STATE SELECT
	public static final int DELIVERY_STATE_NOT_DELIVERED = 1;
	public static final int DELIVERY_STATE_PARTIALLY_DELIVERED = 2;
	public static final int DELIVERY_STATE_DELIVERED = 3;

	// AVAILABLE STATUS SELECT
	public static final int STATUS_AVAILABLE = 1;
	public static final int STATUS_MISSING = 2;
}

