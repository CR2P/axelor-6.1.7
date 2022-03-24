package com.axelor.apps.stock.db.repo;

import com.axelor.apps.stock.db.StockMove;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class StockMoveRepository extends JpaRepository<StockMove> {

	public StockMoveRepository() {
		super(StockMove.class);
	}

	public StockMove findByName(String name) {
		return Query.of(StockMove.class)
				.filter("self.name = :name")
				.bind("name", name)
				.fetchOne();
	}

	public Query<StockMove> findAllBySaleOrderAndStatus(String originTypeSelect, Long originId, Integer statusSelect) {
		return Query.of(StockMove.class)
				.filter("self.originTypeSelect = :originTypeSelect AND self.originId = :originId AND self.statusSelect = :statusSelect")
				.bind("originTypeSelect", originTypeSelect)
				.bind("originId", originId)
				.bind("statusSelect", statusSelect);
	}

	// STATUS SELECT
	public static final int STATUS_DRAFT = 1;
	public static final int STATUS_PLANNED = 2;
	public static final int STATUS_REALIZED = 3;
	public static final int STATUS_CANCELED = 4;

	// AVAILABLE STATUS SELECT
	public static final int STATUS_AVAILABLE = 1;
	public static final int STATUS_PARTIALLY_AVAILABLE = 2;
	public static final int STATUS_UNAVAILABLE = 3;

	// USER TYPE
	public static final String USER_TYPE_SENDER = "Sender";
	public static final String USER_TYPE_SALESPERSON = "Salesperson";

	// TYPE SELECT
	public static final int TYPE_INTERNAL = 1;
	public static final int TYPE_OUTGOING = 2;
	public static final int TYPE_INCOMING = 3;

	// CONFORMITY SELECT
	public static final int CONFORMITY_NONE = 1;
	public static final int CONFORMITY_COMPLIANT = 2;
	public static final int CONFORMITY_NON_COMPLIANT = 3;

	public static final String ORIGIN_INVENTORY = "com.axelor.apps.stock.db.Inventory";
	public static final String ORIGIN_STOCK_CORRECTION = "com.axelor.apps.stock.db.StockCorrection";

	public static final String ORIGIN_SALE_ORDER = "com.axelor.apps.sale.db.SaleOrder";
	public static final String ORIGIN_PURCHASE_ORDER = "com.axelor.apps.purchase.db.PurchaseOrder";

	// INVOICE
	public static final int INVOICE_ALL = 1;
	public static final int INVOICE_PARTIALLY = 2;

	 // INVOICING STATUS
	 public static final int STATUS_NOT_INVOICED = 0;
	 public static final int STATUS_PARTIALLY_INVOICED = 1;
	 public static final int STATUS_INVOICED = 2;
	 public static final int STATUS_DELAYED_INVOICE = 3;
	 public static final int STATUS_VALIDATED_INVOICE = 4;
}

