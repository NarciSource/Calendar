module.exports = {
  parser: "@typescript-eslint/parser",
  parserOptions: {
    project: ["notification/tsconfig.json", "worker/tsconfig.json", "tsconfig.json"],
    tsconfigRootDir: __dirname,
    sourceType: "module",
  },
  plugins: ["@typescript-eslint/eslint-plugin"],
  extends: ["plugin:@typescript-eslint/recommended"],
  root: true,
  env: {
    node: true,
    jest: true,
  },
  ignorePatterns: [".eslintrc.js"],
  rules: {
    "@typescript-eslint/interface-name-prefix": "off",
    "@typescript-eslint/explicit-function-return-type": "off",
    "@typescript-eslint/explicit-module-boundary-types": "off",
    "@typescript-eslint/no-explicit-any": "off",
    "@typescript-eslint/no-unused-vars": "off",
    "linebreak-style": ["off"],
  },
  overrides: [
    {
      files: ["jest.config.js"],
      rules: {
        "@typescript-eslint/no-require-imports": "off",
      },
    },
    {
      files: ["notification/**/*.ts"],
      parserOptions: {
        project: "./notification/tsconfig.json",
        tsconfigRootDir: __dirname,
      },
    },
    {
      files: ["worker/**/*.ts"],
      parserOptions: {
        project: "./worker/tsconfig.json",
        tsconfigRootDir: __dirname,
      },
    },
  ],
};
