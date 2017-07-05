package com.minipro.criteria;

import java.util.Set;

import com.minipro.service.param.UserServiceParam.RecommendParam;

public class RecommendSearchCriteria extends RecommendParam{
	Set<String> removeSets;

	public Set<String> getRemoveSets() {
		return removeSets;
	}

	public void setRemoveSets(Set<String> removeSets) {
		this.removeSets = removeSets;
	}
	
}
