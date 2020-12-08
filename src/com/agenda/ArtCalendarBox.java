package com.agenda;

import totalcross.sys.Convert;
import totalcross.sys.Settings;
import totalcross.sys.SpecialKeys;
import totalcross.ui.ArrowButton;
import totalcross.ui.Button;
import totalcross.ui.Control;
import totalcross.ui.Label;
import totalcross.ui.PushButtonGroup;
import totalcross.ui.UIColors;
import totalcross.ui.Window;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.event.KeyEvent;
import totalcross.ui.font.Font;
import totalcross.ui.gfx.Color;
import totalcross.ui.gfx.Graphics;
import totalcross.ui.gfx.Rect;
import totalcross.util.Date;
import totalcross.util.InvalidDateException;
import totalcross.util.Vector;

public class ArtCalendarBox extends Window {
	private int day = -1, month, year;
	private int sentDay, sentMonth, sentYear;
	private Button btnToday, btnClear, btnCancel;
	private ArrowButton btnMonthNext, btnMonthPrev, btnYearNext, btnYearPrev;
	private PushButtonGroup pbgDays;
	private String[] tempDays = new String[42];
	private StringBuffer sb = new StringBuffer(20);

	/** True if the user had canceled without selecting */
	public boolean canceled;

	/** The 7 week names painted in the control. */
	public static String[] weekNames = { "D", "S", "T", "Q", "Q", "S", "S" }; // guich#573_39
	/** The labels for Today, Clear and Cancel. */
	public static String[] todayClearCancel = { "Hoje", "Limpa", "Cancela" }; // guich@573_39

	/** The labels between the arrows: Month, Year */
	public static String[] yearMonth = { "ano", "m�s" };

	/**
	 * Constructs Calendar set to the current day.
	 */
	public ArtCalendarBox() {
		super(" ", uiAndroid ? ROUND_BORDER : RECT_BORDER); // no title yet
		uiAdjustmentsBasedOnFontHeightIsSupported = false;
		fadeOtherWindows = Settings.fadeOtherWindows;
		transitionEffect = Settings.enableWindowTransitionEffects ? TRANSITION_OPEN : TRANSITION_NONE;
		String[] defCaps = new String[42];
		for (int i = 0; i < 42; i++)
			defCaps[i] = Convert.toString(i + 10);
		pbgDays = new PushButtonGroup(defCaps, false, -1, -1, fm.charWidth('@') - 2, 6, true, PushButtonGroup.NORMAL);
	}

