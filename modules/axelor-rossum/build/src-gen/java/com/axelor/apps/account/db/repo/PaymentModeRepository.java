package com.axelor.apps.account.db.repo;

import com.axelor.apps.account.db.PaymentMode;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class PaymentModeRepository extends JpaRepository<PaymentMode> {

	public PaymentModeRepository() {
		super(PaymentMode.class);
	}

	public PaymentMode findByCode(String code) {
		return Query.of(PaymentMode.class)
				.filter("self.code = :code")
				.bind("code", code)
				.fetchOne();
	}

	public PaymentMode findByName(String name) {
		return Query.of(PaymentMode.class)
				.filter("self.name = :name")
				.bind("name", name)
				.fetchOne();
	}

	// TYPE
	public static final int TYPE_OTHER = 1;
	public static final int TYPE_DD = 2;
	public static final int TYPE_IPO = 3;
	public static final int TYPE_IPO_CHEQUE = 4;
	public static final int TYPE_CASH = 5;
	public static final int TYPE_BANK_CARD = 6;
	public static final int TYPE_CHEQUE = 7;
	public static final int TYPE_WEB = 8;
	public static final int TYPE_TRANSFER = 9;

	// Sales or purchase
	public static final int SALES = 1;
	public static final int PURCHASES = 2;

	// IN OUT SELECT
	public static final int IN = 1;
	public static final int OUT = 2;
}

