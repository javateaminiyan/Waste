package com.example.admin.solidwaste.Interface;

import java.util.List;

public interface MainActivityContract {


    interface View {
     //   void showData(List<CryptoData> data);

        void showError(String message);

        void showComplete();

        void showProgress();

        void hideProgress();
    }

    interface Presenter {
        void loadData();
    }
}
