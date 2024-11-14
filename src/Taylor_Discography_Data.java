/*****
 * @file: Taylor_Discography_Data.java
 * @description: This class represents individual entries in Taylor Swift's discography.
 *               It includes attributes such as album name, track title, track number,
 *               release year, lyrics, label, and writers. The class implements Comparable
 *               to allow for ordering within the BST and includes methods for string
 *               representation and equality comparison.
 * @author: Aashi Patel
 * @date: September 26, 2024
 ****/

import java.util.Objects;

public class Taylor_Discography_Data implements Comparable<Taylor_Discography_Data> {
    private String album;
    private String track_title;
    private int track_number;
    private String released_year;
    private String lyrics;
    private String writers;
    private String spotifyId;

    // Default constructor
    public Taylor_Discography_Data() {
    }

    // Parameterized constructor
    public Taylor_Discography_Data(String album, String track_title, int track_number, String released_year, String lyrics, String writers, String spotifyId) {
        this.album = album;
        this.track_title = track_title;
        this.track_number = track_number;
        this.released_year = released_year;
        this.lyrics = lyrics;
        this.writers = writers;
        this.spotifyId = spotifyId;
    }

    // Copy constructor
    public Taylor_Discography_Data(Taylor_Discography_Data other) {
        this(other.album, other.track_title, other.track_number, other.released_year, other.lyrics, other.writers, other.spotifyId);
    }

    // Getters
    public String getSpotifyId() {
        return spotifyId;
    }
    public String getAlbum() {
        return album;
    }

    public String getTrackTitle() {
        return track_title;
    }

    public int getTrackNumber() {
        return track_number;
    }

    public String getReleasedYear() {
        return released_year;
    }

    @Override
    public String toString() { // toString method
        return String.format("Album: %s\nTrack: %s\nTrack Number: %d\nYear: %s\nWriters: %s\n",
                album, track_title, track_number, released_year, writers);
    }

    // equals method
    @Override
    public boolean equals(Object obj) {  // Check if the compared object is this object itself
        if (this == obj) return true;
        // Check if the compared object is null or of a different class
        if (obj == null || getClass() != obj.getClass()) return false;
        // Cast the compared object to Taylor_Discography_Data
        Taylor_Discography_Data that = (Taylor_Discography_Data) obj;
        // Compare all relevant fields for equality
        return track_number == that.track_number &&
                Objects.equals(album, that.album) &&
                Objects.equals(track_title, that.track_title) &&
                Objects.equals(released_year, that.released_year) &&
                Objects.equals(writers, that.writers);
    }

    // compareTo method
    public int compareTo(Taylor_Discography_Data other) {
        //compare by albums alphabetically
        int albumComparison = this.album.compareTo(other.album);
        //if  albums are same, return the result of that comparison
        if (albumComparison != 0) {
            return albumComparison;
        }
//        if (albumComparison == 0) {
//        return Integer.parseInt(other.album);
        return this.track_title.compareTo(other.track_title);
    }
}