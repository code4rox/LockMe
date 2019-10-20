package com.code4rox.lockme

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import java.util.logging.Handler
import android.widget.Toast
import android.os.Looper
import android.util.Log
import android.app.PendingIntent
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context.DEVICE_POLICY_SERVICE
import android.provider.Settings
import android.text.TextUtils
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.startActivity


/**
 * Implementation of App Widget functionality.
 */
class LockWidgets : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {

        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }


    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        if (MyOnClick.equals(intent?.getAction())) {

                lockMe(context)
        }
    }


    private fun lockMe(context: Context?) {

        val clickTime = System.currentTimeMillis()
        if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
            lastClickTime = 0
          m7673a(8, context!!)
        }
        lastClickTime = clickTime

    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    private fun getPendingSelfIntent(context: Context, action: String): PendingIntent {
        val intent = Intent(context, javaClass)
        intent.action = action
        return PendingIntent.getBroadcast(context, 0, intent, 0)
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    private fun m7673a(i: Int, context: Context) {
        if (!m7702j(context)) {
            m7699i(context)
        } else if (NavigationService.f8846a == null) {
            m7699i(context)
        } else {
            try {
                NavigationService.f8846a.performGlobalAction(i)
            } catch (unused: Exception) {
            }

        }
    }

    private fun m7699i(context: Context) {
        val intent = Intent("android.settings.ACCESSIBILITY_SETTINGS")
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    private fun m7702j(context: Context): Boolean {
        var i: Int
        val sb = StringBuilder()
        sb.append(context.getPackageName())
        sb.append("/com.code4rox.lockme.NavigationService")
        val sb2 = sb.toString()
        try {
            i = Settings.Secure.getInt(context.getContentResolver(), "accessibility_enabled")
        } catch (e: Settings.SettingNotFoundException) {
            e.printStackTrace()
            i = 0
        }
        val simpleStringSplitter = TextUtils.SimpleStringSplitter(':')
        if (i == 1) {
            val string = Settings.Secure.getString(context.getContentResolver(), "enabled_accessibility_services")
            if (string != null) {
                simpleStringSplitter.setString(string)
                while (simpleStringSplitter.hasNext()) {
                    if (simpleStringSplitter.next().equals(sb2, ignoreCase = true)) {
                        return true
                    }
                }
            }
        }
        return false
    }



    internal fun updateAppWidget(
        context: Context, appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {

        val widgetText = context.getString(R.string.appwidget_text)
        // Construct the RemoteViews object
        val views = RemoteViews(context.packageName, R.layout.lock_widgets)
        views.setOnClickPendingIntent(R.id.appwidget_text, getPendingSelfIntent(context, MyOnClick))
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }

    companion object {
        private val MyOnClick = "myOnClickTag"
        private val DOUBLE_CLICK_TIME_DELTA: Long = 300//milliseconds
        var lastClickTime: Long = 0

    }



}

