//package com.itschool.study_pod.repository;
//
//import com.itschool.study_pod.StudyPodApplicationTests;
//import com.itschool.study_pod.entity.InterestedSubject;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//class InterestedSubjectRepositoryTest extends StudyPodApplicationTests {
//    @Autowired
//    private InterestedSubjectRepostory interestedSubjectRepostory;
//
//    @BeforeEach // 테스트 실행 전 실행하는 메서드
//    public void BeforeCleanUp() {
//        System.out.println(this.getClass().getName() + " 테이블 전부 delete 시작");
//        interestedSubjectRepostory.deleteAll();
//        System.out.println(this.getClass().getName() + " 테이블 전부 delete 완료");
//    }
//
//    @AfterEach // 테스트 실행 후 실핼하는 메서드
//    public void AfterCleanUp() {
//        System.out.println(this.getClass().getName() + " 테이블 전부 delete 시작");
//        interestedSubjectRepostory.deleteAll();
//        System.out.println(this.getClass().getName() + " 테이블 전부 delete 완료");
//    }

//    // region CRUD
//    @Test
//    void create() {
//        InterestedSubject entity = InterestedSubject.builder()
//                .name("abc")
//                .build();
//
//        InterestedSubject newEntity = interestedSubjectRepostory.save(entity);
//
//        System.out.println("entity의 id값 : " + entity.getId()); // entity도 마찬가지로 영속상태로 등록
//        System.out.println("newEntity의 id값 : " + newEntity.getId()); // 영속성 컨텍스트에서 관리
//        System.out.println(entity == newEntity); // 결과값 true, 영속성 컨텍스트가 같은 엔티티는 같은 주소값을 반환
//
//        assertThat(entity.getName()).isEqualTo(newEntity.getName());
//    }
//
//    @Test
//    void read() {
//        String name = "test";
//
//        InterestedSubject entity = InterestedSubject.builder()
//                .name(name)
//                .build();
//
//        InterestedSubject newEntity = interestedSubjectRepostory.save(entity);
//
//        InterestedSubject findEntity = InterestedSubjectRepostory.findById(newEntity.getId())
//                .orElseThrow(() -> new RuntimeException());
//
//        System.out.println(newEntity == findEntity);
//
//        assertThat(entity.getId()).isEqulTo(findEntity.getId());
//        assertThat(entity.getName()).isEqualTo(findEntity.getName)
//    }

//}