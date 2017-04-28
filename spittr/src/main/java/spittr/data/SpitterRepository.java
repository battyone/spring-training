package spittr.data;

import spittr.model.Spitter;
import spittr.model.Spittle;

/**
 * Created on 28.04.2017.
 * Deal with {@link Spitter}
 * @author Artemis A. Sirosh
 */
public interface SpitterRepository {

    /**
     * Saves new Spitter object
     * @param spitter object, which should be saved
     * @return saved Spitter
     */
    Spitter save(Spitter spitter);

    /**
     * Finds spitter by himself username
     * @param username spitter's username
     * @return founded spitter
     */
    Spitter findByUsername(String username);
}
