package com.mmit.model.repos;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mmit.model.entity.Orders;
import com.mmit.projection.DailyOrderSale;
import com.mmit.projection.MonthlyOrderSale;

public interface OrderRepo extends JpaRepository<Orders, Long> {
	
	@Query(value = "SELECT Date(created_at) as order_date, sum(amount) as daily_sales FROM `orders` Where status != 'cancelled' GROUP By Date(created_at) ORDER By Date(created_at) DESC LIMIT 5", nativeQuery = true)
	List<DailyOrderSale> findDailyOrderSalesInLastFiveDays();
	
	@Query(value = "SELECT year(created_at) as year, month(created_at) as month, sum(amount) as monthly_sales From orders Where status != 'cancelled' GROUP By year(created_at), month(created_at) Order By year(created_at), month(created_at) DESC LIMIT 1", nativeQuery = true)
	MonthlyOrderSale findLatestMonthOrderSales();

	@Query(value = "SELECT COUNT(*) FROM orders  WHERE status = 'pending'", nativeQuery = true)
	long findPendingOrderCount();
	
	@Query(value = "SELECT * FROM `orders` WHERE status != 'received' And status != 'cancelled' And customer_id = :customerID", nativeQuery = true)
	List<Orders> findOrderByPendingAndDeliveredStatus(@Param("customerID") int customerID);

	@Query("SELECT o FROM Orders o WHERE o.customer.id = :id")
	List<Orders> findByCustomerId(@Param("id") int id);
}
