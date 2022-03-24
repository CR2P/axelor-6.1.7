package com.axelor.apps.base.db.repo;

import com.axelor.apps.base.db.ProductCategory;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class ProductCategoryRepository extends JpaRepository<ProductCategory> {

	public ProductCategoryRepository() {
		super(ProductCategory.class);
	}

	public ProductCategory findByCode(String code) {
		return Query.of(ProductCategory.class)
				.filter("self.code = :code")
				.bind("code", code)
				.fetchOne();
	}

	public ProductCategory findByName(String name) {
		return Query.of(ProductCategory.class)
				.filter("self.name = :name")
				.bind("name", name)
				.fetchOne();
	}

	public ProductCategory findByPrestaShopId(Integer prestaShopId) {
		return Query.of(ProductCategory.class)
				.filter("self.prestaShopId = :prestaShopId")
				.bind("prestaShopId", prestaShopId)
				.fetchOne();
	}

}

