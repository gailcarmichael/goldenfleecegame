package goldenFleece.storySystem.prerequisites;

import goldenFleece.storySystem.state.SingularStateValue;
import goldenFleece.storySystem.state.StateValue;
import goldenFleece.storySystem.state.StoryState;
import goldenFleece.storySystem.storyNodes.StoryNode;

import org.simpleframework.xml.Element;

public class SingularMinPrerequisite extends Prerequisite
{

	@Element(name="minValueToPass")
	protected int m_minValue;
	
	public SingularMinPrerequisite(
			@Element(name="associatedStateValue") String stateValueName, 
			@Element(name="minValueToPass") int minValue)
	{
		this(null, stateValueName, minValue);
	}
	
	public SingularMinPrerequisite(
			@Element(name="id", required=false) String id,
			@Element(name="associatedStateValue") String stateValueName, 
			@Element(name="minValueToPass") int minValue)
	{
		super(id, stateValueName);
		m_minValue = minValue;
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
			ret = (stateValue.getValue() >= m_minValue);
		}
		
		return ret;
	}

}
