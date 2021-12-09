package test.demo.rekognition;

import com.demo.rekognition.DetectLabel;
import org.junit.Test;

public class DetectLabelTest {

    @Test
    public void TestLabel(){

        DetectLabel.main(new String[]{"/tmp/santa3.jpg"});
    }
}
