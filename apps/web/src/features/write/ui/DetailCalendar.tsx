import Calendar from "react-calendar";
import "react-calendar/dist/Calendar.css";
import styled from "styled-components";
import { useAtom } from "jotai";
import {
  selectedDateAtom,
  currentMonthAtom,
  eventsAtom,
} from "../../atom/writeAtom";

export const StyledCalendar = styled(Calendar)`
  &.react-calendar-custom {
    border: 1px solid #ccc;
    box-shadow: 0px 10px 20px 0px #0000000d;
    width: 70%;
    max-width: 50vw;
    margin: 0 20px;

    .react-calendar__navigation {
      background: white;
      border-radius: 20px 20px 0 0;
      padding: 50px;
      span {
        font-size: 24px;
        font-weight: 600;
        color: black;
      }
    }

    .react-calendar__navigation button {
      font-size: 24px;
      font-weight: 700;
    }

    .react-calendar__month-view__weekdays {
      abbr {
        text-decoration: none;
      }
      font-size: 18px;
    }

    .react-calendar__month-view__days__day--weekend {
      color: #009a6e;
    }

    .react-calendar__tile {
      text-align: center;
      transition: background-color 0.3s ease;
      height: 100px;

      &:hover {
        background-color: rgba(0, 154, 110, 0.5);
      }
    }

    .react-calendar__tile--range {
      background: rgba(0, 154, 110, 0.5) !important;
    }

    .react-calendar__tile--now {
      background: none;
      border: solid 1px rgba(0, 154, 110, 0.5) !important;
    }

    .highlight-tile {
      background-color: #90ee90 !important;
      border-radius: 50%;
      color: white;
      font-weight: bold;
    }
  }
`;

const DetailCalendar = () => {
  const [selectedDate, setSelectedDate] = useAtom(selectedDateAtom);
  const [currentMonth, setCurrentMonth] = useAtom(currentMonthAtom);
  const [events] = useAtom(eventsAtom);

  const dateHasEvent = (date: Date) => {
    const dateKey = date.toLocaleDateString("sv-SE");
    return events[dateKey]?.length > 0;
  };

  console.log("selectedDate:", selectedDate);

  return (
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
  );
};

export default DetailCalendar;
