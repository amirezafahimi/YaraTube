package com.yaratech.yaratube.ui.mainpage.more;

import com.yaratech.yaratube.data.source.Repository;

public class MorePresenter implements MoreContract.Presenter {

    MoreContract.View moreFragmentViewListener;
    Repository repository;

    public MorePresenter(MoreContract.View moreFragmentViewListener, Repository repository) {
        this.moreFragmentViewListener = moreFragmentViewListener;
        this.repository = repository;
    }

    @Override
    public boolean checkIfUserIsLogedIn() {
        return repository.checkIfUserIsLogedIn();
    }
}
