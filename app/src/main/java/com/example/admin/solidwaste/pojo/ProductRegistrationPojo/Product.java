package com.example.admin.solidwaste.pojo.ProductRegistrationPojo;


import com.example.admin.solidwaste.Interface.NewProductContract;

public class Product implements NewProductContract.model {

    String productname;
    String type;
    String color;
    String grade;
    String approximateval;
    String unit;
    String sugession;
    String addimage;
    String description;

    public Product(String productname, String type, String color, String grade, String approximateval, String unit, String sugession, String addimage, String description) {
        this.productname = productname;
        this.type = type;
        this.color = color;
        this.grade = grade;
        this.approximateval = approximateval;
        this.unit = unit;
        this.sugession = sugession;
        this.addimage = addimage;
        this.description = description;
    }

    public Product(String productname, String type, String color, String grade, String approximateval, String unit, String addimage) {
        this.productname = productname;
        this.type = type;
        this.color = color;
        this.grade = grade;
        this.approximateval = approximateval;
        this.unit = unit;
        this.addimage = addimage;

    }

    @Override
    public String productname() {
        return productname;
    }

    @Override
    public String type() {
        return type;
    }

    @Override
    public String color() {
        return color;
    }

    @Override
    public String grade() {
        return grade;
    }

    @Override
    public String approximateval() {
        return approximateval;
    }

    @Override
    public String unit() {
        return unit;
    }

    @Override
    public String sugession() {
        return sugession;
    }

    @Override
    public String addimage() {
        return addimage;
    }

    @Override
    public String description() {
        return description;
    }




    public int validateFields(){

        if(productname().isEmpty()){
            return 1;
        }else if(type().isEmpty()){
            return 2;
        }else if(color().isEmpty()){
            return 3;
        }else if(grade().isEmpty()){
            return 4;
        }else if(approximateval().isEmpty()){
            return 5;
        }else if(unit().isEmpty()){
            return 6;
        }

        return 0;
    }
}
