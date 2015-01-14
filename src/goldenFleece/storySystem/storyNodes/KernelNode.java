package goldenFleece.storySystem.storyNodes;

import goldenFleece.storySystem.prerequisites.Prerequisite;

import org.simpleframework.xml.Element;

public class KernelNode extends StoryNode
{
	public KernelNode(
			@Element(name="id") String nodeID,
			@Element(name="teaserText") String teaserText, 
			@Element(name="eventText") String nodeText,
			@Element(name="nodeType", required=false) NodeType type)
	{
		this(nodeID, null, teaserText, nodeText, type);
	}
	
	public KernelNode(
			@Element(name="id") String nodeID, 
			@Element(name="teaserText") String teaserText, 
			@Element(name="eventText") String nodeText)
	{
		this(nodeID, null, teaserText, nodeText);
	}
	
	public KernelNode(
			@Element(name="id") String nodeID, 
			Prerequisite prereq, 
			@Element(name="teaserText") String teaserText, 
			@Element(name="eventText") String nodeText,
			@Element(name="nodeType", required=false) NodeType type)
	{
		super(nodeID, prereq, teaserText, nodeText, type);
	}
	
	public KernelNode(
			@Element(name="id") String nodeID, 
			Prerequisite prereq, 
			@Element(name="teaserText") String teaserText, 
			@Element(name="eventText") String nodeText)
	{
		super(nodeID, prereq, teaserText, nodeText);
	}

	
	public boolean isKernel() { return true; }
}
