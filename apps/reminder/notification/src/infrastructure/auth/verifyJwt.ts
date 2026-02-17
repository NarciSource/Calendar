import { createRemoteJWKSet, jwtVerify } from "jose";

const jwt_set_url = "http://keycloak:8080/auth/realms/dev/protocol/openid-connect/certs";
const browser_access_url = `http://${process.env.PUBLIC_BASE_DOMAIN}:${process.env.PUBLIC_BASE_PORT}/auth/realms/dev`;
const client_id = process.env.KEYCLOAK_CLIENT_ID;

const JWKS = createRemoteJWKSet(new URL(jwt_set_url));

export default async function verifyJwt(token: string) {
    const { payload } = await jwtVerify(token, JWKS, {
        issuer: browser_access_url,
        audience: client_id,
    });

    return payload;
}
