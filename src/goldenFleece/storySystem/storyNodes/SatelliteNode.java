package goldenFleece.storySystem.storyNodes;

import org.simpleframework.xml.Element;

import goldenFleece.storySystem.prerequisites.Prerequisite;

public class SatelliteNode extends StoryNode
{
	public SatelliteNode(
			@Element(name="id") String nodeID,
			@Element(name="teaserText") String teaserText, 
			@Element(name="eventText") String nodeText, 
			@Element(name="nodeType", required=false) NodeType type)
	{
		this(nodeID, null, teaserText, nodeText, type);
	}
	
	public SatelliteNode(
			@Element(name="id") String nodeID, 
			@Element(name="teaserText") String teaserText, 
			@Element(name="eventText") String nodeText)
	{
		this(nodeID, null, teaserText, nodeText);
	}

	
	public SatelliteNode(
			@Element(name="id") String nodeID, 
			Prerequisite prereq, 
			@Element(name="teaserText") String teaserText, 
			@Element(name="eventText") String nodeText, 
			@Element(name="nodeType", required=false) NodeType type)
	{
		super(nodeID, prereq, teaserText, nodeText, type);
	}
	
	public SatelliteNode(
			@Element(name="id") String nodeID, 
			Prerequisite prereq, 
			@Element(name="teaserText") String teaserText, 
			@Element(name="eventText") String nodeText)
	{
		super(nodeID, prereq, teaserText, nodeText);
	}

	public boolean isSatellite() { return true; }
}
