package com.chuckanutbay.businessobjects;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "packaging_transactions")
public class PackagingTransaction {
	private Integer id;
	private TrayLabel trayLabel;
	private String labelBarcode;
	private Date dateTime;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tray_label_id", nullable = false)
	public TrayLabel getTrayLabel() {
		return trayLabel;
	}
	public void setTrayLabel(TrayLabel trayLabel) {
		this.trayLabel = trayLabel;
	}

	@Column(name = "label_barcode", nullable = false)
	public String getLabelBarcode() {
		return labelBarcode;
	}
	public void setLabelBarcode(String labelBarcode) {
		this.labelBarcode = labelBarcode;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_time", length = 19)
	public Date getDateTime() {
		return dateTime;
	}
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
}
