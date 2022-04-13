package com.example.winxbitirmeapp.Models;

public class AchievementModel
{

    private long id;
    private String email;
    private String achievementType;
    private String description;

    public long getId() {
        return id;
    }

    public AchievementModel(long id, String email, String achievementType, String description, double percentage, boolean isCompleted, long goal, long occurred) {
        this.id = id;
        this.email = email;
        this.achievementType = achievementType;
        this.description = description;
        this.percentage = percentage;
        this.isCompleted = isCompleted;
        this.goal = goal;
        this.occurred = occurred;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAchievementType() {
        return achievementType;
    }

    public void setAchievementType(String achievementType) {
        this.achievementType = achievementType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public long getGoal() {
        return goal;
    }

    public void setGoal(long goal) {
        this.goal = goal;
    }

    public long getOccurred() {
        return occurred;
    }

    public void setOccurred(long occurred) {
        this.occurred = occurred;
    }

    private double percentage;
    private boolean isCompleted;
    private long goal;
    private long occurred;





}
