package com.yaratech.yaratube.ui.productdetails;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.yaratech.yaratube.R;
import com.yaratech.yaratube.data.model.Comment;
import com.yaratech.yaratube.data.model.Product;
import com.yaratech.yaratube.data.model.ProductDetail;
import com.yaratech.yaratube.data.source.Repository;
import com.yaratech.yaratube.ui.login.LoginDialogContainer;
import com.yaratech.yaratube.ui.player.PlayerActivity;
import com.yaratech.yaratube.ui.productdetails.commentdialog.CommentDialogFragment;

import java.util.List;

import static android.widget.GridLayout.VERTICAL;
import static com.yaratech.yaratube.ui.player.PlayerActivity.PLAYER_ACTIVITY_KEY;

public class ProductDetailsFragment extends Fragment implements ProductDetailsContract.View {

    ProductDetailsPresenter productDetailsPresenter;
    Product product;
    ProductDetail productDetail;
    List<Comment> comments;
    ImageButton play;
    ImageView imageView;
    TextView title;
    TextView description;
    Button commentButton;
    ProgressBar commentProgressBar;
    ProgressBar productDetailsProgressBar;
    ProductDetailsRecyclerViewAdpter adpter;
    RecyclerView productDetailsRecyclerView;


    public ProductDetailsFragment() {
        // Required empty public constructor
    }

    public static ProductDetailsFragment newInstance(Product product) {
        ProductDetailsFragment fragment = new ProductDetailsFragment();
        Bundle args1 = new Bundle();
        args1.putParcelable("product", product);
        fragment.setArguments(args1);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        product = getArguments().getParcelable("product");
        postponeEnterTransition();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(R.transition.simple_fragment_transition));
        }
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
        commentProgressBar = view.findViewById(R.id.loading_commens);
        productDetailsProgressBar = view.findViewById(R.id.loading_product_details);
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
        /*ThreeBounce threeBounce = new ThreeBounce();
        threeBounce.setColor(R.color.colorAccent);
        productDetailsProgressBar.setIndeterminateDrawable(threeBounce);
        commentProgressBar.setIndeterminateDrawable(threeBounce);*/
        hideProductDetailsProgrssBar();
        hideCommentProgrssBar();
        bindViewProudctDetails();
    }

    @Override
    public void onResume() {
        super.onResume();

        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (productDetailsPresenter.checkIfUserIsLogedIn()) {
                    FragmentManager fragmentManager = getFragmentManager();
                    CommentDialogFragment.newInstance(product.getId()).show(fragmentManager, "comment dialog");
                } else {
                    LoginDialogContainer loginDialogContainer = LoginDialogContainer.newInstance();
                    loginDialogContainer.setCancelable(false);
                    loginDialogContainer.show(getChildFragmentManager(), "login dialog");
                }
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (productDetailsPresenter.checkIfUserIsLogedIn()) {
                    Intent intent = new Intent(getActivity(), PlayerActivity.class);
                    intent.putExtra(PLAYER_ACTIVITY_KEY, productDetail.getFiles().get(0).getFile());
                    startActivity(intent);
                } else {
                    LoginDialogContainer loginDialogContainer = LoginDialogContainer.newInstance();
                    loginDialogContainer.setCancelable(false);
                    loginDialogContainer.show(getChildFragmentManager(), "login dialog");
                }
            }
        });
    }

    //----------------------------------------------------------------------------------------------

    private void bindViewProudctDetails() {

        title.setText(product.getName());


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageView.setTransitionName("transition" + product.getId());
        }

        startPostponedEnterTransition();
        Glide.with(getContext()).load(product.getFeatureAvatar().getXxxdpi())
                .apply(new RequestOptions().dontAnimate())
                .apply(RequestOptions.centerCropTransform())
                /*.listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        startPostponedEnterTransition();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        startPostponedEnterTransition();
                        return false;
                    }
                })*/
                .into(imageView);
        productDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration itemDecor = new
                DividerItemDecoration(productDetailsRecyclerView.getContext(), VERTICAL);
        productDetailsRecyclerView.addItemDecoration(itemDecor);
        adpter = new ProductDetailsRecyclerViewAdpter(getContext());
        productDetailsRecyclerView.setAdapter(adpter);

        productDetailsPresenter = new
                ProductDetailsPresenter(this, new Repository());
        productDetailsPresenter.fetchProductDetails(product.getId());
    }

    //----------------------------------------------------------------------------------------------

    @Override
    public void showProductDetail(ProductDetail productDetail) {
        this.productDetail = productDetail;
        description.setText(productDetail.getDescription());
    }

    @Override
    public void showCommentList(List<Comment> comments) {
        this.comments = comments;
        adpter.setData(comments);

    }

    @Override
    public void showErrorMessage(String err) {
        Toast.makeText(getContext(), err, Toast.LENGTH_LONG);
    }

    @Override
    public void showCommentProgrssBar() {
        commentProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideCommentProgrssBar() {
        commentProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showProductDetailsProgrssBar() {
        productDetailsProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProductDetailsProgrssBar() {
        productDetailsProgressBar.setVisibility(View.GONE);
    }


    //----------------------------------------------------------------------------------------------

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
