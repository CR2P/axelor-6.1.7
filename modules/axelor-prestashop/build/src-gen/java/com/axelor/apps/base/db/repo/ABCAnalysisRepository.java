package com.axelor.apps.base.db.repo;

import com.axelor.apps.base.db.ABCAnalysis;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class ABCAnalysisRepository extends JpaRepository<ABCAnalysis> {

	public ABCAnalysisRepository() {
		super(ABCAnalysis.class);
	}

	public ABCAnalysis findByName(String name) {
		return Query.of(ABCAnalysis.class)
				.filter("self.name = :name")
				.bind("name", name)
				.fetchOne();
	}

	// STATUS SELECT
	public static final int STATUS_DRAFT = 1;
	public static final int STATUS_ANALYZING = 2;
	public static final int STATUS_FINISHED = 3;
}

