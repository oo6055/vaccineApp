package com.example.vaccineapp;

/**
 * The Students activity.
 *
 *  @author Ori Ofek <oriofek106@gmail.com> 17/04/2021
 *  @version 1.0
 *  @since 17/04/2021
 *  sort description:
 *  this is the activty the implement the exercise that my teacher gave and in this class there is a student
 */
public class Student {
    private String privateName;
    private String secondName;
    private String grade; // format grade:class
    private String classStud;
    private boolean canBeVaccinated;
    private Vaccine v1;
    private Vaccine v2;
    String classFormat;

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
        this.classFormat = String.valueOf(v1.getOccur()) + ":" + grade + ":" + clases;
        this.privateName = privateName;
        this.secondName = secondName;
        this.grade = grade;
        this.classStud = clases;
        this.canBeVaccinated = canBeVaccinated;
        this.v1 = v1;
        this.v2 = v2;
    }

    // wow
    public String getClassFormat()
    {
        return this.classFormat;
    }
    public String getClassStud()
    {
        return this.classStud;
    }
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

    public void setClassFormat(String s)
    {
        this.classFormat = s;
    }
    public void setClassStud(String classe) {
        this.classStud = classe;
        setClassFormat(this.grade + ":" + this.classStud);
    }
    public void setVaccine1(Vaccine v1)
    {
        if(canBeVaccinated)
        {
            this.v1 = v1;
        }
        else
        {
            this.v1 = new Vaccine("NOT TAKEN","NOT TAKEN");
        }
    }
    public void setVaccine2(Vaccine v2)
    {
        if(canBeVaccinated)
        {
            this.v2 = v2;
        }
        else
        {
            this.v2 = new Vaccine("NOT TAKEN","NOT TAKEN");
        }
    }
    public void setCanBeVaccinated(boolean b)
    {
        if(!b)
        {
            this.v1 = new Vaccine("NOT TAKEN","NOT TAKEN");
            this.v2 = new Vaccine("NOT TAKEN","NOT TAKEN");
        }

        this.canBeVaccinated = b;
    }
    public void setGrade(String g)
    {
        this.grade = g;
        setClassFormat(this.grade + ":" + this.classStud);
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
