package entities;

public class Properties {

    private String age = "43";
    private String date_of_birth = "290671";
    private String active = "true";

    public String getAge() {
        return age;
    }

    public Properties setAge(String age) {
        this.age = age;
        return this;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public Properties setDob(String dob) {
        this.date_of_birth = dob;
        return this;
    }

    public String getActive() {
        return active;
    }

    public Properties setActive(String active) {
        this.active = active;
        return this;
    }

}
