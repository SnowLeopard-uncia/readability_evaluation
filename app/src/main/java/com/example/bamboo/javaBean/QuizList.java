package com.example.bamboo.javaBean;

public class QuizList {
    private String answer;
    private Integer book_id;
    private ChoiceObjDTO choice_obj;
    private String createdAt;
    private String objectId;
    private String question;
    private String question_id;
    private String updatedAt;
    private ChoiceDTO choice;

    public static class ChoiceObjDTO {
        private String a;
        private String b;
        private String c;
        private String d;

        public String getA() {
            return a;
        }

        public void setA(String a) {
            this.a = a;
        }

        public String getB() {
            return b;
        }

        public void setB(String b) {
            this.b = b;
        }

        public String getC() {
            return c;
        }

        public void setC(String c) {
            this.c = c;
        }

        public String getD() {
            return d;
        }

        public void setD(String d) {
            this.d = d;
        }

    }



    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getBook_id() {
        return book_id;
    }

    public void setBook_id(Integer book_id) {
        this.book_id = book_id;
    }

    public ChoiceObjDTO getChoice_obj() {
        return choice_obj;
    }

    public void setChoice_obj(ChoiceObjDTO choice_obj) {
        this.choice_obj = choice_obj;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }


    public static class ChoiceDTO {
        private String A;
        private String B;
        private String C;
        private String D;

        public String getA() {
            return A;
        }

        public void setA(String a) {
            A = a;
        }

        public String getB() {
            return B;
        }

        public void setB(String b) {
            B = b;
        }

        public String getC() {
            return C;
        }

        public void setC(String c) {
            C = c;
        }

        public String getD() {
            return D;
        }

        public void setD(String d) {
            D = d;
        }
    }

    public ChoiceDTO getChoice() {
        return choice;
    }

    public void setChoice(ChoiceDTO choice) {
        this.choice = choice;
    }
}
