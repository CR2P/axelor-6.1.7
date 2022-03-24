package com.axelor.apps.base.db.repo;

import com.axelor.apps.base.db.BankDetails;
import com.axelor.apps.base.db.Partner;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class BankDetailsRepository extends JpaRepository<BankDetails> {

	public BankDetailsRepository() {
		super(BankDetails.class);
	}

	public BankDetails findByCode(String code) {
		return Query.of(BankDetails.class)
				.filter("self.code = :code")
				.bind("code", code)
				.fetchOne();
	}

	public BankDetails findDefaultByPartner(Partner partner) {
		return Query.of(BankDetails.class)
				.filter("self.partner = :partner AND self.isDefault = TRUE")
				.bind("partner", partner)
				.fetchOne();
	}

	public Query<BankDetails> findActivesByPartner(Partner partner, Boolean active) {
		return Query.of(BankDetails.class)
				.filter("self.partner = :partner AND self.active = :active")
				.bind("partner", partner)
				.bind("active", active);
	}

}

