package com.zend.uninst;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.DialogInterface;
import android.os.Build;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import java.util.List;
// com.google.android.packageinstaller

public class myClickService extends AccessibilityService {
  private static final String TAG = myClickService.class.getSimpleName();
  private AccessibilityServiceInfo info = new AccessibilityServiceInfo();

  @Override
  public void onAccessibilityEvent(AccessibilityEvent event) {
    Log.i(TAG, "ACC::onAccessibilityEvent: " + event.getEventType());

    // TYPE_WINDOW_STATE_CHANGED == 32
    if (1/*AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED */== event.getEventType()) {
      AccessibilityNodeInfo nodeInfo = event.getSource();
      AccessibilityNodeInfo rootNode = getRootInActiveWindow();

      // exploreNodeHierarchy(getRootInActiveWindow(), 0);
      Log.i(TAG, "ACC::onAccessibilityEvent: nodeInfo=" + nodeInfo); // + " root= " + rootNode);
      if (nodeInfo == null) {
        nodeInfo = rootNode;
      }

      if (nodeInfo == null) {
        return;
      }

      List<AccessibilityNodeInfo> list =
          nodeInfo.findAccessibilityNodeInfosByViewId("com.android.settings:id/left_button");
      for (AccessibilityNodeInfo node : list) {
        Log.i(TAG, "ACC::onAccessibilityEvent: left_button " + node);
        node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
      }

      list = nodeInfo.findAccessibilityNodeInfosByViewId("android:id/button1");
      for (AccessibilityNodeInfo node : list) {
        Log.i(TAG, "ACC::onAccessibilityEvent: button1 " + node);
        node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
      }
    }
  }

  public static void exploreNodeHierarchy(final AccessibilityNodeInfo nodeInfo, final int depth) {

    // Super important check! AccessibilityNodes can get invalidated at ANY time.
    if (nodeInfo == null) return;

    // Log the nodeINfo to string, with some tabs for visible parent/child relationships.
    Log.d(TAG, new String(new char[depth]).replace("\0", "\t") + nodeInfo.toString());

    if (nodeInfo.getViewIdResourceName() == "the view id you have") {
      // Do work here.
    }

    for (int i = 0; i < nodeInfo.getChildCount(); ++i) {
      // logNodeHierarchy(nodeInfo.getChild(i), depth + 1);
    }
  }

  @Override
  public void onServiceConnected() {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      info.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;

    } else {
      info.eventTypes =
          AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED
              | AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED;
    }
    /* info.eventTypes =
        AccessibilityEvent.TYPE_VIEW_CLICKED
            | AccessibilityEvent.TYPE_VIEW_FOCUSED
            | AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED;
    // info.flags = AccessibilityServiceInfo.FLAG_REPORT_VIEW_ID; */
    info.feedbackType = AccessibilityServiceInfo.FEEDBACK_ALL_MASK;
    info.notificationTimeout = 100;

    this.setServiceInfo(info);
    Log.i(TAG, "Service on!");
  }

  @Override
  public void onInterrupt() {
    // TODO Auto-generated method stub

  }
}
