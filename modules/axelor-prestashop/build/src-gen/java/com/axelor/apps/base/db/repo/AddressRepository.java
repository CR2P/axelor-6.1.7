package com.axelor.apps.base.db.repo;

import com.axelor.apps.base.db.Address;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class AddressRepository extends JpaRepository<Address> {

	public AddressRepository() {
		super(Address.class);
	}

	public Address findByPrestaShopId(Integer prestaShopId) {
		return Query.of(Address.class)
				.filter("self.prestaShopId = :prestaShopId")
				.bind("prestaShopId", prestaShopId)
				.fetchOne();
	}

}

