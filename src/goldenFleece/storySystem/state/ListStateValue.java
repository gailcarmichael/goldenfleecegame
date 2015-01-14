package goldenFleece.storySystem.state;

import java.util.ArrayList;
import java.util.Iterator;

import org.simpleframework.xml.ElementList;

public abstract class ListStateValue extends StateValue
{
	@ElementList(name="values", inline=true, entry="listItem", type=String.class)
	protected ArrayList<String> m_values;
	
	public ListStateValue(
			String name, 
			ArrayList<String> values, 
			boolean hasModifier)
	{
		super(name, hasModifier);
		m_values = values;
	}
	
	public ListStateValue(String name, ArrayList<String> values)
	{
		this(name, values, false);
	}
	
	public ListStateValue(String name)
	{
		this(name, new ArrayList<String>());
	}
	
	public Iterator<String> getValuesIterator()
	{
		return m_values.iterator();
	}
	
	public boolean containsValue(String toFind)
	{
		return m_values.contains(toFind);
	}
	
	public void addValue(String newValue)
	{
		if (!m_values.contains(newValue))
		{
			m_values.add(newValue);
		}
	}
	
	public void removeValue(String toRemove)
	{
		m_values.remove(toRemove);
	}

	public boolean listOfValues()
	{
		return true;
	}
	

	
	public float getModifierScore(StoryState playerState)
	{
		// List values do not currently support modifiers,
		// but if needed they could follow the dictionary's lead
		return 0;
	}
	
	public void addToValue()
	{
		// List values do not currently support modifiers and
		// cannot act as desire values
	}
	
	public void resetValue(StateValue stateToCompare)
	{
		// List values do not currently support modifiers and
		// cannot act as desire values
	}
}
