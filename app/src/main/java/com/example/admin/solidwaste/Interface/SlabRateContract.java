package com.example.admin.solidwaste.Interface;


import com.example.admin.solidwaste.pojo.SlabRatePojo.Products;

public interface SlabRateContract {

    interface view{

        void ViewSlabRate(Products slabBeanList);
    }

    interface model{

    }

    interface presenter{
        void getSlabRate(String userid);

    }
}
