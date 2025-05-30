package com.itschool.study_pod.domain.statistics.service;

import com.itschool.study_pod.domain.statistics.dto.response.PopularKeywordResponse;
import com.itschool.study_pod.domain.studygroup.repository.StudyGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PopularKeywordService {

    private final StudyGroupRepository studyGroupRepository;

    public List<PopularKeywordResponse> getPopularKeywords() {
        return studyGroupRepository.findTopKeywords().stream()
                .map(obj -> PopularKeywordResponse.builder()
                        .keyword((String) obj[0])
                        .count((Long) obj[1])
                        .build())
                .collect(Collectors.toList());
    }
}
