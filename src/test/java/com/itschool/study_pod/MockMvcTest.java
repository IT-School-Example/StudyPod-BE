package com.itschool.study_pod;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

// Mock Object(모의 객체) : 실제의 모듈을 흉내내는 가짜 모듈을 작성해서 테스트의 효용성을 높이는 객체
// Mockito : 테스트에 사용할 가짜 객체인 Mock 객체를 만들고, 관리하고, 검증할 수 있는 프레임워크
// @SpringBootTest + @AutoConfigureMockMvc
@AutoConfigureMockMvc
public abstract class MockMvcTest extends StudyPodApplicationTests {

    @Autowired
    private WebApplicationContext context;

    // HTTP 요청을 모의(Mock)하여 @Controller 또는 @RestController의 동작을 검증
    protected MockMvc mockMvc; // Spring MVC 컨트롤러를 테스트하는데 사용, 실제 서버를 띄우지 않고 가짜 요청을 만들어서 컨트롤러 호출

    @BeforeEach // 테스트 실행 전 실행하는 메서드
    public void mockMvcSetup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build(); // mockMvc에서 요청 상태 값을 가지고 있어서 초기화
    }
}
