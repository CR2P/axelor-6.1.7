package com.axelor.apps.base.db.repo;

import com.axelor.apps.base.db.Partner;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class PartnerRepository extends JpaRepository<Partner> {

	public PartnerRepository() {
		super(Partner.class);
	}

	public Partner findByName(String name) {
		return Query.of(Partner.class)
				.filter("self.name = :name")
				.bind("name", name)
				.fetchOne();
	}

	public Partner findByPartnerSeq(String partnerSeq) {
		return Query.of(Partner.class)
				.filter("self.partnerSeq = :partnerSeq")
				.bind("partnerSeq", partnerSeq)
				.fetchOne();
	}

	public Partner findByOffice365Id(String office365Id) {
		return Query.of(Partner.class)
				.filter("self.office365Id = :office365Id")
				.bind("office365Id", office365Id)
				.fetchOne();
	}

	public static final int PARTNER_TYPE_COMPANY = 1;
	public static final int PARTNER_TYPE_INDIVIDUAL = 2;

	public static final int PARTNER_TITLE_M = 1;
	public static final int PARTNER_TITLE_MS = 2;
	public static final int PARTNER_TITLE_DR = 3;
	public static final int PARTNER_TITLE_PROF = 4;
}

