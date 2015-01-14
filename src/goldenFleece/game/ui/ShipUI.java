package goldenFleece.game.ui;

import goldenFleece.game.Ship;
import processing.core.PConstants;
import processing.core.PImage;


public class ShipUI
{
	protected static final String IMAGE_PATH = "../data/images/ship3.png";
	
	protected GoldenFleeceApplet m_parent;
	protected PImage m_image;
	
	protected Ship m_ship;
	protected int m_xOffset, m_yOffset;

	public ShipUI(GoldenFleeceApplet parent, Ship ship)
	{
		m_parent = parent;
		m_image = m_parent.loadImage(IMAGE_PATH);
		m_image.resize(0, 30);
		
		m_ship = ship;
		
		updateLocationOnScreen();
	}
	
	void draw()
	{
		move();
		
		if (m_ship.getX() + m_xOffset >= m_parent.getMapUI().getMapXOnScreen() &&
			m_ship.getX() + m_xOffset <= m_parent.getMapUI().getMapXOnScreen() +  m_parent.getMapUI().getMapWidthOnScreen() &&
			m_ship.getY() + m_yOffset >= m_parent.getMapUI().getMapYOnScreen() &&
			m_ship.getY() + m_yOffset <= m_parent.getMapUI().getMapYOnScreen() +  m_parent.getMapUI().getMapHeightOnScreen())
		{
			m_parent.imageMode(PConstants.CENTER);
			m_parent.image(m_image, m_ship.getX() + m_xOffset, m_ship.getY() + m_yOffset);
		}
	}
	
	boolean wasClicked(int clickX, int clickY)
	{
		return (clickX >= m_ship.getX() + m_xOffset - m_image.width/2 &&
				clickX <= m_ship.getX() + m_xOffset + m_image.width/2 &&
				clickY <= m_ship.getY() + m_yOffset + m_image.height/2 &&
				clickY >= m_ship.getY() + m_yOffset - m_image.height/2);
	}

	
	void handleClick(int x, int y)
	{
		if (m_parent.getGame().noNodesBeingConsumed() || m_parent.getGame().nodeIsChosenForConsumption())
		{
			if (m_parent.getMapUI().pointIsWithinBounds(x, y))
			{
				// Don't forget to update x and y according to the offset...
				
				m_ship.headToward(x - m_xOffset, y - m_yOffset);
			}
		}
	}
	
	
	void updateLocationOnScreen()
	{
		// Needs to be called when the map scrolls, since we want the ship to be anchored
		// to the map relative to its x/y position
		
		m_xOffset = m_parent.getMapUI().getMapImageX() - m_parent.getMapUI().getMapXOnScreen();
		m_yOffset = m_parent.getMapUI().getMapImageY() - m_parent.getMapUI().getMapYOnScreen();
	}
	
	
	protected void move()
	{
		if (m_parent.getGame().nodeIsBeingConsumed() ||
			m_parent.getGame().nodeOutcomeIsBeingApplied())
		{
			return;
		}
		
		m_ship.moveShipOneUnit();
	}
}
