package com.mlabs;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxLink;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.handler.resource.ResourceStreamRequestHandler;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.resource.ContentDisposition;
import org.apache.wicket.util.resource.AbstractResourceStreamWriter;
import org.apache.wicket.util.resource.FileResourceStream;
import org.apache.wicket.util.resource.IResourceStream;

import java.io.File;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;

    private transient IndicatingAjaxLink mDownload;

    public HomePage(final PageParameters parameters) {
		//add(new Label("version", getApplication().getFrameworkSettings().getVersion()));

        /*final AJAXDownload download = new AJAXDownload()
        {
            @Override
            protected IResourceStream getResourceStream()
            {

                AbstractResourceStreamWriter rstream = new AbstractResourceStreamWriter(){

                    public void write(Response output) {
                        output.write(MyPDFSharedResource.getPDFBytes());
                    }
                };

                return rstream;
            }

            @Override
            protected String getFileName()
            {
                return MyPDFSharedResource.getFileName();
            }
        };
        add(download); */

        mDownload = new IndicatingAjaxLink("download"){

            @Override
            public void onClick(AjaxRequestTarget target) {

                //download.initiate(target);

                AbstractResourceStreamWriter rstream = new AbstractResourceStreamWriter(){

                    public void write(Response output) {
                        output.write(MyPDFSharedResource.getPDFBytes());
                    }
                };
                ResourceStreamRequestHandler handler = new ResourceStreamRequestHandler(rstream, MyPDFSharedResource.getFileName());
                handler.setContentDisposition(ContentDisposition.ATTACHMENT);
                getRequestCycle().scheduleRequestHandlerAfterCurrent(handler);

                String url = MyPDFSharedResource.getUrl();
                target.appendJavaScript("setTimeout(\"window.location.href='" + url + "'\", 100);");
            }

            //@Override
            public IAjaxCallDecorator getAjaxCallDecorator() {
                return new BlockUIDecorator();
            }
        };

        add(mDownload);

    }
}
