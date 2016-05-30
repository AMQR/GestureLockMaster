package com.am.gesturelocklib.bean;

public class GestueCircleBean {
	private int ox;          // 圆心横坐标
	private int oy;          // 圆心纵坐标
	private float r;         // 半径长度
	private Integer num;     // 代表数值
	private boolean onTouch; // false=未选中
	public int getOx() {
		return ox;
	}
	public void setOx(int ox) {
		this.ox = ox;
	}
	public int getOy() {
		return oy;
	}
	public void setOy(int oy) {
		this.oy = oy;
	}
	public float getR() {
		return r;
	}
	public void setR(float r) {
		this.r = r;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public boolean isOnTouch() {
		return onTouch;
	}
	public void setOnTouch(boolean onTouch) {
		this.onTouch = onTouch;
	}
	public boolean isPointIn(int x, int y) {
		double distance = Math.sqrt((x - ox) * (x - ox) + (y - oy) * (y - oy));
		return distance < r;
	}
}
