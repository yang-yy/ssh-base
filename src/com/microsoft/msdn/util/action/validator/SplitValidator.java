package com.microsoft.msdn.util.action.validator;


import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

public class SplitValidator extends FieldValidatorSupport {
    private String splitString;

    private int minCount;

    private int maxCount;

    private String singleRegex;

    private boolean ignoreEmpty = false;

    @Override
    public void validate(Object o) throws ValidationException {
        if (!(o instanceof ActionSupport)) return;
        ActionSupport action = (ActionSupport) o;
        if ((action.getFieldErrors() != null) && (action.getFieldErrors().size() > 0)) return;
        if (this.getFieldValue(this.getFieldName(), o) == null) return;
        if (minCount == 0) minCount = 1;
        if (maxCount == 0) maxCount = Integer.MAX_VALUE;

        String fieldName = this.getFieldName();
        String fieldValue = StringUtils.trim(ObjectUtils.toString(this.getFieldValue(fieldName, o), ""));
        String[] splits = StringUtils.split(fieldValue, splitString);

        if ((ignoreEmpty == true) && (fieldValue.length() == 0)) return;
        if (splits == null)
            return;
        if (splits.length < minCount)
            this.addFieldError(fieldName, o);
        if (splits.length > maxCount)
            this.addFieldError(fieldName, o);
        if (StringUtils.trimToEmpty(singleRegex).length() == 0) return;
        for (String single : splits) {
            if (single.matches(singleRegex)) continue;
            this.addFieldError(fieldName, o);
            break;
        }
    }

    public String getSplitString() {
        return splitString;
    }

    public void setSplitString(String splitString) {
        this.splitString = splitString;
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

    public String getSingleRegex() {
        return singleRegex;
    }

    public void setSingleRegex(String singleRegex) {
        this.singleRegex = singleRegex;
    }

    public boolean isIgnoreEmpty() {
        return ignoreEmpty;
    }

    public void setIgnoreEmpty(boolean ignoreEmpty) {
        this.ignoreEmpty = ignoreEmpty;
    }
}
