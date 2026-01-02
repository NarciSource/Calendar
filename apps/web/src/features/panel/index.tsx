import {
  Box,
  Button,
  PopoverCloseTrigger,
  PopoverContent,
  PopoverRoot,
  PopoverTrigger,
} from "@chakra-ui/react";
import CalendarWrapper from "./ui/calendarWrap";
import Preview from "./ui/Preview";
import { GrFormPrevious } from "react-icons/gr";
import { useAtom } from "jotai";
import { eventsAtom } from "../atom/writeAtom";

const CalendarPanel = () => {
  const [events] = useAtom(eventsAtom);

  console.log("CalendarPanel events:", events);
  return (
    <Box left="0" top="0" position="fixed">
      <PopoverRoot closeOnInteractOutside={false} defaultOpen unstyled>
        <PopoverTrigger asChild position={"fixed"}>
          <Button colorPalette="teal">OPEN</Button>
        </PopoverTrigger>
        <PopoverContent
          height="100vh"
          width="fit-content"
          borderWidth="1px"
          borderRadius="lg"
          position="fixed"
          bg="white"
        >
          <PopoverCloseTrigger alignSelf="end" p="10px" position={"fixed"}>
            <Button variant="ghost">
              <GrFormPrevious />
            </Button>
          </PopoverCloseTrigger>
          <Box mx="auto" my={10} p={4}>
            <CalendarWrapper />
            <Preview />
          </Box>
        </PopoverContent>
      </PopoverRoot>
    </Box>
  );
};
export default CalendarPanel;
