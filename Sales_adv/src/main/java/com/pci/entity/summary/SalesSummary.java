package com.pci.entity.summary;

//import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SalesSummary{
	//private static final long serialVersionUID = 1L;
	
	@Id
	private String code;
	private String name;
	private Long total;
	
	public SalesSummary() {
	}

	public SalesSummary(String code, String name, Long total) {
		this.code = code;
		this.name = name;
		this.total = total;
	}
	
	public SalesSummary(Object date,Object total) {
		this.code = (String)date;
		this.total = (Long)total;
	}
	public SalesSummary(Object[] objects) {
		this((String)objects[0],(String)objects[1],(Long)objects[2]);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}
}
