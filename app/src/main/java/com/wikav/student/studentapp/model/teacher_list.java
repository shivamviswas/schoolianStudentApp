package com.wikav.student.studentapp.model;

/**
 * Created by Aws on 11/03/2018.
 */

public class teacher_list {

    private String teacherName ;
    private String teacherId;
    private String subject;
    private String time ;
    private String image_url_teacher;

    public teacher_list() {
    }

    public String getTeacherName() {
        return teacherName;
    }

    public String getSubject() {
        return subject;
    }

    public String getTimeOf() {
        return time;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public String getImage_url_teacher() {
        return image_url_teacher;
    }


    public void setTeacherName(String name) {
        this.teacherName = name;
    }

    public void setSubject(String description) {
        subject = description;
    }
    public void setTimOf(String name) {
        this.time = name;
    }

    public void setTeacherId(String nb_episode) {
        this.teacherId = nb_episode;
    }

    public void setImage_url_teacher(String image_url) {
        this.image_url_teacher = image_url;
    }
}
