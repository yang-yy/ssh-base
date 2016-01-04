package com.microsoft.msdn.util.action.validator;


import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

import java.net.URLDecoder;


public class StringLengthValidator extends FieldValidatorSupport {
    private int minCount;

    private int maxCount;

    private int urlType;

    private String urlEncode;

    private String regex;

    private boolean ignoreEmpty = false;

    @Override
    public void validate(Object o) throws ValidationException {
        if (!(o instanceof ActionSupport)) return;
        ActionSupport action = (ActionSupport) o;
        if ((action.getFieldErrors() != null) && (action.getFieldErrors().size() > 0)) return;
        if (this.getFieldValue(this.getFieldName(), o) == null) return;
        if (minCount == 0) minCount = 1;
        if (maxCount == 0) maxCount = Integer.MAX_VALUE;
        if (urlType == 0) urlType = 1;
        if (StringUtils.isBlank(urlEncode)) urlEncode = "UTF-8";

        String fieldName = this.getFieldName();
        String fieldValue = StringUtils.trim(ObjectUtils.toString(this.getFieldValue(fieldName, o), ""));

        if ((ignoreEmpty == true) && (fieldValue.length() == 0)) return;
        if (this.urlType != 0) {
            fieldValue = StringUtils.trim(ObjectUtils.toString(this.getFieldValue(fieldName, o), ""));
            try {
                fieldValue = URLDecoder.decode(fieldValue, urlEncode);
            } catch (Exception e) {
                fieldName = "";
            }
            fieldValue = StringUtils.trimToEmpty(fieldValue);
        }

        if (fieldValue.length() < minCount)
            this.addFieldError(fieldName, o);
        if (fieldValue.length() > maxCount)
            this.addFieldError(fieldName, o);
        if (StringUtils.isNotBlank(regex) && !fieldValue.matches(StringUtils.trim(regex)))
            this.addFieldError(fieldName, o);
    }

    public int getMinCount() {
        return minCount;
    }

    public void setMinCount(int minCount) {
        this.minCount = minCount;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public int getUrlType() {
        return urlType;
    }

    public void setUrlType(int urlType) {
        this.urlType = urlType;
    }

    public String getUrlEncode() {
        return urlEncode;
    }

    public void setUrlEncode(String urlEncode) {
        this.urlEncode = urlEncode;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public boolean isIgnoreEmpty() {
        return ignoreEmpty;
    }

    public void setIgnoreEmpty(boolean ignoreEmpty) {
        this.ignoreEmpty = ignoreEmpty;
    }
}
