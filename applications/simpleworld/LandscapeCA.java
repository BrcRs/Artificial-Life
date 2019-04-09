// ### WORLD OF CELLS ###
// created by nicolas.bredeche(at)upmc.fr
// date of creation: 2013-1-12

package applications.simpleworld;

import cellularautomata.CellularAutomataDouble;
import cellularautomata.CellularAutomataInteger;
import worlds.World;

import cellularautomata.LsCell;

import cellularautomata.CellularAutomataLS;

public class LandscapeCA extends CellularAutomataLS {

	private CellularAutomataLS _cellsLSCA;

	//World world;

	public LandscapeCA (  int __dx , int __dy)
	{
		super(__dx,__dy,true ); // buffering must be true.

		_cellsLSCA = new CellularAutomataLS(__dx, __dy, true);

		//_cellsLSCA.world = __world;
	}

	public CellularAutomataLS getCA()
	{
		return _cellsLSCA;
	}

	public void init()
	{
		for ( int x = 0 ; x != _dx ; x++ )
		for ( int y = 0 ; y != _dy ; y++ )
		{
			_cellsLSCA.setCellState(x, y, new LsCell());
		}
		_cellsLSCA.swapBuffer();

	}

	public void addVolcano(int __x, int __y)
	{
		_cellsLSCA.getCellState((int)(__x / 2), (int)(__y / 2)).mode = LsCell.CellType.VOLCANO_UP;
	}


	public void stepNormal(int ite, int itemax)
	{
		for ( int i = 0 ; i != _dx ; i++ )
		for ( int j = 0 ; j != _dy ; j++ )
		{
			/* NORMAL */
			if (_cellsLSCA.getCellState(i, j).mode == LsCell.CellType.NORMAL || _cellsLSCA.getCellState(i, j).mode == LsCell.CellType.NORMALSTOP)
			{
				//System.out.print(" ");

				if (Math.random() < 0.1 && ite < itemax/1.5) // chance of spontaneously be higher or lower
				{
					if (Math.random() < 0.5) // chance of getting lower
					{
						_cellsLSCA.getNewCellState(i, j).whiteness -= (int)(Math.random() * 100);

					}
					else // chance of getting higher
					{
						_cellsLSCA.getNewCellState(i, j).whiteness += (int)(Math.random() * 100);
					}
				}
				else // Be the mean value of surrounding neighbours, plus itself
				{
					int mean = 0;
					for (int di = -1 ; di <= 1 ; di ++)
					for (int dj = -1 ; dj <= 1 ; dj ++)
					{
						mean += _cellsLSCA.getCellState((i + di + _dx) % _dx, (j + dj + _dy) % _dy).whiteness;
					}
					mean = (int)(mean / 9.);
					_cellsLSCA.getNewCellState(i, j).whiteness = mean;
				}

				/* chance of becoming a VOLCANO_UP */
				if (Math.random() < 0.) // 0.000005
				{
					_cellsLSCA.getNewCellState(i, j).mode = LsCell.CellType.VOLCANO_UP;
				}

			}

			/* STOP */
			if (_cellsLSCA.getCellState(i, j).mode == LsCell.CellType.STOP)
			{

			}

			/* To be sure that the colors do not go higher than 255 or lower than 0 */
			if (_cellsLSCA.getNewCellState(i, j).whiteness < 0)
			{
				_cellsLSCA.getNewCellState(i, j).whiteness = 0;
			}
			if (_cellsLSCA.getNewCellState(i, j).whiteness > 255)
			{
				_cellsLSCA.getNewCellState(i, j).whiteness = 255;
			}
		}
		_cellsLSCA.swapBuffer();
	}



