import java.util.Scanner;
import java.util.Arrays;
import java.util.Random;


public class Mastermind {
	Scanner sc = new Scanner(System.in);
	String input = "";
	
	int codeSize = 4;
	int pogingenToegestaan = 12;
	int pogingenGedaan = 0;
	String mode = "EASY";
	
	// boolean array die bijhoudt op welke posities de letters al goed geraden zijn en dus niet meer gecheckt te hoeven worden
	boolean[] alGoedGeraden;
	
	String lettersOpGoedePlek = "";
	String lettersOpVerkeerdePlek = "";
	
	String feedback = "";
	
	String code = "";// "ABCD";
	
	// bijhouden of er al gezegd is dat een letter op de verkeerde plek staat
	String alGenoemd = "";
	
	
	// ----- MAIN --------
	public static void main(String[] args) {
		Mastermind mm = new Mastermind();
		//System.out.println("bevat alex een e? " + bevat("alex", 'e')); //test
		//System.out.println("bevat alex een z? " + bevat("alex", 'z')); //test
		
		// GAME START
		mm.resetAlGenoemd();
		mm.selecteerMoeilijkheid();
		mm.initSpel();
		mm.initAlGoedGeraden();
		//System.out.println("algoedgeradeninit" + Arrays.toString(mm.alGoedGeraden)); //werkt
		mm.genereerCode();
		
		
		
		// GAME LOOP hier
		mm.spelen();
		
		
	}
	
