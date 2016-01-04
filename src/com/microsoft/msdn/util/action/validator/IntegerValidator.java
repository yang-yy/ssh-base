package com.microsoft.msdn.util.action.validator;


import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

public class IntegerValidator extends FieldValidatorSupport {
    private int min = 0;

    private int max = Integer.MAX_VALUE;

    private boolean ignoreEmpty = false;

    @Override
    public void validate(Object o) throws ValidationException {
        if (!(o instanceof ActionSupport)) return;
        ActionSupport action = (ActionSupport) o;
        if ((action.getFieldErrors() != null) && (action.getFieldErrors().size() > 0)) return;
        if (this.getFieldValue(this.getFieldName(), o) == null) return;

        String fieldName = this.getFieldName();
        String fieldValue = fieldValue = StringUtils.trim(ObjectUtils.toString(this.getFieldValue(fieldName, o), ""));
        String regex = "^[-]{0,1}(0|[1-9]\\d*)$";

        if ((ignoreEmpty == true) && (fieldValue.length() == 0)) return;
        if (fieldValue.matches(regex)) {
            int cmp = Integer.parseInt(fieldValue);
            if ((cmp >= min) && (cmp <= max)) return;
            else this.addFieldError(fieldName, o);
        } else
            this.addFieldError(fieldName, o);
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public boolean isIgnoreEmpty() {
        return ignoreEmpty;
    }

    public void setIgnoreEmpty(boolean ignoreEmpty) {
        this.ignoreEmpty = ignoreEmpty;
    }
}
