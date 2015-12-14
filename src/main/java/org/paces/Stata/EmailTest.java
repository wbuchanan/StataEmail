package org.paces.Stata;

import org.springframework.core.io.*;
import org.springframework.mail.javamail.*;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.*;
import java.util.Date;
import java.util.Properties;

/**
 * @author Billy Buchanan
 * @version 0.0.0
 */
class EmailTest {

	/***
	 * Password authentication object (used for authenticating the client
	 * server connection)
	 */
	private final PasswordAuthentication pwauth;

	/***
	 * Spring.io JavaMailSenderImplementation Object
	 */
	final JavaMailSenderImpl sender = new JavaMailSenderImpl();

	/***
	 * JavaMail properties
	 */
	private final Properties mailprops = new Properties();

	/***
	 * Helper class to construct mail messages with attachments
	 */
	private MimeMessageHelper helper;

	/***
	 * MimeMessage object used to construct the email message
	 */
	private MimeMessage message;

	/***
	 * A JavaMail Session Object
	 */
	private Session session;

	/***
	 * Option to specify if a multipart message is required
	 */
	private boolean ismultipart = false;

	/***
	 * Option to specify if the user is passing embedded HTML content
	 */
	private boolean htmlcontent = false;

	/***
	 * Parameters used for sending and authenticating
 	 */
	private final String username, password, port, host, protocol;

	public static final String SMTP_HOST = "mail.smtp.host";
	public static final String SMTP_PORT = "mail.smtp.port";
	public static final String SMTP_USERNAME = "mail.smtp.username";
	public static final String SMTP_PASSWORD = "mail.smtp.password";
	public static final String SMTP_PROTOCOL = "mail.transport.protocol";
	public static final String SMTPS_HOST = "mail.smtps.host";
	public static final String SMTPS_PORT = "mail.smtps.port";
	public static final String SMTPS_USERNAME = "mail.smtps.username";
	public static final String SMTPS_PASSWORD = "mail.smtps.password";
	public static final String SMTPS_PROTOCOL = "mail.transport.protocol";
	public Object hstname;
	public Object prtnum;
	public Object pwd;
	public Object uname;
	public Object proto;

