package com.itschool.study_pod.ifs;

public interface Convertible <UpdateReq, Res> {
    void update(UpdateReq request);

    Res response();
}
