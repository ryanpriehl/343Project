import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class WordGenerator{
	
	private Scanner scan;
	private Random rand;
	private String word;

	public WordGenerator(){
		rand = new Random();
	}
	
	public String generateWord(int level) throws FileNotFoundException{
		if(level == 1){
			boolean found = false;
			while(!found){
				scan = new Scanner(new File("words_short.txt"));
				found = true;
				int n = rand.nextInt(2186) + 1;
				for(int i = 0 ; i < n; i++){
					word = scan.nextLine();
				}
				if(word.length() <= 2)
					found = false;
			}
			return word.toUpperCase();
		} 
		else if(level == 2){
			scan = new Scanner(new File("words_medium.txt"));
			int n = rand.nextInt(5471) + 1;
			for(int i = 0 ; i < n; i++){
				word = scan.nextLine();
			}
			return word.toUpperCase();
		} 
		else if(level == 3){
			boolean found = false;
			while(!found){
				scan = new Scanner(new File("words_long.txt"));
				found = true;
				int n = rand.nextInt(2246) + 1;
				for(int i = 0 ; i < n; i++){
					word = scan.nextLine();
				}
				if(word.length() > 14)
					found = false;
			}
			return word.toUpperCase();
		} 
		else{
			System.out.println("Error");
			return null;
		}
	}
}
