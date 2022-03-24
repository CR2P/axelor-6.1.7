package com.axelor.apps.account.db.repo;

import com.axelor.apps.account.db.FixedAsset;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class FixedAssetRepository extends JpaRepository<FixedAsset> {

	public FixedAssetRepository() {
		super(FixedAsset.class);
	}

	public FixedAsset findByName(String name) {
		return Query.of(FixedAsset.class)
				.filter("self.name = :name")
				.bind("name", name)
				.fetchOne();
	}

	// STATUS SELECT
	public static final int STATUS_DRAFT = 1;
	public static final int STATUS_VALIDATED = 2;
	public static final int STATUS_DEPRECIATED = 3;
	public static final int STATUS_TRANSFERRED = 4;

	// COMPUTATION METHOD
	public static final String COMPUTATION_METHOD_LINEAR = "linear";
	public static final String COMPUTATION_METHOD_DEGRESSIVE = "degressive";
}

