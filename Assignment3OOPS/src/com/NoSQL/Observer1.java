package com.NoSQL;

/**
 * @author Deep Sanghvi
 */

public class Observer1 implements Observer {
		
	private Object value;
	
	@Override
	public Object update(Object value) {
		System.out.println("notify...");
		return this.value=value;
	}
}
