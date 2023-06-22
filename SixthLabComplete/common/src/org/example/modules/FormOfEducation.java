package org.example.modules;



public enum FormOfEducation  {
    DISTANCE_EDUCATION(100),
    FULL_TIME_EDUCATION(80),
    EVENING_CLASSES(70);
    public final int quality;

    FormOfEducation(int quality) {
        this.quality = quality;
    }
}
