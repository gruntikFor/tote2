package tote.entity;

import java.util.List;

public class Category extends Entity {

    private static final long serialVersionUID = 1L;

    private String name;

    private List<Event> events;

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category(String name) {
        super();
        this.name = name;
    }

    public Category() {
    }
}
