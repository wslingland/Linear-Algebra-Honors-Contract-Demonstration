package applicationMain;


import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	
	public final static double WINDOW_WIDTH = 800;
	public final static double WINDOW_HEIGHT = 600;

	public static int activeHomePage = 0;		
	
	@Override
	public void start(Stage theStage) {
		
		// display the gui
		guiMessages.viewMessages.displayMessages(theStage);	
		
	}

public static void main(String[] args) {
	launch(args);	// The launch method loads JavaFX and invokes its initialization.  When it
						// is done, it calls the start method shown above.
}
}

// Test reverting commits