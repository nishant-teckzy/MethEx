package com.executor.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class MethExProperties {

	private static Byte[] justForLock = new Byte[1];
	private static MethExProperties thisInstance;
	public static final String installationLoc = Constants.installationLocation;
	private static final String platform = Constants.propertyFileName;

	private static Properties props;
	private static boolean isLoaded = false;

	public static MethExProperties getInstance() {
		if (thisInstance == null) {
			synchronized (justForLock) {
				if (thisInstance == null) {
					thisInstance = new MethExProperties();
				}
			}
		}
		return thisInstance;
	}

	private MethExProperties() {
		loadProperties();

	}
	private void loadProperties(){
		InputStream input = null;
		try {
				if (!isLoaded) {
					props = new Properties();
					input = this.getClass().getResourceAsStream("/methex.properties");//new FileInputStream(installationLoc + "/" + platform);
					if ("null".equals(input)) {
						throw new IllegalStateException("Either Property file " + platform
								+ " is missing. It must be at path " + installationLoc);
					}
					props.load(input);
					isLoaded = true;
				}
		
		}catch(FileNotFoundException FileEx){
			System.out.println(FileEx.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
//			e.printStackTrace();
		}  finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public String getMethExProperty(String key) {
		String value = null;
		if (props != null) {
			value = props.getProperty(key);
		}
		return value;
	}
}
