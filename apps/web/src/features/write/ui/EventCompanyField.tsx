import { Input } from "@chakra-ui/react";
import { Company } from "../../../entities/events/model/Company";

type CompanyInputFieldsProps = {
  company: Company;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
};

const EventCompanyField = ({ company, onChange }: CompanyInputFieldsProps) => {
  return (
    <>
      <Input
        placeholder="회사명"
        name="name"
        value={company.name}
        onChange={onChange}
      />
      <Input
        placeholder="면접 장소"
        name="location"
        value={company.location}
        onChange={onChange}
      />
    </>
  );
};

export default EventCompanyField;
