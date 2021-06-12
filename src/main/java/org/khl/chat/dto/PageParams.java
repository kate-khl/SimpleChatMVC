package org.khl.chat.dto;

public class PageParams {
	
	private int page;
	private int size;

	public PageParams(int page, int size) {
		super();
		this.page = page;
		this.size = size;
	}
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}

}
