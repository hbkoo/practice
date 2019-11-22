package com.example.acer.wuli.comments.bean;



import com.example.acer.wuli.model.Comment;
import com.example.acer.wuli.model.User;

import java.io.Serializable;

/**
 * 评论列表使用的Item数据
 * 包括user和comment
 */
public class CommentItem implements Serializable {
    private User user;//用户
    private Comment comment;//用户关联的评论

    public CommentItem(User user, Comment comment) {
        this.user = user;
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
}
