package main;

import pieces.Giraffe;
import pieces.Piece;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Input extends MouseAdapter {
    Board board;
    Piece p;

    public Input(Piece p) {
        this.p = p;
    }

    public Input(Board board) {
        this.board = board;
    }

    @Override
    public void mousePressed(MouseEvent e) {//Нажатый курсор мыши
        //Функция mousePressed() вызывается, когда вы нажимаете кнопку мыши.
        int col = e.getX() / board.tileSize;
        int row = e.getY() / board.tileSize;

        Piece pieceXY = board.getPiece(col, row);
            if (pieceXY != null) {
                board.selectedPiece = pieceXY;
            }
    }

    @Override
    public void mouseDragged(MouseEvent e) {//Перетаскиваемый мышью
        //Функция mouseDragged() активируется, когда вы нажимаете кнопку мыши и двигаете мышью, пока кнопка нажата.
        if (board.selectedPiece != null) {
            board.selectedPiece.xPos = e.getX() - board.tileSize / 2;
            board.selectedPiece.yPos = e.getY() - board.tileSize / 2;

            board.repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {//Освобожденный от мыши
        //Функция mouseReleased() вызывается, когда вы отпускаете кнопку мыши.
        int col = e.getX() / board.tileSize;
        int row = e.getY() / board.tileSize;
        if (board.selectedPiece != null) {
            Move move = new Move(board, board.selectedPiece, col, row);
            if (board.isValidMove(move)) {
                board.makeMove(move);
            } else {
                board.selectedPiece.xPos = board.selectedPiece.col * board.tileSize;
                board.selectedPiece.yPos = board.selectedPiece.row * board.tileSize;
            }
        }
        board.selectedPiece = null;
        board.repaint();
    }

}
