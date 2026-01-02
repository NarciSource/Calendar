import axios from "axios";
import { GetCalendarDTO, GetCompanyDTO, GetInterviewDetailDTO, GetInterviewDTO } from "./calendarDTOList";
import { Interview } from "../../../entities/events/model/Interview";
import { interviewToCreateDto } from "../service/interviewToDto";
import { DtoToCalendarEvents, DtoToInterview } from "../service/DtoToInterview";

const SERVER_URL = import.meta.env.VITE_SERVER_URL;
const TOKEN = import.meta.env.VITE_TOKEN;

const client = axios.create({
    baseURL: `http://localhost:8080/calendar`,
    headers: {
        Authorization: `Bearer eyJraWQiOiIyTVRUdXcwaWlBUnJRODN6WW5JVWs5bVlUUzNtcjV3Qm5JMXBnSGhMRllVPSIsImFsZyI6IlJTMjU2In0.eyJzdWIiOiI3NDQ4OGRmYy1lMGYxLTcwYmEtYTMyOS0yYzBmZGI4YTU0ZDYiLCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAuYXAtbm9ydGhlYXN0LTIuYW1hem9uYXdzLmNvbVwvYXAtbm9ydGhlYXN0LTJfUkZ4VENWeHR6IiwiY2xpZW50X2lkIjoiNjAwMTFidDBldjQ2bHBiNTFwYm5wdWdwbjciLCJvcmlnaW5fanRpIjoiMGUxMjYxNGMtY2UyOC00NmIyLTgwNmYtZDFiYWMxZjg4MGVmIiwiZXZlbnRfaWQiOiJlNjkwM2YwMS0yM2RhLTQzYzctOTVmNC00YjAzZTk2ZGRlMmMiLCJ0b2tlbl91c2UiOiJhY2Nlc3MiLCJzY29wZSI6ImF3cy5jb2duaXRvLnNpZ25pbi51c2VyLmFkbWluIiwiYXV0aF90aW1lIjoxNzMzMTIyMDg4LCJleHAiOjE3MzQ0MDk4NTQsImlhdCI6MTczNDQwNjI1NCwianRpIjoiYjc2Njk2YWMtNWM5Zi00OGYzLTg3OWEtNmUwMDhkNjIwNDE3IiwidXNlcm5hbWUiOiJ0ZXN0dXNlciJ9.RSS1P4m0gkiZjOwd5CCx211zZqSiF9WZPEGgp8yXNWiHKpaoV8Nd_L0EPM0Kuzw5Cyzj03BewuHHaAcA_z1W6v5Wb5TUWU21dx3ngRVWs8XiECv5plPr56BRy_f3EW11wSm6LqtFf_feOyd-EFx6mhXO8Jmb11wFHvQTX2JW8d2hogLDoIB3kCsIbPMJ961mnTvJCOCTqRYWT9RhkruWs0F82vHYHi75a_r0lEzE8bNABuIiWwQuOHujmnEiAutB2TdM_Cg3-lPPDvQJo278ywn5QIGD2eOmyksiM5GXRaQJpnUTRSZKi-7vYnXYkA3Llv3XVRHN-x-TFF4UPsXYhA`,
        "Content-Type": "application/json"
    }
}
);

export const updateCompany = async (interviewDetailId: string, data: GetCompanyDTO) => {
    const response = await client.put(`/interview/${interviewDetailId}`, data);
    return response.data;
}

export const updateInterview = async (interviewDetailId: string, data: Interview) => {
    const dto = interviewToCreateDto(data);
    const response = await client.put(`/interview?interviewDetailId=${interviewDetailId}`, dto);
    return response.data;
}

export const getInterview = async (interviewDetailId: string): Promise<Interview> => {
    const response = await client.get(`/interview?=interviewDetailId=${interviewDetailId}`);
    return DtoToInterview(response.data);
}

export const getCalendar = async (date: string): Promise<Record<string, Interview[]>> => {
    const response = await client.get<GetCalendarDTO>("/interviews?yearMonth=" + date);
    return DtoToCalendarEvents(response.data);
};
export const createInterview = async (data: Interview): Promise<{ interviewRecordId: string }> => {
    const dto = interviewToCreateDto(data);
    const response = await client.post('/interview', dto)
    console.log(data)
    return response.data;
}

export const deleteInterview = async (interviewDetailId: string) => {
    const response = await client.delete(`/interview?interviewDetailId=${interviewDetailId}`)
    return response.data
}