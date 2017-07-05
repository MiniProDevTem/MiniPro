package com.minipro.recommend;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * 
 * @author fangqian
 * 推荐用户以及英雄
 * 输入：当前用户A，后台用户N，kbest           喜欢A的用户list（后台完成）,
 * 超参：1.游戏次数   2.推荐threshold
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
	static class ItemScore implements Comparable<ItemScore>
	{
		int id;
		float score;
//		float countSim;
//		float percentSim;
		public ItemScore(int id, float score) {
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
	public static Pair<List<Integer>, List<Integer>> recommend(
			int[] userHaveHeros,
			float[] userCountOfUseHeros,
			float[] userPercentOfWinHeros,
			float[][] candidateCountOfUseHeros,
			float[][] candidatePercentOfWinHeros)
	{
//		Test.matrixShow(candidateCountOfUseHeros);
//		Test.matrixShow(candidatePercentOfWinHeros);
		normalized(candidateCountOfUseHeros);
		normalized(candidatePercentOfWinHeros);
		
//		Test.matrixShow(candidateCountOfUseHeros);
//		Test.matrixShow(candidatePercentOfWinHeros);
		List<ItemScore> kBestUserIds= recommendUsers(userCountOfUseHeros,userPercentOfWinHeros,candidateCountOfUseHeros,candidatePercentOfWinHeros);
		List<Integer> TBestHeroIds= recommendHeros(kBestUserIds,candidateCountOfUseHeros,userHaveHeros);
		List<Integer> kBestUser = new ArrayList<Integer>();
		for(ItemScore item : kBestUserIds)
		{
			System.out.println(item.score);
			System.out.println(item.id);
			kBestUser.add(item.id);
		}
		Pair<List<Integer>, List<Integer>> result = new Pair<List<Integer>, List<Integer>>(kBestUser,TBestHeroIds);
		return result;
	}
	private static List<Integer> recommendHeros(List<ItemScore> kBestUserIds, 
			float[][] candidateCountOfUseHeros, int[] userHaveHeros) {
		// TODO Auto-generated method stub
		List<Integer> TBestHeroIds = new ArrayList<Integer>();
		PriorityQueue<ItemScore> pq = new PriorityQueue<UserBaseRecommend.ItemScore>();
		for(int heroid =0;heroid<userHaveHeros.length;heroid++)
		{
			//meiyou shiyong yingxiong
			if(userHaveHeros[heroid] == 0)
			{
				float score = 0.0f;
				for(int index =0; index < kBestUserIds.size(); index++)
				{
					int userid = kBestUserIds.get(index).id;
					score += (kBestUserIds.get(index).score * candidateCountOfUseHeros[userid][heroid]);
				}
				pq.add(new ItemScore(heroid, score));
			}
		}
		//
		System.out.println("pq:"+pq.size());
		
		for(int i = 0; i < Math.min(pq.size(), T);i++)
		{
			TBestHeroIds.add(pq.poll().id);
		}
		return TBestHeroIds;
	}
	private static List<ItemScore> recommendUsers(float[] userCountOfUseHeros,
			float[] userPercentOfWinHeros,
			float[][] candidateCountOfUseHeros, 
			float[][] candidatePercentOfWinHeros) {
		// TODO Auto-generated method stub
		assert(userCountOfUseHeros.length == userPercentOfWinHeros.length);
		assert(candidateCountOfUseHeros.length == candidatePercentOfWinHeros.length);
		List<ItemScore> kBestUserIds = new ArrayList<ItemScore>();
		float vecMod1 = 0.0f,vecMod2 = 0.0f;
		for(int i =0; i < userCountOfUseHeros.length; i++)
		{
			vecMod1 += userCountOfUseHeros[i] * userCountOfUseHeros[i];
			vecMod2 += userPercentOfWinHeros[i] * userPercentOfWinHeros[i];
		}
		vecMod1 = (float) Math.sqrt(vecMod1);
		vecMod2 = (float) Math.sqrt(vecMod2);
		PriorityQueue<ItemScore> pq = new PriorityQueue<UserBaseRecommend.ItemScore>();
		for(int i =0; i < candidateCountOfUseHeros.length; i++)
		{
			float sim1 = getCosDistance(userCountOfUseHeros, candidateCountOfUseHeros[i], vecMod1);
			float sim2 = getCosDistance(userPercentOfWinHeros, candidatePercentOfWinHeros[i], vecMod2);
			float score = sim1 * W + sim2 * (1 - W);
			ItemScore u = new UserBaseRecommend.ItemScore(i, score);
			pq.add(u);
		}
		int len = Math.min(pq.size(), K);
		for(int i = 0; i < len;i++)
		{
			kBestUserIds.add(pq.poll());
		}
		return kBestUserIds;
	}
	public static float getCosDistance(float[] vec1, float[] vec2,float vec1Mod)
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
	public static void normalized(float[][] matrix)
	{
		//max-min normalized
		int m = matrix.length, n = matrix[0].length;
		for(int i = 0; i<n;i++)
		{
			float max = Float.MIN_VALUE,min = Float.MAX_VALUE;
			for(int j = 0; j < m; j++)
			{
				max = max > matrix[j][i] ? max : matrix[j][i];
				min = min < matrix[j][i] ? min : matrix[j][i];
			}
			for(int j = 0; j < m; j++)
			{
				matrix[j][i] = (matrix[j][i] - min) / (max - min); 
			}
		}
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
