package com.pci.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the TR_SALES_DETAIL database table.
 * 
 */
@Entity
@Table(name="TR_SALES_DETAIL")
@NamedQuery(name="TrSalesDetail.findAll", query="SELECT t FROM TrSalesDetail t")
public class TrSalesDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TR_SALES_DETAILID_GENERATOR", sequenceName="TR_SALES_DETAIL_ID_SEQ",allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TR_SALES_DETAILID_GENERATOR")
	@Column(name="DETAIL_ID")
	private long detailId;

	private int quantity;

	@Column(name="SALES_PRICE")
	private int salesPrice;

	//bi-directional many-to-one association to MtItem
	@ManyToOne
	@JoinColumn(name="ITEM_CODE")
	private MtItem mtItem;

	//bi-directional many-to-one association to TrSalesOutline
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="SALES_ID")
	private TrSalesOutline trSalesOutline;

	public TrSalesDetail() {
	}

	public TrSalesDetail(int quantity, int salesPrice, MtItem mtItem) {
		this.quantity = quantity;
		this.salesPrice = salesPrice;
		this.mtItem = mtItem;
	}



	public long getDetailId() {
		return this.detailId;
	}

	public void setDetailId(long detailId) {
		this.detailId = detailId;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getSalesPrice() {
		return this.salesPrice;
	}

	public void setSalesPrice(int salesPrice) {
		this.salesPrice = salesPrice;
	}

	public MtItem getMtItem() {
		return this.mtItem;
	}

	public void setMtItem(MtItem mtItem) {
		this.mtItem = mtItem;
	}

	public TrSalesOutline getTrSalesOutline() {
		return this.trSalesOutline;
	}

	public void setTrSalesOutline(TrSalesOutline trSalesOutline) {
		this.trSalesOutline = trSalesOutline;
	}

}