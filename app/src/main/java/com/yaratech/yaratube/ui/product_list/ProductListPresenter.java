package com.yaratech.yaratube.ui.product_list;

import com.yaratech.yaratube.data.model.Product;
import com.yaratech.yaratube.data.source.GetResultInterface;
import com.yaratech.yaratube.data.source.Repository;

import java.util.List;

public class ProductListPresenter implements ProductListContract.presenter {
    ProductListContract.view productsViewListener;
    Repository repository;
    public ProductListPresenter(ProductListContract.view productsViewListener, Repository repository) {
        this.productsViewListener = productsViewListener;
        this.repository = repository;
    }

    @Override
    public void fetchProducts(int id) {
        productsViewListener.showProgrssBar();
        repository.getProducts(id, new GetResultInterface<List<Product>>() {
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
