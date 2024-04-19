package com.example.ms1.note.note.note;

import com.example.ms1.note.note.notebook.Notebook;
import com.example.ms1.note.note.notebook.NotebookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class NoteController {

    private final NoteRepository noteRepository;
    private final NotebookRepository notebookRepository;

    @RequestMapping("/")
    public String main(Model model) {

        List<Notebook> notebookList = notebookRepository.findAll();

        if(notebookList.isEmpty()) {
            Notebook notebook = new Notebook();
            notebook.setName("새노트");
            notebookRepository.save(notebook);

            return "redirect:/";
        }
        Notebook targetNotebook = notebookList.get(0);

        //1. DB에서 데이터 꺼내오기
        List<Note> noteList = noteRepository.findAll();

        if(noteList.isEmpty()) {
            saveDefault(targetNotebook);
            return "redirect:/";
        }

        //2. 꺼내온 데이터를 템플릿으로 보내기
        model.addAttribute("noteList", noteList);
        model.addAttribute("targetNote", noteList.get(0));
        model.addAttribute("notebookList", notebookList);
        model.addAttribute("targetNotebook", targetNotebook);

        return "main";
    }

    @PostMapping("/books/{notebookId}/write")
    public String write(@PathVariable("notebookId") Long notebookId) {
        Notebook notebook = notebookRepository.findById(notebookId).orElseThrow();
        saveDefault(notebook);

        return "redirect:/";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable Long id) {
        Note note = noteRepository.findById(id).get();
        model.addAttribute("targetNote", note);
        model.addAttribute("noteList", noteRepository.findAll());

        return "main";
    }
    @PostMapping("/update")
    public String update(Long id, String title, String content) {
        Note note = noteRepository.findById(id).get();

        if(title.trim().length() == 0) {
            title = "제목 없음";
        }

        note.setTitle(title);
        note.setContent(content);

        noteRepository.save(note);
        return "redirect:/detail/" + id;
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {

        noteRepository.deleteById(id);

        return "redirect:/";
    }

    public Note saveDefault(Notebook notebook) {
        Note note = new Note();
        note.setTitle("new title..");
        note.setContent("");
        note.setCreateDate(LocalDateTime.now());
        note.setNotebook(notebook);

        return this.noteRepository.save(note);
    }
}
