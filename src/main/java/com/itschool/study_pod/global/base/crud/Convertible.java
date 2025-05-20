package com.itschool.study_pod.global.base.crud;

public interface Convertible <UpdateReq, Res> {
    void update(UpdateReq request);

    Res response();
}
