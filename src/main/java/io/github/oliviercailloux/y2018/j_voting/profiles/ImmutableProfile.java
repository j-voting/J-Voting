package io.github.oliviercailloux.y2018.j_voting.profiles;

import io.github.oliviercailloux.y2018.j_voting.*;
import java.util.*;
import org.slf4j.*;

/**
 * This class is immutable.
 * Represents a Complete Profile.
 */
public class ImmutableProfile extends ImmutableProfileI implements Profile{

	private static final Logger LOGGER = LoggerFactory.getLogger(ImmutableProfile.class.getName());
	
	public ImmutableProfile(Map<Voter, Preference> votes) {
		super(votes);
	}

	@Override
	public int getNbAlternatives() {
		LOGGER.debug("getNbAlternatives:");
		return getAlternatives().size();
	}

	@Override
	public Set<Alternative> getAlternatives() {
		LOGGER.debug("getAlternatives:");
		Preference p = votes.values().iterator().next();
		return Preference.toAlternativeSet(p.getPreferencesNonStrict());
	}
}
