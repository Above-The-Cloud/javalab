package window;

import java.awt.Point;

public class MoveStep {
	public Point src;
	public Point des;
	public Point getSrc() {
		return src;
	}
	public void setSrc(Point src) {
		this.src = src;
	}
	public Point getDes() {
		return des;
	}

	
	public void setDes(Point des) {
		this.des = des;
	}
	
	public MoveStep(Point src, Point des) {
		super();
		this.src = src;
		this.des = des;
	}
}
