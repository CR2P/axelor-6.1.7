package com.axelor.apps.sale.db.repo;

import com.axelor.apps.base.db.Company;
import com.axelor.apps.sale.db.SaleOrder;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class SaleOrderRepository extends JpaRepository<SaleOrder> {

	public SaleOrderRepository() {
		super(SaleOrder.class);
	}

	public SaleOrder findBySaleOrderSeqAndCompany(String saleOrderSeq, Company company) {
		return Query.of(SaleOrder.class)
				.filter("self.saleOrderSeq = :saleOrderSeq AND self.company = :company")
				.bind("saleOrderSeq", saleOrderSeq)
				.bind("company", company)
				.fetchOne();
	}

	public SaleOrder findByPrestaShopId(Integer prestaShopId) {
		return Query.of(SaleOrder.class)
				.filter("self.prestaShopId = :prestaShopId")
				.bind("prestaShopId", prestaShopId)
				.fetchOne();
	}

	// STATUS
	public static final int STATUS_DRAFT_QUOTATION = 1;
	public static final int STATUS_FINALIZED_QUOTATION = 2;
	public static final int STATUS_ORDER_CONFIRMED = 3;
	public static final int STATUS_ORDER_COMPLETED = 4;
	public static final int STATUS_CANCELED = 5;

	// INVOICE
	public static final int INVOICE_ALL = 1;
	public static final int INVOICE_LINES = 2;
	public static final int INVOICE_ADVANCE_PAYMENT = 3;
	public static final int INVOICE_TIMETABLES = 4;

	// SALE ORDER TYPE
	public static final int SALE_ORDER_TYPE_NORMAL = 1;
	public static final int SALE_ORDER_TYPE_SUBSCRIPTION = 2;

	// DELIVERY STATE SELECT
	public static final int DELIVERY_STATE_NOT_DELIVERED = 1;
				public static final int DELIVERY_STATE_PARTIALLY_DELIVERED = 2;
				public static final int DELIVERY_STATE_DELIVERED = 3;
}