	public void genereerCode(){
		//System.out.println("codesize: " + codeSize); test, oud
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
		
		System.out.println("Kies een moeilijkheidsgraad:");
		System.out.println("1 voor EASY MODE");
		System.out.println("2 voor HARD MODE");
		
		while (!keuzeGemaakt){
			input = sc.nextLine();
			
			switch (input){
			
			case "1": 
				System.out.println("Je hebt gekozen voor EASY MODE");
				System.out.println("Kies je altijd de makkelijkste opties in je leven?");
				mode = "EASY";
				keuzeGemaakt = true;
				break;
				
			case "2":
				System.out.println("Je hebt gekozen voor HARD MODE");
				System.out.println("Wist je dat 80% van de mensen denkt dat ze beter kunnen autorijden dan gemiddeld?");
				System.out.println("Maar ja, als jij HARD MODE wil spelen moet je dat zelf weten.");
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

	public void spelen(){
		
		toonInstructies();
		
		while (sc.hasNextLine()){
			
			input = sc.nextLine().toUpperCase();
			
			if (input.length() == codeSize || input.equals("Q")){
				System.out.println("Je hebt ingevoerd:\n" + input);
				// spel blijft hangen bij sommige specifieke combinaties... (zoals ABCDE)
				
				if (input.compareTo(code) == 0){
					eindeSpel();
					break;
				} else if (input.equals("Q")){
					System.out.println("Ik had al een vermoeden dat je zou opgeven");
					System.out.println("No offense, maar dit spel is eigenlijk alleen voor de volwassenen onder ons");
					System.out.println("THE END");
					System.exit(0);
				}
				else{
					foutAntwoord();
				}
			} else{
				System.out.println("Typ aub " + codeSize + " letters, van A tot F");
			}
		}
	}
	
	public void toonInstructies(){
		if (mode.equals("EASY")){
			System.out.println("----------- WELKOM BIJ MASTERMIND -----------");
			System.out.println("Ik heb een code van 4 letters in gedachten");
			System.out.println("Elke letter is een van de volgende letters: ABCDEF");
			System.out.println("Een letter kan meerdere keren voorkomen");
			System.out.println("Probeer de code maar te raden, als dat niet teveel moeite is...");
			System.out.println("Ik geef je 12 pogingen");
			System.out.println("Voer (q) in als wil stoppen");
		} else{
			System.out.println("----------- MASTERMIND VOOR GEVORDENEN -----------");
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
		if (pogingenGedaan >= pogingenToegestaan){
			System.out.println("Dat was de laatste poging, je mag niet meer raden.");
			System.out.println("Misschien past tic-tac-toe wat meer bij je eigen niveau?");
			System.exit(0);
		}
		//System.out.println("deze code is fout"); //OUD
		pogingenGedaan++;
		
		
		// voor elke input letter afzonderlijk (i)
		for(int i = 0; i < codeSize; i++){
			
			if (mode.equals("EASY")){
				checkLetterCorrect(i, code.charAt(i));
				checkLetterAnderePlek(i);
				
			} else{
				checkLetterCorrectHard(i, code.charAt(i));
			}
		}
		
		// aparte for loop om te zorgen dat eerst alle letters zijn gecheckt op goed geraden, en DAN pas of er letters op een andere plek staan
		if (mode.equals("HARD")){
			for(int i = 0; i < codeSize; i++){
				checkLetterAnderePlekHard(i);
			}
			
			// uncomment regel hieronder voor code die meestal bijna helemaal werkt
			//lettersOpVerkeerdePlek = dubbeleLettersFilter(); // laat programma soms nog hangen in de while loop van die methode :(
			
			printFeedbackHard();
		}
		

		
		System.out.println("Je hebt nog " + (pogingenToegestaan - pogingenGedaan) + " pogingen over");
		
		resetAlGoedGeraden();
		resetAlGenoemd(); 
		
		lettersOpGoedePlek = "";
		lettersOpVerkeerdePlek = "";
	
		
	}
	
	// =====================================================================      EASY MODE
	// c is de letter vd code, i is de letter vd input
	public void checkLetterCorrect(int i, char c){
		if (input.charAt(i) == c){
			System.out.println(input.charAt(i) + "(" + i + ")" + " staat op de goede plek");
			alGoedGeraden[i] = true;
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
	
	
	
	
	// ===============================================================================      HARD
	public void checkLetterCorrectHard(int i, char c){
		// System.out.println("dit is een char: " + c); //test
		if (input.charAt(i) == c){
			lettersOpGoedePlek += input.charAt(i);
			//System.out.println("lettersOpGoedePlek: " + lettersOpGoedePlek); //test
			alGoedGeraden[i] = true;
		}
		
	}
	
	// todo: methode herschrijven zodat lettersOpVerkeerdePlek eens per i kijkt, ipv ook nog per j
	// vertel speler niet welke letter op een andere plek staat
	
	public void checkLetterAnderePlekHard(int i){
		if ( !alGoedGeraden[i] // condition A
			&& bevat(code, input.charAt(i)) ){ // condition B
			//&&
			//System.out.println("character at " + i + ": " + input.charAt(i) + " zit ergens in de code, maar verkeerd"); //test
			lettersOpVerkeerdePlek += input.charAt(i);
		}
		
		// werkt bijna, maar als een letter eerst goed geraden is kan hij nog steeds ergens anders dubbel staan
		// wat er moet gebeuren: 
		// check na de goed geraden letter voor de andere input letters nog of er ergens anders alsnog diezelfde letter geraden meot worden
		
		// backup code van hierboven
		/*
		if ( !alGoedGeraden[i] // condition A
				&& bevat(code, input.charAt(i)) // condition B
				&& !bevat(lettersOpGoedePlek, input.charAt(i)) ){ // condition C
				System.out.println("character at " + i + ": " + input.charAt(i) + " zit ergens in de code, maar verkeerd");
			}
		*/
		
	}
	
	
	
	
	// ??
	/*
	public void setGeraden(int positie){
		System.out.println("de letter op positie " + positie + " klopt");
	}
	*/
	
	// oud, misschien weggooien
	// check voor elke letter in lettersOpVerkeerdePlek (k) of die al in lettersOpGoedePlek (l) zit
	public void haalGoedeLettersWeg(){
		String newFeedback = "";
		
		// voor elke letter op verkeerde plek
		for (int k = 0; k < lettersOpVerkeerdePlek.length(); k++){
			for (int l = 0; l < lettersOpGoedePlek.length(); l++){
				if (lettersOpVerkeerdePlek.charAt(k) == lettersOpGoedePlek.charAt(l)){
					//
				}
			}
		}
		//return feedback;
	}
	
	// nieuwste idee (werkt OOK al niet)
	public String dubbeleLettersFilter(){
		String filteredVerkeerdeLetters = lettersOpVerkeerdePlek;
		String filteredGoedeLetters = lettersOpGoedePlek;
		
		//System.out.println("filteredGoedeLetters" + filteredGoedeLetters); //test
		//System.out.println("filteredVerkeerdeLetters" + filteredVerkeerdeLetters); //test
		
		/// (!) huidige manier laat programma soms vastlopen 
		while (filteredVerkeerdeLetters.length() > 0){
			for (int i = 0; i < filteredVerkeerdeLetters.length(); i++){
				if ( bevat(filteredGoedeLetters, filteredVerkeerdeLetters.charAt(i)) ){
					//System.out.println("test, i = " + i);
					
					String dezeLetter = filteredVerkeerdeLetters.charAt(i) + "";
					filteredVerkeerdeLetters = filteredVerkeerdeLetters.replaceAll(dezeLetter, "");
					//System.out.println("HIER");
					//System.out.println(filteredVerkeerdeLetters.charAt(i) + " zit nu in de goed geraden letters " + filteredGoedeLetters);
				}
			}
		}
		
		return filteredVerkeerdeLetters;
	}
	
	public void printFeedbackHard(){
		System.out.println("Aantal letters goed geraden: " + lettersOpGoedePlek.length());
		System.out.println("Aantal letters op de verkeerde plek: " + lettersOpVerkeerdePlek.length());
	}
	
	public void initAlGoedGeraden(){
		alGoedGeraden = new boolean[codeSize];
		for (int i = 0; i < alGoedGeraden.length; i++){
			alGoedGeraden[i] = false;
		}
	}
	
	public void resetAlGoedGeraden(){
		for (int i = 0; i < codeSize; i++){
			alGoedGeraden[i] = false;
		}
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
	public static boolean bevat(String s, char c){
		for (int i = 0; i < s.length(); i++){
			if (s.charAt(i) == c){
				return true;
			}
		}
		
		return false;
	}
	
}
