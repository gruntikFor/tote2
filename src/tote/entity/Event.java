package tote.entity;

import java.util.List;

public class Event extends Entity {

    private static final long serialVersionUID = 1L;

    private String date;
    private Long idCategory;
    private String name;
    private List<Result> results;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Long idCategory) {
        this.idCategory = idCategory;
    }

    public Event(String date, Long idCategory, String name) {
        super();
        this.date = date;
        this.idCategory = idCategory;
        this.name = name;
    }

    public Event() {
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> list) {
        this.results = list;
    }

}
