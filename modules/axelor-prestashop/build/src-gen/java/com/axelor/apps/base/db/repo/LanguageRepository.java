package com.axelor.apps.base.db.repo;

import com.axelor.apps.base.db.Language;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class LanguageRepository extends JpaRepository<Language> {

	public LanguageRepository() {
		super(Language.class);
	}

	public Language findByCode(String code) {
		return Query.of(Language.class)
				.filter("self.code = :code")
				.bind("code", code)
				.fetchOne();
	}

	public Language findByName(String name) {
		return Query.of(Language.class)
				.filter("self.name = :name")
				.bind("name", name)
				.fetchOne();
	}

	public Language findByPrestaShopId(Integer prestaShopId) {
		return Query.of(Language.class)
				.filter("self.prestaShopId = :prestaShopId")
				.bind("prestaShopId", prestaShopId)
				.fetchOne();
	}

}

