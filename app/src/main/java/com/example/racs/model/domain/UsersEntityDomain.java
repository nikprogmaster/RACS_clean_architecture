package com.example.racs.model.domain;


import java.util.List;
import java.util.Objects;

public class UsersEntityDomain {

    private Integer uId;
    private String email;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String cardId;
    private String role;
    private Boolean isSuperuser;

    public Integer getUId() {
        return uId;
    }

    public void setUId(Integer uId) {
        this.uId = uId;
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

    public Boolean getIsSuperuser() {
        return isSuperuser;
    }

    public void setIsSuperuser(Boolean isSuperuser) {
        this.isSuperuser = isSuperuser;
    }



    public UsersEntityDomain searchById(Integer id, List<UsersEntityDomain> users) {
        UsersEntityDomain user = null;
        for (UsersEntityDomain i : users) {
            if (i.getUId() == id) {
                user = i;
            }
        }
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersEntityDomain that = (UsersEntityDomain) o;
        return Objects.equals(uId, that.uId) &&
                Objects.equals(email, that.email) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(patronymic, that.patronymic) &&
                Objects.equals(cardId, that.cardId) &&
                Objects.equals(role, that.role) &&
                Objects.equals(isSuperuser, that.isSuperuser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uId, email, firstName, lastName, patronymic, cardId, role, isSuperuser);
    }

    @Override
    public String toString() {
        return "UsersEntityDomain{" +
                "uId=" + uId +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", cardId='" + cardId + '\'' +
                ", role='" + role + '\'' +
                ", isSuperuser=" + isSuperuser +
                '}';
    }
}