	private void setupUI() // guich@tc100b5_28
	{
		int hh = getClientRect().y + 2;

		int yearColor = Color.BRIGHT;
		int monthColor = Color.BRIGHT;

		started = true; // avoid calling the initUI method

		yearColor = UIColors.calendarAction;
		monthColor = UIColors.calendarAction;
		// setBackForeColors(UIColors.calendarBack, UIColors.calendarFore);
		setBackForeColors(Color.WHITE, Color.BLACK);

		// compute the window's rect
		Font bold = font.asBold();
		Font mini = font.adjustedBy(-4);
		int labH = bold.fm.height;
		int arrowW = uiAndroid ? 2 * labH / 3 : labH / 2;

		Button.commonGap = 2;
		btnToday = new Button(todayClearCancel[0]);
		btnClear = new Button(todayClearCancel[1]);
		btnCancel = new Button(todayClearCancel[2]);
		btnToday.setFont(font);
		btnClear.setFont(font);
		btnCancel.setFont(font);

		btnCancel.setFont(font);
		int btnH = btnCancel.getPreferredHeight();
		Button.commonGap = 0;

		pbgDays.insideGap = fm.charWidth('@') - 2;
		pbgDays.setFont(font);
		int pbgW = uiAndroid ? (Math.min(fmH * 16, Math.min(Settings.screenWidth, Settings.screenHeight)) - 20) / 7 * 7
				: pbgDays.getPreferredWidth();
		int cellWH = pbgW / 7;
		int captionW = bold.fm.getMaxWidth(Date.monthNames, 0, Date.monthNames.length) + bold.fm.stringWidth("2011   ");
		int titleW = 4 * arrowW + mini.fm.stringWidth(yearMonth[0]) + mini.fm.stringWidth(yearMonth[1]) + captionW; // guich@tc130:
																													// avoid
																													// problems
																													// if
																													// title
																													// is
																													// too
																													// small
		pbgDays.setBackColor(Color.getRGB(131, 170, 218));
		pbgDays.setForeColor(Color.WHITE);
		setRect(CENTER, CENTER, Math.max(titleW, pbgW) + 10, 18 + hh + labH + cellWH * 6 + btnH); // same gap in all
																									// corners

		// arrows
		btnYearPrev = new ArrowButton(Graphics.ARROW_LEFT, arrowW, yearColor);
		btnYearNext = new ArrowButton(Graphics.ARROW_RIGHT, arrowW, yearColor);
		btnMonthPrev = new ArrowButton(Graphics.ARROW_LEFT, arrowW, monthColor);
		btnMonthNext = new ArrowButton(Graphics.ARROW_RIGHT, arrowW, monthColor);
		btnYearPrev.setFont(font);
		btnYearNext.setFont(font);
		btnMonthPrev.setFont(font);
		btnMonthNext.setFont(font);
		btnYearPrev.setBorder(Button.BORDER_NONE);
		btnYearNext.setBorder(Button.BORDER_NONE);
		btnMonthPrev.setBorder(Button.BORDER_NONE);
		btnMonthNext.setBorder(Button.BORDER_NONE);
		btnYearPrev.transparentBackground = btnYearNext.transparentBackground = btnMonthPrev.transparentBackground = btnMonthNext.transparentBackground = true;

		int bw = uiAndroid ? PREFERRED + fmH / 2 : PREFERRED;
		Label l1, l2, l;

		l1 = new Label(yearMonth[0]);
		l2 = new Label(yearMonth[1]);
		l1.setFont(mini);
		l2.setFont(mini);
		int yb = (titleGap + titleFont.fm.height - labH) / 2;

		add(btnYearPrev, LEFT + 2, yb, bw, PREFERRED);
		add(l1, AFTER, CENTER_OF, PREFERRED, SAME);
		add(btnYearNext, AFTER, yb, bw, PREFERRED);
		add(btnMonthNext, RIGHT - 2, yb, bw, PREFERRED);
		add(l2, BEFORE, CENTER_OF, PREFERRED, SAME);
		add(btnMonthPrev, BEFORE, yb, bw, PREFERRED);

		// change title alignment to use the area available between the buttons
		titleAlign = CENTER - (btnMonthPrev.getX() - btnYearNext.getX2() - (captionW - bold.fm.charWidth(' ') * 2)) / 2;

		// buttons
		Button.commonGap = 2;
		add(btnToday, LEFT + 4, BOTTOM - 4, bw, PREFERRED);
		add(btnClear, CENTER, SAME, bw, PREFERRED);
		add(btnCancel, RIGHT - 4, SAME, bw, PREFERRED);
		Button.commonGap = 0;

		// days
		add(pbgDays);
		if (!uiAndroid)
			pbgDays.setSimpleBorder(true);
		pbgDays.setRect(CENTER + 3, BEFORE - 4, pbgW, 6 * cellWH + 1); // +3 = 6 (buttons that have joined sides) / 2
		pbgDays.setCursorColor(Color.brighter(yearColor));

		// weeks
		int xx = pbgDays.getX();
		for (int i = 0; i < 7; i++) {
			l = new Label(weekNames[i]);
			l.setFont(bold);
			Rect r = pbgDays.rects[i];
			add(l, xx + r.x + (r.width - l.getPreferredWidth()) / 2, hh);
		}

		l1.transparentBackground = l2.transparentBackground = true;
		l1.setForeColor(backColor);
		l2.setForeColor(backColor);
		btnToday.setBackColor(Color.getRGB(33, 103, 165));
		btnToday.setForeColor(Color.WHITE);
		btnClear.setBackColor(Color.getRGB(33, 103, 165));
		btnClear.setForeColor(Color.WHITE);
		btnCancel.setBackColor(Color.getRGB(33, 103, 165));
		btnCancel.setForeColor(Color.WHITE);
		btnYearPrev.setBackColor(UIColors.calendarArrows);
		btnYearNext.setBackColor(UIColors.calendarArrows);
		btnMonthNext.setBackColor(UIColors.calendarArrows);
		btnMonthPrev.setBackColor(UIColors.calendarArrows);

		tabOrder = new Vector(new Control[] { pbgDays, btnToday, btnClear, btnCancel, btnYearPrev, btnYearNext,
				btnMonthPrev, btnMonthNext });
		btnYearPrev.autoRepeat = btnYearNext.autoRepeat = btnMonthNext.autoRepeat = btnMonthPrev.autoRepeat = true; // guich@tc122_47
		btnYearPrev.AUTO_DELAY = btnYearNext.AUTO_DELAY = 300;
	}

	/**
	 * Returns the selected Date.
	 * 
	 * @return Date object set to the selected day, or null if an error occurs.
	 */
	public Date getSelectedDate() {
		if (day == -1)
			return null;
		try {
			return new Date(day, month, year);
		} catch (InvalidDateException ide) {
			return null;
		}
	}

