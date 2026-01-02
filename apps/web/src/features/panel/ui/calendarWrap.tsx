import Calendar from "react-calendar";
import "react-calendar/dist/Calendar.css";
import { Box } from "@chakra-ui/react";
import styled from "styled-components";
import { useAtom } from "jotai";
import {
  currentMonthAtom,
  eventsAtom,
  selectedDateAtom,
} from "../../atom/writeAtom";

export const StyledCalendar = styled(Calendar)`
  &.react-calendar-custom {
    border: 1px solid #ccc;
    padding: 10px;

    .react-calendar__tile {
      text-align: center;
      transition: background-color 0.3s ease;
      height: 45px;

      &:hover {
        background-color: #009a6e;
      }
    }

    .react-calendar__tile--now {
      background: grey !important;
    }

    .react-calendar__tile--now {
      background: none;
      border: solid 1px rgba(0, 154, 110, 0.5) !important;
    }

    .react-calendar__tile--range {
      background: rgba(0, 154, 110, 0.5) !important;
    }

    .highlight-tile {
      background-color: #90ee90 !important;
      border-radius: 50%;
      color: white;
      font-weight: bold;
    }
  }
`;

const CalendarWrapper = () => {
  const [selectedDate, setSelectedDate] = useAtom(selectedDateAtom);
  const [currentMonth, setCurrentMonth] = useAtom(currentMonthAtom);
  const [events] = useAtom(eventsAtom);

  const dateHasEvent = (date: Date) => {
    const dateKey = date.toLocaleDateString("sv-SE");
    return events[dateKey]?.length > 0;
  };

  return (
    <Box placeItems="center">
      <StyledCalendar
        onChange={(value) => {
          if (value instanceof Date) {
            setSelectedDate(value);
          } else if (Array.isArray(value)) {
            setSelectedDate(value[0]);
          } else {
            setSelectedDate(null);
          }
        }}
        onActiveStartDateChange={({ activeStartDate }) => {
          if (activeStartDate) {
            setCurrentMonth(activeStartDate);
          }
        }}
        value={selectedDate}
        className="react-calendar-custom"
        tileClassName={({ date, view }) =>
          view === "month" && dateHasEvent(date) ? "highlight-tile" : ""
        }
        calendarType="gregory"
        minDetail="month"
        prev2Label={null}
        next2Label={null}
        showNeighboringMonth={false}
        activeStartDate={currentMonth}
      />
    </Box>
  );
};

export default CalendarWrapper;
