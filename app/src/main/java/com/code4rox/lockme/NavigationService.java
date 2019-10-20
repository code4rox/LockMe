package com.code4rox.lockme;

import android.accessibilityservice.AccessibilityService;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Toast;

public class NavigationService extends AccessibilityService {

    public static NavigationService f8846a;

    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
    }

    public void onInterrupt() {
    }

    public void onServiceConnected() {
        super.onServiceConnected();
        f8846a = this;
    }
}
