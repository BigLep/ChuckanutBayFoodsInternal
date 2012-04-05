package com.chuckanutbay.businessobjects;

// Generated Oct 20, 2010 1:24:33 PM by Hibernate Tools 3.4.0.Beta1

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.google.common.base.Objects;

/**
 * InventoryItems generated by hbm2java
 */
@Entity
@Table(name = "inventory_items")
public class InventoryItem implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String description;
	private Set<InventoryLot> inventoryLots = new HashSet<InventoryLot>(0);

	public InventoryItem() {
	}

	public InventoryItem(String id, String description) {
		this.id = id;
		this.description = description;
	}

	public InventoryItem(String id, String description,
			Set<InventoryLot> inventoryLots) {
		this.id = id;
		this.description = description;
		this.inventoryLots = inventoryLots;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "description", nullable = false, length = 1024)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "inventoryItem")
	public Set<InventoryLot> getInventoryLots() {
		return this.inventoryLots;
	}

	public void setInventoryLots(Set<InventoryLot> inventoryLots) {
		this.inventoryLots = inventoryLots;
	}

	@Override
	public int hashCode(){
		return Objects.hashCode(super.hashCode(), description);
	}

	@Override
	public boolean equals(Object object){
		if (object instanceof InventoryItem) {
			InventoryItem that = (InventoryItem)object;
			return Objects.equal(this.description, that.description);
		}
		return false;
	}

}
