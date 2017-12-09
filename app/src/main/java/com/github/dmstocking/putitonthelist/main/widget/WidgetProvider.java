package com.github.dmstocking.putitonthelist.main.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.github.dmstocking.putitonthelist.R;
import com.github.dmstocking.putitonthelist.grocery_list.GroceryListActivity;

public class WidgetProvider extends AppWidgetProvider {

  public static String EXTRA_WORD = "com.commonsware.android.appwidget.lorem.WORD";

  @Override
  public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    for (int i=0; i < appWidgetIds.length; i++) {
      Intent svcIntent = new Intent(context, WidgetService.class);
      svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
      svcIntent.setData(Uri.parse(svcIntent.toUri(Intent.URI_INTENT_SCHEME)));

      RemoteViews widget = new RemoteViews(context.getPackageName(), R.layout.main_widget);

      widget.setRemoteAdapter(appWidgetIds[i], svcIntent);

      Intent clickIntent = new Intent(context, GroceryListActivity.class);

      PendingIntent clickPI = PendingIntent.getActivity(context,
                                                        0,
                                                        clickIntent,
                                                        PendingIntent.FLAG_UPDATE_CURRENT);

      widget.setPendingIntentTemplate(R.id.words, clickPI);

      appWidgetManager.updateAppWidget(appWidgetIds[i], widget);
    }

    super.onUpdate(context, appWidgetManager, appWidgetIds);
  }
}
