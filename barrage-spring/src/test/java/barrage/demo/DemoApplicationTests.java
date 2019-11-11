package barrage.demo;

import barrage.demo.config.MethodLogConfig;
import barrage.demo.methodlog.OriginalPeople;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {


    @Test
    public void methodLogTest() {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(MethodLogConfig.class);
        OriginalPeople originalPeople = (OriginalPeople) annotationConfigApplicationContext.getBean(OriginalPeople.class.getName());
        originalPeople.saySomething("Hello World");
    }

}
