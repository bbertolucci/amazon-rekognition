package test.demo.rekognition;

import com.demo.rekognition.DetectFace;
import org.junit.Test;

public class DetectFaceTest {

    @Test
    public void TestFace(){

        DetectFace.main(new String[]{"/tmp/santa2.jpg"});
    }
}
