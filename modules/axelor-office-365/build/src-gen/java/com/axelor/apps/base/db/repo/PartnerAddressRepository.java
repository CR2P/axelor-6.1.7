package com.axelor.apps.base.db.repo;

import com.axelor.apps.base.db.PartnerAddress;
import com.axelor.db.JpaRepository;

import java.util.HashMap;import java.util.Map;

public class PartnerAddressRepository extends JpaRepository<PartnerAddress> {

	public PartnerAddressRepository() {
		super(PartnerAddress.class);
	}

	public  static Map<String,String> modelPartnerFieldMap = new HashMap<>();
}

