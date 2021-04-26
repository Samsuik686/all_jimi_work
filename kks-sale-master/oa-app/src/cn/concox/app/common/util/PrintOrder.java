package cn.concox.app.common.util;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

/**
 * 打印快递单类
 * @author Administrator
 *
 */
public class PrintOrder implements Printable {
	
	
	 

	private String[] value = null;// 所要打印的数据{ "001", "002", "003"}; 
	private int[][] position = null;// 每个数据在图片中的坐标{ { 10, 50 }, { 30, 70 }, { 50, 90 }}; 
	/** 
	 * implements Printable 
	 * PageFormat类描述了页面的定位信息、它的尺寸以及每英尺可绘制的区域（单位1/72nd）
	**/
	public int print(Graphics g, PageFormat pf, int pageIndex) {

//		System.out.println(pageIndex);// 只打一页
		if (pageIndex > 0) {
			return Printable.NO_SUCH_PAGE;
		}

		Graphics2D g2d = (Graphics2D) g;
		for (int i = 0; i < value.length; i++) {

			String str = value[i];

			int[] temp = position[i];

			int x = temp[0];

			int y = temp[1];

			// 设置打印字体（字体名称、样式和点大小）

			Font font = new Font("新宋体", Font.PLAIN, 9);

			g2d.setFont(font); // 设置字体

			g2d.drawString(str, x, y);
		}

		return Printable.PAGE_EXISTS;
	}

	public static void main(String[] args) {
		String[] wfp = new String[] { "深圳市康凯斯信息技术有限公司", "钱玲芳", "广东省深圳市宝安区67区留仙一路高新奇工业园C栋4楼","13480175400","四五圆梦专营店","广东省深圳市宝安区67区留仙一路高新奇工业园C栋4楼","13345678911" };
		int[][] position = new int[][] { { 102, 110 }, { 255, 110 }, { 50, 130 },{ 75,150 },{ 102,170 },{ 35,210 },{ 75,230} };
		printReport(wfp,position);

	}

	public static void printReport(String[] wfp,int[][] position) {
		PrinterJob pj = PrinterJob.getPrinterJob();// 创建一个打印任务
		PageFormat pf = PrinterJob.getPrinterJob().defaultPage();
		Paper paper = pf.getPaper();
		// 设置页面高和宽，A4纸为595,842
		double pageWidth = 595;

		double pageHeight = 810;
		paper.setSize(pageWidth, pageHeight);
		paper.setImageableArea(0, 0, pageWidth, pageHeight);

		pf.setOrientation(PageFormat.LANDSCAPE);
		// 设置打印方向，LANDSCAPE为横向，打印方向默认为纵向
		pf.setPaper(paper);
		PrintOrder printTest = new PrintOrder();
		printTest.setValue(wfp);
		printTest.setPosition(position);

		pj.setPrintable(printTest, pf);
		if (pj.printDialog()) { // 弹出打印对话框，打印对话框，用户可以通过它改变各种选项，例如：设置打印副本数目，页面方向，或者目标打印机。
			try {

				pj.print();

			} catch (PrinterException e) {

				e.printStackTrace();

			}
		}
	}

	/**
	 * @return 返回 position。
	 */
	public int[][] getPosition() {
		return position;
	}

	/**
	 * @param position
	 *            要设置的 position。
	 */

	public void setPosition(int[][] position) {
		this.position = position;
	}

	/**
	 * 
	 * @return 返回value
	 */
	public String[] getValue() {
		return value;

	}

	/**
	 * @param value
	 *            要设置的value。
	 */

	public void setValue(String[] value) {

		this.value = value;

	}

}  

