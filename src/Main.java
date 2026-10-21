



import javafx.application.Application;				// Basis-Klasse,				Ausgangspunkt für JavaFX-Anwendung
import javafx.application.Platform;					// Plattform-Klasse,			steuert Lebenszyklus der Anwendung (z.B. Beenden)
import javafx.animation.KeyFrame;					// Zeit-Rahmen,					definiert einzelnen Zeitpunkt für Animation oder Timer-Tick
import javafx.animation.Timeline;					// Zeit-Leiste,					spielt eine Abfolge von KeyFrames ab (eigentlicher Timer)
import javafx.geometry.Insets;						// Innenabstand,				definiert Ränder (Padding) für Container in Pixeln
import javafx.geometry.Pos;							// Positionierung,				bestimmt die Ausrichtung von Elementen (z.B. CENTER)
import javafx.scene.Scene;							// Scene-Klasse,				für den Fensterinhalt
import javafx.scene.control.*;						// Steuerelemente,				importiert alle GUI-Komponenten -> Buttons, Labels und TextFields
import javafx.scene.layout.VBox;					// Layout-Klasse,				ordnet GUI-Elemente automatisch vertikal (untereinander)
import javafx.stage.Stage;							// Bühne,						das eigentliche Windows-Fenster
import javafx.util.Duration;						// Zeitdauer,					gibt Zeitabstände an (z.B. Sekunden oder Millisekunden)
import java.io.IOException;							// IO-Ausnahme,					fängt Fehler ab, falls Kommunikation mit Windows (CMD) fehlschlägt
import java.time.LocalTime;							// Lokale Uhrzeit,				fragt nach der aktuellen SystemUhrzeit
import java.time.temporal.ChronoUnit;				// Datum/Zeitberechnung			erlaubt es Zeit und Datum zu berechnen

//=======================================================================================================================

// Main erbt von Application -> um JavaFX zu nutzen:
public class Main extends Application {
	
	// Variablen deklarieren:
    int restZeitSekunden = 0;
    Timeline zeitLeiste;
    
