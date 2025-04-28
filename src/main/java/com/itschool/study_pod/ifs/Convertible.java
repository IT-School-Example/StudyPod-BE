package com.itschool.study_pod.ifs;

public interface Convertible <Req, Res> {
    void update(Req request);

    Res response();
}
