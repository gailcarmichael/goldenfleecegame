package goldenFleece.storySystem;

import goldenFleece.storySystem.state.GoldenFleeceStateValues;
import goldenFleece.storySystem.state.StoryState;
import goldenFleece.storySystem.storyNodes.GoldenFleeceGeneralStoryNodes;
import goldenFleece.storySystem.storyNodes.GoldenFleeceMapStoryNodes;
import goldenFleece.storySystem.storyNodes.StoryNode;

import java.io.File;
import java.util.ArrayList;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

@Root(name="GoldenFleece")
public class GoldenFleeceStoryInfo
{
	@ElementList(name="mapStoryNodes")
	private ArrayList<StoryNode> m_mapNodes;

	@ElementList(name="generalStoryNodes")
	private ArrayList<StoryNode> m_generalNodes;
	
	@Element(name="storyState", required=false)
	private StoryState m_storyState;

	
	public GoldenFleeceStoryInfo(
			@ElementList(name="mapStoryNodes") ArrayList<StoryNode> mapNodes,
			@ElementList(name="generalStoryNodes") ArrayList<StoryNode> generalNodes)
	{
		this(mapNodes, generalNodes, null);
	}
	
	public GoldenFleeceStoryInfo(
			@ElementList(name="mapStoryNodes") ArrayList<StoryNode> mapNodes,
			@ElementList(name="generalStoryNodes") ArrayList<StoryNode> generalNodes,
			@Element(name="storyState", required=false) StoryState storyState)
	{
		m_mapNodes = mapNodes;
		m_generalNodes = generalNodes;
		m_storyState = storyState;
	}
	
	public ArrayList<StoryNode> getMapStoryNodes() { return m_mapNodes; }
	public ArrayList<StoryNode> getGeneralStoryNodes() { return m_generalNodes; }
	public StoryState getStoryState() { return m_storyState; }
	
	public static GoldenFleeceStoryInfo getDefaultStartState()
	{
		return new GoldenFleeceStoryInfo(
				GoldenFleeceMapStoryNodes.getMapStoryNodes(),
				GoldenFleeceGeneralStoryNodes.getGeneralStoryNodes(),
				GoldenFleeceStateValues.defaultFleeceStartState());
	}
	
	public static GoldenFleeceStoryInfo getDefaultLaterState()
	{
		return new GoldenFleeceStoryInfo(
				GoldenFleeceMapStoryNodes.getMapStoryNodes(),
				GoldenFleeceGeneralStoryNodes.getGeneralStoryNodes(),
				GoldenFleeceStateValues.defaultFleeceLaterState());
	}
	
	public static GoldenFleeceStoryInfo readStoryInfoFromFile(String filename)
	{
		GoldenFleeceStoryInfo storyInfo = null;
		
		try
		{
			Serializer serializer = new Persister();
			File storyFile = new File(filename);
			
			storyInfo = serializer.read(GoldenFleeceStoryInfo.class, storyFile);
		}
		catch (Exception e)
		{
			System.err.println("Error: Could not read story info from " + filename + " due to error \n" + e.getLocalizedMessage());
		}
		
		return storyInfo;
	}
	
	
	// Test the XML serialization
	public static void main(String args[])
	{
		Serializer serializer = new Persister();
		
		GoldenFleeceStoryInfo story = GoldenFleeceStoryInfo.getDefaultStartState();
		
		try
		{
			File result = new File("example.xml");
			
			serializer.write(story, result);
			
			GoldenFleeceStoryInfo readStory = serializer.read(GoldenFleeceStoryInfo.class, result);
			System.out.println(readStory.getMapStoryNodes().get(0).getID());
			
			File result2 = new File("example2.xml");
			
			serializer.write(readStory, result2);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
