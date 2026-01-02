import { Box, Text, Button, Flex } from "@chakra-ui/react";
import { useState } from "react";
import { GetInterviewDetailDTO } from "../api/calendarDTOList";
import EventInputField from "./eventInputField";
import { useAtom } from "jotai";
import { eventsAtom, selectedDateAtom } from "../../atom/writeAtom";

type EventListProps = {
  onDelete: (index: number) => void;
  onUpdate: (index: number, updatedEvent: GetInterviewDetailDTO) => void;
};

const EventList = ({ onDelete, onUpdate }: EventListProps) => {
  const [editIndex, setEditIndex] = useState<number | undefined>(undefined);
  const [editEvent, setEditEvent] = useState<GetInterviewDetailDTO | undefined>(
    undefined
  );

  const [events] = useAtom(eventsAtom);
  const [selectedDate] = useAtom(selectedDateAtom);

  const dateKey = selectedDate
    ? selectedDate.toLocaleDateString("sv-SE")
    : undefined;
  const eventList = dateKey ? events[dateKey] ?? [] : [];

  const handleEditClick = (index: number) => {
    setEditIndex(index);
    setEditEvent(eventList[index]);
  };

  const handleSaveClick = async () => {
    if (editIndex !== undefined && editEvent) {
      onUpdate(editIndex, editEvent);
      setEditIndex(undefined);
      setEditEvent(undefined);
    }
  };

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;

    setEditEvent((prev) => {
      if (!prev) return prev;

      if (name === "companyName") {
        return {
          ...prev,
          company: { ...prev.company, name: value },
        };
      }

      if (name === "location") {
        return {
          ...prev,
          company: { ...prev.company, location: value },
        };
      }

      return { ...prev, [name]: value };
    });
  };

  if (eventList.length === 0) {
    return <Text>일정이 없습니다.</Text>;
  }

  return (
    <Box mt={4}>
      {eventList.map((event, index) => (
        <Box key={index} mt={4} p={2} borderWidth="1px" borderRadius="md">
          {editIndex === index ? (
            <Flex direction="column" gap={2}>
              <EventInputField
                placeholder="회사명"
                name="companyName"
                value={editEvent?.company.name || ""}
                onChange={handleInputChange}
              />
              <EventInputField
                placeholder="면접 유형"
                name="category"
                value={editEvent?.category || ""}
                onChange={handleInputChange}
              />
              <EventInputField
                placeholder="면접 장소"
                name="location"
                value={editEvent?.company.location || ""}
                onChange={handleInputChange}
              />
              <EventInputField
                placeholder="면접 시간"
                name="interviewTime"
                value={editEvent?.interviewTime || ""}
                onChange={handleInputChange}
              />
              <EventInputField
                placeholder="지원 직무"
                name="position"
                value={editEvent?.position || ""}
                onChange={handleInputChange}
              />
              <EventInputField
                placeholder="메모"
                name="description"
                value={editEvent?.description || ""}
                onChange={handleInputChange}
              />
              <Flex gap={2} mt={2} justifyContent="flex-end">
                <Button
                  background="none"
                  color="black"
                  onClick={handleSaveClick}
                >
                  저장
                </Button>
                <Button
                  background="none"
                  color="black"
                  onClick={() => setEditIndex(undefined)}
                >
                  취소
                </Button>
              </Flex>
            </Flex>
          ) : (
            <>
              <Text fontWeight="bold">회사명: {event.company?.name}</Text>
              <Text>면접 유형: {event.category}</Text>
              <Text>면접 장소: {event.company?.location}</Text>
              <Text>면접 시간: {event.interviewTime}</Text>
              <Text>지원 직무: {event.position}</Text>
              <Text>메모: {event.description}</Text>
              <Flex gap={2} mt={2} justifyContent="flex-end">
                <Button variant="ghost" onClick={() => handleEditClick(index)}>
                  수정
                </Button>
                <Button variant="ghost" onClick={() => onDelete(index)}>
                  삭제
                </Button>
              </Flex>
            </>
          )}
        </Box>
      ))}
    </Box>
  );
};

export default EventList;
