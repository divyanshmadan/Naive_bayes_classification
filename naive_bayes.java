import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;


public class naive_bayes {
	static int line_counter;
	static int column_count;
	static int test_line_counter;
	static int test_column_count;
	static double[][] testDataMatrix;
	static double[][] testProbabilityMatrixforB;
	static double[][] testProbabilityMatrixforM;
	static double[][] trainingDataMAtrixforB;
	static double[][] trainingDataMAtrixforM;
	static double[][] meanForB;
	static double[][] varianceForB;
	static double[][] meanForM;
	static double[][] varianceForM;
	static double classB;
	static double classM;
	
	static ArrayList<String> classDetails=new ArrayList<String>();
	static ArrayList<String> temp1=new ArrayList<String>();
	
	static int bValue=0;
	static int mValue=0;
	static int totalValue=0;
	public static void main(String args[]) throws IOException{
		Scanner userInput = new Scanner(System.in);
		String file_name;
		String test_file_name;
		//User Interface for Input
		System.out.println("*********** IMPLEMENTATION OF NAIVE BAYES CLUSTERING ALGORITHM **********");
		System.out.println("Enter the filename for training data set: ");
		file_name=userInput.next();
		
		
		getTrainingDataAttributes(file_name);
		getTrainingDataValues(file_name);
		getProbabilityValues();
		//test data input
		System.out.println("*********** CALCULATIONS DONE **********");
		System.out.println("Enter the filename for test data set: ");
		test_file_name=userInput.next();
		getTestDataAttributes(test_file_name);
		getTestDataValues(test_file_name);
		getTestProbabilityValues();
	    doClassification();
		
	}
	public static void getTrainingDataAttributes(String fileName) throws IOException{
		//System.out.println("In");
		BufferedReader inFile = new BufferedReader(new FileReader(fileName));		
		line_counter=0;
		String current_line;
		while((current_line=inFile.readLine()) != null){
			//System.out.println("In 1");
			StringTokenizer items = new StringTokenizer(current_line, "\t");	
			String token;
            column_count=0;
			while(items.hasMoreTokens()){
				token=items.nextToken();
				
				if(token.equalsIgnoreCase("B"))
					bValue++;
				else if(token.equalsIgnoreCase("M"))
					mValue++;
				
				column_count++;		
			}
			
			line_counter++;
		}
		trainingDataMAtrixforB=new double[line_counter][column_count-2];
		trainingDataMAtrixforM=new double[line_counter][column_count-2];
		totalValue=bValue+mValue;
		classB=(double) bValue/totalValue;
		classM=(double) mValue/totalValue;
	}
	public static void getTrainingDataValues(String fileName) throws IOException{
		//System.out.println("In");
		BufferedReader inFile = new BufferedReader(new FileReader(fileName));		
		line_counter=0;
		String current_line;
		line_counter=0;
		while((current_line=inFile.readLine()) != null){
			//System.out.println("In 1");
			StringTokenizer items = new StringTokenizer(current_line, "\t");	
			String token=null;
			items.nextToken();
			token=items.nextToken();
		    classDetails.add(token);
            column_count=0;
			while(items.hasMoreTokens()){
				
				if(token.equalsIgnoreCase("B"))
					trainingDataMAtrixforB[line_counter][column_count]=Double.parseDouble(items.nextToken());
				else if(token.equalsIgnoreCase("M"))
					trainingDataMAtrixforM[line_counter-bValue][column_count]=Double.parseDouble(items.nextToken());
				
				
				
				column_count++;		
			}
			//System.out.println("CC"+column_count);
			line_counter++;
		}
	}
		
