package com.MyJogl.cache;

import java.util.Hashtable;

import com.MyJogl.Model.Model;

public class ModelCache {
	private Hashtable<String, Model> cache;
	
	private ModelCache() {
		cache = new Hashtable<String, Model>();
	}
	
	public static ModelCache createCache() {
		return new ModelCache();
	}
	
	private void add(Model model) {
		cache.put(model.getName(), model);
	}
	
	public Model getModel(String name) {
		//returns a model stored in the cache if it shares 
		return cache.get(name);
	}
}
