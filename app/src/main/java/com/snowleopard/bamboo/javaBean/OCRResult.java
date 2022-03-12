package com.snowleopard.bamboo.javaBean;

import java.util.List;


public class OCRResult {


    private List<WordsResultDTO> words_result;

    private Integer words_result_num;

    private Long log_id;

    public static class WordsResultDTO {

        private String words;

        @Override
        public String toString() {
            return words ;
        }
    }

    public OCRResult(List<WordsResultDTO> wordsResult, Integer wordsResultNum, Long logId) {
        this.words_result = wordsResult;
        this.words_result_num = wordsResultNum;
        this.log_id = logId;
    }

    public List<WordsResultDTO> getWords_result() {
        return words_result;
    }

    public void setWords_result(List<WordsResultDTO> words_result) {
        this.words_result = words_result;
    }

    public Integer getWords_result_num() {
        return words_result_num;
    }

    public void setWords_result_num(Integer words_result_num) {
        this.words_result_num = words_result_num;
    }

    public Long getLog_id() {
        return log_id;
    }

    public void setLog_id(Long log_id) {
        this.log_id = log_id;
    }
}
