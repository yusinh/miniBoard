package com.example.ms1.note.note.notebook;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NotebookController {

    @PostMapping("/books/write")
    public String write() {
        return null;
    }
}
