package com.example.bamboo.javaBean;



public class TextResponse {
    private EnDFDTO EnDF;
   private EnGFDTO EnGF;
   private OSKFDTO OSKF;
   private POSFDTO POSF;
   private PhrFDTO PhrF;
   private PsyFDTO PsyF;
   private ShaFDTO ShaF;
   private TTRFDTO TTRF;
   private TrSFDTO TrSF;
   private TraFDTO TraF;
   private VarFDTO VarF;
   private WBKFDTO WBKF;
   private WoKFDTO WoKF;
   private WoLFDTO WoLF;
//cry了255个字段每个都要和json的一样，不然就接受不到，自动生成的和json字段又不一样wwwww
    public EnDFDTO getEnDF() {
        return EnDF;
    }

    public void setEnDF(EnDFDTO enDF) {
        EnDF = enDF;
    }

    public EnGFDTO getEnGF() {
        return EnGF;
    }

    public void setEnGF(EnGFDTO enGF) {
        EnGF = enGF;
    }

    public OSKFDTO getOSKF() {
        return OSKF;
    }

    public void setOSKF(OSKFDTO OSKF) {
        this.OSKF = OSKF;
    }

    public POSFDTO getPOSF() {
        return POSF;
    }

    public void setPOSF(POSFDTO POSF) {
        this.POSF = POSF;
    }

    public PhrFDTO getPhrF() {
        return PhrF;
    }

    public void setPhrF(PhrFDTO phrF) {
        PhrF = phrF;
    }

    public PsyFDTO getPsyF() {
        return PsyF;
    }

    public void setPsyF(PsyFDTO psyF) {
        PsyF = psyF;
    }

    public ShaFDTO getShaF() {
        return ShaF;
    }

    public void setShaF(ShaFDTO shaF) {
        ShaF = shaF;
    }

    public TTRFDTO getTTRF() {
        return TTRF;
    }

    public void setTTRF(TTRFDTO TTRF) {
        this.TTRF = TTRF;
    }

    public TrSFDTO getTrSF() {
        return TrSF;
    }

    public void setTrSF(TrSFDTO trSF) {
        TrSF = trSF;
    }

    public TraFDTO getTraF() {
        return TraF;
    }

    public void setTraF(TraFDTO traF) {
        TraF = traF;
    }

    public VarFDTO getVarF() {
        return VarF;
    }

    public void setVarF(VarFDTO varF) {
        VarF = varF;
    }

    public WBKFDTO getWBKF() {
        return WBKF;
    }

    public void setWBKF(WBKFDTO WBKF) {
        this.WBKF = WBKF;
    }

    public WoKFDTO getWoKF() {
        return WoKF;
    }

    public void setWoKF(WoKFDTO woKF) {
        WoKF = woKF;
    }

    public WoLFDTO getWoLF() {
        return WoLF;
    }

    public void setWoLF(WoLFDTO woLF) {
        WoLF = woLF;
    }


    public static class EnDFDTO {
               private String as_EntiM_C;
               private String as_UEnti_C;
               private String at_EntiM_C;
               private String at_UEnti_C;
               private String to_EntiM_C;
               private String to_UEnti_C;

        @Override
        public String toString() {
            return "\nEnDF{" +
                    "\nas_EntiM_C=" + as_EntiM_C +
                    "\n as_UEnti_C=" + as_UEnti_C +
                    "\nat_EntiM_C=" + at_EntiM_C +
                    "\n at_UEnti_C=" + at_UEnti_C +
                    "\n To_EntiM_C=" + to_EntiM_C +
                    "\n To_UEnti_C=" + to_UEnti_C +
                    '}';
        }
    }

    
    
