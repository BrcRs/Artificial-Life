package cellularautomata;

public class LsCell {
	public double height;
	
	public enum CellType 
	{
	    NORMAL, VOLCANO_UP, VOLCANO_DOWN; 
	}
	
	public CellType mode;
	
	public double parameter;

	public LsCell(double _height, CellType _mode, double _parameter)
	{
		this.height = _height;
		this.mode = _mode;
		this.parameter = _parameter;
		
	}
	
	public LsCell()
	{
		this(0., CellType.NORMAL, 0);
	}
	
	
}
