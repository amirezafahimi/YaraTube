package com.yaratech.yaratube.ui.Productdetails;

import com.yaratech.yaratube.data.model.Comment;
import com.yaratech.yaratube.data.model.ProductDetail;
import com.yaratech.yaratube.data.source.GetResultInterface;
import com.yaratech.yaratube.data.source.Repository;

import java.util.List;


public class ProductDetailsPresenter implements ProductDetailsContract.Presenter {

    ProductDetailsContract.View productDetailsViewListener;
    Repository repository;

    public ProductDetailsPresenter(ProductDetailsContract.View productDetailsViewListener, Repository repository) {
        this.productDetailsViewListener = productDetailsViewListener;
        this.repository = repository;
    }

    @Override
    public void fetchProductDetails(int id) {
        productDetailsViewListener.showProgrssBar();
        repository.getProductDetails(id, new GetResultInterface<ProductDetail>() {
            @Override
            public void onSuccess(ProductDetail result) {
                productDetailsViewListener.showProductDetail(result);
            }

            @Override
            public void onFail(String err) {
                productDetailsViewListener.hideProgrssBar();
                productDetailsViewListener.showErrorMessage(err);
            }
        });

        repository.getCommentList(id, new GetResultInterface<List<Comment>>() {
            @Override
            public void onSuccess(List<Comment> result) {
                productDetailsViewListener.hideProgrssBar();
                productDetailsViewListener.showCommentList(result);

            }

            @Override
            public void onFail(String err) {
                productDetailsViewListener.hideProgrssBar();
                productDetailsViewListener.showErrorMessage(err);

            }
        });

    }
}
