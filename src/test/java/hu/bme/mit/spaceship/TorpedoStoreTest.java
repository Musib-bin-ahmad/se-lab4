package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for TorpedoStore.
 *
 * The failure rate is always passed explicitly (0.0 = never fails, 1.0 = always fails),
 * so the tests are deterministic and independent of the IVT_RATE environment variable.
 */
class TorpedoStoreTest {

    @Test
    void fire_Success() {
        // Arrange
        TorpedoStore store = new TorpedoStore(1, 0.0);

        // Act
        boolean result = store.fire(1);

        // Assert
        assertTrue(result);
        assertEquals(0, store.getTorpedoCount());
    }

    @Test
    void fire_Failure_CountUnchanged() {
        // Arrange
        TorpedoStore store = new TorpedoStore(5, 1.0);

        // Act
        boolean result = store.fire(1);

        // Assert
        assertFalse(result);
        assertEquals(5, store.getTorpedoCount());
    }

    @Test
    void fire_MultipleTorpedoes_DecreasesCountByRequestedAmount() {
        // Arrange
        TorpedoStore store = new TorpedoStore(5, 0.0);

        // Act
        boolean result = store.fire(3);

        // Assert
        assertTrue(result);
        assertEquals(2, store.getTorpedoCount());
    }

    @Test
    void fire_AllTorpedoes_EmptiesStore() {
        // Arrange
        TorpedoStore store = new TorpedoStore(2, 0.0);

        // Act
        boolean result = store.fire(2);

        // Assert
        assertTrue(result);
        assertTrue(store.isEmpty());
    }

    @Test
    void fire_ZeroTorpedoes_ThrowsException() {
        // Arrange
        TorpedoStore store = new TorpedoStore(3, 0.0);

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> store.fire(0));
    }

    @Test
    void fire_MoreThanAvailable_ThrowsException() {
        // Arrange
        TorpedoStore store = new TorpedoStore(2, 0.0);

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> store.fire(3));
    }

    @Test
    void isEmpty_StoreWithTorpedoes_ReturnsFalse() {
        // Arrange
        TorpedoStore store = new TorpedoStore(1, 0.0);

        // Act + Assert
        assertFalse(store.isEmpty());
    }
}