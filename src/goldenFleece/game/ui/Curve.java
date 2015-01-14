package goldenFleece.game.ui;

import java.util.ArrayList;

import processing.core.PApplet;

/////
// This class is currently unused
/////

public class Curve
{
	protected PApplet m_parent;
	
	protected ArrayList<CurvePoint> m_points;
	
	public Curve(PApplet parent)
	{
		m_parent = parent;
		m_points = new ArrayList<CurvePoint>();
	}
	
	public int numPoints() { return m_points.size(); }
	public int getNthX(int n) { if (n < numPoints()) return m_points.get(n).x; else return -1; }
	public int getNthY(int n) { if (n < numPoints()) return m_points.get(n).y; else return -1; }
	
	public void loadCurve(String filename)
	{
		String[] pointStrings = m_parent.loadStrings(filename);
		for (String pointString : pointStrings)
		{
			String[] coordStrings = pointString.split(" ");
			CurvePoint point = new CurvePoint();
			point.x = Integer.parseInt(coordStrings[0]);
			point.y = Integer.parseInt(coordStrings[1]);
			m_points.add(point);
		}
	}
	
	public void drawCurve()
	{
		for (CurvePoint point : m_points)
		{
			m_parent.point(point.x, point.y);
		}
	}
	
	
	protected class CurvePoint
	{
		public int x;
		public int y;
	}
}
