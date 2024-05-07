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
    private final NotebookRepository notebookRepository;
    private final NoteService noteService;

    @PostMapping("/books/write")
    public String write() {
        Notebook notebook = new Notebook();
        notebook.setName("새노트북");

        notebookRepository.save(notebook);
        noteService.saveDefault(notebook);

        return "redirect:/";
    }
    @GetMapping("/books/{id}")
    public String detail(@PathVariable("id") Long id) {
        Notebook notebook = notebookRepository.findById(id).orElseThrow();
        Note note = notebook.getNoteList().get(0);

        return "redirect:/books/%d/notes/%d".formatted(id, note.getId());
    }

    @PostMapping("/groups/{notebookId}/books/write")
    public String groupWrite(@PathVariable("notebookId") Long notebookId) {
        Notebook parent = notebookRepository.findById(notebookId).orElseThrow();

        Notebook child = new Notebook();
        child.setName("새노트북");
        notebookRepository.save(child);

        parent.addChild(child);
        notebookRepository.save(parent);

        return "redirect:/";
    }
}
