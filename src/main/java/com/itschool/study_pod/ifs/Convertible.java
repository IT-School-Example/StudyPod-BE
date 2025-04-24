package com.itschool.study_pod.ifs;

public interface Convertible <ReqDto, ResDto> {
    void update(ReqDto requestEntity);

    ResDto response();
}
