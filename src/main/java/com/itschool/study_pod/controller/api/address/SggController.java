package com.itschool.study_pod.controller.api.address;

import com.itschool.study_pod.controller.base.CrudController;
import com.itschool.study_pod.dto.request.address.SggRequest;
import com.itschool.study_pod.dto.request.address.SidoRequest;
import com.itschool.study_pod.dto.response.address.SggResponse;
import com.itschool.study_pod.dto.response.address.SidoResponse;
import com.itschool.study_pod.entity.address.Sgg;
import com.itschool.study_pod.entity.address.Sido;
import com.itschool.study_pod.service.address.SggService;
import com.itschool.study_pod.service.address.SidoService;
import com.itschool.study_pod.service.base.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("sgg")
public class SggController extends CrudController<SggRequest, SggResponse, Sgg> {

    private final SggService sggService;

    @Override
    protected CrudService<SggRequest, SggResponse, Sgg> getBaseService() {
        return sggService;
    }
}
