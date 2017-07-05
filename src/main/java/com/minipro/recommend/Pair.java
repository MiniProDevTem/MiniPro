package com.minipro.recommend;

public class Pair <T,P> {
	T first;
	P second;
	public Pair() {
		// TODO Auto-generated constructor stub
	}
	public Pair(T first, P second) {
		// TODO Auto-generated constructor stub
		this.first = first;
		this.second = second;
	}
	public T getFirst() {
		return first;
	}
	public void setFirst(T first) {
		this.first = first;
	}
	public P getSecond() {
		return second;
	}
	public void setSecond(P second) {
		this.second = second;
	}
	
}
