package com.example.domain;

public class Settlement {

	/** 決済Id */
	private Integer id;
	/** 決済 */
	private String settlementName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSettlementName() {
		return settlementName;
	}

	public void setSettlementName(String settlementName) {
		this.settlementName = settlementName;
	}

	@Override
	public String toString() {
		return "Settlement [id=" + id + ", settlementName=" + settlementName + "]";
	}

}
