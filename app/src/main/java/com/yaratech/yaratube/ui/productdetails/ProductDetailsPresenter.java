package com.yaratech.yaratube.ui.productdetails;

import com.yaratech.yaratube.data.model.Comment;
import com.yaratech.yaratube.data.model.ProductDetail;
import com.yaratech.yaratube.data.source.ApiResultCallback;
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
    public void fetchProductDetails(final int id) {
        productDetailsViewListener.showProductDetailsProgrssBar();
        repository.getProductDetails(id, new ApiResultCallback<ProductDetail>() {
            @Override
            public void onSuccess(ProductDetail result) {
                productDetailsViewListener.showProductDetail(result);
                productDetailsViewListener.hideProductDetailsProgrssBar();
                fetchCommentList(id);
            }

            @Override
            public void onFail(String err) {
                productDetailsViewListener.showErrorMessage(err);
            }
        });
    }

    @Override
    public boolean checkIfUserIsLogedIn() {
        return repository.checkIfUserIsLogedIn();
    }

    public void fetchCommentList(int id) {
        productDetailsViewListener.showCommentProgrssBar();
        repository.getCommentList(id, new ApiResultCallback<List<Comment>>() {
            @Override
            public void onSuccess(List<Comment> result) {
                productDetailsViewListener.hideCommentProgrssBar();
                productDetailsViewListener.showCommentList(result);
            }

            @Override
            public void onFail(String err) {
                productDetailsViewListener.hideCommentProgrssBar();
                productDetailsViewListener.showErrorMessage(err);
            }
        });
    }
}
