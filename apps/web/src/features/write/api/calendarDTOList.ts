export interface PutCompanyDTO {
    name: string
    location: string
}

export interface PutInterviewDetailDTO {
    company: PutCompanyDTO
    interviewTime: string
    position: string
    category: string
    description: string
}

export interface PutApiResponseDTO {
    success: string
    message: string
}

export interface PostCompanyDTO {
    name: string
    location: string
}

export interface PostInterviewDetailDTO {
    company: PostCompanyDTO
    interviewTime: string
    position: string
    category: string
    description: string
}

export interface PostApiResponseDTO {
    success: string
    message: string
    interviewDetailId: string
}

export interface GetCalendarDTO {
    clientId: string
    interviewDetails: GetInterviewDetailDTO[]
}

export interface GetCompanyDTO {
    name: string
    location: string
}

export interface GetInterviewDetailDTO {
    interviewDetailId: string
    company: GetCompanyDTO
    interviewTime: string
    position: string
    category: string
    description: string
}

export interface GetInterviewDTO {
    clientId: string
    interviewDetailId: string
    company: GetCompanyDTO
    interviewTime: string
    position: string
    category: string
    description: string
}

export interface DeleteApiResponseDTO {
    success: string
    message: string
}