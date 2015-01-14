package goldenFleece.game.ui;

import goldenFleece.storySystem.storyNodes.NodeChoice;
import goldenFleece.storySystem.storyNodes.StoryNode;

import java.util.ArrayList;
import java.util.HashMap;

import processing.core.PApplet;

public class StoryNodesUI
{
	protected GoldenFleeceApplet m_parent;
	
	protected static final int NODE_RADIUS = 10;
	protected static final int GENERAL_NODE_SPACING = 50;
	
	protected final int NODE_FILL;
	protected final int NODE_HOVER_FILL;
	protected final int NODE_EVENT_TEXT_FILL;
	
	protected static final int CHOICE_BUTTON_WIDTH_MIN = 100;
	protected static final int CHOICE_BUTTON_WIDTH_MAX = 200;
	protected static final int CHOICE_BUTTON_HEIGHT = 100;
	protected static final int CHOICE_BUTTON_SPACE_BETWEEN = 20;
	
	protected final int CHOICE_BUTTON_FILL;
	protected final int CHOICE_BUTTON_HOVER_FILL;
	
	protected static final int MAP_NODE_FADE_RATE = 1;
	protected static final int MAP_NODE_TIME_TO_LIVE = 15;
	protected static final int MAP_NODE_TIME_TO_START_FADING = 6;
	
	
	protected int m_xOffset, m_yOffset;
	
	protected ArrayList<ChoiceButton> m_choiceButtons;
	
	protected HashMap<StoryNode, Integer> m_visibleNodes;

	

	///////////////////////////////////////////////////////////////////////////
	

	protected class ChoiceButton
	{
		String m_text;
		int m_x, m_y;
		int m_width, m_height;
		
		ChoiceButton(String text, int x, int y, int width, int height)
		{
			m_text = text;
			m_x = x;
			m_y = y;
			m_width = width;
			m_height = height;
		}
		
		boolean pointInside(int pointX, int pointY)
		{
			return (pointX >= m_x && pointX <= m_x + m_width &&
					pointY >= m_y && pointY <= m_y + m_height);
		}
	}
	
	
	///////////////////////////////////////////////////////////////////////////
	
	
	public StoryNodesUI(GoldenFleeceApplet parent)
	{
		m_parent = parent;
		
		NODE_FILL = m_parent.color(240,0,0);
		NODE_HOVER_FILL = m_parent.color(200);
		NODE_EVENT_TEXT_FILL = m_parent.color(240);
		
		CHOICE_BUTTON_FILL = m_parent.color(200);
		CHOICE_BUTTON_HOVER_FILL = m_parent.color(240, 100, 100);
		
		m_choiceButtons = new ArrayList<ChoiceButton>();
		
		m_visibleNodes = new HashMap<StoryNode, Integer>();
	}
	

	///////////////////////////////////////////////////////////////////////////
	
	
	protected int getNodeXOnScreen(StoryNode node)
	{
		int x = node.getX();
		if (node.isMapNode()) x += m_xOffset;
		
		return x;
	}
	
	protected int getNodeYOnScreen(StoryNode node)
	{
		int y = node.getY();
		if (node.isMapNode()) y += m_yOffset;
		
		return y;
	}
	
	protected int getLargeTextX()
	{
		return (m_parent.getMapUI().getMapWidthOnScreen() - m_parent.getMapUI().getMapXOnScreen())/2;
	}
	
	protected int getLargeTextWidth()
	{
		return (int)(m_parent.getMapUI().getMapWidthOnScreen() * 0.75f);
	}
	
	protected int getLargeTextY()
	{
		return (m_parent.getMapUI().getMapHeightOnScreen() - m_parent.getMapUI().getMapYOnScreen())/2;
	}
	
	protected int getLargeTextHeight()
	{
		return (int)(m_parent.getMapUI().getMapHeightOnScreen() * 0.75f);
	}
	

