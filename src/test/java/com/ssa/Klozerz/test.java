package com.ssa.Klozerz;

import java.util.Scanner;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;

import com.ssa.Klozerz.util.AppUtil;


public class test {
	  public static void main(String[] args) {
	        try {
	        String question;
	        String firstOption;
	        String secondOption;
	        String thirdOption;
	        
	        System.out.println("Which is Capital of Punjab");
	        
	        System.out.println("A : Okara");
	        System.out.println("B : Lahore");
	        System.out.println("C : Karachi");
	        
	        System.out.println("Enter correct option");
	        Scanner  input = new Scanner(System.in);
	        String option = input.next();
	       //c System.out.println("Next songs");
	        if(option.equals("A".toLowerCase())) {
	        	System.out.println("Wrong");
	        	System.out.println("This is an wrong answer");
	        }else if(option.equals("B")) {
	        	System.out.println("Correct");
	        	System.out.println("Yes Lahore is capital city of Punjab");
	        }else if(option.equals("C")) {
	        	System.out.println("Wrong");
	        	System.out.println("This is an wrong answer");
	        }
		        	          

	        } catch (Exception e) {
	           e.printStackTrace();
	        }
	    }
}
