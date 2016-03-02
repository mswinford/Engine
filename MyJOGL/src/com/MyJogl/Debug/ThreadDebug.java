package com.MyJogl.Debug;

import com.MyJogl.Logger.Logger;

public class ThreadDebug {
	
	public ThreadDebug() {
		
	}
	
	public static void printAllThreads() {
		Thread[] threads = new Thread[Thread.activeCount() + 10];
		Thread.enumerate(threads);
		for(Thread t : threads) {
			if( t != null ) {
				Logger.writeToLog("Debug", t);
			}
		}
	}

}
