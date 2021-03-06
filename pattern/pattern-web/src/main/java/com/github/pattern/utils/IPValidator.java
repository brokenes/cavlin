package com.github.pattern.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.baidu.unbiz.fluentvalidator.Validator;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;

public class IPValidator extends ValidatorHandler<String> implements Validator<String>{

	private static final String IP_REG = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";


	private String fieldName;

    public IPValidator(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public boolean validate(ValidatorContext context, String s) {
    	Pattern pattern = Pattern.compile(IP_REG);
    	Matcher matcher = pattern.matcher(s);
    	if(!matcher.find()) {
    		context.addError(ValidationError.create(String.format("%s格式不正确！", fieldName))
                    .setErrorCode(-1)
                    .setField(fieldName)
                    .setInvalidValue(s));
            return false;
    	}
        return true;
    }
    
}
