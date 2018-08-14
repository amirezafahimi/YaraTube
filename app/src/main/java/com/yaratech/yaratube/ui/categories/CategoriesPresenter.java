package com.yaratech.yaratube.ui.categories;

import com.yaratech.yaratube.data.model.Category;
import com.yaratech.yaratube.data.source.GetResultInterface;
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
        repository.getCategories(new GetResultInterface<List<Category>>() {
            @Override
            public void onSuccess(List<Category> categories) {
                categoriesViewListener.hideProgrssBar();
                categoriesViewListener.showListCategories(categories);
            }

            @Override
            public void onFail() {
                categoriesViewListener.showErrorMessage();

            }
        });

    }
}
