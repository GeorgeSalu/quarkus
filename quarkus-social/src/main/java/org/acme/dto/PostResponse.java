package org.acme.dto;

import java.time.LocalDateTime;

import org.acme.model.Post;

public class PostResponse {

	private String text;
	private LocalDateTime dataTime;
	
	public static PostResponse fromEntity(Post post) {
		var response = new PostResponse();
		response.setText(post.getText());
		response.setDataTime(post.getDataTime());
		return response;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public LocalDateTime getDataTime() {
		return dataTime;
	}

	public void setDataTime(LocalDateTime dataTime) {
		this.dataTime = dataTime;
	}

}
