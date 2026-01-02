import { Box, Container, Flex } from "@chakra-ui/react";
import CalendarPanel from "../../features/panel";
import { Outlet } from "react-router-dom";
import CalendarMain from "../../features/write";

const CalendarPage = () => {
  return (
    <Container>
      <CalendarPanel />
      <CalendarMain />
    </Container>
  );
};

export default CalendarPage;
