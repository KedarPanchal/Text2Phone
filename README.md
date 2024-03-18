# Text2Phone
A Java program that enables sending files from your PC to your phone using MMS Gateways.

# Table of contents
- [Usage](#usage)
    - [Flags](#flags)
      - `-l` (or) `--login`
      - `-o` (or) `--logout`
      - `-a` (or) `--add-device`
      - `-r` (or) `--remove-device`
      - `-d` (or) `--list-devices`
      - `-s` (or) `--send` (or) `--send-file`
      - `-h` (or) `--help`
    - [Supported SMTP providers](#supported-smtp-providers)
    - [Supported cell service providers](#supported-cell-service-providers)
  - [Installation](#installation)
  - [Troubleshooting](#troubleshooting)
  - [License](#license)

# Usage
[(Back to top)](#table-of-contents)
### Flags
- With `-l` (or) `--login`: Logs into your SMTP provider. See [Supported SMTP providers](#supported-smtp-providers) for a full list of supported providers.

  ![login image](https://github.com/KedarPanchal/Text2Phone/assets/115903691/224478f9-8599-4a41-b44c-be596f637b8b)

  
- With `-o` (or) `--logout`: Logs out of your SMTP provider.

  ![logout image](https://github.com/KedarPanchal/Text2Phone/assets/115903691/fde82bdb-5ec1-4c9e-bb54-3dc52e7308f5)

- With `a` (or) `--add-device`: Adds a device to send files to.

  ![add device image](https://github.com/KedarPanchal/Text2Phone/assets/115903691/665a17a1-6dd1-4b07-9cfb-cf78cc426514)

- With `r` (or) `--remove-device`: Remove a device so files can no longer be sent to it.

  ![remove device image](https://github.com/KedarPanchal/Text2Phone/assets/115903691/90c92505-55f8-4677-8a90-faef1f213e26)

- With `d` (or) `--list-devices`: List all devices to which files can be sent.

  ![image](https://github.com/KedarPanchal/Text2Phone/assets/115903691/7c2f73a5-a364-4565-bbcb-4d7335988ef6)

- With `-s` (or) `--send` (or) `--send-file` `<device name> <filepath>` : Sends the file specified at `<filepath>` to an added device with the name `<device name>`. If the file at `<filepath>` is a folder, it is zipped and sent to the device. If no file at `<filepath>` exists, the value of the `<filepath>` argument is sent as a text message instead.

  ![image](https://github.com/KedarPanchal/Text2Phone/assets/115903691/bf4ac659-39ad-47f9-b677-5e04d15c2a97)
  
- With `-h` (or) `--help`: Prints out the descriptions of all the available arguments. If a flag is specified that's an invalid argument for this program, the output of the `--help` flag is printed instead.

  ![image](https://github.com/KedarPanchal/Text2Phone/assets/115903691/82562390-8d20-4c9b-acf9-8004782700fc)

### Supported SMTP providers

Text2Phone uses MMS gateways to send files to your phone, which uses SMTP (E-Mail) in the backend. Gmail logins are only supported at the moment.

### Supported cell service providers

The following cell service providers can have files sent to them:
- Alltel
- AT&T
- Boost Mobile
- Consumer Cellular
- Cricket Wireless
- Google Fi Wireless
- MetroPCS
- Spectrum Mobile
- Sprint
- T-Mobile
- U.S. Cellular
- Verizon Wireless
- Virgin Mobile
- XFinity Mobile

# Installation
[(Back to top)](#table-of-contents)

1. [Install](https://www.oracle.com/java/technologies/downloads/) JDK 21 or later.
2. [Install](https://maven.apache.org/download.cgi) Apache Maven (version 3.9.6 or higher is preferred).
3. Generate an executable `.jar` with `mvn clean install`. The generated `.jar` should be in the `target` folder.
4. Run the `.jar` with the command `java -jar target/ttp-1.0.0-shaded.jar <flags> <arguments>`
5. OPTIONAL -- Set an alias to the command in the above step to quickly use this program

# Troubleshooting
[(Back to top)](#table-of-contents)
### `Error: Unable to send file` message
The error mainly occurs because Gmail requires users to use an "app password" when logging into third-party apps. Generate an app password for your Gmail account by following [these instructions](https://support.google.com/accounts/answer/185833?hl=en) and then log in to the program using the app password you just generated. 
### The program is saying `Successfully sent to device` but I'm not receiving messages
Some cell service providers (like T-Mobile) block emails from sending messages through MMS gateways repeatedly in a short period. Add the email you use with this program to your device's contacts, and then try sending the files to your device again. If that doesn't work, Gmail allows adding a `+` character followed by text before the `@` symbol when using your email address. For example, `test@gmail.com` and `test+sometext@gmail.com` are both considered the same email address and use the same login credentials. Try logging in with a similarly modified email address and sending your file to a device again.

# License
[(Back to top)](#table-of-contents)
The BSD 3-Clause License (BSD-3) 2024 - [Kedar Panchal](https://github.com/KedarPanchal). Please look at the [LICENSE](LICENSE) for further information.
