package com.github.dmstocking.putitonthelist.grocery_list.items.add;

import android.support.annotation.NonNull;

import com.github.dmstocking.putitonthelist.Color;
import com.github.dmstocking.putitonthelist.Icon;
import com.github.dmstocking.putitonthelist.PerController;
import com.github.dmstocking.putitonthelist.grocery_list.GroceryListRepository;
import com.github.dmstocking.putitonthelist.grocery_list.sort.CategoryRepository;
import com.github.dmstocking.putitonthelist.uitl.IconUtils;
import com.github.dmstocking.putitonthelist.uitl.Log;
import com.google.auto.value.AutoValue;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

@PerController
public class AddGroceryListItemPresenter implements AddGroceryListItemContract.Presenter {

    public static final String TAG = "AddGroceryListItemPresenter";

    @NonNull private final AddGroceryListItemContract.View view;
    @NonNull private final AddGroceryListItemArgs args;
    @NonNull private final CategoryRepository categoryRepository;
    @NonNull private final GroceryListRepository repository;
    @NonNull private final IconUtils iconUtils;
    @NonNull private final Log log;

    private final Subject<String> nameSubject = BehaviorSubject.createDefault("");
    private String name;
    private final Subject<CategoryDocument> categorySubject = BehaviorSubject.createDefault(new CategoryDocument("other"));
    private CategoryDocument category;

    @Inject
    public AddGroceryListItemPresenter(@NonNull AddGroceryListItemContract.View view,
                                       @NonNull AddGroceryListItemArgs args,
                                       @NonNull CategoryRepository categoryRepository,
                                       @NonNull GroceryListRepository repository,
                                       @NonNull IconUtils iconUtils,
                                       @NonNull Log log) {
        this.view = view;
        this.args = args;
        this.categoryRepository = categoryRepository;
        this.iconUtils = iconUtils;
        this.repository = repository;
        this.log = log;
    }

    @Override
    public void onDoneClicked() {
        repository.create(args.id(),
                          category.getCategory(),
                          name)
                .subscribe(() -> {
                    view.finish();
                }, throwable -> {
                    log.e(TAG, "Failure while adding item", throwable);
                });
    }

    @Override
    public void onNameChanged(@NonNull String name) {
        this.name = name;
        nameSubject.onNext(name);
    }

    @Override
    public void onCategoryChanged(@NonNull CategoryDocument category) {
        this.category = category;
        categorySubject.onNext(category);
    }

    @Override
    public Observable<ViewModel> model() {
        return Observable.combineLatest(
                nameSubject,
                categorySubject,
                categoryRepository.fetchAllCategories(args.id()).toObservable(),
                Input::create)
                .map(input -> {
                    Icon icon = Icon.OTHER;
                    Color color = Color.BLACK;
                    List<ListItemViewModel> listViewModels = new ArrayList<>();
                    for (CategoryDocument doc : input.categoryDocuments()) {
                        boolean selected = doc.getCategory().equals(input.category().getCategory());
                        Color backgroundColor = Color.WHITE;
                        Color textColor = doc.getColor();
                        if (selected) {
                            icon = doc.getIcon();
                            color = doc.getColor();
                            backgroundColor = doc.getColor();
                            textColor = Color.WHITE;
                        }
                        listViewModels.add(ListItemViewModel.create(
                                doc.getId(),
                                iconUtils.iconToUri(doc.getIcon()),
                                doc.getCategory(),
                                textColor,
                                backgroundColor,
                                doc));
                    }
                    boolean doneEnabled = input.name().length() > 0;
                    return ViewModel.create(doneEnabled, iconUtils.iconToUri(icon), color, listViewModels);
                });

    }

    @AutoValue
    static abstract class Input {

        public static Input create(String name,
                                   CategoryDocument category,
                                   List<CategoryDocument> categoryDocuments) {
            return new AutoValue_AddGroceryListItemPresenter_Input(name, category, categoryDocuments);
        }

        @NonNull public abstract String name();
        @NonNull public abstract CategoryDocument category();
        @NonNull public abstract List<CategoryDocument> categoryDocuments();
    }
}
