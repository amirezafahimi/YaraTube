package com.yaratech.yaratube.ui.mainpage.more;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yaratech.yaratube.MainPresenter;
import com.yaratech.yaratube.R;
import com.yaratech.yaratube.data.source.Repository;
import com.yaratech.yaratube.ui.login.LoginDialogContainer;
import com.yaratech.yaratube.ui.profile.ProfileFragment;
import com.yaratech.yaratube.util.Util;


public class MoreFragment extends Fragment implements MoreContract.View{

    private OnMoreFragmentInteractionListener mListener;
    LoginDialogContainer loginDialogContainer;
    public static String MORE_FRAGMENT_TAG = "more_fragment";

    MorePresenter morePresenter;
    ListView listView;
    String[] strings = {"پروفایل", "درباره ما", "تماس با ما"};

    public MoreFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static MoreFragment newInstance() {
        MoreFragment fragment = new MoreFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        morePresenter = new MorePresenter(this, new Repository());
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_more, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = view.findViewById(R.id.list_item);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return strings.length;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @SuppressLint("ViewHolder")
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.category_item, parent, false);
                TextView textView = view.findViewById(R.id.category_title);
                textView.setText(strings[position]);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (position) {
                            case 0:
                                if (morePresenter.checkIfUserIsLogedIn()) {
                                    mListener.goToProfile();
                                } else {
                                    loginDialogContainer = LoginDialogContainer.newInstance();
                                    /*loginDialogContainer.setCancelable(true);*/
                                    loginDialogContainer.show(getFragmentManager(), "login dialog");
                                }
                                break;
                            case 1:
                                break;
                            case 2:
                                break;
                        }
                    }
                });
                return view;
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMoreFragmentInteractionListener) {
            mListener = (OnMoreFragmentInteractionListener) context;
        }
    }

    public interface OnMoreFragmentInteractionListener {
        void goToProfile();
    }
}
