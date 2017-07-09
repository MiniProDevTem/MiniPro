import com.minipro.util.HttpsClient;
import org.junit.Test;

/**
 * Created by chuckulu on 2017/7/9.
 */
public class TestHttpsClient {
    String token_acess = "6626EA4D059440FAF595C205226726BE";
    String url = String.format("https://graph.qq.com/oauth2.0/me?access_token=%s",token_acess);

    @Test
    public void testHttpsClient() {
        try {
            HttpsClient client = new HttpsClient();
           assert client.get(url).contains("openid");
        } catch (Exception ex) {
            System.out.println("exception happen");
        }
    }
}
