package com.axelor.apps.account.db.repo;

import com.axelor.apps.account.db.AccountConfig;
import com.axelor.apps.base.db.Company;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class AccountConfigRepository extends JpaRepository<AccountConfig> {

	public AccountConfigRepository() {
		super(AccountConfig.class);
	}

	public AccountConfig findByCompany(Company company) {
		return Query.of(AccountConfig.class)
				.filter("self.company = :company")
				.bind("company", company)
				.fetchOne();
	}

	// TYPE SELECT
	public static final int INVOICE_WT_ALWAYS = 1;
	public static final int INVOICE_ATI_ALWAYS = 2;
	public static final int INVOICE_WT_DEFAULT = 3;
	public static final int INVOICE_ATI_DEFAULT = 4;

	// AUTOMATIC PARTNER ACCOUNT CREATION TYPE
	public static final int AUTOMATIC_ACCOUNT_CREATION_NONE = 0;
	public static final int AUTOMATIC_ACCOUNT_CREATION_PREFIX = 1;
	public static final int AUTOMATIC_ACCOUNT_CREATION_SEQUENCE = 2;
}

