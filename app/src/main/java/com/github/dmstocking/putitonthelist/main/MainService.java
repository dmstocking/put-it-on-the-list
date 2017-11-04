package com.github.dmstocking.putitonthelist.main;

import com.github.dmstocking.putitonthelist.authentication.UserService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;

@Singleton
public class MainService {

    @NonNull private final MainRepository mainRepository;
    @NonNull private final UserService userService;

    @Inject
    public MainService(MainRepository mainRepository,
                       UserService userService) {
        this.mainRepository = mainRepository;
        this.userService = userService;
    }

    public Completable create(@NonNull String name) {
        return mainRepository.create(userService.getUserId(), name);
    }

    public Flowable<MainViewModel> model() {
        return mainRepository.getModel(userService.getUserId())
                .map(items -> {
                    List<ListViewModel> listItems = new ArrayList<>();
                    for (GroceryListDocument doc : items) {
                        int purchased = 0;
                        for (GroceryListItem listItem : doc.getGroceryListItems()) {
                            if (listItem.isPurchased()) {
                                purchased++;
                            }
                        }
                        listItems.add(
                                ListViewModel.create(
                                        0,
                                        doc.getName(),
                                        purchased,
                                        doc.getGroceryListItems().size()));
                    }
                    return MainViewModel.create(listItems);
                });
    }
}
