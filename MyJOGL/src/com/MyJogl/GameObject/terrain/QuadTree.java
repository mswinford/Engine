package com.MyJogl.GameObject.terrain;

import java.nio.FloatBuffer;

import org.joml.Vector3f;

import com.MyJogl.Camera.Camera;
import com.MyJogl.Logger.Logger;
import com.MyJogl.Util.Util;

public class QuadTree {
	private static int LOD = 10; //the LOD distance
	
	private QuadTree parent;
	private QuadTree[] children; //Q1(bottom left), Q2(bottom right), Q3(top left), Q4(top right)  
	private TerrainVertex north;
	private TerrainVertex south;
	private TerrainVertex east;
	private TerrainVertex west;
	private TerrainVertex center;
	
	public QuadTree(float[][] heights) {
		this(heights, (QuadTree)null, 0, 0, heights.length);
	}

	public QuadTree(float[][] heights, QuadTree parent, int minX, int minZ, int length) {
		this.parent = parent;
		
		int centerX = minX + (length / 2);
		int centerZ = minZ + (length / 2);
		int maxX = minX + length-1;
		int maxZ = minZ + length-1;
		
		center = new TerrainVertex();
		center.setPosition( new Vector3f(centerX, heights[centerZ][centerX], centerZ) );
		
		north = new TerrainVertex();
		north.setPosition( new Vector3f(centerX, heights[maxZ][centerX], maxZ) );

		south = new TerrainVertex();
		south.setPosition( new Vector3f(centerX, heights[minZ][centerX], minZ) );
		
		east = new TerrainVertex();
		east.setPosition( new Vector3f(maxX, heights[centerZ][maxX], centerZ) );
		
		west = new TerrainVertex();
		west.setPosition( new Vector3f(minX, heights[centerZ][minX], centerZ) );
		
		children = null;
		
		if( maxX - minX > 2 ) {
			int subLength = (length / 2) + 1;
			
			//Create the child nodes. Else this is a leaf node, so don't create children.
			children = new QuadTree[4];
			/* Quadrants:
			 *				Q3	Q4
			 * 				Q1	Q2
			 */
			//Q1
			children[0] = new QuadTree(heights, this, minX, minZ, subLength);
			//Q2
			children[1] = new QuadTree(heights, this, (int)(center.getX()), minZ, subLength);
			//Q3
			children[2] = new QuadTree(heights, this, minX, (int)(center.getZ()), subLength);
			//Q4
			children[3] = new QuadTree(heights, this, (int)(center.getX()), (int)(center.getZ()), subLength);
		}
		
	}
	
	public int update(Camera camera) {
		int numVertices = 0;
		
		//all base vertices must be enabled or the terrain will be missing whole quadrants at the top level
		if(parent == null) {
			north.setEnabled(true);
			south.setEnabled(true);
			east.setEnabled(true);
			west.setEnabled(true);
		}
		else {
			processVertex(north, camera);
			processVertex(south, camera);
			processVertex(east, camera);
			processVertex(west, camera);
		}
		
		//if any of the side vertices are enabled, then the middle must be enabled too
		if( north.isEnabled() || south.isEnabled() || east.isEnabled() || west.isEnabled() ) {
			center.setEnabled(true);
		}
		else {
			center.setEnabled(false);
		}
		
		//if children is null, then this is a leaf node and there are no children below to update.
		//also return if the center is not enabled because if it isn't enabled then none of the sides are enabled
		if( children == null ) {
			numVertices += 10;
		}
		else if( children != null && center.isEnabled() ) {
			numVertices += 10;
		
			//if the center is enabled, update the child nodes
			numVertices += children[0].update(camera);
			numVertices += children[1].update(camera);
			numVertices += children[2].update(camera);
			numVertices += children[3].update(camera);
			
			//check each child. if the center is enabled, then the sides of this quad that make up the corners of the child must be enabled
			if( children[0].getCenter().isEnabled() ) {
				west.setEnabled(true);
				south.setEnabled(true);
				numVertices -= 2;
			}
			if( children[1].getCenter().isEnabled() ) {
				east.setEnabled(true);
				south.setEnabled(true);
				numVertices -= 2;
			}
			if( children[2].getCenter().isEnabled() ) {
				west.setEnabled(true);
				north.setEnabled(true);
				numVertices -= 2;
			}
			if( children[3].getCenter().isEnabled() ) {
				east.setEnabled(true);
				north.setEnabled(true);
				numVertices -= 2;
			}
			
			//if all children are enabled then this node is not responsible for any vertices
			if( allChildrenEnabled() ) {
				numVertices -= 2;
			}
		}
		
		return numVertices;
	}
	
