package app;

import java.sql.SQLException;
import java.util.ArrayList;

/*
 * Dovrete sviluppare un programma Java che consenta di gestire i libri di una biblioteca, permettendo di:
1. Aggiungere un libro.
2. Visualizzare tutti i libri disponibili.
3. Ricercare un libro per titolo o ID.
4. Prestare un libro a un lettore.
5. Restituire un libro.
*/

import java.util.Scanner;
import library.Lettore;
import library.Libro;
import library.GestoreBiblioteca;

public class Main {
	private static Scanner scanner = new Scanner(System.in);
	private static ArrayList<Lettore> listaLettori = new ArrayList<Lettore>();

	private static int id;
	private static String titolo;
	private static String autore;
	private static int annoPubblicazione;
	private static boolean disponibile = true;

	private static String scelta;
	private static GestoreBiblioteca gestore = new GestoreBiblioteca();
	private static DatabaseManager db = new DatabaseManager();

	// ----------------------------------------------------------------------------------------------------

	// Entry point dell'applicazione
	public static void main(String[] args) {
		// Creo gestore biblioteca
		initListaLettori();

		// Avvio il menu
		
		try {
			menu();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}

		// Chiudo lo scanner e termino il programma
		scanner.close();
		System.out.println("Programma terminato");
	}

	// ----------------------------------------------------------------------------------------------------

	// Menu dell'applicazione
	private static void menu()  throws SQLException {
		do {
			// Stampo il menu
			printMenu();
			scelta = scanner.nextLine();

			switch (scelta) {
			// Aggiungo libro
			case "1":
				// Inserisco parametri libro
				System.out.print("Inserire titolo: ");
				titolo = scanner.nextLine();
				System.out.print("Inserire autore: ");
				autore = scanner.nextLine();
				annoPubblicazione = getInt("Inserire anno pubblicazione: ");

				Libro libro = new Libro(listaLettori.size() + 1, titolo, autore, annoPubblicazione, disponibile);
				gestore.aggiungiLibro(libro);
				break;

			// Mostro tutti i libri
			case "2":
				gestore.mostraLibri();
				break;

			// Cerco un libro
			// Cerco un libro
			case "3":
				System.out.print("Cerca inserendo titolo oppure ID: ");
				String choice = scanner.nextLine();
				parseChoice(choice);
				break;

			// Prestito libro
			case "4":
				if (listaLettori.isEmpty()) {
					System.out.println("Non ci sono lettori disponibili.");
				} else {
					mostraLettori();
					int idLibro = getInt("Inserire ID libro: ");
					int idLettore = getInt("Inserire ID lettore: ");
					gestore.prestitoLibro(idLibro, idLettore);
				}
				break;
			// Restituisco libro
			case "5":
				id = getInt("Inserire ID libro: ");
				gestore.restituisciLibro(id);
				break;

			// Esco
			case "6":
				break;

			// Stampo messaggio opzione non valida
			default:
				System.out.println("Inserire un'opzione valida.");
				break;
			}

			System.out.println("-".repeat(50));
		} while (!scelta.equals("6"));

		// Chiudo connessione
		db.close();
	}

	// ----------------------------------------------------------------------------------------------------

	// Stampa delle voci del menu
	private static void printMenu() {
		final String MENU_WELCOME = "\nBenvenuto nella biblioteca.";
		final String[] MENU_OPTIONS = { "Aggiungi libro", "Mostra tutti i libri", "Cerca un libro", "Prestito libro",
				"Restituisci libro", "Esci" };

		System.out.println(MENU_WELCOME);
		for (int i = 0; i < MENU_OPTIONS.length; i++)
			System.out.println(String.format("%d. %s", i + 1, MENU_OPTIONS[i]));

		System.out.print("Scelta: ");
		System.out.print("\n");
	}

	// ----------------------------------------------------------------------------------------------------

	// Metodo per ottenere un intero senza causare eccezioni
	private static int getInt(String placeholder) {
		do {
			System.out.print(placeholder);
			try {
				return Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Inserire un valore corretto.");
			}
		} while (true);
	}

	// Metodo che analizza la scelta dell'utente per la ricerca del libro
	private static void parseChoice(String choice) throws SQLException {
		choice = choice.trim(); 
		try {
			Integer choiceID = Integer.parseInt(choice);
			gestore.cercaLibro(choiceID);
			
		} catch (NumberFormatException e) {
			String choiceTitle = String.valueOf(choice);
			gestore.cercaLibro(choiceTitle);
		}
	}

	// Metodo che inizializza la lista di lettori
	private static void initListaLettori() {
		int id = 0;

		listaLettori.add(new Lettore(id++, "Mario", "Rossi", "mario.rossi@mail.com"));
		listaLettori.add(new Lettore(id++, "Gioacchino", "Fenice", "joaquin.phoenix@tantiSaluti.com"));
		listaLettori.add(new Lettore(id++, "Gennaro", "Savastano", "jenny.savastano@waglio.com"));
	}

	// ----------------------------------------------------------------------------------------------------

	// Metodo che visualizza tutti i lettori
	private static void mostraLettori() {
		for (Lettore lettore : listaLettori)
			lettore.mostraLettore();
	}

	// ----------------------------------------------------------------------------------------------------

	// Metodo che cerca e restituisce un lettore per ID
	private static Lettore cercaLettore(int id) {
		for (Lettore lettore : listaLettori) {
			if (lettore.getId() == id)
				return lettore;
		}

		return null;
	}
}