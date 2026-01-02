declare module "@styleguide/global" {
    export {};
}

declare module "@styleguide/react" {
    import { ForwardRefExoticComponent, RefAttributes } from "react";
    import {
        SystemContext,
        ButtonProps,
        IconButtonProps,
        FlexProps,
        NavLinkProps,
        DrawerContentProps,
    } from "@chakra-ui/react";

    const chakraUiSystem: SystemContext;

    const Container: React.FC<{ children: React.ReactNode }>;

    const Button: React.FC<ButtonProps>;

    const IconButton: React.FC<IconButtonProps>;

    const List: React.FC<{
        children: React.ReactNode;
        bordered?: boolean;
        separator?: boolean;
    }>;
    const Item: ForwardRefExoticComponent<
        FlexProps & NavLinkProps & RefAttributes<HTMLDivElement>
    >;

    const DrawerLayout: ForwardRefExoticComponent<
        DrawerContentProps & RefAttributes<HTMLDivElement>
    >;
    const DrawerHeader: React.FC<{ children: React.ReactNode }>;
    const DrawerBody: React.FC<{ children: React.ReactNode }>;
    const DrawerFooter: React.FC<{ children: React.ReactNode }>;

    const PaginateController: React.FC<{
        handlePageChange: (
            direction: "next" | "prev" | "first" | "last",
        ) => void;
        currentPage: number;
        totalPages: number;
    }>;

    export {
        chakraUiSystem,
        Container,
        Button,
        IconButton,
        List,
        Item,
        DrawerLayout,
        DrawerHeader,
        DrawerBody,
        DrawerFooter,
        PaginateController,
    };
}
