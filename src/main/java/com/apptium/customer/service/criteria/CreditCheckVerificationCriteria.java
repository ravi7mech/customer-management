package com.apptium.customer.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.apptium.customer.domain.CreditCheckVerification} entity. This class is used
 * in {@link com.apptium.customer.web.rest.CreditCheckVerificationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /credit-check-verifications?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CreditCheckVerificationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter verQuestion;

    private StringFilter verQuestionChoice;

    private StringFilter verAnswer;

    public CreditCheckVerificationCriteria() {}

    public CreditCheckVerificationCriteria(CreditCheckVerificationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.verQuestion = other.verQuestion == null ? null : other.verQuestion.copy();
        this.verQuestionChoice = other.verQuestionChoice == null ? null : other.verQuestionChoice.copy();
        this.verAnswer = other.verAnswer == null ? null : other.verAnswer.copy();
    }

    @Override
    public CreditCheckVerificationCriteria copy() {
        return new CreditCheckVerificationCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getVerQuestion() {
        return verQuestion;
    }

    public StringFilter verQuestion() {
        if (verQuestion == null) {
            verQuestion = new StringFilter();
        }
        return verQuestion;
    }

    public void setVerQuestion(StringFilter verQuestion) {
        this.verQuestion = verQuestion;
    }

    public StringFilter getVerQuestionChoice() {
        return verQuestionChoice;
    }

    public StringFilter verQuestionChoice() {
        if (verQuestionChoice == null) {
            verQuestionChoice = new StringFilter();
        }
        return verQuestionChoice;
    }

    public void setVerQuestionChoice(StringFilter verQuestionChoice) {
        this.verQuestionChoice = verQuestionChoice;
    }

    public StringFilter getVerAnswer() {
        return verAnswer;
    }

    public StringFilter verAnswer() {
        if (verAnswer == null) {
            verAnswer = new StringFilter();
        }
        return verAnswer;
    }

    public void setVerAnswer(StringFilter verAnswer) {
        this.verAnswer = verAnswer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CreditCheckVerificationCriteria that = (CreditCheckVerificationCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(verQuestion, that.verQuestion) &&
            Objects.equals(verQuestionChoice, that.verQuestionChoice) &&
            Objects.equals(verAnswer, that.verAnswer)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, verQuestion, verQuestionChoice, verAnswer);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CreditCheckVerificationCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (verQuestion != null ? "verQuestion=" + verQuestion + ", " : "") +
            (verQuestionChoice != null ? "verQuestionChoice=" + verQuestionChoice + ", " : "") +
            (verAnswer != null ? "verAnswer=" + verAnswer + ", " : "") +
            "}";
    }
}
