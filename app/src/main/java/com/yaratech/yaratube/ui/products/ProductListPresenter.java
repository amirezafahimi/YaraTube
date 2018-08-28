package com.yaratech.yaratube.ui.products;

import com.yaratech.yaratube.data.model.Product;
import com.yaratech.yaratube.data.source.ApiResultCallback;
import com.yaratech.yaratube.data.source.Repository;

import java.util.List;

public class ProductListPresenter implements ProductListContract.Presenter {
    ProductListContract.View productsViewListener;
    Repository repository;
    public ProductListPresenter(ProductListContract.View productsViewListener, Repository repository) {
        this.productsViewListener = productsViewListener;
        this.repository = repository;
    }

    @Override
    public void fetchProducts(int id) {
        productsViewListener.showProgrssBar();
        repository.getProducts(id, new ApiResultCallback<List<Product>>() {
            @Override
            public void onSuccess(List<Product> result) {
                productsViewListener.hideProgrssBar();
                productsViewListener.showListProducts(result);
            }

            @Override
            public void onFail(String err) {
                productsViewListener.hideProgrssBar();
                productsViewListener.showErrorMessage(err);

            }
        });

    }
}
