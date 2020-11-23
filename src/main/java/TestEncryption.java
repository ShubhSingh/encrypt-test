import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

public class TestEncryption extends AbstractJavaSamplerClient {

  private ObjectMapper mapper;

  @Override
  public SampleResult runTest(JavaSamplerContext context) {
    SampleResult result = new SampleResult();
    boolean success;
    // boolean success = true;
    result.sampleStart();
    // Write your test code here.

    Encrypt e = new Encrypt();
    success = e.testEncryptDecrypt();

    result.sampleEnd();
    result.setSuccessful(success);
    return result;
  }

}
