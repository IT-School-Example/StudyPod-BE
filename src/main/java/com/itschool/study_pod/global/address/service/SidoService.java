package com.itschool.study_pod.global.address.service;

import com.itschool.study_pod.global.base.dto.Header;
import com.itschool.study_pod.global.address.dto.response.SidoResponse;
import com.itschool.study_pod.global.address.repository.SidoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SidoService {

    private final SidoRepository sidoRepository;

    public Header<SidoResponse> read(String id) {
        return Header.OK(
                sidoRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("해당 id " + id + "에 해당하는 객체가 없습니다."))
                        .response());
    }

    public Header<List<SidoResponse>> readAll() {
        List<SidoResponse> responses = sidoRepository.findAll()
                .stream()
                .map(entity -> entity.response())
                .toList();
        return Header.OK(responses);
    }
}
