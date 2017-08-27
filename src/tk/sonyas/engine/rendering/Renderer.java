package tk.sonyas.engine.rendering;

import org.lwjgl.opengl.GL11;
import tk.sonyas.engine.models.Mesh;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class Renderer {

	private float r = 0f;
	private float g = 0f;
	private float b = 0f;
	private float a = 1.0f;

	public void setClearColor(float r, float g, float b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}

	public void setClearColor(float r, float g, float b, float a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}


	public void clear() {
		GL11.glClearColor(r, g, b, a);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
	}

	public void renderMesh(Mesh mesh) {
		glBindVertexArray(mesh.getVao_id());
		glEnableVertexAttribArray(0);

		glDrawElements(GL_TRIANGLES, mesh.getVertex_amount(), GL_UNSIGNED_INT, 0);

		glDisableVertexAttribArray(0);
		glBindVertexArray(0);
	}

}