	///////////////////////////////////////////////////////////////////////////
	
	
	protected boolean nodeIsFadingOut(StoryNode node)
	{
		return m_visibleNodes.get(node) <= MAP_NODE_TIME_TO_START_FADING;
	}
	
	
	void updateVisibleNodesAndTimers()
	{
		// Call this when the available nodes might have changed, so
		// the various timers can be updated here
	
		// Update all the timers
		updateTimers();
		
		///
		
		ArrayList<StoryNode> availableNodes = m_parent.getGame().getAllCurrentlyAvailableNodes();
		
		// First, take care of nodes that are currently visible
		for (StoryNode node : availableNodes)
		{
			// Whether the node was available before or not, we should
			// reset its timer to it continues to live / starts to live
			m_visibleNodes.put(node, MAP_NODE_TIME_TO_LIVE);
		}
		
		// Second, take care of any nodes that were were visible but aren't anymore
		ArrayList<StoryNode> removals = new ArrayList<StoryNode>();
		for (StoryNode node : m_visibleNodes.keySet())
		{
			// If the node is not among those the game deems to be available,
			// it means that it is fading out or will be soon.
			if (!availableNodes.contains(node))
			{
				if (node.isMapNode())
				{
					// Only start fading if the node is not already in the process
					// (otherwise, it will be reset to the start of the fade constantly)
					if (!nodeIsFadingOut(node))
					{
						// If a map node is out of range but passes its prerequisite,
						// we should allow it to count down and fade out.  But if it
						// no longer passes its prerequisites either, it should start
						// to fade immediately.
						if (!m_parent.getGame().nodePassesPrerequisites(node))
						{
							m_visibleNodes.put(node, MAP_NODE_TIME_TO_START_FADING);
						}
					}
				}
				else
				{
					// General nodes should just disappear right away if they are no longer
					// available according to the game
					removals.add(node);
				}
			}
		}
		
		for (StoryNode toRemove : removals)
		{
			m_visibleNodes.remove(toRemove);
		}
	}
	
	
	protected void updateTimers()
	{
		ArrayList<StoryNode> removals = new ArrayList<StoryNode>();
		for (StoryNode node : m_visibleNodes.keySet())
		{
			m_visibleNodes.put(node, m_visibleNodes.get(node) - MAP_NODE_FADE_RATE);
			
			if (m_visibleNodes.get(node) <= 0)
			{
				removals.add(node);
			}
		}
		
		for (StoryNode toRemove : removals)
		{
			m_visibleNodes.remove(toRemove);
		}
	}
	

	///////////////////////////////////////////////////////////////////////////
	
	protected boolean nodeShouldBeVisible(StoryNode node)
	{
		boolean visible = false;
		
		if (node.isMapNode())
		{
			visible = m_parent.getMapUI().
					pointIsWithinBounds(getNodeXOnScreen(node), getNodeYOnScreen(node));
		}
		else
		{
			visible = true;
		}
		
		return visible;
	}
	
	void drawNodes()
	{
		// Draw a white rectangle along the bottom of the screen to clear any map bits showing
		m_parent.fill(255);
		m_parent.rectMode(PApplet.CORNER);
		m_parent.rect(0,
				      m_parent.getMapUI().getMapHeightOnScreen(),
				      m_parent.width, 
				      m_parent.height - m_parent.getMapUI().getMapHeightOnScreen());
		
		if (m_parent.getGame().noNodesBeingConsumed() ||
			m_parent.getGame().nodeIsChosenForConsumption())
		{
			// Update the locations of the visible general nodes so they are arranged evenly at the bottom of the map
			updateGeneralNodeLocations();
			
			// Do the drawing
			for (StoryNode node : m_visibleNodes.keySet())
			{				
				// Move this check to drawNode if we want to draw differently according
				// to whether it's consumed (rather than avoid drawing it at all)
				if (nodeShouldBeVisible(node) && !m_parent.getGame().nodeIsConsumed(node))
				{
					drawNode(node);
				}
			}
		}
	}
	
