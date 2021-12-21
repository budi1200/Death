package si.budimir.death.util

import org.joda.time.DateTimeZone
import org.joda.time.Instant
import org.joda.time.Period
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.PeriodFormatterBuilder


class DateTimeHelper {
    companion object {
        fun prettyPrintPeriod(input: Period): String {
            val formatter = PeriodFormatterBuilder()
                .appendYears().appendSuffix("y ")
                .appendMonths().appendSuffix("m ")
                .appendWeeks().appendSuffix("w ")
                .appendDays().appendSuffix("d ")
                .appendHours().appendSuffix("h ")
                .appendMinutes().appendSuffix("min ")
                .appendSeconds().appendSuffix("sec ")
                .printZeroNever()
                .toFormatter()

            return formatter.print(input)
        }

        fun prettyPrintTimestamp(timestamp: Long): String {
            val formatter = DateTimeFormat.forPattern("dd.MM.yyyy HH:mm").withZone(DateTimeZone.forID("Europe/Ljubljana"))
            val converted = Instant.ofEpochMilli(timestamp)

            return formatter.print(converted)
        }
    }
}