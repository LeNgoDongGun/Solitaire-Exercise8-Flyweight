package ca.mcgill.solitaire.cards;

import java.util.IdentityHashMap;
import java.util.Map;

/**
 * Exercise 8: Lazy Flyweight implementation of a playing card.
 * Thay vì tạo sẵn 52 lá bài trong khối static như Card.java (Eager),
 * lớp Card8 chỉ khởi tạo đối tượng lá bài khi phương thức get() được gọi lần đầu.
 */
public final class Card8 {
	private static final Map<Rank, Map<Suit, Card8>> CARDS = new IdentityHashMap<>();

	private final Rank aRank;
	private final Suit aSuit;

	public static Card8 get(Rank pRank, Suit pSuit) {
		assert pRank != null && pSuit != null;

		Map<Suit, Card8> innerMap = CARDS.getOrDefault(pRank, new IdentityHashMap<>());
		CARDS.putIfAbsent(pRank, innerMap);
		if (!innerMap.containsKey(pSuit)) {
			innerMap.put(pSuit, new Card8(pRank, pSuit));
		}
		return innerMap.get(pSuit);
	}

	private Card8(Rank pRank, Suit pSuit) {
		assert pRank != null && pSuit != null;
		aRank = pRank;
		aSuit = pSuit;
	}

	public Rank rank() {
		return aRank;
	}

	public Suit suit() {
		return aSuit;
	}

	@Override
	public String toString() {
		return aRank + " of " + aSuit;
	}
}