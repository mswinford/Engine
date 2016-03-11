package com.MyJogl.GameObject.terrain;

import java.nio.FloatBuffer;
import org.joml.Vector3f;

import com.MyJogl.Camera.Camera;


/*
 * Need to hange how LOD works. right now it is a single set distance and any node not within that distance dosn't get rendered.
 * 
 * 
 */

public class QuadTree {
	private static int LOD = 50; //the LOD distance
	private static int[] LODs = { 20, 30, 50, 100 };
	
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
	
	public int[] update(Camera camera) {
		int[] ret = new int[2];
		update(camera, ret);
		return ret;
	}
	
	//update returns an int array with three values: number of vertices, number of nodes to draw
	public void update(Camera camera, int[] ret) {
		//all base vertices must be enabled or the terrain will be missing whole quadrants at the top level
		if(parent == null) {
			north.setEnabled(true);
			south.setEnabled(true);
			east.setEnabled(true);
			west.setEnabled(true);
			center.setEnabled(true);
		}
		else {
			processVertex(north, camera);
			processVertex(south, camera);
			processVertex(east, camera);
			processVertex(west, camera);
			processVertex(center,  camera);
		}
		
		//if any of the side vertices are enabled, then the middle must be enabled too
		if( !center.isEnabled() && ( north.isEnabled() || south.isEnabled() || east.isEnabled() || west.isEnabled() ) ) {
			center.setEnabled(true);
		}
		
		//start by adding 10 vertices to the vbo size and one to the count of triangle fans to draw
		ret[0] += 10;
		ret[1]++;
			
			//if it is not a leaf node then reduce the number of vertices based on the child nodes that are enabled
		if( children != null ) {
			children[0].update(camera, ret);
			children[1].update(camera, ret);
			children[2].update(camera, ret);
			children[3].update(camera, ret);
			
			//check each child. if the center is enabled, then the sides of this quad that makes up the corners of the child must be enabled
			if( children[0].getCenter().isEnabled() ) {
				west.setEnabled(true);
				south.setEnabled(true);
				center.setEnabled(true);
				ret[0] -= 2;
			}
			if( children[1].getCenter().isEnabled() ) {
				east.setEnabled(true);
				south.setEnabled(true);
				center.setEnabled(true);
				ret[0] -= 2;
			}
			if( children[2].getCenter().isEnabled() ) {
				west.setEnabled(true);
				north.setEnabled(true);
				center.setEnabled(true);
				ret[0] -= 2;
			}
			if( children[3].getCenter().isEnabled() ) {
				east.setEnabled(true);
				north.setEnabled(true);
				center.setEnabled(true);
				ret[0] -= 2;
			}
			
			//if all children are enabled then this node is not responsible for drawing any vertices
			if( allChildrenEnabled() ) {
				ret[0] -= 2;
				ret[1]--;
			}
		}
		
		//if children is null, then this is a leaf node and there are no children below to update.
		//also return if the center is not enabled because if it isn't enabled then none of the sides are enabled
		if( !center.isEnabled() ) {
			ret[0] -= 10;
			ret[1]--;			
		}
		
		return;
	}
	
	private void processVertex(TerrainVertex v, Camera c) {
		float dist = c.getTranslation().distance(v.getPosition());
		
		float x = c.getTranslation().x - v.getX();
		x += x;
		float z = c.getTranslation().z - v.getZ();
		z += z;
		dist = (float)Math.sqrt(x + z);
		
		if( dist > (float)LOD ) {
			v.setEnabled(false);
		}
		else {
			v.setEnabled(true);
		}
	}
	
	public void render(QTModel model) {
		render( model.getVBO(), model.getFirst(), model.getCount(), 0 );
	}
	
