import { Box, Text, HStack } from "@chakra-ui/react";
import { GrStatusGoodSmall } from "react-icons/gr";
import { useAtom } from "jotai";
import { eventsAtom, selectedDateAtom } from "../../atom/writeAtom";

const Preview = () => {
  const [selectedDate] = useAtom(selectedDateAtom);
  const [events] = useAtom(eventsAtom);

  const dateKey = selectedDate
    ? selectedDate.toLocaleDateString("sv-SE")
    : undefined;
  const eventList = dateKey ? events[dateKey] ?? [] : [];

  console.log("Preview events:", events);
  return (
    <Box mt={6} w="20vw">
      <Text fontSize="lg">
        {selectedDate
          ? selectedDate.toLocaleDateString("sv-SE")
          : "날짜를 선택해주세요."}
        의 일정:
      </Text>
      {eventList.map((event, index) => (
        <Box
          key={index}
          p={4}
          borderWidth="1px"
          borderRadius="lg"
          my={2}
          maxW="20vw"
        >
          <HStack>
            <GrStatusGoodSmall color="teal" />
            <Text>
              회사: {event.company.name} | 장소: {event.company.location}
            </Text>
          </HStack>
        </Box>
      ))}
    </Box>
  );
};

export default Preview;
