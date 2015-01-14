package goldenFleece.storySystem.storyNodes;

import goldenFleece.storySystem.prerequisites.Prerequisite;

import java.util.ArrayList;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

public class NodeChoice
{
	@ElementList(name="prerequisites", required=false)
	protected ArrayList<Prerequisite> m_prereqs;
	
	@Element(name="text", required=false)
	protected String m_text;
	
	@Element(name="outcome")
	protected Outcome m_outcome;
	
	public NodeChoice(
			@Element(name="text", required=false) String text, 
			@Element(name="outcome") Outcome outcome)
	{
		this(null, text, outcome);
	}
	
	public NodeChoice(
			@ElementList(name="prerequisites", required=false) ArrayList<Prerequisite> prereqs,
			@Element(name="text", required=false) String text, 
			@Element(name="outcome") Outcome outcome)
	{
		m_prereqs = prereqs;
		if (m_prereqs == null)
		{
			m_prereqs = new ArrayList<Prerequisite>();
		}
		
		m_text = text;
		m_outcome = outcome;
	}
	
	public void addPrerequisite(Prerequisite p)
	{
		if (m_prereqs == null)	
		{
			m_prereqs = new ArrayList<Prerequisite>();
		}
		
		m_prereqs.add(p);
	}
	
	public ArrayList<Prerequisite> getPrerequisites() {	return m_prereqs; }
	public String getText() { return m_text; }
	public Outcome getOutcome() { return m_outcome; }
	
}
