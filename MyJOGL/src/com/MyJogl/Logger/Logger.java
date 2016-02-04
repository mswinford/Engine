package com.MyJogl.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {
	private static final String DEFAULT_LOG_PATH = "src/Log.txt";
	
	private static File logFile;
	private static BufferedWriter bw;
	
	public static void initilalizeLogger() {
		initilalizeLogger(DEFAULT_LOG_PATH);
	}
	public static void initilalizeLogger(String logPath) {
		try {
			logFile = new File(logPath);
			if(!logFile.exists()) {
				logFile.createNewFile();
			}
			bw = new BufferedWriter(new FileWriter(logFile));
		} catch (IOException e) {
			writeToLog("Log file could not be created!");
			System.out.println(e.getMessage());
		}
	}
	
	public static void writeToLog(String line) {		
		try {
			bw.write(line);
			bw.newLine();
			bw.flush();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} 
	}
	
	public static void dispose() {
		try {
			bw.flush();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				bw.close();
			} catch (IOException e) {
				System.out.println("Log file could not be closed");
				System.out.println(e.getMessage());
			}
		}
	}
	
	private enum Level {
		SEVERE,
		WARNING,
		INFO,
	}
	
	
}
