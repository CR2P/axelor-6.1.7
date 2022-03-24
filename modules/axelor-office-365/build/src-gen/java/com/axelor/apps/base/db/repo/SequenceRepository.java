package com.axelor.apps.base.db.repo;

import com.axelor.apps.base.db.Company;
import com.axelor.apps.base.db.Sequence;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class SequenceRepository extends JpaRepository<Sequence> {

	public SequenceRepository() {
		super(Sequence.class);
	}

	public Sequence findByName(String name) {
		return Query.of(Sequence.class)
				.filter("self.name = :name")
				.bind("name", name)
				.fetchOne();
	}

	public Sequence find(String codeSelect, Company company) {
		return Query.of(Sequence.class)
				.filter("self.codeSelect = :codeSelect AND self.company = :company")
				.bind("codeSelect", codeSelect)
				.bind("company", company)
				.fetchOne();
	}

	public Sequence findByCodeSelect(String codeSelect) {
		return Query.of(Sequence.class)
				.filter("self.codeSelect = :codeSelect")
				.bind("codeSelect", codeSelect)
				.fetchOne();
	}

	//SEQUENCE SELECT
	public static final String PARTNER = "partner";
	public static final String PRODUCT = "product";

	//SEQUENCE SELECT
			public static final String OPPORTUNITY = "opportunity";
}

