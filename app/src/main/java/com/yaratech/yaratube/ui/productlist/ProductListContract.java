package com.yaratech.yaratube.ui.productlist;

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
        void fetchProducts(int id, int offset);
    }
}
