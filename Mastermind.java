import java.util.Scanner;
import java.util.Arrays;
import java.util.Random;


public class Mastermind {
	Scanner sc = new Scanner(System.in);
	String input = "";
	String code = "";// "ABCD";
	
	int codeSize = 4;
	int pogingenToegestaan = 12;
	int pogingenGedaan = 0;
	String mode = "EASY";
	
	String feedback = ""; //??
	String alGenoemd = "";
	
	int aantalLettersGoed = 0;
	int aantalLettersVerkeerd = 0;
	
	
	StringBuilder codeCopy = new StringBuilder("");
	StringBuilder inputCopy = new StringBuilder("");
	
	
	// ----- MAIN --------
	public static void main(String[] args) {
		Mastermind mm = new Mastermind();
		
		// GAME START
		mm.resetAlGenoemd();
		mm.selecteerMoeilijkheid();
		mm.initSpel();
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
		System.out.println("Wil je de code weten? [Y/N]");
		String antwoord = sc.nextLine().toUpperCase();
		if (antwoord.equals("Y"))
			System.out.println("De code is " + code);
	}
	
	public void selecteerMoeilijkheid(){
		boolean keuzeGemaakt = false;
		
		System.out.println("Kies een moeilijkheidsgraad:");
		System.out.println("1 voor EASY MODE");
		System.out.println("2 voor HARD MODE");
		
		while (!keuzeGemaakt){
			input = sc.nextLine();
			
			switch (input){
			
			case "1": 
				System.out.println("Je hebt gekozen voor EASY MODE");
				System.out.println("Kies je altijd de makkelijkste opties in je leven?\n");
				mode = "EASY";
				keuzeGemaakt = true;
				break;
				
			case "2":
				System.out.println("Je hebt gekozen voor HARD MODE");
				System.out.println("Wist je dat 80% van de mensen denkt dat ze beter kunnen autorijden dan gemiddeld?\n");
				mode = "HARD";
				keuzeGemaakt = true;
				break;
				
			default: System.out.println("kies 1 of 2... (misschien is dit spel voor jou toch te hoog gegrepen?)");
				
			}
		}
		
	}
	
	public void initSpel(){
		if (mode.equals("EASY")){
			codeSize = 4;
			pogingenToegestaan = 12;
		}else {
			codeSize = 5;
			pogingenToegestaan = 10;
		}
		
	}

	
	//--------------------------------- SPELEN ---------------------------------------------
	
	public void spelen(){
		
		toonInstructies();
		
		while (sc.hasNextLine()){
			
			input = sc.nextLine().toUpperCase();
			
			if (input.length() == codeSize || input.equals("Q")){
				System.out.println("Je hebt ingevoerd:\n" + input);
				
				inputCopy.replace(0, inputCopy.length(), input);
				codeCopy.replace(0, codeCopy.length(), code);
				aantalLettersGoed = 0;
				aantalLettersVerkeerd = 0;
				
				if ( inputCopy.toString().compareTo(codeCopy.toString()) == 0){
					eindeSpel();
					break;
				} else if (inputCopy.equals("Q")){
					spelerGeeftOp();
				}
				else{
					foutAntwoord();
				}
			} else{
				System.out.println("Typ aub " + codeSize + " letters, van A tot F...");
			}
		}
	}
	
	public void toonInstructies(){
		if (mode.equals("EASY")){
			System.out.println("\n----------- WELKOM BIJ MASTERMIND -----------");
			System.out.println("Ik heb een code van 4 letters in gedachten");
			System.out.println("Elke letter is een van de volgende letters: ABCDEF");
			System.out.println("Een letter kan meerdere keren voorkomen");
			System.out.println("Probeer de code maar te raden, als dat niet teveel moeite is...");
			System.out.println("Ik geef je 12 pogingen");
			System.out.println("Voer (q) in als wil stoppen");
		} else{
			System.out.println("\n----------- MASTERMIND VOOR GEVORDENEN -----------");
			System.out.println("Ik heb een code van 5 letters in gedachten");
			System.out.println("Elke letter is een van de volgende letters: ABCDEF");
			System.out.println("Een letter kan meerdere keren voorkomen");
			System.out.println("De twist is hier dat ik niet vertel welke letters goed of verkeerd staan, alleen hoeveel");
			System.out.println("Probeer de code maar te raden (tip: geef gelijk op, en ga maar Netflix kijken of zo)");
			System.out.println("Ik geef je 10 pogingen");
			System.out.println("Voer (q) in als wil stoppen");
		}
	}
	
