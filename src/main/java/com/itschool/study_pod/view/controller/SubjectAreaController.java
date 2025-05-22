package com.itschool.study_pod.view.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/subject-area")
public class SubjectAreaController {
    @GetMapping("/management")
    public String getListPage() {
        return "subjectarea/subject-area-list";
    }
}
