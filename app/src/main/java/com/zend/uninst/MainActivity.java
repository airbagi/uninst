package com.zend.uninst;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
  private static final String TAG = myClickService.class.getSimpleName();
  public static int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 2323;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Button button = (Button) findViewById(R.id.uninstbutton);
    button.setOnClickListener(
        new View.OnClickListener() {
          public void onClick(View v) {
            UninstallAppbyIntent(getApplicationContext(), getApplicationContext().getPackageName());
          }
        });
    if (!isAccessibilitySettingsOn(getApplicationContext())) RunAccessebilitySettings();
  }

  public static void UninstallAppbyIntent(Context context, String packageName) {
    try {
      Intent intent = new Intent(Intent.ACTION_DELETE, Uri.fromParts("package", packageName, null));
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
      intent.addFlags(Intent.FLAG_RECEIVER_NO_ABORT);
      intent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
      intent.putExtra("android.intent.extra.UNINSTALL_ALL_USERS", true);
      context.startActivity(intent);
    } catch (Exception e) {
      Log.e(TAG, "uninstall unssuccessfull " + e.toString());
    }
  } // ..UninstallAppbyIntent

  // To check if service is enabled
  private boolean isAccessibilitySettingsOn(Context mContext) {
    int accessibilityEnabled = 0;
    final String service =
        getPackageName() + "/" + myClickService.class.getCanonicalName();
    try {
      accessibilityEnabled =
          Settings.Secure.getInt(
              mContext.getApplicationContext().getContentResolver(),
              android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
      Log.v(TAG, "accessibilityEnabled = " + accessibilityEnabled);
    } catch (Settings.SettingNotFoundException e) {
      Log.e(TAG, "Error finding setting, default accessibility to not found: " + e.getMessage());
    }
    TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');

    if (accessibilityEnabled == 1) {
      String settingValue =
          Settings.Secure.getString(
              mContext.getApplicationContext().getContentResolver(),
              Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
      if (settingValue != null) {
        mStringColonSplitter.setString(settingValue);
        while (mStringColonSplitter.hasNext()) {
          String accessibilityService = mStringColonSplitter.next();

          Log.v(
              TAG,
              "-------------- > accessibilityService :: " + accessibilityService + " " + service);
          if (accessibilityService.equalsIgnoreCase(service)) {
            Log.v(TAG, "We've found the correct setting - accessibility is switched on!");
            return true;
          }
        }
      }
    } else {
      Log.v(TAG, "***ACCESSIBILITY IS DISABLED***");
    }

    return false;
  }

  protected void RunAccessebilitySettings() {
    Intent intent = new Intent();
    // intent.setClassName("com.android.settings", "com.android.settings.Settings");
    intent.setAction(Settings.ACTION_ACCESSIBILITY_SETTINGS);
    intent.addFlags(
        Intent.FLAG_ACTIVITY_NEW_TASK
            | Intent.FLAG_ACTIVITY_CLEAR_TASK
            | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
    startActivity(intent);
  }
}