package com.anton.capacitor.mocklocation;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.provider.Settings.Secure;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MockLocation {

    private Context context;
    private int MY_PERMISSIONS_REQUEST = 0;

    private JSONArray arrayGPS = new JSONArray();
    private JSONObject objGPS = new JSONObject();
    private com.anton.capacitor.mocklocation.MockLocation mContext;

    MockLocation(Context context) {
        this.context = context;
    }

    public boolean check(String action, JSONArray data) throws JSONException {
        mContext = this;
        if (action.equals("check")) {
            if (android.os.Build.VERSION.SDK_INT <= 22) {
                if (Secure.getString(this.context.getContentResolver(), Secure.ALLOW_MOCK_LOCATION).equals("0")){
                    return false;
                }else{
                    return true;
                }

            }
            else {
                if (areThereMockPermissionApps(this.context)) {
                    return true;
                } else {
                    return false;
                }
            }
        }else {
            return false;
        }

    }

    public static boolean areThereMockPermissionApps(Context context) {
        int count = 0;

        PackageManager pm = context.getPackageManager();
        List<ApplicationInfo> packages =
                pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo applicationInfo : packages) {
            try {
                PackageInfo packageInfo = pm.getPackageInfo(applicationInfo.packageName,
                        PackageManager.GET_PERMISSIONS);

                // Get Permissions
                String[] requestedPermissions = packageInfo.requestedPermissions;

                if (requestedPermissions != null) {
                    for (int i = 0; i < requestedPermissions.length; i++) {
                        // Check for System App //
                        if(!((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 1)) {
                            if (requestedPermissions[i]
                                    .equals("android.permission.ACCESS_MOCK_LOCATION")
                                    && !applicationInfo.packageName.equals(context.getPackageName())) {
                                count++;
                            }
                        }
                    }
                }
            } catch (PackageManager.NameNotFoundException e) {
                Log.e("Got exception " , e.getMessage());
            }
        }

        if (count > 0)
            return true;
        return false;
    }

    public String getUuid() {
        return Secure.getString(this.context.getContentResolver(), Secure.ANDROID_ID);
    }

    public String echo(String value) {
        return value;
    }
}
