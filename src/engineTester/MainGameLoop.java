package engineTester;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.RawModel;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import renderEngine.EntityRenderer;
import shaders.StaticShader;
import terrains.Terrain;
import textures.ModelTexture;

public class MainGameLoop {

	public static void main(String[] args) {
		
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		
		
		RawModel model = OBJLoader.loadObjModel("stall", loader);
		TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("stallTexture")));
		
		ModelTexture texture = staticModel.getTexture();
		texture.setShineDamper(10);
		texture.setReflectivity(1);
		
		Entity entity = new Entity(staticModel, new Vector3f(0,0,-25), 0, 0, 0, 1);
		
		Light light = new Light(new Vector3f(200,200,100),new Vector3f(1,1,1));
		
		Terrain terrain = new Terrain(-800, -800, loader,new ModelTexture(loader.loadTexture("blanco")));
		Terrain terrain2 = new Terrain(0, -800, loader,new ModelTexture(loader.loadTexture("blue")));
		Terrain terrain3 = new Terrain(0, 1, loader,new ModelTexture(loader.loadTexture("whito")));
		Camera camera = new Camera();
		MasterRenderer renderer = new MasterRenderer();
		float rot =0;
		while(!Display.isCloseRequested()){
			//game logic
			entity.increseRotation(0, rot, 0);
			
			if(Keyboard.isKeyDown(Keyboard.KEY_Q))
				rot += 0.01f;
			if(Keyboard.isKeyDown(Keyboard.KEY_E))
				rot -= 0.01f;
			camera.move();
			renderer.processTerrain(terrain);
			renderer.processTerrain(terrain2);
			//renderer.processTerrain(terrain3);
			renderer.processEntity(entity);
			renderer.render(light, camera);
			DisplayManager.updateDisplay();
		}
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();

	}

}
