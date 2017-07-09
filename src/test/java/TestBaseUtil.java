import com.minipro.util.Base64Util;
import org.junit.Test;

/**
 * Created by chuckulu on 2017/7/9.
 */
public class TestBaseUtil {
    String message = "test base64";

    @Test
    public void TestBaseUtil() {

        String encodedMessage = Base64Util.encodeString(message);
        assert Base64Util.decodeString(encodedMessage).equals(message);
    }
}
