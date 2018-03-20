package com.codecool.shitwish.model;


import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "User.findByOrder",
                query = "SELECT u FROM user u WHERE u.modelType.order = :order"),
        @NamedQuery(name = "User.findById",
                query = "SELECT u FROM user u WHERE u.modelType.id = :id"),
        @NamedQuery(name = "User.findByEmail",
                query = "SELECT u FROM user u WHERE u.modelType.email = :email"),
        @NamedQuery(name = "User.findByRating",
                query = "SELECT u FROM u WHERE u.modelType.averageRating = :averageRating")
})
@Table(name = "user")
public class User {

    @Id
    private Long id;

    @Column(name = "email")
    private String email;

    private String password;
    private Order order;

    @Column(name = "averageRating")
    private Long averageRating;


    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Long getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Long averageRating) {
        this.averageRating = averageRating;
    }
}
