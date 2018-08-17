package com.yaratech.yaratube.ui.product_list;

import com.yaratech.yaratube.data.model.Product;

import java.util.List;

public interface ProductListContract {
    interface view{

        void showListProducts(List<Product> categories);

        void showErrorMessage();

        void showProgrssBar();

        void hideProgrssBar();

    }

    interface presenter{
        public void fetchProducts(int id);
    }
}
