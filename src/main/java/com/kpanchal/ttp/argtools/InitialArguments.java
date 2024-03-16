package com.kpanchal.ttp.argtools;

import java.util.List;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(parametersValidators=InitialArgumentsValidator.class)
public class InitialArguments {
    @Parameter(names={"-l", "--login"}, description="Login to SMTP provider")
    private boolean login = false;

    @Parameter(names={"-o", "--logout"}, description="Logout of SMTP provider")
    private boolean logout = false;
    
    @Parameter(names={"-a", "--add-device"}, description="Adds a device to have images sent to later")
    private boolean addDevice = false;

    @Parameter(names={"-r", "--remove-device"}, description="Removes a device (images cannot be sent to this device)")
    private boolean removeDevice = false;

    @Parameter(names={"-d", "--list-devices"}, description="Lists devices that can have images be sent to")
    private boolean listDevices = false;

    @Parameter(names={"-s", "--send", "--send-file"}, description="Sends a file to iPhone address. The first argument is the name of the device the fill will be sent to. The second is the path of the file that will be sent to the device.", arity=2)
    private List<String> sendInfo;

    @Parameter(names={"-h", "--help"}, description="Lists all commands and their usages", help=true)
    private boolean help = false;

    
    

    public boolean getLogin() {
        return this.login;
    }

    public boolean getLogout() {
        return this.logout;
    }

    public boolean getListDevices() {
        return this.listDevices;
    }

    public boolean getHelp() { // Me frfr
        return this.help;
    }

    public boolean getAddDevice() {
        return this.addDevice;
    }

    public boolean getRemoveDevice() {
        return this.removeDevice;
    }

    public List<String> getSendInfo() {
        return this.sendInfo;
    }
}