	private void processVertex(TerrainVertex v, Camera c) {
		float dist = c.getTranslation().distance(v.getPosition());
		if( dist >= LOD ) {
			v.setEnabled(false);
		}
		else {
			v.setEnabled(true);
		}
	}
	
	public void render(QTModel model) {
		//render via triangle fans.
		//recursively render each child until it gets to a leaf i.e. the center is disabled
		if( !center.isEnabled() ) return;
		
		boolean isLeaf = false;
		if(children == null) isLeaf = true;
		
		if( !isLeaf ) {
			children[0].render(model);
			children[1].render(model);
			children[2].render(model);
			children[3].render(model);
		}
		
		if( !isLeaf && allChildrenEnabled() ) {
			return;
		}
		
		//start adding to the VBO
		FloatBuffer vbo = model.getVBO();
		vbo.put(center.getXYZ());
		
		TerrainVertex corner = null;
		//check each vertex including the corners and add it to the vbo if necessary
		//start with the west vertex although this is arbitrary
		
		//west
		if( isLeaf || !(children[0].getCenter().isEnabled() && children[2].getCenter().isEnabled()) ) {
			vbo.put(west.getXYZ());
		}
		//southwest corner
		if( isLeaf || !children[0].getCenter().isEnabled() ) {
			corner = new TerrainVertex( west.getPosition().add(south.getPosition(), new Vector3f()) );
			vbo.put( corner.getXYZ() );
		}
		//south
		if( isLeaf || !(children[0].getCenter().isEnabled() && children[1].getCenter().isEnabled()) ) {
			vbo.put(south.getXYZ());
		}
		//southeast corner
		if( isLeaf || !children[1].getCenter().isEnabled() ) {
			corner = new TerrainVertex( south.getPosition().add(east.getPosition(), new Vector3f()) );
			vbo.put( corner.getXYZ() );
		}
		//east
		if( isLeaf || !(children[1].getCenter().isEnabled() && children[3].getCenter().isEnabled()) ) {
			vbo.put(east.getXYZ());
		}
		//northeast corner
		if( isLeaf || !children[3].getCenter().isEnabled() ) {
			corner = new TerrainVertex( east.getPosition().add(north.getPosition(), new Vector3f()) );
			vbo.put( corner.getXYZ() );
		}
		//north
		if( isLeaf || !(children[3].getCenter().isEnabled() && children[2].getCenter().isEnabled()) ) {
			vbo.put(north.getXYZ());
		}
		//northwest corner
		if( isLeaf || !children[2].getCenter().isEnabled() ) {
			corner = new TerrainVertex( north.getPosition().add(west.getPosition(), new Vector3f()) );
			vbo.put( corner.getXYZ() );
			vbo.put( west.getXYZ() );
		}
	}

	public QuadTree[] getChildren() {
		return children;
	}

	public void setChildren(QuadTree[] children) {
		this.children = children;
	}

	public TerrainVertex getNorth() {
		return north;
	}

	public void setNorth(TerrainVertex north) {
		this.north = north;
	}

	public TerrainVertex getSouth() {
		return south;
	}

	public void setSouth(TerrainVertex south) {
		this.south = south;
	}

	public TerrainVertex getEast() {
		return east;
	}

	public void setEast(TerrainVertex east) {
		this.east = east;
	}

	public TerrainVertex getWest() {
		return west;
	}

	public void setWest(TerrainVertex west) {
		this.west = west;
	}

	public TerrainVertex getCenter() {
		return center;
	}

	public void setCenter(TerrainVertex center) {
		this.center = center;
	}
	
	private boolean allChildrenEnabled() {
		if( children[0].getCenter().isEnabled() && children[1].getCenter().isEnabled() && children[2].getCenter().isEnabled() && children[3].getCenter().isEnabled()) {
			return true;
		}
		
		return false;
	}
}
