package com.axelor.apps.account.db.repo;

import com.axelor.apps.account.db.AccountManagement;
import com.axelor.db.JpaRepository;

public class AccountManagementRepository extends JpaRepository<AccountManagement> {

	public AccountManagementRepository() {
		super(AccountManagement.class);
	}

	// TYPE SELECT
	public static final int TYPE_PRODUCT = 1;
	public static final int TYPE_TAX = 2;
	public static final int TYPE_PAYMENT = 3;
}