	void drawText()
	{
		if (m_parent.getGame().noNodesBeingConsumed() ||
			m_parent.getGame().nodeIsChosenForConsumption())
		{
			// Ask the game for the currently available (map) story nodes, and draw them
			// here.  Check if the mouse is hovering over any of them, and if so, display
			// their teaser text.
		
			for (StoryNode node : m_visibleNodes.keySet())
			{
				// Move this check to drawNode if we want to draw differently according
				// to whether it's consumed (rather than avoid drawing it at all)
				if (nodeShouldBeVisible(node) && !m_parent.getGame().nodeIsConsumed(node))
				{
					// Check for hover text
					if (m_parent.mouseX >= getNodeXOnScreen(node) - NODE_RADIUS && 
						m_parent.mouseX <= getNodeXOnScreen(node) + NODE_RADIUS &&
						m_parent.mouseY >= getNodeYOnScreen(node) - NODE_RADIUS &&
						m_parent.mouseY <= getNodeYOnScreen(node) + NODE_RADIUS)
					{
						drawHoverText(node);
					}
				}
			}
		}
		else if (m_parent.getGame().nodeIsBeingConsumed())
		{
			drawEventText();
		}
		else if (m_parent.getGame().nodeOutcomeIsBeingApplied())
		{
			if (m_parent.getGame().nodeBeingConsumedHasOutcomeText())
			{
				drawOutcomeText();
			}
		}
	}
	
    /////////////////////////
	
	protected void drawNode(StoryNode node)
	{
		float alpha = 1;
		
		if (m_visibleNodes.containsKey(node))
		{
			alpha = m_visibleNodes.get(node) / (float)(MAP_NODE_TIME_TO_START_FADING);
			if (alpha > 1) alpha = 1;
		}
				
		m_parent.fill(NODE_FILL, (int)(alpha*255));
		m_parent.stroke(0, (int)(alpha*255));
		m_parent.strokeWeight(1);
		m_parent.ellipse(getNodeXOnScreen(node), getNodeYOnScreen(node), NODE_RADIUS*2, NODE_RADIUS*2);
	}
	
	protected void drawHoverText(StoryNode node)
	{
		String teaserText = node.getTeaserText().replace("\n", "").replace("\r", "");
		float width = m_parent.textWidth(teaserText);
		
		m_parent.rectMode(PApplet.CENTER);
		m_parent.textAlign(PApplet.CENTER, PApplet.CENTER);
		m_parent.noStroke();
		
		float height = m_parent.textAscent() + m_parent.textDescent();
		
		m_parent.fill(NODE_HOVER_FILL);
		m_parent.rect(getNodeXOnScreen(node), getNodeYOnScreen(node), width*1.15f, height*1.6f);
		
		m_parent.fill(0);
		m_parent.textSize(14);
		m_parent.text(teaserText, getNodeXOnScreen(node), getNodeYOnScreen(node)-2);
		
		m_parent.rectMode(PApplet.CORNER);
	}
	
	/////////////////////////
	
	protected void drawChoiceButtons()
	{
		// Currently we draw a button no matter how many choices
		// we are offering
		
		//if (m_parent.getGame().getNodeBeingConsumedAvailableChoices().size() > 1)
		{
			for (ChoiceButton b : m_choiceButtons)
			{
				m_parent.fill(CHOICE_BUTTON_FILL);
				if (b.pointInside(m_parent.mouseX, m_parent.mouseY))
				{
					m_parent.fill(CHOICE_BUTTON_HOVER_FILL);
				}
				
				m_parent.rect(b.m_x, b.m_y, b.m_width, b.m_height);
				
				if (b.m_text != null)
				{
					String text = b.m_text.replace("\n", "").replace("\r", "");
					
					m_parent.fill(0);
					m_parent.text(text, b.m_x, b.m_y, b.m_width * 0.95f, b.m_height * 0.95f);
				}
			}
		}
	}
	
