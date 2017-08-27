#version 330 core

in vec3 pso;

out vec4 color;

void main() {
    color = vec4(0.5 + pso.x, 0.5 + pso.y, 0.5 + pso.z, 1);
}