    public static class EnGFDTO {
              private String LoCoDPA_S;
              private String LoCoDPU_S;
              private String LoCoDPW_S;
              private String LoCohPA_S;
              private String LoCohPU_S;
              private String LoCohPW_S;
              private String ra_NNTo_C;
              private String ra_NOTo_C;
              private String ra_NSTo_C;
              private String ra_NXTo_C;
              private String ra_ONTo_C;
              private String ra_OOTo_C;
              private String ra_OSTo_C;
              private String ra_OXTo_C;
              private String ra_SNTo_C;
              private String ra_SOTo_C;
              private String ra_SSTo_C;
              private String ra_SXTo_C;
              private String ra_XNTo_C;
              private String ra_XOTo_C;
              private String ra_XSTo_C;
              private String ra_XXTo_C;

        public String getRa_NNTo_C() {
            return ra_NNTo_C;
        }

        @Override
        public String toString() {
            return "\nEnGF{" +
                    "\nLoCoDPA_S=" + LoCoDPA_S +
                    "\n LoCoDPU_S=" + LoCoDPU_S +   
                    "\n LoCoDPW_S=" + LoCoDPW_S +   
                    "\n LoCohPA_S=" + LoCohPA_S +   
                    "\n LoCohPU_S=" + LoCohPU_S +   
                    "\n LoCohPW_S=" + LoCohPW_S +   
                    "\n ra_NNTo_C=" + ra_NNTo_C +   
                    "\n ra_NOTo_C=" + ra_NOTo_C +   
                    "\n ra_NSTo_C=" + ra_NSTo_C +   
                    "\n ra_NXTo_C=" + ra_NXTo_C +   
                    "\n ra_ONTo_C=" + ra_ONTo_C +   
                    "\n ra_OOTo_C=" + ra_OOTo_C +   
                    "\n ra_OSTo_C=" + ra_OSTo_C +   
                    "\n ra_OXTo_C=" + ra_OXTo_C +   
                    "\n ra_SNTo_C=" + ra_SNTo_C +   
                    "\n ra_SOTo_C=" + ra_SOTo_C +   
                    "\n ra_SSTo_C=" + ra_SSTo_C +   
                    "\n ra_SXTo_C=" + ra_SXTo_C +   
                    "\n ra_XNTo_C=" + ra_XNTo_C +   
                    "\n ra_XOTo_C=" + ra_XOTo_C +   
                    "\n ra_XSTo_C=" + ra_XSTo_C +   
                    "\n ra_XXTo_C=" + ra_XXTo_C +   
                    '}';
        }
    }

    
    
    public static class OSKFDTO {
              private String BClar05_S;
              private String BClar10_S;
              private String BClar15_S;
              private String BClar20_S;
              private String BNois05_S;
              private String BNois10_S;
              private String BNois15_S;
              private String BNois20_S;
              private String BRich05_S;
              private String BRich10_S;
              private String BRich15_S;
              private String BRich20_S;
              private String BTopc05_S;
              private String BTopc10_S;
              private String BTopc15_S;
              private String BTopc20_S;

        @Override
        public String toString() {
            return "\nOSKF{" +
                    "\nBClar05_S=" + BClar05_S +
                    "\n BClar10_S=" + BClar10_S +   
                    "\n BClar15_S=" + BClar15_S +   
                    "\n BClar20_S=" + BClar20_S +   
                    "\n BNois05_S=" + BNois05_S +   
                    "\n BNois10_S=" + BNois10_S +   
                    "\n BNois15_S=" + BNois15_S +   
                    "\n BNois20_S=" + BNois20_S +   
                    "\n BRich05_S=" + BRich05_S +   
                    "\n BRich10_S=" + BRich10_S +   
                    "\n BRich15_S=" + BRich15_S +   
                    "\n BRich20_S=" + BRich20_S +   
                    "\n BTopc05_S=" + BTopc05_S +   
                    "\n BTopc10_S=" + BTopc10_S +   
                    "\n BTopc15_S=" + BTopc15_S +   
                    "\n BTopc20_S=" + BTopc20_S +   
                    '}';
        }
    }

    
    
