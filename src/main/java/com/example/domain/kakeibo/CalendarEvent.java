package com.example.domain.kakeibo;

public class CalendarEvent {

	private String title;
	private String start;
	private String end;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	@Override
	public String toString() {
		return "CalendarEvent [title=" + title + ", start=" + start + ", end=" + end + "]";
	}

}
