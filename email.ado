
// Drop program from memory if previously loaded
cap prog drop email

// Define program
prog def email

    // Version of Stata to use for interpreting code
    version 13.1

    // Syntax structure of program
    syntax, [ PROPerties(string asis) FRom(string asis)                      ///
        To(string asis) Cc(string asis) BCc(string asis)                     ///
        SUBject(string asis) REPlyto(string asis) Body(string asis)          ///
        PASSword(string asis) Attachments(string asis) DEBug                 ///
        Username(string asis) Priority(integer 0) Validate                   ///
        Inline(string asis) HTML Host(string asis) POrt(string asis)         ///
        PROTocol(string asis) ]

    // Check for required data for Java application
    if `"`username'`password'`host'`port'`protocol'`from'"' == "" &          ///
    `"`properties'"' == "" {

        // Print error message to console
        di as err "Must provide either a properties file OR pass arguments " ///
        "to the username, password, host, port, and protocol parameters"

        // Error out
        err 198

    } // End IF Block for invalid program signature

    // Check if properties argument is a file or a string
    cap confirm file `"`properties'"'

    // If not a file
    if _rc != 0 {

        // Create a new macro to store the additional properties
        loc additionalprops `properties'

        // Void the existing local
        loc properties

    } // End if block for additional properties

    // Check for option to validate email addresses (defaults to false)
    if `"`validate'"' == "" loc validate false

    // User requests option turned on
    else loc validate true

    // If user wants to override the content type of the body as HTML text this
    // option is used.  If the user passes any arguments to the inline option
    // it will automatically trigger this option (since the user is
    // responsible for providing the HTML required to specify the location of
    // the embedded content.  If not selected defaults to false.
    if `"`html'"' == "" & `"`inline'"' == "" loc htmlcontent false

    // If user specified turns the option on
    else loc htmlcontent true

    // Call Java plugin to send email
    /*
    javacall org.paces.Stata.StataEmail sendMail,                            ///
    args("`properties'" "`host'" "`port'" "`password'" "`username'"          ///
    "`protocol'" "`attachments'" "`inline'" "`from'" "`htmlcontent'"         ///
    "`body'" "`subject'" "`to'" "`cc'" "`bcc'" "`replyto'" "`priority'"      ///
    "`validate'")
    */

    ! java -jar "`c(sysdir_personal)'e/email.jar" `properties' "`host'"      ///
    "`port'" "`password'" "`username'" "`protocol'" "`attachments'"          ///
    "`inline'" "`from'" "`htmlcontent'" "`body'" "`subject'" "`to'" "`cc'"   ///
    "`bcc'" "`replyto'" "`priority'" "`validate'"


// End program definition
end

