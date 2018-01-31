import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/*
 * Bank Application
 * By: Benjamin Stafford
 * 12/17/17
 */

public class BankApplication extends BankApplicationMenu{
	
	static String ID = null;			// to give access to other classes
	static double balance = 0;
	static HashMap<String,String> map = new HashMap<String,String>();
	static String address = "C:\\Users\\Benjamin\\Dropbox\\JavaProject\\All Accounts\\accounts";
	//C:\\Users\\Benjamin\\Dropbox\\JavaProject\\All Accounts\\accounts
	
	public static void main(String[] args){
		printMenu();
		
		// Declare map, userInput, running, and sc
		Scanner sc = new Scanner(System.in);
		boolean running = true;
		int userInput = sc.nextInt();
		
		// Update map from file
		openFile();
													// Delete extra files with name ID after change of ID name
		while(running){
			
			if (userInput == 1)
				login(sc);
			else if (userInput == 2)
				createAccount(sc);
			else if(userInput == 3)
				displayAll();
			else if(userInput == 4)
				exit(sc);
			else
				System.out.print("Invalid Response. Please Try Again.\nEnter Response: ");
			
			printMenu();
			userInput = sc.nextInt();
		}
		
		// End of program
		sc.close();
	}
		public static void printMenu(){
		System.out.println("=========MAIN MENU==========");
		System.out.println("1. Login In");
		System.out.println("2. Create An Account");
		System.out.println("3. Display All Accounts");
		System.out.println("4. Exit Program");
		System.out.println("============================");
		System.out.print("Enter Response: ");
	}
	
	public static void saveFile(){
		
		// Initializes file to null
		File file = null;
		PrintWriter pw = null;
		
		// For FileNotFound Exception
		try{
			file = new File(BankApplication.address);
			pw = new PrintWriter(file);
		}
		catch(Exception e){
			System.out.println("File Not Found.");
		}
				
		// Prints to data to accounts file
		for(String number : BankApplication.map.keySet()){
			pw.println(number + " : " + BankApplication.map.get(number));
		}
		
		// Closes pw
		pw.close();
		
	}
	
	public static void openFile(){
		
		// Initializes file and sc to null
		File file = null;
		Scanner sc = null;
		
		// For FileNotFound Exception
		try {
			file = new File(BankApplication.address);
			sc = new Scanner(file);
		}
		catch(Exception e){
			e.printStackTrace();
		}
			
		// Takes data from file and puts in HashMap
		while(sc.hasNextLine()){
			
			String line = sc.nextLine();
			String[] array = line.split(":");
			array[0] = array[0].trim();
			array[1] = array[1].trim();
			
			// Set flag to stop while loop from putting in map
			boolean flag = false;
			
			// Checks if there are duplicate accounts
			for(String number : BankApplication.map.keySet()){
				if(array[0].equals(number)) {
					flag = true;
					continue;
				}
			}
			if(flag==true)
				continue;
			
			// Puts data into map
			BankApplication.map.put(array[0], array[1]);	
		}
			
		// Closes sc
		sc.close();
	}
	
	public static void displayAll(){
		System.out.println("======ALL ACCOUNTS======");
		System.out.println("ID : Password");
		for(String number : BankApplication.map.keySet()){
			System.out.println(number + " : " + BankApplication.map.get(number));
		}
	}
	
	public static void createAccount(Scanner sc){
		
		// Sets ID
		System.out.print("Enter an ID: ");
		String ID = sc.next();
		ID = ID.trim();					// Trim for error checking
		
		// Sets password
		System.out.print("Enter a Password: ");
		String password = sc.next();
		password = password.trim();		// Trim for error checking
		BankApplication.map.put(ID,password);
		System.out.println("============================");
		System.out.println("Your account has been created for:\nID: " + ID + "\nPassword: " + password);
		
	}
	
	public static void login(Scanner sc){
		
		// Sets ID
		System.out.print("Enter ID: ");
		ID = sc.next();
		ID = ID.trim();					// Trim for error checking
		
		// Sets password
		System.out.print("Enter Password: ");
		String password = sc.next();
		password = password.trim();		// Trim for error checking
		
		// Sets flag
		boolean flag = false;
		
		for(String number : BankApplication.map.keySet()){
				
			// ID and Password validation
			if(ID.equals(number)){
				if(password.equals(BankApplication.map.get(number))){
					System.out.println("============================");
					System.out.println("You successfully logged in!");
					saveFile();												// MARKED FOR SAVE FILE??
					String[] args = {"1"};
					BankApplicationMenu.main(args);
					flag = true;
				}
			}
		}
			
		if(flag == false){
			System.out.println("============================");
			System.out.print("Invalid Login. Please Try Again.\n============================\nEnter ID: ");
			ID = sc.next();
			ID = ID.trim();					// Trim for error checking
				
			System.out.print("Enter Password: ");
			password = sc.next();
			password = password.trim();		// Trim for error checking
		}
	}

