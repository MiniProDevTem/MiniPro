package com.minipro.recommend;

import java.util.ArrayList;
import java.util.List;

public class User {
	public String userId;
	public double[] categoryFeatures;
	public double[] numicFeatures;
	public List<User> candsAccpeted;
	public List<User> candsRejected;
	public User() {
		userId = "";
		categoryFeatures = new double[0];
		numicFeatures = new double[0];
		candsAccpeted = new ArrayList<User>();
		candsRejected = new ArrayList<User>();
	}
	public int getCategoryFeatureLen()
	{
		if(categoryFeatures == null)
		{
			return 0;
		}
		return categoryFeatures.length;
	}
	public int getNumicFeaturesLen()
	{
		if(numicFeatures == null)
		{
			return 0;
		}
		return numicFeatures.length;
	}
	public int getFeatureLen()
	{
		return categoryFeatures.length + numicFeatures.length;
	}
	public double[] mergeInfo()
	{
		int a = categoryFeatures.length;
		int b = numicFeatures.length;
		double[] featureArray = new double[a + b];
		for(int i =0; i < a; i++)
		{
			featureArray[i] = categoryFeatures[i];
		}
		for(int i =0; i < b; i++)
		{
			featureArray[a + i] = numicFeatures[i];
		}
		return featureArray;
	}
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public double[] getCategoryFeatures() {
		return categoryFeatures;
	}
	public void setCategoryFeatures(double[] categoryFeatures) {
		this.categoryFeatures = categoryFeatures;
	}
	public double[] getNumicFeatures() {
		return numicFeatures;
	}
	public void setNumicFeatures(double[] numicFeatures) {
		this.numicFeatures = numicFeatures;
	}
	public List<User> getCandsAccpeted() {
		return candsAccpeted;
	}
	public void setCandsAccpeted(List<User> candsAccpeted) {
		this.candsAccpeted = candsAccpeted;
	}
	public List<User> getCandsRejected() {
		return candsRejected;
	}
	public void setCandsRejected(List<User> candsRejected) {
		this.candsRejected = candsRejected;
	}
	public void setNumicFeatures(int dim, double value)
	{
		if(numicFeatures == null || dim >= numicFeatures.length)
		{
			System.err.println("some thing error in Class User!!");
		}
		numicFeatures[dim] = value;
	}
	public void setCategoryFeatures(int dim, double value)
	{
		if(categoryFeatures == null || dim >= categoryFeatures.length)
		{
			System.err.println("some thing error in Class User!!");
		}
		categoryFeatures[dim] = value;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
