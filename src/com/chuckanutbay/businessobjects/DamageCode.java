package com.chuckanutbay.businessobjects;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "damage_code")
public class DamageCode {
	private Integer id;
	private String code;
	private Set<PackagingTransaction> packagingTransactions;
	
	public DamageCode(){}
	
	public DamageCode(Integer id, String code) {
		super();
		this.id = id;
		this.code = code;
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "code", nullable = false, length = 50)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "damageCode")
	public Set<PackagingTransaction> getPackagingTransactions() {
		return packagingTransactions;
	}

	public void setPackagingTransactions(Set<PackagingTransaction> packagingTransactions) {
		this.packagingTransactions = packagingTransactions;
	}
}
