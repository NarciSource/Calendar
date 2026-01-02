/// <reference types="vite/client" />

interface ImportMetaEnv {
    readonly VITE_PUBLIC_URL: string;
    readonly VITE_SERVER_PORT: number;
    readonly VITE_MF_TYPE: "application" | "parcel";
}

interface ImportMeta {
    readonly env: ImportMetaEnv;
}
