package goldenFleece.storySystem.storyNodes;

import goldenFleece.storySystem.StoryMode;
import goldenFleece.storySystem.prerequisites.Prerequisite;
import goldenFleece.storySystem.state.GoldenFleeceStateValues;
import goldenFleece.storySystem.state.ListStateValue;
import goldenFleece.storySystem.state.StateValue;
import goldenFleece.storySystem.state.StoryState;

import java.util.ArrayList;
import java.util.Iterator;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import processing.core.PApplet;

@Root
public abstract class StoryNode
{
	enum NodeType
	{
		MapNode, GeneralNode
	}

	protected static int autoGenNodeID = 0;

	protected static final int DISTANCE_THRESHOLD = 125;

	@Element(name = "id")
	protected final String m_nodeID;
	
	@ElementList(name="labels", required=false)
	protected ArrayList<String> m_labels;

	// Annotated in get/set methods
	protected final ArrayList<Prerequisite> m_prereqs;

	@ElementList(name = "sceneStateValues", required = false)
	protected ArrayList<StateValue> m_stateValues;

	@Element(name = "teaserText")
	protected final String m_teaserText;

	@Element(name = "eventText")
	protected final String m_nodeText;

	// Annotated in get/set methods
	protected ArrayList<NodeChoice> m_choices;

	protected int m_nodeChoiceIndex;

	@Element(name = "locationX", required = false)
	protected int m_x;

	@Element(name = "locationY", required = false)
	protected int m_y;

	protected boolean m_consumed;

	@Element(name = "nodeType", required = false)
	protected NodeType m_nodeType;

	// TODO: Include an enum to track the location type - no particular location
	// (Default), a random location,
	// wherever needed, or a specific location

	public StoryNode(String nodeID, Prerequisite prereq, String teaserText,
	        String nodeText)
	{
		m_stateValues = new ArrayList<StateValue>();
		m_nodeID = nodeID;
		
		m_labels = new ArrayList<String>();

		m_prereqs = new ArrayList<Prerequisite>();
		if (prereq != null)
		{
			m_prereqs.add(prereq);
		}

		m_teaserText = teaserText;
		m_nodeText = nodeText;
		m_consumed = false;
		m_nodeType = NodeType.GeneralNode;

		m_choices = new ArrayList<NodeChoice>();
		m_nodeChoiceIndex = -1;
	}

	public StoryNode(String nodeID, Prerequisite prereq, String teaserText,
	        String nodeText, NodeType type)
	{
		this(nodeID, prereq, teaserText, nodeText);
		m_nodeType = type;
	}

	public final String getID()
	{
		return m_nodeID;
	}
	
	public ArrayList<String> getLabels()
	{
		return m_labels;
	}
	
	public void addLabel(String label)
	{
		m_labels.add(label);
	}

	public final String getTeaserText()
	{
		return m_teaserText;
	}

	public final String getNodeText()
	{
		return m_nodeText;
	}

	public final boolean isMapNode()
	{
		return m_nodeType == NodeType.MapNode;
	}

	public int getX()
	{
		return m_x;
	}

	public int getY()
	{
		return m_y;
	}

	public void setX(int x)
	{
		m_x = x;
	}

	public void setY(int y)
	{
		m_y = y;
	}

	public final boolean isConsumed(StoryState state)
	{
		ListStateValue scenesSeen = (ListStateValue)state.getStateValue(GoldenFleeceStateValues.SCENES_SEEN);
		
		return m_consumed || scenesSeen.containsValue(m_nodeID);
	}

	public boolean isKernel()
	{
		return false;
	}

	public boolean isSatellite()
	{
		return false;
	}

	public boolean isGameplay()
	{
		return false;
	}

	////
	
	public String toString()
	{
		return getID();
	}

	////

	public static String autoGenerateNextID()
	{
		autoGenNodeID++;
		return "node" + String.format("%03d", autoGenNodeID);
	}

	////

	public void addPrerequisite(Prerequisite p)
	{
		m_prereqs.add(p);
	}

	@ElementList(name = "prerequisites", required = false)
	public void setPrerequisites(ArrayList<Prerequisite> pList)
	{
		m_prereqs.addAll(pList);
	}

	@ElementList(name = "prerequisites", required = false)
	public ArrayList<Prerequisite> getPrerequisites()
	{
		return m_prereqs;
	}
	
	////
	
	public boolean allowedInCurrentStoryMode(StoryState storyState)
	{
		boolean allowed = false;
		
		StoryMode mode = storyState.getCurrentMode();
		
		if (mode.doesModeHaveLabels())
		{
			ArrayList<String> labels = mode.getRelatedLabels();
	
			if (mode.getLabelsIndicateThoseAllowed())
			{
				// This means that only nodes that have at least one of
				// the labels in the list above may be available
				for (String label : labels)
				{
					if (m_labels.contains(label))
					{
						allowed = true;
						break;
					}
				}
			}
			else
			{
				// In this case, the node must not have any of
				// the labels in the list in order to be available
				allowed = true;
				for (String label : labels)
				{
					if (m_labels.contains(label))
					{
						allowed = false;
						break;
					}
				}
			}
		}
		else
		{
			allowed = true;
		}
		
		return allowed;
	}
	
	////
	
	public boolean passesDistanceRules(StoryState storyState)
	{
		boolean passes = true;
		
		// Check spatial proximity requirements
		if (passes && isMapNode())
		{
			float distance = PApplet.dist(storyState.getShipX(),
			        storyState.getShipY(), getX(), getY());

			if (distance > DISTANCE_THRESHOLD)
			{
				passes = false;
			}
		}
		
		return passes;
	}
	
