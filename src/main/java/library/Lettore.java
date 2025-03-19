package library;

/*+---------+--------------+------+-----+---------+----------------+
| Field   | Type         | Null | Key | Default | Extra          |
+---------+--------------+------+-----+---------+----------------+
| id      | int          | NO   | PRI | NULL    | auto_increment |
| nome    | varchar(50)  | YES  |     | NULL    |                |
| cognome | varchar(50)  | YES  |     | NULL    |                |
| email   | varchar(100) | YES  |     | NULL    |                |
+---------+--------------+------+-----+---------+----------------+
*/
public class Lettore {
	
	private Integer id;
	private String nome;
	private String cognome;
	private String email;
	
	public Lettore(String nome, String cognome, String email) {
		this.id = null;
		this.nome = nome;
		this.cognome = cognome;
		this.email = email;
	}
	
	public Lettore(Integer id, String nome, String cognome, String email) {
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.email = email;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void mostraLettore() {
        System.out.println("ID: " + id + " | Nome: " + nome + " | Cognome: " + cognome + " | Email: " + email);
    }
}
