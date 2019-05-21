package barrage.demo.methodLog;

import java.util.Set;

public interface AnnotationScannedMeta {

    Set<Class<?>> scannedCandidates(String pkgName);

}
