package goldenFleece.storySystem.storyNodes;

import goldenFleece.storySystem.state.DictionaryStateValue;
import goldenFleece.storySystem.state.GoldenFleeceStateValues;
import goldenFleece.storySystem.state.ListStateValue;
import goldenFleece.storySystem.state.SingularStateValue;
import goldenFleece.storySystem.state.StoryState;

import java.util.ArrayList;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;



// Represents what happens after a story node is consumed, from
// dialog to changes in the game state
public class Outcome
{
	@Element(name="outcomeText", required=false)
	protected String m_outcomeText;
	
	@Element(name="storyModeModifier", required=false)
	protected StoryModeModifier m_storyModeModifier;
	
	@ElementList(name="modifiers", required=false)
	protected ArrayList<OutcomeModifier> m_outcomeModifiers;

	
	public Outcome(String outcomeText, StoryModeModifier storyModeMod)
	{
		m_outcomeText = outcomeText;
		m_outcomeModifiers = new ArrayList<OutcomeModifier>();
		m_storyModeModifier = storyModeMod;
	}
	
	public Outcome(String outcomeText)
	{
		this(outcomeText, null);
	}
	
	public Outcome()
	{
		this(null);
	}
	
	public final String getOutcomeText() { return m_outcomeText; }
	
	public StoryModeModifier getStoryStateModifier() { return m_storyModeModifier; }
	public void setStoryStateModifier(StoryModeModifier mod) { m_storyModeModifier = mod; }
	
	public void addOutcomeModifier(OutcomeModifier modifier)
	{
		m_outcomeModifiers.add(modifier);
	}
	
	public void applyOutcomeToStoryState(StoryNode node, StoryState state)
	{
		// Add the node to the scenes seen state value
		ListStateValue scenesSeen = (ListStateValue)state.getStateValue(GoldenFleeceStateValues.SCENES_SEEN);
		scenesSeen.addValue(node.getID());
		
		
		// Increment all the desire values in the story state
		// (each state value knows its own rate)
		state.addToDesireValues();
		
		
		// Reset desire values for state values found in the story node
		node.resetRelevantStoryStateValues(state);
		
		
		// Apply each outcome modifier to adjust the story state
		for (OutcomeModifier mod : m_outcomeModifiers)
		{
			mod.applyOutcomeToStoryState(state);
		}
		
		
		// Change story modes if there is something to change
		if (m_storyModeModifier != null)
		{
			m_storyModeModifier.applyModifier(state);
		}
	}
	
	
	//////////////////////////////////////////////////
	
	
	public static class StoryModeModifier
	{
		@Element(name="newModeID")
		protected String m_newModeID; 
		
		public StoryModeModifier(@Element(name="newModeID") String newModeID)
		{
			m_newModeID = newModeID;
		}
		
		protected void applyModifier(StoryState state)
		{
			state.setCurrentStoryMode(m_newModeID);
		}
	}
	
	
	//////////////////////////////////////////////////
	
	public static abstract class OutcomeModifier
	{
		@Element(name="absolute", required=false)
		protected boolean m_absolute;
		
		@Element(name="stateValueName")
		protected String m_stateName;
		
		@Element(name="numericAmountToChange", required=false)
		protected int m_value;
		
		public OutcomeModifier(boolean absolute, String stateName, int value)
		{
			m_absolute = absolute;
			m_stateName = stateName;
			m_value = value;
		}
		
		public abstract void applyOutcomeToStoryState(StoryState state);
		
		String getStateValueName() { return m_stateName; }
	}
	
	
	//////////////////////////////////////////////////
	
	
	public static class SingularStateOutcomeModifier extends OutcomeModifier
	{		
		public SingularStateOutcomeModifier(
				@Element(name="absolute", required=false) boolean absolute, 
				@Element(name="stateValueName") String name, 
				@Element(name="numericAmountToChange", required=false) int value)
		{
			super(absolute, name, value);
		}
		
		public void applyOutcomeToStoryState(StoryState state)
		{
			SingularStateValue value = (SingularStateValue)state.getStateValue(m_stateName);
			
			// TODO: If the value does not exist in the player state, perhaps
			// it should be added? How would we know what kind of state value
			// to create since we need the actual class?
			if (value != null)
			{
				if (m_absolute)
				{
					value.setValue(m_value);
				}
				else
				{
					value.setValue(value.getValue() + m_value);
				}
			}
		}
	}
	
	
	//////////////////////////////////////////////////
	
	
	public static class ListStateOutcomeModifier extends OutcomeModifier
	{
		// This type is a special case: a list state is just a list of Strings
		// that we can add or remove from
		
		@Element(name="valueToAddOrRemove")
		protected String m_value;
		
		@Element(name="addValue")
		protected boolean m_add;
		
		public ListStateOutcomeModifier(
				@Element(name="addValue") boolean add, 
				@Element(name="stateValueName") String name, 
				@Element(name="valueToAddOrRemove") String value)
		{			
			super(false, name, 0);
			m_value = value;
			m_add = add;
		}
		
		public void applyOutcomeToStoryState(StoryState state)
		{
			ListStateValue value = (ListStateValue)state.getStateValue(m_stateName);
			if (m_add)
			{
				value.addValue(m_value);
			}
			else
			{
				value.removeValue(m_value);
			}
		}
	}
	
	
	//////////////////////////////////////////////////
	
	
	public static class DictionaryStateOutcomeModifier extends OutcomeModifier
	{
		@Element(name="valueName")
		protected String m_valueName;
		
		public DictionaryStateOutcomeModifier(
				@Element(name="absolute", required=false) boolean absolute, 
				@Element(name="stateValueName") String stateName, 
				@Element(name="valueName") String valueName, 
				@Element(name="numericAmountToChange", required=false) int value)
		{
			super(absolute, stateName, value);
			m_valueName = valueName;
		}
		
		public void applyOutcomeToStoryState(StoryState state)
		{
			DictionaryStateValue value = (DictionaryStateValue)state.getStateValue(m_stateName);
			if (m_absolute)
			{
				value.modifyValue(m_valueName, m_value);
			}
			else
			{
				value.modifyValue(m_valueName, value.getValue(m_valueName) + m_value);
			}
		}
	}

}
