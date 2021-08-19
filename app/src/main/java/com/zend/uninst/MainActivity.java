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
            // Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_LONG).show();
            UninstallAppbyIntent(getApplicationContext(), getApplicationContext().getPackageName());
          }
        });

    getPermissions();

    // RunAccessebilitySettings();
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

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE) {
      // if (Settings.canDrawOverlays(this)) {
      // You have permission
      // }
    }
  }

  protected void getPermissions() {
    /*

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      if (!Settings.canDrawOverlays(this)) {
        Intent intent =
            new Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
      }
    }
    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SYSTEM_ALERT_WINDOW)
        != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(
          this, new String[] {Manifest.permission.SYSTEM_ALERT_WINDOW}, 1);
    }
    */
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