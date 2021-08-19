package com.zend.uninst;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
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

    RunAccessebilitySettings();
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