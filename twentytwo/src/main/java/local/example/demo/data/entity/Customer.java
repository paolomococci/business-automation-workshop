package local.example.demo.data.entity;

import java.time.LocalDate;
import javax.persistence.Entity;
import local.example.demo.data.AbstractEntity;

@Entity
public class Customer extends AbstractEntity {

    private String name;
    private String surname;
    private String email;
    private String phone;
    private LocalDate birthday;
    private String occupation;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public LocalDate getBirthday() {
        return birthday;
    }
    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
    public String getOccupation() {
        return occupation;
    }
    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }
}
