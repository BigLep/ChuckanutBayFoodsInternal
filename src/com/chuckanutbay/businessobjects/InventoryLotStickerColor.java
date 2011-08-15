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

import com.google.common.base.Objects;

@Entity
@Table(name = "inventory_lot_sticker_colors")
public class InventoryLotStickerColor {
	
	private Integer id;
	private String name;
	private Set<InventoryLot> inventoryLots;
	
	public InventoryLotStickerColor() {}
	
	public InventoryLotStickerColor(String name) {
		this.name = name;
	}
	
	public InventoryLotStickerColor(
			Integer id,
			String name,
			Set<InventoryLot> inventoryLots) {
		super();
		this.id = id;
		this.name = name;
		this.inventoryLots = inventoryLots;
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
	
	@Column(name = "name", nullable = false, length = 20)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "inventoryLotStickerColor")
	public Set<InventoryLot> getInventoryLots() {
		return inventoryLots;
	}
	public void setInventoryLots(
			Set<InventoryLot> inventoryLots) {
		this.inventoryLots = inventoryLots;
	}
	
	@Override
	public int hashCode(){
		return Objects.hashCode(super.hashCode(), name);
	}

	@Override
	public boolean equals(Object object){
		if (object instanceof InventoryLotStickerColor) {
			InventoryLotStickerColor that = (InventoryLotStickerColor)object;
			if (Objects.equal(this.name, that.name)) {
				return true;
			}
		}
		return false;
	}
}
