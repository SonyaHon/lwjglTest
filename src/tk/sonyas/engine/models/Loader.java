package tk.sonyas.engine.models;

import org.lwjgl.system.MemoryUtil;

import java.awt.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.glDeleteLists;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;

/**
 * Created by sonyahon on 26/08/2017.
 */
public class Loader {

	private java.util.List<Integer> vaos = new ArrayList<>();
	private java.util.List<Integer> vbos = new ArrayList<>();

	public Mesh loadFromBuffers(float[] positions, int[] indices) {
		int vao = createVao();
		bindIndicesBuffer(indices);
		storeDataIntAttrListStatic(0, 3, positions);
		unbind();
		return new Mesh(vao, indices.length);
	}



	public void cleanUp() {
		for(int i : vaos) {
			glDeleteVertexArrays(i);
		}
		for(int i : vbos) {
			glDeleteBuffers(i);
		}
	}

	private int createVao(){
		int vao = glGenVertexArrays();
		vaos.add(vao);
		glBindVertexArray(vao);
		return vao;
	}

	private void bindIndicesBuffer(int[] indices) {
		int vbo = glGenBuffers();
		vbos.add(vbo);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vbo);
		IntBuffer buffer = MemoryUtil.memAllocInt(indices.length);
		buffer.put(indices).flip();
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		MemoryUtil.memFree(buffer);
	}

	private void storeDataIntAttrListStatic(int attr_number, int length, float[] data) {
		int vbo = glGenBuffers();
		vbos.add(vbo);
		glBindBuffer(GL_ARRAY_BUFFER, vbo);

		FloatBuffer buffer = MemoryUtil.memAllocFloat(data.length);
		buffer.put(data).flip();

		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		glVertexAttribPointer(attr_number, length, GL_FLOAT, false, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);

		MemoryUtil.memFree(buffer);
	}

	private void unbind() {
		glBindVertexArray(0);
	}

}
