package com.itschool.study_pod.view.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/study-group")
public class StudyGroupController {
    @GetMapping("/management")
    public String getListPage() {
        return "studygroup/study-group-list";
    }
}
