package test.demo.rekognition;

import com.demo.rekognition.CompareFaces;
import org.junit.Test;

public class CompareFacesTest {

    @Test
    public void TestText(){

        CompareFaces.main(new String[]{"/tmp/santa6-1.jpg","/tmp/santa6-2.jpg"});
    }
}