	public static void exit(Scanner sc){
		System.out.println("============================");
		System.out.println("Want to save before exitting? (Y or N)");
		System.out.println("============================");
		System.out.print("Enter Response: ");
		String response = sc.next();
		while(true){
			if(response.equals("Y") || response.equals("y")){
				saveFile();
				System.exit(0);
			}
			else if(response.equals("N") || response.equals("n")){
				System.exit(0);
			}
			else{
				System.out.println("Invalid Response. Please try again.\nEnter Response: ");
				response = sc.next();
			}
		}
	}
}




class BankApplicationMenu{

	public static void main(String[] args){
		ArrayList<Double> list = new ArrayList<Double>();
		
		// Update ArrayList from file
		list = openFile(list);
		
		// Calculates balance every loop
		for(int i = 0; i < list.size(); i++){
			BankApplication.balance += list.get(i);
		}
		printMenu();
		
		// Declare list, userInput, running, balance, and sc
		Scanner sc = new Scanner(System.in);
		boolean running = true;      
		int userInput = sc.nextInt();
		
		while(running){
			
			if(userInput == 1)
				deposit(sc,list);
			else if(userInput == 2)
				withdraw(sc,list);
			else if(userInput == 3)
				searchTransactions(sc,list);
			else if(userInput == 4)
				displayAll(list);
			else if(userInput == 5)
				changeID(sc);
			else if(userInput == 6)
				changePassword(sc,list);
			else if(userInput == 7)
				exit(running,sc,list);
			else
				System.out.println("Invalid Response. Please try again.\nEnter Response: ");
			
			printMenu();
			
			// Accept new response every loop
			userInput = sc.nextInt();
		}
		
	// End of program
	sc.close();
	}

	public static void printMenu(){
		System.out.println("========YOUR ACCOUNT========");
		System.out.println("\tID: " + BankApplication.ID);
		System.out.println("Available Balance: " + BankApplication.balance);
		System.out.println("1. Deposit");
		System.out.println("2. Withdrawal");
		System.out.println("3. Search for a Transaction ");
		System.out.println("4. Display All Transactions");
		System.out.println("5. Change ID");
		System.out.println("6. Change Password");
		System.out.println("7. Logout");
		System.out.println("============================");
		System.out.print("Enter Response: ");
	}
	
	public static void deposit(Scanner sc, ArrayList<Double> list){
		
		// Initialize balance
		System.out.print("Enter the amount you want to deposit: ");
		double deposit = sc.nextDouble();
		System.out.println("Are you sure you want to deposit " + deposit + " ? (Y or N)");
		String check = sc.next();
		
		// Verify if Y or N
		while(true) {
			if(check.equals("y") || check.equals("Y")) 
				if(deposit > 0){
					list.add(deposit);
					System.out.println("Your deposit of " + deposit + " was successful!");
					return;
				}
				else {
					System.out.println("Your deposit is not a positive amount.");
					return;
				}
			else if(check.equals("n") || check.equals("N")){
				return;
			}
			else {
				System.out.println("Invalid Response. Please try again.\nEnter Response: ");
				check = sc.next();
			}
		}
	}
	
	public static void withdraw(Scanner sc, ArrayList<Double> list){
		
		// Initialize
		System.out.print("Enter the amount you want to withdraw: ");
		double withdraw = sc.nextDouble();
		System.out.print("Are you sure you want to withdraw " + withdraw + " ? (Y or N)");
		String check = sc.next();
		
		// Verify balance > 0
		double change = BankApplication.balance - withdraw;
		
		// Verify if Y or N 
		// edit to repeat to verify
		while(true) {
			if(check.equals("y") || check.equals("Y")){		
				if(change > 0){								// FIX FOR ENTERING NEGATIVE VALUE AS WITHDRAWAL
					withdraw = withdraw-(withdraw*2);
					list.add(withdraw);
					System.out.println("Your withdrawal of " + withdraw + " was successful!");
					return;
				}
				else
					System.out.println("Insufficient funds.");
				return;
			}
			else if(check.equals("n") || check.equals("N")){
				return;
			}
			else {
				System.out.print("Invalid Response. Please try again.\nEnter Response: ");
				check = sc.next();
			}
		}		
	}
	
