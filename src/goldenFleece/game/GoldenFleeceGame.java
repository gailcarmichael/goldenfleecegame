package goldenFleece.game;

import goldenFleece.storySystem.GoldenFleeceStoryInfo;
import goldenFleece.storySystem.StoryMode;
import goldenFleece.storySystem.state.StateValue;
import goldenFleece.storySystem.storyNodes.ModifierEquation;
import goldenFleece.storySystem.storyNodes.NodeChoice;
import goldenFleece.storySystem.storyNodes.StoryNode;

import java.util.ArrayList;


public class GoldenFleeceGame
{
	protected enum NodeConsumptionState
	{
		NoNodes,
		NodeChosenForConsumption,
		ConsumingNode,
		ApplyingOutcome
	}
	
	////
	
	protected final static int NUM_TOP_NODES = 4;
	
	protected final static int SHIP_START_X = 970;
	protected final static int SHIP_START_Y = 1100;
	
	protected Ship m_ship;
	protected Map m_map;
	protected Provisions m_provisions;
	
	protected GoldenFleeceStoryInfo m_storyInfo;
	
	protected CharacterGroup m_argonautGroup;
	protected CharacterGroup m_godGroup;
	
	protected ArrayList<StoryNode> m_topNScoringNodes;
	protected ArrayList<Integer> m_topNScores;
	
	protected StoryNode m_nodeBeingConsumed;
	protected NodeConsumptionState m_nodeConsumptionState;
	
	///////////////////////////////////////////////////////////////////////////
	
	public GoldenFleeceGame(GoldenFleeceStoryInfo storyInfo)
	{
		m_storyInfo = storyInfo;
		
		storyInfo.getStoryState().setGame(this);
		
		m_map = new Map(this);
		
		m_ship = new Ship(this, SHIP_START_X, SHIP_START_Y);
		
		m_provisions = new Provisions(this);
		
		m_argonautGroup = new CharacterGroup(this, CharacterGroup.GroupType.Argonauts);
		m_godGroup = new CharacterGroup(this, CharacterGroup.GroupType.Gods);
		
		m_nodeBeingConsumed = null;
		m_nodeConsumptionState = NodeConsumptionState.NoNodes;
	}
	
	///////////////////////////////////////////////////////////////////////////
	

	public Ship getShip() { return m_ship; }
	
	public float getShipX() { return m_ship.getX(); }
	public float getShipY() { return m_ship.getY(); }
	
	public void setShipLocation(int x, int y)
	{
		m_ship.setX(x);
		m_ship.setY(y);
	}

	public CharacterGroup getArgonautGroup() { return m_argonautGroup; }
	public CharacterGroup getGodGroup() { return m_godGroup; }
	public Provisions getProvisions() { return m_provisions; }
	public Map getMap() { return m_map; }

	
	///////////////////////////////////////////////////////////////////////////
	
	
	public void setNodeChosenToConsume(StoryNode node)
	{
		m_nodeConsumptionState = NodeConsumptionState.NodeChosenForConsumption;
		m_nodeBeingConsumed = node;
	}

	
	public void startConsumingNode()
	{
		m_nodeConsumptionState = NodeConsumptionState.ConsumingNode;
	}
	
	
	public void applyOutcomeToNode()
	{
		m_nodeBeingConsumed.applyOutcome(m_storyInfo.getStoryState());
		if (m_nodeBeingConsumed.selectedChoiceIsValid(m_storyInfo.getStoryState()))
		{
			m_nodeConsumptionState = NodeConsumptionState.ApplyingOutcome;
		}
		else
		{
			finishConsumingNode();
		}
	}
	
	
	public void finishConsumingNode()
	{
		// If this code changes, check cancelConsumingNode() to see if
		// it needs to be updated as well
		
		m_nodeConsumptionState = NodeConsumptionState.NoNodes;
		m_nodeBeingConsumed = null;
		
		//printTopScoringNodesAndScores();
	}
	
	
	public void cancelConsumingNode()
	{
		finishConsumingNode();
	}
	
	
	///////////////////////////////////////////////////////////////////////////
	
