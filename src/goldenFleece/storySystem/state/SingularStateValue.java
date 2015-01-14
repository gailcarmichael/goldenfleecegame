package goldenFleece.storySystem.state;

import org.simpleframework.xml.Element;


public abstract class SingularStateValue extends StateValue
{
	@Element(name="value")
	protected int m_value;
	
	public SingularStateValue(String name, int value, boolean hasModifier)
	{
		super(name, hasModifier);
		m_value = value;
	}
	
	public SingularStateValue(String name, int value)
	{
		this(name, value, false);
	}
	
	public int getValue() { return m_value; }	
	
	public void setValue(int newValue)
	{
		m_value = newValue;
	}
	
	public boolean singleValue()
	{
		return true;
	}
	
	
	
	public float getModifierScore(StoryState storyState)
	{
		float score = 0;
		
		if (getHasModifier())
		{
			// Find the state value in the player state first
			SingularStateValue storyStateValue = (SingularStateValue)storyState.getStateValue(getName());
			
			if (storyStateValue != null)
			{			
				// Score is calculated as the value in the scene state (i.e.
				// the relevance) times the value in the story state
				// (i.e. the desire to see that value)
		
				int desire = storyStateValue.getValue();
				int relevance = getValue();
				score = desire * relevance;
			}
		}
		
		return score;
	}
	
	public void addToValue()
	{
		if (getHasModifier())
		{
			m_value = (int)(m_value + m_rate);
		}
	}
	
	public void resetValue(StateValue stateToCompare)
	{
		if (stateToCompare.getName().equals(getName()))
		{
			m_value = 1;
		}
	}

}
