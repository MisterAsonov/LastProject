package com.example.lastproject;

public class PersonInGroup {
    String Id_of_student;

    public PersonInGroup(String id_of_student) {
        Id_of_student = id_of_student;
    }

    public PersonInGroup(){

    }

    @Override
    public String toString() {
        return "PersonInGroup{" +
                "Id_of_student='" + Id_of_student + '\'' +
                '}';
    }

    public String getId_of_student() {
        return Id_of_student;
    }

    public void setId_of_student(String id_of_student) {
        Id_of_student = id_of_student;
    }
}
