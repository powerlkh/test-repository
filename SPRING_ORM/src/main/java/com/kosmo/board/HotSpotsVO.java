package com.kosmo.board;

import java.util.ArrayList;
import java.util.HashMap;

public class HotSpotsVO {

	private HashMap<String, Object> result;
	private String internationalPhoneNumber;
	private String weekdayText;
	private ArrayList<String> photoReference;
	private ArrayList<String> reviewsList;


	private ArrayList<String> pictureList;


	public HashMap<String, Object> getResult() {
		return result;
	}

	public void setResult(HashMap<String, Object> result) {
		this.result = result;
	}

	public String getInternationalPhoneNumber() {
		return internationalPhoneNumber;
	}

	public void setInternationalPhoneNumber(String internationalPhoneNumber) {
		this.internationalPhoneNumber = internationalPhoneNumber;
	}

	public String getWeekdayText() {
		return weekdayText;
	}

	public void setWeekdayText(String weekdayText) {
		this.weekdayText = weekdayText;
	}

	public ArrayList<String> getPhotoReference() {
		return photoReference;
	}

	public void setPhotoReference(ArrayList<String> photoReference) {
		this.photoReference = photoReference;
	}

	public ArrayList<String> getReviewsList() {
		return reviewsList;
	}

	public void setReviewsList(ArrayList<String> reviewsList) {
		this.reviewsList = reviewsList;
	}

	public ArrayList<String> getPictureList() {
		return pictureList;
	}

	public void setPictureList(ArrayList<String> pictureList) {
		this.pictureList = pictureList;
	}



}
