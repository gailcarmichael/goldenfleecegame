package goldenFleece.game.ui;

import g4p_controls.GAlign;
import g4p_controls.GCScheme;
import g4p_controls.GLabel;
import goldenFleece.storySystem.state.GoldenFleeceStateValues;
import goldenFleece.storySystem.state.SingularStateValue;

import java.awt.Font;
import java.awt.FontMetrics;


public class SideBarUI
{
	protected GoldenFleeceApplet m_parent;
	
	protected final static int SIDE_BAR_WIDTH = 220;
	protected final static int SIDE_BAR_PADDING = 15;
	
	protected final static int SIDE_BAR_ELEMENT_SPACING = 25;
	
	protected final int SIDE_BAR_BACKGROUND_COLOR;
	
	protected final int FONT_HEIGHT;
	protected final int ELEMENT_WIDTH = SIDE_BAR_WIDTH - 2*SIDE_BAR_PADDING;;
	protected final int ELEMENT_X;
	
	protected final static int SIDE_BAR_FONT_SIZE = 14;
	protected final static String SIDE_BAR_FONT_NAME = "Verdana";

	protected final static Font SIDE_BAR_FONT; 
	
	protected final static int SIDE_BAR_COLOR_SCHEME = GCScheme.PURPLE_SCHEME; 
	
	protected GLabel m_provisionsLabel;
	protected GLabel m_argonautHappinessLabel;
	protected GLabel m_godHappinessLabel;
	protected GLabel m_crewMoraleLabel;
	
