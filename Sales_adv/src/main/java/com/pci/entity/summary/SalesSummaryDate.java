package com.pci.entity.summary;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SalesSummaryDate {

	@Id
	private Date date;
	private Long total;
	public SalesSummaryDate() {
		super();
	}
	public SalesSummaryDate(Date date, Long total) {
		super();
		this.date = date;
		this.total = total;
	}
	
	public SalesSummaryDate(Object[] objects) {
		this((Date)objects[0],(Long)objects[1]);
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	
	
}
