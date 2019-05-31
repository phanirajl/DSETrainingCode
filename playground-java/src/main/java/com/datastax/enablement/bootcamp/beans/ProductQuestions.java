/**
 * 
 */
package com.datastax.enablement.bootcamp.beans;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.Computed;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

/**
 * @author matwater
 *
 */

@Table(name = "product_questions",
        keyspace = "aurabute",
        readConsistency = "LOCAL_ONE",
        writeConsistency = "LOCAL_QUORUM",
        caseSensitiveKeyspace = false,
        caseSensitiveTable = false)
public class ProductQuestions {

    @PartitionKey(value = 0)
    @Column(name = "product_id")
    private UUID productId;

    @ClusteringColumn(value = 0)
    @Column(name = "question_id")
    private UUID questionId;

    @ClusteringColumn(value = 1)
    @Column(name = "answer_id")
    private UUID answerId;

    @Computed("toTimestamp(answer_id)")
    private Date answerTime;

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public UUID getQuestionId() {
        return questionId;
    }

    public void setQuestionId(UUID questionId) {
        this.questionId = questionId;
    }

    public UUID getAnswerId() {
        return answerId;
    }

    public void setAnswerId(UUID answerId) {
        this.answerId = answerId;
    }

    public Date getAnswerTime() {
        return answerTime;
    }

    @Override
    public String toString() {
        return "ProductQuestions [productId=" + productId + ", questionId=" + questionId + ", answerId=" + answerId
                + ", answerTime=" + answerTime + "]";
    }

    
}
