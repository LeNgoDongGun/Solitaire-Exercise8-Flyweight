package ca.mcgill.solitaire.cards;

public class Exercise08 {
	public static void main(String[] args) {
		System.out.println("=== 1. KIEM TRA TINH DUY NHAT CUA FLYWEIGHT (SOLUTION 8) ===");
		Card8 card1 = Card8.get(Rank.ACE, Suit.CLUBS);
		Card8 card2 = Card8.get(Rank.ACE, Suit.CLUBS);
		System.out.println("Card 1: " + card1 + " ( identityHashCode: " + System.identityHashCode(card1) + " )");
		System.out.println("Card 2: " + card2 + " ( identityHashCode: " + System.identityHashCode(card2) + " )");
		System.out.println("Are they the same object? " + (card1 == card2));
		System.out.println();

		System.out.println("=== 2. SO SANH THOI GIAN LAY 52 LA BAI (1 LAN DUYET) ===");
		// Đo Lazy Flyweight (Card8)
		long startLazyOnce = System.currentTimeMillis();
		long startLazyNano = System.nanoTime();
		for (Suit suit : Suit.values()) {
			for (Rank rank : Rank.values()) {
				Card8.get(rank, suit);
			}
		}
		long timeLazyNano = System.nanoTime() - startLazyNano;
		long timeLazyOnce = System.currentTimeMillis() - startLazyOnce;

		// Đo Eager Flyweight (Card hiện có trong project)
		long startEagerOnce = System.currentTimeMillis();
		long startEagerNano = System.nanoTime();
		for (Suit suit : Suit.values()) {
			for (Rank rank : Rank.values()) {
				Card.get(rank, suit);
			}
		}
		long timeEagerNano = System.nanoTime() - startEagerNano;
		long timeEagerOnce = System.currentTimeMillis() - startEagerOnce;

		System.out.println("- Lazy Flyweight  (Card8.get) : " + timeLazyOnce + " ms (" + timeLazyNano + " ns)");
		System.out.println("- Eager Flyweight (Card.get)  : " + timeEagerOnce + " ms (" + timeEagerNano + " ns)");
		System.out.println();

		System.out.println("=== 3. DO TREN 500,000 LAN LAY 52 LA BAI BANG currentTimeMillis() ===");
		int iterations = 500_000;

		long startLazyLoop = System.currentTimeMillis();
		for (int i = 0; i < iterations; i++) {
			for (Suit suit : Suit.values()) {
				for (Rank rank : Rank.values()) {
					Card8.get(rank, suit);
				}
			}
		}
		long timeLazyLoop = System.currentTimeMillis() - startLazyLoop;

		long startEagerLoop = System.currentTimeMillis();
		for (int i = 0; i < iterations; i++) {
			for (Suit suit : Suit.values()) {
				for (Rank rank : Rank.values()) {
					Card.get(rank, suit);
				}
			}
		}
		long timeEagerLoop = System.currentTimeMillis() - startEagerLoop;

		System.out.println("- Lazy Flyweight  (Card8.get) : " + timeLazyLoop + " ms");
		System.out.println("- Eager Flyweight (Card.get)  : " + timeEagerLoop + " ms");
	}
}