	public static void getProbabilityValues()
	{
		
		double variance;
		double sum;
		//System.out.println("bvalue"+bValue);
		//mean and variance for class B
		meanForB=new double[1][column_count];
		for(int i=0;i<column_count;i++)
		{
			sum=0;
			for(int j=0;j<bValue;j++)
			{
				sum+=trainingDataMAtrixforB[j][i];
			}
			//System.out.println("Sum"+sum);
			meanForB[0][i]= (sum/bValue);
			//System.out.println("Mean"+meanForB[0][i]);
		}
		//variance
		varianceForB=new double[1][column_count];
		for(int i=0;i<column_count;i++)
		{
			variance=0;
			for(int j=0;j<bValue;j++)
			{
				variance+=((trainingDataMAtrixforB[j][i]-meanForB[0][i])*(trainingDataMAtrixforB[j][i]-meanForB[0][i]));
			}
			
			varianceForB[0][i]=(variance/bValue);
			//System.out.println("Variance"+varianceForB[0][i]);
		}
		
		//class M
		meanForM=new double[1][column_count];
		for(int i=0;i<column_count;i++)
		{
			sum=0;
			for(int j=0;j<mValue;j++)
			{
				sum+=trainingDataMAtrixforM[j][i];
			}
			//System.out.println("Sum"+sum);
			meanForM[0][i]= (sum/mValue);
			//System.out.println("Mean"+meanForM[0][i]);
		}
		//variance
		varianceForM=new double[1][column_count];
		for(int i=0;i<column_count;i++)
		{
			variance=0;
			for(int j=0;j<mValue;j++)
			{
				variance+=((trainingDataMAtrixforM[j][i]-meanForM[0][i])*(trainingDataMAtrixforM[j][i]-meanForM[0][i]));
			}
			
			varianceForM[0][i]=(variance/mValue);
			//System.out.println("Variance"+varianceForM[0][i]);
		}
		
		//System.out.println("ccc"+column_count);
		
	}
	public static void getTestDataAttributes(String fileName) throws IOException{
		//System.out.println("In");
		BufferedReader inFile = new BufferedReader(new FileReader(fileName));		
		line_counter=0;
		String current_line;
		while((current_line=inFile.readLine()) != null){
			//System.out.println("In 1");
			StringTokenizer items = new StringTokenizer(current_line, "\t");	
			String token;
            test_column_count=0;
			while(items.hasMoreTokens()){
				
				temp1.add(items.nextToken());
				test_column_count++;		
			}
			
			test_line_counter++;
		}
		testDataMatrix=new double[test_line_counter][test_column_count-1];
		testProbabilityMatrixforB=new double[test_line_counter][test_column_count-1];
		testProbabilityMatrixforM=new double[test_line_counter][test_column_count-1];
	}
	public static void getTestDataValues(String fileName) throws IOException{
		//System.out.println("In");
		BufferedReader inFile = new BufferedReader(new FileReader(fileName));		
		
		String current_line;
		test_line_counter=0;
		while((current_line=inFile.readLine()) != null){
			//System.out.println("In 1");
			StringTokenizer items = new StringTokenizer(current_line, "\t");	
			String token=null;
			items.nextToken();
			
            test_column_count=0;
			while(items.hasMoreTokens()){
				token=items.nextToken();
				testDataMatrix[test_line_counter][test_column_count]=Double.parseDouble(token);
				//System.out.println(testDataMatrix[test_line_counter][test_column_count]+"test"+test_line_counter+test_column_count);
				
				
				test_column_count++;		
			}
			//System.out.println("CC"+test_column_count);
			test_line_counter++;
		}
	}	
	public static void getTestProbabilityValues()
	{
		double probabilityforB;
		double probabilityforM;
		double part1;
		double part2;
		for(int i=0;i<test_line_counter;i++)
		{
			for(int j=0;j<test_column_count-1;j++)
			{
				part1=1/(Math.sqrt(2*Math.PI*varianceForB[0][j]));
				part2=Math.exp((((testDataMatrix[i][j]-meanForB[0][j])*(testDataMatrix[i][j]-meanForB[0][j]))*(-1))/(2*varianceForB[0][j]));
				probabilityforB=part1*part2;
				//System.out.println("p for b"+probabilityforB);
				testProbabilityMatrixforB[i][j]=probabilityforB;
				
			}
		}
		for(int i=0;i<test_line_counter;i++)
		{
			for(int j=0;j<test_column_count-1;j++)
			{
				part1=1/(Math.sqrt(2*Math.PI*varianceForM[0][j]));
				part2=Math.exp((((testDataMatrix[i][j]-meanForM[0][j])*(testDataMatrix[i][j]-meanForM[0][j]))*(-1))/(2*varianceForM[0][j]));
				probabilityforM=part1*part2;
				//System.out.println("p for m"+probabilityforM);
				testProbabilityMatrixforM[i][j]=probabilityforM;
				
			}
		}
		
	}
	public static void doClassification()
	{
		double evidence;
		double posteriorB;
		double posteriorM;
		double tempB;
		double tempM;
		int check=0;
		for(int i=0;i<test_line_counter;i++)
		{
			evidence=0;
			posteriorB=0;
			posteriorM=0;
			tempB=classB;
			tempM=classM;
			//System.out.println("B before"+tempB+"M"+tempM);
			for(int j=0;j<test_column_count-1;j++)
			{
				tempB*=testProbabilityMatrixforB[i][j];
				tempM*=testProbabilityMatrixforM[i][j];
			}
			//System.out.println("B"+tempB+"M"+tempM);
			evidence=tempB+tempM;
			tempB=classB;
			tempM=classM;
			for(int k=0;k<test_column_count-1;k++)
			{
				tempB*=testProbabilityMatrixforB[i][k];
				tempM*=testProbabilityMatrixforM[i][k];
			}
			posteriorB=tempB/evidence;
			posteriorM=tempM/evidence;
			if(posteriorB>posteriorM)
			{
				System.out.println("Class B");
				check++;
			}
			else if(posteriorB<posteriorM)
			{
				System.out.println("Class M");
				check++;
			}
			
		}
		//System.out.println("total values"+check);
	}
}
