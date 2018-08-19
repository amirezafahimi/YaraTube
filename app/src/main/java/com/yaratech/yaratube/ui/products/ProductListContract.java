package com.yaratech.yaratube.ui.products;

import com.yaratech.yaratube.data.model.Product;

import java.util.List;

public interface ProductListContract {
    interface View{

        void showListProducts(List<Product> products);

        void showErrorMessage(String err);

        void showProgrssBar();

        void hideProgrssBar();

    }

    interface Presenter{
        void fetchProducts(int id);
    }
}
