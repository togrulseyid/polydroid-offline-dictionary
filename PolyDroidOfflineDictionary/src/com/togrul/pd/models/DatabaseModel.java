package com.togrul.pd.models;

public class DatabaseModel {

	private String id;
	private String size;//
	private String name; //
	private String dbName;
//	private boolean download; // new pinnerAdapter(activity).isDownloaded(Stringeger.valueOf(XMLfunctions.getValue(e, "id")))+ "")
	private boolean isdownload = false; //

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbname(String dbName) {
		this.dbName = dbName;
	}

//	public String getDownload() {
//		return download;
//	}
//
//	public void setDownload(String download) {
//		this.download = download;
//	}

	public boolean getIsDownload() {
		return isdownload;
	}

	public void setIsDownload(boolean isdownload) {
		this.isdownload = isdownload;
	}
	
	@Override
	public String toString() {
		return "DatabaseModel [id=" + id + ", size=" + size + ", name=" + name
				+ ", dbName=" + dbName /*+ ", download=" + download*/
				+ ", isdownload=" + isdownload + "]";
	}
}