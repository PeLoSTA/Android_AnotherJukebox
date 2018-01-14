package de.peterloos.anotherjukebox.dtos;

/**
 * Created by loospete on 14.01.2018.
 */

public class ArtistDTO {

    private String name;

    public ArtistDTO() {

        // default constructor required for calls to DataSnapshot.getValue(ArtistDTO.class)
        this.name = "unknown";
    }

    public String getName() {
        return this.name;
    }

    public void setName(String title) {
        this.name = title;
    }

    @Override
    public String toString () {
        return String.format("ArtistDTO: Name: %s", this.name);
    }
}