    public static class POSFDTO {
               private String as_AjTag_C;
               private String as_AvTag_C;
               private String as_CoTag_C;
               private String as_ContW_C;
               private String as_FuncW_C;
               private String as_NoTag_C;
               private String as_SuTag_C;
               private String as_VeTag_C;
               private String at_AjTag_C;
               private String at_AvTag_C;
               private String at_CoTag_C;
               private String at_ContW_C;
               private String at_FuncW_C;
               private String at_NoTag_C;
               private String at_SuTag_C;
               private String at_VeTag_C;
               private String ra_AjAvT_C;
               private String ra_AjCoT_C;
               private String ra_AjNoT_C;
               private String ra_AjSuT_C;
               private String ra_AjVeT_C;
               private String ra_AvAjT_C;
               private String ra_AvCoT_C;
               private String ra_AvNoT_C;
               private String ra_AvSuT_C;
               private String ra_AvVeT_C;
               private String ra_CoAjT_C;
               private String ra_CoAvT_C;
               private String ra_CoFuW_C;
               private String ra_CoNoT_C;
               private String ra_CoSuT_C;
               private String ra_CoVeT_C;
               private String ra_NoAjT_C;
               private String ra_NoAvT_C;
               private String ra_NoCoT_C;
               private String ra_NoSuT_C;
               private String ra_NoVeT_C;
               private String ra_SuAjT_C;
               private String ra_SuAvT_C;
               private String ra_SuCoT_C;
               private String ra_SuNoT_C;
               private String ra_SuVeT_C;
               private String ra_VeAjT_C;
               private String ra_VeAvT_C;
               private String ra_VeCoT_C;
               private String ra_VeNoT_C;
               private String ra_VeSuT_C;
               private String to_AjTag_C;
               private String to_AvTag_C;
               private String to_CoTag_C;
               private String to_ContW_C;
               private String to_FuncW_C;
               private String to_NoTag_C;
               private String to_SuTag_C;
               private String to_VeTag_C;

