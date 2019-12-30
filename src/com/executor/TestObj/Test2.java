package com.executor.TestObj;

import java.util.ArrayList;

public class Test2 {

	ArrayList<test3> list = new ArrayList<>();
	
	public Test2(String from, String to, String msg) {
		super();
		list.add(new test3(from,to,msg));
	}
	
	
}
