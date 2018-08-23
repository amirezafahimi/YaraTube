package com.yaratech.yaratube.ui.MainPage.categories;

import com.yaratech.yaratube.data.model.Category;

import java.util.List;

public interface CategoriesContract {
    interface view{

        void showListCategories(List<Category> categories);

        void showErrorMessage(String err);

        void showProgrssBar();

        void hideProgrssBar();

    }

    interface presenter{
        public void fetchCategories();
    }
}
