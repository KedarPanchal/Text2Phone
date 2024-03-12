package argtools;

import com.beust.jcommander.Parameter;

public class LoginArguments {
   @Parameter(names="Email Address", description="Email Address", password=true, echoInput=true) 
   private String emailAddress;

   // For some reason this is visible in the command line. That shouldn't be the case.
   // Seeing passwords is bad. This might be because Windows bad. We'll see.
   @Parameter(names="Password", description="Password", password=true, echoInput=false)
   private String password;

   public String getEmailAddress() {
      return this.emailAddress;
   }

   public String getPassword() {
      return this.password;
   }
}