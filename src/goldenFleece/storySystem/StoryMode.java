package goldenFleece.storySystem;

import java.util.ArrayList;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

public class StoryMode
{
	@Element(name="id")
	protected String m_id;
	
	@Element(name="mapID")
	protected String m_mapID;
	
	@ElementList(name="relatedLabels", required=false)
	protected ArrayList<String> m_relatedLabels;
	
	@Element(name="labelsAllowed", required=false)
	protected boolean m_labelsAllowed;
	
	
	public StoryMode(
			@Element(name="id") String id,
			@Element(name="mapID") String mapID)
	{
		this(id, mapID, new ArrayList<String>(), true);
	}
	
	protected StoryMode(
			@Element(name="id") String id,
			@Element(name="mapID") String mapID,
			@ElementList(name="relatedLabels", required=false) ArrayList<String> labels,
			@Element(name="labelsAllowed", required=false) boolean labelsAllowed)
	{
		m_id = id;
		m_mapID = mapID;
		m_relatedLabels = labels;
		m_labelsAllowed = labelsAllowed;
	}
	
	public String getID() { return m_id; }
	public String getMapID() { return m_mapID; }
	
	public boolean doesModeHaveLabels()
	{
		return m_relatedLabels != null && m_relatedLabels.size() > 0;
	}
	
	public ArrayList<String> getRelatedLabels() { return m_relatedLabels; }
	public boolean getLabelsIndicateThoseAllowed() { return m_labelsAllowed; }
	public void setLabelsIndicateThoseAllowed(boolean labelsAllowed) { m_labelsAllowed = labelsAllowed; }
	
	public void addRelatedLabel(String label)
	{
		m_relatedLabels.add(label);
	}
}
