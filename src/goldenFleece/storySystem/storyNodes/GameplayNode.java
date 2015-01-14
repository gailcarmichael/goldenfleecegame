package goldenFleece.storySystem.storyNodes;

import goldenFleece.storySystem.prerequisites.Prerequisite;

import org.simpleframework.xml.Element;

public class GameplayNode extends StoryNode
{
	public GameplayNode(
			@Element(name="id") String nodeID,
			@Element(name="teaserText") String teaserText, 
			@Element(name="eventText") String nodeText,
			@Element(name="nodeType", required=false) NodeType type)
	{
		this(nodeID, null, teaserText, nodeText, type);
	}
	
	public GameplayNode(
			@Element(name="id") String nodeID, 
			@Element(name="teaserText") String teaserText, 
			@Element(name="eventText") String nodeText)
	{
		this(nodeID, null, teaserText, nodeText);
	}
	
	public GameplayNode(
			@Element(name="id") String nodeID, 
			Prerequisite prereq, 
			@Element(name="teaserText") String teaserText, 
			@Element(name="eventText") String nodeText,
			@Element(name="nodeType", required=false) NodeType type)
	{
		super(nodeID, prereq, teaserText, nodeText, type);
	}
	
	public GameplayNode(
			@Element(name="id") String nodeID, 
			Prerequisite prereq, 
			@Element(name="teaserText") String teaserText, 
			@Element(name="eventText") String nodeText)
	{
		super(nodeID, prereq, teaserText, nodeText);
	}

	
	public boolean isGameplay() { return true; }
}
