package com.yaratech.yaratube.ui.Productdetails;

import com.yaratech.yaratube.data.model.ProductDetail;

import java.util.List;

public interface ProductDetailsContract {

    interface View{


        void showListProducts(ProductDetail productDetail);

        void showErrorMessage(String err);

        void showProgrssBar();

        void hideProgrssBar();
    }
    interface Presenter{
        void fetchProductDetails(int id);
        void fetchCommentList(int id);
    }

}
