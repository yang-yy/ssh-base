import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:applicationContext.xml",
        "classpath:applicationContext-database.xml",
        "classpath:applicationContext-jobs.xml",
        "classpath:applicationContext-models.xml",
})
public class Http {

    @Autowired
    private CloseableHttpClient httpClient;

    private String host;

    @Before
    public void init() throws Exception {
        // httpClient = HttpClients.createDefault();
    }

    @Test
    public void testGet() throws Exception {
    }

    @Test
    public void testPost() throws Exception {
        HttpPost httpPost = null;
        HttpResponse response = null;
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();

        nvps.add(new BasicNameValuePair("userName", "吴晓东"));
        nvps.add(new BasicNameValuePair("userPassword", "123456"));
        nvps.add(new BasicNameValuePair("userId", "10000"));
        httpPost = new HttpPost("http://test.cnfol.com:8080/test/index.shtml");

        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nvps, Consts.UTF_8);
        httpPost.setEntity(urlEncodedFormEntity);
        response = httpClient.execute(httpPost);
        if (response.getStatusLine().getStatusCode() == 200) {
            System.out.println(writeToString(response.getEntity().getContent()));
        } else {
            throw new Exception(response.getStatusLine().toString());
        }
    }

    @Test
    public void testPostWithFile() throws Exception {
        HttpPost httpPost = null;
        HttpResponse response = null;

        httpPost = new HttpPost("http://test.cnfol.com:8080/test/index.shtml");

        MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
        entityBuilder.addBinaryBody("userName", "吴晓东".getBytes(Consts.UTF_8));
        entityBuilder.addBinaryBody("uploadFileName", "消费明细.xlsx".getBytes(Consts.UTF_8));
        entityBuilder.addTextBody("userPassword", "123456");
        entityBuilder.addTextBody("userId", "10000");
        entityBuilder.addBinaryBody("upload", new File("C:\\Users\\Administrator\\Desktop\\消费明细.xlsx"));
        entityBuilder.setCharset(Consts.UTF_8);


        httpPost.setEntity(entityBuilder.build());
        response = httpClient.execute(httpPost);
        if (response.getStatusLine().getStatusCode() == 200) {
            System.out.println(writeToString(response.getEntity().getContent()));
        } else {
            throw new Exception(response.getStatusLine().toString());
        }
    }

    @Test
    public void testDelete() throws Exception {
        HttpDelete httpDelete = null;
        HttpResponse response = null;

        httpDelete = new HttpDelete("http://test.cnfol.com:8080/test/aa.txt");
        response = httpClient.execute(httpDelete);
        if (response.getStatusLine().getStatusCode() >= 200) {
            System.out.println(response.getStatusLine().getStatusCode());
        } else {
            throw new Exception(response.getStatusLine().toString());
        }
    }

    @Test
    public void testPut() throws Exception {
        HttpPut httpPut = null;
        HttpResponse response = null;

        String resourceUrl = "http://test.cnfol.com:8080/test/abcd.txt";
        String servletUrl = "http://localhost:8080/test/";

        httpPut = new HttpPut("http://localhost:8080/test/abcd.txt");
        httpPut.setEntity(new FileEntity(new File("C:\\Users\\Administrator\\Desktop\\aa.txt")));
        response = httpClient.execute(httpPut);
        if (response.getStatusLine().getStatusCode() >= 200) {
            System.out.println(response.getStatusLine().getStatusCode());
        } else {
            throw new Exception(response.getStatusLine().toString());
        }
    }

    public String writeToString(InputStream is) throws Exception {
        InputStreamReader isr = new InputStreamReader(is, Charset.forName("UTF-8"));
        BufferedReader br = new BufferedReader(isr);
        StringWriter sw = new StringWriter();
        String line = null;
        while ((line = br.readLine()) != null) {
            sw.write(line);
            sw.write("\n");
            sw.flush();
        }
        sw.flush();
        br.close();
        sw.close();
        return sw.toString();
    }

}
