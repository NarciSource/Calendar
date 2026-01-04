const dbName = process.env.MONGO_INITDB_DATABASE;
const user = process.env.MONGO_INITDB_ROOT_USERNAME;
const pwd = process.env.MONGO_INITDB_ROOT_PASSWORD;

db = db.getSiblingDB(dbName);

db.createUser({
    user,
    pwd,
    roles: [
        { role: "readWrite", db: "schedule" }
    ]
});
