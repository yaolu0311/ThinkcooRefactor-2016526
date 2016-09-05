package com.thinkcoo.mobile.model.entity.RequestParam;

import com.thinkcoo.mobile.presentation.views.PageMachine;

import retrofit2.http.Query;

/**
 * Created by Administrator on 2016/8/6.
 */
public class SelectStudentParam implements RequestParam{

    private String eventId;
    private String school;
    private String classProfession;
    private String keyWord;
    private PageMachine pageMachine;

    private SelectStudentParam(Builder builder) {
        eventId = builder.eventId;
        school = builder.school;
        classProfession = builder.classProfession;
        pageMachine = builder.pageMachine;
        keyWord = builder.keyWord;
    }

    public static Builder newBuilder() {
        return new Builder();
    }



    public static final class Builder {
        private String eventId;
        private String school;
        private String classProfession;
        private String keyWord;
        private PageMachine pageMachine;

        private Builder() {
        }

        public Builder eventId(String val) {
            eventId = val;
            return this;
        }

        public Builder school(String val) {
            school = val;
            return this;
        }

        public Builder classProfession(String val) {
            classProfession = val;
            return this;
        }

        public Builder pageMachine(PageMachine val) {
            pageMachine = val;
            return this;
        }
        public Builder keyWord(String val) {
            keyWord = val;
            return this;
        }
        public SelectStudentParam build() {
            return new SelectStudentParam(this);
        }


    }


    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }
    public String getClassProfession() {
        return classProfession;
    }

    public void setClassProfession(String classProfession) {
        this.classProfession = classProfession;
    }


    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public PageMachine getPageMachine() {
        return pageMachine;
    }

    public void setPageMachine(PageMachine pageMachine) {
        this.pageMachine = pageMachine;
    }
}
