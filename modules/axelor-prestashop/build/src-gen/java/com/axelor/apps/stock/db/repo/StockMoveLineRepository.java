package com.axelor.apps.stock.db.repo;

import java.time.LocalDate;

import com.axelor.apps.base.db.Company;
import com.axelor.apps.base.db.Country;
import com.axelor.apps.sale.db.SaleOrder;
import com.axelor.apps.stock.db.StockMoveLine;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class StockMoveLineRepository extends JpaRepository<StockMoveLine> {

	public StockMoveLineRepository() {
		super(StockMoveLine.class);
	}

	public StockMoveLine findByName(String name) {
		return Query.of(StockMoveLine.class)
				.filter("self.name = :name")
				.bind("name", name)
				.fetchOne();
	}

	public Query<StockMoveLine> findAllBySaleOrder(SaleOrder saleOrder) {
		return Query.of(StockMoveLine.class)
				.filter("self.stockMove.originTypeSelect LIKE 'com.axelor.apps.sale.db.SaleOrder' AND self.stockMove.originId = :saleOrder.id")
				.bind("saleOrder", saleOrder);
	}

	public Query<StockMoveLine> findAllBySaleOrderAndStatusSelect(SaleOrder saleOrder, Integer statusSelect) {
		return Query.of(StockMoveLine.class)
				.filter("self.stockMove.originTypeSelect LIKE 'com.axelor.apps.sale.db.SaleOrder' AND self.stockMove.originId = :saleOrder.id AND self.stockMove.statusSelect = :statusSelect")
				.bind("saleOrder", saleOrder)
				.bind("statusSelect", statusSelect);
	}

	public Query<StockMoveLine> findForDeclarationOfExchanges(LocalDate fromDate, LocalDate toDate, String productTypeSelect, int stockMoveTypeSelect, Country country, Company company) {
		return Query.of(StockMoveLine.class)
				.filter("self.stockMove.realDate BETWEEN :fromDate AND :toDate		  AND self.product.usedInDEB is true		  AND self.product.productTypeSelect = :productTypeSelect		  AND self.stockMove.typeSelect = :stockMoveTypeSelect		  AND self.stockMove.statusSelect = 3		  AND self.stockMove.company = :company		  AND (COALESCE(self.stockMove.fromAddress.addressL7Country, self.stockMove.fromStockLocation.address.addressL7Country) = :country		  OR COALESCE(self.stockMove.toAddress.addressL7Country, self.stockMove.toStockLocation.address.addressL7Country) = :country)		  AND COALESCE(self.stockMove.fromAddress.addressL7Country, self.stockMove.fromStockLocation.address.addressL7Country)		  != COALESCE(self.stockMove.toAddress.addressL7Country, self.stockMove.toStockLocation.address.addressL7Country)		  AND COALESCE(self.stockMove.fromAddress.addressL7Country.economicArea, self.stockMove.fromStockLocation.address.addressL7Country.economicArea)		  = COALESCE(self.stockMove.toAddress.addressL7Country.economicArea, self.stockMove.toStockLocation.address.addressL7Country.economicArea)")
				.bind("fromDate", fromDate)
				.bind("toDate", toDate)
				.bind("productTypeSelect", productTypeSelect)
				.bind("stockMoveTypeSelect", stockMoveTypeSelect)
				.bind("country", country)
				.bind("company", company)
				.order("stockMove.realDate")
				.order("id");
	}

	// CONFORMITY SELECT
	public static final int CONFORMITY_NONE = 1;
	public static final int CONFORMITY_COMPLIANT = 2;
	public static final int CONFORMITY_NON_COMPLIANT = 3;

	public static final int TYPE_NORMAL = 0;
	public static final int TYPE_TITLE = 1;

	// AVAILABLE STATUS SELECT
	public static final int STATUS_AVAILABLE = 1;
	public static final int STATUS_AVAILABLE_FOR_PRODUCT = 2;
	public static final int STATUS_MISSING = 3;
}

