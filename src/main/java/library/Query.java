package library;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import app.DatabaseManager;

public class Query {

	public static void add(Libro libro) {
		String query = "INSERT INTO libri (titolo, autore, anno_pubblicazione, disponibile) VALUES (?, ?, ?, ?)";

		try (Connection db = DatabaseManager.getConnection(); PreparedStatement p = db.prepareStatement(query)) {

			p.setString(1, libro.getTitolo());
			p.setString(2, libro.getAutore());
			p.setInt(3, libro.getAnnoPubblicazione());
			p.setBoolean(4, libro.getDisponibile());

			p.executeUpdate();
			System.out.println("Nuovo libro inserito:\t" + libro.dettagliLibro());

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	public ResultSet list() {
		String query = "SELECT * FROM libri";

		try {
			Connection db = DatabaseManager.getConnection();
			PreparedStatement p = db.prepareStatement(query);
			return p.executeQuery();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

	public ResultSet findById(Integer id) {
	    String query = "SELECT * FROM libri WHERE id = ?";

	    try {
	        Connection db = DatabaseManager.getConnection();
	        PreparedStatement p = db.prepareStatement(query);
	        
	        p.setInt(1, id); 
	        ResultSet rs = p.executeQuery();  
	        return rs; 

	    } catch (SQLException e) {
	        System.err.println("Errore SQL: " + e.getMessage());
	    }
	    return null; 
	}

	public ResultSet findByTitolo(String titolo) {
	    String query = "SELECT * FROM libri WHERE titolo LIKE ?";
	    try {
	        Connection db = DatabaseManager.getConnection();
	        PreparedStatement p = db.prepareStatement(query);
	        p.setString(1, "%" + titolo + "%");
	        return p.executeQuery();
	    } catch (SQLException e) {
	        System.err.println(e.getMessage());
	    }
	    return null;
	}

	public void borrowBook(Integer idLibro, Integer idLettore) {
	    String queryLibro = "UPDATE libri SET disponibile = false WHERE id = ?";
	    String queryLettore = "SELECT * FROM lettori WHERE id = ?";

	    try (Connection db = DatabaseManager.getConnection();
	         PreparedStatement pLibro = db.prepareStatement(queryLibro);
	         PreparedStatement pLettore = db.prepareStatement(queryLettore)) {

	        pLettore.setInt(1, idLettore);
	        ResultSet rsLettore = pLettore.executeQuery();

	        if (!rsLettore.next()) { 
	            System.out.println("Lettore con ID " + idLettore + " non trovato.");
	            return;
	        }

	        pLibro.setInt(1, idLibro);
	        int rowsUpdated = pLibro.executeUpdate();

	        if (rowsUpdated > 0) {
	            System.out.println("Hai prestato il libro a " + rsLettore.getString("nome"));
	        } else {
	            System.out.println("Libro con ID " + idLibro + " non trovato o già non disponibile.");
	        }

	    } catch (SQLException e) {
	        System.err.println("Errore SQL: " + e.getMessage());
	    }
	}

	public void returnBook(Integer idLibro) {
		String queryLibro = "UPDATE libri SET disponibile = true WHERE id = ?";

		try (Connection db = DatabaseManager.getConnection();
				PreparedStatement pLibro = db.prepareStatement(queryLibro);
		) {
			pLibro.setInt(1, idLibro);
			pLibro.executeUpdate();

			System.out.println("Libro con id: " + idLibro + " ritornato!");
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
}
