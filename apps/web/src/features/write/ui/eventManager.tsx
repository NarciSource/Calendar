import { Box, Text } from "@chakra-ui/react";
import EventList from "./eventList";
import { GetInterviewDetailDTO } from "../api/calendarDTOList";
import { useAtom } from "jotai";
import { selectedDateAtom } from "../../atom/writeAtom";

type EventManagerProps = {
  onDelete: (index: number) => void;
  onUpdate: (index: number, updatedEvent: GetInterviewDetailDTO) => void;
};

const EventManager = ({ onDelete, onUpdate }: EventManagerProps) => {
  const selectedDate = useAtom(selectedDateAtom)[0];

  return (
    <Box mt={6} width="700px">
      <Text fontSize="lg">
        {selectedDate
          ? selectedDate.toLocaleDateString("sv-SE")
          : "날짜를 선택해주세요!"}
        의 일정:
      </Text>
      <EventList onDelete={onDelete} onUpdate={onUpdate} />
    </Box>
  );
};

export default EventManager;
