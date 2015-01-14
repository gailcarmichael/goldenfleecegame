package goldenFleece.game;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;

import pathfinder.Graph;
import pathfinder.GraphNode;
import pathfinder.GraphSearch_Dijkstra;
import pathfinder.IGraphSearch;

public class Map
{
	protected static final String BACKGROUND_MAP_FILENAME = "./data/images/map-zoomed.png";
	
	public static final String MAP_GRAPH_NAME_NULL = "null"; // placeholder for when there is no graph
	
	public static final String MAP_GRAPH_NAME_SYMPLEGADES = "symplegades";
	protected static final String MAP_GRAPH_FILENAME_SYMPLEGADES = "./data/symplegades.txt";
	
	protected GoldenFleeceGame m_game;
	
	protected HashMap<String, Graph> m_mapGraphs;
	
	protected IGraphSearch m_pathFinder;
	
	////
	
	protected class MapGraphNode extends GraphNode
	{
		protected String m_tag;
	
		String getTag()
		{
			return m_tag;
		}
	
		MapGraphNode(int id, String tag)
		{
			super(id);
			m_tag = tag;
		}
	
		MapGraphNode(int id, double x, double y, double z, String tag)
		{
			super(id, x, y, z);
			m_tag = tag;
		}
	
		MapGraphNode(int id, double x, double y, String tag)
		{
			super(id, x, y);
			m_tag = tag;
		}
	}

	////
	
	public Map(GoldenFleeceGame game)
	{
		m_game = game;
		
		m_mapGraphs = new HashMap<String, Graph>();
		
		// Load the graphs
		m_mapGraphs.put(MAP_GRAPH_NAME_NULL, null);
		loadMapGraph(MAP_GRAPH_NAME_SYMPLEGADES, MAP_GRAPH_FILENAME_SYMPLEGADES);
		
		// Update the path finder so it's ready to go in the current story mode
		updatePathFinderForMap();
	}
	
	
	public String getImageFilename() { return BACKGROUND_MAP_FILENAME; }
	public Graph getMapWithID(String name) { return m_mapGraphs.get(name); }
	
	public Graph getCurrentMapGraph()
	{
		Graph graph = null;
		
		String currentMapID = m_game.getCurrentStoryMode().getMapID();
		
		if (currentMapID != null)
		{
			if (currentMapID.equals(MAP_GRAPH_NAME_NULL))
			{
				graph = new Graph(); // give back an empty graph
			}
			else
			{
				graph = m_mapGraphs.get(currentMapID);
			}
		}
		
		return graph;
	}
	
	
	public void updatePathFinderForMap()
	{
		Graph g = getCurrentMapGraph();
		if (g.getNbrNodes() > 0)
		{
			//m_pathFinder = new GraphSearch_Astar(g);
			m_pathFinder = new GraphSearch_Dijkstra(g);
		}
	}
	
	
	boolean calculateShortestPath(GraphNode start, GraphNode end)
	{
		boolean success = false;
		
		if (start != null && end != null)
		{
			LinkedList<GraphNode> nodes = m_pathFinder.search(start.id(), end.id());
			success = nodes != null && nodes.size() > 0;
		}
		
		return success;
	}
	
	
	public GraphNode[] getLastShortestPathRoute()
	{
		if (m_pathFinder != null)
		{
			return m_pathFinder.getRoute();
		}
		else
		{
			return null;
		}
	}
	
	
	protected void loadMapGraph(String name, String filename)
	{
		try
		{
			FileInputStream in = new FileInputStream(filename);
		    BufferedReader br = new BufferedReader(new InputStreamReader(in));
		    
		    String line;
		    boolean readingNodes = true;
		    
		    Graph graph = new Graph();
		    while ((line = br.readLine()) != null)
		    {
		    	if (line.equals("edges"))
		    	{
		    		readingNodes = false;
		    	}
		    	else
		    	{
		    		String[] data = line.split(",");
			    	if (readingNodes)
			    	{
			    		MapGraphNode node = 
			    				new MapGraphNode(
			    						Integer.parseInt(data[0]), // ID
			    						Double.parseDouble(data[1]), // x-pos
			    						Double.parseDouble(data[2]), // y-pos
			    						data[3]); // terrain type tag
			    		graph.addNode(node);
			    	}
			    	else
			    	{
			    		// Only one direction of edge was stored in the file,
			    		// so add both directions
			    		
			    		graph.addEdge(
			    				Integer.parseInt(data[0]), // ID1
			    				Integer.parseInt(data[1]), // ID2
			    				Double.parseDouble(data[2])); // edge cost
			    		
			    		graph.addEdge(
			    				Integer.parseInt(data[1]), // ID2
			    				Integer.parseInt(data[0]), // ID1
			    				Double.parseDouble(data[2])); // edge cost
			    	}
		    	}
		    }
		    
		    m_mapGraphs.put(name, graph);
		    
		    in.close();
		}
		catch (Exception e)
		{
			System.err.println(e);
		}
	}
}


