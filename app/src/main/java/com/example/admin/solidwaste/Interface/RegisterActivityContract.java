package com.example.admin.solidwaste.Interface;

import android.support.design.widget.TabLayout;

import com.example.admin.solidwaste.adapter.TabAdapter;

public interface RegisterActivityContract {

    interface RegisterPresenter {
        void highLightCurrentTab(int position, TabLayout tabLayout, TabAdapter adapter);
    }

    interface RegisterView {


//   // button events
//   void onCommonGreetingButtonClicked();
//   void onLobbyGreetingButtonClicked();

        // greeting text actions
        void displayGreeting(String greeting);

//   // greeting error actions
//   void displayGreetingError(Throwable throwable);
    }

}
