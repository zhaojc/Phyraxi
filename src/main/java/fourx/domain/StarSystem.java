package fourx.domain;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A star system consisting of a star or stars, and possibly planetary bodies
 * orbiting it or them.
 */
public class StarSystem {
    
    private StarHierarchy stars;
    
    static class StarIterator implements Iterator<Star> {
	
	private StarHierarchy currentHierarchy;
	private Star currentStar;
	
	StarIterator(StarHierarchy starHierarchy) {
	    this.currentHierarchy = starHierarchy;
	    this.currentStar = null;
	}
	
	@Override
	public boolean hasNext() {
	    if (currentStar == null) {
		return true;
	    }
	    if (currentStar == currentHierarchy.centralStar) {
		if (currentHierarchy.binaryPair != null)  {
		    return true;
		}
	    }
	    return currentHierarchy.companionComponent != null;
	}

	@Override
	public Star next() {
	    Star next = null;
	    if (currentStar == null) {
		next = currentHierarchy.centralStar;
	    } else if (currentStar == currentHierarchy.centralStar) {
		if (currentHierarchy.binaryPair != null)  {
		    next = currentHierarchy.binaryPair;
		}
	    }
	    if (next == null) {
		if (currentHierarchy.companionComponent == null) {
		    throw new NoSuchElementException();
		}
		currentHierarchy = currentHierarchy.companionComponent;
		next = currentHierarchy.centralStar;
	    }
	    currentStar = next;
	    return next;
	}

	@Override
	public void remove() {
	    throw new UnsupportedOperationException("No removal permitted!");
	}
	
    }
    
    static class StarHierarchy {
	
	private Star centralStar;
	private Star binaryPair;
	private StarHierarchy companionComponent;
	
	private StarHierarchy(Star centralStar, Star binaryPair, StarHierarchy companionComponent) {
	    this.centralStar = centralStar;
	    this.binaryPair = binaryPair;
	    this.companionComponent = companionComponent;
	}
	
	boolean isSingle() {
	    return (binaryPair == null && companionComponent == null);
	}
	
	boolean isBinary() {
	    return (binaryPair != null && companionComponent == null);
	}
	
	boolean hasCompanion() {
	    return (companionComponent != null);
	}
	
	Star getCentralStar() {
	    return centralStar;
	}
	
	Star getBinaryPair() {
	    return binaryPair;
	}
	
	StarHierarchy getCompanionComponent() {
	    return companionComponent;
	}
	
	Iterator<Star> starIterator() {
	    return new StarIterator(this);
	}
	
	static StarHierarchy createSingleStar(Star star) {
	    return new StarHierarchy(star, null, null);
	}
	
	static StarHierarchy createBinaryPair(Star centralStar, Star binaryPair) {
	    return new StarHierarchy(centralStar, binaryPair, null);
	}
	
	static StarHierarchy createDistantPair(Star centralStar, Star companion) {
	    return new StarHierarchy(centralStar, null, createSingleStar(companion));
	}
	
	static StarHierarchy createWithCompanion(StarHierarchy centralComponent, StarHierarchy companionComponent) {
	    return new StarHierarchy(centralComponent.centralStar, centralComponent.binaryPair, companionComponent);
	}
    }

}
