import { Interview } from "../../../entities/events/model/Interview";
import { PostInterviewDetailDTO } from "../api/calendarDTOList";

export function interviewToCreateDto(interview: Interview) {
    let formattedInterviewTime = interview.interviewTime;

    if (interview.interviewTime) {
        try {
            const date = new Date(`${interview.interviewTime}:00`);
            console.log("시간 확인:", date)

            formattedInterviewTime = date.toISOString();
            console.log("변환된 시간:", formattedInterviewTime);
        } catch (error) {
            console.error("면접 시간 변환 오류:", error);
        }
    }

    return {
        company: {
            name: interview.company.name,
            location: interview.company.location,
        },
        interviewTime: formattedInterviewTime,
        category: interview.category,
        position: interview.position,
        description: interview.description,
    } as PostInterviewDetailDTO;
}