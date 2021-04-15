package com.library.utilities;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.model.Book;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class JSONmapper {
    public static List<Book> myListOfBooks = getBooks();

    public static List<Book> getBooks() {
        List<Book> booksList = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            Path path = Paths.get("src/main/resources/file.json");
            BufferedReader reader = Files.newBufferedReader(path);
            TypeReference<List<Book>> listReference = new TypeReference<List<Book>>() {
            };
            booksList = mapper.readValue(reader, listReference);

            reader.close();

        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
        } catch (JsonParseException ex) {
            System.out.println("Something Went Wrong retrieving file ");
        } catch (JsonMappingException ex) {
            System.out.println("Something Went Wrong retrieving file ");
        } catch (IOException ex) {
            System.out.println("Something Went Wrong retrieving file ");
        }
        return booksList;

    }


}
