package com.joshluisaac.samples;


/**
 * Regardless of how many times the constructor gets initialized, the static block gets called once.
 * Static blocks are good for loading runtime props and configs which only gets called once.
 * For example, initializing a connection to a database or loading a file to application memory 
 * are things that can be wrapped in static blocks.
 * 
 * Output of this program will be
 * 
 * Static block initialized
 * New object constructed...
 * New object constructed...
 * New object constructed...
 * 
 * The constructor got initialized thrice but the static block got executed just once
 */

public class StaticBlocks {
	
	private StaticBlocks(){
		System.out.println("New object constructed...");
	}
	
	static {
		System.out.println("Static block initialized");
	}

	public static void main(String[] args) {
		
		new StaticBlocks();
		new StaticBlocks();
		new StaticBlocks();

	}

}


