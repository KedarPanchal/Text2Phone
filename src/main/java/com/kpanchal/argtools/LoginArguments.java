package com.kpanchal.argtools;

import com.beust.jcommander.Parameter;

public class LoginArguments {
   @Parameter(names="Email Address", description="Email Address", password=true, echoInput=true) 
   private String emailAddress;

   // For some reason this is visible in the command line. That shouldn't be the case.
   // Seeing passwords is bad. This might be because Windows bad. We'll see.
   @Parameter(names="Password", description="Password", password=true, echoInput=false)
   private String password;

   @Parameter(names="Email Provider", description="""
         [1] America OnLine
         [2] G-Mail
         [3] Hotmail/Windows Live
         [4] iCloud
         [5] Yahoo!
         Enter the index of your email service provider""",
         password=true, echoInput=true)
   private int SMTPProviderIndex;

   public String getEmailAddress() {
      return this.emailAddress;
   }

   public String getPassword() {
      return this.password;
   }

   public int getSMTPProviderIndex() {
      return this.SMTPProviderIndex;
   }
}