package goldenFleece.game;

import goldenFleece.storySystem.state.DictionaryStateValue;
import goldenFleece.storySystem.state.GoldenFleeceStateValues;

public class Provisions
{
	protected static final int QUALITY_POOR_CEILING = 20;
	protected static final int QUALITY_AVERAGE_CEILING = 60;
	protected static final int QUALITY_GOOD_CEILING = 90;
	protected static final int QUALITY_EXCELLENT_CEILING = 100;

	protected static final int MIN_LEVEL = 0;
	protected static final int MAX_LEVEL = 100;

	protected static final int FOOD_REDUCTION_RATE = 50; // how many units to
														 // move before reducing
	
	protected static final int FOOD_AMOUNT_TO_REDUCE = 1; // how much to reduce
														  // by

	protected static final int WATER_REDUCTION_RATE = 40; // how many units to
														  // move before
														  // reducing
	
	protected static final int WATER_AMOUNT_TO_REDUCE = 1; // how much to reduce
														   // by

	protected float m_distSinceLastFoodReduction;
	protected float m_distSinceLastWaterReduction;

	protected GoldenFleeceGame m_game;

	public Provisions(GoldenFleeceGame game)
	{
		m_game = game;
		m_distSinceLastFoodReduction = 0;
		m_distSinceLastWaterReduction = 0;
	}

	public int getFoodNumber()
	{
		int num = 0;

		DictionaryStateValue value = (DictionaryStateValue) m_game
		        .getStateValue(GoldenFleeceStateValues.PROVISIONS);

		if (value != null)
		{
			num = value.getValue(GoldenFleeceStateValues.PROVISIONS_FOOD);
		}

		return num;
	}

	public int getWaterNumber()
	{
		int num = 0;

		DictionaryStateValue value = (DictionaryStateValue) m_game
		        .getStateValue(GoldenFleeceStateValues.PROVISIONS);

		if (value != null)
		{
			num = value.getValue(GoldenFleeceStateValues.PROVISIONS_WATER);
		}

		return num;
	}

	public String getFoodQualityText()
	{
		String text = "";

		if (getFoodNumber() <= QUALITY_POOR_CEILING)
		{
			text = "Poor";
		}
		else if (getFoodNumber() <= QUALITY_AVERAGE_CEILING)
		{
			text = "Average";
		}
		else if (getFoodNumber() <= QUALITY_GOOD_CEILING)
		{
			text = "Good";
		}
		else if (getFoodNumber() <= QUALITY_EXCELLENT_CEILING)
		{
			text = "Excellent";
		}

		return text;
	}

	public String getWaterQualityText()
	{
		String text = "";

		if (getWaterNumber() <= QUALITY_POOR_CEILING)
		{
			text = "Poor";
		}
		else if (getWaterNumber() <= QUALITY_AVERAGE_CEILING)
		{
			text = "Average";
		}
		else if (getWaterNumber() <= QUALITY_GOOD_CEILING)
		{
			text = "Good";
		}
		else if (getWaterNumber() <= QUALITY_EXCELLENT_CEILING)
		{
			text = "Excellent";
		}

		return text;
	}

	public void reduceFromMovement(float distance)
	{
		m_distSinceLastFoodReduction += distance;
		m_distSinceLastWaterReduction += distance;
		
		DictionaryStateValue value = (DictionaryStateValue) m_game
		        .getStateValue(GoldenFleeceStateValues.PROVISIONS);

		if (m_distSinceLastFoodReduction > FOOD_REDUCTION_RATE)
		{
			int foodNum = getFoodNumber(); 
			foodNum -= FOOD_AMOUNT_TO_REDUCE;
			foodNum = Math.max(foodNum, MIN_LEVEL);
			foodNum = Math.min(foodNum, MAX_LEVEL);
			
			value.modifyValue(GoldenFleeceStateValues.PROVISIONS_FOOD, foodNum);

			m_distSinceLastFoodReduction = 0;
		}

		if (m_distSinceLastWaterReduction > WATER_REDUCTION_RATE)
		{
			int waterNum = getWaterNumber(); 
			waterNum -= WATER_AMOUNT_TO_REDUCE;
			waterNum = Math.max(waterNum, MIN_LEVEL);
			waterNum = Math.min(waterNum, MAX_LEVEL);
			
			value.modifyValue(GoldenFleeceStateValues.PROVISIONS_WATER, waterNum);

			m_distSinceLastWaterReduction = 0;
		}
	}
}
