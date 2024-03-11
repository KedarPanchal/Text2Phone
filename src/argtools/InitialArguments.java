package argtools;

import java.util.List;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(parametersValidators=InitialArgumentsValidator.class)
public class InitialArguments {
    @Parameter(names={"-l", "--login"}, description="Login to SMTP provider")
    private boolean login = false;

    @Parameter(names={"-o", "--logout"}, description="Logout of SMTP provider")
    private boolean logout = false;

    @Parameter(names={"-ld", "--list-devices"}, description="Lists devices")
    private boolean listDevices = false;

    @Parameter(names={"-h", "--help"}, help=true)
    private boolean help = false;

    @Parameter(names={"-a", "--add-device"}, arity=3, description="Add device")
    private List<String> deviceInfo;

    @Parameter(names={"-s", "--send", "--send-file"}, description="Sending file to iPhone address", arity=2)
    private List<String> sendInfo;

    public boolean getLogin() {
        return this.login;
    }

    public boolean getLogout() {
        return this.logout;
    }

    public boolean getListDevices() {
        return this.listDevices;
    }

    public List<String> getDeviceInfo() {
        return this.deviceInfo;
    }

    public List<String> getSendInfo() {
        return this.sendInfo;
    }
}