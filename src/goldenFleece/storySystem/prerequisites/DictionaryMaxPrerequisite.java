package goldenFleece.storySystem.prerequisites;

import goldenFleece.storySystem.state.DictionaryStateValue;
import goldenFleece.storySystem.state.StateValue;
import goldenFleece.storySystem.state.StoryState;
import goldenFleece.storySystem.storyNodes.StoryNode;

import org.simpleframework.xml.Element;

public class DictionaryMaxPrerequisite extends SingularMaxPrerequisite
{
	@Element(name="categoryName")
	protected String m_keyToCheck;
	
	public DictionaryMaxPrerequisite(
			@Element(name="associatedStateValue") String stateValueName, 
			@Element(name="maxValueToPass") int maxValue,
			@Element(name="categoryName") String keyToCheck)
	{
		this(null, stateValueName, maxValue, keyToCheck);
	}

	public DictionaryMaxPrerequisite(
			@Element(name="id", required=false) String id,
			@Element(name="associatedStateValue") String stateValueName, 
			@Element(name="maxValueToPass") int maxValue,
			@Element(name="categoryName") String keyToCheck)
	{
		super(id, stateValueName, maxValue);
		m_keyToCheck = keyToCheck;
	}
	
	// Override
	public boolean passesPrerequisite(StoryNode node, StoryState storyState)
	{
		boolean ret = false;
		
		// Find the state value by name among the story node's values
		StateValue found = findMatchingStateValue(storyState);
		
		// If we did find one, check the prerequisite
		if (found != null)
		{
			DictionaryStateValue stateValue = (DictionaryStateValue)found;
			
			// Find the specific value within the dictionary, then
			// get the value and check it
			
			ret = stateValue.getValue(m_keyToCheck) <= m_maxValue;
		}
		
		return ret;
	}

}
