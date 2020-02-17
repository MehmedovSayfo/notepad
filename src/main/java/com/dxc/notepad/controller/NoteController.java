package com.dxc.notepad.controller;

import com.dxc.notepad.model.Note;
import com.dxc.notepad.model.NoteModification;
import com.dxc.notepad.service.api.NoteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.UUID;

@Controller
@RequestMapping("/notepad")
public class NoteController {

    private NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/create")
    public ModelAndView showNoteCreationForm(ModelAndView modelAndView, Note note) {
        modelAndView.setViewName("write-note");
        modelAndView.addObject("note", note);
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView createNote(ModelAndView modelAndView,
                                   @Valid @ModelAttribute("note") Note note,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("write-note");
        } else {
            noteService.saveNote(note);
            redirectAttributes.addFlashAttribute("success", true);
            modelAndView.setViewName("redirect:/notepad/create");
        }
        return modelAndView;
    }

    @GetMapping("/view")
    public ModelAndView viewNotes(ModelAndView modelAndView, @PageableDefault(size = 9) Pageable pageable) {
        modelAndView.setViewName("view-notes");
        Page<Note> page = noteService.findAllNotes(pageable);
        modelAndView.addObject("page", page);
        return modelAndView;
    }

    @GetMapping("/archive")
    public ModelAndView viewArchivedNotes(ModelAndView modelAndView, @PageableDefault(size = 9) Pageable pageable) {
        modelAndView.setViewName("view-archive");
        Page<Note> page = noteService.findAllArchivedNotes(pageable);
        modelAndView.addObject("page", page);
        return modelAndView;
    }

    @GetMapping("/history/{id}")
    public ModelAndView viewNoteModification(ModelAndView modelAndView,
                                             @PathVariable("id") UUID id,
                                             RedirectAttributes redirectAttributes) {
        Note note = noteService.findSingleNote(id);
        if (note == null || note.getModificationHistory().isEmpty()) {
            modelAndView.setViewName("redirect:/notepad/view");
            redirectAttributes.addFlashAttribute("hasHistory", false);
        } else {
            modelAndView.setViewName("view-note-history");
            modelAndView.addObject("history", note.getModificationHistory());
        }
        return modelAndView;
    }

    @GetMapping("/archive/{id}")
    public ModelAndView archiveNote(ModelAndView modelAndView,
                                    @PathVariable("id") UUID id,
                                    RedirectAttributes redirectAttributes) {
        Note note = noteService.findSingleNote(id);
        boolean isDeleteSuccessful = false;
        if (note != null && note.isNotArchived()) {
            noteService.archiveNote(note);
            isDeleteSuccessful = true;
        }
        redirectAttributes.addFlashAttribute("successDelete", isDeleteSuccessful);
        modelAndView.setViewName("redirect:/notepad/view");
        return modelAndView;
    }

    @GetMapping("/update/{id}")
    public ModelAndView viewNoteUpdateForm(ModelAndView modelAndView,
                                           @PathVariable("id") UUID id,
                                           NoteModification modification,
                                           RedirectAttributes redirectAttributes) {
        Note note = noteService.findSingleNote(id);
        if (note != null && note.isNotArchived()) {
            modelAndView.setViewName("update-note");
            modelAndView.addObject("noteId", id);
            modelAndView.addObject("modification", modification);
        } else {
            modelAndView.setViewName("redirect:/notepad/view");
            redirectAttributes.addFlashAttribute("successUpdate", false);
        }
        return modelAndView;
    }

    @PostMapping("/update/{id}")
    public ModelAndView updateNote(ModelAndView modelAndView, @PathVariable("id") UUID id,
                                   @Valid @ModelAttribute("modification") NoteModification noteModification,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("update-note");
            modelAndView.addObject("noteId", id);
        } else {
            Note note = noteService.findSingleNote(id);
            noteService.updateNote(note, noteModification);
            redirectAttributes.addFlashAttribute("successUpdate", true);
            modelAndView.setViewName("redirect:/notepad/view");
        }
        return modelAndView;
    }
}
