package guiMessages;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class viewMessages {

	// These are the application values required by the user interface

	private static double width = applicationMain.Main.WINDOW_WIDTH;
	private static double height = applicationMain.Main.WINDOW_HEIGHT;

	private static Label label_ApplicationTitle = new Label("Secret Message Encryption");

	
	private static Label label_EnterMessageInstructions = new Label("Enter your message and encryption");
	private static Label label_decryptInstructions = new Label("Enter the encrypted message and key");
	protected static Alert alertMessageError = new Alert(AlertType.INFORMATION);
	protected static Alert alertDecryptError = new Alert(AlertType.INFORMATION);
	
	protected static Alert Message = new Alert(AlertType.INFORMATION);
	
	public static TextField text_message = new TextField();
	public static TextField encrypt = new TextField();
	
	private static Button button_Enter = new Button("Encrypt");
	
	public static TextField encrypted_message = new TextField();
	public static TextField decrypt = new TextField();
	private static Button button_decrypt = new Button("Decrypt");
	
	private static Button generate_Matrix = new Button("Generate An Encryption Matrix");
	
	private static Button button_Quit = new Button("Quit");

	protected static Stage theStage;	
	private static Pane theRootPane;
	public static Scene theEnterMessageScene = null;	


	private static viewMessages theView = null;	

	public static void displayMessages(Stage ps) {
		
		// Establish the references to the GUI.
		theStage = ps;
		
		// If not yet established, populate the static aspects of the GUI
		if (theView == null) theView = new viewMessages();
		
		// Populate the dynamic aspects of the GUI 
		text_message.setText("");		// Reset the message from the last use

		// Set the title for the window, display the page, and wait for the user to do something
		theStage.setTitle("Enter Message page");		
		theStage.setScene(theEnterMessageScene);
		theStage.show();
	}

	/**********
	 * <p> Method: viewMessages() </p>
	 * 
	 * <p> Description: This method is called when the application first starts. </p>
	 * 
	 * @param ps specifies the JavaFX Stage to be used for this GUI and it's methods
	 * 
	 * @param theRoot specifies the JavaFX Pane to be used for this GUI and it's methods
	 * 
	 * @param db specifies the Database to be used by this GUI and it's methods
	 * 
	 */
	private viewMessages() {

		// Create the Pane for the list of widgets and the Scene for the window
		theRootPane = new Pane();
		theEnterMessageScene = new Scene(theRootPane, width, height);
		
		// Populate the window with the title and other common widgets and set their static state
		setupLabelUI(label_ApplicationTitle, "Arial", 32, width, Pos.CENTER, 0, 10);


		// enter message

		setupLabelUI(label_EnterMessageInstructions, "Arial", 18, width, Pos.BASELINE_LEFT, 20, 190);
		setupLabelUI(label_decryptInstructions, "Arial", 18, width, Pos.BASELINE_LEFT, 20, 330);

		// Establish the text input operand field for the message
		setupTextUI(text_message, "Arial", 18, 300, Pos.BASELINE_LEFT, 50, 220, true);
		text_message.setPromptText("Enter message");
		setupTextUI(encrypt, "Arial", 18, 300, Pos.BASELINE_LEFT, 50, 260, true);
		encrypt.setPromptText("Enter Encryption");
		
		setupTextUI(encrypted_message, "Arial", 18, 300, Pos.BASELINE_LEFT, 50, 360, true);
		encrypted_message.setPromptText("Enter encrypted message");
		
		setupTextUI(decrypt, "Arial", 18, 300, Pos.BASELINE_LEFT, 50, 400, true);
		decrypt.setPromptText("Enter decryption key");
		
		// Set up the enter button
		setupButtonUI(button_Enter, "Dialog", 18, 200, Pos.CENTER, 475, 220);
		button_Enter.setOnAction((event) -> {controllerMessage.encryptMessage(theStage); });

		setupButtonUI(button_decrypt, "Dialog", 18, 200, Pos.CENTER, 475, 360);
		button_decrypt.setOnAction((event) -> {controllerMessage.decryptMessage(theStage); });
		
		setupButtonUI(generate_Matrix, "Dialog", 18, 200, Pos.CENTER, 475, 90);
		generate_Matrix.setOnAction((event) -> {controllerMessage.generateMatrix(3); });
		
		alertMessageError.setTitle("Empty Message, please type a message");
		alertMessageError.setHeaderText(null);
		
		alertDecryptError.setTitle("Empty, please type a message");
		alertDecryptError.setHeaderText(null);
		
		
		// Set up the Quit button  
		setupButtonUI(button_Quit, "Dialog", 18, 250, Pos.CENTER, 300, 520);
		button_Quit.setOnAction((event) -> {controllerMessage.performQuit(); });


		theRootPane.getChildren().addAll(
				label_ApplicationTitle, 
				label_EnterMessageInstructions, text_message, encrypted_message, decrypt, button_decrypt,
				button_Enter, generate_Matrix, encrypt, label_decryptInstructions,
				button_Quit);
	}

	/**********
	 * Private local method to initialize the standard fields for a label
	 */

	private void setupLabelUI(Label l, String ff, double f, double w, Pos p, double x, double y){
		l.setFont(Font.font(ff, f));
		l.setMinWidth(w);
		l.setAlignment(p);
		l.setLayoutX(x);
		l.setLayoutY(y);		
	}


	/**********
	 * Private local method to initialize the standard fields for a button
	 * 
	 * @param b		The Button object to be initialized
	 * @param ff	The font to be used
	 * @param f		The size of the font to be used
	 * @param w		The width of the Button
	 * @param p		The alignment (e.g. left, centered, or right)
	 * @param x		The location from the left edge (x axis)
	 * @param y		The location from the top (y axis)
	 */
	private void setupButtonUI(Button b, String ff, double f, double w, Pos p, double x, double y){
		b.setFont(Font.font(ff, f));
		b.setMinWidth(w);
		b.setAlignment(p);
		b.setLayoutX(x);
		b.setLayoutY(y);		
	}

	/**********
	 * Private local method to initialize the standard fields for a text field
	 */
	private void setupTextUI(TextField t, String ff, double f, double w, Pos p, double x, double y, boolean e){
		t.setFont(Font.font(ff, f));
		t.setMinWidth(w);
		t.setMaxWidth(w);
		t.setAlignment(p);
		t.setLayoutX(x);
		t.setLayoutY(y);		
		t.setEditable(e);
	}		
}