	public void stepVolcano(int ite, int itemax)
	{
		for ( int i = 0 ; i != _dx ; i++ )
		for ( int j = 0 ; j != _dy ; j++ )
		{


			/* VOLCANO UP */
			if (_cellsLSCA.getCellState(i, j).mode == LsCell.CellType.VOLCANO_UP)
			{
				//System.out.print("+");



				_cellsLSCA.getNewCellState(i, j).mode = LsCell.CellType.NORMALSTOP;

				if (_cellsLSCA.getCellState(i, j).parameter <= 7)
				{
					for (int di = -1 - (int)(Math.random() * 2); di <= 1 + (int)(Math.random() * 2); di ++)
					for (int dj = -1 - (int)(Math.random() * 2); dj <= 1 + (int)(Math.random() * 2); dj ++)
					{
						if (di == 0 && dj == 0)
						{
							continue;
						}
						/* if neighbour is normal, set it up to VOLCANO_UP, but with a modified parameter */
						if (_cellsLSCA.getCellState((i + di + _dx) % _dx, (j + dj + _dy) % _dy).mode == LsCell.CellType.NORMAL)
						{
							/**/
							/* Set neighbour to VOLCANO_UP */
							_cellsLSCA.getNewCellState((i + di + _dx) % _dx, (j + dj + _dy) % _dy).mode = LsCell.CellType.VOLCANO_UP;

							/* Increment neighbour's parameter */
							_cellsLSCA.getNewCellState((i + di + _dx) % _dx, (j + dj + _dy) % _dy).parameter = _cellsLSCA.getCellState(i, j).parameter + 1;

							/* Change neighbour's height */
							/**/
							_cellsLSCA.getNewCellState((i + di + _dx) % _dx, (j + dj + _dy) % _dy).whiteness += _cellsLSCA.getNewCellState((i + di + _dx) % _dx, (j + dj + _dy) % _dy).parameter * _cellsLSCA.getNewCellState((i + di + _dx) % _dx, (j + dj + _dy) % _dy).parameter * 1.5 + 1;
							/**
							_cellsLSCA.getNewCellState((i + di + _dx) % _dx, (j + dj + _dy) % _dy).whiteness += _cellsLSCA.getNewCellState((i + di + _dx) % _dx, (j + dj + _dy) % _dy).parameter * 255;

							if (_cellsLSCA.getNewCellState((i + di + _dx) % _dx, (j + dj + _dy) % _dy).parameter > 0)
							{
							System.out.println("Ah oui !");
						}
						/**/

						if (_cellsLSCA.getNewCellState((i + di + _dx) % _dx, (j + dj + _dy) % _dy).whiteness < 0)
						{
							_cellsLSCA.getNewCellState((i + di + _dx) % _dx, (j + dj + _dy) % _dy).whiteness = 0;
						}
						if (_cellsLSCA.getNewCellState((i + di + _dx) % _dx, (j + dj + _dy) % _dy).whiteness > 255)
						{
							_cellsLSCA.getNewCellState((i + di + _dx) % _dx, (j + dj + _dy) % _dy).whiteness = 255;
						}
						/**/

					}
					/*
					if (_cellsLSCA.getCellState((i + di + _dx) % _dx, (j + dj + _dy) % _dy).mode == LsCell.CellType.VOLCANO_DOWN)
					{
					_cellsLSCA.getNewCellState(i, j).mode = LsCell.CellType.NORMAL;
				}
				/**/
			}
		}
		/**/
		//if (Math.random() * 255 < _cellsLSCA.getNewCellState(i, j).whiteness)
		if (_cellsLSCA.getCellState(i, j).parameter > 7)
		{
			_cellsLSCA.getNewCellState(i, j).mode = LsCell.CellType.VOLCANO_DOWN;
			//_cellsLSCA.getCellState(i, j).mode = LsCell.CellType.STOP;
		}
		/*
		if (_cellsLSCA.getCellState(i, j).parameter > 5)
		{
		_cellsLSCA.getNewCellState(i, j).mode = LsCell.CellType.NORMAL;
	}
	/**/


}
/* VOLCANO DOWN */
if (_cellsLSCA.getCellState(i, j).mode == LsCell.CellType.VOLCANO_DOWN)
{
	System.out.print("-");

	/**
	_cellsLSCA.getNewCellState(i, j).whiteness =
	(int)(_cellsLSCA.getCellState(i, j).whiteness /1.1);
	/**/
	_cellsLSCA.getNewCellState(i, j).mode = LsCell.CellType.NORMALSTOP;

	if (_cellsLSCA.getCellState(i, j).parameter > 0)
	{
		for (int di = -1  - (int)(Math.random() * 2); di <= 1 + (int)(Math.random() * 2); di ++)
		for (int dj = -1 - (int)(Math.random() * 2); dj <= 1 + (int)(Math.random() * 2); dj ++)
		{
			if (di == 0 && dj == 0)
			{
				continue;
			}
			/* if neighbour is NORMAL, set it up to VOLCANO_DOWN */
			if (_cellsLSCA.getCellState((i + di + _dx) % _dx, (j + dj + _dy) % _dy).mode == LsCell.CellType.NORMAL)
			{
				_cellsLSCA.getNewCellState((i + di + _dx) % _dx, (j + dj + _dy) % _dy).mode = LsCell.CellType.VOLCANO_DOWN;

				/* Decrement neighbour's parameter */
				_cellsLSCA.getNewCellState((i + di + _dx) % _dx, (j + dj + _dy) % _dy).parameter = _cellsLSCA.getCellState(i, j).parameter - 1;


				/**/
				_cellsLSCA.getNewCellState((i + di + _dx) % _dx, (j + dj + _dy) % _dy).whiteness -=
				(- _cellsLSCA.getNewCellState((i + di + _dx) % _dx, (j + dj + _dy) % _dy).parameter * 1.1);
				/**
				_cellsLSCA.getNewCellState((i + di + _dx) % _dx, (j + dj + _dy) % _dy).whiteness -= 50;
				/**/



				if (_cellsLSCA.getNewCellState((i + di + _dx) % _dx, (j + dj + _dy) % _dy).whiteness < 0)
				{
					_cellsLSCA.getNewCellState((i + di + _dx) % _dx, (j + dj + _dy) % _dy).whiteness = 0;
				}
				if (_cellsLSCA.getNewCellState((i + di + _dx) % _dx, (j + dj + _dy) % _dy).whiteness > 255)
				{
					_cellsLSCA.getNewCellState((i + di + _dx) % _dx, (j + dj + _dy) % _dy).whiteness = 255;
				}


			}
		}
	}

	if (_cellsLSCA.getCellState(i, j).parameter <= 0)
	//if (Math.random() * 255 > _cellsLSCA.getNewCellState(i, j).whiteness)
	{
		_cellsLSCA.getNewCellState(i, j).mode = LsCell.CellType.NORMALSTOP;
		//_cellsLSCA.getNewCellState(i, j).mode = LsCell.CellType.STOP;
	}

}

if (_cellsLSCA.getCellState(i, j).mode == LsCell.CellType.STOP)
{

}

/* To be sure that the colors do not go higher than 255 or lower than 0 */
if (_cellsLSCA.getNewCellState(i, j).whiteness < 0)
{
	_cellsLSCA.getNewCellState(i, j).whiteness = 0;
}
if (_cellsLSCA.getNewCellState(i, j).whiteness > 255)
{
	_cellsLSCA.getNewCellState(i, j).whiteness = 255;
}
}
_cellsLSCA.swapBuffer();
}



public void growUp(int ite)
{

	this.addVolcano(100, 100);
	for (int i = 0 ; i < ite ; i++)
	{
		this.stepVolcano(i, ite);
	}
	for (int i = 0 ; i < ite ; i++)
	{
		this.stepNormal(i, ite);
	}

}


}
