package argtools;

import com.beust.jcommander.Parameter;

public class AddDeviceArguments {
    @Parameter(names="Device Name", description="Device Name", password=true, echoInput=true)
    private String deviceName;

    @Parameter(names="Phone Number", description="Phone Number", password=true, echoInput=true)
    private String phoneNumber;

    @Parameter(names="Cell Service Provider", description="""
            Enter the index of the device's cell service provider:
            [1] Alltel
            [2] AT&T
            [3] Boost Mobile
            [4] Consumer Cellular
            [5] Cricket Wireless
            [6] Google Fi Wireless
            [7] MetroPCS
            [8] Spectrum Mobile
            [9] Sprint
            [10] T-Mobile
            [11] U.S. Cellular
            [12] Verizon Wireless
            [13] Virgin Mobile
            [14] XFinity Mobile""",
            password=true, echoInput=true)
    private int cellProvider;

    public String getDeviceName() {
        return this.deviceName;
    }

    public String getPhoneNumber() {
        return this.phoneNumber.replaceAll("\\D", "");
    }

    public int getCellProviderIndex() {
        return this.cellProvider;
    }
}
