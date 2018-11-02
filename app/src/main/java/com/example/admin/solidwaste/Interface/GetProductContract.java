package com.example.admin.solidwaste.Interface;



import com.example.admin.solidwaste.pojo.RegisteredProduct.GetProductResponseResponseResponse;

import java.util.List;

public interface GetProductContract {

    interface view{
      void loadData(List<GetProductResponseResponseResponse> getProductResponseResponseResult);
        void deleteSuccess(String message);
        void deleteFailure(String message);
    }



    interface model{

    }

    interface presenter{
        void getProduct(String userid);
        void deleteProduct(String userid, int product_id);

    }
}