        @Override
        public String toString() {
            return "\nPOSF{" +
                    "\nas_AjTag_C=" + as_AjTag_C +
                    "\n as_AvTag_C=" + as_AvTag_C +   
                    "\n as_CoTag_C=" + as_CoTag_C +   
                    "\n as_ContW_C=" + as_ContW_C +   
                    "\n as_FuncW_C=" + as_FuncW_C +   
                    "\n as_NoTag_C=" + as_NoTag_C +   
                    "\n as_SuTag_C=" + as_SuTag_C +   
                    "\n as_VeTag_C=" + as_VeTag_C +   
                    "\n at_AjTag_C=" + at_AjTag_C +   
                    "\n at_AvTag_C=" + at_AvTag_C +   
                    "\n at_CoTag_C=" + at_CoTag_C +   
                    "\n at_ContW_C=" + at_ContW_C +   
                    "\n at_FuncW_C=" + at_FuncW_C +   
                    "\n at_NoTag_C=" + at_NoTag_C +   
                    "\n at_SuTag_C=" + at_SuTag_C +   
                    "\n at_VeTag_C=" + at_VeTag_C +   
                    "\n ra_AjAvT_C=" + ra_AjAvT_C +   
                    "\n ra_AjCoT_C=" + ra_AjCoT_C +   
                    "\n ra_AjNoT_C=" + ra_AjNoT_C +   
                    "\n ra_AjSuT_C=" + ra_AjSuT_C +   
                    "\n ra_AjVeT_C=" + ra_AjVeT_C +   
                    "\n ra_AvAjT_C=" + ra_AvAjT_C +   
                    "\n ra_AvCoT_C=" + ra_AvCoT_C +   
                    "\n ra_AvNoT_C=" + ra_AvNoT_C +   
                    "\n ra_AvSuT_C=" + ra_AvSuT_C +   
                    "\n ra_AvVeT_C=" + ra_AvVeT_C +   
                    "\n ra_CoAjT_C=" + ra_CoAjT_C +   
                    "\n ra_CoAvT_C=" + ra_CoAvT_C +   
                    "\n ra_CoFuW_C=" + ra_CoFuW_C +   
                    "\n ra_CoNoT_C=" + ra_CoNoT_C +   
                    "\n ra_CoSuT_C=" + ra_CoSuT_C +   
                    "\n ra_CoVeT_C=" + ra_CoVeT_C +   
                    "\n ra_NoAjT_C=" + ra_NoAjT_C +   
                    "\n ra_NoAvT_C=" + ra_NoAvT_C +   
                    "\n ra_NoCoT_C=" + ra_NoCoT_C +   
                    "\n ra_NoSuT_C=" + ra_NoSuT_C +   
                    "\n ra_NoVeT_C=" + ra_NoVeT_C +   
                    "\n ra_SuAjT_C=" + ra_SuAjT_C +   
                    "\n ra_SuAvT_C=" + ra_SuAvT_C +   
                    "\n ra_SuCoT_C=" + ra_SuCoT_C +   
                    "\n ra_SuNoT_C=" + ra_SuNoT_C +   
                    "\n ra_SuVeT_C=" + ra_SuVeT_C +   
                    "\n ra_VeAjT_C=" + ra_VeAjT_C +   
                    "\n ra_VeAvT_C=" + ra_VeAvT_C +   
                    "\n ra_VeCoT_C=" + ra_VeCoT_C +   
                    "\n ra_VeNoT_C=" + ra_VeNoT_C +   
                    "\n ra_VeSuT_C=" + ra_VeSuT_C +   
                    "\n To_AjTag_C=" + to_AjTag_C +
                    "\n To_AvTag_C=" + to_AvTag_C +
                    "\n To_CoTag_C=" + to_CoTag_C +
                    "\n To_ContW_C=" + to_ContW_C +
                    "\n To_FuncW_C=" + to_FuncW_C +
                    "\n To_NoTag_C=" + to_NoTag_C +
                    "\n To_SuTag_C=" + to_SuTag_C +
                    "\n To_VeTag_C=" + to_VeTag_C +
                    '}';
        }
    }

    
    
    public static class PhrFDTO {
               private String as_AjPhr_C;
               private String as_AvPhr_C;
               private String as_NoPhr_C;
               private String as_PrPhr_C;
               private String as_SuPhr_C;
               private String as_VePhr_C;
               private String at_AjPhr_C;
               private String at_AvPhr_C;
               private String at_NoPhr_C;
               private String at_PrPhr_C;
               private String at_SuPhr_C;
               private String at_VePhr_C;
               private String ra_AjAvP_C;
               private String ra_AjNoP_C;
               private String ra_AjPrP_C;
               private String ra_AjSuP_C;
               private String ra_AjVeP_C;
               private String ra_AvAjP_C;
               private String ra_AvNoP_C;
               private String ra_AvPrP_C;
               private String ra_AvSuP_C;
               private String ra_AvVeP_C;
               private String ra_NoAjP_C;
               private String ra_NoAvP_C;
               private String ra_NoPrP_C;
               private String ra_NoSuP_C;
               private String ra_NoVeP_C;
               private String ra_PrAjP_C;
               private String ra_PrAvP_C;
               private String ra_PrNoP_C;
               private String ra_PrSuP_C;
               private String ra_PrVeP_C;
               private String ra_SuAjP_C;
               private String ra_SuAvP_C;
               private String ra_SuNoP_C;
               private String ra_SuPrP_C;
               private String ra_SuVeP_C;
               private String ra_VeAjP_C;
               private String ra_VeAvP_C;
               private String ra_VeNoP_C;
               private String ra_VePrP_C;
               private String ra_VeSuP_C;
               private String to_AjPhr_C;
               private String to_AvPhr_C;
               private String to_NoPhr_C;
               private String to_PrPhr_C;
               private String to_SuPhr_C;
               private String to_VePhr_C;

