package com.example.admin.solidwaste.Interface;





import com.example.admin.solidwaste.pojo.UserProductPojo.UserProductResponseResponse;

import java.util.List;

public interface IShowDialogContract {

//    void showDialog(int position);

    void showDialogd(int position, List<UserProductResponseResponse> userProductResponseResponses);
    void showDeleteDialog(int position, String title);
    void calIntent(String mobileno);

}
