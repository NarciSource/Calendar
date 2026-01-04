const dbName = process.env.MONGO_INITDB_DATABASE;

db = db.getSiblingDB(dbName);

function dateInCurrentMonthUTC(day, hour, minute = 0) {
    const now = new Date();
    return new Date(Date.UTC(
        now.getUTCFullYear(),
        now.getUTCMonth(),
        day, hour, minute, 0, 0
    ));
}

db.schedule.insertMany([
    {
        _id: ObjectId(),
        clientId: "user:test",
        date: new Date(Date.now() + 10 * 60 * 1000),
        company: { name: "지원회사1", location: "잠실" },
        position: "마케팅",
        category: "1차 면접",
        description: "면접 15분 전까지 도착하기",
        createdAt: new Date(),
        updatedAt: new Date(),
    },
    {
        _id: ObjectId(),
        clientId: "user:test",
        date: new Date(Date.now() + 15 * 60 * 1000),
        company: { name: "지원회사2", location: "잠실" },
        position: "개발자",
        category: "1차 기술면접",
        description: "면접 15분 전까지 도착하기",
        createdAt: new Date(),
        updatedAt: new Date(),
    },
    {
        _id: ObjectId(),
        clientId: "user:test",
        date: new Date(Date.now() + 30 * 60 * 1000),
        company: { name: "지원회사3", location: "잠실" },
        position: "경영",
        category: "2차 기술면접",
        description: "면접 15분 전까지 도착하기",
        createdAt: new Date(),
        updatedAt: new Date(),
    },

    {
        _id: ObjectId(),
        clientId: "user:test",
        date: dateInCurrentMonthUTC(1, 17),
        company: { name: "지원회사4", location: "잠실" },
        position: "산업",
        category: "1차 기술면접",
        description: "면접 15분 전까지 도착하기",
        createdAt: new Date(),
        updatedAt: new Date(),
    },
    {
        _id: ObjectId(),
        clientId: "user:test",
        date: dateInCurrentMonthUTC(3, 14),
        company: { name: "지원회사5", location: "잠실" },
        position: "재무",
        category: "1차 면접",
        description: "면접 15분 전까지 도착하기",
        createdAt: new Date(),
        updatedAt: new Date(),
    },
    {
        _id: ObjectId(),
        clientId: "user:test",
        date: dateInCurrentMonthUTC(4, 6),
        company: { name: "지원회사6", location: "잠실" },
        position: "연구",
        category: "2차 면접",
        description: "면접 15분 전까지 도착하기",
        createdAt: new Date(),
        updatedAt: new Date(),
    },
    {
        _id: ObjectId(),
        clientId: "user:test",
        date: dateInCurrentMonthUTC(17, 8),
        company: { name: "지원회사7", location: "잠실" },
        position: "디자이너",
        category: "최종 면접",
        description: "면접 15분 전까지 도착하기",
        createdAt: new Date(),
        updatedAt: new Date(),
    },
    {
        _id: ObjectId(),
        clientId: "user:test",
        date: dateInCurrentMonthUTC(28, 19),
        company: { name: "지원회사8", location: "잠실" },
        position: "사장",
        category: "셀프 면접",
        description: "면접 15분 전까지 도착하기",
        createdAt: new Date(),
        updatedAt: new Date(),
    },
]);
