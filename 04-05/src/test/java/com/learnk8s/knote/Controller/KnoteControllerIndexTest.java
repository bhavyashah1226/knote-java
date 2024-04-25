package com.learnk8s.knote.Controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class KnoteControllerIndexTest {

	@InjectMocks
	KnoteController knoteController;

	@Mock
	Model model;

	@Mock
	NotesRepository notesRepository;

	@Before
    public void setup() {
        when(notesRepository.findAll()).thenReturn(new ArrayList<>());
    }

	@Test
	public void testSuccessfulRetrievalOfAllNotes() {
		List<Note> expectedNotes = new ArrayList<>();
		expectedNotes.add(new Note());
		expectedNotes.add(new Note());

		when(notesRepository.findAll()).thenReturn(expectedNotes);
		ResponseEntity<List<Note>> response = knoteController.index(model);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(expectedNotes, response.getBody());
	}

	@Test
	public void testEmptyListReturnWhenNoNotes() {
		ResponseEntity<List<Note>> response = knoteController.index(model);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(0, response.getBody().size());
	}

	@Test(expected = NullPointerException.class)
	public void testExceptionHandlingWhenModelIsNull() {
		knoteController.index(null);
	}

}