    /////////////////////////
	
	protected void drawLargeTextInBox(String text, boolean choiceButtons)	
	{
		m_parent.rectMode(PApplet.CENTER);
		
		m_parent.textAlign(PApplet.CENTER, PApplet.CENTER);
		m_parent.noStroke();
		
		m_parent.fill(NODE_EVENT_TEXT_FILL);
		m_parent.rect(getLargeTextX(), getLargeTextY(),
			      getLargeTextWidth(), getLargeTextHeight());
		
		float textBoxX = getLargeTextX();
		float textBoxY = getLargeTextY();
		
		float textBoxWidth = getLargeTextWidth()*0.9f;
		float textBoxHeight = getLargeTextHeight()*0.9f;
		
		if (choiceButtons)
		{
			textBoxHeight = getLargeTextHeight() - 3*CHOICE_BUTTON_SPACE_BETWEEN - CHOICE_BUTTON_HEIGHT;
			
			textBoxY = getLargeTextY() - getLargeTextHeight()/2;
			textBoxY += CHOICE_BUTTON_SPACE_BETWEEN + textBoxHeight/2;
		}
		
		m_parent.fill(0);
		m_parent.text(text, textBoxX, textBoxY, textBoxWidth, textBoxHeight);
		
		m_parent.rectMode(PApplet.CORNER);
	}
	
	protected void drawEventText()
	{
		// We are currently drawing a button no matter how many choices there are

		StoryNode currNode = m_parent.getGame().getNodeBeingConsumed();
		String text = currNode.getNodeText();
		if (currNode != null)
		{
			//ArrayList<NodeChoice> choices = m_parent.getGame().getNodeBeingConsumedAvailableChoices(); 
			
			//if (choices.size() <= 1)
			//{
			//	drawLargeTextInBox(text, false);
			//}
			//else
			{
				drawLargeTextInBox(text, true);
				drawChoiceButtons();
			}
		}
	}
	
	protected void drawOutcomeText()
	{
		drawLargeTextInBox(m_parent.getGame().getNodeBeingConsumedOutcomeText(), false);
	}

	
	///////////////////////////////////////////////////////////////////////////

	void updateMapNodesLocationOnScreen()
	{
		// Needs to be called when the map scrolls, since we want the nodes to be anchored
		// to the map relative to their x/y positions
		
		m_xOffset = m_parent.getMapUI().getMapImageX() - m_parent.getMapUI().getMapXOnScreen();
		m_yOffset = m_parent.getMapUI().getMapImageY() - m_parent.getMapUI().getMapYOnScreen();
	}
	
