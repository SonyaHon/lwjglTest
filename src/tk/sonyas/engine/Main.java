package tk.sonyas.engine;

import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;
import tk.sonyas.engine.displayManager.DisplayManager;
import tk.sonyas.engine.models.Loader;
import tk.sonyas.engine.models.Mesh;
import tk.sonyas.engine.rendering.Renderer;
import tk.sonyas.engine.shaders.ShaderProgram;

import java.nio.FloatBuffer;
import java.util.Date;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.system.MemoryUtil.memFree;

public class Main {
	public void run() {
		DisplayManager displayManager = new DisplayManager(800, 600, "Game");
		Loader loader = new Loader();

		Renderer renderer = new Renderer();
		renderer.setClearColor(0.33f, 0.44f, 0.66f);

		ShaderProgram basic = new ShaderProgram("src/tk/sonyas/engine/shaders/testVert.glsl",
						"src/tk/sonyas/engine/shaders/testFrag.glsl");


		float[] vertices = new float[]{
						0.0f,  0.5f, 0.0f,
						-0.5f, -0.5f, 0.0f,
						0.5f, -0.5f, 0.0f
		};

		int[] inds = new int[]{
					0, 1, 2
		};
		

		Mesh mesh = loader.loadFromBuffers(vertices, inds);

		double secsPerUpdate = 1d / 30d;
		double previous = new Date().getTime();
		double steps = 0.0; // Sync stuff

		while(!displayManager.shouldClose()) {
			double loopStartTime = new Date().getTime();
			double elapsed = loopStartTime - previous;
			previous = loopStartTime;
			steps += elapsed; // Sync stuff

			displayManager.prepare();
			renderer.clear();

			while (steps >= secsPerUpdate) {
				// logic here


				steps -= secsPerUpdate; // sync stuff
			}

			//render here
			basic.start();

			renderer.renderMesh(mesh);

			basic.stop();
			sync(loopStartTime); // sync stuff
		}

		loader.cleanUp();
		basic.deleteProgram();
		displayManager.destoryDisplay();
	}

	private void sync(double loopStartTime) {
		float loopSlot = 1f / 50;
		double endTime = loopStartTime + loopSlot;
		while(new Date().getTime() < endTime) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException ie) {}
		}
	}

	public static void main(String[] args) {
		new Main().run();
	}

}
