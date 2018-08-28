package com.yaratech.yaratube.ui.productdetails;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yaratech.yaratube.R;
import com.yaratech.yaratube.data.model.Comment;
import com.yaratech.yaratube.data.model.Product;
import com.yaratech.yaratube.data.model.ProductDetail;
import com.yaratech.yaratube.data.source.Repository;
import com.yaratech.yaratube.ui.login.LoginDialogContainer;
import com.yaratech.yaratube.ui.productdetails.commentdialog.CommentDialog;

import java.util.List;

import static android.widget.GridLayout.VERTICAL;

public class ProductDetailsFragment extends Fragment implements ProductDetailsContract.View {

    ProductDetailsPresenter productDetailsPresenter;
    Product product;
    boolean userIsLogined;
    ProductDetail productDetail;
    List<Comment> comments;
    ImageView play;
    ImageView imageView;
    TextView title;
    TextView description;
    Button commentButton;
    ProgressBar progressBar;
    ProductDetailsRecyclerViewAdpter adpter;
    RecyclerView productDetailsRecyclerView;


    public ProductDetailsFragment() {
        // Required empty public constructor
    }

    public static ProductDetailsFragment newInstance(Product product, boolean userIsLogined) {
        ProductDetailsFragment fragment = new ProductDetailsFragment();
        Bundle args1 = new Bundle();
        args1.putParcelable("product", product);
        args1.putBoolean("user is logined", userIsLogined);
        fragment.setArguments(args1);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        product = getArguments().getParcelable("product");
        userIsLogined = getArguments().getBoolean("user is logined");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = view.findViewById(R.id.loading_product_details);
        play = view.findViewById(R.id.play);
        imageView = view.findViewById(R.id.product_video);
        title = view.findViewById(R.id.product_name);
        description = view.findViewById(R.id.product_explain);
        commentButton = view.findViewById(R.id.comment_button);
        productDetailsRecyclerView = view.findViewById(R.id.product_comments);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        hideProgrssBar();
        bindViewProudctDetails();
        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                CommentDialog.newInstance().show(fragmentManager, "comment dialog");
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userIsLogined){

                }
                else{
                    LoginDialogContainer loginDialogContainer = LoginDialogContainer.newInstance();
                    loginDialogContainer.setCancelable(false);
                    loginDialogContainer.show(getChildFragmentManager(), "login dialog");
                }
            }
        });
    }

    //----------------------------------------------------------------------------------------------

    private void bindViewProudctDetails() {
        productDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration itemDecor = new
                DividerItemDecoration(productDetailsRecyclerView.getContext(), VERTICAL);
        productDetailsRecyclerView.addItemDecoration(itemDecor);
        adpter = new ProductDetailsRecyclerViewAdpter(getContext());
        productDetailsRecyclerView.setAdapter(adpter);
        title.setText(product.getName());
        Glide.with(getContext()).load(product.getFeatureAvatar().getXxxdpi()).into(imageView);

        showProgrssBar();
        productDetailsPresenter = new
                ProductDetailsPresenter(this, new Repository());
        productDetailsPresenter.fetchProductDetails(product.getId());
    }

    public void showProgrssBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgrssBar() {
        progressBar.setVisibility(View.GONE);
    }

    //----------------------------------------------------------------------------------------------

    @Override
    public void showProductDetail(ProductDetail productDetail) {
        this.productDetail = productDetail;
        description.setText(productDetail.getDescription());
    }

    @Override
    public void showCommentList(List<Comment> comments) {
        hideProgrssBar();
        this.comments = comments;
        adpter.setData(comments);

    }

    @Override
    public void showErrorMessage(String err) {
        hideProgrssBar();
        Toast.makeText(getContext(), err, Toast.LENGTH_LONG);
    }

    //----------------------------------------------------------------------------------------------

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
