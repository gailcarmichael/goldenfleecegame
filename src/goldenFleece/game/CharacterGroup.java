package goldenFleece.game;

import goldenFleece.storySystem.state.DictionaryStateValue;
import goldenFleece.storySystem.state.GoldenFleeceStateValues;

import java.util.Iterator;

public class CharacterGroup
{
	public enum GroupType
	{
		Argonauts,
		Gods
	}
	
	protected GoldenFleeceGame m_game;
	protected GroupType m_type;
	
	public CharacterGroup(GoldenFleeceGame game, GroupType type)
	{
		m_game = game;
		m_type = type;
	}
	
	public String getHappinessStateValueName()
	{
		switch (m_type)
		{
			case Argonauts:
				return GoldenFleeceStateValues.ARGONAUTS_HAPPINESS;
			case Gods:
				return GoldenFleeceStateValues.GODS_HAPPINESS;
		}
		
		return "";
	}
	
	public int getNumInGroup(String groupName)
	{ 
		int num = 0;
		
		DictionaryStateValue value = 
				(DictionaryStateValue)m_game.getStateValue(groupName);
		
		if (value != null)
		{
			num = value.numItems();
		}
		
		return num;
	}
	
	public String getNameString(String delimiter)
	{
		String names = "";
		
		DictionaryStateValue value = 
				(DictionaryStateValue)m_game.getStateValue(getHappinessStateValueName());
		
		if (value.numItems() > 0)
		{
			Iterator<String> it = value.getKeysIterator();
			
			while (it.hasNext())
			{
				names += it.next() + delimiter;
			}
			names = names.substring(0, names.length()-1);
		}
		else
		{
			names += "none";
		}
		
		return names;
	}
	
	public String getNameAndHappinessString(String separator, String lineDelimiter)
	{
		String result = "";
		
		DictionaryStateValue value = 
				(DictionaryStateValue)m_game.getStateValue(getHappinessStateValueName());
		
		if (value.numItems() > 0)
		{
			Iterator<String> it = value.getKeysIterator();
			
			while (it.hasNext())
			{
				String key = it.next();
				result += key + separator + value.getValue(key);
				result += lineDelimiter;
			}
			result = result.substring(0, result.length()-1);
		}
		else
		{
			result += "none";
		}
		
		return result;
	}
}
