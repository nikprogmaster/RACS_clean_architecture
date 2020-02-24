
package com.example.racs.model.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UsersEntityData {


    public class User {

        @SerializedName("u_id")
        @Expose
        private Integer uId;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("first_name")
        @Expose
        private String firstName;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("patronymic")
        @Expose
        private String patronymic;
        @SerializedName("card_id")
        @Expose
        private String cardId;
        @SerializedName("role")
        @Expose
        private String role;
        @SerializedName("is_superuser")
        @Expose
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

    }


    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("next")
    @Expose
    private Object next;
    @SerializedName("previous")
    @Expose
    private Object previous;
    @SerializedName("results")
    @Expose
    private ArrayList<User> allusers = new ArrayList<>();

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Object getNext() {
        return next;
    }

    public void setNext(Object next) {
        this.next = next;
    }

    public Object getPrevious() {
        return previous;
    }

    public void setPrevious(Object previous) {
        this.previous = previous;
    }

    public ArrayList<User> getUsers() {
        return allusers;
    }

    public void setResults(ArrayList<User> results) {
        this.allusers = results;
    }

    public Integer searchByCard(String cardId) {
        for (User i : allusers) {
            if (i.getCardId().equals(cardId))
                return i.getUId();
        }
        return 0;
    }

    public User searchById(Integer id, List<User> users) {
        User user = null;
        for (User i : users) {
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
        UsersEntityData that = (UsersEntityData) o;
        return Objects.equals(count, that.count) &&
                Objects.equals(next, that.next) &&
                Objects.equals(previous, that.previous) &&
                Objects.equals(allusers, that.allusers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(count, next, previous, allusers);
    }

    @Override
    public String toString() {
        return "UsersEntityData{" +
                "count=" + count +
                ", next=" + next +
                ", previous=" + previous +
                ", allusers=" + allusers +
                '}';
    }
}
