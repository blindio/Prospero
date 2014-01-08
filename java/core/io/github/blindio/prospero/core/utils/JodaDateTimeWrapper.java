/*******************************************************************************
 * Copyright 2014 S. Thorson Little
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package io.github.blindio.prospero.core.utils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class JodaDateTimeWrapper {

    public static final String DATE_FORMAT_STRING_YMD = "yyyy/MM/dd";
    public static final String DATE_FORMAT_STRING_MDY = "MM/dd/yyyy";
    public static final String TIME_FORMAT_STRING_HHMM = "hh:mm a";

    private DateTime jodaDateTime;
    private static final DateTimeFormatter formatYMD = DateTimeFormat
	    .forPattern(DATE_FORMAT_STRING_YMD);
    private static final DateTimeFormatter formatMDY = DateTimeFormat
	    .forPattern(DATE_FORMAT_STRING_MDY);
    private static final DateTimeFormatter formatTime = DateTimeFormat
	    .forPattern(TIME_FORMAT_STRING_HHMM);
    private static DateTimeFormatter dateTimeFormat = formatMDY;

    public static final int SUNDAY = DateTimeConstants.SUNDAY;
    public static final int MONDAY = DateTimeConstants.MONDAY;
    public static final int TUESDAY = DateTimeConstants.TUESDAY;
    public static final int WEDNESDAY = DateTimeConstants.WEDNESDAY;
    public static final int THURSDAY = DateTimeConstants.THURSDAY;
    public static final int FRIDAY = DateTimeConstants.FRIDAY;
    public static final int SATURDAY = DateTimeConstants.SATURDAY;

    public static final int JANUARY = DateTimeConstants.JANUARY;
    public static final int FEBRUARY = DateTimeConstants.FEBRUARY;
    public static final int MARCH = DateTimeConstants.MARCH;
    public static final int APRIL = DateTimeConstants.APRIL;
    public static final int MAY = DateTimeConstants.MAY;
    public static final int JUNE = DateTimeConstants.JUNE;
    public static final int JULY = DateTimeConstants.JULY;
    public static final int AUGUST = DateTimeConstants.AUGUST;
    public static final int SEPTEMBER = DateTimeConstants.SEPTEMBER;
    public static final int OCTOBER = DateTimeConstants.OCTOBER;
    public static final int NOVEMBER = DateTimeConstants.NOVEMBER;
    public static final int DECEMBER = DateTimeConstants.DECEMBER;

    public static synchronized void setUseYMDFormat(boolean useYMD) {
	dateTimeFormat = useYMD ? formatYMD : formatMDY;
    }

    public static boolean getUseYMDFormat() {
	return dateTimeFormat == formatYMD;
    }

    public JodaDateTimeWrapper() {
	this.jodaDateTime = new DateTime();
    }

    public JodaDateTimeWrapper(int year, int month, int dayOfMonth) {
	this.jodaDateTime = new DateTime(year, month, dayOfMonth, 0, 0)
		.withTimeAtStartOfDay();
    }

    public JodaDateTimeWrapper(int hour, int minute) {
	this.jodaDateTime = new DateTime().withHourOfDay(hour)
		.withMinuteOfHour(minute);
    }

    public JodaDateTimeWrapper(DateTime joda) {
	this.jodaDateTime = joda;
    }

    public static JodaDateTimeWrapper parseDateString(String dateString) {
	return new JodaDateTimeWrapper(DateTime.parse(dateString,
		dateTimeFormat).withTimeAtStartOfDay());
    }

    public static JodaDateTimeWrapper parseYMDDateString(String ymdDateString) {
	return new JodaDateTimeWrapper(DateTime.parse(ymdDateString, formatYMD)
		.withTimeAtStartOfDay());
    }

    public static JodaDateTimeWrapper parseMDYDateString(String mdyDateString) {
	return new JodaDateTimeWrapper(DateTime.parse(mdyDateString, formatMDY)
		.withTimeAtStartOfDay());
    }

    public static JodaDateTimeWrapper parseTimeString(String timeString) {
	return new JodaDateTimeWrapper().withTimeString(timeString);
    }

    public static JodaDateTimeWrapper parseDateTimeStrings(String dateString,
	    String timeString) {
	return JodaDateTimeWrapper.parseDateString(dateString).withTimeString(
		timeString);
    }

    public static JodaDateTimeWrapper parseYMDDateTimeStrings(
	    String ymdDateString, String timeString) {
	return JodaDateTimeWrapper.parseYMDDateString(ymdDateString)
		.withTimeString(timeString);
    }

    public static JodaDateTimeWrapper parseMDYDateTimeStrings(
	    String mdyDateString, String timeString) {
	return JodaDateTimeWrapper.parseMDYDateString(mdyDateString)
		.withTimeString(timeString);
    }

    public String toDateString() {
	return this.jodaDateTime.toString(dateTimeFormat);
    }

    public String toDateStringMDY() {
	return this.jodaDateTime.toString(formatMDY);
    }

    public String toDateStringYMD() {
	return this.jodaDateTime.toString(formatYMD);
    }

    public String toTimeString() {
	return this.jodaDateTime.toString(formatTime);
    }

    public DateTime getJodaDateTime() {
	return this.jodaDateTime;
    }

    public JodaDateTimeWrapper plusDays(int amount) {
	return new JodaDateTimeWrapper(this.jodaDateTime.plusDays(amount));
    }

    public JodaDateTimeWrapper plusWeeks(int amount) {
	return new JodaDateTimeWrapper(this.jodaDateTime.plusWeeks(amount));
    }

    public JodaDateTimeWrapper plusMonths(int amount) {
	return new JodaDateTimeWrapper(this.jodaDateTime.plusMonths(amount));
    }

    public JodaDateTimeWrapper plusYears(int amount) {
	return new JodaDateTimeWrapper(this.jodaDateTime.plusYears(amount));
    }

    public int getMinuteOfHour() {
	return this.jodaDateTime.getMinuteOfHour();
    }

    public int getHourOfDay() {
	return this.jodaDateTime.getHourOfDay();
    }

    public int getDayOfMonth() {
	return this.jodaDateTime.getDayOfMonth();
    }

    public int getMonthOfYear() {
	return this.jodaDateTime.getMonthOfYear();
    }

    public int getYear() {
	return this.jodaDateTime.getYear();
    }

    public int getDayOfWeek() {
	return this.jodaDateTime.getDayOfWeek();
    }

    public JodaDateTimeWrapper withHourOfDay(int hour) {
	return new JodaDateTimeWrapper(this.jodaDateTime.withHourOfDay(hour));
    }

    public JodaDateTimeWrapper withMinuteOfHour(int hour) {
	return new JodaDateTimeWrapper(this.jodaDateTime.withMinuteOfHour(hour));
    }

    public JodaDateTimeWrapper withTime(int hour, int minute) {
	return new JodaDateTimeWrapper(this.jodaDateTime.withTime(hour, minute,
		0, 0));
    }

    public JodaDateTimeWrapper withDateString(String dateString) {
	JodaDateTimeWrapper newDate = JodaDateTimeWrapper
		.parseDateString(dateString);
	return this.withYMD(newDate.getYear(), newDate.getMonthOfYear(),
		newDate.getDayOfMonth());
    }

    public JodaDateTimeWrapper withYMDDateString(String ymdDateString) {
	JodaDateTimeWrapper newDate = JodaDateTimeWrapper
		.parseYMDDateString(ymdDateString);
	return this.withYMD(newDate.getYear(), newDate.getMonthOfYear(),
		newDate.getDayOfMonth());
    }

    public JodaDateTimeWrapper withMDYDateString(String mdyDateString) {
	JodaDateTimeWrapper newDate = JodaDateTimeWrapper
		.parseMDYDateString(mdyDateString);
	return this.withYMD(newDate.getYear(), newDate.getMonthOfYear(),
		newDate.getDayOfMonth());
    }

    public JodaDateTimeWrapper withTimeString(String timeString) {
	DateTime newTime = DateTime.parse(timeString, formatTime);
	return this.withTime(newTime.getHourOfDay(), newTime.getMinuteOfHour());
    }

    public JodaDateTimeWrapper withMD(int month, int dayOfMonth) {
	return this.withMonthOfYear(month).withDayOfMonth(dayOfMonth);
    }

    public JodaDateTimeWrapper withYMD(int year, int month, int dayOfMonth) {
	return this.withYear(year).withMonthOfYear(month)
		.withDayOfMonth(dayOfMonth);
    }

    public JodaDateTimeWrapper withNextDayOfWeek(int dayOfWeek) {
	JodaDateTimeWrapper nextDay;
	do {
	    nextDay = withTomorrow();
	} while (nextDay.getDayOfWeek() != dayOfWeek);
	return nextDay;
    }

    public JodaDateTimeWrapper withTomorrow() {
	return plusDays(1);
    }

    public JodaDateTimeWrapper withNextWeekDay() {
	JodaDateTimeWrapper nextDay = this.withTomorrow();
	while (nextDay.getDayOfWeek() == SUNDAY
		|| nextDay.getDayOfWeek() == SATURDAY) {
	    nextDay = nextDay.withTomorrow();
	}
	return nextDay;
    }

    public JodaDateTimeWrapper withNextSunday() {
	return withNextDayOfWeek(SUNDAY);
    }

    public JodaDateTimeWrapper withNextMonday() {
	return withNextDayOfWeek(MONDAY);
    }

    public JodaDateTimeWrapper withNextTuesday() {
	return withNextDayOfWeek(TUESDAY);
    }

    public JodaDateTimeWrapper withNextWednesday() {
	return withNextDayOfWeek(WEDNESDAY);
    }

    public JodaDateTimeWrapper withNextThursday() {
	return withNextDayOfWeek(THURSDAY);
    }

    public JodaDateTimeWrapper withNextFriday() {
	return withNextDayOfWeek(FRIDAY);
    }

    public JodaDateTimeWrapper withNextSaturday() {
	return withNextDayOfWeek(SATURDAY);
    }

    public JodaDateTimeWrapper withLastDayOfMonth() {
	return new JodaDateTimeWrapper(this.jodaDateTime.dayOfMonth()
		.withMaximumValue());
    }

    public JodaDateTimeWrapper withDayOfMonth(int dayOfMonth) {
	return new JodaDateTimeWrapper(
		this.jodaDateTime.withDayOfMonth(dayOfMonth));
    }

    public JodaDateTimeWrapper withMonthOfYear(int month) {
	return new JodaDateTimeWrapper(this.jodaDateTime.withMonthOfYear(month));
    }

    public JodaDateTimeWrapper withYear(int year) {
	return new JodaDateTimeWrapper(this.jodaDateTime.withYear(year));
    }

}
