package com.example.annation.view;


import com.example.annation.status.CommentEntity;

import java.util.List;

public interface CommentView extends BaseView {
    void onSuccess(List<CommentEntity> list);

}
