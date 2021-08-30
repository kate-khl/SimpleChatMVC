package org.khl.chat.util;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class DateHelper {
	private DateHelper() {
	}

	public static final String DEFAULT_DATE_TIME_PATTERN = "dd-MM-yyyy'T'HH:mm:ss";
	public static final String ZONED_DATE_TIME_PATTERN = "d.MM.y HH:m:s XXX'['VV']'";
	public static final String ISO_OFFSSET_DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ssXXX";
	public static final DateTimeFormatter ISO_OFFSSET_DATE_TIME_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;
	private static final DateTimeFormatter QUEUE_DATE_TIME_FORMATTER = DateTimeFormatter
			.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

	private static final ZoneOffset defaultClientZoneOffset = ZoneOffset.of("+03:00");

	private static final DateTimeFormatter DEFAULT_OFFSET_DATE_TIME_FORMATTER = DateTimeFormatter
			.ofPattern("dd-MM-yyyy'T'HH:mm:ssXXX");

	public static OffsetDateTime parsetOffsetDateTime(String text) {
		return OffsetDateTime.parse(text, DEFAULT_OFFSET_DATE_TIME_FORMATTER);
	}

	public static OffsetDateTime now() {
		return OffsetDateTime.now(Clock.systemUTC());
	}

	public static OffsetDateTime parseISO(String text) {
		OffsetDateTime odt = OffsetDateTime.parse(text, ISO_OFFSSET_DATE_TIME_FORMATTER);
		return odt.withOffsetSameInstant(ZoneOffset.UTC);
	}

	public static boolean testISOValidDate(String text) {
		try {
			OffsetDateTime.parse(text, ISO_OFFSSET_DATE_TIME_FORMATTER);
			return true;
		} catch (DateTimeParseException ex) {
			return false;
		}
	}

	public static String asTextQueueLocalDateTime(OffsetDateTime ldt) {
		return ldt.format(QUEUE_DATE_TIME_FORMATTER);
	}

	public static String asTextISO(OffsetDateTime odt, String ifNull) {
		if (odt == null) {
			return ifNull;
		} else {
			return asTextISO(odt);
		}
	}

	public static String asTextISO(OffsetDateTime odt) {
		Objects.requireNonNull(odt);
		odt = odt.withOffsetSameInstant(defaultClientZoneOffset);
		return odt.format(ISO_OFFSSET_DATE_TIME_FORMATTER);
	}

	public static String asText(LocalDateTime ldt, String pattern) {
		return ldt.format(DateTimeFormatter.ofPattern(pattern));
	}

	public static LocalDateTime parseLocalDateTime(String dateText) {
		if (dateText == null) {
			return null;
		}
		if (dateText.contains("T")) {
			return LocalDateTime.parse(dateText, DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_PATTERN));
		} else {
			LocalDate localDate = LocalDate.parse(dateText, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
			return LocalDateTime.of(localDate, LocalTime.of(0, 0));
		}
	}

	public static LocalDateTime parseLocalDateTime(String dateText, String format) {
		return LocalDateTime.parse(dateText, DateTimeFormatter.ofPattern(format));
	}

}