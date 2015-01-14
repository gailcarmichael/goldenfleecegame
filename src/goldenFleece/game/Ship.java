package goldenFleece.game;

import pathfinder.Graph;
import pathfinder.GraphNode;
import processing.core.PApplet;

public class Ship
{
	protected static final float SHIP_PERCENT_TO_MOVE = 0.2f;
	protected static final float SHIP_SPEED = 0.5f;
	
	protected static final int NODE_DISTANCE_THRESH_CLICKED = 8;
	protected static final int NODE_DISTANCE_THRESH_REACHED = 3;
	
	protected GoldenFleeceGame m_game;
	protected float m_x, m_y;
	
	protected GraphNode m_lastNodeVisited;
	protected GraphNode m_nextNodeToVisit;
	protected GraphNode m_headTowardNode;
	
	protected float m_percentTravelledToNode;
	protected float m_previousX;
	protected float m_previousY;

	public Ship(GoldenFleeceGame game, float x, float y)
	{
		m_game = game;
		m_x = x;
		m_y = y;
		
		m_lastNodeVisited = m_nextNodeToVisit = m_headTowardNode = null;
	}
	
	public Ship(GoldenFleeceGame game)
	{
		this(game,0,0);
	}
	
	public float getX() { return m_x; }
	public float getY() { return m_y; }
	
	public void setX(float x) { m_x = x; }
	public void setY(float y) { m_y = y; }
	
	
	protected GraphNode getClosestNodeTo(float x, float y)
	{
		GraphNode closest = null;
		
		Graph g = m_game.getMap().getCurrentMapGraph();
		if (g != null)
		{
			closest = g.getNodeAt(getX(), getY(), 0, NODE_DISTANCE_THRESH_CLICKED);
		}
		
		return closest;
	}
	
	
	public void headToward(float x, float y)
	{
		// Find the closest node in the graph to head toward
		m_headTowardNode = 
				m_game.getMap().getCurrentMapGraph().getNodeAt(x, y, 0, NODE_DISTANCE_THRESH_CLICKED);
		
		// Calculate the shortest path to this node, if we were able to find one
		if (m_headTowardNode != null)
		{
			// The last visited node starts as our current location
			m_lastNodeVisited = getClosestNodeTo(getX(), getY());
			
			boolean success = 
					m_game.getMap().calculateShortestPath(m_lastNodeVisited, m_headTowardNode);
			
			if (success)
			{
				// The next node to visit should be the second one on the path
				GraphNode[] route = m_game.getMap().getLastShortestPathRoute();
				if (route.length >= 2)
				{
					m_nextNodeToVisit = route[1];
					resetLinearInterpolation();					
				}
			}
			else
			{
				System.out.println("ERROR: Could not find a path to where you want the ship to go.");
			}
		}
	}
	
	protected void resetLinearInterpolation()
	{
		m_previousX = m_x;
		m_previousY = m_y;
		m_percentTravelledToNode =  0;
	}
	
	protected void updateToNextNodeOnPath()
	{
		// Find the next node along the route
		GraphNode[] route = m_game.getMap().getLastShortestPathRoute();
		for (int i = 0; i < route.length; i++)
		{
			if (route[i] == m_lastNodeVisited)
			{
				m_lastNodeVisited = m_nextNodeToVisit;

				if (i < route.length - 1)
				{
					m_nextNodeToVisit = route[i + 1];
					resetLinearInterpolation();
				}
				else
				{
					m_nextNodeToVisit = null;
				}

				break;
			}
		}
	}
	
	
	public void moveShipOneUnit()
	{
		// No movement if there is nowhere to go
		if (m_nextNodeToVisit == null) return;
		

		
		moveTo(((m_nextNodeToVisit.xf() * m_percentTravelledToNode) + (m_previousX * (1 - m_percentTravelledToNode))),
			   ((m_nextNodeToVisit.yf() * m_percentTravelledToNode) + (m_previousY * (1 - m_percentTravelledToNode))));
		
		m_percentTravelledToNode += SHIP_PERCENT_TO_MOVE;
		
		
		
		// Once we've reached our next destination, move on to the next node
		if (PApplet.dist(getX(), getY(), m_nextNodeToVisit.xf(), m_nextNodeToVisit.yf()) 
				< NODE_DISTANCE_THRESH_REACHED)
		{
			// If we have reached the last node, we are at the end of the path
			// and can check whether there is a node where we ended up (if so,
			// consume it)
			if (m_nextNodeToVisit == m_headTowardNode)
			{
				if (m_game.nodeIsChosenForConsumption())
				{
					m_game.startConsumingNode();
				}
				
				m_lastNodeVisited = m_headTowardNode = m_nextNodeToVisit = null;
			}
			
			// Otherwise, we can start heading toward the next node in the path
			else
			{
				updateToNextNodeOnPath();
			}
			
		}
	}
	
	
	protected void moveTo(float x, float y)
	{
		m_game.getProvisions().reduceFromMovement(PApplet.dist(x, y, m_x, m_y));
		
		m_x = x;
		m_y = y;
	}
}
