package se.omegapoint.student;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;


/**
 * Example of how to handle camera and input in libGDX
 *
 * Code from: https://github.com/libgdx/libgdx/wiki/Orthographic-camera
 *
 * Notes (Help):
 *
 * Texture:
 *  -  An image that has been decoded from its original format (e.g., PNG) and uploaded to the GPU is called a texture.
 *
 * SpriteBatch:
 *  -  It would be inefficient to send each rectangle one at a time to the GPU to be drawn.
 *     Instead, many rectangles for the same texture can be described and sent to the GPU all at once.
 *     This is what the SpriteBatch class does.
 *
 * Sprite:
 *  -  The Sprite class describes both a texture region, the geometry where it will be drawn, and the color it will be drawn.
 *
 */
public class CameraAndInputGame extends ApplicationAdapter{

    //Sets the size of the loaded map (Sprite). This is not in pixels!
    private static final float WORLD_WIDTH = 100f;
    private static final float WORLD_HEIGHT = 100f;

    private static final int MOVE_SPEED = 3;
    private static final float ROTATION_SPEED = 0.5f;
    public static final double ZOOM_SPEED = 0.02;

    private OrthographicCamera cam;
    private SpriteBatch batch;

    private Sprite mapSprite;

    private int counter = 0;

    @Override
    public void create() {

        mapSprite = new Sprite(new Texture(Gdx.files.internal("sc_map.png")));
        mapSprite.setPosition(0, 0);
        mapSprite.setSize(WORLD_WIDTH, WORLD_HEIGHT);

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        //Defines the scope of the camera. This will initiate a camera that views 30/100 of the maps width.
        //The height is adjusted with to the proportions of the display.
        cam = new OrthographicCamera(30, 30 * (h / w));
        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
        cam.update();

        batch = new SpriteBatch();

    }


    @Override
    public void render() {

        //Display status every forth second for debugging.
        if(this.counter++ > 240) {
            this.counter = 0;
            System.out.println("* * * * Current camera status: * * * *");
            System.out.println("cam.vireportWidth: " + cam.viewportWidth);
            System.out.println("cam.vireportHeigth: " + cam.viewportHeight);
            System.out.println("cam.zoom: " + cam.zoom);
            System.out.println("cam.zoom: " + cam.zoom);
            System.out.println("cam.position.x: " + cam.position.x);
            System.out.println("cam.position.y: " + cam.position.y);
            System.out.println("- - - - - - - - - - - - - - -\n");
        }
        handleInput();
        cam.update();
        batch.setProjectionMatrix(cam.combined);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        mapSprite.draw(batch);
        batch.end();

    }


    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            cam.zoom += ZOOM_SPEED;
            //If the A Key is pressed, add 0.02 to the Camera's Zoom
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            cam.zoom -= ZOOM_SPEED;
            //If the Q Key is pressed, subtract 0.02 from the Camera's Zoom
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            cam.translate(-MOVE_SPEED, 0, 0);
            //If the LEFT Key is pressed, translate the camera -3 units in the X-Axis
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            cam.translate(MOVE_SPEED, 0, 0);
            //If the RIGHT Key is pressed, translate the camera 3 units in the X-Axis
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            cam.translate(0, -MOVE_SPEED, 0);
            //If the DOWN Key is pressed, translate the camera -3 units in the Y-Axis
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            cam.translate(0, MOVE_SPEED, 0);
            //If the UP Key is pressed, translate the camera 3 units in the Y-Axis
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            cam.rotate(-ROTATION_SPEED, 0, 0, 1);
            //If the W Key is pressed, rotate the camera by -ROTATION_SPEED around the Z-Axis
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            cam.rotate(ROTATION_SPEED, 0, 0, 1);
            //If the E Key is pressed, rotate the camera by ROTATION_SPEED around the Z-Axis
        }

        cam.zoom = MathUtils.clamp(cam.zoom, 0.1f, 100 / cam.viewportWidth);

        float effectiveViewportWidth = cam.viewportWidth * cam.zoom;
        float effectiveViewportHeight = cam.viewportHeight * cam.zoom;

        cam.position.x = MathUtils.clamp(cam.position.x, effectiveViewportWidth / 2f, 100 - effectiveViewportWidth / 2f);
        cam.position.y = MathUtils.clamp(cam.position.y, effectiveViewportHeight / 2f, 100 - effectiveViewportHeight / 2f);
    }
}
