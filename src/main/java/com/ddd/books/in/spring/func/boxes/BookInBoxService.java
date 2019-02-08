package com.ddd.books.in.spring.func.boxes;

import com.ddd.books.in.spring.func.exceptions.FunctionalException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BookInBoxService {
    private final BookInBoxRepository bookInBoxRepository;

    public BookInBoxService(BookInBoxRepository bookInBoxRepository) {
        this.bookInBoxRepository = bookInBoxRepository;
    }
    public BookInBox add(BookInBox book) throws FunctionalException {
        return this.bookInBoxRepository.add(book);
    }
    public List<BookInBox> getAll(){
        return this.bookInBoxRepository.getAll();
    }
    public List<BookInBox> getAllByBoxId(String id){
        return this.bookInBoxRepository.getAllByBoxId(id);
    }
    public void removeBookById(String id){
        this.bookInBoxRepository.removeBookById(id);
    }
    public BookInBox getBookById(String id){
        return this.bookInBoxRepository.getBookById(id);
    }
}
