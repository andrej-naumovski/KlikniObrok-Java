package mk.klikniobrok.models;

/**
 * Created by andrejnaumovski on 12/8/16.
 */
public enum ResponseStatus {
    SUCCESS("success"), FAILURE("failure");

    private String status;
    ResponseStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
