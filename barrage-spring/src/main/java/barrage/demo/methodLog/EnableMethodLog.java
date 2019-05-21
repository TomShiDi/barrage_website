package barrage.demo.methodLog;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({MethodLogResourceRegistry.class})
public @interface EnableMethodLog {

    String scannedPkgName();

}
