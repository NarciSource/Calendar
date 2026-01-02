import singleSpaReact from "single-spa-react";

import React from "react";
import ReactDOM from "react-dom";

import App from "./App";

const lifecycle = singleSpaReact({
    React,
    ReactDOM,
    rootComponent: App,
    errorBoundary(err, info, props) {
        console.error(err, info, props);
        return <div>Error</div>;
    },
});

export const { bootstrap, mount, unmount } = lifecycle;
