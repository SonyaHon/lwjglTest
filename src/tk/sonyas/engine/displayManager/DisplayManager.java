package tk.sonyas.engine.displayManager;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;
import java.util.Date;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Created by sonyahon on 26/08/2017.
 */
public class DisplayManager {

	private int width;
	private int height;
	private long mainWindow;

	public DisplayManager(int width, int height, String title) {
		this.width = width;
		this.height = height;

		GLFWErrorCallback.createPrint(System.err).set();
		if ( !glfwInit() )
			throw new IllegalStateException("Unable to initialize GLFW");

		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);

		mainWindow = glfwCreateWindow(width, height, title, NULL, NULL);
		if ( mainWindow == NULL )
			throw new RuntimeException("Failed to create the GLFW window");

		try ( MemoryStack stack = stackPush() ) {
			IntBuffer pWidth = stack.mallocInt(1);
			IntBuffer pHeight = stack.mallocInt(1);

			glfwGetWindowSize(mainWindow, pWidth, pHeight);

			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			glfwSetWindowPos(
							mainWindow,
							(vidmode.width() - pWidth.get(0)) / 2,
							(vidmode.height() - pHeight.get(0)) / 2
			);
		}

		glfwMakeContextCurrent(mainWindow);
		glfwSwapInterval(1);
		glfwShowWindow(mainWindow);
		GL.createCapabilities();
	}

	public boolean keyPressed(int keyCode) {
		return glfwGetKey(mainWindow, keyCode) == GLFW_PRESS;
	}

	public boolean keyReleased(int keyCode) {
		return glfwGetKey(mainWindow, keyCode) == GLFW_RELEASE;
	}

	public void prepare() {
		GL.createCapabilities();
		glfwSwapBuffers(mainWindow);
		glfwPollEvents();
	}

	public boolean shouldClose() {
		return glfwWindowShouldClose(mainWindow);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void destoryDisplay() {

		glfwFreeCallbacks(mainWindow);
		glfwDestroyWindow(mainWindow);

		glfwTerminate();
		glfwSetErrorCallback(null);
	}

}
