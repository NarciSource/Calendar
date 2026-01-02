import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { Provider as ChakraProvider } from "../shared/chakra-ui/provider";
import CalendarPage from "../pages/calendar";

const queryClient = new QueryClient();

function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <ChakraProvider>
        <CalendarPage />
      </ChakraProvider>
    </QueryClientProvider>
  );
}

export default App;
