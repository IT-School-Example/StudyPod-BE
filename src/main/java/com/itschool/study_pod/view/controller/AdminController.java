package com.itschool.study_pod.view.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/management")
    public String getListPage() {
        return "admin/admin-list";
    }

    @GetMapping("/form")
    public String getCreateForm(@RequestParam(name = "id", required = false) Long id) {
        return "admin/admin-form";
    }
}
