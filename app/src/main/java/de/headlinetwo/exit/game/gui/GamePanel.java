package de.headlinetwo.exit.game.gui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import de.headlinetwo.exit.R;
import de.headlinetwo.exit.game.GameConstants;
import de.headlinetwo.exit.game.GameHandler;
import de.headlinetwo.exit.game.logic.SwipeHandler;
import de.headlinetwo.exit.util.LevelHintTextBoxTextSplitter;
import de.headlinetwo.exit.util.Rectangle;
import de.headlinetwo.exit.util.direction.Direction;

/**
 * Created by headlinetwo on 30.11.17.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder surfaceHolder;
    private GameHandler gameHandler;

    private SwipeHandler swipeHandler = null;

    private Canvas bufferCanvas;
    private Bitmap bufferBitmap;
    private Paint drawPaint = new Paint();

    private int screenWidth = 1080; //default initialization values
    private int screenHeight = 1920; //default initialization values

    private int blockSize; //the size of one block, in pixel
    private int gridXStart; //left border to start drawing the blocks, in pixel
    private int gridYStart; //top border to start drawing the blocks, in pixel

    private int hintTextBoxStartX;
    private int hintTextBoxStartY;
    private int hintTextBoxLineHeight;
    private int hintTextBoxWidth;

    private Paint backgroundPaint;
    private Paint textPaint;

    private int gridStartXOffset = 0;
    private int gridStartYOffset = 0;

    public GamePanel(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        setFocusable(true);
        getHolder().addCallback(this);

        setWillNotDraw(false);

        backgroundPaint = new Paint();
        backgroundPaint.setColor(getResources().getColor(R.color.levelBackground));

        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(getResources().getDimensionPixelSize(R.dimen.levelHintTextBoxTextSize));
        textPaint.setTypeface(Typeface.createFromAsset(context.getAssets(), "font/visitor1.otf"));

        clearBuffer();
    }

    /**
     * @return the left most coordinate where the grid shall be drawn on the display,
     * measured in pixels
     */
    private int getStartX() {
        return gridXStart + gridStartXOffset;
    }

    /**
     * @return the top most coordinate where the grid shall be drawn on the display,
     * measured in pixel
     */
    public int getStartY() {
        return gridYStart + gridStartYOffset;
    }

    public void initialize(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
        this.swipeHandler = new SwipeHandler(gameHandler);
    }

    /**
     * Updates the blockSize, gridXStart, gridYStart as well as the location an size of the
     * hint test box after the screens dimensions have bee changed
     */
    public void updateValues() {
        blockSize = PanelUtil.calculateBlockSize(getWidth(), getHeight());
        gridXStart = PanelUtil.calculateStartX(getWidth(), gameHandler.getCurrentLevel().getGrid().getWidth(), blockSize);
        gridYStart = PanelUtil.calculateStartY(getHeight(), gameHandler.getCurrentLevel().getGrid().getHeight(), blockSize);

        hintTextBoxStartX = (int) (getWidth() * GameConstants.TEN_PERCENT);
        hintTextBoxStartY = getStartY() + (blockSize * gameHandler.getCurrentLevel().getGrid().getHeight()) + 2 * blockSize;
        hintTextBoxLineHeight = blockSize;
        hintTextBoxWidth = (int) (getWidth() * GameConstants.EIGHTY_PERCENT);

        Paint.FontMetrics fm = textPaint.getFontMetrics();
        hintTextBoxLineHeight = (int) ((fm.descent - fm.ascent) * 1.1f);

        gameHandler.onScreenSizeChange();
    }

    public void onGridSizeChange() {
        updateValues();
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;

        if (!gameHandler.getGameThread().isAlive()) {
            gameHandler.startNewThread();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
        this.screenWidth = width;
        this.screenHeight = height;

        updateValues();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        gameHandler.getGameThread().setRunning(false);
        while (retry) {
            try {
                gameHandler.getGameThread().join();
                retry = false;
            } catch (Exception ex) {}
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (swipeHandler != null) swipeHandler.onEventOccur(e);

        return true; //True if the event was handled, false otherwise.
    }

    public void redraw() {
        if (surfaceHolder == null) return;

        Canvas currentCanvas = null;

        try {
            currentCanvas = surfaceHolder.lockCanvas();
            if (currentCanvas == null) return;
            synchronized (currentCanvas) {
                currentCanvas.drawBitmap(bufferBitmap, 0, 0, drawPaint);
                clearBuffer();
            }
        } finally {
            if (currentCanvas != null) surfaceHolder.unlockCanvasAndPost(currentCanvas);
        }
    }

    /**
     * Fills the given block (square) on the grid completely
     * @see de.headlinetwo.exit.game.logic.Grid for the grid coordiantes
     *
     * @param gridX the x-coordinate (within the grid) of the block on the grid to fill
     *      * @param gridY the y-coordinate (within the grid) of the block on the grid to fill
     * @param paint the paint used to fill the given block in the grid
     */
    public void fillRect(int gridX, int gridY, Paint paint) {
        int startX = getStartX() + gridX * blockSize;
        int startY = getStartY() + gridY * blockSize;
        bufferCanvas.drawRect(startX, startY, startX + blockSize, startY + blockSize, paint);
    }

    /**
     * Draws a fully painted block on the grid. The block may overlap multiple grid cells depending
     * on the given coordinates
     * @see de.headlinetwo.exit.game.logic.Grid for the grid coordiantes
     *
     * @param gridX the top x-coordinate (within the grid) of the square to draw on the grid
     * @param gridY the left y-coordinate (within the grid) of the square to draw on the grid
     * @param paint the paint used to fill the given square on the grid
     */
    public void fillRect(float gridX, float gridY, Paint paint) {
        float startX = getStartX() + gridX * blockSize;
        float startY = getStartY() + gridY * blockSize;
        bufferCanvas.drawRect(startX, startY, startX + blockSize, startY + blockSize, paint);
    }

    /**
     * Draws a fully painted rectangle on the grid. The rectangle may overlap multiple grid cells
     * depending on the given coordinates and width/height.
     * @see de.headlinetwo.exit.game.logic.Grid for the grid coordiantes
     *
     * @param gridX the top x-coordinate (within the grid) of the rectangle to draw on the grid
     * @param gridY the left y-coordinate (within the grid) of the rectangle to draw on the grid
     * @param width the width of the rectangle to draw on the grid.
     *              (1 = one block size, 2 = 2 block sizes...)
     * @param height the height of the rectangle to draw on the grid.
     *               (1 = one block size, 2 = 2 block sizes...)
     * @param paint the paint used to fill the given rectangle on the grid
     */
    public void fillRect(float gridX, float gridY, float width, float height, Paint paint) {
        float startX = getStartX() + gridX * blockSize;
        float startY = getStartY() + gridY * blockSize;
        bufferCanvas.drawRect(startX, startY, startX + blockSize * width, startY + blockSize * height, paint);
    }

    /**
     * Draws a fully painted rectangle on the grid within one grid cell.
     * @see de.headlinetwo.exit.game.logic.Grid for the grid coordinantes
     *
     * @param gridX the x-coordinate (within the grid) of the rectangle on the grid to fill
     * @param gridY the y-coordinate (within the grid) of the rectangle on the grid to fill
     * @param leftGap the left gap between the rectangle to fill and the grids cell (from 0..1)
     * @param topGap the top gap between the rectangle to fill and the grids cell (from 0..1
     * @param rightGap the right gap between the rectangle to fill and the grids cell (from 0..1)
     * @param bottomGap the bottom gap between the rectangle to fill and the grids cell (from 0..1)
     * @param paint the paint used to fill the given rectangle on the grid
     */
    public void fillRect(int gridX, int gridY, float leftGap, float topGap, float rightGap, float bottomGap, Paint paint) {
        float startX = getStartX() + (gridX * blockSize) + (leftGap * blockSize);
        float startY = getStartY() + (gridY * blockSize) + (topGap * blockSize);
        float width = blockSize - (blockSize * (leftGap + rightGap));
        float height = blockSize - (blockSize * (topGap + bottomGap));
        bufferCanvas.drawRect(startX, startY, startX + width, startY + height, paint);
    }

    /**
     * Draws a fully painted rectangle on the grid. The rectangle may overlap multiple grid cells
     * depending on the given coordinates and width/height.
     * @see de.headlinetwo.exit.game.logic.Grid for the grid coordiantes
     *
     * @param gridX the top x-coordinate (within the grid) of the rectangle to draw on the grid
     * @param gridY the left y-coordinate (within the grid) of the rectangle to draw on the grid
     * @param leftGap the left padding
     * @param topGap the top padding
     * @param rightGap the right padding
     * @param bottomGap the bottom padding
     * @param paint the paint used to draw the given rectangle on the grid
     */
    public void fillRect(float gridX, float gridY, float leftGap, float topGap, float rightGap, float bottomGap, Paint paint) {
        float startX = getStartX() + (gridX * blockSize) + (leftGap * blockSize);
        float startY = getStartY() + (gridY * blockSize) + (topGap * blockSize);
        float width = blockSize - (blockSize * (leftGap + rightGap));
        float height = blockSize - (blockSize * (topGap + bottomGap));
        bufferCanvas.drawRect(startX, startY, startX + width, startY + height, paint);
    }

    /**
     * Draws a fully painted rectangle on the grid. The rectangle may overlap multiple grid cells
     * depending on the given coordinates and width/height.
     * @see de.headlinetwo.exit.game.logic.Grid for the grid coordinates
     *
     * @param rectangle the rectangle (coordinates of the rectangle) to draw on the grid
     * @param paint the paint used to draw the given rectangle on the grid
     */
    public void fillRect(Rectangle rectangle, Paint paint) {
        float startX = getStartX() + (rectangle.getLeft() * blockSize);
        float startY = getStartY() + (rectangle.getTop() * blockSize);
        float stopX = getStartX() + (rectangle.getRight() * blockSize);
        float stopY = getStartY() + (rectangle.getBottom() * blockSize);
        bufferCanvas.drawRect(startX, startY, stopX, stopY, paint);
    }

    /**
     * Draws a fully painted rectangle on the grid. The rectangle may overlap multiple grid cells
     * depending on the given coordinates and width/height.
     * @see de.headlinetwo.exit.game.logic.Grid for the grid coordinates
     *
     * @param rectangle the rectangle (coordinates of the rectangle) to draw on the grid
     * @param offset the offset to add to the given rectangles coordinates
     * @param direction the direction in which to add the offset to the rectangles coordinates
     * @param paint the paint used to draw the given rectangle on the grid
     */
    public void fillRect(Rectangle rectangle, float offset, Direction direction, Paint paint) {
        float startX = getStartX() + (rectangle.getLeft() * blockSize) + (direction.getAddX() * offset * blockSize);
        float startY = getStartY() + (rectangle.getTop() * blockSize) + (direction.getAddY() * offset * blockSize);
        float endX  = getStartX() + (rectangle.getRight() * blockSize) + (direction.getAddX() * offset * blockSize);
        float endY = getStartY() + (rectangle.getBottom() * blockSize) + (direction.getAddY() * offset * blockSize);
        bufferCanvas.drawRect(startX, startY, endX, endY, paint);
    }

    /**
     * Writes the given text on the screen
     *
     * @param gridX the top x-coordinate where to start the text
     * @param gridY the left most y-coordinate where to start the text
     * @param text the text to write on the screen
     */
    public void drawText(int gridX, int gridY, String text) {
        int startX = getStartX() + (gridX * blockSize);
        int startY = getStartY() + (gridY * blockSize);

        Rect areaRect = new Rect(startX, startY, startX + blockSize, startY + blockSize);

        RectF bounds = new RectF(areaRect);

        bounds.right = textPaint.measureText(text, 0, text.length());
        bounds.bottom = textPaint.descent() - textPaint.ascent();

        bounds.left += (areaRect.width() - bounds.right) / 2.0f;
        bounds.top += (areaRect.height() - bounds.bottom) / 2.0f;

        bufferCanvas.drawText(text, bounds.left, bounds.top - textPaint.ascent(), textPaint);
    }

    /**
     * Writes the level hint text at the bottom below the actual level blocks on the screen
     *
     * @param textLines the different lines of the level hint text
     */
    public void drawLevelHintText(String[] textLines) {
        for (int lineIndex = 0; lineIndex < textLines.length; lineIndex++) {
            bufferCanvas.drawText(textLines[lineIndex].trim(), hintTextBoxStartX, hintTextBoxStartY + lineIndex * hintTextBoxLineHeight, textPaint);
        }
    }

    private void clearBuffer() {
        bufferBitmap = Bitmap.createBitmap(screenWidth, screenHeight, Bitmap.Config.ARGB_8888);
        bufferCanvas = new Canvas();
        bufferCanvas.setBitmap(bufferBitmap);

        bufferCanvas.drawRect(0, 0, screenWidth, screenHeight, backgroundPaint);
    }

    /**
     * Splits the given text into multiple lines so that each line fits on the screen
     *
     * @param text the text to split into multiple lines
     * @return the resulting lines
     */
    public String[] splitHintBoxTextLines(String text) {
        return LevelHintTextBoxTextSplitter.split(text, hintTextBoxWidth, textPaint);
    }

    /**
     * @return the total screen width, measured in pixel
     */
    public int getScreenWidth() {
        return screenWidth;
    }

    /**
     * @return the current size of on square block on the grid, measured in pixel
     */
    public int getBlockSize() {
        return blockSize;
    }

    /**
     * Adds an offset to the the whole grid
     *
     * @param offset the x-offset, measured in pixel
     */
    public void setGridStartXOffset(int offset) {
        this.gridStartXOffset = offset;
    }

    /**
     * Adds an offset to the whole grid
     *
     * @param offset the y-offset, measured in pixel
     */
    public void setGridStartYOffset(int offset) {
        this.gridStartYOffset = offset;
    }
}