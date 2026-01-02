import { Input } from "@chakra-ui/react";

type EventInputFieldProps = {
  placeholder: string;
  name: string;
  value: string;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
};

const EventInputField = ({
  placeholder,
  name,
  value,
  onChange,
}: EventInputFieldProps) => {
  return (
    <Input
      placeholder={placeholder}
      name={name}
      type={placeholder === "면접 시간" ? "time" : "text"}
      value={value}
      onChange={onChange}
      mb={2}
    />
  );
};

export default EventInputField;