	public static ArrayList<Double> openFile(ArrayList<Double> list){
		
		// Initialize fileName from other class 
		String fileName = BankApplication.ID;
		
		// Sets up file and sets newSC to null
		File file = new File(fileName);
		Scanner newSC = null;
		
		// Verify if file exists
		if(!file.exists()){
			return list;
		}
		
		// For FileNotFound Exception
		try{
			newSC = new Scanner(file);
		}
		catch(Exception e){
			System.out.println("File Not Found");
		}
		
		// Verify if file hasNextLine()
		if(!newSC.hasNext()){
			return list;
		}
		
		// Puts file data into ArrayList
		while(newSC.hasNext()){
			double transaction = newSC.nextDouble();
			list.add(transaction);
		}
		
		// Close newSC and return
		newSC.close();
		return list;
	}
	
	public static void saveFile(Scanner sc, ArrayList<Double> list){
		
		// Declare fileName from ID in BankApplication class
		String fileName = BankApplication.ID;
		PrintWriter pw = null;
		
		// For FileNotFound Exception
		try{
			pw = new PrintWriter(new File(fileName));
		}
		catch(Exception E){
			System.out.println("Invalid File.");
		}
		
		// Prints transaction amount to ID fileName
		for(int i = 0; i < list.size(); i++){
			pw.printf("%.2f\n",list.get(i));
		}
		
		// Closes pw
		pw.close();
	}
	
	public static void searchTransactions(Scanner sc, ArrayList <Double> list) { 
		
		// Initialize balance
		double balance = 0;
		System.out.print("Enter the Transaction #: ");
		int valueToFind = sc.nextInt();
		
		// Error checking for negative values and out of bounds values 
		if(valueToFind > list.size() && valueToFind > 0) {
			System.out.print("There are not " + valueToFind + " transactions in your account. Please try again.\nEnter Response: ");
			valueToFind = sc.nextInt();
		}
		
		// Sets balance from file
		for(int i = 0; i < valueToFind; i++){			
			balance += list.get((i));
		}
		// Prints search to screen
		System.out.printf("Transaction #: %d\nTransaction amount: %.2f\nBalance after transaction: %.2f\n",valueToFind,list.get((valueToFind-1)),balance);
		
	}
	
	public static void displayAll(ArrayList<Double> list){
		
		// Initialize balance
		double balance = 0;
		System.out.println("======ALL TRANSACTIONS======");
		
		// Prints data to screen
		for(int i = 0; i < list.size(); i++){
			balance += list.get(i);
			System.out.printf("Transaction #: %d\nTransaction amount: %.2f\nBalance after transaction: %.2f\n",i+1,list.get(i),balance);
			if(i != (list.size()-1))								
				System.out.println("============================");
		}
	}
	
	public static void changeID(Scanner sc) {
		
		// Set new ID
		System.out.print("Enter new ID: ");
		BankApplication.ID = sc.next();
		System.out.println("You successfully changed your ID to " + BankApplication.ID + "!");
	
	}
	
	public static void changePassword(Scanner sc, ArrayList<Double> list) {		// need to check if save to file
		
		// Set ID
		System.out.print("Enter ID: ");
		String userInput = sc.next();
		userInput = userInput.trim();			// Trim for error checking
		
		// Set password
		System.out.print("Enter Password: ");
		String password = sc.next();
		password = password.trim();				// Trim for error checking
		
		// Sets flag
		boolean running = true;
				
		while(running){
			for(String number : BankApplication.map.keySet()){
				
				// ID and Password validation
				if(userInput.equals(number)){
					if(password.equals(BankApplication.map.get(number)))
						System.out.print("Enter new password: ");
						password = sc.next();
						BankApplication.map.put(BankApplication.ID,password);	// overwrite old data
						System.out.println("You successfully changed your password to " + BankApplication.map.get(BankApplication.ID) + "!");
						running = false;
						break;
				}
				else{
					System.out.println("============================");
					System.out.print("Invalid Login. Please Try Again.\n");
					System.out.print("============================\nEnter ID: ");
					BankApplication.ID = sc.next();
					BankApplication.ID = BankApplication.ID.trim();
					System.out.print("Enter Password: ");
					password = sc.next();
					password = password.trim();
				}
			}
		}
	}
	
	public static void exit(boolean running, Scanner sc, ArrayList<Double> list){
		System.out.println("============================");
		System.out.println("Want to save before exitting? (Y or N)");
		System.out.println("============================");
		System.out.print("Enter Response: ");
		String response = sc.next();
		if(response.equals("Y") || response.equals("y")){
			saveFile(sc,list);
			String [] args = {"1"};		// created to use as
			BankApplication.main(args);	// parameter for main
		}
		else{
			String [] args = {"1"};		// created to use as
			BankApplication.main(args);	// parameter for main
		}
	}
}
