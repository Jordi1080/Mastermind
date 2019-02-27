import java.util.Scanner;
import java.util.Random;


public class Mastermind {
	Scanner sc = new Scanner(System.in);
	String input = "";
	
	int codeSize = 4;
	int pogingenToegestaan = 12;
	int pogingenGedaan = 0;
	String mode = "EASY";
	
	String code = "";// "ABCD";
	
	// bijhouden of er al gezegd is dat een letter op de verkeerde plek staat
	String alGenoemd = "";
	
	
	
	public static void main(String[] args) {
		Mastermind mm = new Mastermind();
		
		
		// GAME START
		mm.resetAlGenoemd();
		mm.selecteerMoeilijkheid();
		mm.genereerCode();
		
		// GAME LOOP hier
		mm.spelen();
		
		
	}
	
	public void genereerCode(){

		// genereer 4 keer een letter, i is de positie
		while (code.length() < codeSize){
			
			Random rng = new Random();
			int letterInt = (rng.nextInt(6));
			char letter =(char)(letterInt + 65);
			
			// als de code uit 4 unieke letters moet bestaan, uncomment de if statement
			// if (code.indexOf(letter) == -1){	
				code += letter;
			//}
			
		}
		System.out.println(code);
	}
	
	public void selecteerMoeilijkheid(){
		boolean keuzeGemaakt = false;
		
		System.out.println("kies je moeilijkheidsgraad");
		System.out.println("1 voor easy");
		System.out.println("2 voor hard");
		
		while (!keuzeGemaakt){
			input = sc.nextLine();
			
			switch (input){
			
			case "1": 
				System.out.println("Je kiest easy mode");
				mode = "EASY";
				keuzeGemaakt = true;
				break;
				
			case "2":
				System.out.println("Je kiest hard mode");
				mode = "HARD";
				keuzeGemaakt = true;
				break;
				
			default: System.out.println("kies 1 of 2... (misschien is dit spel voor jou toch te hoog gegrepen?)");
				
			}
		}
		
	}
	
	public void initSpel(String mode){
		if (mode.equals("EASY")){
			codeSize = 4;
			pogingenToegestaan = 12;
		}else {
			codeSize = 5;
			pogingenToegestaan = 10;
		}
	}

	public void spelen(){
		
		toonInstructies();
		
		while (sc.hasNextLine()){
			
			input = sc.nextLine();
			System.out.println("Je hebt ingevoerd:\n" + input);
			
			if (input.compareTo(code) == 0){
				eindeSpel();
				break;
			} else if (input.equals("q")){
				System.out.println("Ik had al een vermoeden dat je zou opgeven");
				System.out.println("No offense, maar dit spel is eigenlijk alleen voor de volwassenen onder ons");
				System.out.println("THE END");
				System.exit(0);
			}
			else{
				foutAntwoord();
			}
		}
	}
	
	public void toonInstructies(){
		if (mode.equals("EASY")){
			System.out.println("----------- WELKOM BIJ MASTERMIND -----------");
			System.out.println("Ik heb een code van 4 letters in gedachten");
			System.out.println("Elke letter is een van de volgende letters: ABCDEF");
			System.out.println("Een letter kan meerdere keren voorkomen");
			System.out.println("Probeer de code maar te raden (tip: het gaat je niet lukken)");
			System.out.println("Ik geef je 12 pogingen");
			System.out.println("Voer (q) in als wil stoppen");
		} else{
			System.out.println("----------- MASTERMIND VOOR GEVORDENEN -----------");
			System.out.println("Ik heb een code van 5 letters in gedachten");
			System.out.println("Elke letter is een van de volgende letters: ABCDEF");
			System.out.println("Een letter kan meerdere keren voorkomen");
			System.out.println("Probeer de code maar te raden (tip: geef maar gelijk op)");
			System.out.println("Ik geef je 10 pogingen");
			System.out.println("Voer (q) in als wil stoppen");
		}
	}
	
	public void foutAntwoord(){
		System.out.println("deze code is fout");
		pogingenGedaan++;
		System.out.println("Je hebt nog " + (pogingenToegestaan - pogingenGedaan) + " pogingen over");
		
		// voor elke input letter afzonderlijk (i)
		for(int i = 0; i < codeSize; i++){
			checkLetterCorrect(i, code.charAt(i));
			
			
			checkLetterAnderePlek(i);
			
			//setGeraden(i);
		}
		resetAlGenoemd();
		
	}
	
	// c is de letter vd code, i is de letter vd input
	public void checkLetterCorrect(int i, char c){
		// System.out.println("dit is een char: " + c); //test
		if (input.charAt(i) == c){
			System.out.println(input.charAt(i) + "(" + i + ")" + " staat op de goede plek");
		}
		
	}
	
	//*
	// check of input letter i op ergens van de j plekken staat
	public void checkLetterAnderePlek(int i){
		
		for(int j = 0; j < codeSize; j++){
			// check 2 condities:
			// 1. staat input letter i ergens tussen de correcte letters
			// 2.
			if ( input.charAt(i) == code.charAt(j) 
					&& input.charAt(i) != code.charAt(i) 
					&& !bevat(alGenoemd, input.charAt(i)) ){
				System.out.println("De " + input.charAt(i) + " zit erin, maar op een andere plek");
				
				alGenoemd += input.charAt(i);
				
				//System.out.println("alGenoemd: " + alGenoemd);
				
			}
		}
	}
	//*/
	
	
	public void setGeraden(int positie){
		System.out.println("de letter op positie " + positie + " klopt");
	}
	
	public void resetAlGenoemd(){
		for (int i = 0; i < codeSize; i++){
			alGenoemd = "";
		}
	}
	
	
	public void eindeSpel(){
		System.out.println("Beginnersgeluk, je hebt de code geraden...");
		System.out.println("Ik heb geen zin meer");
	}
	
	// eigen methode om snel te checken of een String een bepaalde char bevat
	public boolean bevat(String s, char c){
		for (int i = 0; i < s.length(); i++){
			if (s.charAt(i) == c){
				return true;
			}
		}
		
		return false;
	}
	
}
