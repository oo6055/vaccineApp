package com.example.vaccineapp;

public class Student {
    private String privateName;
    private String secondName;
    private String grade; // format grade:class
    boolean canBeVaccinated;
    Vaccine v1;
    Vaccine v2;

    // deafultiv
    public Student(String privateName,String secondName,String grade,String clases, boolean canBeVaccinated,Vaccine v1,Vaccine v2)
    {
        this.privateName = privateName;
        this.secondName = secondName;
        this.grade = grade +":"+clases;
        this.canBeVaccinated = canBeVaccinated;
        this.v1 = v1;
        this.v2 = v2;
    }
    public Vaccine getVaccine1()
    {
        return this.v1;
    }
    public Vaccine getVaccine2()
    {
        return this.v2;
    }
}