	protected GLabel m_jasonHeroismLabel;
	protected GLabel m_herculesRageLabel;
	protected GLabel m_tensionLabel;
	
	
	static
	{
		SIDE_BAR_FONT = new Font(SIDE_BAR_FONT_NAME, Font.PLAIN, SIDE_BAR_FONT_SIZE);
	}
	
	
	SideBarUI(GoldenFleeceApplet parent)
	{
		m_parent = parent;
		
		SIDE_BAR_BACKGROUND_COLOR = m_parent.color(245);
		
		FontMetrics metrics = m_parent.getGraphics().getFontMetrics(SIDE_BAR_FONT);
		FONT_HEIGHT = metrics.getLeading() + metrics.getMaxAscent() + metrics.getMaxDescent();
		ELEMENT_X = m_parent.getMapUI().getMapXOnScreen() + m_parent.getMapUI().getMapWidthOnScreen() + SIDE_BAR_PADDING;
		
		///
		
		m_provisionsLabel = new GLabel(
				m_parent, 
				ELEMENT_X, 
				SIDE_BAR_PADDING, 
				ELEMENT_WIDTH,
				5 * FONT_HEIGHT);
		
		m_provisionsLabel.setTextAlign(GAlign.LEFT, GAlign.TOP);
		m_provisionsLabel.setFont(SIDE_BAR_FONT);
		m_provisionsLabel.setLocalColorScheme(SIDE_BAR_COLOR_SCHEME);
		
		///
		
		m_argonautHappinessLabel = new GLabel(
				m_parent, 
				ELEMENT_X, 
				m_provisionsLabel.getY() + m_provisionsLabel.getHeight() + SIDE_BAR_ELEMENT_SPACING, 
				ELEMENT_WIDTH,
				(m_parent.getGame().getArgonautGroup().getNumInGroup(
						GoldenFleeceStateValues.ARGONAUTS_HAPPINESS) + 1) * FONT_HEIGHT);
		
		m_argonautHappinessLabel.setTextAlign(GAlign.LEFT, GAlign.TOP);
		m_argonautHappinessLabel.setFont(SIDE_BAR_FONT);
		m_argonautHappinessLabel.setLocalColorScheme(SIDE_BAR_COLOR_SCHEME);
		
		///
		
		m_godHappinessLabel = new GLabel(
				m_parent, 
				ELEMENT_X, 
				m_argonautHappinessLabel.getY() + m_argonautHappinessLabel.getHeight() + SIDE_BAR_ELEMENT_SPACING, 
				ELEMENT_WIDTH,
				(m_parent.getGame().getGodGroup().getNumInGroup(
						GoldenFleeceStateValues.GODS_HAPPINESS) + 1) * FONT_HEIGHT);
		
		m_godHappinessLabel.setTextAlign(GAlign.LEFT, GAlign.TOP);
		m_godHappinessLabel.setFont(SIDE_BAR_FONT);
		m_godHappinessLabel.setLocalColorScheme(SIDE_BAR_COLOR_SCHEME);
		
		///
		
		m_crewMoraleLabel = new GLabel(
				m_parent, 
				ELEMENT_X, 
				m_godHappinessLabel.getY() + m_godHappinessLabel.getHeight() + SIDE_BAR_ELEMENT_SPACING, 
				ELEMENT_WIDTH,
				FONT_HEIGHT);
		
		m_crewMoraleLabel.setTextAlign(GAlign.LEFT, GAlign.TOP);
		m_crewMoraleLabel.setFont(SIDE_BAR_FONT);
		m_crewMoraleLabel.setLocalColorScheme(SIDE_BAR_COLOR_SCHEME);
		
		///
		
		m_jasonHeroismLabel = new GLabel(
				m_parent, 
				ELEMENT_X, 
				m_crewMoraleLabel.getY() + m_crewMoraleLabel.getHeight() /*+ SIDE_BAR_ELEMENT_SPACING*/, 
				ELEMENT_WIDTH,
				FONT_HEIGHT);
		
		m_jasonHeroismLabel.setTextAlign(GAlign.LEFT, GAlign.TOP);
		m_jasonHeroismLabel.setFont(SIDE_BAR_FONT);
		m_jasonHeroismLabel.setLocalColorScheme(SIDE_BAR_COLOR_SCHEME);
		
		///
		
		m_herculesRageLabel = new GLabel(
				m_parent, 
				ELEMENT_X, 
				m_jasonHeroismLabel.getY() + m_jasonHeroismLabel.getHeight() /*+ SIDE_BAR_ELEMENT_SPACING*/, 
				ELEMENT_WIDTH,
				FONT_HEIGHT + 1); // some extra height for the 'g'
		
		m_herculesRageLabel.setTextAlign(GAlign.LEFT, GAlign.TOP);
		m_herculesRageLabel.setFont(SIDE_BAR_FONT);
		m_herculesRageLabel.setLocalColorScheme(SIDE_BAR_COLOR_SCHEME);
		
		///
		
		m_tensionLabel = new GLabel(
				m_parent, 
				ELEMENT_X, 
				m_herculesRageLabel.getY() + m_herculesRageLabel.getHeight() + SIDE_BAR_ELEMENT_SPACING, 
				ELEMENT_WIDTH,
				FONT_HEIGHT);
		
		m_tensionLabel.setTextAlign(GAlign.LEFT, GAlign.TOP);
		m_tensionLabel.setFont(SIDE_BAR_FONT);
		m_tensionLabel.setLocalColorScheme(SIDE_BAR_COLOR_SCHEME);
	}
	
	
	int getWidth() { return SIDE_BAR_WIDTH; }
	
	
	void draw()
	{
		m_parent.noStroke();
		
		// Main window background
		m_parent.fill(m_parent.color(SIDE_BAR_BACKGROUND_COLOR));
		m_parent.rect(m_parent.width - SIDE_BAR_WIDTH, 0, SIDE_BAR_WIDTH, m_parent.height/*, 5, 0, 0, 5*/);
		
		// Provisions
		drawProvisions();
		
		// Separator
		drawSeparator((int)m_argonautHappinessLabel.getY() - SIDE_BAR_ELEMENT_SPACING / 2);
		
		// Argonauts
		drawArgonauts();
		
		// Separator
		drawSeparator((int)m_godHappinessLabel.getY() - SIDE_BAR_ELEMENT_SPACING / 2);
		
		// Gods
		drawGods();

		// Separator
		drawSeparator((int)m_crewMoraleLabel.getY() - SIDE_BAR_ELEMENT_SPACING / 2);
		
		// Crew Morale
		drawCrewMorale();
		
		// Jason's Heroism
		drawJasonsHeroism();
		
		// Hercules Rage
		drawHerculesRage();

		// Separator
		drawSeparator((int)m_tensionLabel.getY() - SIDE_BAR_ELEMENT_SPACING / 2);
		
		// Tension
		drawTension();
	}
	
