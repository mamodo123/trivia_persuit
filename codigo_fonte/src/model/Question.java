package model;

import br.ufsc.inf.leobr.cliente.Jogada;

public class Question implements Jogada {
    private String category;
    private String question;
    private String answer;
    private String right_answer;
    private boolean acertou;

    public Question(String category, String question, String answer, String right_answer, boolean acertou) {
        this.category = category;
        this.question = question;
        this.answer = answer;
        this.acertou = acertou;
        this.right_answer = right_answer;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isAcertou() {
        return acertou;
    }

    public void setAcertou(boolean acertou) {
        this.acertou = acertou;
    }

    public String getRight_answer() {
        return right_answer;
    }

    public void setRight_answer(String right_answer) {
        this.right_answer = right_answer;
    }
}
