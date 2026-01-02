import {
  Button,
  PopoverRoot,
  PopoverTrigger,
  PopoverContent,
  PopoverHeader,
  PopoverBody,
  PopoverTitle,
  PopoverCloseTrigger,
} from "@chakra-ui/react";
import EventForm from "./eventForm";
import { Interview } from "../../../entities/events/model/Interview";

type EventDialogProps = {
  newEvent: Interview;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  onAdd: () => void;
};

const EventDialog = ({ newEvent, onChange, onAdd }: EventDialogProps) => {
  return (
    <PopoverRoot>
      <PopoverTrigger asChild>
        <Button mt={4} colorPalette="teal" fontWeight="bold">
          일정 추가
        </Button>
      </PopoverTrigger>
      <PopoverContent>
        <PopoverHeader>
          <PopoverTitle>
            일정 추가
            <PopoverCloseTrigger>
              <Button variant="ghost">X</Button>
            </PopoverCloseTrigger>
          </PopoverTitle>
        </PopoverHeader>
        <PopoverBody>
          <EventForm newEvent={newEvent} onChange={onChange} onAdd={onAdd} />
        </PopoverBody>
      </PopoverContent>
    </PopoverRoot>
  );
};

export default EventDialog;
