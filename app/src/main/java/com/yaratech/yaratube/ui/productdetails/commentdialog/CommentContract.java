package com.yaratech.yaratube.ui.productdetails.commentdialog;

import com.yaratech.yaratube.data.model.CommentResponse;

public interface CommentContract {
    interface View {
        void dissmissDialog(String msg);
        void showErrorMessege(String err);
    }

    interface Presenter {
        String getUserToken();
        void sendComment(int productId, String token, float rate, String comment);
    }
}
