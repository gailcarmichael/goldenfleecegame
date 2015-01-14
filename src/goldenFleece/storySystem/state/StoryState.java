package goldenFleece.storySystem.state;

import goldenFleece.game.GoldenFleeceGame;
import goldenFleece.storySystem.StoryMode;
import goldenFleece.storySystem.prerequisites.Prerequisite;
import goldenFleece.storySystem.storyNodes.StoryNode;

import java.util.ArrayList;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class StoryState
{
	@ElementList(name="storyStateValues")
	protected ArrayList<StateValue> m_stateValues;
	
	@ElementList(name="globalPrerequisites", required=false)
	protected ArrayList<Prerequisite> m_globalPrereqs;
	
	// Annotated in get/set methods
	protected ArrayList<StoryMode> m_storyModes;
	
	@Element(name="defaultStoryMode")
	protected String m_currentStoryModeName;
	
	protected GoldenFleeceGame m_game;

	
	////
	
	public StoryState()
	{
		m_storyModes = null;
		m_stateValues = new ArrayList<StateValue>();
		m_globalPrereqs = new ArrayList<Prerequisite>();
	}
	
	
	////
	
	
	public void setGame(GoldenFleeceGame game)
	{
		m_game = game;
	}
	
	public float getShipX() { return m_game.getShipX(); }
	public float getShipY() { return m_game.getShipY(); }
	
	
	
	@ElementList(name="storyModes")
	public ArrayList<StoryMode> getStoryModes()
	{
		return m_storyModes;
	}
	
	@ElementList(name="storyModes")
	public void setStoryModes(ArrayList<StoryMode> modes)
	{
		m_storyModes = modes;
	}
	
	public StoryMode getStoryModeWithName(String name)
	{
		StoryMode found = null;
		for (StoryMode mode : m_storyModes)
		{
			if (mode.getID().equals(name))
			{
				found = mode;
			}
		}
		return found;
	}
	
	public StoryMode getCurrentMode()
	{
		return getStoryModeWithName(m_currentStoryModeName);
	}
	
	public void setCurrentStoryMode(String name)
	{
		m_currentStoryModeName = name;
		
		if (getStoryModeWithName(name) == null)
		{
			System.out.println("WARNING: Default story mode name was not found.");
		}
		else
		{
			if (m_game != null && m_game.getMap() != null)
			{
				m_game.getMap().updatePathFinderForMap();
			}
		}
		
	}
	
	public void addStoryMode(StoryMode mode)
	{
		if (m_storyModes == null)
		{
			m_storyModes = new ArrayList<StoryMode>();
		}
		
		m_storyModes.add(mode);
	}

	////
	
	public void addStateValue(StateValue v)
	{
		if (!m_stateValues.contains(v))
		{
			m_stateValues.add(v);
		}
	}
	
	
	public void addToDesireValues()
	{
		for (StateValue value : m_stateValues)
		{
			if (value.getHasModifier())
			{
				value.addToValue();
			}
		}
	}
	
	
	public void resetStateValue(StateValue value)
	{	
		int index = m_stateValues.indexOf(value);
		if (index >= 0)
		{
			m_stateValues.get(index).resetValue(value);
		}
	}
	
	
	protected boolean scenesSeenExists()
	{
		for (StateValue v : m_stateValues)
		{
			if (v.getName().equals(GoldenFleeceStateValues.SCENES_SEEN))
			{
				return true;
			}
		}
		
		return false;
	}
	
	
	public StateValue getStateValue(String name)
	{
		StateValue result = null;
		
		// If we haven't made a "scenes seen" state value yet, create it now
		if (name.equalsIgnoreCase(GoldenFleeceStateValues.SCENES_SEEN) && !scenesSeenExists())
		{
			addStateValue(result = new GoldenFleeceStateValues.ScenesSeenStateValue());
		}
		// Otherwise, search for the state value we need by name
		else
		{
			for (StateValue v : m_stateValues)
			{
				if (v.getName().equals(name))
				{
					result = v;
					break;
				}
			}
		}
		
		if (result == null)
		{
			System.out.println("WARNING: Could not find a state value called '" + name + "'");
		}
		
		return result;
	}
	
	////
	
	public boolean addGlobalPrerequisite(Prerequisite p)
	{
		boolean added = false;
		if (!m_globalPrereqs.contains(p))
		{
			m_globalPrereqs.add(p);
			added = true;
		}
		return added;
	}
	
	
	public void removeGlobalPrerequisite(Prerequisite p)
	{
		m_globalPrereqs.remove(p);
	}
	
	
	public boolean nodePassesGlobalPrerequisites(StoryNode node)
	{
		boolean passes = true;
		
		for (Prerequisite p : m_globalPrereqs)
		{
			if (p != null && !p.passesPrerequisite(node, this))
			{
				passes = false;
				break;
			}
		}
		
		return passes;
	}
}
