package com.cse682.chess_cse682;

import com.cse682.chess_cse682.piece.*;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

/**
 * Dialog for handling the promotion of pawns that reach the end
 * of the board.
 */
public class PromotionDialog extends Dialog<Piece> {

    /**
     * Pawn being promoted.
     */
    private Piece selectedPiece;

    /**
     * Parameterized constructor for the PromotionDialog class.
     * @param pawn Pawn to be promoted.
     */
    public PromotionDialog(Pawn pawn) {
        setTitle("Promote pawn " + pawn.getColor().prettyName());
        setResultConverter(f -> selectedPiece);
        HBox hbox = new HBox();
        hbox.getChildren().add(new PromotionCandidateLabel(new Queen(pawn.getColor(), null)));
        hbox.getChildren().add(new PromotionCandidateLabel(new Knight(pawn.getColor(), null)));
        hbox.getChildren().add(new PromotionCandidateLabel(new Rook(pawn.getColor(), null)));
        hbox.getChildren().add(new PromotionCandidateLabel(new Bishop(pawn.getColor(), null)));
        getDialogPane().setContent(hbox);
    }

    /**
     * Inner class used to render the promotion piece candidates in the dialog window.
     */
    private class PromotionCandidateLabel extends Label {

        /**
         * Promotion candidate piece type.
         */
        Piece piece;

        /**
         * Parameterized constructor for the PromotionCandidateLabel class.
         * @param figure Piece representing the promoted piece selection candidate.
         */
        PromotionCandidateLabel(Piece figure) {
            this.piece = figure;
            setGraphic(new ImageView(Piece.pieceIconCache.get(piece.getResourceFileName())));
            setOnMouseReleased(this::onMouseReleased);
        }

        /**
         * Handler method for when the player selects the piece to promote to.
         * @param e MouseEvent instance.
         */
        private void onMouseReleased(MouseEvent e) {
            // Set the selected piece to be placed into the board.
            selectedPiece = piece;
            getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
            close();
            e.consume();
        }
    }

}
