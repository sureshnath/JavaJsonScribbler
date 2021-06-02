import java.util.HashMap;
import java.util.List;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import org.junit.jupiter.api.Test;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleTest {
    @Builder
    @EqualsAndHashCode
    static class Request{
        String roleFor;
        BoundClaim boundClaim;
    }
    static class BoundClaim extends HashMap<String, Object> {
        static BoundClaim of(){
            return new BoundClaim();
        }
        BoundClaim putt(String key, Object value){
            super.put(key,value);
            return this;
        }
    }

    Gson gson = new GsonBuilder().create();

    @Test
    void testHuman(){
        Request expectedRequest = Request.builder().roleFor("Human")
                .boundClaim(BoundClaim.of().putt("roles", List.of("role1", "role2", "role3"))).build();
        String actualSerialisedString = gson.toJson(expectedRequest);
        String expectedRequestString =
                "{\"roleFor\":\"Human\",\"boundClaim\":{\"roles\":[\"role1\",\"role2\",\"role3\"]}}";
        assertThat(actualSerialisedString).isEqualTo(expectedRequestString);
        Request actualDeserialisedRequest = gson.fromJson(expectedRequestString, Request.class);
        assertThat(actualDeserialisedRequest).isEqualTo(expectedRequest);
        assertThat(actualDeserialisedRequest.boundClaim.get("roles")).asList().contains("role1");
    }

    @Test
    void testLinux(){
        Request request = Request.builder().roleFor("linux")
                .boundClaim(BoundClaim.of().putt("iam","unixmachine")
                        .putt("service",List.of("service-1", "service-2"))).build();
        String actual = gson.toJson(request);
        String expected = "{\"roleFor\":\"linux\",\"boundClaim\":{\"iam\":\"unixmachine\",\"service\":[\"service-1\"," +
                "\"service-2\"]}}";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void testWindows(){
        Request request = Request.builder().roleFor("windows")
                .boundClaim(BoundClaim.of().putt("iam","windowsmachine")
                        .putt("service",List.of("application-1"))).build();
        String actual = gson.toJson(request);
        String expected = "{\"roleFor\":\"windows\",\"boundClaim\":{\"iam\":\"windowsmachine\"," +
                "\"service\":[\"application-1\"]}}";
        assertThat(actual).isEqualTo(expected);
    }
}
