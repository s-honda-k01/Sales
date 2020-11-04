package com.pci.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the MT_USER database table.
 * 
 */
@Entity
@Table(name="MT_USER")
@NamedQuery(name="MtUser.findAll", query="SELECT m FROM MtUser m")
public class MtUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="USER_CODE")
	private String userCode;

	private String password;

	@Column(name="USER_NAME")
	private String userName;

	private boolean validity;
	
	@Transient
	private boolean memoShare;

	//bi-directional many-to-one association to MtRole
	@ManyToOne
	@JoinColumn(name="ROLE_CODE")
	private MtRole mtRole;

	//bi-directional many-to-one association to TrMemo
	@OneToMany(mappedBy="mtUser")
	private List<TrMemo> trMemos;

	//bi-directional many-to-one association to TrMemoShareMember
	@OneToMany(mappedBy="mtUser")
	private List<TrMemoShareMember> trMemoShareMembers;
	
	//bi-directional many-to-one association to TrSalesOutline
	@OneToMany(mappedBy="mtUser")
	private List<TrSalesOutline> trSalesOutlines;

	public MtUser() {
	}
	
	public MtUser(String userCode,String userName, String password, MtRole mtRole, boolean validity) {
		this.userCode = userCode;
		this.userName = userName;
		this.password = password;
		this.mtRole = mtRole;
		this.validity = validity;
	}

	public String getUserCode() {
		return this.userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public boolean getValidity() {
		return this.validity;
	}

	public void setValidity(boolean validity) {
		this.validity = validity;
	}

	public MtRole getMtRole() {
		return this.mtRole;
	}

	public void setMtRole(MtRole mtRole) {
		this.mtRole = mtRole;
	}

	public List<TrMemo> getTrMemos() {
		return this.trMemos;
	}

	public void setTrMemos(List<TrMemo> trMemos) {
		this.trMemos = trMemos;
	}

	public TrMemo addTrMemo(TrMemo trMemo) {
		getTrMemos().add(trMemo);
		trMemo.setMtUser(this);

		return trMemo;
	}

	public TrMemo removeTrMemo(TrMemo trMemo) {
		getTrMemos().remove(trMemo);
		trMemo.setMtUser(null);

		return trMemo;
	}

	public List<TrSalesOutline> getTrSalesOutlines() {
		return this.trSalesOutlines;
	}

	public void setTrSalesOutlines(List<TrSalesOutline> trSalesOutlines) {
		this.trSalesOutlines = trSalesOutlines;
	}

	public TrSalesOutline addTrSalesOutline(TrSalesOutline trSalesOutline) {
		getTrSalesOutlines().add(trSalesOutline);
		trSalesOutline.setMtUser(this);

		return trSalesOutline;
	}

	public TrSalesOutline removeTrSalesOutline(TrSalesOutline trSalesOutline) {
		getTrSalesOutlines().remove(trSalesOutline);
		trSalesOutline.setMtUser(null);

		return trSalesOutline;
	}

	public boolean isMemoShare() {
		return memoShare;
	}

	public void setMemoShare(boolean memoShare) {
		this.memoShare = memoShare;
	}
}