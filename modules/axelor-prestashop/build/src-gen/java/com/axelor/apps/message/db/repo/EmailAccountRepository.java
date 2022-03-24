package com.axelor.apps.message.db.repo;

import com.axelor.apps.message.db.EmailAccount;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class EmailAccountRepository extends JpaRepository<EmailAccount> {

	public EmailAccountRepository() {
		super(EmailAccount.class);
	}

	public EmailAccount findByName(String name) {
		return Query.of(EmailAccount.class)
				.filter("self.name = :name")
				.bind("name", name)
				.fetchOne();
	}

	// SERVER TYPE SELECT
	public static final int SERVER_TYPE_SMTP = 1;
	public static final int SERVER_TYPE_POP = 2;
	public static final int SERVER_TYPE_IMAP = 3;

	// SECURITY TYPE SELECT
	public static final int SECURITY_NONE = 0;
	public static final int SECURITY_SSL = 1;
	public static final int SECURITY_STARTTLS = 2;
}

