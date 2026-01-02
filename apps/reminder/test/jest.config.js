module.exports = {
    displayName: "e2e",
    preset: "ts-jest",
    testEnvironment: "node",
    rootDir: "../",

    testMatch: ["<rootDir>/test/**/*.e2e-spec.ts"],

    moduleNameMapper: {
        "^@notification/(.*)$": "<rootDir>/notification/src/$1",
        "^@worker/(.*)$": "<rootDir>/worker/src/$1",
    },
};
