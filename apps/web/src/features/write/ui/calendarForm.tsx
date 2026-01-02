import { useState } from "react";
import { Box } from "@chakra-ui/react";
import EventDialog from "./eventDialog";
import EventManager from "./eventManager";
import DetailCalendar from "./DetailCalendar";
import { Interview } from "../../../entities/events/model/Interview";

const CalendarForm = ({
  onAddEvent,
  onDeleteEvent,
  onUpdateEvent,
}: {
  onAddEvent: (newEvent: Interview) => void;
  onDeleteEvent: (index: number) => void;
  onUpdateEvent: (index: number, updatedEvent: Interview) => void;
}) => {
  const [newEvent, setNewEvent] = useState<Interview>(Interview.empty());

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;

    setNewEvent((prev) => {
      if (name.startsWith("company.")) {
        const field = name.split(".")[1];
        return {
          ...prev,
          company: {
            ...prev.company,
            [field]: value,
          },
        };
      } else {
        return { ...prev, [name]: value };
      }
    });
  };

  const handleAddEvent = () => {
    onAddEvent(newEvent);
    setNewEvent(Interview.empty());
  };

  return (
    <Box mx="auto" mt={10} p={4} placeItems="center">
      <DetailCalendar />
      <EventDialog
        newEvent={newEvent}
        onChange={handleInputChange}
        onAdd={handleAddEvent}
      />
      <EventManager onDelete={onDeleteEvent} onUpdate={onUpdateEvent} />
    </Box>
  );
};
export default CalendarForm;
