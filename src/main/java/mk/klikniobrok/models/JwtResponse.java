package mk.klikniobrok.models;

/**
 * Created by andrejnaumovski on 12/8/16.
 */
public class JwtResponse {
    private String token;
    private ResponseStatus responseStatus;

    public JwtResponse() {

    }

    public JwtResponse(String token, ResponseStatus responseStatus) {
        this.token = token;
        this.responseStatus = responseStatus;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }
}
