package com.minipro.recommend;

import java.util.ArrayList;
import java.util.List;

import com.minipro.recommend.UserBaseRecommend.UserScore;

import Jama.Matrix;

public class Recommend {
	public static Pair<List<Integer>, List<Integer>> recommend(
			User user,
			User[] candidates)
	{
		
		int row = candidates.length + 1;
		int col = user.getFeatureLen();
		//第一行为用户向量
		Matrix usersInfosMatrix = new Matrix(row, col);
		Matrix mm = new Matrix(user.mergeInfo(), 1);
		usersInfosMatrix.setMatrix(0, 0, 0, col - 1, new Matrix(user.mergeInfo(), 1));
		for(int i = 1; i <= candidates.length; i++)
		{
			usersInfosMatrix.setMatrix(i, i, 0, col - 1, new Matrix(candidates[i - 1].mergeInfo(), 1));
		}
		UserBaseRecommend usrBasedRecommend = new UserBaseRecommend();
		usrBasedRecommend.normalized(usersInfosMatrix);
		Matrix weights = usrBasedRecommend.getUserCurWeights(user);
		List<UserScore> kBestUserIds= usrBasedRecommend.recommendUsers(usersInfosMatrix,weights);
		List<Integer> TBestHeroIds= usrBasedRecommend.recommendHeros(kBestUserIds,candidates,user.categoryFeatures);
		List<Integer> kBestUser = new ArrayList<Integer>();
		for(UserScore item : kBestUserIds)
		{
			kBestUser.add(item.id);
		}
		Pair<List<Integer>, List<Integer>> result = new Pair<List<Integer>, List<Integer>>(kBestUser,TBestHeroIds);
		return result;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
	}

}
