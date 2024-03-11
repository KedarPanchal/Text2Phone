package argtools;

import java.util.Map;

import com.beust.jcommander.IParametersValidator;
import com.beust.jcommander.ParameterException;

public class InitialArgumentsValidator implements IParametersValidator {
    @Override
    public void validate(Map<String,  Object> parameters) throws ParameterException {
        int count = 0;
        for (Map.Entry<String, Object> parameter : parameters.entrySet()) {
            if (parameter.getValue() != null && !parameter.getValue().equals(Boolean.FALSE)) {
                System.out.println(parameter);
                count++;
            }

            if (count > 1) {
                throw new ParameterException("Error: too many arguments!");
            }
        }
    }
}