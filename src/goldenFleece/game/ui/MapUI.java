package goldenFleece.game.ui;

import goldenFleece.game.Map;
import pathfinder.Graph;
import pathfinder.GraphEdge;
import pathfinder.GraphNode;
import processing.core.PApplet;
import processing.core.PImage;

public class MapUI
{
	protected static final int MAP_SCREEN_WIDTH = 850;
	protected static final int MAP_SCREEN_HEIGHT = 624;
	
	protected static final int START_SCROLLING_DISTANCE = 50;
	protected static final float SCROLLING_FACTOR = 0.1f;
	
	protected GoldenFleeceApplet m_parent;
	
	protected Map m_map;
	
	protected PImage m_mapImage;
	protected int m_mapImageX, m_mapImageY;
	
	protected float m_scrollingSpeed;
	protected char m_scrollingDirection;
	
	
	
	MapUI(GoldenFleeceApplet parent, Map map)
	{
		m_parent = parent;
		m_map = map;
		m_mapImage = m_parent.loadImage(m_map.getImageFilename());
		m_mapImageX = m_mapImageY = 0;
		m_scrollingSpeed = 0;
	}

	
	int getMapImageX() { return m_mapImageX; }
	int getMapImageY() { return m_mapImageY; }
	
	int getMapImageWidth() { return m_mapImage.width; }
	int getMapImageHeight() { return m_mapImage.height; }
	
	
	int getMapXOnScreen() { return 0; }
	int getMapYOnScreen() { return 0; }
	
	int getMapWidthOnScreen() { return MAP_SCREEN_WIDTH; }
	int getMapHeightOnScreen() { return MAP_SCREEN_HEIGHT; }
	
	
	void draw()
	{
		m_parent.imageMode(PApplet.CORNER);
		m_parent.image(m_mapImage, getMapImageX(), getMapImageY());
		
		//drawGraph();
	}
	
	void drawGraph()
	{
		Graph graph = m_map.getCurrentMapGraph();
		if (graph != null)
		{
			float xOffset = getMapImageX() - getMapXOnScreen();
			float yOffest = getMapImageY() - getMapYOnScreen();
			
			
			// Draw edges
			m_parent.stroke(0,255,255);
			m_parent.strokeWeight(1);
			for (GraphEdge e : graph.getAllEdgeArray())
			{
				m_parent.line(e.from().xf() + xOffset, e.from().yf() + yOffest,
						      e.to().xf() + xOffset,   e.to().yf() + yOffest);
			}
			
			
			// Draw route
			GraphNode[] route = m_map.getLastShortestPathRoute();
			if (route != null)
			{
				m_parent.stroke(255,0,0);
				for (int i=0; i < route.length-1; i++)
				{
					m_parent.line(route[i].xf() + xOffset, route[i].yf() + yOffest,
							      route[i+1].xf() + xOffset, route[i+1].yf() + yOffest);
				}
			}
			
			m_parent.noStroke();
		}
	}
	
	
	boolean pointIsWithinBounds(int x, int y)
	{
		boolean within = false;
		
		if (x >= getMapXOnScreen() && x <= getMapXOnScreen() + getMapWidthOnScreen() &&
			y >= getMapYOnScreen() && y <= getMapYOnScreen() + getMapHeightOnScreen())
		{
			within = true;
		}
		
		return within;
	}
	
	
	void setScrollingSpeed(int x, int y)
	{
		m_scrollingSpeed = 0;
		
		if (pointIsWithinBounds(x, y))
		{
			int distTop = (int)PApplet.dist(x, y, x, 0);
			int distLeft = (int)PApplet.dist(x, y, 0, y);
			int distRight = (int)PApplet.dist(x, y, getMapXOnScreen() + getMapWidthOnScreen(), y);
			int distBottom = (int)PApplet.dist(x, y, x, getMapYOnScreen() + getMapHeightOnScreen());
			
			int[] distances = {distTop, distLeft, distRight, distBottom};
			
			int minDist = PApplet.min(distances);
			
			if (minDist < START_SCROLLING_DISTANCE)
			{
				m_scrollingSpeed = (START_SCROLLING_DISTANCE - minDist) * SCROLLING_FACTOR;
				
				if (minDist == distTop) m_scrollingDirection = 't';
				else if (minDist == distLeft) m_scrollingDirection = 'l';
				else if (minDist == distRight) m_scrollingDirection = 'r';
				else if (minDist == distBottom) m_scrollingDirection = 'b';
			}
		}
	}
	
	void scrollMap()
	{	
		switch (m_scrollingDirection)
		{
			case 't':
				m_mapImageY = (int)(m_mapImageY + m_scrollingSpeed);
				m_mapImageY = Math.min(m_mapImageY, 0);
				break;
			case 'l':
				m_mapImageX = (int)(m_mapImageX + m_scrollingSpeed);
				m_mapImageX = Math.min(m_mapImageX, 0);
				break;
			case 'r':
				m_mapImageX = (int)(m_mapImageX - m_scrollingSpeed);
				m_mapImageX = Math.max(m_mapImageX, getMapXOnScreen() + getMapWidthOnScreen() - getMapImageWidth());
				break;
			case 'b':
				m_mapImageY = (int)(m_mapImageY - m_scrollingSpeed);
				m_mapImageY = Math.max(m_mapImageY, getMapYOnScreen() + getMapHeightOnScreen() - getMapImageHeight());
				break;
			default:
				break;
		}
		
		m_parent.getShipUI().updateLocationOnScreen();
		m_parent.getStoryNodesUI().updateMapNodesLocationOnScreen();
	}
	
	void centerMapOnShip()
	{
		// Note that the map won't be centered if it causes the edges to go out of bounds
		
		int centerXOnScreen = (getMapWidthOnScreen() - getMapXOnScreen()) / 2 + getMapXOnScreen();
		int centerYOnScreen = (getMapHeightOnScreen() - getMapYOnScreen()) / 2 + getMapYOnScreen();
		
		float shipXOnScreen = m_parent.getGame().getShipX() + getMapImageX();
		float shipYOnScreen = m_parent.getGame().getShipY() + getMapImageY();
		
		m_mapImageX += centerXOnScreen - shipXOnScreen;
		m_mapImageX = Math.min(m_mapImageX, 0);
		m_mapImageX = Math.max(m_mapImageX, getMapXOnScreen() + getMapWidthOnScreen() - getMapImageWidth());
		
		m_mapImageY = (int)(centerYOnScreen - shipYOnScreen);
		m_mapImageY = Math.min(m_mapImageY, 0);
		m_mapImageY = Math.max(m_mapImageY, getMapYOnScreen() + getMapHeightOnScreen() - getMapImageHeight());
	}
}
