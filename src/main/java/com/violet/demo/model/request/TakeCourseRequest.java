package com.violet.demo.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public class TakeCourseRequest {
    @JsonProperty("course_ids")
    private Set<Long> courseIds;

    public Set<Long> getCourseIds() {
        return courseIds;
    }

    public void setCourseIds(Set<Long> courseIds) {
        this.courseIds = courseIds;
    }
}
