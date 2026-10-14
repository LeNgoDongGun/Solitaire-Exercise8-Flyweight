package ca.mcgill.solitaire.gui;

import java.util.List;
import ca.mcgill.solitaire.auto.GreedyPlayingStrategy;
import ca.mcgill.solitaire.cards.Deck;
import ca.mcgill.solitaire.cards.Deck.DeckConfig; // Thêm import DeckConfig
import ca.mcgill.solitaire.model.FoundationPile;
import ca.mcgill.solitaire.model.GameModel;
import ca.mcgill.solitaire.model.TableauPile;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Solitaire extends Application {
	private static final int WIDTH = 680;
	private static final int HEIGHT = 500;
	private static final int MARGIN_OUTER = 10;
	private static final String TITLE = "Solitaire";
	private static final String VERSION = "1.3";

	public Solitaire() {}

	public static void main(String[] pArgs) {
		launch(pArgs);
	}

	@Override
	public void start(Stage pPrimaryStage) {
		DeckConfig config = DeckConfig.RANDOM;
		List<String> rawArgs = getParameters().getRaw();
		if (!rawArgs.isEmpty()) {
			String arg = rawArgs.get(0).trim().toUpperCase();
			if (arg.equals("1") || arg.equals("EASY_WIN") || arg.equals("EASY")) {
				config = DeckConfig.EASY_WIN;
			} else if (arg.equals("2") || arg.equals("REVERSE_WIN")) {
				config = DeckConfig.REVERSE_WIN;
			}
		}

		pPrimaryStage.setTitle(TITLE + " " + VERSION + " [" + config + "]");

		GridPane root = new GridPane();
		root.setStyle("-fx-background-color: green;");
		root.setHgap(MARGIN_OUTER);
		root.setVgap(MARGIN_OUTER);
		root.setPadding(new Insets(MARGIN_OUTER));

		final GameModel model = GameModel.instance();
		DeckView deckView = new DeckView(model);
		DiscardPileView discardPileView = new DiscardPileView(model);

		root.add(deckView, 0, 0);
		root.add(discardPileView, 1, 0);

		for (FoundationPile index : FoundationPile.values()) {
			root.add(new SuitStack(model, index), 3 + index.ordinal(), 0);
		}

		for (TableauPile index : TableauPile.values()) {
			root.add(new CardPileView(model, index), index.ordinal(), 1);
		}

		root.setOnKeyTyped(new EventHandler<KeyEvent>() {
			@Override
			public void handle(final KeyEvent pEvent) {
				if (pEvent.getCharacter().equals("\r")) {
					model.tryToAutoPlay();
				}
				else if (pEvent.getCharacter().equals("\b")) {
					model.undoLast();
				}
				pEvent.consume();
			}
		});

		pPrimaryStage.setResizable(false);
		pPrimaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
		pPrimaryStage.show();
	}
}