package com.abn_amro.exercise.dto;

/**
 * DTO object of the instruction entity
 */
public class InstructionDTO {
    private Integer id;

    private String description;

    private Integer sequence;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Integer getSequence() {
        return sequence;
    }
}
