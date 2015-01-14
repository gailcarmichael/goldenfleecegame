package goldenFleece.storySystem.prerequisites;

import goldenFleece.storySystem.state.ListStateValue;
import goldenFleece.storySystem.state.StateValue;
import goldenFleece.storySystem.state.StoryState;
import goldenFleece.storySystem.storyNodes.StoryNode;

import org.simpleframework.xml.Element;

public class ContainsPrerequisite extends Prerequisite
{
	@Element(name="valueToCompare")
	protected String m_valueToCompare;
	
	@Element(name="shouldContainValue")
	protected boolean m_shouldContainValue;
	
	public ContainsPrerequisite(
			@Element(name="associatedStateValue") String stateValueName,
			@Element(name="valueToCompare") String valueToCompare,
			@Element(name="shouldContainValue") boolean shouldContain)
	{
		this(null, stateValueName, valueToCompare, shouldContain);
	}

	public ContainsPrerequisite(
			@Element(name="id", required=false) String id,
			@Element(name="associatedStateValue") String stateValueName,
			@Element(name="valueToCompare") String valueToCompare,
			@Element(name="shouldContainValue") boolean shouldContain)
	{
		super(id , stateValueName);
		m_valueToCompare = valueToCompare;
		m_shouldContainValue = shouldContain;
	}
	
	public boolean passesPrerequisite(StoryNode node, StoryState storyState)
	{
		boolean ret = false;
		
		// Find the state value by name among the story node's values
		StateValue found = findMatchingStateValue(storyState);
		
		// If we did find one, check the prerequisite
		if (found != null)
		{
			ListStateValue stateValue = (ListStateValue)found;
			ret = stateValue.containsValue(m_valueToCompare);
			if (!m_shouldContainValue)
			{
				ret = !ret;
			}
		}
		
		return ret;
	}

}
