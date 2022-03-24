package com.axelor.apps.base.db.repo;

import com.axelor.apps.base.db.ShippingCoef;
import com.axelor.db.JpaRepository;

public class ShippingCoefRepository extends JpaRepository<ShippingCoef> {

	public ShippingCoefRepository() {
		super(ShippingCoef.class);
	}

}

