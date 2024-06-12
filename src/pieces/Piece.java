package pieces;

import main.Board;

import javax.imageio.ImageIO;
import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Piece {
    public int col, row;
    public int xPos, yPos;
    public boolean isWhite;
    public String name;
    public int value;
    public boolean isFirstMove = true;
    BufferedImage sheet;

    {
        try {
            //sheet = ImageIO.read(ClassLoader.getSystemResourceAsStream("pieces.png"));
            sheet = ImageIO.read(ClassLoader.getSystemResourceAsStream("GreatChess.png"));
            //sheet = ImageIO.read(ClassLoader.getSystemResourceAsStream("GreatChessp.svg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //protected int sheetScale = sheet.getWidth() / 6;//масштаб листа(т.к 6 фигур в строке)
    protected int sheetScale =  sheet.getWidth() / 9;//масштаб листа(т.к 9 фигур в строке)
    Image sprite;//графический объект
    Board board;


    public Piece(Board board) {
        this.board = board;
    }

    public boolean isValidMovement(int col, int row) {
        return true;
    }

    public boolean moveCollidesWithPiece(int col, int row) {
        return false;
    }

    public void paint(Graphics2D g2d) {
        g2d.drawImage(sprite, xPos, yPos, null);
    }
}
