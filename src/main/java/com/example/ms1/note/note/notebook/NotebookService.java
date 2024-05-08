package com.example.ms1.note.note.notebook;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotebookService {

    private final NotebookRepository notebookRepository;

    public Notebook getNotebook(Long notebookId) {
        return notebookRepository.findById(notebookId).orElseThrow();
    }

    public void save(Notebook notebook) {
        notebookRepository.save(notebook);
    }

    public List<Notebook> getNotebookList() {
        return notebookRepository.findAll();
    }
}
