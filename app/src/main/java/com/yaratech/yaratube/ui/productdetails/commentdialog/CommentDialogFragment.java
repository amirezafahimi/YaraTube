package com.yaratech.yaratube.ui.productdetails.commentdialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.yaratech.yaratube.R;
import com.yaratech.yaratube.data.source.Repository;

public class CommentDialogFragment extends DialogFragment implements CommentContract.View{


    int productId;
    CommentPresenter commentPresenter;
    Button send;
    RatingBar ratingBar;
    EditText commentText;

    // TODO: Rename and change types and number of parameters
    public static CommentDialogFragment newInstance(int productId) {
        CommentDialogFragment fragment = new CommentDialogFragment();
        Bundle args = new Bundle();
        args.putInt("productId", productId);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productId = getArguments().getInt("productId");
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container
            , Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_comment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        send = view.findViewById(R.id.send_comment);
        ratingBar = view.findViewById(R.id.comment_rating_bar);
        commentText = view.findViewById(R.id.comment_edit_text);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        commentPresenter = new CommentPresenter(this, new Repository());
    }

    @Override
    public void onResume() {
        super.onResume();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commentPresenter.sendComment(productId, commentPresenter.getUserToken(),
                        ratingBar.getRating(), commentText.getText().toString());
            }
        });
    }

    @Override
    public void dissmissDialog(String  msg) {
        dismiss();
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showErrorMessege(String err) {
        Toast.makeText(getContext(), err, Toast.LENGTH_LONG).show();
    }
}