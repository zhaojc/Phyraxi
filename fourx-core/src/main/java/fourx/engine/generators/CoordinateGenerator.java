package fourx.engine.generators;

import java.util.List;

import fourx.command.GameSettings;
import fourx.domain.Coordinates;

/**
 * 
 */
public interface CoordinateGenerator {

    List<Coordinates> generateStarSystemCoordinates(GameSettings gameSettings);
    
    /*
     * Non-uniform randomness:
     * - http://en.wikipedia.org/wiki/Ziggurat_algorithm
     */

}