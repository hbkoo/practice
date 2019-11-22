package com.example.acer.wuli.comments.helper;


import com.example.acer.wuli.lichenhao.activity.MainActivity;
import com.example.acer.wuli.model.User;

import org.litepal.LitePal;

public class CurrentUser {

    public static User cur;

    static {
        cur = LitePal.where("ID = ?", String.valueOf(MainActivity.user_ID))
                .findFirst(User.class);
    }

    public static User getCurrentUser() {
        return cur;
    }
}
