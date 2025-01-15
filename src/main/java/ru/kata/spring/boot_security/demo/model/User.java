package ru.kata.spring.boot_security.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;


@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty(message = "Username не должно быть пустым")
    @Size(min = 2, max = 15, message = "Username должно быть длиной от 2 до 15 символов")
    @Column(name = "username")
    private String username;

    @NotBlank(message = "Password не должно быть пустым")
    @Size(min = 3, message = "Password должен быть не менее 6 символов")
    @Column(name = "password")
    private String password;

    @NotBlank(message = "Email не должно быть пустым")
    @Email(message = "Email должен быть действительным")
    @Column(name = "email")
    private String email;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    public User() {}

    public User(String username, String email, String password, List<Role> roles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public Long getId() {
        return id;}

    public void setId(Long id) {
        this.id = id;}

    public String getUsername() {
        return username;}

    public void setUsername (String username) {
        this.username = username;}

    public String getPassword() {
        return password;}

    public void setPassword(String password) {
        this.password = password;}

    public String getEmail() {
        return email;}

    public void setEmail(String email) {
        this.email = email;}

    public void setRoles(List<Role> roles) {
        this.roles = roles;}

    public List<Role> getRoles() {
        return roles;}

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                '}';
    }
}
