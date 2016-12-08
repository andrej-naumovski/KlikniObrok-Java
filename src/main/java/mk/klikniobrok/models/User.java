package mk.klikniobrok.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by andrejnaumovski on 12/8/16.
 */

@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
    @Id
    private String username;
    private String password;
    private int enabled;
    @Column(name = "date_created", insertable = false, updatable = false)
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date dateCreated;
    @Column(name = "last_used")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date lastUsed;
    @Enumerated
    private Role role;

    public User() {

    }

    public User(
            String username,
            String password,
            int enabled,
            java.util.Date dateCreated,
            java.util.Date lastUsed,
            Role role
    ) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.dateCreated = dateCreated;
        this.lastUsed = lastUsed;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    public int isEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    @Column(name = "date_created")
    public java.util.Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Column(name = "last_used")
    public java.util.Date getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(Timestamp lastUsed) {
        this.lastUsed = lastUsed;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
