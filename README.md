# Text2Phone
A Java program that enables sending files from your PC to your phone using SMS Gateways.

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

Gmail logins are only supported at the moment.

### Supported cell service providers

The following cell providers can have files sent to them:
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

# Troubleshooting

# License
