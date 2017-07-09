package com.minipro.recommend;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import com.sun.org.apache.xerces.internal.impl.dv.xs.FloatDV;

import Jama.Matrix;

/**
 * 
 * @author fangqian
 * 推荐用户以及英雄
 * 输入：当前用户A，后台用户N，kbest           喜欢A的用户list（后台完成）,
 * 超参：1.游戏次数   2.推荐threshold
 * 欧式距离->马氏距离 使用反馈
 * 后台完成：
 * 1.每隔一段时间重新计算 2.推荐列表为空，重新计算
 */
public class UserBaseRecommend {

	/**
	 * @param args
	 */
	//user
	final static int K = 10;
	//hero
	final static int T = 3;
	final static float W = 0.7f;
	class UserScore implements Comparable<UserScore>
	{
		//top small 
		int id;
		double score;
		public UserScore(int id, double score) {
			// TODO Auto-generated constructor stub
			this.id = id;
			this.score = score;
		}
		@Override
		public int compareTo(UserScore obj) {
			// TODO Auto-generated method stub
			return (obj.score) < (this.score) ? 1 : -1;
		}
	}
	class ItemScore implements Comparable<ItemScore>
	{
		//top larger
		int id;
		double score;
		public ItemScore(int id, double score) {
			// TODO Auto-generated constructor stub
			this.id = id;
			this.score = score;
		}
		@Override
		public int compareTo(ItemScore obj) {
			// TODO Auto-generated method stub
			return (obj.score) > (this.score) ? 1 : -1;
		}
	}
	public UserBaseRecommend(){
		
	}
	public  List<Integer> recommendHeros(List<UserScore> kBestUserIds, 
			User[] candidates, double[] userHaveHeros) {
		// TODO Auto-generated method stub
		List<Integer> TBestHeroIds = new ArrayList<Integer>();
		PriorityQueue<ItemScore> pq = new PriorityQueue<UserBaseRecommend.ItemScore>();
		for(int heroid =0;heroid<userHaveHeros.length;heroid++)
		{
			if(userHaveHeros[heroid] == 0)
			{
				float score = 0.0f;
				for(int index =0; index < kBestUserIds.size(); index++)
				{
					int userid = kBestUserIds.get(index).id;
					score += (kBestUserIds.get(index).score * candidates[userid].categoryFeatures[heroid]);
				}
				pq.add(new ItemScore(heroid, score));
			}
		}
		int itemLen = 0;
		while(itemLen < T && !pq.isEmpty())
		{
			TBestHeroIds.add(pq.poll().id);
			itemLen++;
		}
		return TBestHeroIds;
	}
	private double getVariantEuclideanDis(Matrix m1, Matrix m2, Matrix weights)
	{
		
		return weights.arrayTimes(m1.minus(m2)).times(m1.minus(m2).transpose()).get(0, 0);
	}
	public  List<UserScore> recommendUsers(Matrix usersInfosMatrix, Matrix weights) {
		// TODO Auto-generated method stub
		int userRow = usersInfosMatrix.getRowDimension();
		int userCol = usersInfosMatrix.getColumnDimension();
		
		List<UserScore> kBestUserIds = new ArrayList<UserScore>();
		Matrix curMatrix = usersInfosMatrix.getMatrix(0, 0, 0, userCol - 1);
		PriorityQueue<UserScore> pq = new PriorityQueue<UserBaseRecommend.UserScore>();
		for(int i = 1; i < userRow; i++)
		{
			Matrix candMatrix = usersInfosMatrix.getMatrix(i, i, 0, userCol - 1);
			double euclideanDis = getVariantEuclideanDis(curMatrix,candMatrix,weights);
			UserScore item = new UserBaseRecommend.UserScore(i - 1, euclideanDis);
			pq.add(item);
		}
		int userLen = 0;
		while(userLen < K && !pq.isEmpty())
		{
			kBestUserIds.add(pq.poll());
//			System.out.println(kBestUserIds.get(userLen).id + kBestUserIds.get(userLen).score);
			userLen++;
		}
		return kBestUserIds;
	}
	public  float getCosDistance(float[] vec1, float[] vec2,float vec1Mod)
	{
		float dotValue = 0.0f;
		float vec2Mod = 0.0f;
		for(int i = 0; i < vec1.length; i++)
		{
			dotValue += vec1[i] * vec2[i];
			vec2Mod += vec2[i] * vec2[i];
		}
		vec2Mod = (float) Math.sqrt(vec2Mod);
		
		return dotValue / (vec1Mod * vec2Mod);
	}
	public  void normalized(Matrix matrix)
	{
		//max-min normalized
		int m = matrix.getRowDimension(), n = matrix.getColumnDimension();
		for(int i = 0; i<n;i++)
		{
			double max = Float.MIN_VALUE,min = Float.MAX_VALUE;
			for(int j = 0; j < m; j++)
			{
				max = max > matrix.get(j, i) ? max : matrix.get(j, i);
				min = min < matrix.get(j, i) ? min : matrix.get(j, i);
			}
			for(int j = 0; j < m; j++)
			{
				matrix.set(j, i, (matrix.get(j, i) - min) / (max - min));
			}
		}
	}
	
	public Matrix getUserCurWeights(User user) {
		// TODO Auto-generated method stub
		int len = user.getFeatureLen();
		int categoryFeatureLen = user.getCategoryFeatureLen();
		Matrix weights = new Matrix(1, len, 1);
		for(int categoryFeatureId = 0; categoryFeatureId < categoryFeatureLen; categoryFeatureId++)
		{
			float per1 = 0.0f, per2 = 0.0f;
			int count = 0;
			for(int i = 0; i < user.candsAccpeted.size(); i++)
			{
				if(user.candsAccpeted.get(i).categoryFeatures[categoryFeatureId] == 1)
				{
					count++;
				}
			}
			per1 = (float) (count*1.0 / user.candsAccpeted.size());
			count = 0;
			for(int i = 0; i < user.candsRejected.size(); i++)
			{
				if(user.candsRejected.get(i).categoryFeatures[categoryFeatureId] == 1)
				{
					count++;
				}
			}
			per2 = (float) (count*1.0 / user.candsRejected.size());
			//用户拥有该属性、
//			System.out.println(per1 + "," + per2);
			double value = Math.log10(1 + Math.abs(per1 - per2)*10);
//			System.out.println(1 + Math.abs(per1 - per2)*10);
//			System.out.println("value:"+value);
			if(user.categoryFeatures[categoryFeatureId] == 1 && per1 > per2)
			{
				value = 1 + value;
			}
			if(user.categoryFeatures[categoryFeatureId] == 1 && per1 < per2)
			{
				value = 1 - value;
			}
			if(user.categoryFeatures[categoryFeatureId] == 0 && per1 > per2)
			{
				value = 1 - value;
			}
			if(user.categoryFeatures[categoryFeatureId] == 0 && per1 < per2)
			{
				value = 1 + value;
			}
//			System.out.println(value);
			weights.set( 0, categoryFeatureId,value);
		}
		return weights;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		UserBaseRecommend ub = new UserBaseRecommend();

	}
}
