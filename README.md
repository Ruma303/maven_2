```sql
CREATE DATABASE IF NOT EXISTS biblioteca;
USE biblioteca;

CREATE TABLE libri (
  id INT AUTO_INCREMENT PRIMARY KEY,
  titolo VARCHAR(100),
  autore VARCHAR(100),
  anno_pubblicazione INT,
  disponibile BOOLEAN
);

INSERT INTO libri (titolo, autore, anno_pubblicazione, disponibile) VALUES
  ('Il Signore degli Anelli', 'J.R.R. Tolkien', 1954, TRUE),
  ('Harry Potter e la Pietra Filosofale', 'J.K. Rowling', 1997, TRUE),
  ('Il Codice da Vinci', 'Dan Brown', 2003, FALSE),
  ('Il Grande Gatsby', 'F. Scott Fitzgerald', 1925, TRUE),
  ('1984', 'George Orwell', 1949, TRUE),
  ('Il Nome della Rosa', 'Umberto Eco', 1980, FALSE),
  ('Moby Dick', 'Herman Melville', 1851, TRUE),
  ('Orgoglio e Pregiudizio', 'Jane Austen', 1813, TRUE),
  ('Cime Tempestose', 'Emily Brontë', 1847, FALSE),
  ('Il Piccolo Principe', 'Antoine de Saint-Exupéry', 1943, TRUE);

CREATE TABLE lettori (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(50),
  cognome VARCHAR(50),
  email VARCHAR(100)
);

INSERT INTO lettori (nome, cognome, email) VALUES
  ('Mario', 'Rossi', 'mario.rossi@example.com'),
  ('Luisa', 'Bianchi', 'luisa.bianchi@example.com'),
  ('Giovanni', 'Verdi', 'giovanni.verdi@example.com'),
  ('Anna', 'Neri', 'anna.neri@example.com'),
  ('Paolo', 'Gialli', 'paolo.gialli@example.com');
```

