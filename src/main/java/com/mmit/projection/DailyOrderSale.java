package com.mmit.projection;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface DailyOrderSale {

	//Note getter method name have to match the column name return from database if nativeQuery is set to true
	LocalDate getOrder_date();
	long getDaily_sales();
}