        @Override
        public String toString() {
            return "\nPhrF{" +
                    "\nas_AjPhr_C=" + as_AjPhr_C +
                    "\n as_AvPhr_C=" + as_AvPhr_C +   
                    "\n as_NoPhr_C=" + as_NoPhr_C +   
                    "\n as_PrPhr_C=" + as_PrPhr_C +   
                    "\n as_SuPhr_C=" + as_SuPhr_C +   
                    "\n as_VePhr_C=" + as_VePhr_C +   
                    "\n at_AjPhr_C=" + at_AjPhr_C +   
                    "\n at_AvPhr_C=" + at_AvPhr_C +   
                    "\n at_NoPhr_C=" + at_NoPhr_C +   
                    "\n at_PrPhr_C=" + at_PrPhr_C +   
                    "\n at_SuPhr_C=" + at_SuPhr_C +   
                    "\n at_VePhr_C=" + at_VePhr_C +   
                    "\n ra_AjAvP_C=" + ra_AjAvP_C +   
                    "\n ra_AjNoP_C=" + ra_AjNoP_C +   
                    "\n ra_AjPrP_C=" + ra_AjPrP_C +   
                    "\n ra_AjSuP_C=" + ra_AjSuP_C +   
                    "\n ra_AjVeP_C=" + ra_AjVeP_C +   
                    "\n ra_AvAjP_C=" + ra_AvAjP_C +   
                    "\n ra_AvNoP_C=" + ra_AvNoP_C +   
                    "\n ra_AvPrP_C=" + ra_AvPrP_C +   
                    "\n ra_AvSuP_C=" + ra_AvSuP_C +   
                    "\n ra_AvVeP_C=" + ra_AvVeP_C +   
                    "\n ra_NoAjP_C=" + ra_NoAjP_C +   
                    "\n ra_NoAvP_C=" + ra_NoAvP_C +   
                    "\n ra_NoPrP_C=" + ra_NoPrP_C +   
                    "\n ra_NoSuP_C=" + ra_NoSuP_C +   
                    "\n ra_NoVeP_C=" + ra_NoVeP_C +   
                    "\n ra_PrAjP_C=" + ra_PrAjP_C +   
                    "\n ra_PrAvP_C=" + ra_PrAvP_C +   
                    "\n ra_PrNoP_C=" + ra_PrNoP_C +   
                    "\n ra_PrSuP_C=" + ra_PrSuP_C +   
                    "\n ra_PrVeP_C=" + ra_PrVeP_C +   
                    "\n ra_SuAjP_C=" + ra_SuAjP_C +   
                    "\n ra_SuAvP_C=" + ra_SuAvP_C +   
                    "\n ra_SuNoP_C=" + ra_SuNoP_C +   
                    "\n ra_SuPrP_C=" + ra_SuPrP_C +   
                    "\n ra_SuVeP_C=" + ra_SuVeP_C +   
                    "\n ra_VeAjP_C=" + ra_VeAjP_C +   
                    "\n ra_VeAvP_C=" + ra_VeAvP_C +   
                    "\n ra_VeNoP_C=" + ra_VeNoP_C +   
                    "\n ra_VePrP_C=" + ra_VePrP_C +   
                    "\n ra_VeSuP_C=" + ra_VeSuP_C +   
                    "\n To_AjPhr_C=" + to_AjPhr_C +
                    "\n To_AvPhr_C=" + to_AvPhr_C +
                    "\n To_NoPhr_C=" + to_NoPhr_C +
                    "\n To_PrPhr_C=" + to_PrPhr_C +
                    "\n To_SuPhr_C=" + to_SuPhr_C +
                    "\n To_VePhr_C=" + to_VePhr_C +
                    '}';
        }
    }

    
    
