package com.chuckanutbay.webapp.common.shared;

import java.io.Serializable;

import com.google.common.base.Objects;

public class InventoryLotStickerColorDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public Integer id;
	public String name;
	
	/**
	 * @see "http://code.google.com/webtoolkit/doc/latest/tutorial/RPC.html#serialize"
	 */
	public InventoryLotStickerColorDto(){}

	public InventoryLotStickerColorDto(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public int hashCode(){
		return Objects.hashCode(super.hashCode(), name);
	}

	@Override
	public boolean equals(Object object){
		if (object instanceof InventoryLotStickerColorDto) {
			InventoryLotStickerColorDto that = (InventoryLotStickerColorDto)object;
			if (Objects.equal(this.name, that.name)) {
				return true;
			}
		}
		return false;
	}
}
