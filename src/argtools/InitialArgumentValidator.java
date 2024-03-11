package argtools;

import java.util.Map;

import com.beust.jcommander.IParametersValidator;
import com.beust.jcommander.ParameterException;

public class InitialArgumentValidator implements IParametersValidator {
    @Override
    public void validate(Map<String,  Object> parameters) throws ParameterException {
        if (parameters.size() > 1) {
            throw new ParameterException("Error: Too many arguments");
        }
    }
}
