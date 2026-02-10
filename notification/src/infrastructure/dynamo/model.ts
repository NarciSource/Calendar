import dynamoose from "dynamoose";
import { ModelType } from "dynamoose/dist/General";
import { AnyItem } from "dynamoose/dist/Item";
import { Inject, Injectable } from "@nestjs/common";

import { NotificationStatus } from "domain/entity";

/**
 * DynamooseModel 클래스는 DynamoDB와 상호작용하기 위한 Dynamoose 모델을 생성하고 관리합니다.
 *
 * @@Injectable 데코레이터를 사용하여 의존성 주입이 가능하도록 설정됩니다.
 *
 * @class
 */
@Injectable()
export default class DynamooseModel {
    /**
     * Dynamoose 모델 인스턴스를 저장하는 필드입니다.
     * @private
     * @type {ModelType<AnyItem>}
     */
    private readonly model: ModelType<AnyItem>;

    /**
     * DynamooseModel 클래스의 생성자입니다.
     *
     * @param {typeof dynamoose} instance - 의존성 주입된 Dynamoose 인스턴스입니다.
     * @Inject("DYNAMOOSE") 데코레이터를 통해 주입됩니다.
     */
    constructor(@Inject("DYNAMOOSE") private readonly instance: typeof dynamoose) {
        const schema = new instance.Schema({
            event_id: { type: String, hashKey: true },
            send_at: {
                type: Date,
                index: {
                    type: "global",
                    name: "send_at-status-index",
                    rangeKey: "status",
                },
            },
            status: {
                type: String,
                enum: Object.values(NotificationStatus),
            },
        });

        this.model = this.instance.model("PickMe-Reminder", schema);
    }

    /**
     * Dynamoose 모델 인스턴스를 반환합니다.
     *
     * @returns {ModelType<AnyItem>} Dynamoose 모델 인스턴스
     */
    getModel(): ModelType<AnyItem> {
        return this.model;
    }
}
