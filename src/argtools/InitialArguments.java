package argtools;

import java.util.List;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(parametersValidators=InitialArgumentValidator.class)
public class InitialArguments {
    @Parameter(names={"-l", "--login"}, description="Login to SMTP provider")
    private boolean login = false;

    @Parameter(names={"-c"}, description="Add contact information")
    private boolean contact = false;

    @Parameter(description="Sending file to iPhone address", variableArity=true)
    private List<String> sendInfo;
}