    public static class PsyFDTO {
               private String as_AABiL_C;
               private String as_AABrL_C;
               private String as_AACoL_C;
               private String as_AAKuL_C;
               private String as_AAKuW_C;
               private String at_AABiL_C;
               private String at_AABrL_C;
               private String at_AACoL_C;
               private String at_AAKuL_C;
               private String at_AAKuW_C;
               private String to_AABiL_C;
               private String to_AABrL_C;
               private String to_AACoL_C;
               private String to_AAKuL_C;
               private String to_AAKuW_C;

        public String getAt_AABiL_C() {
            return at_AABiL_C;
        }



        @Override
        public String toString() {
            return "\nPsyF{" +
                    "\nas_AABiL_C=" + as_AABiL_C +
                    "\n as_AABrL_C=" + as_AABrL_C +   
                    "\n as_AACoL_C=" + as_AACoL_C +   
                    "\n as_AAKuL_C=" + as_AAKuL_C +   
                    "\n as_AAKuW_C=" + as_AAKuW_C +   
                    "\n at_AABiL_C=" + at_AABiL_C +   
                    "\n at_AABrL_C=" + at_AABrL_C +   
                    "\n at_AACoL_C=" + at_AACoL_C +   
                    "\n at_AAKuL_C=" + at_AAKuL_C +   
                    "\n at_AAKuW_C=" + at_AAKuW_C +   
                    "\n To_AABiL_C=" + to_AABiL_C +
                    "\n To_AABrL_C=" + to_AABrL_C +
                    "\n To_AACoL_C=" + to_AACoL_C +
                    "\n To_AAKuL_C=" + to_AAKuL_C +
                    "\n To_AAKuW_C=" + to_AAKuW_C +
                    '}';
        }
    }

    
    
    public static class ShaFDTO {
              private String TokSenL_S;
              private String TokSenM_S;
              private String TokSenS_S;
               private String as_Chara_C;
               private String as_Sylla_C;
               private String as_Token_C;
               private String at_Chara_C;
               private String at_Sylla_C;

        public String getAt_Chara_C() {
            return at_Chara_C;
        }

        @Override
        public String toString() {
            return "\nShaF{" +
                    "\nTokSenL_S=" + TokSenL_S +
                    "\n TokSenM_S=" + TokSenM_S +   
                    "\n TokSenS_S=" + TokSenS_S +   
                    "\n as_Chara_C=" + as_Chara_C +   
                    "\n as_Sylla_C=" + as_Sylla_C +   
                    "\n as_Token_C=" + as_Token_C +   
                    "\n at_Chara_C=" + at_Chara_C +   
                    "\n at_Sylla_C=" + at_Sylla_C +   
                    '}';
        }
    }

    
    
    public static class TTRFDTO {
              private String BiLoTTR_S;
              private String CorrTTR_S;
              private String MTLDTTR_S;
              private String SimpTTR_S;
              private String UberTTR_S;

        @Override
        public String toString() {
            return "\nTTRF{" +
                    "\nBiLoTTR_S=" + BiLoTTR_S +
                    "\n CorrTTR_S=" + CorrTTR_S +   
                    "\n MTLDTTR_S=" + MTLDTTR_S +   
                    "\n SimpTTR_S=" + SimpTTR_S +   
                    "\n UberTTR_S=" + UberTTR_S +   
                    '}';
        }
    }

    
    
    public static class TrSFDTO {
               private String as_FTree_C;
               private String as_TreeH_C;
               private String at_FTree_C;
               private String at_TreeH_C;
               private String to_FTree_C;
               private String to_TreeH_C;

        public String getAt_FTree_C() {
            return at_FTree_C;
        }

        @Override
        public String toString() {
            return "\nTrSF{" +
                    "\nas_FTree_C=" + as_FTree_C +
                    "\n as_TreeH_C=" + as_TreeH_C +   
                    "\n at_FTree_C=" + at_FTree_C +   
                    "\n at_TreeH_C=" + at_TreeH_C +   
                    "\n To_FTree_C=" + to_FTree_C +
                    "\n To_TreeH_C=" + to_TreeH_C +
                    '}';
        }
    }

    
    
