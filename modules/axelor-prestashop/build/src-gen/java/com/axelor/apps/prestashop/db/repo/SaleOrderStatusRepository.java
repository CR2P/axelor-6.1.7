package com.axelor.apps.prestashop.db.repo;

import com.axelor.apps.prestashop.db.SaleOrderStatus;
import com.axelor.db.JpaRepository;

public class SaleOrderStatusRepository extends JpaRepository<SaleOrderStatus> {

	public SaleOrderStatusRepository() {
		super(SaleOrderStatus.class);
	}

}

