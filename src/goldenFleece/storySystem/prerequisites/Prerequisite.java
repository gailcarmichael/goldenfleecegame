package goldenFleece.storySystem.prerequisites;

import goldenFleece.storySystem.state.StateValue;
import goldenFleece.storySystem.state.StoryState;
import goldenFleece.storySystem.storyNodes.StoryNode;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public abstract class Prerequisite
{	
	@Element(name="id", required=false)
	protected String m_id;
	
	@Element(name="associatedStateValue")
	protected String m_stateValueName;
	
	public Prerequisite(String stateValueName)
	{
		this(null, stateValueName);
	}
	
	public Prerequisite(String id, String stateValueName)
	{
		m_id = id;
		m_stateValueName = stateValueName;
	}
	
	protected StateValue findMatchingStateValue(StoryState storyState)
	{
		return storyState.getStateValue(m_stateValueName);
	}
	
	public abstract boolean passesPrerequisite(StoryNode node, StoryState state);

}
