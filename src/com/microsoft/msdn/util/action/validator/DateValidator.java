package com.microsoft.msdn.util.action.validator;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;


public class DateValidator extends FieldValidatorSupport {
    private boolean ignoreEmpty = false;

    @Override
    public void validate(Object o) throws ValidationException {
        if (!(o instanceof ActionSupport)) return;
        ActionSupport action = (ActionSupport) o;
        if ((action.getFieldErrors() != null) && (action.getFieldErrors().size() > 0)) return;
        if (this.getFieldValue(this.getFieldName(), o) == null) return;

        String fieldName = this.getFieldName();
        String fieldValue = fieldValue = StringUtils.trim(ObjectUtils.toString(this.getFieldValue(fieldName, o), ""));
        String dateRegex = "^((18|19|20)\\d{2})-((0[1-9]|1[0-2])|[1-9])-((0[1-9]|1\\d{1}|2\\d{1}|3[0-1])|[1-9])( ([0-1]\\d|2[0-3]|\\d{1}):([0-5]\\d{1}|\\d{1}):([0-5]\\d{1}|\\d{1})){0,1}$";

        if ((ignoreEmpty == true) && (fieldValue.length() == 0)) return;
        if (fieldValue.matches(dateRegex)) {
            boolean right = false;
            try {
                right = isDateRight(fieldValue);
            } catch (Exception e) {
                right = false;
            }
            if (!right)
                this.addFieldError(fieldName, o);
        } else {
            this.addFieldError(fieldName, o);
        }
    }

    private boolean isDateRight(String dateStr) throws Exception {
        int realMonth = DateUtils.parseDate(dateStr, new String[]{"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"}).getMonth() + 1;
        int strMonth = Integer.valueOf(dateStr.substring(dateStr.indexOf("-") + 1, dateStr.lastIndexOf("-")));
        return realMonth == strMonth;
    }

    public boolean isIgnoreEmpty() {
        return ignoreEmpty;
    }

    public void setIgnoreEmpty(boolean ignoreEmpty) {
        this.ignoreEmpty = ignoreEmpty;
    }
}
