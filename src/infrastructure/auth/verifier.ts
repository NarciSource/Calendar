import { CognitoJwtVerifier } from "aws-jwt-verify";

const userPoolId = process.env.COGNITO_USER_POOL_ID;
const clientId = process.env.COGNITO_CLIENT_ID;

if (!userPoolId || !clientId) {
    throw new Error(
        "COGNITO_USER_POOL_ID and COGNITO_CLIENT_ID must be set in environment variables",
    );
}

/**
 * Cognito JWT 검증기를 생성합니다.
 *
 * @remarks
 * 이 함수는 AWS Cognito User Pool ID와 Client ID를 사용하여 액세스 토큰을 검증하는 데 사용됩니다.
 *
 * @see {@link https://github.com/awslabs/cognito-jwt-verifier} Cognito JWT Verifier 라이브러리
 *
 * @param userPoolId - AWS Cognito User Pool의 ID
 * @param clientId - AWS Cognito 클라이언트 ID
 * @param tokenUse - 검증할 토큰의 유형 ("access"로 설정됨)
 *
 * @returns CognitoJwtVerifier 인스턴스
 */
export default CognitoJwtVerifier.create({
    userPoolId,
    clientId,
    tokenUse: "access",
});
