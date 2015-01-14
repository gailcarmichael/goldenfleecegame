package goldenFleece.storySystem.storyNodes;

import goldenFleece.storySystem.state.StateValue;
import goldenFleece.storySystem.state.StoryState;

import java.util.Iterator;

public class ModifierEquation
{
	public static int getTotalModifierScore(StoryState storyState, StoryNode node)
	{
		int runningTotal = 0;
		
		// Iterate over the list of state values, and for each value,
		// find and apply the appropriate modifier.
		Iterator<StateValue> it = node.getStateValueIterator();
		while (it.hasNext())
		{
			StateValue nextStateValue = it.next();
			
			// Each state value has a reference to the appropriate
			// modifier. Use that modifier now to update the 
			// running total of the equation so far.
			runningTotal += nextStateValue.getModifierScore(storyState);
		}
		
		return runningTotal;
	}
}
