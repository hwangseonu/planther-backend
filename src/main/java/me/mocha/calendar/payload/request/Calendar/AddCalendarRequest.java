package me.mocha.calendar.payload.request.Calendar;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.mocha.calendar.model.entity.Calendar;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class AddCalendarRequest {

    @Max(2100)
    @Min(2000)
    @NotNull
    private int year;

    @Max(12)
    @Min(1)
    @NotNull
    private int month;

    @Max(31)
    @Min(1)
    private int day;

    @NotNull
    @NotEmpty
    private String title;

    @NotNull
    @NotEmpty
    private String content;

    @NotNull
    private Calendar.Type type;

}
