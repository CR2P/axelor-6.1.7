package com.axelor.apps.message.db.repo;

import com.axelor.apps.message.db.Message;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class MessageRepository extends JpaRepository<Message> {

	public MessageRepository() {
		super(Message.class);
	}

	public Query<Message> findByRelatedTo(long relatedToSelectId, String relatedToSelect) {
		return Query.of(Message.class)
				.filter("self.relatedTo1SelectId = :relatedToSelectId				AND self.relatedTo1Select = :relatedToSelect			   OR self.relatedTo2SelectId = :relatedToSelectId			   AND self.relatedTo2Select = :relatedToSelect")
				.bind("relatedToSelectId", relatedToSelectId)
				.bind("relatedToSelect", relatedToSelect);
	}

	public Message findByOffice365Id(String office365Id) {
		return Query.of(Message.class)
				.filter("self.office365Id = :office365Id")
				.bind("office365Id", office365Id)
				.fetchOne();
	}

	// TYPE SELECT
	public static final int TYPE_RECEIVED = 1;
	public static final int TYPE_SENT = 2;

	// STATUS SELECT
	public static final int STATUS_DRAFT = 1;
	public static final int STATUS_IN_PROGRESS = 2;
	public static final int STATUS_SENT = 3;
	public static final int STATUS_DELETED = 4;

	// MEDIA TYPE SELECT
	public static final int MEDIA_TYPE_MAIL = 1;
	public static final int MEDIA_TYPE_EMAIL = 2;
	public static final int MEDIA_TYPE_CHAT = 3;
	public static final int MEDIA_TYPE_SMS = 5;

	// RELATED TO SELECT
	public static final String RELATED_TO_PARTNER = "com.axelor.apps.base.db.Partner";
	public static final String RELATED_TO_LEAD = "com.axelor.apps.crm.db.Lead";
	public static final String RELATED_TO_OPPORTUNITY = "com.axelor.apps.crm.db.Opportunity";
	public static final String RELATED_TO_PRODUCT = "com.axelor.apps.base.db.Product";
	public static final String RELATED_TO_EVENT = "com.axelor.apps.crm.db.Event";
	public static final String RELATED_TO_SALESORDER = "com.axelor.apps.sale.db.SaleOrder";
	public static final String RELATED_TO_PROJECT = "com.axelor.apps.project.db.Project";
}

