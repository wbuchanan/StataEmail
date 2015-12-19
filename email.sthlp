{smcl}
{* v 1.0.1 19DEC2015}{...}
{hline}
help for {hi:email}
{hline}

{title: Email from Stata}

{title:Syntax}

{p 4}{cmd:email}, [ {cmdab:prop:erties(}{it:filename}{opt )} 
{cmdab:fr:om(}{it:string}{opt )} {cmdab:t:o(}{it:string}{opt )}
{cmdab:c:c(}{it:string}{opt )} {cmdab:bc:c(}{it:string}{opt )}
{cmdab:sub:ject(}{it:string}{opt )} {cmdab:rep:lyto(}{it:string}{opt )}
{cmdab:b:ody(}{it:string}{opt )} {cmdab:pass:word(}{it:string}{opt )}
{cmdab:a:ttachments(}{it:string}{opt )} {cmdab:u:sername(}{it:string}{opt )}
{cmdab:p:riority(}{it:int}{opt )} {cmdab:v:alidate} 
{cmdab:i:nline(}{it:string}{opt )} {cmd html} {cmdab:h:ost(}{it:string}{opt )} 
{cmdab:po:rt(}{it:int}{opt )} {cmdab:prot:ocol(}{it:string}{opt )} ]

{title: Description}

{p 4 4 8}{cmd:email} is a Java-based plugin for Stata to help you send email directly from your Stata session.  If you have a long-running job and would like a notification when the job completes, this program provides you with the flexibility to do so without having to worry about scheduling an email through cron. The new version of the program allows you to specify connection details in a Java properties file or to provide the options through the program call itself ({it:highly recommend using the first option for security purposes.}).{p_end}

{p 4 4 8}{hi:You must specify either a properties file or pass the values as arguments to connect to your email server!}{p_end}

{title: Options}

{p 4 4 8}{cmdab:prop:erties} takes a fully qualified filepath (e.g., the path to the file and the name of the file containing the properties itself) from which to read your connection settings/properties.{p_end}

{p 4 4 8}{cmdab:f:rom} is the sending email address. {p_end}

{p 4 4 8}{cmdab:t:o} takes a comma separated list of email addresses to populate the {hi:TO} field of the distribution list. {p_end} 

{p 4 4 8}{cmdab:c:c} takes a comma separated list of email addresses to populate the {hi:CC} field of the distribution list. {p_end}

{p 4 4 8}{cmdab:bc:c} takes a comma separated list of email addresses to populate the {hi:BCC} field of the distribution list. 

{p 4 4 8}{cmdab:s:ubject} sets the subject line of the email. If no value is supplied, it will place "StataEmail Message" into the subject line (this can help with avoiding spam blockers). {p_end} 

{p 4 4 8}{cmdab:rep:lyto} the email address to which responses should be addressed. {p_end}

{p 4 4 8}{cmdab:b:ody} this option is for adding text to the body of the email.  The body can be plain text or HTML.  If the body uses HTML, be sure to turn on the HTML option to be safe.  {hi:For inline attachments, you must use HTML and specify the location where the attachments will be embedded in the DOM.}{p_end}

{p 4 4 8}{cmdab:pass:word} an option to pass login credentials to the server connection.  {it:Although this option is available, I cannot stress enough how much less prefered using this option is.}{p_end}

{p 4 4 8}{cmdab:a:ttachments} a comma separated list of fully qualified filepaths/names to attach to the email message.  {p_end}

{p 4 4 8}{cmdab:u:sername} is an option used to specify the login username. {p_end}

{p 4 4 8}{cmdab:p:riority} a numeric value indicating the priority with which the message should be sent.  Defaults to 0. {p_end}

{p 4 4 8}{cmdab:v:alidate} an option to have JavaMail attempt to validate the email addresses prior to sending the message. {p_end}

{p 4 4 8}{cmdab:i:nline} accepts a comma separated list of fully qualified filepaths/names containing files that should be embedded into the email inline with the text.  Using this option triggers the HTML option, but still requires that you correctly specify the HTML to correctly embed the media in the message. {p_end}

{p 4 4 8}{cmd html} is an option used to indicate that the body of the email message is formatted as HTML and {hi:not as plain text}. {p_end}

{p 4 4 8}{cmdab:h:ost} the hostname of your email service provider.  This can also be specified in the properties file. {p_end}

{p 4 4 8}{cmdab:po:rt} is the port number on the host to use for the connection.  Check your email client configuration if you're unsure of this.  Ports 25/26 are often used for SMTP (unsecured) email traffic, while port 465 is fairly common to use for SMTPS communications (secured). {p_end}

{p 4 4 8}{cmdab:prot:ocol} is an option used to specify which transport protocol to use.  For outgoing email, you are limited to SMTP (not secure) and SMTPS (secured) transport protocols. {p_end}

{title:Additional Information}
{p 4 4 8}The table below shows an example based on the email properties file that I use to connect to my email service.  You may/may not need to configure additional options.  In either case, you should take a look at the documentation/PropertiesList file at {browse "http://github.com/wbuchanan/StataEmail"}.{p_end}

{p2colset 10 35 35 10}
{p2line 0 35}{p2col:Property}Example Value{p_end}
{p2line 0 35}{p2colset 10 35 35 10}{p2col:mail.transport.protocol=}smtp{p_end}
{p2colset 10 35 35 10}{p2col:mail.smtp.username=}william@williambuchanan.net{p_end}
{p2colset 10 35 35 10}{p2col:mail.smtp.password=}*******************************{p_end}
{p2colset 10 35 35 10}{p2col:mail.smtp.host=}host243.hostmonster.com{p_end}
{p2colset 10 35 35 10}{p2col:mail.smtp.port=}465{p_end}
{p2colset 10 35 35 10}{p2col:mail.smtp.auth=}true{p_end}
{p2colset 10 35 35 10}{p2col:mail.smtp.ssl.enable=}true{p_end}
{p2colset 10 35 35 10}{p2col:mail.smtp.ssl.trust=}host243.hostmonster.com{p_end}
{p2colset 10 35 35 10}{p2col:mail.smtp.starttls.enable=}true{p_end}
{p2colset 10 35 35 10}{p2col:mail.smtp.starttls.required=}true{p_end}
{p2colset 10 35 35 10}{p2col:mail.smtp.socketFactory.class=}javax.net.ssl.SSLSocketFactory{p_end}
{p2colset 10 35 35 10}{p2col:mail.smtp.socketFactory.fallback=}true{p_end}
{p2colset 10 35 35 10}{p2col:Is Writable}true{p_end}
{p2line 0 35}


{title:Examples}

{p 4 4 8}{cmd:email}, {cmdab:t:o}({it:you@thisaddress.com}) {cmdab:f:rom}({it:me@me.com}) {cmdab:s:ubject}({it:"This is just a test"}) {cmdab:b:ody}({it:"No. Seriously. It is just a test."}) {cmdab:a:ttachment}({it:myfile.txt}) {cmd: directory(}{it:"/Users/my/Folder Is/Here/"}{cmd:)}

{title: Author}{break}
{p 1 1 1} William R. Buchanan, Ph.D. {break}
Data Scientist {break}
{browse "http://mpls.k12.mn.us":Minneapolis Public Schools} {break}
William.Buchanan at mpls [dot] k12 [dot] mn [dot] us
