package com.chuckanutbay.webapp.common.shared;

import java.io.Serializable;
import java.util.Date;

/**
 * Contains a start and end {@link Date}.
 */
public class IntervalDto implements Serializable {
	private static final long serialVersionUID = 1L;
	public Date start;
	public Date end;
	
	public IntervalDto() {}
	
	public IntervalDto(Date start, Date end) {
		this.start = start;
		this.end = end;
	}
	
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
}
