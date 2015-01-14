package goldenFleece.storySystem.state;

import org.simpleframework.xml.Root;

@Root
public abstract class StateValue
{
	protected String m_name;
	
	protected float m_rate;

	protected boolean m_hasModifier;
	
	
	StateValue(String name)
	{
		this(name, true);
	}
	
	StateValue(String name, 
			   boolean modifier)
	{
		m_name = name;
		
		m_hasModifier = modifier;
		
		// TODO: Rate will eventually be customizable
		m_rate = 1.0f;
	}
	
	
	public float getRate() { return m_rate; }
	
	public boolean getHasModifier() { return m_hasModifier; }
	public void setHasModifier(boolean modifier) { m_hasModifier = modifier; }
	
	public abstract float getModifierScore(StoryState playerState);
	
	public abstract void addToValue();
	public abstract void resetValue(StateValue stateToCompare);
	
	public boolean equals(Object o)
	{
		boolean ret = false;
		
		if (o instanceof StateValue)
		{
			ret = ((StateValue)o).getName().equals(getName());
		}
		
		return ret;
	}
	
	public String getName()
	{
		return m_name;
	}

	
	boolean singleValue() { return false; }
	boolean listOfValues() { return false; }
	boolean dictionaryOfValues() { return false; }
}
