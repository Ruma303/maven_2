package library;

/*+--------------------+--------------+------+-----+---------+----------------+
| Field              | Type         | Null | Key | Default | Extra          |
+--------------------+--------------+------+-----+---------+----------------+
| id                 | int          | NO   | PRI | NULL    | auto_increment |
| titolo             | varchar(100) | YES  |     | NULL    |                |
| autore             | varchar(100) | YES  |     | NULL    |                |
| anno_pubblicazione | int          | YES  |     | NULL    |                |
| disponibile        | tinyint(1)   | YES  |     | NULL    |                |
+--------------------+--------------+------+-----+---------+----------------+*/

public class Libro {

	private Integer id;
	private String titolo;
	private String autore;
	private int annoPubblicazione;
	private boolean disponibile;

	public Libro(int id, String titolo, String autore, int annoPubblicazione, boolean disponibile) {
		this.id = id;
		this.titolo = titolo;
		this.autore = autore;
		this.annoPubblicazione = annoPubblicazione;
		this.disponibile = disponibile;
	}

	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getAutore() {
		return autore;
	}

	public void setAutore(String autore) {
		this.autore = autore;
	}

	public int getAnnoPubblicazione() {
		return annoPubblicazione;
	}

	public void setAnnoPubblicazione(int annoPubblicazione) {
		this.annoPubblicazione = annoPubblicazione;
	}

	public boolean getDisponibile() {
		return this.disponibile;
	}

	public void setDisponibile(boolean disponibile) {
		this.disponibile = disponibile;
	}

	public String dettagliLibro() {
		return "ID: " + id + "| Titolo: " + titolo + "| Autore: " + autore + "| Anno di pubblicazione: "
				+ annoPubblicazione + "| Disponibile: " + (disponibile ? "Si" : "No");
	}
}
