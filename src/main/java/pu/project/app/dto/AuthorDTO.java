package pu.project.app.dto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AuthorDTO extends AbstractEntity {

    private String name;
    private Integer birthYear;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }
}