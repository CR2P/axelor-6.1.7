package com.axelor.apps.rossum.db.repo;

import com.axelor.apps.rossum.db.Queue;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class QueueRepository extends JpaRepository<Queue> {

	public QueueRepository() {
		super(Queue.class);
	}

	public Queue findByUrl(String queueUrl) {
		return Query.of(Queue.class)
				.filter("self.queueUrl = :queueUrl")
				.bind("queueUrl", queueUrl)
				.fetchOne();
	}

}

