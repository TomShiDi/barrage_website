package barrage.demo.methodLog;

import java.util.HashSet;
import java.util.Set;

public class AnnotationScannedFullAchieve implements AnnotationScannedMeta {

    private Set<Class<?>> candidateClass;

    private void getClass(String pkgName) {
        if (this.candidateClass == null) {
            this.candidateClass = new HashSet<>();
        }
        ClassScanUtil.getClass(pkgName, this.candidateClass);
    }

    @Override
    public Set<Class<?>> scannedCandidates(String pkgName) {
        getClass(pkgName);
        return this.candidateClass;
    }
}
