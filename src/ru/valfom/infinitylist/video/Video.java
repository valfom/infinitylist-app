package ru.valfom.infinitylist.video;

import android.graphics.drawable.Drawable;

public class Video {
	
	public static final String TYPE_YOUTUBE = "youtube";
	public static final String TYPE_VIMEO = "vimeo";

	private String url;
	private String title;
	private String previewUrl;
	private String type;
	private Drawable thumbnail;
	
	public Drawable getThumbnail() {
		
		return thumbnail;
	}

	public void setThumbnail(Drawable thumbnail) {
		
		this.thumbnail = thumbnail;
	}

	public Video() {}

	public Video(String title) {

		this.title = title;
	}

	public Video(String url, String title, String previewUrl, String type) {
		
		this.url = url;
		this.title = title;
		this.previewUrl = previewUrl;
		this.type = type;
	}

	public String getUrl() {
		
		return url;
	}
	
	public void setUrl(String url) {
		
		this.url = url;
	}
	
	public String getTitle() {
		
		return title;
	}
	
	public void setTitle(String title) {
		
		this.title = title;
	}
	
	public String getPreviewUrl() {
		
		return previewUrl;
	}
	
	public void setPreviewUrl(String previewUrl) {
		
		this.previewUrl = previewUrl;
	}
	
	public String getType() {
		
		return type;
	}
	
	public void setType(String type) {
		
		this.type = type;
	}
}
