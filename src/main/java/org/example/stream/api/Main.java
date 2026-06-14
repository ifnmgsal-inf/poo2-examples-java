/******************************************************************************
 Stream API example
 @author leonardosilva

 *******************************************************************************/
package org.example.stream.api;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Stream;

public class Main
{
    public static void main(String[] args) {
        ArrayList<Book> books = populateLibrary();
        /*
        books.stream()
                .filter((book) -> book.getAuthor().startsWith("J"))
                .filter((book) -> book.getTitle().startsWith("E"))
                .forEach(System.out::println);
        */
        // The average number of pages of "Jane Austen" books

        /*
        var array = Stream
                .generate(() -> new Random().nextInt(100)).limit(10).toArray();
        for(Object i : array) {
            System.out.println(i);
        }
        */

    }

    private static ArrayList<Book> populateLibrary() {
        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book("Alice Walker", "The Color Purple", 200));
        books.add(new Book("Alice Walker", "Meridian", 120));
        books.add(new Book("Toni Morrison", "Beloved", 230));
        books.add(new Book("Toni Morrison", "Jazz", 200));
        books.add(new Book("Toni Morrison", "Paradise", 30));
        books.add(new Book("John Steinbeck", "The Grapes of Wrath", 200));
        books.add(new Book("John Steinbeck", "Eats of Eden", 1200));
        books.add(new Book("Kazuo Ishiguro", "The Remains of The Day", 340));
        books.add(new Book("Kazuo Ishiguro", "Never Let Me Go", 203));
        books.add(new Book("Kazuo Ishiguro", "The Buried Giant", 220));
        books.add(new Book("Jane Austen", "Pride and Prejudice", 700));
        books.add(new Book("Jane Austen", "Emma", 200));
        books.add(new Book("Jane Austen", "Persuasion", 400));
        books.add(new Book("Jane Austen", "Mansfield Park", 300));
        books.add(new Book("Chinua Achebe", "Things Fall Apart", 230));
        books.add(new Book("Chinua Achebe", "No Longer at Ease", 320));
        books.add(new Book("Chinua Achebe", "Home and Exile", 299));
        return books;
    }
}
