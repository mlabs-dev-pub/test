package com.mlabs;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.handler.resource.ResourceReferenceRequestHandler;
import org.apache.wicket.request.resource.ByteArrayResource;
import org.apache.wicket.request.resource.SharedResourceReference;
import org.apache.wicket.util.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public class MyPDFSharedResource extends ByteArrayResource {

    private static final String KEY = MyPDFSharedResource.class.getName();

    public static String getUrl() {
        return RequestCycle.get().urlFor(
                new ResourceReferenceRequestHandler(new SharedResourceReference(KEY))
        ).toString();
    }

    public static void register(WebApplication application) {

        application.getSharedResources().add(KEY, new MyPDFSharedResource("application/pdf"));
    }

    public MyPDFSharedResource(String contentType) {
        super(contentType);
    }

    @Override
    protected ResourceResponse newResourceResponse(final Attributes attributes)
    {
        ResourceResponse response = super.newResourceResponse(attributes);

        response.disableCaching();

        return response;
    }

    @Override
    protected byte[] getData(final Attributes attributes)
    {
        return MyPDFSharedResource.getPDFBytes();
    }

    public static byte[] getPDFBytes(){

        InputStream pdf = MyPDFSharedResource.class.getResourceAsStream("w4.pdf");

        if (pdf!=null){

            try {
                return IOUtils.toByteArray(pdf);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static String getFileName(){return "w4.pdf";}
}

