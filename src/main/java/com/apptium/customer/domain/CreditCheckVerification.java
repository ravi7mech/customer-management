package com.apptium.customer.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A CreditCheckVerification.
 */
@Entity
@Table(name = "credit_check_verification")
public class CreditCheckVerification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "ver_question", nullable = false)
    private String verQuestion;

    @NotNull
    @Column(name = "ver_question_choice", nullable = false)
    private String verQuestionChoice;

    @NotNull
    @Column(name = "ver_answer", nullable = false)
    private String verAnswer;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CreditCheckVerification id(Long id) {
        this.id = id;
        return this;
    }

    public String getVerQuestion() {
        return this.verQuestion;
    }

    public CreditCheckVerification verQuestion(String verQuestion) {
        this.verQuestion = verQuestion;
        return this;
    }

    public void setVerQuestion(String verQuestion) {
        this.verQuestion = verQuestion;
    }

    public String getVerQuestionChoice() {
        return this.verQuestionChoice;
    }

    public CreditCheckVerification verQuestionChoice(String verQuestionChoice) {
        this.verQuestionChoice = verQuestionChoice;
        return this;
    }

    public void setVerQuestionChoice(String verQuestionChoice) {
        this.verQuestionChoice = verQuestionChoice;
    }

    public String getVerAnswer() {
        return this.verAnswer;
    }

    public CreditCheckVerification verAnswer(String verAnswer) {
        this.verAnswer = verAnswer;
        return this;
    }

    public void setVerAnswer(String verAnswer) {
        this.verAnswer = verAnswer;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreditCheckVerification)) {
            return false;
        }
        return id != null && id.equals(((CreditCheckVerification) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CreditCheckVerification{" +
            "id=" + getId() +
            ", verQuestion='" + getVerQuestion() + "'" +
            ", verQuestionChoice='" + getVerQuestionChoice() + "'" +
            ", verAnswer='" + getVerAnswer() + "'" +
            "}";
    }
}
