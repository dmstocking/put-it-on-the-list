package com.github.dmstocking.putitonthelist.main;

import android.util.Pair;

import com.github.dmstocking.putitonthelist.authentication.UserService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.processors.ReplayProcessor;

@Singleton
public class MainService {

    @NonNull private final MainRepository mainRepository;
    @NonNull private final MainResources mainResources;
    @NonNull private final UserService userService;

    @NonNull private final ReplayProcessor<Set<GroceryListId>> markProcessor = ReplayProcessor.createWithSize(1);
    @NonNull private Set<GroceryListId> markedItems = new HashSet<>();

    @Inject
    public MainService(MainRepository mainRepository,
                       MainResources mainResources,
                       UserService userService) {
        this.mainRepository = mainRepository;
        this.mainResources = mainResources;
        this.userService = userService;
        markProcessor.onNext(new HashSet<>(markedItems));
    }

    public Completable create(@NonNull String name) {
        return mainRepository.create(userService.getUserId(), name);
    }

    public Flowable<MainViewModel> model() {
        return Flowable.combineLatest(
                mainRepository.getModel(userService.getUserId()),
                markProcessor,
                Pair::create
        )
                .map(pair -> {
                    List<GroceryListDocument> items = pair.first;
                    Set<GroceryListId> marked = pair.second;
                    List<ListViewModel> listItems = new ArrayList<>();
                    for (GroceryListDocument doc : items) {
                        int purchased = 0;
                        for (GroceryListItem listItem : doc.getGroceryListItems()) {
                            if (listItem.isPurchased()) {
                                purchased++;
                            }
                        }
                        GroceryListId id = GroceryListId.create(doc.getId());
                        listItems.add(
                                ListViewModel.create(
                                        id,
                                        doc.getName(),
                                        purchased + "/" + doc.getGroceryListItems().size(),
                                        marked.contains(id)));
                    }
                    boolean isSelecting = marked.size() > 0;
                    return MainViewModel.create(
                            mainResources.getActionTitle(marked.size()),
                            listItems,
                            isSelecting);
                });
    }

    public void mark(GroceryListId id) {
        if (markedItems.add(id)) {
            markProcessor.onNext(new HashSet<>(markedItems));
        }
    }

    public void unmark(GroceryListId id) {
        if (markedItems.remove(id)) {
            markProcessor.onNext(new HashSet<>(markedItems));
        }
    }

    public void delete() {
        Observable.fromIterable(markedItems)
                .flatMapCompletable(id -> mainRepository.delete(id.id()))
                .doOnComplete(this::cancel)
                .subscribe();
    }

    public void cancel() {
        markedItems.clear();
        markProcessor.onNext(new HashSet<>(markedItems));
    }
}
