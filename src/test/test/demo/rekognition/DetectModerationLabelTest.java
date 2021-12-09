package test.demo.rekognition;

import com.demo.rekognition.DetectModerationLabel;
import org.junit.Test;

public class DetectModerationLabelTest {

    @Test
    public void TestLabel(){

        DetectModerationLabel.main(new String[]{"/tmp/santa4.jpg"});
    }
}
