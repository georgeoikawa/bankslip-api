package com.bankslip.web.validator;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.bankslip.service.IMessage;
import com.bankslip.web.message.MessageInfo;

public abstract class AbstractValidatior {

    protected void validateRequiredFields(final Object object, final List<MessageInfo> errorList,
            final IMessage message) {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        final Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object);
        for (final ConstraintViolation<Object> constraintViolation : constraintViolations) {
            final MessageInfo mensagem = message.getMessageInfo(constraintViolation.getMessage());
            errorList.add(mensagem);
        }
    }
}
