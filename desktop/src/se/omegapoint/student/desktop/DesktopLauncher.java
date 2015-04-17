package se.omegapoint.student.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import se.omegapoint.student.Box2dGame;
import se.omegapoint.student.CameraAndInputGame;
import se.omegapoint.student.HelloWorldGame;
import se.omegapoint.student.OpStudentLibGdx;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.title = "Omegapoint Student Conference Demo";
        config.width = 1280;
        config.height = 720;

		//new LwjglApplication(new OpStudentLibGdx(), config);
        //new LwjglApplication(new HelloWorldGame(), config);
        //new LwjglApplication(new CameraAndInputGame(), config);
        new LwjglApplication(new Box2dGame(), config);
	}
}
