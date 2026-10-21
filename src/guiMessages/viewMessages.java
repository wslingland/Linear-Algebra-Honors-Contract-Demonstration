package guiMessages;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class viewMessages {

	// These are the application values required by the user interface

	private static double width = applicationMain.Main.WINDOW_WIDTH;
	private static double height = applicationMain.Main.WINDOW_HEIGHT;

	private static Label label_ApplicationTitle = new Label("Secret Message Encryption");

	
	private static Label label_EnterMessageInstructions = new Label("Encrypt a message");
	private static Label label_decryptInstructions = new Label("Decrypt a message");
	private static Separator separator = new Separator();
	protected static Alert alertMessageError = new Alert(AlertType.INFORMATION);
	protected static Alert alertDecryptError = new Alert(AlertType.INFORMATION);
	
	protected static Alert Message = new Alert(AlertType.INFORMATION);
	
	public static TextField text_message = new TextField();
	public static TextField encrypt = new TextField();
	
	private static Button button_Enter = new Button("Encrypt");
	
	public static TextField encrypted_message = new TextField();
	public static TextField decrypt = new TextField();
	private static Button button_decrypt = new Button("Decrypt");
	
	private static Button generate_Matrix = new Button("Generate Key");
	
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
		theStage.setTitle("Secret Message Encryption");
		theStage.setScene(theEnterMessageScene);
		theStage.show();
	}

	/**********
	 * <p> Method: viewMessages() </p>
	 * 
	 * <p> Description: This method is called when the application first starts. </p>
	 */
	private viewMessages() {

		// Create the Pane for the list of widgets and the Scene for the window
		theRootPane = new Pane();
		theRootPane.setStyle("-fx-background-color: white;");
		theEnterMessageScene = new Scene(theRootPane, width, height);

		// Populate the window with the title and other common widgets and set their static state
		setupLabelUI(label_ApplicationTitle, "Segoe UI", 30, width, Pos.CENTER, 0, 20);
		label_ApplicationTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 30));

		// Encrypt section: message, key (with a generate button beside it), and the encrypt button
		setupLabelUI(label_EnterMessageInstructions, "Segoe UI", 18, 300, Pos.BASELINE_LEFT, 50, 90);
		label_EnterMessageInstructions.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));

		setupTextUI(text_message, "Segoe UI", 16, 450, Pos.BASELINE_LEFT, 50, 125, true);
		text_message.setPromptText("Message to encrypt");
		setupTextUI(encrypt, "Segoe UI", 16, 450, Pos.BASELINE_LEFT, 50, 170, true);
		encrypt.setPromptText("Encryption key (9 numbers separated by spaces)");

		setupButtonUI(generate_Matrix, "Segoe UI", 16, 200, Pos.CENTER, 520, 170);
		generate_Matrix.setOnAction((event) -> {
			int[][] matrix = controllerMessage.generateMatrix(3);
			encrypt.setText(controllerMessage.convertFromMatrixToStringofNumbers1(matrix).trim());
		});

		setupButtonUI(button_Enter, "Segoe UI", 16, 200, Pos.CENTER, 50, 215);
		button_Enter.setDefaultButton(true);
		button_Enter.setOnAction((event) -> {controllerMessage.encryptMessage(theStage); });

		// Divider between the two sections
		separator.setPrefWidth(width - 100);
		separator.setLayoutX(50);
		separator.setLayoutY(280);

		// Decrypt section: encrypted numbers, key, and the decrypt button
		setupLabelUI(label_decryptInstructions, "Segoe UI", 18, 300, Pos.BASELINE_LEFT, 50, 300);
		label_decryptInstructions.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));

		setupTextUI(encrypted_message, "Segoe UI", 16, 450, Pos.BASELINE_LEFT, 50, 335, true);
		encrypted_message.setPromptText("Encrypted message (numbers)");
		setupTextUI(decrypt, "Segoe UI", 16, 450, Pos.BASELINE_LEFT, 50, 380, true);
		decrypt.setPromptText("Decryption key (the 9 numbers used to encrypt)");

		setupButtonUI(button_decrypt, "Segoe UI", 16, 200, Pos.CENTER, 50, 425);
		button_decrypt.setOnAction((event) -> {controllerMessage.decryptMessage(theStage); });

		alertMessageError.setTitle("Missing input");
		alertMessageError.setHeaderText(null);
		alertMessageError.setContentText("Please enter a message and an encryption key.");

		alertDecryptError.setTitle("Missing input");
		alertDecryptError.setHeaderText(null);
		alertDecryptError.setContentText("Please enter the encrypted message and the decryption key.");

		// Set up the Quit button in the bottom right corner
		setupButtonUI(button_Quit, "Segoe UI", 16, 120, Pos.CENTER, width - 170, height - 70);
		button_Quit.setOnAction((event) -> {controllerMessage.performQuit(); });


		theRootPane.getChildren().addAll(
				label_ApplicationTitle,
				label_EnterMessageInstructions, text_message, encrypt, generate_Matrix, button_Enter,
				separator,
				label_decryptInstructions, encrypted_message, decrypt, button_decrypt,
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