	protected void updateGeneralNodeLocations()
	{
		// Filter the list for the general nodes, then arrange these nodes evenly spaced
		
		ArrayList<StoryNode> generalNodes = new ArrayList<StoryNode>();
		
		for (StoryNode n : m_visibleNodes.keySet())
		{
			if (!n.isMapNode())
			{
				generalNodes.add(n);
			}
		}
		
		int widthOfAllNodes = (generalNodes.size() * 2 * NODE_RADIUS) + ((generalNodes.size()-1) * GENERAL_NODE_SPACING); 
		
		int currX = m_parent.getMapUI().getMapWidthOnScreen() / 2 - widthOfAllNodes / 2;
		int currY = m_parent.getMapUI().getMapHeightOnScreen() + (m_parent.height - m_parent.getMapUI().getMapHeightOnScreen()) / 2;
		for (StoryNode generalNode : generalNodes)
		{
			generalNode.setX(currX);
			generalNode.setY(currY);
			
			currX += 2*NODE_RADIUS + GENERAL_NODE_SPACING;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	void setupChoiceButtons()
	{
		m_choiceButtons.clear();
		
		StoryNode currNode = m_parent.getGame().getNodeBeingConsumed();
		if (currNode != null)
		{
			ArrayList<NodeChoice> availableChoices = m_parent.getGame().getNodeBeingConsumedAvailableChoices(); 
			
			int numChoices = availableChoices.size();
			
			float allButtonWidth = getLargeTextWidth() - ((numChoices + 1) * CHOICE_BUTTON_SPACE_BETWEEN);
			float width = allButtonWidth / numChoices;
			width = Math.min(width, CHOICE_BUTTON_WIDTH_MAX);
			width = Math.max(width, CHOICE_BUTTON_WIDTH_MIN);
			
			float allButtonAndSpacesWidth = (width * numChoices) + (CHOICE_BUTTON_SPACE_BETWEEN * (numChoices + 1));
			
			int x = (int)(getLargeTextX() - allButtonAndSpacesWidth/2 + CHOICE_BUTTON_SPACE_BETWEEN);
			int y = getLargeTextY() + getLargeTextHeight()/2 - CHOICE_BUTTON_HEIGHT - CHOICE_BUTTON_SPACE_BETWEEN;
			
			for (int i=0; i < numChoices; i++)
			{
				ChoiceButton newB = new ChoiceButton(
						availableChoices.get(i).getText(),
						x, y, 
						(int)width, CHOICE_BUTTON_HEIGHT);
				
				m_choiceButtons.add(newB);
				
				x += width + CHOICE_BUTTON_SPACE_BETWEEN;
			}
		}
	}
	
	ChoiceButton choiceButtonClicked(int clickX, int clickY)
	{
		ChoiceButton clicked = null;
		
		//ArrayList<NodeChoice> availableChoices = 
		//		m_parent.getGame().getNodeBeingConsumedAvailableChoices();
		
		//if (availableChoices.size() > 1)
		{
			for (ChoiceButton b : m_choiceButtons)
			{
				if (b.pointInside(clickX, clickY))
				{
					clicked = b;
					break;
				}
			}
		}
		
		return clicked;
	}
	
	void handleChoiceButtonClick(ChoiceButton clicked)
	{
		m_parent.getGame().getNodeBeingConsumed().selectChoice(clicked.m_text);
		m_parent.getGame().applyOutcomeToNode();
		
		if (!m_parent.getGame().nodeBeingConsumedHasOutcomeText())
		{
			m_parent.getGame().finishConsumingNode();
		}
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	void handleClick(int clickX, int clickY)
	{
		if (m_parent.getGame().noNodesBeingConsumed() || m_parent.getGame().nodeIsChosenForConsumption())
		{
			// Look for a node that might have been clicked...
			
			boolean clickedNode = false;
			
			for (StoryNode node : m_visibleNodes.keySet())
			{
				if (m_parent.mouseX >= getNodeXOnScreen(node) - NODE_RADIUS && 
					m_parent.mouseX <= getNodeXOnScreen(node) + NODE_RADIUS &&
					m_parent.mouseY >= getNodeYOnScreen(node) - NODE_RADIUS &&
					m_parent.mouseY <= getNodeYOnScreen(node) + NODE_RADIUS)
				{
					clickedNode = true;
					m_parent.getGame().setNodeChosenToConsume(node);
					if (!node.isMapNode())
					{
						m_parent.getGame().startConsumingNode();
					}
					setupChoiceButtons();
					break;
				}
				
				if (!clickedNode)
				{
					m_parent.getGame().cancelConsumingNode();
				}
			}
		}
		else if (m_parent.getGame().nodeIsBeingConsumed())
		{
			ChoiceButton clicked = choiceButtonClicked(clickX, clickY);
			if (clicked != null)
			{
				handleChoiceButtonClick(clicked);
			}
		}
		else if (m_parent.getGame().nodeOutcomeIsBeingApplied())
		{
			m_parent.getGame().finishConsumingNode();
		}
	}

}
