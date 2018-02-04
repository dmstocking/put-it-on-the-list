package com.github.dmstocking.putitonthelist.main.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.github.dmstocking.putitonthelist.R;
import com.github.dmstocking.putitonthelist.authentication.UserService;
import com.github.dmstocking.putitonthelist.main.GroceryListDocument;
import com.github.dmstocking.putitonthelist.main.GroceryListId;
import com.github.dmstocking.putitonthelist.main.ListViewModel;
import com.github.dmstocking.putitonthelist.main.MainRepository;
import com.github.dmstocking.putitonthelist.uitl.Log;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

@AutoFactory
class MainViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    public static final String TAG = "MainViewsFactory";

    @NonNull private final Context context;
    @NonNull private final Log log;
    @NonNull private final MainRepository mainRepository;
    @NonNull private final String packageName;
    @NonNull private final UserService userService;
    private final int appWidgetId;

    private List<ListViewModel> model;

    public MainViewsFactory(@Provided @NonNull Context context,
                            @Provided @NonNull Log log,
                            @Provided @NonNull MainRepository mainRepository,
                            @Provided @Named("packageName") @NonNull String packageName,
                            @Provided @NonNull UserService userService,
                            Intent intent) {
        this.context = context;
        this.log = log;
        this.mainRepository = mainRepository;
        this.packageName = packageName;
        this.userService = userService;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                                         AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        String userId = userService.getUserId()
                .filter(uid -> !uid.isEmpty())
                .firstOrError()
                .blockingGet();
        model = mainRepository.getModel(userId)
                .map(documents -> {
                    List<ListViewModel> list = new ArrayList<>();
                    for (GroceryListDocument document : documents) {
                        list.add(ListViewModel.create(
                                ListViewModel.Type.ITEM,
                                GroceryListId.create(document.getId()),
                                document.getName(),
                                document.getPurchased() + "/" + document.getTotal(),
                                false));
                    }
                    return list;
                })
                .firstOrError()
                .blockingGet();
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        return model.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        ListViewModel viewModel = model.get(position);
        RemoteViews remoteViews = new RemoteViews(packageName, R.layout.main_widget_item);
        remoteViews.setTextViewText(R.id.name, viewModel.headline());
        remoteViews.setTextViewText(R.id.items, viewModel.trailingCaption());
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
