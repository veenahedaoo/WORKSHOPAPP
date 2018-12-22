package com.tantransh.workshopapp.appdata;

import android.os.Build;
import android.text.Html;
import android.text.Spanned;

public class AppServices {


    public static Spanned getRequiredFormatString(String string){
        int version = Build.VERSION.SDK_INT;
        if(version >= Build.VERSION_CODES.N){
            return Html.fromHtml("<font color = 'red' weight = 'bold'>*</font>&nbsp;"+string,Html.FROM_HTML_MODE_COMPACT);

        }

        return Html.fromHtml("<font color = 'red' weight = 'bold'>*</font>&nbsp;"+string);
    }
}