    public static class TraFDTO {
              private String AutoRea_S;
              private String ColeLia_S;
              private String FleschG_S;
              private String Gunning_S;
              private String LinseaW_S;
              private String SmogInd_S;

        @Override
        public String toString() {
            return "\nTraF{" +
                    "\nAutoRea_S=" + AutoRea_S +
                    "\n ColeLia_S=" + ColeLia_S +   
                    "\n FleschG_S=" + FleschG_S +   
                    "\n Gunning_S=" + Gunning_S +   
                    "\n LinseaW_S=" + LinseaW_S +   
                    "\n SmogInd_S=" + SmogInd_S +   
                    '}';
        }
    }

    
    
    public static class VarFDTO {
              private String CorrAjV_S;
              private String CorrAvV_S;
              private String CorrNoV_S;
              private String CorrVeV_S;
              private String SimpAjV_S;
              private String SimpAvV_S;
              private String SimpNoV_S;
              private String SimpVeV_S;
              private String SquaAjV_S;
              private String SquaAvV_S;
              private String SquaNoV_S;
              private String SquaVeV_S;

        @Override
        public String toString() {
            return "\nVarF{" +
                    "\nCorrAjV_S=" + CorrAjV_S +
                    "\n CorrAvV_S=" + CorrAvV_S +   
                    "\n CorrNoV_S=" + CorrNoV_S +   
                    "\n CorrVeV_S=" + CorrVeV_S +   
                    "\n SimpAjV_S=" + SimpAjV_S +   
                    "\n SimpAvV_S=" + SimpAvV_S +   
                    "\n SimpNoV_S=" + SimpNoV_S +   
                    "\n SimpVeV_S=" + SimpVeV_S +   
                    "\n SquaAjV_S=" + SquaAjV_S +   
                    "\n SquaAvV_S=" + SquaAvV_S +   
                    "\n SquaNoV_S=" + SquaNoV_S +   
                    "\n SquaVeV_S=" + SquaVeV_S +   
                    '}';
        }
    }

    
    
    public static class WBKFDTO {
              private String BClar05_S;
              private String BClar10_S;
              private String BClar15_S;
              private String BClar20_S;
              private String BNois05_S;
              private String BNois10_S;
              private String BNois15_S;
              private String BNois20_S;
              private String BRich05_S;
              private String BRich10_S;
              private String BRich15_S;
              private String BRich20_S;
              private String BTopc05_S;
              private String BTopc10_S;
              private String BTopc15_S;
              private String BTopc20_S;

        @Override
        public String toString() {
            return "\nWBKF{" +
                    "\nBClar05_S=" + BClar05_S +
                    "\n BClar10_S=" + BClar10_S +   
                    "\n BClar15_S=" + BClar15_S +   
                    "\n BClar20_S=" + BClar20_S +   
                    "\n BNois05_S=" + BNois05_S +   
                    "\n BNois10_S=" + BNois10_S +   
                    "\n BNois15_S=" + BNois15_S +   
                    "\n BNois20_S=" + BNois20_S +   
                    "\n BRich05_S=" + BRich05_S +   
                    "\n BRich10_S=" + BRich10_S +   
                    "\n BRich15_S=" + BRich15_S +   
                    "\n BRich20_S=" + BRich20_S +   
                    "\n BTopc05_S=" + BTopc05_S +   
                    "\n BTopc10_S=" + BTopc10_S +   
                    "\n BTopc15_S=" + BTopc15_S +   
                    "\n BTopc20_S=" + BTopc20_S +   
                    '}';
        }
    }

    
    
    public static class WoKFDTO {
              private String WClar05_S;
              private String WClar10_S;
              private String WClar15_S;
              private String WClar20_S;
              private String WNois05_S;
              private String WNois10_S;
              private String WNois15_S;
              private String WNois20_S;
              private String WRich05_S;
              private String WRich10_S;
              private String WRich15_S;
              private String WRich20_S;
              private String WTopc05_S;
              private String WTopc10_S;
              private String WTopc15_S;
              private String WTopc20_S;

