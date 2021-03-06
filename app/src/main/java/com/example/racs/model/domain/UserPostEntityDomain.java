package com.example.racs.model.domain;

import java.util.Objects;

public class UserPostEntityDomain {


    private String email;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String cardId;
    private String role;


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

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPostEntityDomain that = (UserPostEntityDomain) o;
        return email.equals(that.email) &&
                firstName.equals(that.firstName) &&
                lastName.equals(that.lastName) &&
                Objects.equals(patronymic, that.patronymic) &&
                cardId.equals(that.cardId) &&
                role.equals(that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, firstName, lastName, patronymic, cardId, role);
    }

    @Override
    public String toString() {
        return "UserPostEntityDomain{" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", cardId='" + cardId + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

}
