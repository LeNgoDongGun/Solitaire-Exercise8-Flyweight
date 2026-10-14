package ca.mcgill.solitaire.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
	public enum DeckConfig {
		RANDOM,
		EASY_WIN,
		REVERSE_WIN
	}

	private final DeckConfig aConfig;
	private final List<Card> aCards = new ArrayList<>();

	public Deck() {
		this(DeckConfig.RANDOM);
	}

	public Deck(DeckConfig pConfig) {
		aConfig = pConfig;
		resetDeck();
	}

	public void shuffle() {
		if (aConfig == DeckConfig.RANDOM) {
			Collections.shuffle(aCards);
		} else {
			resetDeck();
		}
	}

	private void resetDeck() {
		aCards.clear();
		switch (aConfig) {
			case EASY_WIN:
				buildEasyWinDeck();
				break;
			case REVERSE_WIN:
				buildReverseWinDeck();
				break;
			case RANDOM:
			default:
				buildStandardDeck();
				Collections.shuffle(aCards);
				break;
		}
	}

	private void buildStandardDeck() {
		for (Suit suit : Suit.values()) {
			for (Rank rank : Rank.values()) {
				aCards.add(Card.get(rank, suit));
			}
		}
	}

	private void buildEasyWinDeck() {
		List<Card> aces = new ArrayList<>();
		for (Suit suit : Suit.values()) {
			aces.add(Card.get(Rank.ACE, suit));
		}

		List<Card> remaining = new ArrayList<>();
		for (Rank rank : Rank.values()) {
			if (rank == Rank.ACE) continue;
			for (Suit suit : Suit.values()) {
				remaining.add(Card.get(rank, suit));
			}
		}

		for (Card card : remaining) {
			aCards.add(card);
		}

		int aceIndex = 0;
		for (int drawCount = 1; drawCount <= 28; drawCount++) {
			if ((drawCount == 1 || drawCount == 3 || drawCount == 6 || drawCount == 10) && aceIndex < aces.size()) {
				aCards.add(aces.get(aceIndex++));
			}
		}

		while (aCards.size() < 52) {
			aCards.add(0, Card.get(Rank.KING, Suit.SPADES));
		}
	}

	private void buildReverseWinDeck() {
		buildStandardDeck();
		Collections.reverse(aCards);
	}

	public Card draw() {
		assert !isEmpty();
		return aCards.remove(aCards.size() - 1);
	}

	public void push(Card pCard) {
		assert pCard != null;
		aCards.add(pCard);
	}

	public boolean isEmpty() {
		return aCards.isEmpty();
	}
}