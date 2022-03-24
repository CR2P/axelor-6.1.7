package com.axelor.exception.db.repo;

import com.axelor.db.JpaRepository;
import com.axelor.exception.db.ExceptionOrigin;

public class ExceptionOriginRepository extends JpaRepository<ExceptionOrigin> {

	public ExceptionOriginRepository() {
		super(ExceptionOrigin.class);
	}

	/**Origin select*/

	public static final String IMPORT = "import";

	public static final String CRM = "crm";
}

