
package goldenFleece.game.ui;

import goldenFleece.game.GoldenFleeceGame;
import goldenFleece.storySystem.GoldenFleeceStoryInfo;
import processing.core.PApplet;

public class GoldenFleeceApplet extends PApplet
{
	private static final long serialVersionUID = -1675155212001870633L;
	
	protected static final int FRAME_RATE_UPDATE_NODE_AVAILABILITY = 15;
	
	///////////////////////////////////////////////////////////////////////////
	
	// Model
	protected GoldenFleeceGame m_game;
	
	// UI Stuff
	protected MapUI m_mapUI;
	protected SideBarUI m_sideBarUI;
	protected ShipUI m_shipUI;
	protected StoryNodesUI m_storyNodesUI;
	
	///////////////////////////////////////////////////////////////////////////
	
	
	public GoldenFleeceGame getGame() { return m_game; }
	
	MapUI getMapUI() { return m_mapUI; }
	ShipUI getShipUI() { return m_shipUI; }
	StoryNodesUI getStoryNodesUI() { return m_storyNodesUI; }
	
	///////////////////////////////////////////////////////////////////////////

	protected int calculateWidth() { return getMapUI().getMapWidthOnScreen() + m_sideBarUI.getWidth(); }
	protected int calculateHeight() { return getMapUI().getMapHeightOnScreen() + 60; }
	
	public void setup()
	{
		//m_game = new GoldenFleeceGame(GoldenFleeceStoryInfo.getDefaultStartState());
		//m_game = new GoldenFleeceGame(GoldenFleeceStoryInfo.getDefaultLaterState());
		m_game = new GoldenFleeceGame(GoldenFleeceStoryInfo.readStoryInfoFromFile("./data/defaultStart.xml"));
		
		m_mapUI = new MapUI(this, getGame().getMap());
		m_sideBarUI = new SideBarUI(this);
		
		size(calculateWidth(), calculateHeight());
		background(255);

		m_shipUI = new ShipUI(this, getGame().getShip());
		m_storyNodesUI = new StoryNodesUI(this);
		
		m_mapUI.centerMapOnShip();
		
		m_game.recalculateNTopScoringStoryNodes();
		m_storyNodesUI.updateVisibleNodesAndTimers();
	}
	
	public void draw()
	{
		background(255);
		
		if (frameCount % FRAME_RATE_UPDATE_NODE_AVAILABILITY == 0)
		{
			// Filter nodes by prerequisite and recalculate priority scores 
			if (!m_game.nodeIsBeingConsumed())
			{
				m_game.recalculateNTopScoringStoryNodes();
				m_storyNodesUI.updateVisibleNodesAndTimers();
			}
		}
		
		// Do scrolling-map work
		if (m_mapUI.pointIsWithinBounds(mouseX, mouseY))
		{
			m_mapUI.setScrollingSpeed(mouseX, mouseY);
			m_mapUI.scrollMap();
		}
		
		m_mapUI.draw();
		m_storyNodesUI.drawNodes();
		m_shipUI.draw();
		m_storyNodesUI.drawText();		
		m_sideBarUI.draw();
	}
	
	public void mouseClicked()
	{
		if (m_sideBarUI.wasClicked(mouseX, mouseY))
		{
			m_sideBarUI.handleClick(mouseX, mouseY);
		}
		else
		{
			m_shipUI.handleClick(mouseX, mouseY);
			m_storyNodesUI.handleClick(mouseX, mouseY);
		}
	}

	
	///////////////////////////////////////////////////////////////////////////
	
	public static void main(String args[]) 
	{
	    PApplet.main(GoldenFleeceApplet.class.getCanonicalName());
	}
	
}
