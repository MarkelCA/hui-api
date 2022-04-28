package com.grupo5.huiapi.modules.event.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.grupo5.huiapi.modules.category.entity.Category;
import com.grupo5.huiapi.modules.user.entity.User;
import org.springframework.util.ObjectUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity @Table
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Event {
    @Id
    @SequenceGenerator(name = "event_sequence", sequenceName = "event_sequence", allocationSize = 1 )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_sequence")
    private Long id;
    @Column(nullable = false)
    private String title;
    private String description;
    @Column(nullable = false)
    private double latitude;
    @Column(nullable = false)
    private double longitude;


	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "event_categories",
        joinColumns = {
            @JoinColumn(name = "event_id", referencedColumnName = "id", updatable = false)
        },
        inverseJoinColumns = {
            @JoinColumn(name = "category_id", referencedColumnName = "id", updatable = false)
        }
    )
    @JsonIdentityReference(alwaysAsId = true)
    private Set<Category> categories = new HashSet<>();

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "organizer_id", nullable = false)
    @JsonIdentityReference(alwaysAsId = true)
    private User organizer;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "enrolled_events",
            joinColumns = {
                    @JoinColumn(name = "user_id", referencedColumnName = "id", updatable = false)
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "event_id", referencedColumnName = "id", updatable = false)
            }
    )
    private Set<Event> enrolled_users = new HashSet<>();

    public Event(String title, String description, Set<Category> categories, User organizer) {
        this.title = title;
        this.description = description;
        this.categories = categories;
        this.organizer = organizer;
    }


    public Event(String title, String description, User organizer) {
        this.title = title;
        this.description = description;
        this.organizer = organizer;
    }

        public Set<Category> addCategory(Category cat) {
        this.categories.add(cat);
        if(cat.getParent() != null)
            this.addCategory(cat.getParent());
        return this.categories;
    }

    public String checkNullFields() {
        List<String> missingFields = new ArrayList<>();
        if(ObjectUtils.isEmpty(this.title))
            missingFields.add("title");
        if(ObjectUtils.isEmpty(this.organizer))
            missingFields.add("organizer");
        if(ObjectUtils.isEmpty(this.categories))
            missingFields.add("categories");


        return missingFields.isEmpty() ? null : String.join(", ", missingFields);
    }

    public Event(Long id, String title, String description, Set<Category> categories, User organizer,
			Set<Event> enrolled_users) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.categories = categories;
		this.organizer = organizer;
		this.enrolled_users = enrolled_users;
	}
    
    public Event() {

	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Set<Category> getCategories() {
		return categories;
	}


	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}


	public User getOrganizer() {
		return organizer;
	}


	public void setOrganizer(User organizer) {
		this.organizer = organizer;
	}


	public Set<Event> getEnrolled_users() {
		return enrolled_users;
	}


	public void setEnrolled_users(Set<Event> enrolled_users) {
		this.enrolled_users = enrolled_users;
	}

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