    // Info an Compiler -> start-Methode von Application überschreiben:
    @Override
    // Haupteinstiegspunkt für die GUI:
	public void start(Stage primaryStage) {

//=======================================================================================================================
    	
//    	    _____ _    _ _____                 _       _ _              
//    	   / ____| |  | |_   _|               | |     | | |           _ 
//    	  | |  __| |  | | | |     ___ _ __ ___| |_ ___| | | ___ _ __ (_)
//    	  | | |_ | |  | | | |    / _ \ '__/ __| __/ _ \ | |/ _ \ '_ \   
//    	  | |__| | |__| |_| |_  |  __/ |  \__ \ ||  __/ | |  __/ | | |_ 
//    	   \_____|\____/|_____|  \___|_|  |___/\__\___|_|_|\___|_| |_(_)  
    	
    	// Hinweistext:
        Label titleLabel = new Label("bis PC sich herunter fährt:");
        // Style ändern:	 - Schriftgröße; 	  - Fett gedruckt		 - SchriftFarbe
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #E6EAF2;");
        
// -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -
        
        // Label für Text neben dem Textfeld erstellen:
        Label minutenLabel = new Label ("Minuten");
        minutenLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #E6EAF2");
        
   
        // Textfeld für MinutenEingabe:
        TextField minutenEingabe = new TextField();    
        // Breite des Eingabefeldes:
        minutenEingabe.setMaxWidth(100);
        // Style ändern:		 - Schriftgröße		  - Hintergrundfarbe			 - TextFarbe 
        minutenEingabe.setStyle("-fx-font-size: 12px; -fx-background-color: #3A4459; -fx-text-fill: #E6EAF2;");
        // Schrift mittig auslegen:
        minutenEingabe.setAlignment(Pos.CENTER);
        
        
        // Label und Textfeld in HBox packen:
        VBox minutenVbox = new VBox(2);
        minutenVbox.setAlignment(Pos.CENTER);
        minutenVbox.setStyle("-fx-background-color: #2B3345;");
        minutenVbox.setMaxWidth(100);
        
        
        // Elemente der Hbox hinzufügen:
        minutenVbox.getChildren().addAll(minutenEingabe, minutenLabel); 
        
// -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -
        
        // Label für Text neben dem Textfeld erstellen:
        Label uhrzeitLabel = new Label ("Uhr");
        uhrzeitLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #E6EAF2");
        
        
        // Textfeld für Uhrzeit:
        TextField uhrzeitEingabe = new TextField();
        // Breite des Eingabefeldes:
        uhrzeitEingabe.setMaxWidth(100);
        // Schriftgröße + Schriftfarbe für Zeiteingabe:
        uhrzeitEingabe.setStyle("-fx-font-size: 12px; -fx-background-color: #3A4459; -fx-text-fill: #E6EAF2;");
        // Schrift mittig auslegen:
        uhrzeitEingabe.setAlignment(Pos.CENTER);
        
        
        // Label und Textfeld in HBox packen:
        VBox uhrzeitVbox = new VBox(2);
        uhrzeitVbox.setAlignment(Pos.CENTER);
        uhrzeitVbox.setStyle("-fx-background-color: #2B3345;");
        uhrzeitVbox.setMaxWidth(100);
        
        
        // Elemente der Hbox hinzufügen:
        uhrzeitVbox.getChildren().addAll(uhrzeitEingabe, uhrzeitLabel); 
        
// -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -
        
        // leeres Label -> zeigt dann den LiveCountdown an:
        Label countdownAnzeigen = new Label("");
        // Style ändern:	 		- Schriftgröße; 	 - Fett gedruckt;
        countdownAnzeigen.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #4FC3F7;");
        
        
        // nicht Anzeigen:
        countdownAnzeigen.setVisible(false);
        countdownAnzeigen.setManaged(false);

// -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   
        
        // Button erstellen:
        Button startBtn = new Button("Start");
        // Größe in Pixel:	 <->  ↕
        startBtn.setPrefSize(100, 20); 
        
        Button pauseBtn = new Button("Pause");
        pauseBtn.setPrefSize(100, 20);
        
        Button abbruchBtn = new Button("Abbrechen");
        abbruchBtn.setPrefSize(100, 20);
        
        
        // vorerst nicht benötigte Buttons ausblenden:
        pauseBtn.setVisible(false);
        pauseBtn.setManaged(false);
        
        abbruchBtn.setVisible(false);
        abbruchBtn.setManaged(false);
        
//=======================================================================================================================

//         _______ _                _      _              
//        |__   __(_)              | |    (_)           _ 
//           | |   _ _ __ ___   ___| |     _ _ __   ___(_)
//           | |  | | '_ ` _ \ / _ \ |    | | '_ \ / _ \  
//           | |  | | | | | | |  __/ |____| | | | |  __/_ 
//           |_|  |_|_| |_| |_|\___|______|_|_| |_|\___(_)

        
        // Zeitleiste: Rechnet eingegebene Zeit herunter -> jede Sekunde - eine Sekunde:
        // Keyframe feuert jede Sekunde - 1 Sekunde:
        zeitLeiste = new Timeline(new KeyFrame(Duration.seconds(1), aktion -> {

        	// noch Sekunden übrig:
            if (restZeitSekunden > 0) {
            	// Sekunde - 1
                restZeitSekunden--;
                
                // volle Stunden berechnen (1 Stunde = 3600 Sekunden):
                int hours = restZeitSekunden / 3600;
                // Minuten berechnen -> erst die vollen Stunden abziehen (% 3600), dann durch 60:
                int minutes = (restZeitSekunden % 3600) / 60;
                // verbleibende RestSekunden:
                int seconds = restZeitSekunden % 60;
                
                // Formatiert Text im Label neu -> %02d erzwingt eine zweistellige Anzeige mit führender Null:
                // (Pos.CENTER)r ichtet den Text im Label mittig aus:
                countdownAnzeigen.setAlignment(Pos.CENTER);
                // Zentriert mehrzeiligen Text mittig:
                countdownAnzeigen.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
                countdownAnzeigen.setText(String.format("Computer fährt herunter in: \n%02d:%02d:%02d",hours, minutes, seconds));
                countdownAnzeigen.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #4FC3F7;");
              
                
            // Wenn Timeline bei 0 angekommen:
            } else {
            	// TimeLine stoppen -> verhindert weiteres herunter zählen:
                zeitLeiste.stop();
                countdownAnzeigen.setText("Fahre herunter...");
                // Funktion aufrufen:
                sendeShutDown();
            }
	            

        }));
        // Timeline unendlich laufen lassen -> bis gestoppt wird:
        zeitLeiste.setCycleCount(Timeline.INDEFINITE);

//=======================================================================================================================
        
//         ____        _   _                _                 _ _      
//        |  _ \      | | | |              | |               (_) |   _ 
//        | |_) |_   _| |_| |_ ___  _ __   | |     ___   __ _ _| | _(_)
//        |  _ <| | | | __| __/ _ \| '_ \  | |    / _ \ / _` | | |/ /  
//        | |_) | |_| | |_| || (_) | | | | | |___| (_) | (_| | |   < _ 
//        |____/ \__,_|\__|\__\___/|_| |_| |______\___/ \__, |_|_|\_(_)
//                                                       __/ |         
//                                                      |___/                   
        
//         ______           __      ___       __  __            _ 
//        / __/ /____ _____/ /_____/ _ )__ __/ /_/ /____  ___  (_)
//       _\ \/ __/ _ `/ __/ __/___/ _  / // / __/ __/ _ \/ _ \_   
//      /___/\__/\_,_/_/  \__/   /____/\_,_/\__/\__/\___/_//_(_)  

//      __  __ _          _            _ 
//     |  \/  (_)_ _ _  _| |_ ___ _ _ (_)
//     | |\/| | | ' \ || |  _/ -_) ' \ _ 
//     |_|  |_|_|_||_\_,_|\__\___|_||_(_)
        
        	startBtn.setOnAction(aktion -> {	                                    
        	// .trim()	-> entfernt leerzeichen am Anfang des Textes
        	if (!minutenEingabe.getText().trim().isEmpty() && uhrzeitEingabe.getText().trim().isEmpty()) {
        		
	        	// versuche potentiell gefährlichen Code auszuführen:
	            try {
	            	// Liest den Text aus Feld und konvertiert in 'int'
	                int minutes = Integer.parseInt(minutenEingabe.getText().trim());
	                
	                
	                // Zahl muss zwischen 1 und 1440 liegen (24 Stunden):
	                if (minutes <= 0 || minutes > 1440 ) {
	                	throw new NumberFormatException();
	                }
	
		                restZeitSekunden = minutes * 60;
		                
		                // Anzeige aus restZeitSekunden berechnen (minutes ist hier die Eingabe):
		                int hours = restZeitSekunden / 3600;
		                int anzeigeMinuten = (restZeitSekunden % 3600) / 60;
		                int seconds = restZeitSekunden % 60;
		                
		                // Zentriert mehrzeiligen Text mittig:
		                countdownAnzeigen.setAlignment(Pos.CENTER);
		                countdownAnzeigen.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
		                countdownAnzeigen.setText(String.format("Computer fährt herunter in: \n%02d:%02d:%02d",hours, anzeigeMinuten, seconds));
		                countdownAnzeigen.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #4FC3F7;");
		                
		                // VBox/Textfeld sperren/freigeben:
		                minutenVbox.setVisible(false);
		                minutenVbox.setManaged(false);
		                
		                uhrzeitVbox.setVisible(false);
		                uhrzeitVbox.setManaged(false);
		                
		                // TextLabel sperren/freigeben:
		                titleLabel.setVisible(false);
		                titleLabel.setManaged(false);
		                
		                // Buttons sperren/freigeben:
		                startBtn.setVisible(false);
		                startBtn.setManaged(false);
		                
		                abbruchBtn.setVisible(true);
		                abbruchBtn.setManaged(true);
		                
		                pauseBtn.setVisible(true);
		                pauseBtn.setManaged(true);
		                
		                // Countdown anzeigen:
		                countdownAnzeigen.setVisible(true);
		                countdownAnzeigen.setManaged(true);
		                zeitLeiste.play();
	                
	            // Fehlermeldung bei falscher Eingabe:
	            } catch (NumberFormatException ex) {
	                Alert alert = new Alert(Alert.AlertType.ERROR, "Bitte eine Zahl zwischen 1 und 1440 eingeben!");
	                alert.setHeaderText(null);
	                alert.showAndWait();
	                return;
	            }
	            
// -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -
	            
//         _   _ _               _ _   _ 
//        | | | | |_  _ _ ______(_) |_(_)
//        | |_| | ' \| '_|_ / -_) |  _|_ 
//         \___/|_||_|_| /__\___|_|\__(_)
	            
        	} else if (!uhrzeitEingabe.getText().trim().isEmpty() && minutenEingabe.getText().trim().isEmpty()) {      
        		
                try {
            		// Aktuelle Lokale Uhrzeit holen:
            		LocalTime aktuelleUhrzeit = LocalTime.now();
            		
    	            // Liest den Text aus TextFeld:
                    String uhrzeitText = uhrzeitEingabe.getText().trim();
                	// .split()	-> TextfeldEingabe wird am ":" geteilt:
                	// wird immer in einem Array gespeichert.
                	String[] aufteilen = uhrzeitText.split(":");
                	
                	// String in int parsen:
                	int stunden = Integer.parseInt(aufteilen[0]);
                	int minuten = Integer.parseInt(aufteilen[1]);
                	// int Werte jetzt als LocalTome speichern:
                	LocalTime shutdownUhrzeit = LocalTime.of(stunden,minuten);
                	
                	// ChronoUnit berechnet die differenz zwischen der aktuellen SystemZeit und der gewünschten Shutdown Uhrzeit:
                	// between() gibt immer ein Long zurück
                	long sekundenBisShutdown = ChronoUnit.SECONDS.between(aktuelleUhrzeit, shutdownUhrzeit);
                	
                	
                	// Wenn Sekunden <= 0 -> neuen Tag dazu rechnen -> Shutdwon nach 00:00 Uhr:
                  	if (sekundenBisShutdown <=0) {
                		sekundenBisShutdown += 86400;
                  	}
                		
                	restZeitSekunden = (int)sekundenBisShutdown;
                	
                	// Anzeige aus restZeitSekunden berechnen:
                	int hours = restZeitSekunden / 3600;
                	int minutes = (restZeitSekunden % 3600) / 60;
	                int seconds = restZeitSekunden % 60;
	                
	                // Zentriert mehrzeiligen Text mittig:
	                countdownAnzeigen.setAlignment(Pos.CENTER);
	                countdownAnzeigen.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
	                countdownAnzeigen.setText(String.format("Computer fährt herunter in: \n%02d:%02d:%02d",hours, minutes, seconds));
	                countdownAnzeigen.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #4FC3F7;");
	                
	                // VBox/Textfeld sperren/freigeben:
	                minutenVbox.setVisible(false);
	                minutenVbox.setManaged(false);
	                
	                uhrzeitVbox.setVisible(false);
	                uhrzeitVbox.setManaged(false);
	                
	                // TextLabel sperren/freigeben:
	                titleLabel.setVisible(false);
	                titleLabel.setManaged(false);
	                
	                // Buttons sperren/freigeben:
	                startBtn.setVisible(false);
	                startBtn.setManaged(false);
	                
	                abbruchBtn.setVisible(true);
	                abbruchBtn.setManaged(true);
	                	                
	                // Countdown anzeigen:
	                countdownAnzeigen.setVisible(true);
	                countdownAnzeigen.setManaged(true);
	                
	                zeitLeiste.play();

                } catch (Exception f) {
                    // Fängt Formatfehler (kein ":") und Textfehler:
	                Alert alert = new Alert(Alert.AlertType.ERROR, "Bitte gültiges Uhrzeit Format eingeben! hh:mm");
	                alert.setHeaderText(null);
	                alert.showAndWait();
                    return;
                }
                
// -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -

//         _              __        _ _ _ 
//        | |___ ___ _ _ / /_ _____| | (_)
//        | / -_) -_) '_/ /\ V / _ \ | |_ 
//        |_\___\___|_|/_/  \_/\___/_|_(_)                                              

            // wenn beide oder kein Feld ausgefüllt:
        	} else {
		        Alert alert = new Alert(Alert.AlertType.ERROR, "Bitte nur ein Feld ausfüllen!");
		        alert.setHeaderText(null);
		        alert.showAndWait();	        	
        	}		
        });

// -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -
        
//         ___   __   __                __       ___       __  __            _ 
//        / _ | / /  / /  ______ ______/ /  ____/ _ )__ __/ /_/ /____  ___  (_)
//       / __ |/ _ \/ _ \/ __/ // / __/ _ \/___/ _  / // / __/ __/ _ \/ _ \_   
//      /_/ |_/_.__/_.__/_/  \_,_/\__/_//_/   /____/\_,_/\__/\__/\___/_//_(_)  
                                                                             
        abbruchBtn.setOnAction(aktion -> {
            zeitLeiste.stop();
            restZeitSekunden = 0;
            
            // Textfelder sperren/freigeben:
            minutenVbox.setVisible(true);
            minutenVbox.setManaged(true);
            
            uhrzeitVbox.setVisible(true);
            uhrzeitVbox.setManaged(true);
                        
            // Buttons sperren/freigeben:
            startBtn.setVisible(true);
            startBtn.setManaged(true);
            
            abbruchBtn.setVisible(false);
            abbruchBtn.setManaged(false);
            
            pauseBtn.setVisible(false);
            pauseBtn.setManaged(false);
        
            titleLabel.setVisible(true);
            titleLabel.setManaged(true);
            
            // Countdown verbergen:
            countdownAnzeigen.setVisible(false);
            countdownAnzeigen.setManaged(false);
               
            // wenn bei Abbruch PauseBtn noch "Fortsetzen" -> ändern zu "Pause":
            if (pauseBtn.getText().equals("Fortsetzen")) {
            	pauseBtn.setText("Pause");
            }
            
        });
                
// -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -
        
//         ___                        ___       __  __            _ 
//        / _ \___ ___ _____ ___ ____/ _ )__ __/ /_/ /____  ___  (_)
//       / ___/ _ `/ // (_-</ -_)___/ _  / // / __/ __/ _ \/ _ \_   
//      /_/   \_,_/\_,_/___/\__/   /____/\_,_/\__/\__/\___/_//_(_)  

        pauseBtn.setOnAction(aktion -> {        	
        	// Buttons sperren/freigeben:
        	if (pauseBtn.getText().equals("Pause")) {
            	zeitLeiste.pause();
        		pauseBtn.setText("Fortsetzen");
        	}else {
        		pauseBtn.setText("Pause");
        		zeitLeiste.play();
        	}
        });
        
//=======================================================================================================================

//         ______             _                                                                 _                                
//        |  ____|           | |                                                               | |                           _ _ 
//        | |__ ___ _ __  ___| |_ ___ _ __   _____   _ ___  __ _ _ __ ___  _ __ ___   ___ _ __ | |__   __ _ _   _  ___ _ __ (_|_)
//        |  __/ _ \ '_ \/ __| __/ _ \ '__| |_  / | | / __|/ _` | '_ ` _ \| '_ ` _ \ / _ \ '_ \| '_ \ / _` | | | |/ _ \ '_ \     
//        | | |  __/ | | \__ \ ||  __/ |     / /| |_| \__ \ (_| | | | | | | | | | | |  __/ | | | |_) | (_| | |_| |  __/ | | |_ _ 
//        |_|  \___|_| |_|___/\__\___|_|    /___|\__,_|___/\__,_|_| |_| |_|_| |_| |_|\___|_| |_|_.__/ \__,_|\__,_|\___|_| |_(_|_)
                                                                                                                               
        // root stapelt mit vbox alle Elemenete untereinander an:
        // Abstand zwischen Vertikalen Elementen:
        VBox root = new VBox(15);
        // Hintergrundfarbe der VBox:
        root.setStyle("-fx-background-color: #1E2430;");
        
        // Abstand vom Rand:
        root.setPadding(new Insets(20));
        
        // Inhalt mittig ausrichten:
        root.setAlignment(Pos.CENTER);
        
        // Elemente dem root hinzufügen:
        root.getChildren().addAll(minutenVbox, uhrzeitVbox, titleLabel, countdownAnzeigen, startBtn, pauseBtn, abbruchBtn);

        // Größe des Fensters:
        Scene scene = new Scene(root, 400, 300);
        
// -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -
        
        // Fenster final vorbereiten:
        primaryStage.setTitle("ShutDown-Timer");
        primaryStage.setScene(scene);
//        primaryStage.setAlwaysOnTop(true);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
//=======================================================================================================================

//     _____ _           _   _____                        __  __      _   _               _        
//    / ____| |         | | |  __ \                      |  \/  |    | | | |             | |     _ 
//   | (___ | |__  _   _| |_| |  | | _____      ___ __   | \  / | ___| |_| |__   ___   __| | ___(_)
//    \___ \| '_ \| | | | __| |  | |/ _ \ \ /\ / / '_ \  | |\/| |/ _ \ __| '_ \ / _ \ / _` |/ _ \  
//    ____) | | | | |_| | |_| |__| | (_) \ V  V /| | | | | |  | |  __/ |_| | | | (_) | (_| |  __/_ 
//   |_____/|_| |_|\__,_|\__|_____/ \___/ \_/\_/ |_| |_| |_|  |_|\___|\__|_| |_|\___/ \__,_|\___(_)
                                                                                                 
    
    // Methode wenn Countdown 0 erreicht:
    // Java schickt Befehl direkt an Eingabeaufforderung/Console/Terminal:
    private void sendeShutDown() {

        // getProperty(); -> Name des Betriebssystems abrufen:
        String betriebsSystem = System.getProperty("os.name").toLowerCase();

        // Befehl als Array: jedes Element ist ein Teil des Befehls:
        // Array wird erst innerhalb der Verzweigung mit Werten gefüllt:
        // null = "noch kein Befehl gewählt"
        String[] befehl = null;

        // Windows:
        if (betriebsSystem.contains("win")) {
            befehl = new String[]{"shutdown", "-s", "-f", "-t", "0"};

        // Mac:
        } else if (betriebsSystem.contains("mac")) {
            befehl = new String[]{"osascript", "-e", "tell app \"System Events\" to shut down"};

        // Linux / Unix:
        } else if (betriebsSystem.contains("nix") || betriebsSystem.contains("nux") || betriebsSystem.contains("aix")) {
            befehl = new String[]{"systemctl", "poweroff"};
        }

        try {
            // nur ausführen wenn Betriebssystem erkannt wurde:
            if (befehl != null) {
                Runtime.getRuntime().exec(befehl);
                // schließt alle JavaFX-Prozesse:
                Platform.exit();
                // beendet das Java-Programm:
                System.exit(0);
            } else {
            	Alert alert = new Alert(Alert.AlertType.ERROR, "unbekanntes Betriebssystem!\nShutdown leider nicht möglich!");
                alert.setHeaderText(null);
                alert.show();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//=======================================================================================================================
    
    public static void main(String[] args) {
        launch(args);

	}
}

