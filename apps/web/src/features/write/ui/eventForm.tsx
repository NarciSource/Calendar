import { Box, Button } from "@chakra-ui/react";
import EventInputField from "./eventInputField";
import { Interview } from "../../../entities/events/model/Interview";
import { useState } from "react";

export type EventFormProps = {
  newEvent: Interview;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  onAdd: () => void;
};

const EventForm = ({ newEvent, onChange, onAdd }: EventFormProps) => {
  const [eventData, setEventData] = useState<Interview>(newEvent);

  const handleSubmit = async () => {
    try {
      onAdd();
    } catch (error) {
      console.error("면접 정보 저장 실패:", error);
    }
  };

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;

    setEventData((prev) => {
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
    onChange(e);
  };

  return (
    <Box>
      <EventInputField
        placeholder="회사명"
        name="company.name"
        value={eventData.company.name}
        onChange={handleInputChange}
      />
      <EventInputField
        placeholder="면접 장소"
        name="company.location"
        value={eventData.company.location}
        onChange={handleInputChange}
      />
      <EventInputField
        placeholder="면접 유형"
        name="category"
        value={eventData.category}
        onChange={handleInputChange}
      />
      <EventInputField
        placeholder="면접 시간"
        name="interviewTime"
        value={eventData.interviewTime}
        onChange={handleInputChange}
      />

      <EventInputField
        placeholder="지원 직무"
        name="position"
        value={eventData.position}
        onChange={handleInputChange}
      />
      <EventInputField
        placeholder="메모"
        name="description"
        value={eventData.description}
        onChange={handleInputChange}
      />
      <Button colorScheme="teal" onClick={handleSubmit}>
        저장
      </Button>
    </Box>
  );
};

export default EventForm;
