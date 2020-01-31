varying vec4 v_color;
varying vec2 v_texCoord0;

uniform float time;
uniform sampler2D u_sampler2D;

void main() {
	vec4 color5 = texture2D(u_sampler2D, v_texCoord0) * v_color;
	vec4 color1 = texture2D(u_sampler2D, v_texCoord0 + vec2((sin(v_texCoord0.x * 50.0 + time*4.0)) / 2.0 / 50.0, (sin(v_texCoord0.y * 50.0 + time*4.0)) / 2.0 / 50.0)) * v_color * (1.0 + (sin(v_texCoord0.y * 50.0 + time*4.0) + 1.0) * (sin(v_texCoord0.x * 50.0 + time*4.0) + 1.0) / 8.0);
	vec4 color2 = texture2D(u_sampler2D, v_texCoord0 + vec2((sin(v_texCoord0.x * 60.0 + time*4.2)) / 2.0 / 50.0, (sin(v_texCoord0.y * 60.0 + time*4.2)) / 2.0 / 50.0)) * v_color * (1.0 + (sin(v_texCoord0.y * 60.0 + time*4.2) + 1.0) * (sin(v_texCoord0.x * 60.0 + time*4.2) + 1.0) / 8.0);
	vec4 color3 = texture2D(u_sampler2D, v_texCoord0 + vec2((sin(v_texCoord0.x * 70.0 + time*4.4)) / 2.0 / 50.0, (sin(v_texCoord0.y * 70.0 + time*4.4)) / 2.0 / 50.0)) * v_color * (1.0 + (sin(v_texCoord0.y * 70.0 + time*4.4) + 1.0) * (sin(v_texCoord0.x * 70.0 + time*4.4) + 1.0) / 8.0);
	vec4 color4 = texture2D(u_sampler2D, v_texCoord0 + vec2((sin(v_texCoord0.x * 80.0 + time*4.6)) / 2.0 / 50.0, (sin(v_texCoord0.y * 80.0 + time*4.6)) / 2.0 / 50.0)) * v_color * (1.0 + (sin(v_texCoord0.y * 80.0 + time*4.6) + 1.0) * (sin(v_texCoord0.x * 80.0 + time*4.6) + 1.0) / 8.0);
	vec4 color = color1 + color2 + color3 + color4;
	color = color / 4.0;
	color = color - vec4(0.15, 0.15, 0.15, 0);
	gl_FragColor = color;
}
