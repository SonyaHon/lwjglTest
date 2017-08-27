package tk.sonyas.engine.models;

/**
 * Created by sonyahon on 26/08/2017.
 */
public class Mesh {

	private int vao_id;
	private int vertex_amount;

	public Mesh(int vao, int vertex_amount) {
		this.vao_id = vao;
		this.vertex_amount = vertex_amount;
	}

	public int getVao_id() {
		return vao_id;
	}

	public int getVertex_amount() {
		return vertex_amount;
	}
}
