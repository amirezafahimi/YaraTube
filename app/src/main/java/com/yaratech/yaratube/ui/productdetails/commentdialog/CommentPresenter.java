package com.yaratech.yaratube.ui.productdetails.commentdialog;

import android.util.Log;

import com.yaratech.yaratube.data.model.CommentResponse;
import com.yaratech.yaratube.data.source.ApiResultCallback;
import com.yaratech.yaratube.data.source.Repository;

public class CommentPresenter implements CommentContract.Presenter {

    CommentContract.View commentDialogViewListener;
    Repository repository;

    CommentPresenter(CommentContract.View commentDialogViewListener, Repository repository) {
        this.commentDialogViewListener = commentDialogViewListener;
        this.repository = repository;
    }

    @Override
    public String getUserToken() {
        return repository.getUserToken();
    }

    @Override
    public void sendComment(int productId, String token, float score, String comment) {
        repository.sendComment(productId, token, score, comment, new ApiResultCallback<CommentResponse>() {
            @Override
            public void onSuccess(CommentResponse result) {
                commentDialogViewListener.dissmissDialog(result.getMessage());
            }

            @Override
            public void onFail(String err) {
                commentDialogViewListener.showErrorMessege(err);
            }
        });

    }
}
