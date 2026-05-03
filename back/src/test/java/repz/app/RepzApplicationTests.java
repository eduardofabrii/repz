package repz.app;

import io.minio.MinioClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
class RepzApplicationTests {

    @MockitoBean
    MinioClient minioClient;

    @Test
    void contextLoads() {
    }

}
