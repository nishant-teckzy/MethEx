package com.executor.TestObj;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;

public class TestAgain {
	String str1 = "";
	String str2 = "";
	ArrayList<List<test3>> al = new ArrayList<>();
	HashMap<String,Test2> hm = new HashMap<>();
	
	
	public TestAgain() {
		super();
		this.str1 = "Year";
		this.str2 = "2019";
		ArrayList<test3> tes = new ArrayList<>();
		tes.add(new test3("Nishant","Life","Your're Genius"));
		al.add(tes);
		
		hm.put("1", new Test2("java","Crazy"," Shit"));
		hm.put("2",new Test2("Node.js","new Kid"," in the world"));
	}
	

	

}
