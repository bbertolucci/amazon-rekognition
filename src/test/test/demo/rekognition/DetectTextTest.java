package test.demo.rekognition;

import com.demo.rekognition.DetectText;
import org.junit.Test;

public class DetectTextTest{

    @Test
    public void TestText(){

        DetectText.main(new String[]{"/tmp/santa1.png"});
    }
}
