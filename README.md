[![Project Status: WIP - Initial development is in progress, but there has not yet been a stable, usable release suitable for the public.](http://www.repostatus.org/badges/latest/wip.svg)](http://www.repostatus.org/#wip)

# Stata SMTP Email Client
A Java plugin used to make it easier to integrate Stata and email into a single workflow.  Previous version of email available on SSC used Python but has since stopped working correctly.  New version of program is designed to leverage the Java API of Stata to process the call.  This program also uses the Spring Framework to simplify some of the code base.  

## Additional info
Program works from the command line, but has somewhat verbose syntax for that interface.  When called from Stata, a typical use of the program might look something like:

```
sysuse auto.dta, clear
tw scatter mpg weight || loess mpg weight
gr export scatterplot1.png, as(png) replace
email, from(me@me.com) to(me@me.com) sub(Job's done) attachments(`"`c(pwd)'/scatterplot1.png"') properties(/Users/me/stataEmail.props) body(The job finished and the scatterplot is attached)
```

Adding attachments and/or embedding attachments inline in the email have not yet been tested, but should work.  Any help testing would be appreciated.  To do so, download the file `target/StataEmail-jar-with-dependencies.jar` and `email.ado` and place them on your ADOPATH.  There isn't currently a helpfile, but the parameter names should be fairly unambiguous.  

## Minimal properties configuration
The minimal properties file that I've used to test and send emails from the 
program thus far set the following parameters in a properties file:

```
mail.debug=true
mail.transport.protocol=smtp
mail.smtp.username=username
mail.smtp.password=password
mail.smtp.host=hostname
mail.smtp.port=portnumber
mail.smtp.auth=true
mail.smtp.ssl.enable=true
mail.smtp.ssl.trust=hostname
mail.smtp.starttls.enable=true
mail.smtp.starttls.required=true
mail.smtp.socketFactory.class=javax.net.ssl.SSLSocketFactory
mail.smtp.socketFactory.fallback=true
```

Naturally, *username* should be your username used to authenticate with your 
email host, *password* should be the password you use to authenticate, 
*hostname* should be the DSN address for your mail server (e.g., smtp.mysite.com), 
*port* should be the port number used to connect (if you enable ssl this should 
be the port used for SSL-based connections), and I set *mail.smtp.ssl.trust* 
to the same address as the host.  
