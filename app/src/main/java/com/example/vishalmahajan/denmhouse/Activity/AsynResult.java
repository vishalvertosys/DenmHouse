package com.example.vishalmahajan.denmhouse.Activity;

/**
 * Created by VS-2 on 8/16/2017.
 */

public interface AsynResult<TData> {

     void success(TData data);
     void failure(String error);
     void passItemValue(int value);

}
