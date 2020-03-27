package com.example.testapp.ClassObjects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Studies implements Comparable{

    private int StudyId;
    private String Study;

    public Studies() {
    }

    public Studies(int studyId, String study) {
        StudyId = studyId;
        Study = study;
    }

    public int getStudyId() {
        return StudyId;
    }

    public void setStudyId(int studyId) {
        StudyId = studyId;
    }

    public String getStudy() {
        return Study;
    }

    public void setStudy(String study) {
        Study = study;
    }

    //implements Comparable
    @Override
    public int compareTo(Object st) {
        return this.StudyId-((Studies)st).getStudyId();
    }

    public static void SortA_Z(ArrayList<Studies> AL, Boolean isReverse) {
        if (isReverse) {
            Collections.sort(AL, new Comparator<Studies>() {
                @Override
                public int compare(Studies o1, Studies o2) {
                    return o1.getStudy().toLowerCase().compareTo(o2.getStudy().toLowerCase());
                }
            });
        }else {
            Collections.sort(AL, new Comparator<Studies>() {
                @Override
                public int compare(Studies o1, Studies o2) {
                    return o2.getStudy().toLowerCase().compareTo(o1.getStudy().toLowerCase());
                }
            });
        }
    }


}
