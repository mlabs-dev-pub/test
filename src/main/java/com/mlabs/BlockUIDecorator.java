package com.mlabs;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.IAjaxCallDecorator;

public class BlockUIDecorator implements IAjaxCallDecorator {

    public CharSequence decorateScript(Component component, CharSequence script) {
        return "$.blockUI({message:'downloading file, please wait...'});" + script;
    }

    public CharSequence decorateOnSuccessScript(Component component, CharSequence script) {
        return "$.unblockUI();" + script;
    }

    public CharSequence decorateOnFailureScript(Component component, CharSequence script) {
        return "$.unblockUI();" + script;
    }
}
