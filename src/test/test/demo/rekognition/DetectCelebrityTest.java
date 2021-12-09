package test.demo.rekognition;

import com.demo.rekognition.DetectCelebrity;
import org.junit.Test;

public class DetectCelebrityTest {

    @Test
    public void TestText(){

        DetectCelebrity.main(new String[]{"/tmp/santa5.jpg"});
    }
}
