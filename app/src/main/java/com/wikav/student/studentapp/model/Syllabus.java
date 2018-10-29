package com.wikav.student.studentapp.model;

/**
 * Created by wikav-pc on 9/1/2018.
 */

public class Syllabus {

    private String subject;
    private String syllabusComplete;
    private String Chapter;

    public String getChapter() {
        return Chapter;
    }

    public void setChapter(String chapter) {
        Chapter = chapter;
    }



    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSyllabusComplete() {
        return syllabusComplete;
    }

    public void setSyllabusComplete(String syllabusComplete) {
        this.syllabusComplete = syllabusComplete;
    }
}
