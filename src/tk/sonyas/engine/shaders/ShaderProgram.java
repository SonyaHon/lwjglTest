package tk.sonyas.engine.shaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

/**
 * Created by sonyahon on 26/08/2017.
 */
public class ShaderProgram {

	private int program_id;

	private int vs_id;
	private int fs_id;

	public ShaderProgram(String vertexShader, String fragmentShader) {
		program_id = glCreateProgram();

		try {
			vs_id = createShader(vertexShader, GL_VERTEX_SHADER);
			fs_id = createShader(fragmentShader, GL_FRAGMENT_SHADER);
		} catch (Exception e) {
			e.printStackTrace();
		}

		glAttachShader(program_id, vs_id);
		glAttachShader(program_id, fs_id);
		glLinkProgram(program_id);
		glValidateProgram(program_id);

	}

	public void start() {
		glUseProgram(program_id);
	}

	public void stop() {
		glUseProgram(0);
	}

	public void deleteProgram() {
		stop();
		glDetachShader(program_id, vs_id);
		glDetachShader(program_id, fs_id);
		glDeleteShader(vs_id);
		glDeleteShader(fs_id);
		glDeleteProgram(program_id);
	}

	private int createShader(String path, int type){

		StringBuilder shaderCode = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			String line;
			while ((line = reader.readLine()) != null) {
				shaderCode.append(line).append("\n");
			}
			reader.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		int shaderID = glCreateShader(type);
		glShaderSource(shaderID, shaderCode);
		glCompileShader(shaderID);

		if(glGetShaderi(shaderID, GL_COMPILE_STATUS) == GL_FALSE) {
			System.out.println(glGetShaderInfoLog(shaderID, 500));
			System.out.println("Can`t compile shader " + path);
		}
		return shaderID;
	}

}
