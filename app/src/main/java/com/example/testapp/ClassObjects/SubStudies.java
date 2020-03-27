package com.example.testapp.ClassObjects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SubStudies implements Comparable{

    private int SubStudyId;
    private String SubStudy;
    private int StudyId;

    public SubStudies() {
    }

    public SubStudies(int subStudyId, String subStudy, int studyId) {
        SubStudyId = subStudyId;
        SubStudy = subStudy;
        StudyId = studyId;
    }

    public int getSubStudyId() {
        return SubStudyId;
    }

    public void setSubStudyId(int subStudyId) {
        SubStudyId = subStudyId;
    }

    public String getSubStudy() {
        return SubStudy;
    }

    public void setSubStudy(String subStudy) {
        SubStudy = subStudy;
    }

    public int getStudyId() {
        return StudyId;
    }

    public void setStudyId(int studyId) {
        StudyId = studyId;
    }
    
    //implements Comparable
    @Override
    public int compareTo(Object st) {
        return this.SubStudyId-((SubStudies)st).getSubStudyId();
    }

    public static void SortA_Z(ArrayList<SubStudies> AL, Boolean isReverse) {
        if (isReverse) {
            Collections.sort(AL, new Comparator<SubStudies>() {
                @Override
                public int compare(SubStudies o1, SubStudies o2) {
                    return o1.getSubStudy().toLowerCase().compareTo(o2.getSubStudy().toLowerCase());
                }
            });
        }else {
            Collections.sort(AL, new Comparator<SubStudies>() {
                @Override
                public int compare(SubStudies o1, SubStudies o2) {
                    return o2.getSubStudy().toLowerCase().compareTo(o1.getSubStudy().toLowerCase());
                }
            });
        }
    }
}
