package com.itschool.study_pod.service.address;

import com.itschool.study_pod.dto.Header;
import com.itschool.study_pod.dto.response.address.SidoResponse;
import com.itschool.study_pod.repository.address.SidoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SidoService {

    private final SidoRepository sidoRepository;

    public final Header<SidoResponse> read(String id) {
        return Header.OK(
                sidoRepository.findById(id)
                        .orElseThrow(()-> new EntityNotFoundException("해당 id " + id + "에 해당하는 객체가 없습니다."))
                        .response());
    }
}
