package com.apptium.customer.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.apptium.customer.domain.CreditCheckVerification} entity.
 */
public class CreditCheckVerificationDTO implements Serializable {

    private Long id;

    @NotNull
    private String verQuestion;

    @NotNull
    private String verQuestionChoice;

    @NotNull
    private String verAnswer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVerQuestion() {
        return verQuestion;
    }

    public void setVerQuestion(String verQuestion) {
        this.verQuestion = verQuestion;
    }

    public String getVerQuestionChoice() {
        return verQuestionChoice;
    }

    public void setVerQuestionChoice(String verQuestionChoice) {
        this.verQuestionChoice = verQuestionChoice;
    }

    public String getVerAnswer() {
        return verAnswer;
    }

    public void setVerAnswer(String verAnswer) {
        this.verAnswer = verAnswer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreditCheckVerificationDTO)) {
            return false;
        }

        CreditCheckVerificationDTO creditCheckVerificationDTO = (CreditCheckVerificationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, creditCheckVerificationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CreditCheckVerificationDTO{" +
            "id=" + getId() +
            ", verQuestion='" + getVerQuestion() + "'" +
            ", verQuestionChoice='" + getVerQuestionChoice() + "'" +
            ", verAnswer='" + getVerAnswer() + "'" +
            "}";
    }
}
