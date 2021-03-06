package com.yaratech.yaratube.ui.mainpage.categories;

import com.yaratech.yaratube.data.model.Category;
import com.yaratech.yaratube.data.source.ApiResultCallback;
import com.yaratech.yaratube.data.source.Repository;

import java.util.List;

public class CategoriesPresenter implements CategoriesContract.presenter {

    CategoriesContract.view categoriesViewListener;
    Repository repository;

    public CategoriesPresenter(CategoriesContract.view categoriesViewListener, Repository repository) {
        this.categoriesViewListener = categoriesViewListener;
        this.repository = repository;
    }

    @Override
    public void fetchCategories() {

        categoriesViewListener.showProgrssBar();
        repository.getCategories(new ApiResultCallback<List<Category>>() {
            @Override
            public void onSuccess(List<Category> categories) {
                categoriesViewListener.hideProgrssBar();
                categoriesViewListener.showListCategories(categories);
            }

            @Override
            public void onFail(String err) {
                categoriesViewListener.hideProgrssBar();
                categoriesViewListener.showErrorMessage(err);

            }
        });

    }
}