	////

	public boolean passesPrerequisites(StoryState storyState)
	{
		boolean passes = true;

		if (passes && isConsumed(storyState))
		{
			passes = false;
		}
		
		// Check global prerequisites
		if (passes)
		{
			if (!storyState.nodePassesGlobalPrerequisites(this))
			{
				passes = false;
			}
		}

		// Check node-specific prerequisites
		if (passes)
		{
			for (Prerequisite p : m_prereqs)
			{
				if (p != null && !p.passesPrerequisite(this, storyState))
				{
					passes = false;
					break;
				}
			}
		}

		return passes;
	}

	////

	public void addChoice(NodeChoice newChoice)
	{
		m_choices.add(newChoice);
	}

	@ElementList(name = "choices", required = false)
	public void setChoices(ArrayList<NodeChoice> cList)
	{
		m_choices.addAll(cList);
	}

	@ElementList(name = "choices", required = false)
	public ArrayList<NodeChoice> getChoices()
	{
		return m_choices;
	}

	// //

	public boolean hasSingleAvailableChoice(StoryState state)
	{
		return getCurrentlyAvailableChoices(state).size() <= 1;
	}

	public ArrayList<NodeChoice> getCurrentlyAvailableChoices(StoryState state)
	{
		ArrayList<NodeChoice> available = new ArrayList<NodeChoice>();

		for (NodeChoice c : m_choices)
		{
			boolean passesAll = true;

			for (Prerequisite p : c.getPrerequisites())
			{
				if (!p.passesPrerequisite(this, state))
				{
					passesAll = false;
					break;
				}
			}

			if (passesAll)
			{
				available.add(c);
			}
		}

		return available;
	}

	public boolean selectedChoiceIsValid(StoryState state)
	{
		return getSelectedChoice(state) != null;
	}
	
	public NodeChoice getSelectedChoice(StoryState state)
	{
		NodeChoice selectedChoice = null;
		ArrayList<NodeChoice> availableChoices = getCurrentlyAvailableChoices(state);

		if (m_nodeChoiceIndex >= 0 && m_nodeChoiceIndex < m_choices.size())
		{
			NodeChoice choice = m_choices.get(m_nodeChoiceIndex);
			if (availableChoices.contains(choice))
			{
				selectedChoice = choice;
			}
		}
		else if (hasSingleAvailableChoice(state))
		{
			selectedChoice = availableChoices.get(0);
		}
		
		return selectedChoice;
	}

	public void selectChoice(String choiceText)
	{
		for (NodeChoice choice : m_choices)
		{
			if (choice.getText().equals(choiceText))
			{
				m_nodeChoiceIndex = m_choices.indexOf(choice);
				break;
			}
		}
	}

	////
	
	public boolean hasOutcomeText(StoryState state)
	{
		boolean hasOutputText = false;

		String text = getOutcomeText(state);
		if (text != null && text.length() > 0)
		{
			hasOutputText = true;
		}

		return hasOutputText;
	}

	public final String getOutcomeText(StoryState state)
	{
		String text = "";

		NodeChoice selectedChoice = getSelectedChoice(state);
		
		if (selectedChoice != null)
		{
			Outcome outcome = selectedChoice.getOutcome();
			if (outcome != null)
			{
				text = outcome.getOutcomeText();
			}
		}

		return text;
	}

	public void applyOutcome(StoryState state)
	{
		NodeChoice selectedChoice = getSelectedChoice(state);
		
		if (selectedChoice != null)
		{
			Outcome outcome = selectedChoice.getOutcome();
			if (outcome != null)
			{
				outcome.applyOutcomeToStoryState(this, state);
			}
		}
		else
		{
			System.out.println("WARNING: Unable to apply outcome to "
			        + getID() + " due to invalid choice selection.");
		}

		m_consumed = true;
	}

	////

	public void resetRelevantStoryStateValues(StoryState state)
	{
		for (StateValue stateValue : m_stateValues)
		{
			state.resetStateValue(stateValue);
		}
	}
	
	////

	public void addStateValue(StateValue newValue)
	{
		if (!m_stateValues.contains(newValue))
		{
			m_stateValues.add(newValue);
		}
	}

	public void addStateValues(ArrayList<StateValue> values)
	{
		for (StateValue sv : values)
		{
			addStateValue(sv);
		}
	}

	public Iterator<StateValue> getStateValueIterator()
	{
		return m_stateValues.iterator();
	}

	public StateValue getStateValueWithName(String name)
	{
		StateValue stateValue = null;

		// If we haven't made a "scenes seen" state value yet, create it now
		if (name.equalsIgnoreCase(GoldenFleeceStateValues.SCENES_SEEN)
		        && !m_stateValues.contains(GoldenFleeceStateValues.SCENES_SEEN))
		{
			addStateValue(stateValue = new GoldenFleeceStateValues.ScenesSeenStateValue());
		}
		// Otherwise, search for the state value we need by name
		else
		{
			Iterator<StateValue> it = getStateValueIterator();
			while (it.hasNext())
			{
				StateValue nextValue = it.next();
				if (nextValue.getName().equals(name))
				{
					stateValue = nextValue;
					break;
				}
			}
		}

		return stateValue;
	}
	
	////
	
	public boolean equals(Object node)
	{
		boolean equals = false;
		
		if (node != null && node.getClass().equals(getClass()))
		{
			equals = getID().equals(((StoryNode)node).getID());
		}
		
		return equals;
	}
	
	public int hashCode()
	{
		return getID().hashCode();
	}
}
