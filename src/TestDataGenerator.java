import java.sql.*;
import java.util.Random;

import oracle.jdbc.driver.*;

public class TestDataGenerator {

    // Strings zu Generierung
    final static String[] VornListe = { "Liam", "Laura", "Milan", "Julia", "Julian", "Emilia", "Jonas", "Lea", "Elias", "Lina", "Linus", "Anna", "Levi", "Sarah", "Tim", "Lena", "Michael", "Elena" };
    final static String[] NachnListe = { "Gruber", "Huber", "Wagner", "Pichler", "Müller", "Steiner", "Baumgartner", "Eder", "Weber", "Schneider", "Reiter", "Eder", "Winkler", "Egger", "Haider" };
    final static String[] pwdListe = { "hallo", "passwort", "hallo123", "schalke04", "passwort1", "quertz", "schatz" };
    final static String[] mailDomain = { "@standard.at", "@yahoo.com", "@gmx.at" };
    final static String[] ResortListe = { "International", "Inland", "Wirtschaft", "Web", "Sport", "Panorama", "Etat", "Kultur", "Wissenschaft", "Gesundheit", "Bildung", "Reisen", "Lifestyle" };
    final static String[] UserListe = { "2Star_Princess", "Cool_guy", "5th_angel", "CastBound", "Twin_butterfly_LordOfMud", "Angel_twins", "Battledoom", "Cute_sugar", "Born2Pizza", "Cugary_pie",	"DroolingOnU", "Cugary_cake", "WarlockOPain", "PieSweetnes", "Greek-God",
            "AngelCandy", "Entertain-me", "Angel Doll", "Romeo", "AngelFriend", "Genius-General", "Angel_Cherub", "Lord-voldemort", "Angel_Goldfish", "Kunning-king", "Angel.Girl", "Born-confused", "Angel_Honeybear", "e4envy", "Angel_Memories", "Meat_Duck", "Angel_Munchkin",
            "Popoff", "Angel.Lamb", "Mistalee", "Angel_Snowflakes", "Jaycee", "Apple_Honeypie", "Max", "Angel_Sweet_Lips", "FuNkY_mOnEy", "Angelberry", "Wokie", "Angel", "Froggie", "Pokie", "Angel_Wonderland", "Lil Okie","Angel_Sweetie_Pie", "Whack_Attack", "Angel_Wonderland_Brads",
            "Angel_Cherub", "Manic_Psycho", "Angel_Doll", "PoPkiss", "AwesomeGirls", "BadAss", "AwesomeAngel", "Afro-head", "AwesomeChocolate", "meNcitY", "AwesomePie", "GlowNShow", "Awesome lavender", "Gozmit", "Bee_Buckshot", "Beloved_Fiddlesticks", "Babykins", "Squirrel Nuts",
            "bearhugs", "Bull_Frog", "Beautiful_Dog_Bone", "Beauty_Dixie", "BearGodfather", "Babykins", "Hangman", "Blossom_CyberWarrior", "Bubbles_CyberKing", "Buttercup", "Exotica", "Brownie", "Oblivion", "Bubbly", "Candy Cane", "Beautiful_doll", "Cybertron", "Bliss", "SmartGeek",
            "Bubblegum", "Hypnosis", "Butterscotch", "NetFreak" };
    final static String[] TitelListe = { "ist angesagt", "braucht Hilfe", "muss erneuert werden", "um die Welt!" };

    static Random rand;
    static Connection con;
    static Statement stmt;
    static String[]Verfasser_FK = new String [250];
    static String[]Username_FK = new String [250];
    static int[] ArtikelID_FK = new int [250];
    static int[] KommentarId_FK = new int [250];
    static int AutorIn = 0;
    static int Username = 0;
    static int ArtikelID = 0;
    static int KommentarID = 0;

