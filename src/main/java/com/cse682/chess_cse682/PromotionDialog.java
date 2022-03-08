package com.cse682.chess_cse682;

import com.cse682.chess_cse682.piece.*;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class PromotionDialog extends Dialog<Piece> {

    private Piece selectedPiece;

    public PromotionDialog(Pawn pawn) {
        setTitle("Promote pawn " + pawn.getColor().name());
        setResultConverter(f -> selectedPiece);
        HBox hbox = new HBox();
        hbox.getChildren().add(new PromotionCandidateLabel(new Queen(pawn.getColor(), null)));
        hbox.getChildren().add(new PromotionCandidateLabel(new Knight(pawn.getColor(), null)));
        hbox.getChildren().add(new PromotionCandidateLabel(new Rook(pawn.getColor(), null)));
        hbox.getChildren().add(new PromotionCandidateLabel(new Bishop(pawn.getColor(), null)));
        getDialogPane().setContent(hbox);
    }

    private class PromotionCandidateLabel extends Label {

        Piece piece;

        PromotionCandidateLabel(Piece figure) {
            this.piece = figure;
            setGraphic(new ImageView(Piece.pieceIconCache.get(piece.getResourceFileName())));
            setOnMouseReleased(this::onMouseReleased);
        }

        private void onMouseReleased(MouseEvent e) {
            selectedPiece = piece;
            getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
            close();
            e.consume();
        }
    }

}
