package com.aysenurdemezoglu.firsttaskandroid.model;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

public class PersonRequest implements KvmSerializable {
    private String ad;
    private String soyad;

    public PersonRequest(String ad, String soyad, long tc, int dogumyili) {
        this.ad = ad;
        this.soyad = soyad;
        this.tc = tc;
        this.dogumyili = dogumyili;
    }

    private long tc;
    private int dogumyili;



    @Override
    public Object getProperty(int index) {
        switch(index)
        {
            case 0:
                return ad;

            case 1:
                return soyad;

            case 2:
                return tc;

            case 3:
                return dogumyili;


            default:break;
        }
        return "test";
    }

    @Override
    public int getPropertyCount() {
        return 4;
    }

    @Override
    public void setProperty(int index, Object value) {

    }

    @Override
    public void getPropertyInfo(int index, Hashtable properties, PropertyInfo info) {
        switch(index)
        {
            case 0:
                info.type = PropertyInfo.STRING_CLASS;

                info.name
                        = "ad";
                break;
            case 1:
                info.type = PropertyInfo.STRING_CLASS;

                info.name
                        = "soyad";
                break;
            case 2:
                info.type = PropertyInfo.LONG_CLASS;

                info.name
                        = "tc";
                break;
            case 3:
                info.type = PropertyInfo.INTEGER_CLASS;

                info.name
                        = "dogumyili";
                break;

            default:break;
        }
    }
}