        public String getWRich05_S() {
            return WRich05_S;
        }

        @Override
        public String toString() {
            return "\nWoKF{" +
                    "\nWClar05_S=" + WClar05_S +
                    "\n WClar10_S=" + WClar10_S +   
                    "\n WClar15_S=" + WClar15_S +   
                    "\n WClar20_S=" + WClar20_S +   
                    "\n WNois05_S=" + WNois05_S +   
                    "\n WNois10_S=" + WNois10_S +   
                    "\n WNois15_S=" + WNois15_S +   
                    "\n WNois20_S=" + WNois20_S +   
                    "\n WRich05_S=" + WRich05_S +   
                    "\n WRich10_S=" + WRich10_S +   
                    "\n WRich15_S=" + WRich15_S +   
                    "\n WRich20_S=" + WRich20_S +   
                    "\n WTopc05_S=" + WTopc05_S +   
                    "\n WTopc10_S=" + WTopc10_S +   
                    "\n WTopc15_S=" + WTopc15_S +   
                    "\n WTopc20_S=" + WTopc20_S +   
                    '}';
        }
    }

    
    
    public static class WoLFDTO {
               private String as_SbCDC_C;
               private String as_SbCDL_C;
               private String as_SbFrL_C;
               private String as_SbFrQ_C;
               private String as_SbL1C_C;
               private String as_SbL1W_C;
               private String as_SbSBC_C;
               private String as_SbSBW_C;
               private String at_SbCDC_C;
               private String at_SbCDL_C;
               private String at_SbFrL_C;
               private String at_SbFrQ_C;
               private String at_SbL1C_C;
               private String at_SbL1W_C;
               private String at_SbSBC_C;
               private String at_SbSBW_C;
               private String to_SbCDC_C;
               private String to_SbCDL_C;
               private String to_SbFrL_C;
               private String to_SbFrQ_C;
               private String to_SbL1C_C;
               private String to_SbL1W_C;
               private String to_SbSBC_C;
               private String to_SbSBW_C;

        @Override
        public String toString() {
            return "\nWoLF{" +
                    "\nas_SbCDC_C=" + as_SbCDC_C +
                    "\n as_SbCDL_C=" + as_SbCDL_C +   
                    "\n as_SbFrL_C=" + as_SbFrL_C +   
                    "\n as_SbFrQ_C=" + as_SbFrQ_C +   
                    "\n as_SbL1C_C=" + as_SbL1C_C +   
                    "\n as_SbL1W_C=" + as_SbL1W_C +   
                    "\n as_SbSBC_C=" + as_SbSBC_C +   
                    "\n as_SbSBW_C=" + as_SbSBW_C +   
                    "\n at_SbCDC_C=" + at_SbCDC_C +   
                    "\n at_SbCDL_C=" + at_SbCDL_C +   
                    "\n at_SbFrL_C=" + at_SbFrL_C +   
                    "\n at_SbFrQ_C=" + at_SbFrQ_C +   
                    "\n at_SbL1C_C=" + at_SbL1C_C +   
                    "\n at_SbL1W_C=" + at_SbL1W_C +   
                    "\n at_SbSBC_C=" + at_SbSBC_C +   
                    "\n at_SbSBW_C=" + at_SbSBW_C +   
                    "\n to_SbCDC_C=" + to_SbCDC_C +   
                    "\n to_SbCDL_C=" + to_SbCDL_C +   
                    "\n to_SbFrL_C=" + to_SbFrL_C +   
                    "\n to_SbFrQ_C=" + to_SbFrQ_C +   
                    "\n to_SbL1C_C=" + to_SbL1C_C +   
                    "\n to_SbL1W_C=" + to_SbL1W_C +   
                    "\n to_SbSBC_C=" + to_SbSBC_C +   
                    "\n to_SbSBW_C=" + to_SbSBW_C +   
                    '}';
        }
    }

}
