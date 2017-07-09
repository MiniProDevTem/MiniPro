package com.minipro.recommend;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class Test {
	
	static void matrixShow(float[][] matrix)
	{
		int m = matrix.length, n = matrix[0].length;
		for(int i = 0; i<m;i++)
		{
			for(int j =0;j<n;j++)
			{
				System.out.print(matrix[i][j]+" ");
			}
			System.out.println();
		}
	}
	static void testRecommendAlgorithm()
	{
		
		User[] candidates = new User[100000];
		Random random = new Random();
		for(int i = 0 ; i < candidates.length; i++)
		{
			User cand = new User();
			double[] categoryFeatures = new double[10];
			double[] numicFeatures = new double[10];
			for(int j = 0;j < 10;j++)
			{
				int count = 0;
				float per = 0;
				int bol = random.nextInt(2);
				if(bol == 1)
				{
					count = random.nextInt(100);
					per = (float) (random.nextInt(10)*1.0 / 10);
				}
				categoryFeatures[j] = bol;
				numicFeatures[j] = count;
			}
			cand.setCategoryFeatures(categoryFeatures);
			cand.setNumicFeatures(numicFeatures);
			candidates[i] = cand;
		}
		User user = new User();
		double[] categoryFeatures = new double[10];
		double[] numicFeatures = new double[10];
		for(int j =0;j < 10;j++)
		{
			
			int count = 0;
			float per = 0;
			int bol = random.nextInt(2);
			if(bol == 1)
			{
				count = random.nextInt(100);
				per = (float) (random.nextInt(10)*1.0 / 10);
			}
			categoryFeatures[j] = bol;
			numicFeatures[j] = count;
		}
		user.setCategoryFeatures(categoryFeatures);
		user.setNumicFeatures(numicFeatures);
		List<User> candsAccpeted = new ArrayList<User>();
		for(int i = 0; i < 5; i++)
		{
			candsAccpeted.add(candidates[i]);
		}
		user.setCandsAccpeted(candsAccpeted);
		List<User> candsRejected = new ArrayList<User>();;
		for(int i = 5; i < 8; i++)
		{
			candsRejected.add(candidates[i]);
		}
		user.setCandsRejected(candsRejected);
		Pair<List<Integer>, List<Integer>> result = Recommend.recommend(user, candidates);

		System.out.println(result.first);
		System.out.println(result.second);
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Test.testRecommendAlgorithm();
	}

}
