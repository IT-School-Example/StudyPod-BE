package com.itschool.study_pod.global.base.crud;

public interface Convertible <Req, Res> {
    void update(Req request);

    Res response();
}
