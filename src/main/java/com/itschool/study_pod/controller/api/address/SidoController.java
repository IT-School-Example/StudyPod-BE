package com.itschool.study_pod.controller.api.address;

import com.itschool.study_pod.controller.base.CrudController;
import com.itschool.study_pod.dto.request.AdminRequest;
import com.itschool.study_pod.dto.request.address.SidoRequest;
import com.itschool.study_pod.dto.response.AdminResponse;
import com.itschool.study_pod.dto.response.address.SidoResponse;
import com.itschool.study_pod.entity.Admin;
import com.itschool.study_pod.entity.address.Sido;
import com.itschool.study_pod.service.AdminService;
import com.itschool.study_pod.service.address.SidoService;
import com.itschool.study_pod.service.base.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("sido")
public class SidoController extends CrudController<SidoRequest, SidoResponse, Sido> {

    private final SidoService sidoService;

    @Override
    protected CrudService<SidoRequest, SidoResponse, Sido> getBaseService() {
        return sidoService;
    }
}
