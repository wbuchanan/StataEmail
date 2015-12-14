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


 
