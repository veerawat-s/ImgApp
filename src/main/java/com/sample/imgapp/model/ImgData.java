package com.sample.imgapp.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sample.imgapp.controller.ImgAppController;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "saved_data")
public class ImgData {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(nullable = false, length = 128)
	private Date date;

//	private Integer thumbSize;
//	private String thumbUrl;

	@Column(nullable = true, length = 512)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String description;

	@Column(nullable = true, length = 128)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String title;
	
	@Column(nullable = false, length = 128)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String imgPath;

	@Transient
    public String getImageUrl() {
        return  String.format("/view/%s", id);
    }

	public String getDate() {
		return ImgAppController.formatter.format(date);
	}

	public void setDate(Date date) {
		this.date = date;
	}

//	public Integer getThumbSize() {
//		return thumbSize;
//	}
//
//	public void setThumbSize(Integer thumbSize) {
//		this.thumbSize = thumbSize;
//	}
//
//	public String getThumbUrl() {
//		return thumbUrl;
//	}
//
//	public void setThumbUrl(String thumbUrl) {
//		this.thumbUrl = thumbUrl;
//	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
