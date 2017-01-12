package mk.klikniobrok.models;

import javax.persistence.*;

/**
 * Created by andrejnaumovski on 12/8/16.
 */

@Entity
@Table(name = "customer")
public class Customer extends User {
    @Column(name = "email")
    private String email;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "image_url")
    private String imageUrl;

    public Customer() {
        super();
    }

    public Customer(String username,
                    String password,
                    java.util.Date dateCreated,
                    java.util.Date lastUsed,
                    Role role,
                    String email,
                    String firstName,
                    String lastName,
                    String imageUrl
    ) {
        super(username, password, dateCreated, lastUsed, role);
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.imageUrl = imageUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
