package goldenFleece.storySystem.state;

import java.util.HashMap;
import java.util.Iterator;

import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;

@Root
public abstract class DictionaryStateValue extends StateValue
{
	@ElementMap(name="categoryValues", entry="storyElement", key="name", attribute=true, inline=true)
	protected HashMap<String, Integer> m_values;
	
	public DictionaryStateValue(
			String name, 
			HashMap<String, Integer> values, 
			boolean hasModifier)
	{
		super(name, hasModifier);
		m_values = values;
	}
	
	public DictionaryStateValue(String name, HashMap<String, Integer> values)
	{
		this(name, values, true);
	}
	
	public DictionaryStateValue(String name)
	{
		this(name, new HashMap<String, Integer>());
	}
	
	public int numItems() { return m_values.size(); }
	
	public Iterator<String> getKeysIterator()
	{
		return m_values.keySet().iterator();
	}
	
	public int getValue(String key)
	{
		int returnValue = 0;
		
		if (m_values.containsKey(key))
		{
			returnValue = m_values.get(key).intValue();
		}
		
		return returnValue;
	}
	
	public void addValue(String newKey, int newValue)
	{
		if (!m_values.containsKey(newKey))
		{
			m_values.put(newKey, newValue);
		}
		else
		{
			System.out.println("Error: Cannot add new value for " + newKey + " because it already exists.");
		}
	}
	
	public void modifyValue(String key, int newValue)
	{
		if (m_values.containsKey(key))
		{
			m_values.put(key, newValue);
		}
		else
		{
			System.out.println("Error: Cannot modify value for " + key + " because it does not exist.");
		}
	}
	
	public void removeValue(String toRemove)
	{
		m_values.remove(toRemove);
	}
	
	public boolean dictionaryOfValues()
	{
		return true;
	}
	
	
	
	public float getModifierScore(StoryState storyState)
	{
		float maxScore = 0;
		
		if (getHasModifier())
		{
			// Find the state value in the player state first
			DictionaryStateValue storyStateValue = (DictionaryStateValue)storyState.getStateValue(getName());
			
			if (storyStateValue != null)
			{			
				// Score is calculated as the value in the scene state (i.e.
				// the relevance) times the value in the story state
				// (i.e. the desire to see that value); the maximum value
				// for this state value's category is the final score
		
				for (String key : m_values.keySet())
				{
					int desire = storyStateValue.getValue(key);
					int relevance = m_values.get(key);
					int score = desire * relevance;
					
					if (score > maxScore)
					{
						maxScore = score;
					}
				}
			}
		}
			
		
		return maxScore;
	}
	
	
	public void addToValue()
	{
		if (getHasModifier())
		{
			for (String key :  m_values.keySet())
			{
				int newValue = m_values.get(key);
				newValue = (int)(newValue + m_rate);
				m_values.put(key, newValue);
			}
		}
	}
	
	public void resetValue(StateValue stateToCompare)
	{		
		if (stateToCompare.dictionaryOfValues())
		{
			DictionaryStateValue dicStateToCompare = (DictionaryStateValue)stateToCompare;
		
			for (String key :  m_values.keySet())
			{
				if (dicStateToCompare.m_values.containsKey(key))
				{
					m_values.put(key, 1);
				}
			}
		}
	}

}
