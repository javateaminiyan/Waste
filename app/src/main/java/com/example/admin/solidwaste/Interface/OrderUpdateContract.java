package com.example.admin.solidwaste.Interface;

/**
 * Created by Murugan on 04-10-2018.
 */

public interface OrderUpdateContract {

    interface view{

        void successMsg(String message);
        void failureMsg(String message);

    }

    interface model{

    }

    interface presenter{
      void updateOrder(String orderStatus, String orderid, String deliverDate);

    }
}
