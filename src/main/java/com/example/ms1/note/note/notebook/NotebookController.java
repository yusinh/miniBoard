package com.example.ms1.note.note.notebook;

import com.example.ms1.note.note.note.Note;
import com.example.ms1.note.note.note.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class NotebookController {
    private final NotebookService notebookService;
    private final NoteService noteService;


    @PostMapping("/books/write")
    public String write() {

        notebookService.saveDefault();

        return "redirect:/";
    }
    @GetMapping("/books/{id}")
    public String detail(@PathVariable("id") Long id) {
        Notebook notebook = notebookService.getNotebook(id);
        Note note = notebook.getNoteList().get(0);

        return "redirect:/books/%d/notes/%d".formatted(id, note.getId());
    }

    @PostMapping("/groups/{notebookId}/books/write")
    public String groupWrite(@PathVariable("notebookId") Long notebookId) {

        Notebook parent = notebookService.getNotebook(notebookId);

        Notebook child = new Notebook();
        child.setName("새노트북");

        Note note = noteService.saveDefault();
        child.addNote(note);

        notebookService.save(child);

        parent.addChild(child);
        notebookService.save(parent);

        return "redirect:/";
    }
}