	/**
	 * Sets the current day to the Date specified. If its null, sets the date to
	 * today.
	 */
	public void setSelectedDate(Date d) {
		if (d == null)
			d = new Date();
		sentMonth = month = d.getMonth();
		sentYear = year = d.getYear();
		sentDay = day = d.getDay();

		// sets the pbg days
		updateDays();
	}

	private void updateDays() {
		try {
			Date date = new Date(1, month, year);
			int start = date.getDayOfWeek();
			int end = start + date.getDaysInMonth();
			pbgDays.setSelectedIndex(-1);

			int d = 1;
			String[] days = tempDays;

			for (int i = 0; i < 42; i++) {
				pbgDays.setColor(i, -1, -1); // erase all colors
				days[i] = (start <= i && i < end) ? Convert.toString(d++) : "";
			}
			if (Settings.keyboardFocusTraversable)
				days[end] = " x"; // add a way to get out of the pbg without closing it
			// set sent color
			if (year == sentYear && month == sentMonth)
				pbgDays.setColor(sentDay - 1 + start, -1, UIColors.calendarAction);
			pbgDays.setNames(days);

			Window.needsPaint = true;
		} catch (InvalidDateException ide) {
		}
	}

	public void onEvent(Event event) {
		switch (event.type) {
		case KeyEvent.SPECIAL_KEY_PRESS:
			KeyEvent ke = (KeyEvent) event;
			if (ke.key == SpecialKeys.KEYBOARD_ABC || ke.key == SpecialKeys.KEYBOARD_123) // closes this window without
																							// selecting any day
			{
				day = sentDay; // guich@tc100
				unpop();
			} else if (ke.isDownKey()) // guich@tc122_47: here to below
				incYear(false);
			else if (ke.isUpKey())
				incYear(true);
			else if (ke.isNextKey())
				incMonth(true);
			else if (ke.isPrevKey())
				incMonth(false);
			break;
		case ControlEvent.PRESSED:
			if (event.target == btnToday) {
				setSelectedDate(null);
				day = sentDay; // guich@401_14
				unpop(); // guich@401_14
			} else if (event.target == btnClear) {
				day = -1;
				unpop();
			} else if (event.target == btnCancel) {
				canceled = true;
				unpop();
			} else if (event.target == pbgDays && pbgDays.getSelectedIndex() >= 0) {
				try {
					Date date = new Date(Convert.toInt(pbgDays.getSelectedItem()), month, year);
					day = date.getDay();
					unpop(); // closes this window
				} catch (Exception ide) {
					day = -1;
				}
			} else if (event.target == btnMonthNext)
				incMonth(true);
			else if (event.target == btnMonthPrev)
				incMonth(false);
			else if (event.target == btnYearNext)
				incYear(true);
			else if (event.target == btnYearPrev)
				incYear(false);
			break;
		}
	}

	private void incMonth(boolean inc) {
		if (inc) {
			if (++month == 13) {
				month = 1;
				year++;
			}
		} else {
			if (--month == 0) {
				year--;
				month = 12;
			}
		}
		updateDays();
	}

	private void incYear(boolean inc) {
		if (inc)
			year++;
		else
			year--;
		updateDays();
	}

	public void onPaint(Graphics g) {
		// Draw title with appropriote month and year
		sb.setLength(0);
		paintTitle(sb.append(getMonthName(month)).append(' ').append(year).toString(), g);
	}

	protected void onPopup() {
		if (children == null)
			setupUI();
		canceled = false;
		day = -1;
		setTitle(getMonthName(month) + " " + (new Date().getYear()));
		if (sentDay <= 0)
			setSelectedDate(null); // guich@300_45: makes the sentDate the default
	}

	protected void onUnpop() {
		setFocus(this);
	}

	protected void postUnpop() {
		if (!canceled) // guich@580_27
			postPressedEvent();
	}

	private String getMonthName(int mes) {
		String retorno = "";
		switch (mes) {

		case 1:
			retorno = "Janeiro";
			break;
		case 2:
			retorno = "Fevereiro";
			break;
		case 3:
			retorno = "Mar�o";
			break;
		case 4:
			retorno = "Abril";
			break;
		case 5:
			retorno = "Maio";
			break;
		case 6:
			retorno = "Junho";
			break;
		case 7:
			retorno = "Julho";
			break;
		case 8:
			retorno = "Agosto";
			break;
		case 9:
			retorno = "Setembro";
			break;
		case 10:
			retorno = "Outubro";
			break;
		case 11:
			retorno = "Novembro";
			break;
		case 12:
			retorno = "Dezembro";
			break;
		}
		return retorno;
	}
}
