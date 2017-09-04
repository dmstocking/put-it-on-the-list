package com.github.dmstocking.putitonthelist.main;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.reactivex.processors.ReplayProcessor;

@Singleton
public class MainRepository {

    private ArrayList<ListViewModel> model = new ArrayList<ListViewModel>() {{
        add(ListViewModel.create(1, "Kroger", 013, 37));
    }};

    @NonNull private final AtomicInteger id = new AtomicInteger(2);
    @NonNull private final ReplayProcessor<MainViewModel> processor = ReplayProcessor.createWithSize(1);

    @Inject
    public MainRepository() {
        notifyChange();
    }

    public void create(String name) {
        model.add(ListViewModel.create(id.getAndIncrement(), name, 0, 0));
        notifyChange();
    }

    public Flowable<MainViewModel> getModel() {
        return processor;
    }

    private void notifyChange() {
        processor.onNext(MainViewModel.create(model));
    }
}
