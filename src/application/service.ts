import { Inject, Injectable } from "@nestjs/common";

import NotificationEntity from "domain/entity";
import INotificationRepository from "domain/repository";

import { OptionsDTO, ReadRequestDTO, RegisterRequestDTO, UpdateRequestDTO } from "./dto";

/**
 * 알림 서비스를 제공하는 클래스입니다.
 */
@Injectable()
export default class NotificationService {
    /**
     * NotificationService 생성자입니다.
     *
     * @param repository 알림 저장소 인터페이스 구현체
     */
    constructor(
        @Inject("INotificationRepository")
        private repository: INotificationRepository,
    ) {}

    /**
     * 주어진 이벤트 ID, 전송 시간, 상태를 기반으로 알림 엔티티를 생성하고 저장소에 저장합니다.
     *
     * @param {RegisterRequestDTO} params - 알림 등록 요청 데이터 전송 객체
     * @returns {Promise<NotificationEntity>} 생성된 알림 엔티티를 반환합니다.
     */
    async register({ event_id, send_at, status }: RegisterRequestDTO): Promise<NotificationEntity> {
        const entity = new NotificationEntity(event_id, send_at, status); // 도메인 객체 생성

        return this.repository.create(entity); // ORM 엔티티 생성
    }

    /**
     * 기존 알림을 업데이트합니다.
     *
     * @param paramDTO 읽기 요청 DTO
     * @param bodyDTO 업데이트 요청 DTO
     * @returns 업데이트된 알림 엔티티
     * @throws Error 엔티티가 존재하지 않을 경우
     */
    async update(paramDTO: ReadRequestDTO, bodyDTO: UpdateRequestDTO) {
        const entity = await this.get(paramDTO); // 해당 엔티티가 있는지 확인
        if (!entity) {
            throw new Error("Entity not found.");
        }

        return this.register({ ...entity, ...paramDTO, ...bodyDTO });
    }

    /**
     * 특정 알림을 조회합니다.
     *
     * @param {ReadRequestDTO} params - 읽기 요청 데이터 전송 객체
     * @returns 조회된 알림 엔티티
     */
    async get({ event_id }: ReadRequestDTO): Promise<NotificationEntity> {
        return this.repository.findById(event_id);
    }

    /**
     * 주어진 필터 조건에 따라 알림 엔티티 목록을 가져옵니다.
     *
     * @param {OptionsDTO} params - 필터링에 사용할 옵션 객체
     * @returns {Promise<NotificationEntity[]>} 필터링된 알림 엔티티 배열을 반환합니다.
     */
    async getFilteredList({
        start_time,
        end_time,
        status,
    }: OptionsDTO): Promise<NotificationEntity[]> {
        return this.repository.findByReservationTime(start_time, end_time, status);
    }

    /**
     * 특정 알림을 삭제합니다.
     *
     * @param {ReadRequestDTO} params - 읽기 요청 데이터 전송 객체
     * @returns 삭제 성공 여부
     */
    async delete({ event_id }: ReadRequestDTO): Promise<boolean> {
        return this.repository.deleteById(event_id);
    }
}
