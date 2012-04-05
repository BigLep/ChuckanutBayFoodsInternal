package com.chuckanutbay.webapp.common.shared;

import java.io.Serializable;

public class DamageCodeDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String code;
	
	public DamageCodeDto() {}
	
	public DamageCodeDto(Integer id, String code) {
		super();
		this.id = id;
		this.code = code;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
