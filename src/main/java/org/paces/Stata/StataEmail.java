package org.paces.Stata;
import javax.mail.*;

/**
 * Interface for sending email from Stata.
 * @author Billy Buchanan
 * @version 0.0.0
 * @date 08dec2015
 */
public class StataEmail {

	public static void main(String[] args) {
		try {
			EmailTest newEmail = new EmailTest(args);
			newEmail.sendTheMessage();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	/***
	 * Method used to construct a new email message object and send it using
	 * the Java API from Stata.
	 * @param args Part of required method signature
	 * @return Success indicator (0 = Success; 1 = Error)
	 */
	public static int sendMail(String[] args) {

		// Try to send the message
		try {

			// Create a new email message object
			EmailTest newEmail = new EmailTest(args);

			/*
			newEmail.sender
					.getJavaMailProperties()
					.stringPropertyNames()
					.iterator()
					.forEachRemaining(name ->
							SFIToolkit.displayln("Property name : " + name +
									"\tProperty value : " +
									newEmail
											.sender
											.getJavaMailProperties()
											.getProperty(name)));
			*/


			// Try to send it
			newEmail.sendTheMessage();

			// If it succeeded return the success code
			return(0);

		// If there were any exceptions
		} catch (MessagingException e) {

			// Print the stack trace to the Stata console
			//SFIToolkit.errorln(SFIToolkit.stackTraceToString(e));

			// Return a failure code
			return(1);

		} // End of Try/Catch block

	}	// End of method declaration

} // End of Class declaration
