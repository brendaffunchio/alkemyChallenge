package com.backend.disney.Exception;

public class ExceptionMessages {

    public static final String IMAGE_EMPTY = "The Image field is empty.";
    public static final String IMAGE_NULL = "The Image field is null";

    public static final String NAME_CHARACTER_EMPTY = "The Name is empty";
    public static final String NAME_CHARACTER_NULL = "The Name is null";
    public static final String CHARACTER_NOT_FOUND = "The Character was not found";
    public static final String CHARACTER_NULL = "The Character is null";
    public static final String STORY_CHARACTER_LENGTH="The story should not be longer than 255 characters";
    public static final String ID_CHARACTER_DELETE_NULL="  Cannot delete character without id";
    public static final String ID_CHARACTER_GET_DETAILS_NULL="Cannot get details character without id";
    public static final String CHARACTER_DELETED="The character was previously deleted, insert the id of a character that has not been deleted yet";

    public static final String ID_MOVIE_DELETE_NULL="Cannot delete movie without id";
    public static final String ID_MOVIE_GET_DETAILS_NULL="Cannot get details movie without id";
    public static final String TITLE_MOVIE_EMPTY = "The Title is empty";
    public static final String TITLE_MOVIE_NULL = "The Title is null";
    public static final String CREATION_DATE_MOVIE_NULL = "The Creation Date is empty";
    public static final String QUALIFICATION_MOVIE_BAD = "Rating must be between 1 and 5";
    public static final String MOVIE_NOT_FOUND = "The Movie was not found";
    public static final String MOVIE_NULL = "The Movie is null";
    public static final String ORDER_NULL = "The order is null";
    public static final String MOVIE_CHARACTER_CONTAINS = "The movie already contains the selected character";
    public static final String MOVIE_CHARACTER_NOT_CONTAINS = "The movie does not contains the selected character";

    public static final String ID_GENRE_NULL="The id of Genre is null";
    public static final String NAME_GENRE_EMPTY = "The Name of Genre is empty";
    public static final String NAME_GENRE_NULL = "The Name of Genre is null";
    public static final String GENRE_NULL = "The Genre is null";
    public static final String GENRE_EXISTS = "The Genre is already exists";

    public static final String USERNAME_EMPTY = "The Username is empty.";
    public static final String USERNAME_IN_USE = "The username entered is in use. ";
    public static final String PASSWORD_EMPTY = "The Password is empty. ";
    public static final String USER_NOT_FOUND = "A user with the entered username was not found.";
    public static final String USERNAME_PASSWORD_INCORRECT = "The username or password is incorrect";
    public static final String USER_NULL = "The User is null.";

    public static final String NAME_ROLE_EMPTY = "The Name is empty";
    public static final String ROLE_NOT_FOUND = "A role with the entered name was not found";
    public static final String ROLE_EXISTS="The role is already exists";


}

