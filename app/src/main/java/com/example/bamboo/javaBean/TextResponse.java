package com.example.bamboo.javaBean;

public class TextResponse {

    private OSKF OSKF;
    private POSF POSF;
    private PsyF PsyF;
    private ShaF ShaF;

    public OSKF getOSKF() {
        return OSKF;
    }

    public void setOSKF(OSKF OSKF) {
        this.OSKF = OSKF;
    }

    public POSF getPOSF() {
        return POSF;
    }

    public void setPOSF(POSF POSF) {
        this.POSF = POSF;
    }

    public PsyF getPsyF() {
        return PsyF;
    }

    public void setPsyF(PsyF psyF) {
        this.PsyF = psyF;
    }

    public ShaF getShaF() {
        return ShaF;
    }

    public void setShaF(ShaF shaF) {
        this.ShaF = shaF;
    }

    public static class OSKF{
        public String BClar05_S;

        public String getBClar05_S() {
            return BClar05_S;
        }

        public void setBClar05_S(String BClar05_S) {
            this.BClar05_S = BClar05_S;
        }
    }

    public static class POSF{
        public String as_ContW_C;

        public String getAs_ContW_C() {
            return as_ContW_C;
        }

        public void setAs_ContW_C(String as_ContW_C) {
            this.as_ContW_C = as_ContW_C;
        }
    }
    public static class PsyF{
        public String as_AABiL_C;

        public String getAs_AABiL_C() {
            return as_AABiL_C;
        }

        public void setAs_AABiL_C(String as_AABiL_C) {
            this.as_AABiL_C = as_AABiL_C;
        }
    }

    public static class ShaF{
        public String TokSenL_S;

        public String getTokSenL_S() {
            return TokSenL_S;
        }

        public void setTokSenL_S(String tokSenL_S) {
            TokSenL_S = tokSenL_S;
        }
    }
}
