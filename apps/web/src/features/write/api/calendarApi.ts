import axios from "axios";
import { GetInterviewDetailDTO, PostApiResponseDTO, ResponseDTO } from "./calendarDTOList";
import { Interview } from "../../../entities/events/model/Interview";
import { interviewToCreateDto } from "../service/interviewToDto";
import { DtoToCalendarEvents, DtoToInterview } from "../service/DtoToInterview";

const SERVER_URL = import.meta.env.VITE_SERVER_URL;

const client = axios.create({
    baseURL: SERVER_URL,
    headers: {
        "Content-Type": "application/json"
    }
}
);

export const updateInterview = async (interviewDetailId: string, data: Interview) => {
    const dto = interviewToCreateDto(data);
    const response = await client.put<ResponseDTO<null>>(`/${interviewDetailId}`, dto);
    return response.data.data;
}

export const getInterview = async (interviewDetailId: string): Promise<Interview> => {
    const response = await client.get<ResponseDTO<GetInterviewDetailDTO>>(`/${interviewDetailId}`);
    return DtoToInterview(response.data.data);
}

export const getCalendar = async (from: string, to: string): Promise<Record<string, Interview[]>> => {
    const response = await client.get<ResponseDTO<GetInterviewDetailDTO[]>>(`?from=${from}&to=${to}`);
    return DtoToCalendarEvents(response.data.data);
};
export const createInterview = async (data: Interview): Promise<{ id: string }> => {
    const dto = interviewToCreateDto(data);
    const response = await client.post<ResponseDTO<PostApiResponseDTO>>('/', dto)
    console.log(data)
    return response.data.data;
}

export const deleteInterview = async (interviewDetailId: string) => {
    const response = await client.delete<ResponseDTO<null>>(`/${interviewDetailId}`)
    return response.data.data
}