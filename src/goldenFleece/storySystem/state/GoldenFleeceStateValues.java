package goldenFleece.storySystem.state;

import goldenFleece.game.Map;
import goldenFleece.storySystem.StoryMode;

import java.util.HashMap;

import org.simpleframework.xml.Element;

public final class GoldenFleeceStateValues
{
	public static String SCENES_SEEN = "scenes seen";
	
	
	public static String STORY_MODE_LABELS = "mode labels";
	

	public static final String CREW_MORALE = "crew morale";
	public static final String JASON_HEROISM = "Jason's heroism";
	public static final String HERCULES_RAGE = "Hercules' rage";
	public static final String TENSION = "tension";

	public static final String ARGONAUTS_HAPPINESS = "Argonauts happiness";
	public static final String GODS_HAPPINESS = "Gods happiness";
	
	
    public static final String PROVISIONS = "provisions";
    
    public static final String PROVISIONS_WATER = "water";
    public static final String PROVISIONS_FOOD = "food";
	
    
	public static final String THEMES = "themes";
	
	public static final String THEME_WILL_OF_GODS = "will of the gods";
	public static final String THEME_HEROISM = "heroism";
	public static final String THEME_TREACHERY_HONOR = "treachery and honor";
	public static final String THEME_PERIL_SYMPLEGADES = "peril of the Symplegades";
	
	
	public static final String CHARACTERS = "characters";
	
	public static final String CHARACTER_JASON = "Jason";
	public static final String CHARACTER_EPHEMUS = "Ephemus";
	public static final String CHARACTER_HERCULES = "Hercules";
	public static final String CHARACTER_ATALANTA = "Atalanta";
	public static final String CHARACTER_ORPHEUS = "Orpheus";
	
	public static final String CHARACTER_APHRODITE = "Aphrodite";
	public static final String CHARACTER_ZEUS = "Zeus";
	public static final String CHARACTER_HERMES = "Hermes";
	public static final String CHARACTER_ATHENA = "Athena";
	public static final String CHARACTER_ARTEMIS = "Artemis";

	

	private GoldenFleeceStateValues()
	{
		// don't allow class to be instantiated; it's
		// just a keeper of state value classes
	}
	
	
	/////////////////////////////////////////////////////
	
	
	public static StoryState defaultFleeceStartState()
	{
		StoryState state = new StoryState();
		
		
		/////
		// Story Modes
		
		StoryMode mode = new StoryMode(
				"sailing-before-symplegades",
				Map.MAP_GRAPH_NAME_SYMPLEGADES);
		
		mode.setLabelsIndicateThoseAllowed(false);
		mode.addRelatedLabel("treasure island");
		
		state.addStoryMode(mode);
		//state.setCurrentStoryMode(mode.getID());
		
		mode = new StoryMode("pre-set-sail", Map.MAP_GRAPH_NAME_NULL);
		state.addStoryMode(mode);
		state.setCurrentStoryMode(mode.getID());
		
		/////
		// QSEs Without Modifier
		
		// 'Scenes Seen' is created automatically
		
		SingularStateValue s = new GoldenFleeceStateValues.CrewMoraleStateValue(2);
		state.addStateValue(s);
		
		s = new GoldenFleeceStateValues.JasonsHeroismStateValue(1);
		state.addStateValue(s);
		
		s = new GoldenFleeceStateValues.HerculesRageStateValue(1);
		state.addStateValue(s);
		
		s = new GoldenFleeceStateValues.TensionStateValue(0);	
		state.addStateValue(s);
		
		DictionaryStateValue provisions = new GoldenFleeceStateValues.ProvisionsStateValue();
		provisions.addValue(PROVISIONS_FOOD, 100);
		provisions.addValue(PROVISIONS_WATER, 80);
		state.addStateValue(provisions);
		
		DictionaryStateValue happiness = new GoldenFleeceStateValues.ArgonautsHappinessStateValue();
		happiness.addValue(CHARACTER_JASON, 3);
		happiness.addValue(CHARACTER_EPHEMUS, 2);
		happiness.addValue(CHARACTER_HERCULES, 1);
		happiness.addValue(CHARACTER_ATALANTA, 3);
		happiness.addValue(CHARACTER_ORPHEUS, 2);
		state.addStateValue(happiness);
		
		happiness = new GoldenFleeceStateValues.GodsHappinessStateValue();
		happiness.addValue(CHARACTER_ATHENA, 1);
		happiness.addValue(CHARACTER_HERMES, 3);
		happiness.addValue(CHARACTER_ZEUS, 2);
		happiness.addValue(CHARACTER_APHRODITE, 3);
		happiness.addValue(CHARACTER_ARTEMIS, 1);
		state.addStateValue(happiness);
		
		
		/////
		// QSEs With Modifier
		
		// Theme
		DictionaryStateValue v = new GoldenFleeceStateValues.ThemesStateValue();
		v.addValue(GoldenFleeceStateValues.THEME_HEROISM, 1);
		v.addValue(GoldenFleeceStateValues.THEME_TREACHERY_HONOR, 1);
		v.addValue(GoldenFleeceStateValues.THEME_WILL_OF_GODS, 1);
		v.addValue(GoldenFleeceStateValues.THEME_PERIL_SYMPLEGADES, 1);
		state.addStateValue(v);
		
		// Character
		v = new GoldenFleeceStateValues.CharactersStateValue();
		v.addValue(GoldenFleeceStateValues.CHARACTER_JASON, 1);
		v.addValue(GoldenFleeceStateValues.CHARACTER_EPHEMUS, 1);
		v.addValue(GoldenFleeceStateValues.CHARACTER_HERCULES, 1);
		v.addValue(GoldenFleeceStateValues.CHARACTER_ZEUS, 1);
		v.addValue(GoldenFleeceStateValues.CHARACTER_ATALANTA, 1);
		v.addValue(GoldenFleeceStateValues.CHARACTER_ORPHEUS, 1);
		v.addValue(GoldenFleeceStateValues.CHARACTER_APHRODITE, 1);
		v.addValue(GoldenFleeceStateValues.CHARACTER_HERMES, 1);
		v.addValue(GoldenFleeceStateValues.CHARACTER_ATHENA, 1);
		v.addValue(GoldenFleeceStateValues.CHARACTER_ARTEMIS, 1);
		state.addStateValue(v);
		
		return state;
	}
	
	
	/////////////////////////////////////////////////////
	
	
	public static StoryState defaultFleeceLaterState()
	{
		StoryState state = new StoryState();
		
		// Add values that represent some state later in the game
		// if we want to test that
		
		return state;
	}
	
	
	/////////////////////////////////////////////////////
	
	
	public static class StoryModeLabels extends ListStateValue
	{
		public StoryModeLabels()
		{
			super(STORY_MODE_LABELS);
			setHasModifier(false);
		}
	}
	
	
	public static class ThemesStateValue extends DictionaryStateValue
	{
		public ThemesStateValue(HashMap<String, Integer> themes)
		{
			super(THEMES, themes);
		}
		
