package com.example.ms1.note.note.notebook;

import com.example.ms1.note.note.note.Note;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Notebook { // 그룹 노트북 관점, 일반 노트북 관점

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    private Notebook parent;

    @OneToMany(mappedBy = "notebook")
    List<Note> noteList = new ArrayList<>();

    @OneToMany(mappedBy = "parent")
    List<Notebook> children = new ArrayList<>();

    public void addChild(Notebook child) {
        child.setParent(this); // 자식쪽에서 부모 설정
        children.add(child); // 부모쪽에서 자식 목록에 자식 추가설정
    }

    public void addNote(Note note) {
        note.setNotebook(this);
        noteList.add(note);
    }
}



