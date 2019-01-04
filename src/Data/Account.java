package Data;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Account implements ComparableData {
    private final String firstName;
    private final String lastName;
    private ID id;
    private final int age;
    private final int socialSecurityNumber;
    private final  String gender;
    private Path picture ;

    public Account(String firstName, String lastName, String gender, int age, int socialSecurityNumber){
        if(Stream.of("male", "Male", "female", "Female").noneMatch(gender::equals))
            throw new IllegalArgumentException("gender must be male or female");
        if(age <= 0)
            throw new IllegalArgumentException("age must be > 0");
        if(socialSecurityNumber <= 0)
            throw new IllegalArgumentException("socialSecurityNumber must be > 0");
        if(firstName == null)
            throw new IllegalArgumentException("firstName can't be a null value");
        if(lastName == null)
            throw new IllegalArgumentException("lastName can't be a null value");
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.age = age;
        this.id = new ID(firstName,lastName);
        this.socialSecurityNumber = socialSecurityNumber;
        setDefaultPic();
    }

    public String getID() {
        return id.getID();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public int getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public String getGender() {
        return gender;
    }

    public BigDecimal getCode(){
        return id.getCode();
    }

    public Path getPicture() {
        return picture;
    }

    public void setPicture(Path picture) throws IllegalAccessException {
        if(Files.exists(picture))
            this.picture = picture;
        else
            throw new IllegalAccessException("path is not exist");
    }

    private void setDefaultPic(){
        if(gender.equals("male") || gender.equals("Male")){
            picture = Paths.get("Male.jpg");
        }
        else
            picture = Paths.get("Female.jpg");
    }

    @Override
    public BigDecimal getComparableValue(){
        return id.getDecimalID();
    }

    @Override
    public String toString(){
        return String.format("%nFirstName: %s  LastName: %s%nID: %s%nAge: %d%nSocialSecurityNumber: %d%nGender: %s%n",
                getFirstName(),getLastName(),getID(),getAge(),getSocialSecurityNumber(),getGender());
    }
}
