
package com.example.admin.solidwaste.pojo.DirectionApiPojo;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Duration {

    @SerializedName("text")
    private String mText;
    @SerializedName("value")
    private Long mValue;

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public Long getValue() {
        return mValue;
    }

    public void setValue(Long value) {
        mValue = value;
    }

}
