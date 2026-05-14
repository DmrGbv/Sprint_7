import io.restassured.RestAssured;

import org.junit.BeforeClass;

import static data.EndpointAndUriData.*;

public class BaseAPITest  {
    @BeforeClass
    public static void setup(){
        RestAssured.baseURI = BASE_URI;
    }
}
