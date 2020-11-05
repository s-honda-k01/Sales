package com.pci.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pci.entity.TrSalesDetail;

public interface SalesDetailRepository extends JpaRepository<TrSalesDetail, Long> {
	@Query("select t FROM TrSalesDetail t WHERE t.trSalesOutline.salesId= :salesId")
	public List<TrSalesDetail> findBySalesId(@Param("salesId") long salesId);
	
	@Query("select t from TrSalesDetail t where t.trSalesOutline.salesId= :salesId and t.mtItem.itemCode = :itemCode")
	public Optional<TrSalesDetail> findByItemCode(@Param("salesId") long salesId,@Param("itemCode")String itemCode);
	
	@Query("SELECT d.mtItem.itemCode as code, d.mtItem.itemName as name, sum(d.quantity*d.salesPrice) as total "
			+ "FROM TrSalesDetail d "
			+ "GROUP BY d.mtItem.itemCode, d.mtItem.itemName "
			+ "ORDER BY d.mtItem.itemCode")
	public List<Object[]> findBySalesSummaryByItem();

	@Query("SELECT d.mtItem.mtItemGenre.itemGenreCode as code, d.mtItem.mtItemGenre.itemGenreName as name, sum(d.quantity*d.salesPrice) as total "
			+ "FROM TrSalesDetail d "
			+ "GROUP BY d.mtItem.mtItemGenre.itemGenreCode, d.mtItem.mtItemGenre.itemGenreName "
			+ "ORDER BY d.mtItem.mtItemGenre.itemGenreCode")
	public List<Object[]> findBySalesSummaryByItemGenre();

	@Query("SELECT d.trSalesOutline.mtCustomer.customerCode as code, d.trSalesOutline.mtCustomer.customerName as name, sum(d.quantity*d.salesPrice) as total "
			+ "FROM TrSalesDetail d "
			+ "GROUP BY d.trSalesOutline.mtCustomer.customerCode, d.trSalesOutline.mtCustomer.customerName "
			+ "ORDER BY d.trSalesOutline.mtCustomer.customerCode")
	public List<Object[]> findBySalesSummaryByCustomer();

	@Query("SELECT d.trSalesOutline.mtUser.userCode as code, d.trSalesOutline.mtUser.userName as name, sum(d.quantity*d.salesPrice) as total "
			+ "FROM TrSalesDetail d "
			+ "GROUP BY d.trSalesOutline.mtUser.userCode, d.trSalesOutline.mtUser.userName "
			+ "ORDER BY d.trSalesOutline.mtUser.userCode")
	public List<Object[]> findBySalesSummaryByStaff();	

	@Query("SELECT d.trSalesOutline.saleDate as date, sum(d.quantity*d.salesPrice) as total "
			+ "FROM TrSalesDetail d "
			+ "GROUP BY d.trSalesOutline.saleDate "
			+ "ORDER BY d.trSalesOutline.saleDate DESC")
	public List<Object[]> findBySalesSummaryByDate();
	
	@Query("SELECT d.trSalesOutline.saleDate as date, sum(d.quantity*d.salesPrice) as total "
			+ "FROM TrSalesDetail d "
			+ "WHERE d.trSalesOutline.mtUser.userCode = :userCode "
			+ "GROUP BY d.trSalesOutline.saleDate "
			+ "ORDER BY d.trSalesOutline.saleDate DESC")
	public List<Object[]> findBySalesSummaryByDate(@Param("userCode") String userCode);
}
