package com.grupo5.huiapi.modules.user.entity;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.grupo5.huiapi.modules.category.entity.Category;
import com.grupo5.huiapi.modules.event.entity.Event;
import com.grupo5.huiapi.modules.user.modules.role.entity.Role;
import org.springframework.util.ObjectUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// JPA
@Entity @Table(name = "users")
// Lombok

//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User  {
    @Id
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1 )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String fullName;
    @Column()
    private String description;
    @Column()
    private String instagram;
    @Column()
    private String telegram;
    @Column()
    private String youtube;
    @Column()
    private String facebook;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "enrolled_events",
            joinColumns = {
                    @JoinColumn(name = "user_id", referencedColumnName = "id", updatable = false)
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "event_id", referencedColumnName = "id", updatable = false)
            }
    )
    @JsonIdentityReference(alwaysAsId = true)
    private Set<Event> enrolled_events = new HashSet<>();

    @OneToMany(mappedBy = "id", fetch = FetchType.LAZY, cascade = CascadeType.MERGE, orphanRemoval = true)
    @JsonIdentityReference(alwaysAsId = true)
    private Set<Event> organized_events = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "favorite_categories",
            joinColumns = {
                    @JoinColumn(name = "category_id", referencedColumnName = "id", updatable = false)
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "user_id", referencedColumnName = "id", updatable = false)
            }
    )
    //@JsonIdentityReference(alwaysAsId = false)
    private Set<Category> favorite_categories = new HashSet<>();


    public User(String username, String fullName, String instagram, String email, String telegram, String youtube, String facebook) {
        this.username = username;
        this.fullName = fullName;
        this.instagram = instagram;
        this.email = email;
        this.telegram = telegram;
        this.youtube = youtube;
        this.facebook = facebook;
    }

    // Constructor con los parámetros mínimos
    public User(String username, String password, String mail, String fullName) {
        this.password = password;
        this.fullName = fullName;
        this.email = mail;
        this.username = username;
    }
    public User(String username, String password, String mail, String fullName, Set<Category> favorite_categories) {
        this.password = password;
        this.fullName = fullName;
        this.email = mail;
        this.username = username;
        this.favorite_categories = favorite_categories;
    }
	public User(String username, String password, String mail, String fullName, String description, Set<Category> favorite_categories) {
		this.password = password;
		this.description = description;
		this.fullName = fullName;
		this.email = mail;
		this.username = username;
		this.favorite_categories = favorite_categories;
	}
    public String checkNullFields() {
        List<String> missingFields = new ArrayList<>();
        if(ObjectUtils.isEmpty(this.password))
            missingFields.add("password");
        if(ObjectUtils.isEmpty(this.email))
            missingFields.add("email");
        if(ObjectUtils.isEmpty(this.fullName))
            missingFields.add("fullname");
        if(ObjectUtils.isEmpty(this.username))
            missingFields.add("username");

        return missingFields.isEmpty() ? null : String.join(", ", missingFields);
    }

	public User(Long id, String username, String password, String email, String fullName, String description,
			String instagram, String telegram, String youtube, String facebook, Role role, Set<Event> enrolled_events,
			Set<Event> organized_events, Set<Category> favorite_categories) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.fullName = fullName;
		this.description = description;
		this.instagram = instagram;
		this.telegram = telegram;
		this.youtube = youtube;
		this.facebook = facebook;
		this.enrolled_events = enrolled_events;
		this.organized_events = organized_events;
		this.favorite_categories = favorite_categories;
	}
	
	public User() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getInstagram() {
		return instagram;
	}

	public void setInstagram(String instagram) {
		this.instagram = instagram;
	}

	public String getTelegram() {
		return telegram;
	}

	public void setTelegram(String telegram) {
		this.telegram = telegram;
	}

	public String getYoutube() {
		return youtube;
	}

	public void setYoutube(String youtube) {
		this.youtube = youtube;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public Set<Event> getEnrolled_events() {
		return enrolled_events;
	}

	public void setEnrolled_events(Set<Event> enrolled_events) {
		this.enrolled_events = enrolled_events;
	}

	public Set<Event> getOrganized_events() {
		return organized_events;
	}

	public void setOrganized_events(Set<Event> organized_events) {
		this.organized_events = organized_events;
	}

	public Set<Category> getFavorite_categories() {
		return favorite_categories;
	}

	public void setFavorite_categories(Set<Category> favorite_categories) {
		this.favorite_categories = favorite_categories;
	}
}
