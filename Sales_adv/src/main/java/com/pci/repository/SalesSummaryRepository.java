package com.pci.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pci.entity.summary.SalesSummary;

@Repository
public interface SalesSummaryRepository extends JpaRepository<SalesSummary, String> {
	
	@Query("SELECT d.mtItem.itemCode as code, d.mtItem.itemName as name, sum(d.quantity*d.salesPrice) as total "
			+ "FROM TrSalesDetail d "
			+ "GROUP BY d.mtItem.itemCode, d.mtItem.itemName "
			+ "ORDER BY d.mtItem.itemCode")
	public List<Object[]> findBySalesGroupByItem();

	@Query("SELECT d.mtItem.mtItemGenre.itemGenreCode as code, d.mtItem.mtItemGenre.itemGenreName as name, sum(d.quantity*d.salesPrice) as total "
			+ "FROM TrSalesDetail d "
			+ "GROUP BY d.mtItem.mtItemGenre.itemGenreCode, d.mtItem.mtItemGenre.itemGenreName "
			+ "ORDER BY d.mtItem.mtItemGenre.itemGenreCode")
	public List<Object[]> findBySalesGroupByItemGenre();

	@Query("SELECT d.trSalesOutline.mtCustomer.customerCode as code, d.trSalesOutline.mtCustomer.customerName as name, sum(d.quantity*d.salesPrice) as total "
			+ "FROM TrSalesDetail d "
			+ "GROUP BY d.trSalesOutline.mtCustomer.customerCode, d.trSalesOutline.mtCustomer.customerName "
			+ "ORDER BY d.trSalesOutline.mtCustomer.customerCode")
	public List<Object[]> findBySalesGroupByCustomer();

	@Query("SELECT d.trSalesOutline.mtUser.userCode as code, d.trSalesOutline.mtUser.userName as name, sum(d.quantity*d.salesPrice) as total "
			+ "FROM TrSalesDetail d "
			+ "GROUP BY d.trSalesOutline.mtUser.userCode, d.trSalesOutline.mtUser.userName "
			+ "ORDER BY d.trSalesOutline.mtUser.userCode")
	public List<Object[]> findBySalesGroupByStaff();	
	
}
