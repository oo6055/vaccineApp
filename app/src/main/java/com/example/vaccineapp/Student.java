package com.example.vaccineapp;

public class Student {
    private String privateName;
    private String secondName;
    private String grade; // format grade:class
    private boolean canBeVaccinated;
    private Vaccine v1;
    private Vaccine v2;

    // deafultiv
    public Student()
    {
        this.privateName = "";
        this.secondName = "";
        this.grade = "";
        this.canBeVaccinated = false;
        this.v1 = null;
        this.v2 = null;
    }
    public Student(String privateName,String secondName,String grade,String clases, boolean canBeVaccinated,Vaccine v1,Vaccine v2)
    {
        this.privateName = privateName;
        this.secondName = secondName;
        this.grade = grade +":"+clases;
        this.canBeVaccinated = canBeVaccinated;
        this.v1 = v1;
        this.v2 = v2;
    }

    // wow
    public String getFirstName()
    {
        return this.privateName;
    }
    public String getSecondName()
    {
        return this.secondName;
    }
    public String getGrade()
    {
        return this.grade;
    }
    public boolean getCanBeVaccinated()
    {
        return this.canBeVaccinated;
    }
    public Vaccine getVaccine1()
    {
        return this.v1;
    }
    public Vaccine getVaccine2()
    {
        return this.v2;
    }

    public void setVaccine1(Vaccine v1)
    {
        this.v1 = v1;
    }
    public void setVaccine2(Vaccine v2)
    {
        this.v2 = v2;
    }
    public void setCanBeVaccinated(boolean b)
    {
        this.canBeVaccinated = b;
    }
    public void setGrade(String g)
    {
        this.grade = g;
    }
    public void setFirstName(String s)
    {
        this.privateName = s;
    }
    public void setSecondName(String s)
    {
        this.secondName = s;
    }
}
