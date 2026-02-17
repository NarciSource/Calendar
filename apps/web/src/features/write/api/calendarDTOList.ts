export interface ResponseDTO<T> {
    success: boolean,
    message: string,
    data: T
}

export interface PutCompanyDTO {
    name: string
    location: string
}

export interface PutInterviewDetailDTO {
    company: PutCompanyDTO
    date: string
    position: string
    category: string
    description: string
}

export interface PostCompanyDTO {
    name: string
    location: string
}

export interface PostInterviewDetailDTO {
    company: PostCompanyDTO
    date: string
    position: string
    category: string
    description: string
}

export interface PostApiResponseDTO {
    id: string
}

export interface GetCompanyDTO {
    name: string
    location: string
}

export interface GetInterviewDetailDTO {
    id: string
    clientId?: string
    company: GetCompanyDTO
    date: string
    position: string
    category: string
    description: string
}
