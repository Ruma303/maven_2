package library;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
aggiungiLibro(Libro libro)
mostraLibri()
cercaLibro(String titolo)
prestitoLibro(int idLibro, Lettore lettore)
restituisciLibro(int idLibro)
*/

public class GestoreBiblioteca {

	private List<Libro> catalogo;
	private Query query;

	// Costruttore pubblico che chiama quello privato
	public GestoreBiblioteca() {
		this(new ArrayList<>(), new Query());
	}

	// Costruttore privato che esegue il caricamento dal database
	private GestoreBiblioteca(List<Libro> catalogo, Query query) {
		this.catalogo = catalogo;
		this.query = query;
		caricaLibri();
	}

	// Metodo per caricare i libri dal database
	private void caricaLibri() {
		try {
			ResultSet libri = query.list();
			if (libri == null) {
				System.err.println("Errore: nessun libro caricato dal database.");
				return;
			}
			while (libri.next()) {
				catalogo.add(new Libro(libri.getInt("id"), libri.getString("titolo"), libri.getString("autore"),
						libri.getInt("anno_pubblicazione"), libri.getBoolean("disponibile")));
			}
		} catch (SQLException e) {
			System.err.println("Errore nel caricamento dei libri: " + e.getMessage());
		}
	}

	public void aggiungiLibro(Libro libro) {
		catalogo.add(libro);
		query.add(libro);
	}

	public void mostraLibri() throws SQLException {
		if (!exists())
			return;
		ResultSet rs = query.list();

		while (rs.next()) {
			System.out.println("\t" + rs.getInt("id") + ". " + " Titolo: " + rs.getString("titolo") + " Autore: "
					+ rs.getString("autore") + " Anno pubblicazione: " + rs.getString("anno_pubblicazione")
					+ " Disponibile: " + rs.getBoolean("disponibile"));
		}
	}

	public void cercaLibro(Object input) throws SQLException {
		if (!exists())
			return;

		if (input instanceof Integer) {
			Integer id = (Integer) input;
			ResultSet rs = query.findById(id);

			if (rs != null && rs.next()) {
				System.out.println("Libro trovato!");
				System.out.println("\t" + rs.getInt("id") + ". " + " Titolo: " + rs.getString("titolo") + " Autore: "
						+ rs.getString("autore") + " Anno pubblicazione: " + rs.getString("anno_pubblicazione")
						+ " Disponibile: " + rs.getBoolean("disponibile"));
			} else {
				System.out.println("Nessun libro trovato con ID " + id);
			}

			if (rs != null)
				rs.close();

		} else if (input instanceof String) {
			String titolo = (String) input;
			ResultSet rs = query.findByTitolo(titolo);

			if (rs != null && rs.next()) {
				System.out.println("Libro trovato!");
				System.out.println("\t" + rs.getInt("id") + ". " + " Titolo: " + rs.getString("titolo") + " Autore: "
						+ rs.getString("autore") + " Anno pubblicazione: " + rs.getString("anno_pubblicazione")
						+ " Disponibile: " + rs.getBoolean("disponibile"));
			} else {
				System.out.println("Nessun libro trovato con il titolo: " + titolo);
			}

			if (rs != null)
				rs.close();
		} else {
			System.err.println("Input non compatibile!");
		}
	}

	public void prestitoLibro(Integer idLibro, Integer idLettore) {
		if (!exists())
			return;

		if (idLibro == null || idLettore == null) {
			System.out.println("Non hai passato ID libro e/o lettore!");
			return;
		}

		boolean libroTrovato = false;

		for (Libro libro : catalogo) {
			if (libro.getId().equals(idLibro)) {
				libroTrovato = true;
				if (libro.getDisponibile()) {
					libro.setDisponibile(false);
					query.borrowBook(idLibro, idLettore);
					System.out.println("Il libro \"" + libro.getTitolo() + "\" è stato prestato con successo!");
				} else {
					System.out.println("Il libro \"" + libro.getTitolo() + "\" non è disponibile!");
				}
				break;
			}
		}

		if (!libroTrovato) {
			System.out.println("❌ Nessun libro trovato con ID: " + idLibro);
		}
	}

	public void restituisciLibro(Integer idLibro) {
	    if (!exists()) return;

	    if (idLibro == null) {
	        System.out.println("❌ Non hai passato un ID libro valido!");
	        return;
	    }

	    boolean libroTrovato = false;

	    for (Libro libro : catalogo) {
	        if (libro.getId().equals(idLibro)) {
	            libroTrovato = true;

	            if (!libro.getDisponibile()) {
	                query.returnBook(idLibro);
	                System.out.println("Il libro \"" + libro.getTitolo() + "\" è stato restituito con successo!");
	            } else {
	                System.out.println("Il libro \"" + libro.getTitolo() + "\" era già disponibile!");
	            }

	            break; 
	        }
	    }

	    if (!libroTrovato) {
	        System.out.println("❌ Nessun libro trovato con ID: " + idLibro);
	    }
	}

	public boolean exists() {
		ResultSet rs = query.list();
		if (!catalogo.isEmpty() && rs != null)
			return true;

		System.out.println("Non ci sono libri!");
		return false;
	}
}
