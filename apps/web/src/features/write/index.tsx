import { Flex } from "@chakra-ui/react";
import { useEffect } from "react";
import CalendarForm from "./ui/calendarForm";
import { Interview } from "../../entities/events/model/Interview";
import {
  createInterview,
  deleteInterview,
  getCalendar,
  getInterview,
  updateInterview,
} from "./api/calendarApi";
import { useQuery } from "@tanstack/react-query";
import { useAtom } from "jotai";
import {
  currentMonthAtom,
  eventsAtom,
  selectedDateAtom,
} from "../atom/writeAtom";

const CalendarMain = () => {
  const [selectedDate, setSelectedDate] = useAtom(selectedDateAtom);
  const [events, setEvents] = useAtom(eventsAtom);
  const [currentMonth] = useAtom(currentMonthAtom);

  const month = currentMonth.toLocaleDateString("sv-SE").slice(0, 7);
  const dateKey = selectedDate!.toLocaleDateString("sv-SE");

  const { data: fetchedEvents } = useQuery({
    queryKey: ["calendar", month],
    queryFn: () => getCalendar(month),
    staleTime: 1000 * 60 * 60,
  });

  useEffect(() => {
    if (fetchedEvents) {
      setEvents(fetchedEvents);
      console.log("events:", fetchedEvents);
    }
  }, [fetchedEvents, setEvents]);

  const handleDateClick = async (date: Date | null) => {
    if (!date) return;

    const dateKey = date.toLocaleDateString("sv-SE");

    if (!events.interviewDetailId) {
      setSelectedDate(date);
      return;
    }

    try {
      const interview = await getInterview(dateKey);

      setEvents((prev) => ({
        ...prev,
        [dateKey]: interview ? [interview] : [],
      }));

      setSelectedDate(date);
    } catch (error) {
      console.error("해당 날짜의 인터뷰 데이터를 불러오지 못했습니다:", error);
    }
  };

  const handleAddEvent = async (newEvent: Interview) => {
    if (!selectedDate) return;

    newEvent.interviewTime = `${dateKey}T${newEvent.interviewTime}`;

    try {
      await createInterview(newEvent);
      setEvents((prev) => ({
        ...prev,
        [dateKey]: [...(prev[dateKey] || []), newEvent],
      }));
    } catch (error) {
      console.error("실패!:", error);
    }
  };

  const handleDeleteEvent = async (index: number) => {
    if (!selectedDate) return;

    const eventsForDate = events[dateKey] || [];

    const eventToDelete = eventsForDate[index];
    if (!eventToDelete) return;

    try {
      await deleteInterview(eventToDelete.interviewDetailId);
      console.log(`Deleted event with ID: ${eventToDelete.interviewDetailId}`);

      setEvents((prev) => {
        const updatedEvents = [...(prev[dateKey] || [])];
        updatedEvents.splice(index, 1);
        return { ...prev, [dateKey]: updatedEvents };
      });
    } catch (error) {
      console.error("Failed to delete interview event:", error);
    }
  };

  const handleUpdateEvent = async (index: number, updatedEvent: Interview) => {
    if (!selectedDate) return;

    updatedEvent.interviewTime = `${dateKey}T${updatedEvent.interviewTime}`;

    try {
      await updateInterview(updatedEvent.interviewDetailId, updatedEvent);

      setEvents((prev) => {
        const updatedEvents = [...(prev[dateKey] || [])];
        updatedEvents[index] = updatedEvent;
        return { ...prev, [dateKey]: updatedEvents };
      });

      console.log("Interview event successfully updated!");
    } catch (error) {
      console.error("Failed to update interview event:", error);
    }
  };

  return (
    <CalendarForm
      onAddEvent={handleAddEvent}
      onDeleteEvent={handleDeleteEvent}
      onUpdateEvent={handleUpdateEvent}
    />
  );
};

export default CalendarMain;