	public int render(FloatBuffer vbo, int[] first, int[] count, int index) {
		//render via triangle fans.
		//recursively render each child until it gets to a leaf i.e. the center is disabled
		if( !center.isEnabled() ) return index;
		
		boolean isLeaf = (children == null) ? true : false;
		
		if( !isLeaf ) {
			index = children[0].render(vbo, first, count, index);
			index = children[1].render(vbo, first, count, index);
			index = children[2].render(vbo, first, count, index);
			index = children[3].render(vbo, first, count, index);
		}
		
		if( !isLeaf && allChildrenEnabled() ) {
			return index;
		}
		
		
		first[index] = vbo.position() / 3;
		//start adding to the VBO
		vbo.put(center.getXYZ());
		
		//check each child in CCW order starting with Q1
		//get each corner
		TerrainVertex southeast = new TerrainVertex( south.getPosition().add(east.getPosition(), new Vector3f()).sub(center.getPosition(), new Vector3f()) );
		TerrainVertex northeast = new TerrainVertex( east.getPosition().add(north.getPosition(), new Vector3f()).sub(center.getPosition(), new Vector3f()) );
		TerrainVertex northwest = new TerrainVertex( north.getPosition().add(west.getPosition(), new Vector3f()).sub(center.getPosition(), new Vector3f()) );
		TerrainVertex southwest = new TerrainVertex( west.getPosition().add(south.getPosition(), new Vector3f()).sub(center.getPosition(), new Vector3f()) );
		
		boolean[] childDrawn = { false, false, false, false };
		
		if( isLeaf ) {
			vbo.put(south.getXYZ());
			vbo.put(southeast.getXYZ());
			vbo.put(east.getXYZ());
			vbo.put(northeast.getXYZ());
			vbo.put(north.getXYZ());
			vbo.put(northwest.getXYZ());
			vbo.put(west.getXYZ());
			vbo.put(southwest.getXYZ());
			vbo.put(south.getXYZ());
		}
		else if ( !children[0].getCenter().isEnabled() ) {
			if ( !children[2].getCenter().isEnabled() ) {
				if ( !children[3].getCenter().isEnabled() ) {
					if ( !children[1].getCenter().isEnabled() ) {
						vbo.put(south.getXYZ());
						vbo.put(southeast.getXYZ());
						childDrawn[1] = true;
					}
					vbo.put(east.getXYZ());
					vbo.put(northeast.getXYZ());
					childDrawn[3] = true;
				}
				vbo.put(north.getXYZ());
				vbo.put(northwest.getXYZ());
				childDrawn[2] = true;
			}	
			vbo.put(west.getXYZ());
			vbo.put(southwest.getXYZ());
			if ( !childDrawn[1] && !children[1].getCenter().isEnabled() ) {
				vbo.put(south.getXYZ());
				vbo.put(southeast.getXYZ());
				if( !childDrawn[3] && !children[3].getCenter().isEnabled() ) {
					vbo.put(east.getXYZ());
					vbo.put(northeast.getXYZ());
					vbo.put(north.getXYZ());
				}
				else {
					vbo.put(east.getXYZ());
				}
			}
			else {
				vbo.put(south.getXYZ());
			}
		}
		else if ( !children[2].getCenter().isEnabled() ) {
			if ( !children[3].getCenter().isEnabled() ) {
				if ( !children[1].getCenter().isEnabled() ) {
					vbo.put(south.getXYZ());
					vbo.put(southeast.getXYZ());
				}
				vbo.put(east.getXYZ());
				vbo.put(northeast.getXYZ());
			}
			vbo.put(north.getXYZ());
			vbo.put(northwest.getXYZ());
			vbo.put(west.getXYZ());
		}
		else if ( !children[3].getCenter().isEnabled() ) {
			if ( !children[1].getCenter().isEnabled() ) {
				vbo.put(south.getXYZ());
				vbo.put(southeast.getXYZ());
			}
			vbo.put(east.getXYZ());
			vbo.put(northeast.getXYZ());
			vbo.put(north.getXYZ());
		}
		else if ( !children[1].getCenter().isEnabled() ) {
			vbo.put(south.getXYZ());
			vbo.put(southeast.getXYZ());
			vbo.put(east.getXYZ());
		}
			
		
		count[index] = (vbo.position() / 3) - first[index];
		
		return index + 1;
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
		if( children[0].getCenter().isEnabled() 
			&& children[1].getCenter().isEnabled() 
			&& children[2].getCenter().isEnabled() 
			&& children[3].getCenter().isEnabled() ) {
			return true;
		}
		
		return false;
	}
	
	private int getDepth() {
		QuadTree tmp = this;
		int depth = 1;
		while( (tmp = tmp.parent) != null ) {
			depth++;
		}
		return depth;
	}
	
	private void getNeighbors() {
		
	}
}
