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
    @Builder
    @EqualsAndHashCode
    static class BoundClaim{
        List<String> roles;
        String iam;
        List<String> service;
    }

    Gson gson = new GsonBuilder().create();

    @Test
    void testHuman(){
        Request expectedRequest = Request.builder().roleFor("Human")
                .boundClaim(BoundClaim.builder().roles(List.of("role1", "role2", "role3")).build()).build();
        String actualSerialisedString = gson.toJson(expectedRequest);
        String expectedRequestString = "{\"roleFor\":\"Human\",\"boundClaim\":{\"roles\":[\"role1\",\"role2\",\"role3\"]}}";
        assertThat(actualSerialisedString).isEqualTo(expectedRequestString);
        Request actualSerialisedRequest = gson.fromJson(expectedRequestString, Request.class);
        assertThat(actualSerialisedRequest).isEqualTo(expectedRequest);
    }

    @Test
    void testLinux(){
        Request request = Request.builder().roleFor("linux")
                .boundClaim(BoundClaim.builder().iam("unixmachine")
                        .service(List.of("service-1", "service-2")).build()).build();
        String actual = gson.toJson(request);
        String expected = "{\"roleFor\":\"linux\",\"boundClaim\":{\"iam\":\"unixmachine\",\"service\":[\"service-1\"," +
                "\"service-2\"]}}";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void testWindows(){
        Request request = Request.builder().roleFor("windows")
                .boundClaim(BoundClaim.builder().iam("windowsmachine")
                        .service(List.of("application-1")).build()).build();
        String actual = gson.toJson(request);
        String expected = "{\"roleFor\":\"windows\",\"boundClaim\":{\"iam\":\"windowsmachine\"," +
                "\"service\":[\"application-1\"]}}";
        assertThat(actual).isEqualTo(expected);
    }
}
