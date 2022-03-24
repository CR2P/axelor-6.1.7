package com.axelor.apps.base.db.repo;

import com.axelor.apps.base.db.Batch;
import com.axelor.db.JpaRepository;

public class BatchRepository extends JpaRepository<Batch> {

	public BatchRepository() {
		super(Batch.class);
	}

}

