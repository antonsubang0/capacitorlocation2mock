package com.anton.capacitor.mocklocation;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import android.provider.Settings.Secure;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;

@CapacitorPlugin(name = "MockLocation")
public class MockLocationPlugin extends Plugin {

    private MockLocation implementation;

    @Override
    public void load() {
        implementation = new MockLocation(getContext());
    }

    @PluginMethod
    public void echo(PluginCall call) {
        String value = call.getString("value");

        JSObject ret = new JSObject();
        ret.put("value", "siap");
        call.resolve(ret);
    }

    @PluginMethod
    public void check(PluginCall call) {
        JSObject r = new JSObject();
        try {
            boolean gpsfake;
            if (implementation.check("check", null)) gpsfake = true;
            else gpsfake = false;
            r.put("fake", gpsfake);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        call.resolve(r);
    }

    @PluginMethod
    public void getUid(PluginCall call) {
        JSObject r = new JSObject();
        r.put("uuid", implementation.getUuid());
        call.resolve(r);
    }


}