	public boolean noNodesBeingConsumed()
	{
		return m_nodeConsumptionState == NodeConsumptionState.NoNodes;
	}
	
	
	public boolean nodeIsChosenForConsumption()
	{
		return m_nodeConsumptionState == NodeConsumptionState.NodeChosenForConsumption;
	}
	
	
	public StoryNode getNodeBeingConsumed()
	{
		return m_nodeBeingConsumed;
	}
	
	public ArrayList<NodeChoice> getNodeBeingConsumedAvailableChoices()
	{
		return m_nodeBeingConsumed.getCurrentlyAvailableChoices(m_storyInfo.getStoryState());
	}
		
	public boolean nodeBeingConsumedHasOutcomeText()
	{
		return m_nodeBeingConsumed.hasOutcomeText(m_storyInfo.getStoryState());
	}
	
	public String getNodeBeingConsumedOutcomeText()
	{
		return m_nodeBeingConsumed.getOutcomeText(m_storyInfo.getStoryState());
	}
	
	
	public boolean nodeIsBeingConsumed()
	{
		return m_nodeConsumptionState == NodeConsumptionState.ConsumingNode;
	}
	
	public boolean nodeOutcomeIsBeingApplied()
	{
		return m_nodeConsumptionState == NodeConsumptionState.ApplyingOutcome;
	}
	

	///////////////////////////////////////////////////////////////////////////
	
	
	public ArrayList<StoryNode> getAvailableMapStoryNodes()
	{
		ArrayList<StoryNode> list = new ArrayList<StoryNode>();
		
		for (StoryNode next : m_storyInfo.getMapStoryNodes())
		{
			if (next.allowedInCurrentStoryMode(m_storyInfo.getStoryState())
			    && next.passesDistanceRules(m_storyInfo.getStoryState())
				&& next.passesPrerequisites(m_storyInfo.getStoryState())
				&& !next.isGameplay())
			{
				list.add(next);
			}
		}
		
		return list;
	}
	
	public ArrayList<StoryNode> getAvailableGeneralStoryNodes()
	{
		ArrayList<StoryNode> list = new ArrayList<StoryNode>();
		
		for (StoryNode next : m_storyInfo.getGeneralStoryNodes())
		{
			if (next.allowedInCurrentStoryMode(m_storyInfo.getStoryState())
				&& next.passesPrerequisites(m_storyInfo.getStoryState())
				&& !next.isGameplay())
			{
				list.add(next);
			}
		}

		
		return list;
	}
	
