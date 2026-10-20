package guiMessages;


import java.util.Random;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class controllerMessage {
	
public controllerMessage() {
	
}
protected static Alert Message = new Alert(AlertType.INFORMATION);
protected static Alert Message_Numbers = new Alert(AlertType.INFORMATION);
protected static Alert EncryptionMatrix = new Alert(AlertType.INFORMATION);

public static String message;
public static double [][] messageMatrix;
public static double [][] enmessageMatrix;
public static int[][] invertableMatrix;
public static double[][] inverseMatrix;
public static double [][] demessageMatrix;
public static String returned;
public static String encryptedMessage;
public static String messageToDecrypt;
public static double [][] messageMatrixToDecrypt;
public static String key;
public static String key1;
public static int [][] keyMatrix;

public static void encryptMessage(Stage ts) {

message = viewMessages.text_message.getText();
if (message == "") {
viewMessages.alertMessageError.showAndWait();
}

else {
//convert to a matrix
messageMatrix = convertToMatrix(message);
key1 = viewMessages.encrypt.getText();
if (key1 == "") {
viewMessages.alertMessageError.showAndWait();
}

else {
invertableMatrix = convertToMatrixfromStringofNumbers1(key1);    

// call encryptMessage to encrypt the message using a invertable matrix
enmessageMatrix = encryptMessage(messageMatrix, invertableMatrix);

System.out.print("Encrypted Message: ");
encryptedMessage = convertFromMatrixToStringofNumbers(enmessageMatrix);
Message_Numbers.setTitle("The Encrypted Message reads:");
Message_Numbers.setHeaderText(encryptedMessage);
Message_Numbers.showAndWait();

System.out.print("Key: ");
key1 = convertFromMatrixToStringofNumbers1(invertableMatrix);
}
}
}

public static void decryptMessage(Stage ts) {

messageToDecrypt = viewMessages.encrypted_message.getText();
if (messageToDecrypt == "") {
viewMessages.alertDecryptError.showAndWait();
}

else {
messageMatrixToDecrypt = convertToMatrixfromStringofNumbers(messageToDecrypt);

key = viewMessages.decrypt.getText();
if (key == "") {
viewMessages.alertDecryptError.showAndWait();
}

else {
keyMatrix = convertToMatrixfromStringofNumbers1(key);
inverseMatrix = generateInverse(keyMatrix);
// call decryptMessage to decrypt the message using the inverse matrix
demessageMatrix = decryptMessage(messageMatrixToDecrypt, inverseMatrix);

// convert the decrypted matrix back to a string
returned = convertFromMatrixToString(demessageMatrix);
Message.setTitle("The secret Message reads:");
Message.setHeaderText(returned);
Message.showAndWait();
}
}
}

public static double[][] convertToMatrixfromStringofNumbers(String encryptedMessage){

while (encryptedMessage.length()%3 != 0) {
encryptedMessage += " ";
}


// convert to an array first 
String[] tokens = encryptedMessage.trim().split("\\s+");
int[] Message = new int[tokens.length];	

for(int i=0; i<tokens.length; i++) {
Message[i] = Integer.parseInt(tokens[i]);
}

//convert the array to a matrix
int n = 3; //number of columns
int m = (int) Math.ceil((tokens.length)/n); // number of rows
double [][] messageMatrix = new double[m][n];
int MatrixSize = m*n;
for (int i = 0; i<MatrixSize; i++) {
int row = i/n;
int col = i%n;
messageMatrix[row][col] = Message[i];
}

return messageMatrix;

}

public static int[][] convertToMatrixfromStringofNumbers1(String key){

while (key.length()%3 != 0) {
key += " ";
}
	
// convert to an array first 
String[] tokens = key.trim().split("\\s+");
int[] Message = new int[tokens.length];	

for(int i=0; i<tokens.length; i++) {
Message[i] = Integer.parseInt(tokens[i]);
}

//convert the array to a matrix
int n = 3; //number of columns
int m = (int) Math.ceil((tokens.length)/n); // number of rows
int [][] keyMatrix = new int[m][n];
int MatrixSize = m*n;
for (int i = 0; i<MatrixSize; i++) {
int row = i/n;
int col = i%n;
keyMatrix[row][col] = Message[i];
}
return keyMatrix;
}

public static double[][] convertToMatrix(String Message) {

while (Message.length()%3 != 0) {
	Message += " ";
}
// convert to an array first 
double[] asciiOfMessage = new double[Message.length()];

for(int i=0; i<Message.length(); i++) {
char asciiValue = Message.charAt(i);
asciiOfMessage[i] = (int) asciiValue;
}

//convert the array to a matrix
int n = 3; //number of columns
int m = (int) Math.ceil((asciiOfMessage.length)/n); // number of rows
double [][] messageMatrix = new double[m][n];
int MatrixSize = m*n;
for (int i = 0; i<MatrixSize; i++) {
int row = i/n;
int col = i%n;
messageMatrix[row][col] = asciiOfMessage[i];
}


for (int i = 0; i<m; i++) {
	for (int j = 0; j<n; j++) {
		System.out.print(messageMatrix[i][j] + " ");
	}
	System.out.println();
}

return messageMatrix;

}

// creates an invertable matrix by creating two nxn matrics, x (lower triangular) and y (upper triangular), and then multiplying them
public static int[][] generateMatrix(int n) {
	
	Random num = new Random();
	int[][] x = new int[n][n];
	int[][] y = new int[n][n];
	int[][] invertableMatrix = new int[n][n];
	for(int i=0; i<n; i++) {
		for(int j=0; j<n; j++) {
			if(i>=j) x[i][j] = num.nextInt(50) + (i == j ? 1 : 0); // diagonal values are kept non zero
			if(j>=i) y[i][j] = num.nextInt(50) + (i == j ? 1 : 0);
		}
	}
	// multiplication of the upper and lower triangular
	for(int i=0; i<n; i++) {
		for(int j=0; j<n; j++) {
			for(int k=0; k<n; k++) {
				invertableMatrix[i][j] += x[i][k] * y[k][j];
			}
		}
	}
	

	String invertableMatrixString = convertFromMatrixToStringofNumbers1(invertableMatrix);
	EncryptionMatrix.setTitle("The generated Encryption numbers are:");
	EncryptionMatrix.setHeaderText(invertableMatrixString);
	EncryptionMatrix.showAndWait();
	return invertableMatrix;
}
			
protected static double[][] encryptMessage(double[][] messageMatrix, int[][] invertableMatrix){
// perform the matrix multiplication here to encrypt message, return the encryptd message
	
//invertable Encrypter matrix
int[][] EncrypterMatrix = invertableMatrix;
int rowsMessage = messageMatrix.length; // rows of message matrix
int colsEncrypter = EncrypterMatrix[0].length; // columns of Encrypter matrix
double[][] encryptedMessage = new double[rowsMessage][colsEncrypter]; // new matrix to store the encrypted message

//perform matrix multiplication
for(int i = 0; i<rowsMessage; i++) {
	for(int j = 0; j<colsEncrypter; j++) {
		for(int k = 0; k<messageMatrix[0].length; k++ ) {
		encryptedMessage[i][j] += messageMatrix[i][k] * EncrypterMatrix[k][j];
		}
	}
}
System.out.print(colsEncrypter + " "+ rowsMessage);
for (int k = 0; k<rowsMessage; k++) {
	for (int j = 0; j<colsEncrypter; j++) {
		System.out.print(encryptedMessage[k][j] + " ");
	}
	System.out.println();
}

return encryptedMessage;	
}

protected static double[][] generateInverse(int[][] Matrix){
int a = Matrix[0][0], b = Matrix[0][1], c = Matrix[0][2];
int d = Matrix[1][0], e = Matrix[1][1], f = Matrix[1][2];
int g = Matrix[2][0], h = Matrix[2][1], i = Matrix[2][2];
	
double det = a*(e*i-f*h)-b*(d*i-f*g)+c*(d*h-e*g);
if (det == 0){
System.out.print("error");
return null;
}
double [][] inverse = new double[3][3];
double [][] adj = new double[3][3];


adj[0][0] = (e*i - f*h);
adj[0][1] = -(b*i - c*h);
adj[0][2] = (b*f - c*e);

adj[1][0] = -(d*i - f*g);
adj[1][1] = (a*i - c*g);
adj[1][2] = -(a*f - c*d);
adj[2][0] = (d*h - e*g);
adj[2][1] = -(a*h - b*g);
adj[2][2] = (a*e - b*d);


for (int k = 0 ;k<3;k++) {
	for (int j=0; j<3; j++) {
		inverse[k][j] = adj[k][j]/det;
	}
}

/*
for (int k = 0; k<3; k++) {
	for (int j = 0; j<3; j++) {
		System.out.print(inverse[k][j] + " ");
	}
	System.out.println();
}
*/
return inverse;
}

protected static double[][] decryptMessage(double[][] encryptdMatrix, double[][] inverse) {
// perform the matrix multiplication here to decrypt the message, return the decrypted message
	


int rowsMessage = encryptdMatrix.length; // rows of the encryptd message matrix
int colsdecrypt = inverse[0].length; // columns of the inverse decrypt matrix
double[][] decryptedMessage = new double[rowsMessage][colsdecrypt]; // matrix to store the decrypted message

// perform the matrix multiplication
for(int i = 0; i<rowsMessage; i++) {
	for(int j = 0; j<colsdecrypt; j++) {
		for(int k = 0; k<encryptdMatrix[0].length; k++ ) {
		decryptedMessage[i][j] += encryptdMatrix[i][k] * inverse[k][j];
		}
	}
}

return decryptedMessage;

}

protected static String convertFromMatrixToString(double [][] decryptedMessage) {
// converts the already decrypted message matrix back to a string

StringBuilder message = new StringBuilder(); 

int rows = decryptedMessage.length; // rows of the decrypted message matrix
int cols = decryptedMessage[0].length; // columns of the decrypted message matrix

for(int i = 0; i< rows; i++) {
	for(int j=0; j<cols; j++) {
		char c = (char) Math.round(decryptedMessage[i][j]);
		message.append(c);
	}
}

String Message = message.toString(); // convert the string builder into a string

// print the message
System.out.print("\nThe Secret Message is: \n");
System.out.print(Message);
return Message;

}


protected static String convertFromMatrixToStringofNumbers(double [][] decryptedMessage) {
	StringBuilder message = new StringBuilder(); 

	int rows = decryptedMessage.length; // rows of the matrix
	int cols = decryptedMessage[0].length; // columns of the matrix

	for(int i = 0; i< rows; i++) {
		for(int j=0; j<cols; j++) {
			long c =  Math.round(decryptedMessage[i][j]);
			message.append(c);
			message.append(" ");
		}
	}

	String Message = message.toString(); // convert the string builder into a string

	// print the message
	System.out.println(Message + "\n");
	return Message;

	}

protected static String convertFromMatrixToStringofNumbers1(int [][] decryptedMessage) {
	StringBuilder message = new StringBuilder(); 

	int rows = decryptedMessage.length; // rows of the decrypted message matrix
	int cols = decryptedMessage[0].length; // columns of the decrypted message matrix

	for(int i = 0; i< rows; i++) {
		for(int j=0; j<cols; j++) {
			long c =  Math.round(decryptedMessage[i][j]);
			message.append(c);
			message.append(" ");
		}
	}

	String Message = message.toString(); // convert the string builder into a string

	// print the key
	System.out.println(Message);
	return Message;

	}


// Quits the program when the user presses the Quit button	`
protected static void performQuit() {
	System.out.println("Perform Quit");
	System.exit(0);
}	

}