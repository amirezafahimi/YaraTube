package com.yaratech.yaratube.ui.productdetails;

import com.yaratech.yaratube.data.model.Comment;
import com.yaratech.yaratube.data.model.ProductDetail;

import java.util.List;

public interface ProductDetailsContract {

    interface View{


        void showProductDetail(ProductDetail productDetail);

        void showCommentList(List<Comment> comments);

        void showErrorMessage(String err);

        void showProgrssBar();

        void hideProgrssBar();
    }
    interface Presenter{
        void fetchProductDetails(int id);
    }

}
