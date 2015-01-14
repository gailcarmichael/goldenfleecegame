package goldenFleece.storySystem.storyNodes;

import goldenFleece.storySystem.prerequisites.ContainsPrerequisite;
import goldenFleece.storySystem.prerequisites.Prerequisite;
import goldenFleece.storySystem.state.DictionaryStateValue;
import goldenFleece.storySystem.state.GoldenFleeceStateValues;

import java.util.ArrayList;

public class GoldenFleeceMapStoryNodes
{
	
	protected static final float MAP_ZOOM_FACTOR_X = 3.205f;
	protected static final float MAP_ZOOM_FACTOR_Y = 2.137f;

	private GoldenFleeceMapStoryNodes()
	{
		// prevent instantiation
	}
	
	///////////////////////////////////////////////////////////////////////////
		
	
	// The nodes returned here are a sample set for testing. The nodes used
	// in the game ultimately come from an XML file.
	public static ArrayList<StoryNode> getMapStoryNodes()
	{
		ArrayList<StoryNode> mapNodes = new ArrayList<StoryNode>();
		
		Prerequisite prereq;
		String teaserText;
		String eventText;
		DictionaryStateValue stateValue;
		Outcome outcome;
		NodeChoice choice;
		StoryNode node;
		
		/////////////
		
		teaserText = "Gameplay Example";		
		eventText = "<Event text>";
		
		outcome = new Outcome("<Outcome text>");
		
		node = new GameplayNode("Gameplay", teaserText, eventText, StoryNode.NodeType.MapNode);
		
		choice = new NodeChoice("", outcome);
		node.addChoice(choice);
		
		node.setX(875);
		node.setY(1105);
		
		mapNodes.add(node);
		
		/////////////

		teaserText = "Gather provisions at Skiathos";		
		eventText = "<Event text>";
		
		outcome = new Outcome("<Outcome text>");
		
		node = new SatelliteNode("GatherProvisionsSkiathos", teaserText, eventText, StoryNode.NodeType.MapNode);
		
		choice = new NodeChoice("", outcome);
		node.addChoice(choice);
		
		// Only show if we have already set sail
		prereq = new ContainsPrerequisite(GoldenFleeceStateValues.SCENES_SEEN, "SetSail", true);
		node.addPrerequisite(prereq);
		
		node.setX((int)(280 * MAP_ZOOM_FACTOR_X));
		node.setY((int)(305 * MAP_ZOOM_FACTOR_Y));
		
		stateValue = new GoldenFleeceStateValues.ThemesStateValue();
		stateValue.addValue(GoldenFleeceStateValues.THEME_HEROISM, 1);
		node.addStateValue(stateValue);

		stateValue = new GoldenFleeceStateValues.CharactersStateValue();
		stateValue.addValue(GoldenFleeceStateValues.CHARACTER_ATALANTA, 2);
		node.addStateValue(stateValue);
		
		mapNodes.add(node);
		
		/////////////

		teaserText = "The haunted island";		
		eventText = "<Event text>";
		
		outcome = new Outcome("<Outcome text>");
		
		node = new SatelliteNode("HauntedIsland", teaserText, eventText, StoryNode.NodeType.MapNode);
		
		choice = new NodeChoice("", outcome);
		node.addChoice(choice);
		
		// Only show if we have already set sail
		prereq = new ContainsPrerequisite(GoldenFleeceStateValues.SCENES_SEEN, "SetSail", true);
		node.addPrerequisite(prereq);
		
		node.setX((int)(350 * MAP_ZOOM_FACTOR_X));
		node.setY((int)(280 * MAP_ZOOM_FACTOR_Y));
		
		stateValue = new GoldenFleeceStateValues.ThemesStateValue();
		stateValue.addValue(GoldenFleeceStateValues.THEME_TREACHERY_HONOR, 2);
		stateValue.addValue(GoldenFleeceStateValues.THEME_HEROISM, 1);
		node.addStateValue(stateValue);
		
		mapNodes.add(node);
		
		/////////////

		teaserText = "The chariot races";		
		eventText = "<Event text>";
		
		outcome = new Outcome("<Outcome text>");
		
		node = new SatelliteNode("ChariotRaces", teaserText, eventText, StoryNode.NodeType.MapNode);
		
		choice = new NodeChoice("", outcome);
		node.addChoice(choice);
		
		// Only show if we have already set sail
		prereq = new ContainsPrerequisite(GoldenFleeceStateValues.SCENES_SEEN, "SetSail", true);
		node.addPrerequisite(prereq);
		
		node.setX((int)(210 * MAP_ZOOM_FACTOR_X));
		node.setY((int)(250 * MAP_ZOOM_FACTOR_Y));
		
		stateValue = new GoldenFleeceStateValues.ThemesStateValue();
		stateValue.addValue(GoldenFleeceStateValues.THEME_WILL_OF_GODS, 1);
		stateValue.addValue(GoldenFleeceStateValues.THEME_HEROISM, 1);
		node.addStateValue(stateValue);

		stateValue = new GoldenFleeceStateValues.CharactersStateValue();
		stateValue.addValue(GoldenFleeceStateValues.CHARACTER_EPHEMUS, 3);
		node.addStateValue(stateValue);
		
		mapNodes.add(node);
		
		/////////////

		teaserText = "Gather provisions at Sestos";		
		eventText = "<Event text>";
		
		outcome = new Outcome("<Outcome text>");
		
		node = new SatelliteNode("GatherProvisionsSestos", teaserText, eventText, StoryNode.NodeType.MapNode);
		
		choice = new NodeChoice("", outcome);
		node.addChoice(choice);
		
		// Only show if we have already set sail
		prereq = new ContainsPrerequisite(GoldenFleeceStateValues.SCENES_SEEN, "SetSail", true);
		node.addPrerequisite(prereq);
		
		node.setX((int)(390 * MAP_ZOOM_FACTOR_X));
		node.setY((int)(190 * MAP_ZOOM_FACTOR_Y));
		
		stateValue = new GoldenFleeceStateValues.ThemesStateValue();
		stateValue.addValue(GoldenFleeceStateValues.THEME_WILL_OF_GODS, 2);
		node.addStateValue(stateValue);

		stateValue = new GoldenFleeceStateValues.CharactersStateValue();
		stateValue.addValue(GoldenFleeceStateValues.CHARACTER_APHRODITE, 2);
		node.addStateValue(stateValue);
		
		mapNodes.add(node);
		
		/////////////

		teaserText = "Stop at Troy";		
		eventText = "<Event text>";
		
		outcome = new Outcome("<Outcome text>");
		
		node = new SatelliteNode("StopAtTroy", teaserText, eventText, StoryNode.NodeType.MapNode);
		
		choice = new NodeChoice("", outcome);
		node.addChoice(choice);
		
		// Only show if we have already set sail
		prereq = new ContainsPrerequisite(GoldenFleeceStateValues.SCENES_SEEN, "SetSail", true);
		node.addPrerequisite(prereq);
		
		node.setX((int)(360 * MAP_ZOOM_FACTOR_X));
		node.setY((int)(225 * MAP_ZOOM_FACTOR_Y));
		
		stateValue = new GoldenFleeceStateValues.ThemesStateValue();
		stateValue.addValue(GoldenFleeceStateValues.THEME_HEROISM, 2);
		stateValue.addValue(GoldenFleeceStateValues.THEME_WILL_OF_GODS, 2);
		node.addStateValue(stateValue);

		stateValue = new GoldenFleeceStateValues.CharactersStateValue();
		stateValue.addValue(GoldenFleeceStateValues.CHARACTER_ATHENA, 2);
		stateValue.addValue(GoldenFleeceStateValues.CHARACTER_ZEUS, 1);
		node.addStateValue(stateValue);
		
		mapNodes.add(node);
		
		/////////////

		teaserText = "Pass the Symplegades - Hercules";		
		eventText = "<Event text>";
		
		outcome = new Outcome("<Outcome text>");
		
		node = new KernelNode("PassSymplegades - Hercules", teaserText, eventText, StoryNode.NodeType.MapNode);
		
		choice = new NodeChoice("", outcome);
		node.addChoice(choice);
		
		// Only show if we have already set sail
		prereq = new ContainsPrerequisite(GoldenFleeceStateValues.SCENES_SEEN, "SetSail", true);
		node.addPrerequisite(prereq);
		
		node.setX((int)(485 * MAP_ZOOM_FACTOR_X));
		node.setY((int)(155 * MAP_ZOOM_FACTOR_Y));
		
		stateValue = new GoldenFleeceStateValues.ThemesStateValue();
		stateValue.addValue(GoldenFleeceStateValues.THEME_HEROISM, 2);
		node.addStateValue(stateValue);

		stateValue = new GoldenFleeceStateValues.CharactersStateValue();
		stateValue.addValue(GoldenFleeceStateValues.CHARACTER_HERCULES, 3);
		node.addStateValue(stateValue);
		
		mapNodes.add(node);
		
		/////////////

		teaserText = "King Phineas";		
		eventText = "<Event text>";
		
		outcome = new Outcome("<Outcome text>");
		
		node = new SatelliteNode("KingPhineas", teaserText, eventText, StoryNode.NodeType.MapNode);
		
		choice = new NodeChoice("", outcome);
		node.addChoice(choice);
		
		// Only show if we have already set sail
		prereq = new ContainsPrerequisite(GoldenFleeceStateValues.SCENES_SEEN, "SetSail", true);
		node.addPrerequisite(prereq);
		
		node.setX((int)(430 * MAP_ZOOM_FACTOR_X));
		node.setY((int)(190 * MAP_ZOOM_FACTOR_Y));
		
		stateValue = new GoldenFleeceStateValues.ThemesStateValue();
		stateValue.addValue(GoldenFleeceStateValues.THEME_TREACHERY_HONOR, 2);
		stateValue.addValue(GoldenFleeceStateValues.THEME_WILL_OF_GODS, 2);
		stateValue.addValue(GoldenFleeceStateValues.THEME_HEROISM, 4);
		node.addStateValue(stateValue);

		stateValue = new GoldenFleeceStateValues.CharactersStateValue();
		stateValue.addValue(GoldenFleeceStateValues.CHARACTER_HERCULES, 3);
		node.addStateValue(stateValue);
		
		mapNodes.add(node);
		
		/////////////

		teaserText = "The Dolionian Games";		
		eventText = "<Event text>";
		
		outcome = new Outcome("<Outcome text>");
		
		node = new SatelliteNode("DolionianGames", teaserText, eventText, StoryNode.NodeType.MapNode);
		
		choice = new NodeChoice("", outcome);
		node.addChoice(choice);
		
		// Only show if we have already set sail
		prereq = new ContainsPrerequisite(GoldenFleeceStateValues.SCENES_SEEN, "SetSail", true);
		node.addPrerequisite(prereq);
		
		node.setX((int)(480 * MAP_ZOOM_FACTOR_X));
		node.setY((int)(200 * MAP_ZOOM_FACTOR_Y));
		
		stateValue = new GoldenFleeceStateValues.ThemesStateValue();
		stateValue.addValue(GoldenFleeceStateValues.THEME_TREACHERY_HONOR, 2);
		stateValue.addValue(GoldenFleeceStateValues.THEME_HEROISM, 3);
		node.addStateValue(stateValue);

		stateValue = new GoldenFleeceStateValues.CharactersStateValue();
		stateValue.addValue(GoldenFleeceStateValues.CHARACTER_HERCULES, 3);
		node.addStateValue(stateValue);
		
		mapNodes.add(node);
		
		/////////////
		
		
		return mapNodes;
	}

}
