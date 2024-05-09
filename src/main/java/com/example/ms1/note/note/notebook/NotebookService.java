package com.example.ms1.note.note.notebook;

import com.example.ms1.note.note.note.Note;
import com.example.ms1.note.note.note.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotebookService {

    private final NotebookRepository notebookRepository;
    private final NoteService noteService;


    public Notebook getNotebook(Long notebookId) {
        return notebookRepository.findById(notebookId).orElseThrow();
    }

    public void save(Notebook notebook) {
        notebookRepository.save(notebook);
    }

    public List<Notebook> getNotebookList() {
        return notebookRepository.findAll();
    }

    public void saveDefault() {
        Notebook notebook = new Notebook();
        notebook.setName("새노트북");

        notebookRepository.save(notebook);
        Note note = noteService.saveDefault();
        notebook.addNote(note);
    }
}
