# **Creazione di un progetto Maven da eseguire**

**NB**: La seguente è un'applicazione Java/Maven con MySQL come backend. A questo potrà essere aggiunto frontend o delle interfacce grafiche.

## **1. Database di esempio**

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

## **Creare un Jar eseguibile**

Quando la nostra applicazione Maven è pronta possiamo possiamo archiviarla in un Jar eseguibile.

### **1. Istruzioni di building**

Come visto in precedenza, assicuriamoci di aver inserito le istruzioni di build nel `pom.xml`. Esempio:

```xml
<build>
    <plugins>
        <!-- Plugin per generare il Fat JAR -->
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-shade-plugin</artifactId>
            <version>3.2.4</version>
            <executions>
                <execution>
                    <phase>package</phase>
                    <goals>
                        <goal>shade</goal>
                    </goals>
                    <configuration>
                        <!-- Unire il Manifest per avvio corretto -->
                        <transformers>
                            <transformer implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
                                <!-- Sostituire con il nome package.Classe d'avvio -->
                                <mainClass>app.Main</mainClass>
                            </transformer>
                        </transformers>
                    </configuration>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

### 2. **Eseguire il building**

Creiamo una build eseguendo il comando:

```sh
mvn clean package
```

Questo comando pulisce la directory `target` (se esiste) e quindi esegue la compilazione e il packaging del progetto, creando un JAR nella cartella `target/`.

### 3. **Controllare la cartella `target`**

Una volta che il comando di build è stato eseguito correttamente, dovremmo vedere la cartella `target` nella struttura del progetto. Possiamo verificarlo con:

```sh
ls target
```

Dentro questa cartella, dovremmo trovare il JAR eseguibile (esempio: `maven_2-0.0.1-SNAPSHOT.jar`).

### 4. **Verificare il contenuto del JAR**

Se il JAR è presente, possiamo verificarne il contenuto con il comando:

```sh
jar tf target/maven_2-0.0.1-SNAPSHOT.jar
```

Questo mostrerà l'elenco dei file contenuti nel JAR.

### 5. **Eseguire l'applicazione**

Se il JAR è stato creato correttamente, possiamo eseguire l'app con il comando:

```sh
java -jar target/maven_2-0.0.1-SNAPSHOT.jar
```

Se il comando sopra non funziona ancora, controllare se ci sono errori durante la fase di build. Se ci sono problemi con il `maven-shade-plugin` o altre dipendenze, potrebbero esserci errori nel log della build che ti daranno indicazioni utili.

---

## **Creare applicazioni eseguibili da Maven**

Creare applicazioni eseguibili da Maven non è così comune ma è comunque possibile. A causa di alcuni plugin non più supportati, è  consigliato usare `jpackage`, che è ufficialmente supportato da **Java 14+** ed è molto utile per creare pacchetti nativi (applicazioni macOS `.app`, Windows `.exe` e Linux) direttamente dal JAR.

### **1. Aggiungere il plugin `jpackage` al pom.xml**

`jpackage` è un tool integrato in JDK 14+ (quindi non è un plugin Maven separato) e può essere utilizzato direttamente dalla linea di comando, senza bisogno di modificare il pom.xml per aggiungere un plugin. Tuttavia, per la creazione di pacchetti nativi su ogni sistema operativo, dovremmo eseguire il comando direttamente tramite Maven o da terminale.

### **2. Eseguire il comando `jpackage` direttamente dalla linea di comando**

Supponiamo che il nostro JAR si chiami `maven_app-0.0.1-SNAPSHOT.jar`:

- **Per Windows**:

	```sh
	jpackage --name NomeApplicazione --input target --main-jar maven_app-0.0.1-SNAPSHOT.jar --type exe
	```

- **Per macOS**:

	```sh
	jpackage --name NomeApplicazione --input target --main-jar maven_app-0.0.1-SNAPSHOT.jar --type app-image
	jpackage --name NomeApplicazione --input target --main-jar maven_app-0.0.1-SNAPSHOT.jar --type dmg # Oppure
	```

- **Per Linux**:

	```sh
	jpackage --name NomeApplicazione --input target --main-jar maven_app-0.0.1-SNAPSHOT.jar --type deb
	```

### **3. Esecuzione dell'applicazione**

L'applicazione in questione è una normale CLI avviabile da terminale; non utilizza alcun GUI o framework per il front-end. Una volta che il pacchetto è stato creato, esploriamolo alla ricerca del file in formato eseguibile nel terminale.

Esempio macOS:

```sh
/path/to/app/NomeApplicazione.app/Contents/MacOS/Biblioteca
```
