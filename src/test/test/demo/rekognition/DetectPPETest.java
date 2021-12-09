package test.demo.rekognition;

import com.demo.rekognition.DetectPPE;
import com.demo.rekognition.DetectText;
import org.junit.Test;

public class DetectPPETest {

    @Test
    public void TestPPE(){

        DetectPPE.main(new String[]{"/tmp/mask.jpg"});
    }
}
