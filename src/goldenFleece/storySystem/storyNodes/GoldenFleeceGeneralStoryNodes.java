package goldenFleece.storySystem.storyNodes;

import goldenFleece.storySystem.prerequisites.ContainsPrerequisite;
import goldenFleece.storySystem.prerequisites.Prerequisite;
import goldenFleece.storySystem.prerequisites.SingularMaxPrerequisite;
import goldenFleece.storySystem.prerequisites.SingularMinPrerequisite;
import goldenFleece.storySystem.state.DictionaryStateValue;
import goldenFleece.storySystem.state.GoldenFleeceStateValues;
import goldenFleece.storySystem.storyNodes.Outcome.StoryModeModifier;

import java.util.ArrayList;

public final class GoldenFleeceGeneralStoryNodes
{

	private GoldenFleeceGeneralStoryNodes()
	{
		// prevent this class from being instantiated
	}

	// The nodes returned here are a sample set for testing. The nodes used
	// in the game ultimately come from an XML file.
	public static ArrayList<StoryNode> getGeneralStoryNodes()
	{
		ArrayList<StoryNode> generalNodes= new ArrayList<StoryNode>();
		
		Prerequisite prereq;
		String teaserText;
		String eventText;
		DictionaryStateValue stateValue;
		Outcome outcome;
		NodeChoice choice;
		StoryNode node;
		
		/////////////
		
		teaserText = "Sample";
		eventText = "An example of lots of text.  We don't want to skimp here because we want to see the maximum text.  An example of lots of text.  We don't want to skimp here because we want to see the maximum text.  An example of lots of text.  We don't want to skimp here because we want to see the maximum text.  An example of lots of text.  We don't want to skimp here because we want to see the maximum text.  An example of lots of text.  We don't want to skimp here because we want to see the maximum text.  An example of lots of text.  We don't want to skimp here because we want to see the maximum text.  An example of lots of text.  We don't want to skimp here because we want to see the maximum text.  An example of lots of text.  We don't want to skimp here because we want to see the maximum text.  An example of lots of text.  We don't want to skimp here because we want to see the maximum text.  An example of lots of text.  We don't want to skimp here because we want to see the maximum text.  An example of lots of text.  We don't want to skimp here because we want to see the maximum text.  An example of lots of text.  We don't want to skimp here because we want to see the maximum text.  An example of lots of text.  We don't want to skimp here because we want to see the maximum text.  An example of lots of text.  We don't want to skimp here because we want to see the maximum text.  An example of lots of text.  We don't want to skimp here because we want to see the maximum text.  An example of lots of text.  We don't want to skimp here because we want to see the maximum text.  An example of lots of text.  We don't want to skimp here because we want to see the maximum text.  An example of lots of text.  We don't want to skimp here because we want to see the maximum text.  An example of lots of text.  We don't want to skimp here because we want to see the maximum text.  An example of lots of text.  We don't want to skimp here because we want to see the maximum text.  An example of lots of text.  We don't want to skimp here because we want to see the maximum text.  An example of lots of text.  We don't want to skimp here because we want to see the maximum text.  An example of lots of text.  We don't want to skimp here because we want to see the maximum text.  An example of lots of text.  We don't want to skimp here because we want to see the maximum text.  An example of lots of text.  We don't want to skimp here because we want to see the maximum text.  An example of lots of text.  We don't want to skimp here because we want to see the maximum text.  An example of lots of text.  We don't want to skimp here because we want to see the maximum text.  An example of lots of text.  We don't want to skimp here because we want to see the maximum text.  An example of lots of text.  We don't want to skimp here because we want to see the maximum text.  ";
		
		node = new SatelliteNode("Sample", teaserText, eventText);
	
		node.addLabel("treasure island");
		
		outcome = new Outcome("<Outcome text 1>");
		
		outcome.addOutcomeModifier(new Outcome.SingularStateOutcomeModifier(
				false, GoldenFleeceStateValues.CREW_MORALE, -1));
		
		outcome.addOutcomeModifier(new Outcome.DictionaryStateOutcomeModifier(
				true, GoldenFleeceStateValues.ARGONAUTS_HAPPINESS, GoldenFleeceStateValues.CHARACTER_ATALANTA,  2));
		
		outcome.addOutcomeModifier(new Outcome.ListStateOutcomeModifier(
				true, GoldenFleeceStateValues.SCENES_SEEN, "SomeScene"));
		
		
		outcome.setStoryStateModifier(new StoryModeModifier("sailing-before-symplegades"));
		
		
		choice = new NodeChoice("Option 1", outcome);
		prereq = new SingularMaxPrerequisite("ChangeMorale", GoldenFleeceStateValues.CREW_MORALE, 1);
		choice.addPrerequisite(prereq);
		node.addChoice(choice);
		
		
		outcome = new Outcome("<Outcome text 2>");
		
		outcome.addOutcomeModifier(new Outcome.SingularStateOutcomeModifier(
				false, GoldenFleeceStateValues.CREW_MORALE, 1));
		
		
		outcome.setStoryStateModifier(new StoryModeModifier("sailing-before-symplegades"));
		
		
		choice = new NodeChoice("Option 2", outcome);
		node.addChoice(choice);
		
		/*
		prereq = new SingularMinPrerequisite(GoldenFleeceStateValues.CREW_MORALE, 2);
		node.addPrerequisite(prereq);
		
		prereq = new SingularMaxPrerequisite(GoldenFleeceStateValues.TENSION, 1);
		node.addPrerequisite(prereq);
		
		prereq = new DictionaryMaxPrerequisite(GoldenFleeceStateValues.CHARACTERS, 3, GoldenFleeceStateValues.CHARACTER_ATALANTA);
		node.addPrerequisite(prereq);
		
		prereq = new DictionaryMinPrerequisite(GoldenFleeceStateValues.CHARACTERS, 3, GoldenFleeceStateValues.CHARACTER_ATHENA);
		node.addPrerequisite(prereq);
		
		prereq = new ContainsPrerequisite(GoldenFleeceStateValues.SCENES_SEEN, "SetSail", true);
		node.addPrerequisite(prereq);
		*/
		
		stateValue = new GoldenFleeceStateValues.ThemesStateValue();
		stateValue.addValue(GoldenFleeceStateValues.THEME_WILL_OF_GODS, 1);
		node.addStateValue(stateValue);

		stateValue = new GoldenFleeceStateValues.CharactersStateValue();
		stateValue.addValue(GoldenFleeceStateValues.CHARACTER_EPHEMUS, 4);
		node.addStateValue(stateValue);
		
		generalNodes.add(node);
		
		/////////////

		teaserText = "Storm";		
		eventText = "<Event text>";
		
		outcome = new Outcome("<Outcome text>");
		
		node = new SatelliteNode("Storm", teaserText, eventText);
		
		choice = new NodeChoice("", outcome);
		node.addChoice(choice);
		
		prereq = new SingularMinPrerequisite(GoldenFleeceStateValues.CREW_MORALE, 2);
		node.addPrerequisite(prereq);
		
		// Only show if we have already set sail
		prereq = new ContainsPrerequisite(GoldenFleeceStateValues.SCENES_SEEN, "SetSail", true);
		node.addPrerequisite(prereq);
		
		stateValue = new GoldenFleeceStateValues.ThemesStateValue();
		stateValue.addValue(GoldenFleeceStateValues.THEME_WILL_OF_GODS, 2);
		node.addStateValue(stateValue);

		stateValue = new GoldenFleeceStateValues.CharactersStateValue();
		stateValue.addValue(GoldenFleeceStateValues.CHARACTER_EPHEMUS, 4);
		node.addStateValue(stateValue);
		
		generalNodes.add(node);
		
		/////////////

		teaserText = "Set Sail";		
		eventText = "<Event text>";
		
		outcome = new Outcome("<Outcome text>");
		
		outcome.addOutcomeModifier(new Outcome.SingularStateOutcomeModifier(
				false, GoldenFleeceStateValues.CREW_MORALE, 1));
		
		node = new KernelNode("SetSail", teaserText, eventText);
		
		choice = new NodeChoice("", outcome);
		node.addChoice(choice);
		
		generalNodes.add(node);
		
		/////////////

		teaserText = "Jason resigns as captain in favor of Hercules";		
		eventText = "<Event text>";
		
		outcome = new Outcome("<Outcome text>");
		
		node = new SatelliteNode("JasonResigns", teaserText, eventText);
		
		choice = new NodeChoice("", outcome);
		node.addChoice(choice);
		
		// Only show if we have not yet set sail
		prereq = new ContainsPrerequisite(GoldenFleeceStateValues.SCENES_SEEN, "SetSail", false);
		node.addPrerequisite(prereq);
		
		stateValue = new GoldenFleeceStateValues.ThemesStateValue();
		stateValue.addValue(GoldenFleeceStateValues.THEME_TREACHERY_HONOR, 3);
		stateValue.addValue(GoldenFleeceStateValues.THEME_HEROISM, 1);
		node.addStateValue(stateValue);

		stateValue = new GoldenFleeceStateValues.CharactersStateValue();
		stateValue.addValue(GoldenFleeceStateValues.CHARACTER_HERCULES, 6);
		node.addStateValue(stateValue);
		
		generalNodes.add(node);
		
		/////////////

		teaserText = "Argonauts sacrifice to Zeus";		
		eventText = "<Event text>";
		
		outcome = new Outcome("<Outcome text>");
		
		node = new SatelliteNode("SacrificeToZeus", teaserText, eventText);
		
		choice = new NodeChoice("", outcome);
		node.addChoice(choice);
		
		// Only show if we have not yet set sail
		prereq = new ContainsPrerequisite(GoldenFleeceStateValues.SCENES_SEEN, "SetSail", false);
		node.addPrerequisite(prereq);
		
		stateValue = new GoldenFleeceStateValues.ThemesStateValue();
		stateValue.addValue(GoldenFleeceStateValues.THEME_WILL_OF_GODS, 3);
		node.addStateValue(stateValue);

		stateValue = new GoldenFleeceStateValues.CharactersStateValue();
		stateValue.addValue(GoldenFleeceStateValues.CHARACTER_ZEUS, 2);
		node.addStateValue(stateValue);
		
		generalNodes.add(node);
		
		/////////////

		teaserText = "Jason bids farewell to Pelias";		
		eventText = "<Event text>";
		
		outcome = new Outcome("<Outcome text>");
		
		node = new SatelliteNode("FarewellPelias", teaserText, eventText);
		
		choice = new NodeChoice("", outcome);
		node.addChoice(choice);
		
		// Only show if we have not yet set sail
		prereq = new ContainsPrerequisite(GoldenFleeceStateValues.SCENES_SEEN, "SetSail", false);
		node.addPrerequisite(prereq);
		
		stateValue = new GoldenFleeceStateValues.ThemesStateValue();
		stateValue.addValue(GoldenFleeceStateValues.THEME_TREACHERY_HONOR, 2);
		node.addStateValue(stateValue);
		
		generalNodes.add(node);
		
		/////////////

		teaserText = "Orpheus sings of the descent to the underworld";		
		eventText = "<Event text>";
		
		outcome = new Outcome("<Outcome text>");
		
		node = new SatelliteNode("OrpheusDescent", teaserText, eventText);
		
		choice = new NodeChoice("", outcome);
		node.addChoice(choice);
		
		// Only show if we have already set sail
		prereq = new ContainsPrerequisite(GoldenFleeceStateValues.SCENES_SEEN, "SetSail", true);
		node.addPrerequisite(prereq);
		
		stateValue = new GoldenFleeceStateValues.ThemesStateValue();
		stateValue.addValue(GoldenFleeceStateValues.THEME_WILL_OF_GODS, 2);
		stateValue.addValue(GoldenFleeceStateValues.THEME_TREACHERY_HONOR, 1);
		stateValue.addValue(GoldenFleeceStateValues.THEME_HEROISM, 2);
		node.addStateValue(stateValue);

		stateValue = new GoldenFleeceStateValues.CharactersStateValue();
		stateValue.addValue(GoldenFleeceStateValues.CHARACTER_ORPHEUS, 5);
		stateValue.addValue(GoldenFleeceStateValues.CHARACTER_APHRODITE, 1);
		node.addStateValue(stateValue);
		
		generalNodes.add(node);
		
		/////////////

		teaserText = "Echion plays a prank";		
		eventText = "<Event text>";
		
		outcome = new Outcome("<Outcome text>");
		
		node = new SatelliteNode("EchionPrank", teaserText, eventText);
		
		choice = new NodeChoice("", outcome);
		node.addChoice(choice);
		
		// Only show if we have already set sail
		prereq = new ContainsPrerequisite(GoldenFleeceStateValues.SCENES_SEEN, "SetSail", true);
		node.addPrerequisite(prereq);
		
		prereq = new SingularMaxPrerequisite(GoldenFleeceStateValues.CREW_MORALE, -1);
		node.addPrerequisite(prereq);
		
		stateValue = new GoldenFleeceStateValues.ThemesStateValue();
		stateValue.addValue(GoldenFleeceStateValues.THEME_TREACHERY_HONOR, 1);
		node.addStateValue(stateValue);

		stateValue = new GoldenFleeceStateValues.CharactersStateValue();
		stateValue.addValue(GoldenFleeceStateValues.CHARACTER_HERMES, 2);
		node.addStateValue(stateValue);
		
		generalNodes.add(node);
		
		/////////////

		teaserText = "Disguise the Argo";		
		eventText = "<Event text>";
		
		outcome = new Outcome("<Outcome text>");
		
		node = new SatelliteNode("DisguiseArgo", teaserText, eventText);
		
		choice = new NodeChoice("", outcome);
		node.addChoice(choice);
		
		prereq = new ContainsPrerequisite(GoldenFleeceStateValues.SCENES_SEEN, "StopAtTroy", true);
		
		// Only show if we have already set sail
		prereq = new ContainsPrerequisite(GoldenFleeceStateValues.SCENES_SEEN, "SetSail", true);
		node.addPrerequisite(prereq);
		node.addPrerequisite(prereq);
		
		stateValue = new GoldenFleeceStateValues.ThemesStateValue();
		stateValue.addValue(GoldenFleeceStateValues.THEME_TREACHERY_HONOR, 2);
		stateValue.addValue(GoldenFleeceStateValues.THEME_WILL_OF_GODS, 2);
		node.addStateValue(stateValue);

		stateValue = new GoldenFleeceStateValues.CharactersStateValue();
		stateValue.addValue(GoldenFleeceStateValues.CHARACTER_EPHEMUS, 4);
		stateValue.addValue(GoldenFleeceStateValues.CHARACTER_HERMES, 3);
		node.addStateValue(stateValue);
		
		generalNodes.add(node);
		
		/////////////

		teaserText = "Ephemus explains his technique";		
		eventText = "<Event text>";
		
		outcome = new Outcome("<Outcome text>");
		
		node = new SatelliteNode("EphemusTechnique", teaserText, eventText);
		
		choice = new NodeChoice("", outcome);
		node.addChoice(choice);
		
		// Only show if we have already set sail
		prereq = new ContainsPrerequisite(GoldenFleeceStateValues.SCENES_SEEN, "SetSail", true);
		node.addPrerequisite(prereq);
		
		stateValue = new GoldenFleeceStateValues.ThemesStateValue();
		stateValue.addValue(GoldenFleeceStateValues.THEME_TREACHERY_HONOR, 1);
		node.addStateValue(stateValue);

		stateValue = new GoldenFleeceStateValues.CharactersStateValue();
		stateValue.addValue(GoldenFleeceStateValues.CHARACTER_EPHEMUS, 3);
		node.addStateValue(stateValue);
		
		generalNodes.add(node);
		
		/////////////

		teaserText = "Ephemus is sick";		
		eventText = "<Event text>";
		
		outcome = new Outcome("<Outcome text>");
		
		node = new SatelliteNode("EphemusSick", teaserText, eventText);
		
		choice = new NodeChoice("", outcome);
		node.addChoice(choice);
		
		// Only show if we have already set sail
		prereq = new ContainsPrerequisite(GoldenFleeceStateValues.SCENES_SEEN, "SetSail", true);
		node.addPrerequisite(prereq);
		
		stateValue = new GoldenFleeceStateValues.ThemesStateValue();
		stateValue.addValue(GoldenFleeceStateValues.THEME_HEROISM, 1);
		node.addStateValue(stateValue);

		stateValue = new GoldenFleeceStateValues.CharactersStateValue();
		stateValue.addValue(GoldenFleeceStateValues.CHARACTER_EPHEMUS, 4);
		node.addStateValue(stateValue);
		
		generalNodes.add(node);
		
		/////////////

		teaserText = "Orpheus sings about the creation";		
		eventText = "<Event text>";
		
		outcome = new Outcome("<Outcome text>");
		
		node = new SatelliteNode("OrpheusCreation", teaserText, eventText);
		
		choice = new NodeChoice("", outcome);
		node.addChoice(choice);
		
		// Only show if we have already set sail
		prereq = new ContainsPrerequisite(GoldenFleeceStateValues.SCENES_SEEN, "SetSail", true);
		node.addPrerequisite(prereq);
		
		stateValue = new GoldenFleeceStateValues.ThemesStateValue();
		stateValue.addValue(GoldenFleeceStateValues.THEME_WILL_OF_GODS, 3);
		node.addStateValue(stateValue);

		stateValue = new GoldenFleeceStateValues.CharactersStateValue();
		stateValue.addValue(GoldenFleeceStateValues.CHARACTER_ORPHEUS, 3);
		node.addStateValue(stateValue);
		
		generalNodes.add(node);
		
		/////////////

		teaserText = "Hercules takes charge";		
		eventText = "<Event text>";
		
		outcome = new Outcome("<Outcome text>");
		
		node = new SatelliteNode("HerculeTakesCharge", teaserText, eventText);
		
		choice = new NodeChoice("", outcome);
		node.addChoice(choice);
		
		// Only show if we have already set sail
		prereq = new ContainsPrerequisite(GoldenFleeceStateValues.SCENES_SEEN, "SetSail", true);
		node.addPrerequisite(prereq);
		
		stateValue = new GoldenFleeceStateValues.ThemesStateValue();
		stateValue.addValue(GoldenFleeceStateValues.THEME_HEROISM, 1);
		node.addStateValue(stateValue);

		stateValue = new GoldenFleeceStateValues.CharactersStateValue();
		stateValue.addValue(GoldenFleeceStateValues.CHARACTER_HERCULES, 3);
		node.addStateValue(stateValue);
		
		generalNodes.add(node);
		
		/////////////

		teaserText = "Atalanta and the apples";		
		eventText = "<Event text>";
		
		outcome = new Outcome("<Outcome text>");
		
		node = new SatelliteNode("AtalantaApples", teaserText, eventText);
		
		choice = new NodeChoice("", outcome);
		node.addChoice(choice);
		
		// Only show if we have already set sail
		prereq = new ContainsPrerequisite(GoldenFleeceStateValues.SCENES_SEEN, "SetSail", true);
		node.addPrerequisite(prereq);
		
		stateValue = new GoldenFleeceStateValues.ThemesStateValue();
		stateValue.addValue(GoldenFleeceStateValues.THEME_WILL_OF_GODS, 2);
		node.addStateValue(stateValue);

		stateValue = new GoldenFleeceStateValues.CharactersStateValue();
		stateValue.addValue(GoldenFleeceStateValues.CHARACTER_ATALANTA, 3);
		node.addStateValue(stateValue);
		
		generalNodes.add(node);
		
		/////////////
		
		
		return generalNodes;
	}
	
	
}
