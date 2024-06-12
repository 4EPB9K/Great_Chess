package main;

import pieces.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Board extends JPanel {
    public int tileSize = 70;
    int cols = 10;
    int rows = 10;
    ArrayList<Piece> pieceList = new ArrayList<>();

    public Piece selectedPiece;
    Input input = new Input(Board.this);

    CheckScanner checkScanner = new CheckScanner(this);

    //public int enPassantTile = -1;
    private boolean flag = true;

    public Board() {
        this.setPreferredSize(new Dimension(cols * tileSize, rows * tileSize));
        this.addMouseListener(input);
        this.addMouseMotionListener(input);
        addPieces();
    }

    public Piece getPiece(int col, int row) {
        for (Piece piece : pieceList) {
            if (piece.col == col && piece.row == row) {
                return piece;
            }
        }
        return null;
    }

    public void makeMove(Move move) {
        if (move.piece.name.equals("Pawn")) {
            movePawn(move);
        } else {
            move.piece.col = move.newCol;
            move.piece.row = move.newRow;
            move.piece.xPos = move.newCol * tileSize;
            move.piece.yPos = move.newRow * tileSize;

            capture(move.capture);//захватить
        }
        flag = !flag;
        System.out.println(flag);
    }

    private void movePawn(Move move) {

        int colorIndex = move.piece.isWhite ? 0 : 9;
        if (move.newRow == colorIndex) {
            promotePawn(move);
        }

        move.piece.col = move.newCol;
        move.piece.row = move.newRow;
        move.piece.xPos = move.newCol * tileSize;
        move.piece.yPos = move.newRow * tileSize;

        move.piece.isFirstMove = false;

        capture(move.capture);
    }

    private void promotePawn(Move move) {
        pieceList.add(new Queen(this, move.newCol, move.newRow, move.piece.isWhite));
        capture(move.piece);
    }

    public void capture(Piece piece) {//захватить
        pieceList.remove(piece);
    }

    public boolean isValidMove(Move move) {
        //if (move.newCol < cols && move.newRow < rows ) {
        if (move.piece.isWhite != flag){
            return false;
        }
        if (sameTeam(move.piece, move.capture)) {
            return false;
        }
        if (!move.piece.isValidMovement(move.newCol, move.newRow)) {
            return false;
        }
        if (move.piece.moveCollidesWithPiece(move.newCol, move.newRow)) {
            return false;
        }
        if (checkScanner.isKingChecked(move)) {
            return false;
        }
        return true;
    }


    public boolean sameTeam(Piece p1, Piece p2) {
        if (p1 == null || p2 == null) {
            return false;
        }
        return p1.isWhite == p2.isWhite;
    }

    public int getTileNum(int col, int row) {
        return row * rows + col;
    }

    Piece findKing(boolean isWhite) {
        for (Piece piece : pieceList) {
            if (isWhite == piece.isWhite && piece.name.equals("King")) {
                return piece;
            }
        }
        return null;
    }

    public void addPieces() {
        /**
         * Ладья(Rook) - 2
         * Пешка(Pawn) - 10
         * Конь(Knight) - 2
         * Слон(Bishop) - 2
         * Король(King) - 1
         * Ферзь(Queen) - 1
         * Жираф(Giraffe) - 1
         * Визирь(Vizir) - 1
         * Боевая машина(CombatVehicle) - 2
         * **/
        pieceList.add(new Rook(this, 0, 0, false));//ладья
        pieceList.add(new Knight(this, 1, 0, false));//конь
        pieceList.add(new Bishop(this, 2, 0, false));//слон
        pieceList.add(new Queen(this, 3, 0, false));//королева или Ферзь
        pieceList.add(new King(this, 4, 0, false));//король
        pieceList.add(new Giraffe(this, 5, 0, false));//король
        pieceList.add(new Vizir(this, 6, 0, false));//король
        pieceList.add(new Bishop(this, 7, 0, false));//слон
        pieceList.add(new Knight(this, 8, 0, false));//конь
        pieceList.add(new Rook(this, 9, 0, false));//ладья

        pieceList.add(new Pawn(this, 0, 1, false));//пешка
        pieceList.add(new Pawn(this, 1, 1, false));
        pieceList.add(new Pawn(this, 2, 1, false));
        pieceList.add(new Pawn(this, 3, 1, false));
        pieceList.add(new CombatVehicle(this, 4, 1, false));
        pieceList.add(new Pawn(this, 4, 2, false));
        pieceList.add(new CombatVehicle(this, 5, 1, false));
        pieceList.add(new Pawn(this, 5, 2, false));
        pieceList.add(new Pawn(this, 6, 1, false));
        pieceList.add(new Pawn(this, 7, 1, false));
        pieceList.add(new Pawn(this, 8, 1, false));
        pieceList.add(new Pawn(this, 9, 1, false));

        pieceList.add(new Rook(this, 0, 9, true));//ладья
        pieceList.add(new Knight(this, 1, 9, true));//конь
        pieceList.add(new Bishop(this, 2, 9, true));//слон
        pieceList.add(new Vizir(this, 3, 9, true));//слон
        pieceList.add(new Giraffe(this, 4, 9, true));//королева или Ферзь
        pieceList.add(new King(this, 5, 9, true));//король
        pieceList.add(new Queen(this, 6, 9, true));//королева или Ферзь
        pieceList.add(new Bishop(this, 7, 9, true));//слон
        pieceList.add(new Knight(this, 8, 9, true));//конь
        pieceList.add(new Rook(this, 9, 9, true));//ладья

        pieceList.add(new Pawn(this, 0, 8, true));//пешка
        pieceList.add(new Pawn(this, 1, 8, true));
        pieceList.add(new Pawn(this, 2, 8, true));
        pieceList.add(new Pawn(this, 3, 8, true));
        pieceList.add(new CombatVehicle(this, 4, 8, true));
        pieceList.add(new Pawn(this, 4, 7, true));
        pieceList.add(new CombatVehicle(this, 5, 8, true));
        pieceList.add(new Pawn(this, 5, 7, true));
        pieceList.add(new Pawn(this, 6, 8, true));
        pieceList.add(new Pawn(this, 7, 8, true));
        pieceList.add(new Pawn(this, 8, 8, true));
        pieceList.add(new Pawn(this, 9, 8, true));

        //pieceList.add(new Queen(this, 4, 4, true));//королева или Ферзь
    }

    public void paintComponent(Graphics g) {//отрисовываем компонент
        Graphics2D g2d = (Graphics2D) g;
        String alphabet = "abcdefghij";
        //alphabet.split("");
        StringBuilder numbers = new StringBuilder("0");
        for (int i = 9; i > 0; i--) {
            numbers.append(i);
        }

        // paint board
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++) {
                g2d.setColor((c + r) % 2 == 0 ? new Color(240, 217, 181) : new Color(181, 136, 99));
                g2d.fillRect(c * tileSize, r * tileSize, tileSize, tileSize);
            }

        for (int c = 0; c < cols; c++) {
            g2d.setColor(Color.BLACK);
            g2d.drawString(String.valueOf(alphabet.charAt(c)), tileSize * c + 33, 8);
        }
        g2d.drawString("10", 2, tileSize * 0 + 33);

        for (int r = 1; r < rows; r++) {
            g2d.setColor(Color.BLACK);
            g2d.drawString(String.valueOf(numbers.indexOf(String.valueOf(r))), 2, tileSize * r + 33);
        }

        // paint hihtlights
        if (selectedPiece != null)
            for (int r = 0; r < rows; r++)
                for (int c = 0; c < cols; c++) {
                    if (isValidMove(new Move(this, selectedPiece, c, r))) {
                        g2d.setColor(new Color(68, 180, 57, 190));
                        g2d.fillRect(c * tileSize, r * tileSize, tileSize, tileSize);
                    }
                }

        //paint pieces
        for (Piece piece : pieceList) {
            piece.paint(g2d);
        }
    }
}
