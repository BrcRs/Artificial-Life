package cellularautomata;


public class LsCell {
	public int whiteness;

	public enum CellType
	{
	    NORMAL, VOLCANO_UP, VOLCANO_DOWN, STOP, NORMALSTOP;
	}

	public CellType mode;

	public int parameter;

	public LsCell(int _whiteness, CellType _mode, int _parameter)
	{
		this.whiteness = _whiteness;
		this.mode = _mode;
		this.parameter = _parameter;

	}

	public LsCell()
	{
		this((int)(255/2.), CellType.NORMAL, 0);
	}

	public LsCell clone()
	{
		return new LsCell(this.whiteness, this.mode, this.parameter);
	}


}
