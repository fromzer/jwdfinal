package com.epam.jwd.tag;

import com.epam.jwd.validation.Violation;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;

public class FunctionContainsViolationTag extends TagSupport {
    private Object violationList;
    private String fieldName;

    @Override
    public int doStartTag() throws JspException {
        if (violationList == null) {
            return SKIP_BODY;
        }
        List<Violation> list = (List<Violation>) violationList;
        JspWriter out = pageContext.getOut();
        try {
            for (Violation violation : list) {
                if (violation.getField().equals(fieldName))
                    out.print(violation.getMessage());
            }
        } catch (IOException e) {
            throw new JspException(e);
        }
        return SKIP_BODY;
    }

    public Object getViolationList() {
        return violationList;
    }

    public void setViolationList(Object violationList) {
        this.violationList = violationList;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
