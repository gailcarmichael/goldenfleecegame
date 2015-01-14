package goldenFleece.storySystem.prerequisites;

import goldenFleece.storySystem.state.SingularStateValue;
import goldenFleece.storySystem.state.StateValue;
import goldenFleece.storySystem.state.StoryState;
import goldenFleece.storySystem.storyNodes.StoryNode;

import org.simpleframework.xml.Element;

public class SingularMaxPrerequisite extends Prerequisite
{
	@Element(name="maxValueToPass")
	protected int m_maxValue;
	
	public SingularMaxPrerequisite(
			@Element(name="associatedStateValue") String stateValueName, 
			@Element(name="maxValueToPass") int maxValue)
	{
		this(null, stateValueName, maxValue);
	}
	
	public SingularMaxPrerequisite(
			@Element(name="id", required=false) String id,
			@Element(name="associatedStateValue") String stateValueName, 
			@Element(name="maxValueToPass") int maxValue)
	{
		super(id, stateValueName);
		m_maxValue = maxValue;
	}

	
	public boolean passesPrerequisite(StoryNode node, StoryState storyState)
	{
		boolean ret = false;
		
		// Find the state value by name among the story node's values
		StateValue found = findMatchingStateValue(storyState);
		
		// If we did find one, check the prerequisite
		if (found != null)
		{
			SingularStateValue stateValue = (SingularStateValue)found;
			ret = (stateValue.getValue() <= m_maxValue);
		}
		
		return ret;
	}

}
