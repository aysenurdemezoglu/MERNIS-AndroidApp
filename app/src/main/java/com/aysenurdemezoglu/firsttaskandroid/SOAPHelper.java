package com.aysenurdemezoglu.firsttaskandroid;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class SOAPHelper {

    private static final String NAMESPACE = "http://example.com/FirstTask/service";
    private static final String METHOD_NAME = "tcnodogrulaRequest";
    private static final String SOAP_ACTION = NAMESPACE + "/" + METHOD_NAME;
    private static final String URL = "http://<your-backend-url>/service?wsdl";

    public boolean sendSOAPRequest(String ad, String soyad, String dogumyili, String tc) {
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("ad", ad);
            request.addProperty("soyad", soyad);
            request.addProperty("dogumyili", dogumyili);
            request.addProperty("tc", tc);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(SOAP_ACTION, envelope);

            SoapObject response = (SoapObject) envelope.getResponse();
            return Boolean.parseBoolean(response.getProperty("result").toString());

        } catch (Exception e) {
            Log.e("SOAP", "Error: " + e.getMessage());
            return false;
        }
    }
}