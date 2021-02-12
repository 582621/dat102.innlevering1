package no.hvl.dat102.adt;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import no.hvl.dat102.exception.EmptyCollectionException;
import no.hvl.dat102.adt.KoeADT;

public abstract class KoeADTTest {

	private KoeADT<Integer> koe;
	
	private Integer e0 = 1;
	private Integer e1 = 2;
	private Integer e2 = 3;
	private Integer e3 = 4;
	private Integer e4 = 5;
	
	protected abstract KoeADT<Integer> reset();

	/**
	 * Hent en ny kø for hver test.
	 * 
	 * @return
	 */
	@BeforeEach
	public void setup() {
		koe = reset();
	}

	/**
	 * Test på at en ny kø er tom.
	 */
	@Test
	public void nyStabelErTom() {
		assertTrue(koe.erTom());
	}

	/**
	 * Test på ut og inn.
	 */
	@Test
	public void utOgInn() {

		koe.innKoe(e0);
		koe.innKoe(e1);
		koe.innKoe(e2);
		koe.innKoe(e3);

		try {
			assertEquals(e3, koe.utKoe());
			assertEquals(e2, koe.utKoe());
			assertEquals(e1, koe.utKoe());
			assertEquals(e0, koe.utKoe());
		} catch (EmptyCollectionException e) {
			fail("Sletting feilet uventet " + e.getMessage());
		}
	}

	/**
	 * Test på ut og inn med duplikate verdier.
	 */
	@Test
	public void utOgInnMedDuplikater() {

		koe.innKoe(e0);
		koe.innKoe(e1);
		koe.innKoe(e1);
		koe.innKoe(e2);

		try {
			assertEquals(e2, koe.utKoe());
			assertEquals(e1, koe.utKoe());
			assertEquals(e1, koe.utKoe());
			assertEquals(e0, koe.utKoe());
		} catch (EmptyCollectionException e) {
			fail("Sletting feilet uventet " + e.getMessage());
		}
	}

	/**
	 * Testing på foerste.
	 */
	@Test
	public void InnUtInnInnUtTest() {
		try {
			koe.innKoe(e2);
			koe.utKoe();
			koe.innKoe(e3);
			koe.innKoe(e4);
			koe.utKoe();
			assertEquals(e3, koe.foerste());

		} catch (EmptyCollectionException e) {
			fail("Ut eller foerste feilet uventet " + e.getMessage());
		}
	}

	/**
	 * Test på at en kø med noen elementer ikke er tom.
	 */
	@Test
	public final void erIkkeTom() {
		koe.innKoe(e0);
		koe.innKoe(e1);
		assertFalse(koe.erTom());
	}

	/**
	 * Test på at en kø med null elementer er tom.
	 */
	@Test
	public void pushPopErTom() {
		try {
			koe.innKoe(e0);
			koe.utKoe();
			assertTrue(koe.erTom());

		} catch (EmptyCollectionException e) {
			fail("Inn eller ut feilet uventet " + e.getMessage());
		}
	}

	/**
	 * Test på størrelsen
	 */
	@Test
	public void stor() {
		koe.innKoe(e0);
		koe.innKoe(e1);
		koe.innKoe(e2);
		assertEquals(3, koe.antall());
	}

	/**
	 * Forsøk på utKoe av en tom kø skal gi "underflow excepton" *
	 * 
	 * @throws EmptyCollectionException expected exception
	 */
	@Test
	public void utFromEmptyIsUnderflowed() {
		/*
		 * Assertions.assertThrows(EmptyCollectionException.class, new Executable() {
		 * 
		 * @Override public void execute() throws Throwable { koe.utKoe(); } });
		 */

		Assertions.assertThrows(EmptyCollectionException.class, () -> {
			koe.utKoe();
		});
	}
}
