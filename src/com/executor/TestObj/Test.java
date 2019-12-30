package com.executor.TestObj;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.executor.core.Exec;
import com.executor.execute.Parameters;

public class Test {
	

	List<String> ls = new ArrayList<>();
	Test2 ts = new Test2("", "", "");

	
	{Parameters.set(ts);}
	
	public TestAgain print(Test2 t) {
		return new TestAgain();
	}
	

}
