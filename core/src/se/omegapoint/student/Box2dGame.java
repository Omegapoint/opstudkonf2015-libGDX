package se.omegapoint.student;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Logger;

/**
 * Box2D is a 2D physics library, not only used in libGDX. Games such as Angry Birds and Tiny Wings has been written in Box2D.
 * It is one of the most popular physics libraries for 2D games and has been ported to many languages and many different engines, including libgdx..
 *
 * A Box2D manual can be found at: http://box2d.org/manual.pdf
 *
 * World
 *   - The world object is basically what holds all your physics objects/bodies and simulates the reactions between them
 *
 *
 */
public class Box2dGame extends ApplicationAdapter {



    World world;
    Box2DDebugRenderer debugRenderer;
    OrthographicCamera cam;

	@Override
	public void create () {

        //Initializes Box2D
        Box2D.init();

        //Used for debugging. It draws colorful lines around shapes in the world
        debugRenderer = new Box2DDebugRenderer();

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        //Defines the scope of the camera. This will initiate a camera that views 30/100 of the maps width.
        //The height is adjusted with to the proportions of the display.
        cam = new OrthographicCamera(30, 30 * (h / w));
        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
        cam.update();

        //Used for debugging. It draws colorful lines around shapes in the world
        Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();

        /**
         * The first argument we supply is a 2D vector containing the gravity:
         *    0 to indicate no gravity in the horizontal direction
         *    -10 is a downwards force like in real life (assuming your y axis points upwards).
         *
         *    These values can be anything you like, but remember to stick to a constant scale. In Box2D 1 unit = 1 metre.
         */
        world = new World(new Vector2(0, -9.81f), true);


        /**
         * Creating ground body (The container for the ground fixtures) and adds it to the world.
         *
         */
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(15,2);
        bodyDef.type = BodyType.StaticBody;
        Body groundBody = world.createBody(bodyDef);

        /**
         * Creating a polygon shaped fixture that is added to the ground body container.
         *
         */
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape groundShape = new PolygonShape();
        groundShape.setAsBox(10,1);
        fixtureDef.shape = groundShape;
        groundBody.createFixture(fixtureDef);



        /**
         * Creating multiple ball objects, using the same body def as previously.
         *
         */
        bodyDef.position.set(5,10);
        bodyDef.type = BodyType.DynamicBody;
        Body ballBody = world.createBody(bodyDef);

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(0.2f);
        fixtureDef.shape = circleShape;
        fixtureDef.restitution = 0.5f;

        /**
         * Create some ball objects
         */
        for(int i = 5; i < 25; i++){
            for(int k = 15; k < 30; k++){
                float offset = (MathUtils.random(0,1) == 1) ? 0.2f : 0;
                bodyDef.position.set(i+offset,k);
                ballBody = world.createBody(bodyDef);
                ballBody.createFixture(fixtureDef);
            }
        }



    }

    private void handleInput() {


        /**
         * Creates balls on input coordinates.
         *
         */
        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){

            //Calculate proportion between screen size and camera.
            float xprop = (cam.viewportWidth/Gdx.graphics.getWidth());
            float yprop = (cam.viewportHeight/Gdx.graphics.getHeight());

            final float xpos = Gdx.input.getX();
            final float  ypos =Gdx.input.getY();

            BodyDef bodyDef = new BodyDef();
            //Y input seems to be opposite direction, therefore the subtraction.
            bodyDef.position.set(xpos * xprop, cam.viewportHeight - ypos * yprop);
            bodyDef.type = BodyType.DynamicBody;
            FixtureDef fixtureDef = new FixtureDef();
            CircleShape circleShape = new CircleShape();
            //Set random radius.
            circleShape.setRadius((float)(MathUtils.random(1,10)/20.0));
            fixtureDef.shape = circleShape;
            fixtureDef.restitution = 0.9f;

            //bodyDef.position.set(i+offset,k);
            Body ballBody = world.createBody(bodyDef);
            ballBody.createFixture(fixtureDef);

        }


    }


    /**
     * Handles input, lets to world calculate physics and finally render the screen with a debug renderer.
     *
     */
	@Override
	public void render () {
        handleInput();
        world.step(1/60f, 6, 2);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        debugRenderer.render(world, cam.combined);

	}
}