	// slordige code, maar werkend krijgen gaat even voor
	public void foutAntwoord(){
		
		updatePogingenGedaan();
		
		// voor elke input letter afzonderlijk (i)
		
			
			if (mode.equals("EASY")){
				for(int i = 0; i < codeSize; i++){
					
					checkLetterCorrect(i, code.charAt(i));
					checkLetterAnderePlek(i);
				}
				
			} else{ // HARD MODE
				for(int i = 0; i < inputCopy.length(); i++){ //oorspronkelijke loop
					while(inputCopy.length() > i && inputCopy.charAt(i) == codeCopy.charAt(i)){
					//System.out.println("i: " + i);
				//	System.out.println("codeCopy.charAt(i): " + codeCopy.charAt(i)); //test
					checkLetterCorrectHard(i, codeCopy.charAt(i));
					//System.out.println("check " + i); // test
					}
				}
			}
		
		// aparte for loop om te zorgen dat eerst alle letters zijn gecheckt op goed geraden, en DAN pas of er letters op een andere plek staan
		if (mode.equals("HARD")){
			for(int i = 0; i < inputCopy.length(); i++){ //oud: codeSize
				checkLetterAnderePlekHard(i);
			}
			//System.out.println("inputcopy length: " + inputCopy.length());
			//System.out.println("inputcopy: " + inputCopy);

			
			printFeedbackHard();
		}
		

		
		System.out.println("Je hebt nog " + (pogingenToegestaan - pogingenGedaan) + " pogingen over");
		
	}
	
	// =====================================================================      EASY MODE
	// c is de letter vd code, i is de letter vd input
	public void checkLetterCorrect(int i, char c){
		if (input.charAt(i) == c){
			System.out.println(input.charAt(i) + "(" + i + ")" + " staat op de goede plek");
			aantalLettersGoed++;
		}
		
	}
	
	// check of input letter i op ergens van de j plekken staat
	public void checkLetterAnderePlek(int i){
		
		for(int j = 0; j < codeSize; j++){
			if ( input.charAt(i) == code.charAt(j) 
					&& input.charAt(i) != code.charAt(i) 
					&& !bevat(alGenoemd, input.charAt(i)) ){
				System.out.println("De " + input.charAt(i) + " zit erin, maar op een andere plek");
				
				alGenoemd += input.charAt(i);
			}
		}
	}
	
	
	
	
	// ===============================================================================      HARD MODE
	public void checkLetterCorrectHard(int i, char c){
		//System.out.println("hoi");
		if (inputCopy.charAt(i) == c){
			//System.out.println("inputCopy.charAt(i)" + inputCopy.charAt(i)); //test
			//System.out.println("c = " + c); //test
			
			//System.out.println("je hebt een lettehwr goed geradenwhblblblbl");
			aantalLettersGoed++;
			
			inputCopy.delete(i, i+1);
			codeCopy.delete(i, i+1);
			
			//System.out.println("na het verwijderen is inputcopy nu: " + inputCopy);
			//System.out.println("na het verwijderen is codecopy nu: " + codeCopy);
		}
		
	}
	
	// todo: methode herschrijven zodat lettersOpVerkeerdePlek eens per i kijkt, ipv ook nog per j
	// vertel speler niet welke letter op een andere plek staat
	
	public void checkLetterAnderePlekHard(int i){
			if (bevat(codeCopy.toString(), inputCopy.charAt(i))){
				aantalLettersVerkeerd++;
			}
	}


	
	public void printFeedbackHard(){
		if (aantalLettersGoed > 0)
		System.out.println("Je hebt " + aantalLettersGoed + " letters goed.");
		if (aantalLettersVerkeerd > 0)
		System.out.println("Er zitten " + aantalLettersVerkeerd + " letters wel in de code, maar op een andere plek.");
		
		if (aantalLettersGoed == codeSize){
			eindeSpel();
		}
	}	
	
	public void updatePogingenGedaan(){
		if (pogingenGedaan >= pogingenToegestaan){
			System.out.println("Dat was de laatste poging, je mag niet meer raden.");
			System.out.println("Misschien past tic-tac-toe wat meer bij je eigen niveau?");
			System.exit(0);
		}
		//System.out.println("deze code is fout"); //OUD
		pogingenGedaan++;
	}
	
	public void spelerGeeftOp(){
		System.out.println("Ik had al een vermoeden dat je zou opgeven");
		System.out.println("No offense, maar dit spel is eigenlijk alleen voor de volwassenen onder ons");
		System.out.println("THE END");
		System.exit(0);
	}
	
	public void resetAlGenoemd(){
		for (int i = 0; i < codeSize; i++){
			alGenoemd = "";
		}
	}
	
	public void eindeSpel(){
		System.out.println("Beginnersgeluk, je hebt de code geraden...");
		System.out.println("Ik heb geen zin meer");
		System.exit(0);
	}
	
	// eigen methode om snel te checken of een String een bepaalde char bevat
	public static boolean bevat(String s, char c){
		for (int i = 0; i < s.length(); i++){
			if (s.charAt(i) == c){
				return true;
			}
		}
		
		return false;
	}
	
}
