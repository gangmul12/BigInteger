import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern; 

public class BigInteger
{
    public static final String QUIT_COMMAND = "quit";
    public static final String MSG_INVALID_INPUT = "입력이 잘못되었습니다.";

    
    public static final Pattern EXPRESSION_PATTERN = Pattern.compile("^\\s*[-+]?[0-9]+\\s*[+*-]\\s*[-+]?[0-9]+\\s*$");
    //BigInteger member state
    private int[] data;
    private int length;
    
    
    // BinInteger Constructor
    public BigInteger(int i)
    {
    	length=lengthOf(i);
    	data = new int[length];
    	for(int j=0; j<length; j++)
    	{
    		data[j]=i%10;
    		i/=10;
    	}
    }
    
    
    public BigInteger(int[] num1)
    {
    	length=num1.length;
    	data = new int[length];
    	System.arraycopy(num1, 0, data, 0, length);
    }
    
    
    public BigInteger(String s)
    {
    	int sign = 1;
    	switch(s.charAt(0))
    	{
    	case '-':
    		sign = -1;
    	case '+':
    		length = s.length()-1;
    		data = new int[length];
    		for(int i = 0; i < length ; i++)
    		{
    			data[i] = sign * Character.getNumericValue(s.charAt(length-i));
    		}
    		break;
    	default:
    		length = s.length();
    		data = new int[length];
    		for(int i =0 ; i<length; i++)
    		{
    			data[i] = Character.getNumericValue(s.charAt(length-i-1));
    		}
    		
    	}
    	
    			

    }
    
    //BigInteger Method
    public BigInteger add(BigInteger big)
    {
    	//FIXME 
    	return this;

 

    }

    
    public BigInteger subtract(BigInteger big)
    {
    	//FIXME
    	return this;

    	

 

    }
    
    
    public BigInteger multiply(BigInteger big)
    {
    	//FIXME
    	return this;

 

    }
    //@Override
    
    public String toString()
    {
    	
    	String result = "";
    	result = result + data[length-1];
    	for(int i = 1 ; i < length ; i++)
    		result = result + Math.abs(data[length-1-i]);
    	return result;

 

    }
    

    static BigInteger evaluate(String input) throws IllegalArgumentException
    {
        //Check validity
    	if(!isValid(input))
        	throw new IllegalArgumentException();
        //parse input and store parsed data at lhs, operator,rhs
        String lhs = parseOperand(input);
        input = deleteFirstAndTrim(input, lhs);//if we parsed lhs, delete lhs from input
        lhs = deleteZero(lhs);
        char operator = input.charAt(0);
        input = deleteFirstAndTrim(input, "[-+*]");
        
        String rhs = parseOperand(input);
        rhs = deleteZero(rhs);
        
        //make bigInteger Instance by parsed String and evaluate
        BigInteger num1 = new BigInteger(lhs);
        BigInteger num2 = new BigInteger(rhs);
        BigInteger result = evaluateResult(num1, operator, num2);
        
        return result;
     }

    
    public static void main(String[] args) throws Exception
    {
        try (InputStreamReader isr = new InputStreamReader(System.in))
        {

            try (BufferedReader reader = new BufferedReader(isr))
            {
                boolean done = false;
                while (!done)
                {
                    String input = reader.readLine();
                    try
                    {
                        done = processInput(input);

                    }
                    catch (IllegalArgumentException e)
                    {
                        System.err.println(MSG_INVALID_INPUT);
                    }
                }
            }
        }
    }
        
    
    static boolean processInput(String input) throws IllegalArgumentException
    {
        boolean quit = isQuitCmd(input);
        if (quit)
        {
            return true;
        }
        else
        {
            BigInteger result = evaluate(input);
            System.out.println(result.toString());
            return false;
        }
    }

    
    static boolean isQuitCmd(String input)
    {
        return input.equalsIgnoreCase(QUIT_COMMAND);

    }
    
  //check validity
    static boolean isValid(String input)
    {
    	Matcher m = EXPRESSION_PATTERN.matcher(input);
    	return m.find();
    }
    
    //get rid of the first 'delete' from the target and trim.
    static String deleteFirstAndTrim(String target, String delete)
	{
    	if(delete.charAt(0)=='+')
    		delete="\\"+delete; //for regex started with +
		target=target.replaceFirst(delete, "");
		target=target.trim();
		return target;
	}
    
    //Operand Parser
    static String parseOperand(String input)
    {
    	
    	Pattern p = Pattern.compile("[+-]?[0-9]+");
    	Matcher m = p.matcher(input);
    	m.find();
    	
    	return m.group();
    }
    
    //Selection Method
    static BigInteger evaluateResult(BigInteger lhs, char op, BigInteger rhs) throws IllegalArgumentException
    {
    	switch(op)
        {
        case '+':
        	return lhs.add(rhs);
        	
        case '-':
        	return lhs.subtract(rhs);
        	       
        case '*':
        	return lhs.multiply(rhs);
        default:
        	throw new IllegalArgumentException();
        }
    }
    	
    
    static int lengthOf(int i)
    {
    	int result = 1;
    	boolean done = false;
    	while(!done)
    	{
    		if(i/10!=0)
    		{
    			result++;
    			i=i/10;
    			continue;
    		}
    		else break;
    	}
    	return result;
    }
    
    static boolean isSign(char c)
    {
    	return (c=='+'||c=='-');
    }
    
    //delete meaningless Zero
    static String deleteZero(String input)
    {
    	int index = 0;
    	if(isSign(input.charAt(0)))
    		index++;
    	
    	while(input.charAt(index)=='0'&&index!=input.length()-1)
    	{
    		
    		input=input.replaceFirst("0", "");
    		
    	}
    	return input;
    	
    	
    }	
    	
    	
    

}

