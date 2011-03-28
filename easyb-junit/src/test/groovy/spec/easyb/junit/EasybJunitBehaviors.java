package spec.easyb.junit;

import org.easyb.junit.EasybSuite;
import org.junit.Ignore;

import java.io.File;

@Ignore
public class EasybJunitBehaviors extends EasybSuite {

    @Override
    protected File baseDir() {
        return new File("src/test/resources");
    }

    @Override
    protected boolean generateReports() {
        return false;
    }

    protected File withReports() {
        return new File("reports");
    }
}
