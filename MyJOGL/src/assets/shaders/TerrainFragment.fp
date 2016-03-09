#version 330

in vec2 uv;
//in vec3 normal;

out vec4 color;

void main(){
	//vec3 tmpColor = vec3(uv.rg, 0.0) + normal;
	//vec3 light = vec3(0.25, 0.0, 0.25);
	//float cosTheta = 0.5; //clamp( dot( n,l ), 0,1 );
	
	/*
 	if( uv.y <= -50.0 ) {
		color = vec4(0.0, 0.0, 0.5, 0.5); 	
 	}
 	else {
		color = vec4(0.0, clamp(uv.y / 255, 0.15, 1.0), 0.0, 1.0);
	}
	*/
	
	color = vec4(1.0, 1.0, 1.0, 1.0);
}