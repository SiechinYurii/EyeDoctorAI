package sample;

import org.springframework.context.annotation.*;

@Configuration
@ComponentScan("sample")
public class AppConfig {

    @Bean
    @Scope("singleton")
    public ImageFaceDetector imageFaceDetectorBean(){
        return new ImageFaceDetector();
    }

}
