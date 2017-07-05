/*package com.minipro.recommend;

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
		int userNum = 11;
		float[] userHaveHeros = new float[100];//用户已经拥有的英雄
		float[] userCountOfUseHeros = new float[100];//用户英雄使用的次数
		float[] userPercentOfWinHeros = new float[100];//每一个英雄胜率
		float[][] candidateCountOfUseHeros = new float[userNum][100];
		float[][] candidatePercentOfWinHeros = new float[userNum][100];
		Random random = new Random();
		for(int i = 0 ; i < userNum; i++)
		{
			for(int j =0;j < 100;j++)
			{
				int count = 0;
				float per = 0;
				int bol = random.nextInt(2);
				
				if(bol == 1)
				{
					count = random.nextInt(1000);
					per = (float) (random.nextInt(100)*1.0 / 100);
				}
				candidateCountOfUseHeros[i][j] = count;
				candidatePercentOfWinHeros[i][j] = per;
			}
		}
		for(int j =0;j < 100;j++)
		{
			int count = 0;
			float per = 0;
			int bol = random.nextInt(2);
			if(bol == 1)
			{
				count = random.nextInt(1000);
				per = (float) (random.nextInt(100)*1.0 / 100);
			}
			userCountOfUseHeros[j] = count;
			userPercentOfWinHeros[j] = per;
			userHaveHeros[j] = bol;
		}
		Pair<List<Integer>, List<Integer>> result = UserBaseRecommend.recommend(
				userHaveHeros, 
				userCountOfUseHeros, 
				userPercentOfWinHeros,  
				candidateCountOfUseHeros, 
				candidatePercentOfWinHeros);
		System.out.println(result.first);
		System.out.println(result.second);
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Test.testRecommendAlgorithm();
	}

}
*/