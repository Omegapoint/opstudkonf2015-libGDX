package se.omegapoint.student;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

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

        for(int i = 5; i < 25; i++){
            for(int k = 15; k < 30; k++){
                bodyDef.position.set(i,k);
                ballBody = world.createBody(bodyDef);
                ballBody.createFixture(fixtureDef);
            }
        }



    }





	@Override
	public void render () {
        world.step(1/60f, 6, 2);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        debugRenderer.render(world, cam.combined);

	}
}
