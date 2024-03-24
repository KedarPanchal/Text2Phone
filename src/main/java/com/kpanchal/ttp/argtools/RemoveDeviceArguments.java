package com.kpanchal.ttp.argtools;

import com.beust.jcommander.Parameter;

public class RemoveDeviceArguments {
    @Parameter(names="Device Name", description="Device Name", password=true, echoInput=true)
    private String deviceName;

    public String getDeviceName() {
        return this.deviceName;
    }
}
