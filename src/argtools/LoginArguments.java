package argtools;

import com.beust.jcommander.Parameter;

public class LoginArguments {
   @Parameter(names="Email Address") 
   private String emailAddress;

   @Parameter(names="Password", password=true)
   private String password;
}
