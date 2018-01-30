package entities;

public class Properties {

    private String age;
    private String date_of_birth;
    private String active;

    public String getAge() {
        return age;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public String getActive() {
        return active;
    }

    public static class Builder {
        private String age = "43";
        private String date_of_birth = "290671";
        private String active = "true";

        public Builder age(String age) {
            this.age = age;
            return this;
        }

        public Builder date_of_birth(String date_of_birth) {
            this.date_of_birth = date_of_birth;
            return this;
        }

        public Builder active(String active) {
            this.active = active;
            return this;
        }

        public Properties build() {
            return new Properties(this);
        }
    }

    private Properties(Builder builder){
        age = builder.age;
        date_of_birth = builder.date_of_birth;
        active = builder.active;
    }

}
