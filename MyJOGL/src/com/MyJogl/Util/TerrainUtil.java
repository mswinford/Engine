package com.MyJogl.Util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.MyJogl.GameObject.Terrain;
import com.MyJogl.Model.TerrainModel;

public class TerrainUtil {

	public TerrainUtil() {
	}
	
	public static void saveTerrain(Terrain t, String filepath) {
		TerrainModel tm = (TerrainModel)(t.getModel());
		
		File f = new File(filepath);
		BufferedWriter bw = null;
		
		try {
			bw = new BufferedWriter(new FileWriter(f));
			
			//write all vertex information: position, uvs, normals
//			bw.write(str);
			
			bw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if( bw != null ) {
					bw.close();
				} 
			} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
		}
	}

}
