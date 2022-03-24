package com.axelor.apps.docusign.db.repo;

import com.axelor.apps.docusign.db.DocuSignEnvelope;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class DocuSignEnvelopeRepository extends JpaRepository<DocuSignEnvelope> {

	public DocuSignEnvelopeRepository() {
		super(DocuSignEnvelope.class);
	}

	public DocuSignEnvelope findByName(String name) {
		return Query.of(DocuSignEnvelope.class)
				.filter("self.name = :name")
				.bind("name", name)
				.fetchOne();
	}

	//TYPE SELECT
	public static final String STATUS_ANY = "any";
	public static final String STATUS_CREATED = "created";
	public static final String STATUS_SENT = "sent";
	public static final String STATUS_DELIVERED = "delivered";
	public static final String STATUS_SIGNED = "signed";
	public static final String STATUS_COMPLETED = "completed";
	public static final String STATUS_DECLINED = "declined";
	public static final String STATUS_TIMEDOUT = "timedout";
	public static final String STATUS_VOIDED = "voided";
	public static final String STATUS_DELETED = "deleted";
}

