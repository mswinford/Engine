package com.MyJogl.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {
<<<<<<< master
=======
	/**
	 * IMPORTANT NOTE:
	 * Consecutive calls of the writeToLog method are not guaranteed to appear one after the other in the log file.
	 * This is because of the multi-threaded nature of JOGL.
	 * To avoid issues, consecutive calls should be concatenated to one string before writing, preferably using a StringBuffer for long messages.
	 * 
	 * */
	
>>>>>>> a4246be Big update. Changes to testing. Began Terrain implementation.
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
	
<<<<<<< master
	public static void writeToLog(String line) {		
=======
	public static void writeToLog(String category, Object o) {
		String line = o.toString();
>>>>>>> a4246be Big update. Changes to testing. Began Terrain implementation.
		try {
			bw.write(category + ": ");
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
	
	public static void writeToLog(Object o) {
		writeToLog("Info", o);
	}
	
	
}
