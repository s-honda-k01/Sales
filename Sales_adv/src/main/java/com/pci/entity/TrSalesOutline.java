package com.pci.entity;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the TR_SALES_OUTLINE database table.
 * 
 */
@Entity
@Table(name="TR_SALES_OUTLINE")
@NamedQuery(name="TrSalesOutline.findAll", query="SELECT t FROM TrSalesOutline t")
public class TrSalesOutline implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TR_SALES_O_SALESID_GENERATOR", sequenceName="TR_SALES_O_SALES_ID_SEQ",allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TR_SALES_O_SALESID_GENERATOR")
	@Column(name="SALES_ID")
	private long salesId;

	@Temporal(TemporalType.DATE)
	@Column(name="SALE_DATE")
	private Date saleDate;

	//bi-directional many-to-one association to TrSalesDetail
	@OneToMany(mappedBy="trSalesOutline",cascade = CascadeType.ALL,fetch = FetchType.EAGER,orphanRemoval = true)
	private List<TrSalesDetail> trSalesDetails = new ArrayList<TrSalesDetail>();

	//bi-directional many-to-one association to MtCustomer
	@ManyToOne
	@JoinColumn(name="CUSTOMER_CODE")
	private MtCustomer mtCustomer;

	//bi-directional many-to-one association to MtUser
	@ManyToOne
	@JoinColumn(name="USER_CODE")
	private MtUser mtUser;

	@Transient
	@NotNull
	private String salesDateString;
	
	@Transient
	private String[] itemCodeArray;
	
	@Transient
	private String[] quantityArray;
	
	public TrSalesOutline() {
	}

	public long getSalesId() {
		return this.salesId;
	}

	public void setSalesId(long salesId) {
		this.salesId = salesId;
	}

	public Date getSaleDate() {
		return this.saleDate;
	}

	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}

	public List<TrSalesDetail> getTrSalesDetails() {
		return this.trSalesDetails;
	}

	public void setTrSalesDetails(List<TrSalesDetail> trSalesDetails) {
		this.trSalesDetails = trSalesDetails;
	}

	public TrSalesDetail addTrSalesDetail(TrSalesDetail trSalesDetail) {
		getTrSalesDetails().add(trSalesDetail);
		trSalesDetail.setTrSalesOutline(this);

		return trSalesDetail;
	}

	public TrSalesDetail removeTrSalesDetail(TrSalesDetail trSalesDetail) {
		getTrSalesDetails().remove(trSalesDetail);
		trSalesDetail.setTrSalesOutline(null);

		return trSalesDetail;
	}

	public MtCustomer getMtCustomer() {
		return this.mtCustomer;
	}

	public void setMtCustomer(MtCustomer mtCustomer) {
		this.mtCustomer = mtCustomer;
	}

	public MtUser getMtUser() {
		return this.mtUser;
	}

	public void setMtUser(MtUser mtUser) {
		this.mtUser = mtUser;
	}

	public String getSalesDateString() {
		return salesDateString;
	}

	public void setSalesDateString(String salesDateString) {
		this.salesDateString = salesDateString;
	}

	public String[] getItemCodeArray() {
		return itemCodeArray;
	}

	public void setItemCodeArray(String[] itemCodeArray) {
		this.itemCodeArray = itemCodeArray;
	}

	public String[] getQuantityArray() {
		return quantityArray;
	}

	public void setQuantityArray(String[] quantityArray) {
		this.quantityArray = quantityArray;
	}
}