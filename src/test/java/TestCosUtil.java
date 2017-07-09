import com.google.gson.Gson;
import com.minipro.util.CosUtil;
import org.json.JSONObject;
import org.junit.Test;

import static com.minipro.util.CosUtil.upload;

/**
 * Created by chuckulu on 2017/7/9.
 */
public class TestCosUtil {
    String accessUrl = "http://image-1253746863.file.myqcloud.com/test.txt";

    @Test
    public void testCosUtil() {
        CosUtil.del(accessUrl);
        String result = upload("image","src\\test\\resources\\test.txt");
        Gson gson=new Gson();
        JSONObject obj =new JSONObject(result);
        JSONObject data = obj.getJSONObject("data");
        assert data.getString("access_url").equals(accessUrl);
        CosUtil.del(accessUrl);
    }
}
