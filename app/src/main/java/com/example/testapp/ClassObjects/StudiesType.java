package com.example.testapp.ClassObjects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class StudiesType implements Comparable {
    private int StudyTypeID;
    private String StudyType;

    public StudiesType() {
    }

    public StudiesType(int studyTypeID, String studyType) {
        StudyTypeID = studyTypeID;
        StudyType = studyType;
    }

    public void setStudyTypeID(int studyTypeID) {
        StudyTypeID = studyTypeID;
    }

    public void setStudyType(String studyType) {
        StudyType = studyType;
    }

    public int getStudyTypeID() {
        return StudyTypeID;
    }

    public String getStudyType() {
        return StudyType;
    }


    @Override
    public int compareTo(Object st) {
        return this.StudyTypeID-((StudiesType)st).getStudyTypeID();
    }

    public static void SortA_Z(ArrayList<StudiesType> AL,Boolean isReverse) {
        if (isReverse) {
            Collections.sort(AL, new Comparator<StudiesType>() {
                @Override
                public int compare(StudiesType o1, StudiesType o2) {
                    return o1.getStudyType().toLowerCase().compareTo(o2.getStudyType().toLowerCase());
                }
            });
        }else {
            Collections.sort(AL, new Comparator<StudiesType>() {
                @Override
                public int compare(StudiesType o1, StudiesType o2) {
                    return o2.getStudyType().toLowerCase().compareTo(o1.getStudyType().toLowerCase());
                }
            });
        }
    }

}
