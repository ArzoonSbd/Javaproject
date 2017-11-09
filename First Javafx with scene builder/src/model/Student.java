package model;

import java.io.FileInputStream;

public class Student {

    private int id;
    private String name, address, phone_no, roll_no, gender,program, username, password,dob;
    private FileInputStream fis;

   

    public FileInputStream getFis() {
        return fis;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setFis(FileInputStream fis) {
        this.fis = fis;
    }

    public Student() {

    }

    public void Student(int id, String name, String address, String phone_no, String roll_no, String gender, String Program,String username, String password,String dob) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone_no = phone_no;
        this.roll_no = roll_no;
        this.gender = gender;
        this.program= Program;
        this.username = username;
        this.password = password;
        this.dob=dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getRoll_no() {
        return roll_no;
    }

    public void setRoll_no(String roll_no) {
        this.roll_no = roll_no;
    }
     public String getProgram() {
        return program;
    }

    public void setProgram(String Program) {
        this.program = Program;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

   

}