	protected void drawSeparator(int y)
	{
		m_parent.stroke(200);
		m_parent.line(ELEMENT_X, y, ELEMENT_X + ELEMENT_WIDTH, y);
	}
	
	protected void drawProvisions()
	{
		String text = "Food Levels: " + m_parent.getGame().getProvisions().getFoodNumber();
		text += "\n";
		
		int length1 = text.length()-1;
		
		text += "\nFood Quality: " + m_parent.getGame().getProvisions().getFoodQualityText();
		text += "\n \n";
		
		int length2 = text.length()-1;

		text += "Water Levels: " + m_parent.getGame().getProvisions().getWaterNumber();
		text += "\n";
		
		int length3 = text.length()-1;
		
		text += "Water Quality: " + m_parent.getGame().getProvisions().getWaterQualityText();
		
		m_provisionsLabel.setText(text);
		
		m_provisionsLabel.setTextBold(0, 11);
		m_provisionsLabel.setTextBold(length1, length1 + 12);
		m_provisionsLabel.setTextBold(length2, length2 + 12);
		m_provisionsLabel.setTextBold(length3, length3 + 13);
	}
	
	protected void drawArgonauts()
	{
		String label = "Argonaut Happiness";
		String argonautsInfo = m_parent.getGame().getArgonautGroup().getNameAndHappinessString(": ", "\n");
	
		m_argonautHappinessLabel.setText(label + "\n" + argonautsInfo);
		
		m_argonautHappinessLabel.setTextBold(0, label.length());
	}
	
	protected void drawGods()
	{
		String label = "God Happiness";
		String godsInfo = m_parent.getGame().getGodGroup().getNameAndHappinessString(": ", "\n");
	
		m_godHappinessLabel.setText(label + "\n" + godsInfo);
		
		m_godHappinessLabel.setTextBold(0, label.length());
	}
	
	protected void drawCrewMorale()
	{
		SingularStateValue morale = (SingularStateValue)m_parent.getGame().getStateValue(GoldenFleeceStateValues.CREW_MORALE);
		String text = "Crew morale: " + morale.getValue();
		
		m_crewMoraleLabel.setText(text);
		
		m_crewMoraleLabel.setTextBold(0, 11);
	}
	
	protected void drawJasonsHeroism()
	{
		SingularStateValue heroism = (SingularStateValue)m_parent.getGame().getStateValue(GoldenFleeceStateValues.JASON_HEROISM);
		String text = "Jason's Heroism: " + heroism.getValue();
		
		m_jasonHeroismLabel.setText(text);
		
		m_jasonHeroismLabel.setTextBold(0, 15);
	}
	
	protected void drawHerculesRage()
	{
		SingularStateValue rage = (SingularStateValue)m_parent.getGame().getStateValue(GoldenFleeceStateValues.HERCULES_RAGE);
		String text = "Hercules' Rage: " + rage.getValue();
		
		m_herculesRageLabel.setText(text);
		
		m_herculesRageLabel.setTextBold(0, 14);
	}
	
	protected void drawTension()
	{
		SingularStateValue tension = (SingularStateValue)m_parent.getGame().getStateValue(GoldenFleeceStateValues.TENSION);
		String text = "Tension: " + tension.getValue();
		
		m_tensionLabel.setText(text);
		
		m_tensionLabel.setTextBold(0, 7);
	}
	
	///////////
		
	boolean wasClicked(int clickX, int clickY)
	{
		return (clickX >= m_parent.width - SIDE_BAR_WIDTH &&
				clickX <= m_parent.width &&
				clickY <= m_parent.height &&
				clickY >= 0);
	}
	
	void handleClick(int clickX, int clickY)
	{

		
	}
}
