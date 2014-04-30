package edu.sjsu.cmpe.cache.client;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;


public class ConsistentHash<T> {




	private final HashFunction hashFunction;
	private final SortedMap<Integer,T> circle = new TreeMap<Integer,T>();

	public ConsistentHash (HashFunction hashFunction , Collection<T> nodes){
		this.hashFunction = hashFunction;
		for (T node : nodes){
			add(node);
		}
	}


	public void add (T node){
		//System.out.println("node " + node + "node to string" + node.toString());
		//System.out.println("hash function" + hashFunction.hashString(node.toString()));

		circle.put(hashFunction.hashString(node.toString()).asInt(), node);

	}

	public void remove (T node){

		circle.remove(hashFunction.hashString(node.toString()));

	}
	public T get(Object key) {
		if (circle.isEmpty()) {
			return null;
		}
		int hash =  hashFunction.hashString( key.toString()).asInt();
		if (!circle.containsKey(hash)) {
			SortedMap<Integer, T> tailMap =circle.tailMap(hash);
			hash = tailMap.isEmpty() ?circle.firstKey() : tailMap.firstKey();
		}
		return circle.get(hash);
	} 



}
