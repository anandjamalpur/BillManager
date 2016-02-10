package com.afbb.customview;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * This is the custom view class to show the pie chart
 */
public class PieChart extends View {

	private List<Category> categories;
	private RectF rectf;
	private Paint paint;
	// private Random random;
	private Point centerPoint;

	/**
	 * Parameterized constructor
	 * 
	 * @param context
	 */
	public PieChart(Context context) {
		super(context);
		init();
	}

	/**
	 * This constructor is called when the view is defined in the xml layout
	 */
	public PieChart(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	/**
	 * Adds an category to the list of categories.
	 * 
	 * @param category
	 *            name of the category.
	 * @param amount
	 *            amount allocated for this category.
	 */
	public void addCategories(String category, float amount, String color) {
		// if the categories the null then initialize the categories.
		if (category == null) {
			categories = new ArrayList<PieChart.Category>();
		}
		categories.add(new Category(category, amount, color));
	}

	/**
	 * This method will invalidate the view and redraw the view again
	 */
	public void reDraw() {
		invalidate();
		requestLayout();
	}

	/**
	 * This method will refresh the data
	 */
	public void reFresh() {
		categories.clear();
	}

	/**
	 * Initializes the categories list and elements required for drawing the pieChart.
	 */
	private void init() {
		categories = new ArrayList<PieChart.Category>();
		rectf = new RectF();
		paint = new Paint();
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);
		// random = new Random();
		centerPoint = new Point();
	}

	/**
	 * This method will draw the views on the canvas
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		// If there is no data present then show the message only
		if (categories.size() == 0) {
			paint.setTextAlign(Align.CENTER);
			paint.setTextSize(20);
			canvas.drawText("No data to show", getWidth()/2, getHeight()/2, paint);
			return;
		}

		float totalAmount = 0;
		// Iterating through each category for calculating the amount.
		for (Category category : categories) {
			totalAmount += category.amount;
		}
		int width = getWidth();
		int height = getHeight();

		// Calculating the radius for the circle. radius of the circle is the
		// 75% of max radius of the circle.
		// float radius = (75 * (width / 2)) / 100;
		float radius = -1;
		if (width < height)
			radius = (50 * (width / 2)) / 100;
		else
			radius = (75 * (height / 2)) / 100;
		// calculating the center of the circle.
		int cx = width / 2;
		int cy = height / 2;
		centerPoint.x = cx;
		centerPoint.y = cy;
		// assigning the limits for the RectF.
		rectf.left = (int) (cx - radius);
		rectf.top = (int) (cy - radius);
		rectf.right = (int) (cx + radius);
		rectf.bottom = (int) (cy + radius);
		// Iterating through each category for calculating the amount.
		float angle = 0;
		// Iterating through each
		for (Category temp : categories) {
			float precent = (((float) (temp.amount / totalAmount)) * 360);
			// paint.setColor(Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
			paint.setColor(Color.parseColor(temp.color));

			canvas.drawArc(rectf, angle, precent, true, paint);
			paint.setColor(Color.BLACK);
			// Calculating the point on the circumference of the circle.
			Point point = getPointOnCircle(radius, angle + (precent / 2), centerPoint);
			// Calculating the point on the circumference of the circle with
			// higher radius.
			Point point2 = getPointOnCircle(radius + 20, angle + (precent / 2), centerPoint);
			canvas.drawLine(point.x, point.y, point2.x, point2.y, paint);
			paint.setColor(Color.BLACK);
			// If the x-coordinate of first point is greater than second point
			// then set the alignment to right else left.
			if (point.x > point2.x) {
				paint.setTextAlign(Align.RIGHT);
			} else {
				paint.setTextAlign(Align.LEFT);
			}

			// Set the text size
			paint.setTextSize(15);
			canvas.drawText(temp.name, point2.x, point2.y, paint);
			angle += precent;
		}
	}

	/**
	 * Calculates a point on the circle with given radius and given angle.
	 * 
	 * @param radius
	 *            radius of the circle.
	 * @param angleInDegrees
	 *            angle required for the given point.
	 * @param origin
	 *            center of the circle.
	 * @return calculated point on the circle.
	 */
	private Point getPointOnCircle(float radius, float angleInDegrees, Point origin) {
		// Convert from degrees to radians via multiplication by PI/180
		int x = (int) ((radius * Math.cos(angleInDegrees * Math.PI / 180F)) + origin.x);
		int y = (int) ((radius * Math.sin(angleInDegrees * Math.PI / 180F)) + origin.y);
		return new Point(x, y);
	}

	/**
	 * {@link Category} is a static inner class of {@link PieChart}. This class contains the name and amount of each category object.
	 */
	private static class Category {
		String name;
		float amount;
		String color;

		/**
		 * Parameterized constructor
		 * 
		 * @param name
		 * @param amount
		 */
		public Category(String name, float amount, String color) {
			super();
			this.name = name;
			this.amount = amount;
			this.color = color;
		}

	}

}