	/***
	 * Main method for class used primarily for testing
	 * @param args Arguments passed from javacall command in Stata.
	 */
	public static void main(String[] args) {
		try {
			EmailTest x = new EmailTest(args);
			x.sendTheMessage();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	/***
	 * Class Constructor method
	 * @param stargs Arguments passed from javacall command in Stata
	 */
	public EmailTest(String[] stargs) throws MessagingException {

		// Set the JavaMail properties
		this.setProperties(stargs[0]);

		if (stargs[5].equals("smtps") ||
			this.mailprops.getProperty("mail.transport.protocol").equals("smtps")) {
			this.prtnum = SMTPS_PORT;
			this.pwd = SMTPS_PASSWORD;
			this.proto = SMTPS_PROTOCOL;
			this.hstname = SMTPS_HOST;
			this.uname = SMTPS_USERNAME;
		}
		else {
			this.hstname = SMTP_HOST;
			this.prtnum = SMTP_PORT;
			this.pwd = SMTP_PASSWORD;
			this.proto = SMTP_PROTOCOL;
			this.uname = SMTP_USERNAME;
		}


		if (!stargs[1].isEmpty()) {
			// Get the host property
			this.mailprops.put(this.hstname, stargs[1]);
		}

		if (!stargs[2].isEmpty()) {
			// Get the port property
			this.mailprops.put(this.prtnum, stargs[2]);
		}

		if (!stargs[3].isEmpty()) {
			// Get the password property
			this.mailprops.put(this.pwd, stargs[3]);
		}

		if (!stargs[4].isEmpty()) {
			// Get the username property
			this.mailprops.put(this.uname, stargs[4]);
		}

		if (!stargs[5].isEmpty()) {
			// Get the Properties directly from options in the Stata program
			// Get the protocol property
			this.mailprops.put(this.proto, stargs[5]);
		}

		this.host = this.mailprops.getProperty(String.valueOf(this.hstname));
		this.port = mailprops.getProperty(String.valueOf(this.prtnum));
		this.password = this.mailprops.getProperty(String.valueOf(this.pwd));
		this.username = this.mailprops.getProperty(String.valueOf(this.uname));
		this.protocol = this.mailprops.getProperty(String.valueOf(this.proto));

		this.sender.setHost(this.host);
		this.sender.setPort(Integer.valueOf(mailprops.getProperty(String.valueOf(this.prtnum))));
		this.sender.setPassword(this.password);
		this.sender.setUsername(this.username);
		this.sender.setProtocol(this.protocol);

		// initializes the password authentication object
		pwauth = new PasswordAuthentication(this.username, this.password);

		// Creates a new authenticator with the user/pw passed to it.
		Authenticator auth = new Authenticator() {
			/**
			 * Called when password authentication is needed.  Subclasses should
			 * override the default implementation, which returns null. <p>
			 * <p>
			 * Note that if this method uses a dialog to prompt the user for this
			 * information, the dialog needs to block until the user supplies the
			 * information.  This method can not simply return after showing the
			 * dialog.
			 *
			 * @return The PasswordAuthentication collected from the
			 * user, or null if none is provided.
			 */
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return pwauth;
			}
		};

		// Creates a session Object
		this.session = Session.getInstance(this.mailprops, auth);

		// Set the Properties for the sender object
		this.sender.setJavaMailProperties(this.mailprops);

		// Sets the session value for the JavaMailSenderImpl object
		this.sender.setSession(this.session);

		// Creates new message object
		this.message = sender.createMimeMessage();

		// Check for attachments
		this.checkAttachments(stargs[6], stargs[7]);

		// Set the FROM field of the distrolist
		if (!stargs[8].isEmpty()) {
			this.helper.setFrom(stargs[8]);
		}
		else {
			this.helper.setFrom(this.username);
		}

		// Check for user specified HTML content
		this.htmlcontent = Boolean.valueOf(stargs[9]);

		// Sets the content for the body.  HTML content is triggered
		// automatically if user specifies any
		this.helper.setText(stargs[10], this.htmlcontent);

		// Sets the subject line for the email
		if (!stargs[11].isEmpty()) {
			this.helper.setSubject(stargs[11]);
		}
		else {
			this.helper.setSubject("StataEmail Message");
		}

		// Sets the date to display for when the message was sent
		this.helper.setSentDate(new Date());

		// Sets the TO field of the distrolist
		this.setTo(stargs[12]);

		// Sets the CC field of the distrolist
		this.setCC(stargs[13]);

		// Sets the BCC field of the distrolist
		this.setBCC(stargs[14]);

		// Sets the reply to field
		if (!stargs[15].isEmpty()) this.helper.setReplyTo(stargs[15]);

		// Sets the message priority
		this.helper.setPriority(Integer.valueOf(stargs[16]));

		// Sets option to validate email addresses
		this.helper.setValidateAddresses(Boolean.valueOf(stargs[17]));

	} // End of the Class constructor method

	/***
	 * Method to send the email message
	 */
	public void sendTheMessage() {

		// Sends the message
		this.sender.send(this.message);

	} // End of Method declaration


	/***
	 * Method to check for any attachments and call MimeMessageHelper
	 * constructor.  Also sets the ismultipart member by checking whether or
	 * not the user passed any values to the attachment or inline parameters.
	 * @param attachments Content to be attached to the email as a file
	 * @param inline Content to be embedded in the message of the email w/HTML.
	 */
	private void checkAttachments(String attachments, String inline) throws MessagingException {
		if (!attachments.isEmpty()) {
			this.ismultipart = true;
			createHelper();
			attachFiles(attachments);
			attachInlineFiles(inline);
		} else {
			createHelper();
		}
	}

	/***
	 * Method to intialize the MimeMessageHelper class
	 */
	private void createHelper() throws MessagingException {
		this.helper = new MimeMessageHelper(this.message, this.ismultipart);
	}

	/***
	 * Method to add files as attachments to the email
	 * @param filenames Comma separated list of file names to attach to the
	 *                     email
	 */
	private void attachFiles(String filenames) {
		try {
			String[] filenm = filenames.replaceAll(" ", "").split(",");
			for (String aFilenm : filenm) {
				FileSystemResource file = new FileSystemResource(new File(aFilenm));
				helper.addAttachment(aFilenm, file);
			}
		} catch (MessagingException e) {
			// Print the stack trace to the Stata console
			System.out.println(String.valueOf(e));
		}
	}

	/***
	 * Method to add attachments as Inline attachments
	 * @param filenames Comma separated list of file names to insert into the
	 *                     email
	 */
	private void attachInlineFiles(String filenames) throws MessagingException {
		if (!filenames.isEmpty()) {
			String[] filenm = filenames.replaceAll(" ", "").split(";");
			for (String aFilenm : filenm) {
				String[] resource = aFilenm.split(",");
				if (!resource[0].isEmpty() && !resource[1].isEmpty()) {
					FileSystemResource file = new FileSystemResource(new File(resource[0]));
					helper.addInline(resource[1], file);
				}
			}
		}
	}

	/***
	 * Method to set the TO field of the distro list
	 * @param to String with comma separated list of recipients
	 */
	private void setTo(String to)  {
		try {
			if (!to.isEmpty()) {
				String[] torecipients = to.replaceAll(" ", "").split(",");
				for (String torecipient : torecipients) helper.addTo(torecipient);
			}
		} catch (MessagingException e) {
			// Print the stack trace to the Stata console
			System.out.println(String.valueOf(e));
		}
	}

	/***
	 * Method to set the CC field of the distro list
	 * @param cc String with comma separated list of recipients
	 */
	private void setCC(String cc)  {
		try {
			if (!cc.isEmpty()) {
				String[] ccrecipients = cc.replaceAll(" ", "").split(",");
				for (String ccrecipient : ccrecipients) helper.addCc(ccrecipient);
			}
		} catch (MessagingException e) {
			// Print the stack trace to the Stata console
			System.out.println(String.valueOf(e));
		}
	}

	/***
	 * Method to set the BCC field of the distro list
	 * @param bcc String with comma separated list of recipients
	 */
	private void setBCC(String bcc) throws MessagingException {
		try {
			if (!bcc.isEmpty()) {
				String[] bccrecipients = bcc.replaceAll(" ", "").split(",");
				for (String bccrecipient : bccrecipients)
					helper.addBcc(bccrecipient);
			}
		} catch (MessagingException e) {
			// Print the stack trace to the Stata console
			System.out.println(String.valueOf(e));
		}
	}

	/***
	 * Method to process a properties file for the StataEmail package
	 * @param fileLine A string containing a single line from the file
	 */
	private void fileProcessor(String fileLine) {

		// Parse the line on the '=' symbol separating the key/value pair for
		// the properties
		String[] x = fileLine.split("=");

		// Make sure the length of the parsed string is 2
		if (x.length == 2) {

			// If both entries are not empty set the property
			if (!x[0].isEmpty() && !x[1].isEmpty()) {
				this.mailprops.setProperty(String.valueOf(x[0]), String.valueOf(x[1]));
			}

		} // End IF Block for properly formatted lines

		// If the properties line is not properly formatted or is null
		else {

			// Construct error message back to Stata console
			String stmsg = "The property " + String.valueOf(x[0]) +
					" was malformed and will be ignored.";

			// Print error message to the Stata console
			System.out.println(stmsg);

		} // End ELSE Block for malformed

	} // End of fileProcessor method

	/***
	 * Method to set mail properties
	 * @param props The filename where the JavaMail properties file is located
	 */
	private void setProperties(String props) {

		// If the value is not empty
		if (!props.isEmpty()) {

			// Creates a reader object to read the file line by line
			BufferedReader br;

			// Initialize the
			String strLine = "";

			// Attempt creating a stream object of the file's lines
			try {

				// Passes each line of the file to the fileProcessor method
				// of this class which parses the lines and pushes the parsed
				// values into Properties and/or into specific methods from
				// the Spring Framework related to the JavaMail implementation
				br = new BufferedReader( new FileReader(props));

				// Loop over lines of the file until the readLine method
				// returns null
				while ((strLine = br.readLine()) != null) {

					// Only Pass values to the fileProcessor method if the
					// string is not empty
					if (!strLine.isEmpty()) fileProcessor(strLine);

				} // End of While loop

			// If there is an exception reading in the file
			} catch (FileNotFoundException e) {

				// Print the error message to the Stata console
				System.out.println(String.valueOf(e));

			} catch (IOException e) {

				// Print the error message to the Stata console
				System.out.println(String.valueOf(e));

			}  // End of Catch blocks

		} // End IF Block for non-empty method argument

	} // End Method to set the JavaMail properties

} // End of EmailTest Class