		public ThemesStateValue()
		{
			super(THEMES);
		}
	}
	
	
	public static class CharactersStateValue extends DictionaryStateValue
	{
		public CharactersStateValue(HashMap<String, Integer> characters)
		{
			super(CHARACTERS, characters);
		}
		
		public CharactersStateValue()
		{
			super(CHARACTERS);
		}
	}
	
	public static class ProvisionsStateValue extends DictionaryStateValue
	{
		public ProvisionsStateValue(HashMap<String, Integer> provisions)
		{
			super(PROVISIONS, provisions);
			setHasModifier(false);
		}
		
		public ProvisionsStateValue()
		{
			super(PROVISIONS);
			setHasModifier(false);
		}
	}
	
	public static class ArgonautsHappinessStateValue extends DictionaryStateValue
	{
		public ArgonautsHappinessStateValue(HashMap<String, Integer> characters)
		{
			super(ARGONAUTS_HAPPINESS, characters);
			setHasModifier(false);
		}
		
		public ArgonautsHappinessStateValue()
		{
			super(ARGONAUTS_HAPPINESS);
			setHasModifier(false);
		}
	}
	
	public static class GodsHappinessStateValue extends DictionaryStateValue
	{
		public GodsHappinessStateValue(HashMap<String, Integer> characters)
		{
			super(GODS_HAPPINESS, characters);
			setHasModifier(false);
		}
		
		public GodsHappinessStateValue()
		{
			super(GODS_HAPPINESS);
			setHasModifier(false);
		}
	}
	
	
	public static class ScenesSeenStateValue extends ListStateValue
	{
		public ScenesSeenStateValue()
		{
			super(SCENES_SEEN);
			setHasModifier(false);
		}
	}
	
	
	public static class CrewMoraleStateValue extends SingularStateValue
	{
		public CrewMoraleStateValue(@Element(name="value") int value)
		{
			super(CREW_MORALE, value, false);
		}
	}
	
	
	public static class JasonsHeroismStateValue extends SingularStateValue
	{
		public JasonsHeroismStateValue(@Element(name="value") int value)
		{
			super(JASON_HEROISM, value);
			setHasModifier(false);
		}
	}

	
	public static class HerculesRageStateValue extends SingularStateValue
	{
		public HerculesRageStateValue(@Element(name="value") int value)
		{
			super(HERCULES_RAGE, value);
			setHasModifier(false);
		}
	}
	
	
	public static class TensionStateValue extends SingularStateValue
	{
		public TensionStateValue(@Element(name="value") int value)
		{
			super(TENSION, value);
			setHasModifier(false);
		}
	}
	
	
	/*public static class HazardStateValue extends SingularStateValue
	{
		public HazardStateValue(@Element(name="value") int value)
		{
			super(HAZARD, value);
		}
	}*/
	
	
	/*
	public static class GoalsStateValue extends DictionaryStateValue
	{
		public GoalsStateValue(HashMap<String, Integer> goals)
		{
			super("goals", goals);
		}
		
		public GoalsStateValue()
		{
			super("goals");
		}
	}
	*/
	
	
	/*
	public static class KnowledgeStateValue extends DictionaryStateValue
	{
		public KnowledgeStateValue(HashMap<String, Integer> knowledge)
		{
			super("knowledge", knowledge);
		}
	}
	*/
	
	/*
	public static class WithStateValue extends ListStateValue
	{
		public WithStateValue()
		{
			super("with", null);
		}
	}
	*/
	
	
	/*
	public static class HasStateValue extends ListStateValue
	{
		public HasStateValue()
		{
			super("has", null);
		}
	}
	*/

}
