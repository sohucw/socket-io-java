package com.dinwiddy.socketiodemo.domain;

import java.util.Random;

public final class TestEvent {

	private final int i;				// Some number
	private final String msg;			// Some text
	private final long dateCreated; 
	private static Random r = new Random();
	
	private TestEvent(int i, String msg, long dateCreated) {
		this.i = i;
		this.msg = msg;
		this.dateCreated = dateCreated;
	}
	
	public static TestEvent newRandomEvent() {
		
		int i = r.nextInt(56) + 1;
		TestEvent e = new TestEvent(i, "This is a test event", System.currentTimeMillis());
		return e;
	}

	public long getDateCreated() {
		return dateCreated;
	}

	public String getMsg() {
		return msg;
	}

	public int getI() {
		return i;
	}
	
	public String toString() {
		return String.format("[TestEvent i=%d]", i);
	}
}
