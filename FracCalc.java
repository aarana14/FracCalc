package fracCalc;
import java.util.*;

public class FracCalc {
    public static String produceAnswer(String input){ 
    	//Variables
        int operator_index;
        int operand1_whole;
        int operand1_num;
        int operand1_den;
        int operand2_whole;
        int operand2_num;
        int operand2_den;
        String operator;
        String operand1;
        String operand2;
        
        //Answer Variable
        String complexAnswer = "0";
        String finalAnswer = "0";
        
        //Get Problem Data
        operator_index = findOperatorIndex(input);
        operand1 = findOperand1(input, operator_index);
        operand2 = findOperand2(input, operator_index);
        operator = input.substring(operator_index, operator_index + 1);
        
        //Sort Data
        operand1_whole = findWhole(operand1);
        operand2_whole = findWhole(operand2);
        operand1_num = findNum(operand1);
        operand2_num = findNum(operand2);
        operand1_den = findDen(operand1);
        operand2_den = findDen(operand2);
        
        
        //Calculations
        switch (operator){
        	case "+":
        		complexAnswer = addition(operand1_whole, operand1_num, operand1_den, operand2_whole, operand2_num, operand2_den);
        		break;
        	case "-":
        		complexAnswer = subtract(operand1_whole, operand1_num, operand1_den, operand2_whole, operand2_num, operand2_den);
        		break;
        	case "*":
        		complexAnswer = multiply(operand1_whole, operand1_num, operand1_den, operand2_whole, operand2_num, operand2_den);
        		break;
        	case "/":
        		complexAnswer = divide(operand1_whole, operand1_num, operand1_den, operand2_whole, operand2_num, operand2_den);
        		break;
        	default:
        		System.out.println("Invalid operator.");
        		break;
        }
        
        //Simplify and fix complex answer
        finalAnswer = simplifyComplex(complexAnswer);
                  
        //Return Answer
        System.out.println("Answer: " + finalAnswer + "\n");
        return finalAnswer;
    }
    

	public static String simplifyComplex(String complexAnswer) {
		//Variables
        int whole;
        int num;
        int den;
        String simpleAnswer;
        
        //Find values
        whole = findWhole(complexAnswer);
        num = findNum(complexAnswer);
        den = findDen(complexAnswer);
        
        //Return if no fraction
        if(num == 0) {
        	simpleAnswer = whole + "";
        	return simpleAnswer;
        }
        //Fix Negatives
        else if((num < 0) && (whole > 0)){
        	whole = whole * -1;
        	num = num * -1;
        }
        else if((num < 0) && (whole < 0)){
        	num = num * -1;
        }
        else if((den < 0) && (whole > 0)) {
        	whole = whole * -1;
        	den = den * -1;
        }
        else if((den < 0) && (whole < 0)) {
        	den = den * -1;
        }
        else if(den < 0) {
        	den = den *-1;
        	num = num * -1;
        }
        
        //Simplify Fractions
        if ((num > 1) || (num < -1)) {
        	if(findGCD(num, den) != 1) {
        		int GCD = findGCD(num, den);
        		num = num / GCD;
        		den = den / GCD;
        	}
        }
        
        //If whole is 0, just return the fraction
        if (whole == 0) {
        	simpleAnswer = num + "/" + den;
        	return simpleAnswer;
        }
        
        //Answer format
        simpleAnswer = whole + "_" + num + "/" + den;
        
		return simpleAnswer;
	}
	

    public static String divide(int operand1_whole, int operand1_num, int operand1_den, int operand2_whole, int operand2_num, int operand2_den) {
    	//Variables
    	int whole;
        int num;
        int plc_num;
        int num1;
        int num2;
        int den;
        int whole_den1;
        int whole_den2;
       
        //Calculations
        whole_den1 = operand1_den * operand1_whole;
        whole_den2 = operand2_den * operand2_whole;
        num1 = operand1_num + Math.abs(whole_den1);
        num2 = operand2_num + Math.abs(whole_den2);
        //Keep / Change / Flip
        plc_num = num2;
        num2 = operand2_den;
        operand2_den = plc_num; 
	    //Check For Negatives
	    if (whole_den1 < 0){
	     	num1 *= -1;
	    }
	    if(whole_den2 < 0) {
	    	num2 *= -1;
	    }
	    //Finish Calculations
        num = num1 * num2;
        den = operand1_den * operand2_den;
        whole = (int)num/den;
        num = num - (whole * den);
        
        //Answer
    	return whole + "_" + num + "/" + den;
    }
    
    
    public static String multiply(int operand1_whole, int operand1_num, int operand1_den, int operand2_whole, int operand2_num, int operand2_den) {
    	//Variables
    	int whole;
        int num;
        int num1;
        int num2;
        int den;
        int whole_den1;
        int whole_den2;
       
        //Calculations
        whole_den1 = operand1_den * operand1_whole;
        whole_den2 = operand2_den * operand2_whole;
        num1 = operand1_num + Math.abs(whole_den1);
        num2 = operand2_num + Math.abs(whole_den2);
	    //Check For Negatives
	    if (whole_den1 < 0){
	     	num1 *= -1;
	    }
	    if(whole_den2 < 0) {
	    	num2 *= -1;
	    }
	    //Finish Calculations
        num = num1 * num2;
        den = operand1_den * operand2_den;
        whole = (int)num/den;
        num = num - (whole * den);
        
        //Answer
    	return whole + "_" + num + "/" + den;
    }
    
    
	public static String subtract(int operand1_whole, int operand1_num, int operand1_den, int operand2_whole, int operand2_num, int operand2_den) {
		//Variables
    	int whole = 0;
        int num;
        int num1;
        int num2;
        int den;
        int whole_den1;
        int whole_den2;
        int frac1;
        int frac2;
           
        //Calculations
        whole_den1 = operand1_den * operand1_whole;
        whole_den2 = operand2_den * operand2_whole;
        num1 = operand1_num + Math.abs(whole_den1);
        num2 = operand2_num + Math.abs(whole_den2);
        den = operand1_den * operand2_den;
        frac1 = num1 * operand2_den;
        frac2 = num2 * operand1_den;
        
        //Check For Negatives
        if (whole_den1 < 0){
        	frac1 *= -1;
        }
        if(whole_den2 < 0) {
        	frac2 *= -1;
        }
        
        //Numerator
        num = frac1 - frac2;
        
        //Whole
        whole = (int)num/den;
        num = num - (whole * den);
        
        //Answer
    	return whole + "_" + num + "/" + den;
	}


