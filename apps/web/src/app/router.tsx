import { createBrowserRouter } from "react-router-dom";
import CalendarPage from "../pages/calendar";
import CalendarMain from "../features/write";

const basename = import.meta.env.PUBLIC_URL || "/";
const router = createBrowserRouter(
  [
    {
      element: <CalendarPage />,
      children: [
        {
          path: "/:id?",
          element: <CalendarMain />,
        },
      ],
    },
  ],
  { basename }
);

export default router;