    public static void insertAutorIn(int AnzAutorIn) {

        for (int i = 0; i < AnzAutorIn; i++) {
            AutorIn++;
            String Vorn = VornListe[rand.nextInt(VornListe.length)];
            String Nachn = NachnListe[rand.nextInt(NachnListe.length)];
            String email = Vorn + "." + Nachn + Integer.toString(rand.nextInt(100)) + mailDomain[rand.nextInt(mailDomain.length)];
            String pwd = pwdListe[rand.nextInt(pwdListe.length)];
            String Abk = new StringBuilder().append(Vorn.charAt(0)).append(Vorn.charAt(1)).append(Vorn.charAt(2)).append(Nachn.charAt(0)).append(Nachn.charAt(1)).toString();
                            Abk = Abk + AutorIn;
            Verfasser_FK[i] = Abk;
            String Resort = ResortListe[rand.nextInt(ResortListe.length)];

            try {
                String insertSql = "INSERT INTO AutorIn (Vorname, Nachname, Email, Passwort, Abkuerzung, Resort) VALUES ('" + Vorn + "', '" + Nachn + "', '" + email + "', '" + pwd + "', '" + Abk + "', '" + Resort + "')";
                //System.out.println(insertSql);
                stmt.executeUpdate(insertSql);
            } catch (Exception e) {
                System.err.println("Fehler beim Einfuegen des Datensatzes: " + e.getMessage());
                //System.err.println("Fehler beim Einfuegen des Datensatzes: " + e.toString());
            }
        }

        try {
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM AutorIn");
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println("Datensätze erfolgreich in \"AutorIn\" eingefügt: " + count);
            }
            rs.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static void insertNutzerIn (int AnzNutzerIn) {

        for (int i = 0; i < AnzNutzerIn; i++) {
            Username++;
            String Usern = UserListe[rand.nextInt(UserListe.length)];
                    Usern = Username + Usern;
            Username_FK[i] = Usern;
            String email = Usern + Integer.toString(rand.nextInt(100)) + mailDomain[rand.nextInt(mailDomain.length)];
            String pwd = pwdListe[rand.nextInt(pwdListe.length)];
            String reg_seit = Integer.toString(rand.nextInt(30) + 1985) + "-0" + Integer.toString(rand.nextInt(9) + 1) + "-" + Integer.toString(rand.nextInt(3)) + Integer.toString(rand.nextInt(8) + 1);
            int verifiziert = 0;
            int verfasst = 0;

            try {
                String insertSql = "INSERT INTO NutzerIn (Username, Email, Passwort, reg_seit, verifizierter_Nutzer, Anzahl_verfassater_Kommentare) VALUES ('" + Usern + "', '" + email + "', '" + pwd + "', '" + reg_seit + "', '" + verifiziert + "', '" + verfasst + "')";
                //System.out.println(insertSql);
                stmt.executeUpdate(insertSql);
            } catch (Exception e) {
                System.err.println("Fehler beim Einfuegen des Datensatzes: " + e.getMessage());
                }
        }

        try {
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM NutzerIn");
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println("Datensätze erfolgreich in \"NutzerIn\" eingefügt: " + count);
            }
            rs.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static void insertArtikel(int AnzArtikel) {

        for (int i = 0;i < AnzArtikel; i++) {
            ArtikelID_FK[i] = ++ArtikelID;
            String veroeffentlicht = Integer.toString(rand.nextInt(30) + 1985) + "-0" + Integer.toString(rand.nextInt(9) + 1) + "-" + Integer.toString(rand.nextInt(3)) + Integer.toString(rand.nextInt(8) + 1);
            String Resort = ResortListe[rand.nextInt(ResortListe.length)];
            String Titel = Resort +  ' ' + TitelListe[rand.nextInt(TitelListe.length)];

            try {
                String insertSql = "INSERT INTO Artikel (ArtikelID, veroeffentlicht, Rubrik, Titel) VALUES ('" + ArtikelID + "', '" + veroeffentlicht + "', '" + Resort + "', '" + Titel + "')";
                //System.out.println(insertSql);
                stmt.executeUpdate(insertSql);
            } catch (Exception e) {
                System.err.println("Fehler beim Einfuegen des Datensatzes: " + e.getMessage());
                }
        }

        try {
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM Artikel");
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println("Datensätze erfolgreich in \"Artikel\" eingefügt: " + count);
            }
            rs.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }




    public static void insertKommentar(int AnzKommentare) {

        for (int i = 0; i < AnzKommentare; i++) {
            KommentarId_FK[i] = ++KommentarID;
            String Zeitstempel = Integer.toString(rand.nextInt(30) + 1985) + "-0" + Integer.toString(rand.nextInt(9) + 1) + "-" + Integer.toString(rand.nextInt(3)) + Integer.toString(rand.nextInt(8) + 1);

            try {
                String insertSql = "INSERT INTO Kommentar (KommentarID, Zeitstempel) VALUES ('" + KommentarID + "', '" + Zeitstempel + "')";
                //System.out.println(insertSql);
                stmt.executeUpdate(insertSql);
            } catch (Exception e) {
                System.err.println("Fehler beim Einfuegen des Datensatzes: " + e.getMessage());
                //System.err.println("Fehler beim Einfuegen des Datensatzes: " + e.toString());
            }
        }

        try {
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM Kommentar");
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println("Datensätze erfolgreich in \"Kommentare\" eingefügt: " + count);
            }
            rs.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    public static void insertVerfasst(int AnzVerfasst) {

        for (int i = 0;i < AnzVerfasst; i++) {
            String Abk = Verfasser_FK[i];
            String veroeffentlicht = Integer.toString(rand.nextInt(30) + 1985) + "-0" + Integer.toString(rand.nextInt(9) + 1) + "-" + Integer.toString(rand.nextInt(3)) + Integer.toString(rand.nextInt(8) + 1);

            try {
                String insertSql = "INSERT INTO Verfasst (veroeffentlicht, Abkuerzung) VALUES ('" + veroeffentlicht + "', '" + Abk + "')";
                //System.out.println(insertSql);
                stmt.executeUpdate(insertSql);
            } catch (Exception e) {
                System.err.println("Fehler beim Einfuegen des Datensatzes: " + e.getMessage());
                //System.err.println("Fehler beim Einfuegen des Datensatzes: " + e.toString());
            }
        }

        try {
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM Kommentar");
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println("Datensätze erfolgreich in \"Kommentare\" eingefügt: " + count);
            }
            rs.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            }
        }

    public static void insertKommentiert(int AnzKommentiert) {

        for (int i = 0;i < AnzKommentiert; i++) {
            try {
                String insertSql = "INSERT INTO Kommentiert (Username, ArtikelID, KommentarID) VALUES ('" + Username_FK[i] + "', '" + ArtikelID_FK[i] + "', '" + KommentarId_FK[i] +"')";
                //System.out.println(insertSql);
                stmt.executeUpdate(insertSql);
            } catch (Exception e) {
                System.err.println("Fehler beim Einfuegen des Datensatzes: " + e.getMessage());
            }
        }

        try {
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM Kommentiert");
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println("Datensätze erfolgreich in \"Kommentiert\" eingefügt: " + count);
            }
            rs.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }



    public static void main(String args[]) {
        rand = new Random();

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String database = "jdbc:oracle:thin:@oracle-lab.cs.univie.ac.at:1521:lab";
            String user = "a1127050";
            String pass = "dreiti36";

            // establish connection to database
            con = DriverManager.getConnection(database, user, pass);
            stmt = con.createStatement();

            // Value für AutorIn ==Verfasst
            insertAutorIn (100);
            insertNutzerIn (100);
            insertArtikel(100);
            insertKommentar(200);
            insertVerfasst(50);                 //References to AutorIn(Abkuerzung)
            insertKommentiert(50);              //References to NuterIn (Username) && Kommentar(KommentarID)

            // check number of datasets in person table
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM AutorIn");
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println("Number of datasets: " + count);
            }

            // clean up connections
            rs.close();
            stmt.close();
            con.close();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}