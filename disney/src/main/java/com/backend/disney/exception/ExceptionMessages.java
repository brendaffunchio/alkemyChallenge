package com.backend.disney.exception;

public class ExceptionMessages {
    //CHARACTERS
    public static final String CHARACTER_NOT_FOUND = "The Character was not found";

    //MOVIES
    public static final String MOVIE_NOT_FOUND = "The Movie was not found";
    public static final String MOVIE_CHARACTER_CONTAINS = "The movie already contains the selected character";
    public static final String MOVIE_CHARACTER_NOT_CONTAINS = "The movie does not contains the selected character";

    //GENRE
    public static final String GENRE_NOT_FOUND = "The Genre was not found";
    public static final String GENRE_EXISTS = "The Genre is already exists";

    //USER
    public static final String USERNAME_IN_USE = "The username entered is in use. ";
    public static final String USER_NOT_FOUND = "A user with the entered username was not found.";

    //ROLE
    public static final String ROLE_NOT_FOUND = "A role with the entered name was not found";
    public static final String ROLE_EXISTS="The role is already exists";


}