	public static String addition(int operand1_whole, int operand1_num, int operand1_den, int operand2_whole, int operand2_num, int operand2_den) { 
		//Variables
    	int whole;
        int num;
        int num1;
        int num2;
        int den;
        int whole_den1;
        int whole_den2;
        int frac1;
        int frac2;
        
        
        //Calculations
        whole_den1 = operand1_den * operand1_whole;
        whole_den2 = operand2_den * operand2_whole;
	    num1 = operand1_num + Math.abs(whole_den1);
	    num2 = operand2_num + Math.abs(whole_den2);
	    den = operand1_den * operand2_den;
	    frac1 = num1 * operand2_den;
	    frac2 = num2 * operand1_den;
	    
	    //Check For Negatives
	    if (whole_den1 < 0){
	     	frac1 *= -1;
	    }
	    if(whole_den2 < 0) {
	    	frac2 *= -1;
	    }
	        
	    //Numerator
	    num = frac1 + frac2;
        //Whole
        whole = (int)num/den;
        num = num - (whole * den);
        
        //Answer
    	return whole + "_" + num + "/" + den;
	}

	
	public static int findGCD(int num1, int num2) {
		//Find GCD
		if(num2 == 0){
			return num1;
		}
		return findGCD(num2, num1%num2);
	}

	
    public static int findDen(String operand) {
    	//Variables
    	int slash_index;
    	int den;
    	String den_string;
    			
    	//Check whole
    	if (operand.contains("/")) {
    		slash_index = operand.indexOf("/");
    		den_string = operand.substring(slash_index + 1);
    	}
    	else {
    		den_string = "1";
    	}
    	
    	//Convert to int
    	den = Integer.parseInt(den_string);
    	
    	//Return
    	return den;
    }
    
    
    public static int findNum(String operand) {
    	//Variables
    	int under_score_index;
    	int slash_index;
    	int num;
    	String num_string;
    			
    	//Check whole
    	if (operand.contains("/")) {
    		slash_index = operand.indexOf("/");
    		if (operand.contains("_")){
    			under_score_index = operand.indexOf("_");
    			num_string = operand.substring(under_score_index + 1, slash_index);
    		}
    		else {
    			num_string = operand.substring(0, slash_index);
    		}
    	}
    	else {
    		num_string = "0";
    	}
    	
    	//Convert to int
    	num = Integer.parseInt(num_string);
    	
    	//Return
    	return num;
    }

    
	public static int findWhole(String operand) {
		//Variables
		int under_score_index;
		int whole;
		String whole_string;
		
		//Check whole
		if (operand.contains("_")) {
			under_score_index = operand.indexOf("_");
			whole_string = operand.substring(0, under_score_index);
		}
		else if (operand.contains("/")){
			whole_string = "0";
		}
		else {
			whole_string = operand;
		}
		
		//Convert to int
		whole = Integer.parseInt(whole_string);
		
		//Return
		return whole;
	}

    
    public static String findOperand2(String input, int operator_index) {
    	//Variable
    	String operand;
    	
    	//Operand 2
    	operand = input.substring(operator_index + 2, input.length());
    	
    	//Return
    	return operand;
    }
    
    
    public static String findOperand1(String input, int operator_index) {
    	//Variable
    	String operand;
    	
    	//Operand 1
    	operand = input.substring(0, operator_index - 1);
    	
    	//Return
    	return operand;
    }
    
    
    public static int findOperatorIndex(String input) {
    	//Variables
    	int oper_index;
    	int first_space;
    	
    	//Index
    	first_space = input.indexOf(" ");
    	oper_index = first_space + 1;
    	
    	//Return
    	return oper_index;
    }

    
    public static String getInput(Scanner input) {
    	String uinput;
    	//Ask user problem
    	System.out.println("Insert Problem: ");
    	uinput = input.nextLine();
    	//Return input to program
    	return uinput;
    }
    
    
    public static void main(String[] args) {
    	//Variables
    	String uinput = "";
    	
    	//Start Scanner
    	Scanner input = new Scanner (System.in);
    	
    	//Run Program 
    	while (!uinput.contains("quit")) {
    		//Get user input
    		uinput = getInput(input);
    		if (!uinput.contains("quit")) {
    			//Get Answer
    			produceAnswer(uinput);
    		}
    		else {
    			//Exit Message
    			System.out.println("\nThanks for using Gracias Calculator v. 2.0, bye!");
    		}
    	}
    	
    	//Close Input
    	input.close();
    }
}
