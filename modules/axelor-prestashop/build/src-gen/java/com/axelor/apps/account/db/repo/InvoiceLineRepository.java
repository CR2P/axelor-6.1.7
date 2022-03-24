package com.axelor.apps.account.db.repo;

import com.axelor.apps.account.db.InvoiceLine;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class InvoiceLineRepository extends JpaRepository<InvoiceLine> {

	public InvoiceLineRepository() {
		super(InvoiceLine.class);
	}

	public InvoiceLine findByName(String name) {
		return Query.of(InvoiceLine.class)
				.filter("self.name = :name")
				.bind("name", name)
				.fetchOne();
	}

	// TYPE SELECT
	public static final int TYPE_NORMAL = 0;
	public static final int TYPE_TITLE = 1;
	public static final int TYPE_START_OF_PACK = 2;
	public static final int TYPE_END_OF_PACK = 3;
}

