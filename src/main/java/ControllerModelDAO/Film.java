package ControllerModelDAO;

import javax.persistence.*;

/**
 * A Java class named Film is created to model the films entity in the database
 */
@Entity
@Table(name = "film")

public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;
    protected String title;
    protected String genre;
    protected int year;
    protected int price;

    public Film() {
    }

    public Film(int id) {
        this.id = id;
    }

    public Film(int id, String title, String genre, int price, int year ) {
        this(title, genre, year, price);
        this.id = id;
    }

    public Film(String title, String genre, int price,int year) {
        this.title = title;
        this.genre = genre;
        this.price = price;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

}