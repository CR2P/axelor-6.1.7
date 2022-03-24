package com.axelor.apps.rossum.db.repo;

import com.axelor.apps.rossum.db.Organisation;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class OrganisationRepository extends JpaRepository<Organisation> {

	public OrganisationRepository() {
		super(Organisation.class);
	}

	public Organisation findByUrl(String organisationUrl) {
		return Query.of(Organisation.class)
				.filter("self.organisationUrl = :organisationUrl")
				.bind("organisationUrl", organisationUrl)
				.fetchOne();
	}

}