	public ArrayList<StoryNode> getAvailableGameplayNodes()
	{
		ArrayList<StoryNode> mapAndGeneralNodes = new ArrayList<StoryNode>(); 
		mapAndGeneralNodes.addAll(m_storyInfo.getGeneralStoryNodes());
		mapAndGeneralNodes.addAll(m_storyInfo.getMapStoryNodes());
		
		ArrayList<StoryNode> gameplayNodes = new ArrayList<StoryNode>();
		
		for (StoryNode next : mapAndGeneralNodes)
		{
			if (next.isGameplay() 
				&& next.passesDistanceRules(m_storyInfo.getStoryState())
				&& next.passesPrerequisites(m_storyInfo.getStoryState())
				&& next.allowedInCurrentStoryMode(m_storyInfo.getStoryState()))
			{
				gameplayNodes.add(next);
			}
		}
		
		return gameplayNodes;
	}
	
	
	public ArrayList<StoryNode> getAllCurrentlyAvailableNodes()
	{
		ArrayList<StoryNode> nodeList = new ArrayList<StoryNode>();
		
		nodeList.addAll(getNTopScoringStoryNodes());
		nodeList.addAll(getAvailableGameplayNodes());
		
		return nodeList;
	}
	
	
	protected ArrayList<StoryNode> getNTopScoringStoryNodes()
	{
		return m_topNScoringNodes;
	}
	
	
	protected StoryNode getTopNNodesFromList(
			int numNodes,
			ArrayList<StoryNode> nodeList, 
			ArrayList<StoryNode> topNodesList, 
			ArrayList<Integer> topScoresList)
	{
		int nodesAdded = 0;
		
		StoryNode topKernel = null;
		int topKernelScore = -1;
		
		ArrayList<StoryNode> tempTopNodesList = new ArrayList<StoryNode>();
		ArrayList<Integer> tempTopScoresList = new ArrayList<Integer>();
		
		for (StoryNode node : nodeList)
		{
			if (node.isConsumed(m_storyInfo.getStoryState())) continue; // never add consumed nodes
			if (node.isGameplay()) continue; // don't add game play nodes - they will all be added later
			
			int nodeScore = ModifierEquation.getTotalModifierScore(m_storyInfo.getStoryState(), node);
			
			if (node.isKernel() && nodeScore > topKernelScore)
			{
				topKernel = node;
				topKernelScore = nodeScore;
			}
			
			// If there are fewer than N nodes in the output list,
			// just add this one in directly
			if (nodesAdded < numNodes)
			{
				tempTopNodesList.add(node);
				tempTopScoresList.add(nodeScore);
				nodesAdded++;
			}
			// Otherwise, check if this node's score is higher than the lowest
			// score currently in the list
			else
			{
				// Find the minimum score
				int minIndex = 0;
				for (int i=1; i < tempTopNodesList.size(); i++)
				{
					if (tempTopScoresList.get(i) < tempTopScoresList.get(minIndex))
					{
						minIndex = i;
					}
				}
				
				// Check if the current node has a larger score, and if so,
				// replace the old minimum
				if (nodeScore > tempTopScoresList.get(minIndex))
				{
					tempTopNodesList.remove(minIndex);
					tempTopScoresList.remove(minIndex);
					
					tempTopNodesList.add(node);
					tempTopScoresList.add(nodeScore);
				}
			}
		}
		
		// Check whether the top kernel is in the list; if not, add it
		if (topKernel != null && !tempTopNodesList.contains(topKernel))
		{
			tempTopNodesList.add(topKernel);
			tempTopScoresList.add(topKernelScore);
		}
		
		
		topNodesList.addAll(tempTopNodesList);
		topScoresList.addAll(tempTopScoresList);
		

		// Note: Right now we're not using this returned value, but it could be used to
		// check whether we added two top kernels (one general, and one map).  Right now,
		// it is most likely safe to assume that there wouldn't be one of each available
		// at any given time.
		return topKernel;
	}
	
	
	public void recalculateNTopScoringStoryNodes()
	{
		ArrayList<StoryNode> topNodesList = new ArrayList<StoryNode>();
		ArrayList<Integer> topScoresList = new ArrayList<Integer>();

		// First try to get the number of nodes needed from the map nodes
		getTopNNodesFromList(NUM_TOP_NODES,  getAvailableMapStoryNodes(), topNodesList, topScoresList);
		
		// If there weren't enough map nodes, fill in with general nodes
		if (topNodesList.size() < NUM_TOP_NODES)
		{
			getTopNNodesFromList(
					NUM_TOP_NODES - topNodesList.size(), getAvailableGeneralStoryNodes(),
                    topNodesList, topScoresList);
		}
		
			
		m_topNScoringNodes = topNodesList;
		m_topNScores = topScoresList;
	}
	
	
	protected void printTopScoringNodesAndScores()
	{
		for (StoryNode node : m_topNScoringNodes)
		{
			float nodeScore = m_topNScores.get(m_topNScoringNodes.indexOf(node));
			
			String t = node.getID() + " ";
			
			if (node.isKernel()) t += " [k]";
			else t += " [s]";
			
			t += ": " + nodeScore;
			
			System.out.println(t);
		}
		
		System.out.println();
	}
	
	
	///////////////////////////////////////////////////////////////////////////
	
	
	public StoryMode getCurrentStoryMode()
	{
		return m_storyInfo.getStoryState().getCurrentMode();
	}
	
	public StateValue getStateValue(String name)
	{
		return m_storyInfo.getStoryState().getStateValue(name);

	}
	
	public boolean nodeIsConsumed(StoryNode node)
	{
		return node.isConsumed(m_storyInfo.getStoryState());
	}
	
	public boolean nodePassesDistanceRules(StoryNode node)
	{
		return node.passesDistanceRules(m_storyInfo.getStoryState());
	}
	
	public boolean nodePassesPrerequisites(StoryNode node)
	{
		return node.passesPrerequisites(m_storyInfo.getStoryState());
	}
}
