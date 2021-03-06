package io.github.oliviercailloux.j_voting.preferences.analysis;

import static io.github.oliviercailloux.j_voting.AlternativeHelper.a1;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a2;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a3;
import static io.github.oliviercailloux.j_voting.AlternativeHelper.a4;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.Voter;
import io.github.oliviercailloux.j_voting.exceptions.DuplicateValueException;
import io.github.oliviercailloux.j_voting.exceptions.EmptySetException;
import io.github.oliviercailloux.j_voting.preferences.classes.LinearPreferenceImpl;
import io.github.oliviercailloux.j_voting.preferences.interfaces.LinearPreference;

class FrenchElectionTest {

    private static LinearPreference getLinearPref(Voter voter,
                    Alternative... alternatives)
                    throws EmptySetException, DuplicateValueException {
        return LinearPreferenceImpl.asLinearPreference(voter,
                        ImmutableList.copyOf(alternatives));
    }

    @Test
    void testWinner() throws EmptySetException, DuplicateValueException {
        FrenchElection toTest = FrenchElection.asFrenchElection(ImmutableSet.of(
                        getLinearPref(Voter.createVoter(1), a1, a3, a2),
                        getLinearPref(Voter.createVoter(2), a2, a3),
                        getLinearPref(Voter.createVoter(3), a3, a1, a2),
                        getLinearPref(Voter.createVoter(4), a2, a3, a1)));
        assertEquals(ImmutableSet.of(a2), toTest.getWinners());
    }

    @Test
    void testTwoWinner() throws EmptySetException, DuplicateValueException {
        FrenchElection toTest = FrenchElection.asFrenchElection(ImmutableSet.of(
                        getLinearPref(Voter.createVoter(1), a1, a3, a2),
                        getLinearPref(Voter.createVoter(2), a2, a3),
                        getLinearPref(Voter.createVoter(3), a1, a3, a2),
                        getLinearPref(Voter.createVoter(4), a2, a3, a1)));
        assertEquals(ImmutableSet.of(a2, a1), toTest.getWinners());
    }

    @Test
    void testException() throws EmptySetException, DuplicateValueException {
        assertThrows(IllegalArgumentException.class,
                        () -> FrenchElection.asFrenchElection(ImmutableSet.of(
                                        getLinearPref(Voter.createVoter(1), a1,
                                                        a3, a2),
                                        getLinearPref(Voter.createVoter(1), a2,
                                                        a3, a1))));
    }

    @Test
    void testAlternatives() throws EmptySetException, DuplicateValueException {
        FrenchElection toTest = FrenchElection.asFrenchElection(ImmutableSet.of(
                        getLinearPref(Voter.createVoter(1), a1),
                        getLinearPref(Voter.createVoter(2), a4, a3),
                        getLinearPref(Voter.createVoter(3), a3, a1, a2)));
        assertEquals(ImmutableSet.of(a1, a2, a3, a4), toTest.getAlternatives());
    }

    @Test
    void testVoters() throws EmptySetException, DuplicateValueException {
        FrenchElection toTest = FrenchElection.asFrenchElection(ImmutableSet.of(
                        getLinearPref(Voter.createVoter(1), a1),
                        getLinearPref(Voter.createVoter(2), a4, a3),
                        getLinearPref(Voter.createVoter(3), a3, a1, a2)));
        assertEquals(ImmutableSet.of(Voter.createVoter(2), Voter.createVoter(1),
                        Voter.createVoter(3)), toTest.getVoters());
    }
}
