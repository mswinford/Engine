#version 330

in vec2 uv;
in vec3 normal;

out vec4 color;

void main(){
	//vec3 tmpColor = vec3(uv.rg, 0.0) + normal;
	//vec3 light = vec3(0.25, 0.0, 0.25);
	//float cosTheta = 0.5; //clamp( dot( n,l ), 0,1 );
	//color = vec4(tmpColor, 1.0) * cosTheta;

	color = vec4(1.0, 0.0, 1.0, 0.5);
}