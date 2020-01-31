varying vec4 v_color;
varying vec2 v_texCoord0;

uniform float my_color_r;
uniform float my_color_g;
uniform float my_color_b;
uniform sampler2D u_sampler2D;

void main() {
	vec4 color = texture2D(u_sampler2D, v_texCoord0) * v_color;
	color.r = my_color_r;
	color.g = my_color_g;
	color.b = my_color_b;
	gl_FragColor = color;
}
