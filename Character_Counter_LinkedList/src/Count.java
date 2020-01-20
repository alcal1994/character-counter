import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;


public class Count {
	
	/**
	 * Main method 
	 */
	public static void main(String[] args) {
		
		//Specify file path location in arguments
		File file = new File(args[0]);
		
		//check if the file exists in the given file path
		if (!file.exists()) {
			
			//Print the error message and quit the program
			System.out.println(args[0]+ " does not exist. Specify file location in the arguments of the project and re-run.");
			
			//Quit the program
			return;
		}
		//check if file cannot be read from the given path
		if (!(file.isFile() && file.canRead())) {
			
			//Print the error message and 
			System.out.println(file.getName() + " cannot be read from.");
			
			//Quit from program
			return;
		}
		
		//Put the code in the try block so that if any runtime error occur then it can be catch 
		try {

			//Open the Buffered reader stream to read the file in UTF mode.
			//Buffered reader is fast reader because it reads the chunks of file data in one shot and keep that data in buffer  
			BufferedReader bufferedReaderInstance = new BufferedReader( new InputStreamReader( new FileInputStream(file), "UTF8"));
			
			//Declare variable to hold the count of total unique character present in the file
			int totalUniqueCharacter =0;
			
			//Declare the LinkedList of the CharacterFrequency class to 10 instances of the CharacterFrequency class 	
			LinkedList <CharacterFrequency> charFrequency = new LinkedList<CharacterFrequency>();
			
			//Declare the integer variable in which character will be read from the file
			int charinput = 0;
			
			//Read all the character one by one 
			while ((charinput = bufferedReaderInstance.read()) != -1) 
			{
				//take the character from in read input and cast it to char type
				char character = (char)charinput;
				
				//Call method to add the character in the LinkedList or if character already exists then increase the counter
				//If new character added then totalUniqueCharacter will be increased by one and returned by the method
				totalUniqueCharacter = updateCharFrequency(charFrequency, character, totalUniqueCharacter);				
			}//end of while loop
			
			//Create the file instance to write the output in file
			File fileOutput;
			if(args.length == 2)
			{
				//open the file in the directory provided in the arguments path two in the command line
				fileOutput = new File(args[1]);
			}
			else
			{
				//create the file in the working directory if no file exists
				fileOutput = new File("Count.txt");				
			}
			
			 
			//If file is not created then create the file
			//fileOutput.createNewFile();
			
			//Set this file is writable
			fileOutput.setWritable(true);
			
			//Create the instance of the FileOutputStream to write into the file
			FileOutputStream fos = new FileOutputStream(fileOutput);
			
			//Iterate through each instance of the unique character and print the detail using the toString method if the CharacterFrequency class
			for(int i= 0; i<totalUniqueCharacter; i++)
			{
				//call the toString method of the CharacterFrequency class and append newline char and write into the file 
				fos.write((charFrequency.get(i).toString()+"\r\n").getBytes());				
			}
			//close the file output stream
			fos.close();
			
			
			//close the buffered Stream instance
			bufferedReaderInstance.close();
		} catch (IOException e) 
		{
			e.printStackTrace();
		}

	}
	
	
	/**
	 * This method used to update the CharacterFrequency array instance whose character matches with its character instance
	 * and increase its characterCount variable 
	 * @param charFrequencyList : Array containing all the CharacterFrequency instance
	 * @param character : character which read from the file and needed to update 
	 * @param totalUniquechar : total unique characterFrequiency instance added in the charFrequencyList
	 * @return  return the total number of uniqueCharacter in the array
	 */
	public static int updateCharFrequency(LinkedList<CharacterFrequency> charFrequencyList, char character, int totalUniquechar)
	{
		//Create the instance of the CharacterFrequency with new character
		CharacterFrequency chf = new CharacterFrequency(character, 1);
		
		//Flag to check if character exist in the array
		boolean isCharExist = false;
		
		//Iterate through each instance of the CharacterFrequency 
		for(int i= 0; i<totalUniquechar; i++)
		{
			//Check if character exist in the charFrequencyList array
			if(charFrequencyList.get(i).equals(chf))
			{
				//If exist then increase its counter
				charFrequencyList.get(i).increment();
				
				//set the flag true
				isCharExist = true;
				break;
			}
		}
		//If isCharExist false
		if(!isCharExist)
		{
			//add new instance of CharacterFrequency in the charFrequencyList			
			charFrequencyList.add(chf);
			
			//increase the totalUniqueChar by one
			totalUniquechar++;
		}
		//Return the total unique character 
		return totalUniquechar;
	}
}

