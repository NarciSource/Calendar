import { Interview } from "../../../entities/events/model/Interview";
import { GetInterviewDetailDTO } from "../api/calendarDTOList";

const formatInterviewTime = (isoString: string): string => {
    if (!isoString) return "시간 미정";

    const date = new Date(isoString);

    return new Intl.DateTimeFormat("ko-KR", {
        year: "numeric",
        month: "long",
        day: "numeric",
        hour: "numeric",
        minute: "numeric",
        hour12: true,
    }).format(date);
};

export function DtoToInterview(dto: GetInterviewDetailDTO): Interview {
    return new Interview(
        dto.company,
        formatInterviewTime(dto.date),
        dto.position,
        dto.category,
        dto.description,
        dto.id
    );
}

export function DtoToCalendarEvents(dto: GetInterviewDetailDTO[]): Record<string, Interview[]> {
    const events: Record<string, Interview[]> = {};

    dto.forEach((event) => {
        if (!event.date) return;

        const date = new Date(event.date);
        const dateKey = date.toLocaleDateString("sv-SE");

        console.log("dateKey:", dateKey);

        if (!events[dateKey]) {
            events[dateKey] = [];
        }

        events[dateKey].push(DtoToInterview(event));
    });

    return events;
}