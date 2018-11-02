package com.example.admin.solidwaste.Interface;

import java.io.File;
import java.util.ArrayList;

public interface NewProductContract {

    interface view {
        void success(String message);

        void failure(String message);

        void ShowProgress();

        void HideProgress();

        void pricesuggession(String merchantprice, String merchantname);
    }

    interface model {
        String productname();

        String type();

        String color();

        String grade();

        String approximateval();

        String unit();

        String sugession();

        String addimage();

        String description();

    }

    interface presenter {

        void onSubmit(File file, String userid, String name, String type, String color, String grade, String approximateval, String unit, String suggession, String addimage, String description);

        ArrayList<String> ServiceNameList();

        ArrayList<String> TypeList();

        ArrayList<String> GradeList();

        ArrayList<String> UnitList();

        void updateProduct(File file, String productName, String productcost, String userid, String productunit, String productid, String productdescrption, String type, String color, String grade);


        void priceSuggession(String price, String type);


    }

}
